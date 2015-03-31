package com.osu.ceti.REComponent.daoImpl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.List;

import com.osu.ceti.REComponent.dao.PersuasionMessageDao;
import com.osu.ceti.REComponent.model.PersuasionMessage;

@Repository
public class PersuasionMessageDaoImpl implements PersuasionMessageDao {
	@Autowired
	private SessionFactory session;
	
	@Override
	public void add(PersuasionMessage msg) {
		session.getCurrentSession().save(msg);

	}

	@Override
	public void edit(PersuasionMessage msg) {
		session.getCurrentSession().update(msg);

	}

	@Override
	public void delete(int msgId) {
		session.getCurrentSession().delete(getPersuasionMessage(msgId));

	}

	@Override
	public PersuasionMessage getPersuasionMessage(int msgId) {
		return (PersuasionMessage)session.getCurrentSession().get(PersuasionMessage.class, msgId);
	}

	@Override
	public List<PersuasionMessage> getAllPersuasionMessages() {
		List<PersuasionMessage> list = (List<PersuasionMessage>) session.getCurrentSession().createQuery("from PersuasionMessages").list();
		return list;
	}

}
