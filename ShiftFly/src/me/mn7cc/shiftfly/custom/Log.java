package me.mn7cc.shiftfly.custom;

import org.bukkit.Bukkit;

import me.mn7cc.shiftfly.util.TextUtils;

public class Log {
	
	public static void INFO(String message) {
		Bukkit.getConsoleSender().sendMessage(TextUtils.color("&7[&fShift Fly&7] &f" + message));
	}
	
}
