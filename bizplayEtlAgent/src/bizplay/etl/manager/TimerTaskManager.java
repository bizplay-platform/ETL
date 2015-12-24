package bizplay.etl.manager;

import java.util.TimerTask;

public class TimerTaskManager extends TimerTask{
	
	@Override
	public void run() {
		CollectorManager cm = new CollectorManager();
		cm.run();
	}
}
