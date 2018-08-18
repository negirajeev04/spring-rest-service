package org.rajnegi.spring.rest.webservices.springrestservice.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.rajnegi.spring.rest.webservices.springrestservice.bean.User;
import org.springframework.stereotype.Component;

@Component
public class UserDaoService {

	private static List<User> users = new ArrayList<>();
	
	static {
		users.add(new User(1, "Rajeev", new Date()));
		users.add(new User(2, "Laxmi", new Date()));
		users.add(new User(3, "Laisha", new Date()));
	}

	private int usersCount = 3;
	
	public List<User> findAll(){
		return users;
	}
	
	public User findOne(int id) {
		for(User user: users) {
			if(user.getId() == id)
				return user;
		}
		
		return null;
	}
	
	public User save(User user) {
		if(user.getId() == null) {
			user.setId(++usersCount);
		}
		users.add(user);
		return user;
	}
	
	public User deleteById(int id) {
		Iterator<User> iterator = users.iterator();
		
		while(iterator.hasNext()) {
			User user = iterator.next();
			if(user.getId() == id) {
				iterator.remove();
				return user;
			}
		}
		
		return null;
	}
	
}
