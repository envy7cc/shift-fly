package net.minefall.shiftfly.task;

import net.minefall.shiftfly.custom.Database;

public class DatabaseHeartbeatTask implements Runnable {
	
	@Override
	public void run() {
		
		if(!Database.isClosed()) Database.heartbeat();
		
	}

}
