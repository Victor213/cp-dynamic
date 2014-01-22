package com.cloudfordev.controlpanel.data.repository;

import java.io.Serializable;

import org.springframework.data.repository.Repository;

interface BaseRepository<T, ID extends Serializable> extends Repository<T, ID> {
	T findOne(ID id);
	T save(T entity);
    void delete(ID id);
}
