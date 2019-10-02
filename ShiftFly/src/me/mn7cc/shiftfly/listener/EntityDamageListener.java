package me.mn7cc.shiftfly.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import me.mn7cc.shiftfly.custom.Database;
import me.mn7cc.shiftfly.custom.FileManager;
import me.mn7cc.shiftfly.files.ConfigFile;

public class EntityDamageListener implements Listener {
	
	@EventHandler (priority = EventPriority.HIGHEST)
	public void onEntityDamage(EntityDamageEvent event) {
		
		if(event.getEntity() instanceof Player && event.getCause().equals(DamageCause.FALL)) {
			
			Player player = (Player) event.getEntity();
			
			ConfigFile config = FileManager.getConfig();
			if(config.getDisabledWorlds().contains(player.getWorld().getName()) && !player.hasPermission("shiftfly.bypass")) return;
			
			double speed  = Database.getFlySpeed(player);
			
			if(speed > 0) event.setCancelled(true);
			
		}
		
	}

}
