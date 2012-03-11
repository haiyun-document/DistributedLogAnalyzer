package com.github.drashid.redis;

import redis.clients.jedis.JedisPool;
import com.github.drashid.config.ConfigModule;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class RedisModule extends AbstractModule{

  public static final String LOG_CHANNEL_ROOT = "log.";
  
  public RedisModule() {
    install(new ConfigModule("config.json", RedisConfig.class));
  }
  
  protected void configure() { }
  
  @Provides @Singleton
  public JedisPool pool(RedisConfig config){
    JedisPool pool = new JedisPool(config.getHost(), config.getPort());
    return pool;
  }
  
}
