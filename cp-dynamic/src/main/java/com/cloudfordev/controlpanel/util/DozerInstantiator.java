package com.cloudfordev.controlpanel.util;

import javax.inject.Singleton;

import org.dozer.DozerBeanMapper;

@Singleton
public class DozerInstantiator {
	public static DozerBeanMapper getInstance(){
	    return MapperHolder.instance;
	}

	private static class MapperHolder{
	    static final DozerBeanMapper instance = new DozerBeanMapper();
	}
}
