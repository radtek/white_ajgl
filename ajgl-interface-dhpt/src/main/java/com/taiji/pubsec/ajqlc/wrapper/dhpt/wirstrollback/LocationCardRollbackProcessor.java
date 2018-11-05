package com.taiji.pubsec.ajqlc.wrapper.dhpt.wirstrollback;

import com.taiji.pubsec.ajqlc.wrapper.dhpt.exception.DahuaException;
import com.taiji.pubsec.ajqlc.wrapper.dhpt.service.ILocationCardService;

public class LocationCardRollbackProcessor implements Processor {

	private ILocationCardService locationCardService;
	private ErrorRollbackDataMap errorRollbackMap;

	@Override
	public void process(ErrorDahuaDevice edd) {
		ErrorLocationCard errorLocationCard = (ErrorLocationCard) edd;
		// TODO 回滚操作
		try {
			if ("reclaim".equals(errorLocationCard.getOperatorType())) {
				locationCardService.reclaim(errorLocationCard.getId(),
						errorLocationCard.getPolicenumber(),
						errorLocationCard.getOperatorid());
				errorRollbackMap.remove(errorLocationCard.getId());
			} else if ("active".equals(errorLocationCard.getOperatorType())) {
				locationCardService.active(errorLocationCard.getId(),
						errorLocationCard.getPolicenumber(),
						errorLocationCard.getOperatorid());
				errorRollbackMap.remove(errorLocationCard.getId());
			}

		} catch (DahuaException e) {
			// TODO 如果没有回滚成功，则edd.setRetried(+1)
			errorLocationCard.setRetried(errorLocationCard.getRetried() + 1);
			//打印日志
		}
	}

	@Override
	public boolean support(ErrorDahuaDevice edd) {
		if (edd instanceof ErrorLocationCard) {
			return true;
		}
		return false;
	}

	public void setLocationCardService(ILocationCardService locationCardService) {
		this.locationCardService = locationCardService;
	}

	public void setErrorRollbackMap(ErrorRollbackDataMap errorRollbackMap) {
		this.errorRollbackMap = errorRollbackMap;
	}

}
