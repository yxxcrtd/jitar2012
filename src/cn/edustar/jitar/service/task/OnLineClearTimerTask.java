package cn.edustar.jitar.service.task;

import java.util.TimerTask;
import cn.edustar.jitar.service.OnLineService;

/**
 * @author Yang Xinxin
 * @version 1.0.0 Mar 31, 2009 5:30:00 PM
 */
public class OnLineClearTimerTask extends TimerTask {
	private OnLineService onlineService;

	public OnLineClearTimerTask() {
	}

	@Override
	public void run() {
		try {
			onlineService
					.removeUserOnLineOutTime(System.currentTimeMillis() - 10800000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setOnlineService(OnLineService onlineService) {
		this.onlineService = onlineService;
	}

}
