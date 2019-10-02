package me.mn7cc.shiftfly.custom;

public enum Message {

	REQUIRES_PLAYER_AS_SENDER("&4This command cannot be run by the console!"),
	INSUFFICIENT_PERMISSIONS("&4You do not have permissions!"),
	COMMAND_USAGE("&7Usage: &f{0}"),
	PLAYER_NOT_FOUND("&4Player not found!"),
	INVALID_SPEED("&4<speed> has to be a number between {0} and {1}!"),
	FLY_MODE_ENABLED("&7Shift Fly Mode &aenabled &7with speed &2{0}&7."),
	FLY_MODE_DISABLED("&7Shift Fly Mode &cdisabled&7."),
	FLY_MODE_ENABLED_OTHER("&7Shift Fly Mode &aenabled &7for &f{0} &7with speed &2{1}&7."),
	FLY_MODE_DISABLED_OTHER("&7Shift Fly Mode &cdisabled &7for &f{0}&7."),
	FLY_MODE_ENABLED_TARGET("&7Shift Fly Mode has been &aenabled &7with speed &2{0} &7by &f{1}&7."),
	FLY_MODE_DISABLED_TARGET("&7Shift Fly Mode has been &cdisabled &7by &f{0}&7.");
	
	private String text;
	
	private Message(String text) {
		this.text = text;
	}
	
	public String getText() { return text; }
	
}
