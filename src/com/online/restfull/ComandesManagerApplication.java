package com.online.restfull;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class ComandesManagerApplication extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(ComandesManager.class);
		return classes;
	}

}
