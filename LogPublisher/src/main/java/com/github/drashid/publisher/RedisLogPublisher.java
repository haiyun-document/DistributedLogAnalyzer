package com.github.drashid.publisher;

import org.apache.commons.io.input.Tailer;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import com.github.drashid.redis.RedisChannel;
import com.google.inject.Inject;

public class RedisLogPublisher extends AbstractTailAggregator {
  
  protected JedisPool pool;  
  protected Jedis redisClient;
  protected String channel;

  @Inject
  public RedisLogPublisher(JedisPool pool, @RedisChannel String channel) {
    this.pool = pool;
    this.redisClient = pool.getResource();
    this.channel = channel;
  }
  
  @Override
  public void init(Tailer tailer) {
    super.init(tailer);
    
    redisClient.connect();
  }
  
  @Override
  protected void _publishMessage(String message){
    redisClient.publish(channel, message);
  }
  
}
