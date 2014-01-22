package com.cloudfordev.controlpanel.data.repository;

import com.cloudfordev.controlpanel.domain.User;

public interface UserRepository extends BaseRepository<User, Long> {
	User findByEmail(String email);
}
