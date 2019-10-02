package net.minefall.shiftfly.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import net.minefall.shiftfly.custom.Database;
import net.minefall.shiftfly.custom.FileManager;
import net.minefall.shiftfly.files.ConfigFile;

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
