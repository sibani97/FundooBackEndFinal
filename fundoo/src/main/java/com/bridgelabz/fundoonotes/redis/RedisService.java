package com.bridgelabz.fundoonotes.redis;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
@Service("redisService")
public class RedisService<T> {
	
	private HashOperations<String,Object,T> hashOperation;
	
	@SuppressWarnings("unchecked")
	@Autowired
	public RedisService(RedisTemplate<String,T> redisTemplate)
	{
//		this.hashOperation=(HashOperations<String, Object, T>);	
		this.hashOperation = redisTemplate.opsForHash();
	}
	
	public void putMap(String redisKey, Object key, T data) {
		System.out.println("rk"+redisKey+"key"+key+"data"+data);
		hashOperation.put(redisKey, key, data);
	}

	public T getMapAsSingleEntry(String redisKey, Object key) {
		return hashOperation.get(redisKey, key);
	}

	public Map<Object, T> getMapAsAll(String redisKey) {
		return hashOperation.entries(redisKey);
	}

	public void deleteMap(String redisKey, Object key) {
		hashOperation.delete(redisKey, key);
	}
	

}
