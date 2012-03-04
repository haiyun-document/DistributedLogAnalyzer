package com.github.drashid.redis;

import redis.clients.jedis.JedisPool;
import com.google.inject.Inject;

public class RedisPoolManager {

  @Inject
  public static JedisPool pool;
  
  public static JedisPool getInstance(){
    return pool;
  }
  
}
