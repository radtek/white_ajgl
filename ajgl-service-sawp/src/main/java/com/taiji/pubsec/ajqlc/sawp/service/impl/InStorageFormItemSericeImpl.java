package com.taiji.pubsec.ajqlc.sawp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taiji.persistence.dao.Dao;
import com.taiji.persistence.service.AbstractBaseService;
import com.taiji.pubsec.ajqlc.sawp.model.InStorageFormItem;
import com.taiji.pubsec.ajqlc.sawp.service.IInStorageFormItemService;

@Service("inStorageFormItemSerice")
public class InStorageFormItemSericeImpl extends AbstractBaseService<InStorageFormItem, String>
		implements IInStorageFormItemService {

	@Autowired
	public InStorageFormItemSericeImpl(Dao<InStorageFormItem, String> dao){
		setDao(dao);
	}

	@Override
	public InStorageFormItem findItemById(String itemId) {
		return findById(itemId);
	}

}
