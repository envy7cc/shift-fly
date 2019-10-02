package net.minefall.shiftfly.custom;

import org.bukkit.Bukkit;

import net.minefall.shiftfly.util.TextUtils;

public class Log {
	
	public static void INFO(String message) {
		Bukkit.getConsoleSender().sendMessage(TextUtils.color("&7[&fShift Fly&7] &f" + message));
	}
	
}
