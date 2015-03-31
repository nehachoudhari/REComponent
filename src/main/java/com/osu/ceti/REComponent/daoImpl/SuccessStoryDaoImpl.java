package com.osu.ceti.REComponent.daoImpl;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.osu.ceti.REComponent.dao.SuccessStoryDao;
import com.osu.ceti.REComponent.model.SuccessStory;

@Repository
public class SuccessStoryDaoImpl implements SuccessStoryDao {

	@Autowired
	private SessionFactory session;
	
	@Override
	public void add(SuccessStory s) {
		session.getCurrentSession().save(s);

	}

	@Override
	public void edit(SuccessStory s) {
		session.getCurrentSession().update(s);

	}

	@Override
	public void delete(int storyId) {
		session.getCurrentSession().delete(getSuccesStory(storyId));

	}

	@Override
	public SuccessStory getSuccesStory(int storyId) {
		return (SuccessStory)session.getCurrentSession().get(SuccessStory.class, storyId);
	}

	@Override
	public List<SuccessStory> getAllSuccessStories() {
		List<SuccessStory> list = (List<SuccessStory>) session.getCurrentSession().createQuery("from SuccessStories").list();
		return list;
	}
	

}
