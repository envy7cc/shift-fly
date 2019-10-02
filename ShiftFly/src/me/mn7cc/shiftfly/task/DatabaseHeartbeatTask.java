package me.mn7cc.shiftfly.task;

import me.mn7cc.shiftfly.custom.Database;

public class DatabaseHeartbeatTask implements Runnable {
	
	@Override
	public void run() {
		
		if(!Database.isClosed()) Database.heartbeat();
		
	}

}
