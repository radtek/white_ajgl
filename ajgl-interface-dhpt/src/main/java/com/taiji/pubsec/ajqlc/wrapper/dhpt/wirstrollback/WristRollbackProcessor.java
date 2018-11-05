package com.taiji.pubsec.ajqlc.wrapper.dhpt.wirstrollback;

import javax.annotation.Resource;

import com.taiji.persistence.dao.Dao;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.exception.DahuaException;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.IWristbandService;

public class WristRollbackProcessor implements Processor {

	private IWristbandService wristbandService;
	private ErrorRollbackDataMap errorRollbackMap;
	@Override
	public void process(ErrorDahuaDevice edd) {
		ErrorWrist wrist = (ErrorWrist) edd;
		// TODO 回滚操作
		try {
			if ("reclaim".equals(wrist.getOperatorType())) {
				wristbandService.reclaim(wrist.getId(), wrist.getSuspectId(),
						wrist.getOperatorid());
				errorRollbackMap.remove(wrist.getId());
			} else if ("active".equals(wrist.getOperatorType())) {
				wristbandService.active(wrist.getId(), wrist.getSuspectId(),
						wrist.getOperatorid());
				errorRollbackMap.remove(wrist.getId());
			}
		} catch (DahuaException e) {
			// TODO 如果没有回滚成功，则edd.setRetried(+1)
			wrist.setRetried(wrist.getRetried() + 1);
			// logger
		}

	}

	@Override
	public boolean support(ErrorDahuaDevice edd) {
		if (edd instanceof ErrorWrist) {
			return true;
		}
		return false;
	}

	public void setWristbandService(IWristbandService wristbandService) {
		this.wristbandService = wristbandService;
	}

	public void setErrorRollbackMap(ErrorRollbackDataMap errorRollbackMap) {
		this.errorRollbackMap = errorRollbackMap;
	}

}
