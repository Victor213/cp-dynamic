package com.cloudfordev.controlpanel.data.mapper;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.dozer.DozerBeanMapper;

import com.cloudfordev.controlpanel.app.web.UserController;
import com.cloudfordev.controlpanel.data.transfer.UserResource;
import com.cloudfordev.controlpanel.domain.User;
import com.cloudfordev.controlpanel.util.DozerInstantiator;

public class UserMapper {
	
	private DozerBeanMapper mapper;
	
	public UserMapper() {
		mapper = DozerInstantiator.getInstance();
	}

    public UserResource toResource(User user) {   	
        UserResource resource = mapper.map(user, UserResource.class);
        resource.add(linkTo(methodOn(UserController.class).get(user.getId())).withSelfRel());
        return resource;
    }

    public User toEntity(UserResource resource) {
        User user = mapper.map(resource, User.class);
        return user;
    }
}
