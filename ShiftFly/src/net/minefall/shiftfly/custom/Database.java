package net.minefall.shiftfly.custom;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.entity.Player;

import net.minefall.shiftfly.files.ConfigFile;

import com.zaxxer.hikari.HikariDataSource;

public class Database {
	
	public static HikariDataSource datasource = new HikariDataSource();
	public static List<String> queue = new ArrayList<String>();
	public static boolean proxy = false;
	
	public static void setup() {

		try {
			
			ConfigFile config = FileManager.getConfig();
			
			if(config.isUsingMySQL()) {
				datasource.setJdbcUrl("jdbc:mysql://" + config.getMySQLHostname() + ":" + config.getMySQLPort() + "/" + config.getMySQLDatabase());
				datasource.setUsername(config.getMySQLUsername());
				datasource.setPassword(config.getMySQLPassword());
				datasource.setMinimumIdle(3);
				datasource.setMaximumPoolSize(3);
				datasource.addDataSourceProperty("cachePrepStmts", "true");
				datasource.addDataSourceProperty("prepStmtCacheSize", "250");
				datasource.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
			}
			else {
				datasource.setJdbcUrl("jdbc:sqlite:plugins/ShiftFly/database.db");
				datasource.setMinimumIdle(3);
				datasource.setMaximumPoolSize(3);
			}
			
			Connection connection = getConnection();
			DatabaseMetaData metadata = connection.getMetaData();
			ResultSet resultset = metadata.getTables(null, null, "%", null);
			
			List<String> tables = new ArrayList<String>();
			while(resultset.next()) tables.add(resultset.getString(3));
			
			if(!tables.contains("shiftfly_user")) execute("CREATE TABLE shiftfly_user (uuid VARCHAR(36), speed DOUBLE)");
			
			resultset.close();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void load() {
		
		try {
			
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			
			ResultSet user = statement.executeQuery("SELECT * FROM shiftfly_user");
			while(user.next()) users.put(UUID.fromString(user.getString("uuid")), user.getDouble("speed"));
			
			user.close();
			statement.close();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static boolean isClosed() {
		return datasource.isClosed();
	}
	
	public static Connection getConnection() {
		try {
			return datasource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void execute(String execute) {	
		try {
			
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			statement.execute(execute);	
			statement.close();
			connection.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void execute(List<String> execute) {	
		try {
		
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			for(String e : execute) statement.execute(e);
			statement.close();
			connection.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void queue(String sql) {
		queue.add(sql);
	}
	
	public static void heartbeat() {
		execute(queue);
		queue.clear();
	}
	
	public static void close() {
		execute(queue);
		queue.clear();
		datasource.close();
	}
	
	public static HashMap<UUID, Double> users = new HashMap<UUID, Double>();

	public static void setFlySpeed(Player player, double speed) {
		
		UUID uuid = player.getUniqueId();
		
		if(speed == 0 && users.containsKey(uuid)) {
			
			execute("DELETE FROM shiftfly_user WHERE uuid = '" + uuid.toString() + "'");
			users.remove(uuid);
			
		}
		else {
			
			if(!users.containsKey(uuid)) execute("INSERT INTO shiftfly_user VALUES ('" + uuid.toString() + "', " + speed + ")");
			else execute("UPDATE shiftfly_user SET speed = " + speed + " WHERE uuid = '" + uuid.toString() + "'");
			users.put(uuid, speed);
			
		}
		
	}
	
	public static double getFlySpeed(Player player) {
		UUID uuid = player.getUniqueId();
		return users.containsKey(uuid) ? users.get(uuid) : 0;
	}
	
}
