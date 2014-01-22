package com.cloudfordev.controlpanel.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.cloudfordev.controlpanel.app.exception.ResourceNotFoundException;
import com.cloudfordev.controlpanel.data.mapper.UserMapper;
import com.cloudfordev.controlpanel.data.repository.UserRepository;
import com.cloudfordev.controlpanel.data.transfer.UserResource;
import com.cloudfordev.controlpanel.domain.User;

@Service
public class ProfileService {
	
	@Inject
	private UserRepository users;
	
	public UserResource get(long id) {
    	Long longId = new Long(id);
        User user = users.findOne(longId);
        
        if (user == null) {
        	throw new ResourceNotFoundException();
        }

        UserMapper assembler = new UserMapper();
        UserResource resource = assembler.toResource(user);
        
        return resource;
	}
	
	public void update(UserResource resource) {
		//TODO This method is untested!
		UserMapper assembler = new UserMapper();
        User user = assembler.toEntity(resource);
        users.save(user);
	}
}
