package com.osprey.screen.repository;

public interface IOspreyJSONObjectRepository {
	
	public String find(String key);
	public void persist(String key, String value);

}
