package me.mn7cc.shiftfly;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import me.mn7cc.shiftfly.command.ShiftFlyCommand;
import me.mn7cc.shiftfly.custom.Database;
import me.mn7cc.shiftfly.custom.FileManager;
import me.mn7cc.shiftfly.listener.EntityDamageListener;
import me.mn7cc.shiftfly.listener.PlayerJoinListener;
import me.mn7cc.shiftfly.listener.PlayerMoveListener;
import me.mn7cc.shiftfly.task.DatabaseHeartbeatTask;
import me.mn7cc.shiftfly.util.MessageUtils;

public class ShiftFly extends JavaPlugin {
	
	private static Plugin plugin;
	public static Plugin getPlugin() { return plugin; }
	
	@Override
	public void onEnable() {
		
		plugin = this;
		
		FileManager.loadFiles();
		MessageUtils.loadMessages();
		Database.setup();
		Database.load();
		
		Bukkit.getServer().getPluginManager().registerEvents(new EntityDamageListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);
		
    	Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new DatabaseHeartbeatTask(), 5L, 5L);
		
	}
	
	@Override
	public void onDisable() {
		
		if(!Database.isClosed()) Database.heartbeat();
		
		Database.close();
		
	}
	
    @Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
    	
    	if(cmd.getName().equalsIgnoreCase("shiftfly")) { new ShiftFlyCommand(sender, args); return true; }
    	
		return true;
		
    }

}
