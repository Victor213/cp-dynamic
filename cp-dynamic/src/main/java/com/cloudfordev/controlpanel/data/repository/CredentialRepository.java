package com.cloudfordev.controlpanel.data.repository;

import com.cloudfordev.controlpanel.domain.Credential;

public interface CredentialRepository extends BaseRepository<Credential, Long> {
	Credential findByUserId(Long userId);
}