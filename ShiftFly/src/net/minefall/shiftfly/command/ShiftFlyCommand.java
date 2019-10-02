package net.minefall.shiftfly.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.minefall.shiftfly.custom.Database;
import net.minefall.shiftfly.custom.FileManager;
import net.minefall.shiftfly.custom.Message;
import net.minefall.shiftfly.files.ConfigFile;
import net.minefall.shiftfly.util.MathUtils;
import net.minefall.shiftfly.util.MessageUtils;
import net.minefall.shiftfly.util.ServerUtils;
import net.minefall.shiftfly.util.StringUtils;

public class ShiftFlyCommand {

	public ShiftFlyCommand(CommandSender sender, String[] args) {
		
		if(sender instanceof Player == false) {
			MessageUtils.send(sender, Message.REQUIRES_PLAYER_AS_SENDER);
			return;
		}
		
		if(!sender.hasPermission("shiftfly.use")) {
			MessageUtils.send(sender, Message.INSUFFICIENT_PERMISSIONS);
			return;
		}
		
		if(args.length > 2) {
			MessageUtils.send(sender, Message.COMMAND_USAGE, "/sf [player] [speed]");
			return;
		}
		
		Player player = (Player) sender;
		Player target = player;
		double speed = 0;
		
		ConfigFile config = FileManager.getConfig();
		
		if(args.length == 0) {
			
			Database.setFlySpeed(player, 0);
			MessageUtils.send(sender, Message.FLY_MODE_DISABLED);
			
			return;
			
		}
		else if(args.length == 1) {
			
			if(StringUtils.isDouble(args[0])) speed = StringUtils.parseDouble(args[0]);
			else target = ServerUtils.getPlayer(args[0]);
			
		}
		else if(args.length == 2) {
			
			target = ServerUtils.getPlayer(args[0]);
			
			if(target == null) {
				MessageUtils.send(sender, Message.PLAYER_NOT_FOUND);
				return;
			}
			
			if(!StringUtils.isDouble(args[1])) {
				MessageUtils.send(sender, Message.INVALID_SPEED, StringUtils.toString(config.getMinFlySpeed()), StringUtils.toString(config.getMaxFlySpeed()));
				return;
			}
			
			speed = StringUtils.parseDouble(args[1]);
			
		}
		
		if(speed != 0 && (speed < config.getMinFlySpeed() || speed > config.getMaxFlySpeed())) {
			MessageUtils.send(sender, Message.INVALID_SPEED, StringUtils.toString(config.getMinFlySpeed()), StringUtils.toString(config.getMaxFlySpeed()));
			return;
		}
		
		if(player != target && !player.hasPermission("shiftfly.others")) {
			MessageUtils.send(sender, Message.INSUFFICIENT_PERMISSIONS);
			return;
		}
		
		if(speed == 0) {
			
			Database.setFlySpeed(target, speed);
			
			if(player == target) {
				MessageUtils.send(player, Message.FLY_MODE_DISABLED);
			}
			else {
				MessageUtils.send(player, Message.FLY_MODE_DISABLED_OTHER, target.getName());
				MessageUtils.send(target, Message.FLY_MODE_DISABLED_TARGET, player.getName());
			}
			
			return;
			
		}
		
		Database.setFlySpeed(target, MathUtils.shorten(speed));
		
		if(player == target) {
			MessageUtils.send(player, Message.FLY_MODE_ENABLED, StringUtils.toString(speed));
		}
		else {
			MessageUtils.send(player, Message.FLY_MODE_ENABLED_OTHER, target.getName(), StringUtils.toString(speed));
			MessageUtils.send(target, Message.FLY_MODE_ENABLED_TARGET, player.getName(), StringUtils.toString(speed));
		}
		
		return;
		
	}

}
