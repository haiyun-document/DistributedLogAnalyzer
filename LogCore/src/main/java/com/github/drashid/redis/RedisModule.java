package com.github.drashid.redis;

import redis.clients.jedis.JedisPool;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class RedisModule extends AbstractModule{

  public static final String LOG_CHANNEL_ROOT = "log.";
  
  private RedisConfig config;

  public RedisModule(RedisConfig config) {
    this.config = config;
  }
  
  protected void configure() {
    bind(RedisConfig.class).toInstance(config);
  }
  
  @Provides @Singleton
  public JedisPool pool(RedisConfig config){
    //TODO pool config
    JedisPool pool = new JedisPool(config.getHost(), config.getPort());
    return pool;
  }
  
}
