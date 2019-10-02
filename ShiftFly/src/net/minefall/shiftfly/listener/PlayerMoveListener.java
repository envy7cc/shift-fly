package net.minefall.shiftfly.listener;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import net.minefall.shiftfly.custom.Database;
import net.minefall.shiftfly.custom.FileManager;
import net.minefall.shiftfly.files.ConfigFile;

public class PlayerMoveListener implements Listener {
	
	@EventHandler (priority = EventPriority.HIGHEST)
	public void onPlayerMove(PlayerMoveEvent event) {
		
		Player player = event.getPlayer();
		
		ConfigFile config = FileManager.getConfig();
		if(config.getDisabledWorlds().contains(player.getWorld().getName()) && !player.hasPermission("shiftfly.bypass")) return;
		
		if(!player.hasPermission("shiftfly.use"));
		
		double speed = Database.getFlySpeed(player);
		
		if(speed > 0 && player.isSneaking()) {
			
			Location l = player.getLocation();
			Vector v = l.getDirection();
			
			v.multiply(speed);
			player.setVelocity(v);
			
		}
		
	}

}
