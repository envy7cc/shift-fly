package me.mn7cc.shiftfly.files;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;

import me.mn7cc.shiftfly.custom.Message;

public class MessagesFile extends BaseFile {
	
	public MessagesFile() {
		super(getBaseResource(), getBaseFile(), getBaseDefaults());
	}
	
	private static String getBaseResource() {
		return "messages.yml";
	}
	
	private static File getBaseFile() {
		return new File("plugins/ShiftFly/messages.yml");
	}
	
	private static LinkedHashMap<String, Object> getBaseDefaults() {
		
		LinkedHashMap<String, Object> defaults = new LinkedHashMap<String, Object>();
		
		for(Message message : Message.values()) defaults.put(message.name(), message.getText());
		
		return defaults;
		
	}
	
	public HashMap<String, String> getMessages() {
		
		HashMap<String, String> messages = new HashMap<String, String>();
		
		for(String key : getData().getKeys(true)) messages.put(key, getStringValue(key));
		
		return messages;
		
	}
	
}