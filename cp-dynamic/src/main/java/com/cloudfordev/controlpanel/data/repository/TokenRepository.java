package com.cloudfordev.controlpanel.data.repository;

import com.cloudfordev.controlpanel.domain.Token;

public interface TokenRepository extends BaseRepository<Token, Long> {
	Token findByToken(String token);
}
