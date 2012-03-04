package com.github.drashid.publisher;

import redis.clients.jedis.Jedis;
import com.github.drashid.module.RedisChannel;
import com.google.inject.Inject;

public class RedisLogPublisher extends AbstractTailAggregator {
  
  @Inject
  protected Jedis redisClient;

  @Inject @RedisChannel
  protected String channel;

  @Override
  protected void publishMessage(String message){
    if(message == null || (message = message.trim()).length() == 0){
      return;
    }
    redisClient.publish(channel, message);
  }
  
}
