package me.mn7cc.shiftfly.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.mn7cc.shiftfly.custom.Database;

public class PlayerJoinListener implements Listener {
	
	@EventHandler (priority = EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerJoinEvent event) {
 		
    	Player player = event.getPlayer();
    	double speed = Database.getFlySpeed(player);
    	if(speed != 0 && !player.hasPermission("shiftfly.keeponlogout")) Database.setFlySpeed(player, 0);
    	
	}

}