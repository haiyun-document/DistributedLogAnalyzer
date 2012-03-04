package com.github.drashid.module;

import java.net.InetAddress;
import redis.clients.jedis.Jedis;
import com.github.drashid.parse.LogParser;
import com.github.drashid.parse.Slf4jLogParser;
import com.github.drashid.redis.RedisConfig;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class RedisPublisherModule extends AbstractModule {

  public static final String CHANNEL_ROOT = "log." + getMachineIp() + "->";
  
  private final String channelName;
  
  private static String getMachineIp(){
    try{
      return InetAddress.getLocalHost().getHostAddress();
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  
  public RedisPublisherModule(String channelName) {
    this.channelName = CHANNEL_ROOT + channelName;
  }
  
  @Override
  protected void configure() {
    bind(LogParser.class).to(Slf4jLogParser.class);
  }
  
  @Provides @Singleton
  public Jedis client(RedisConfig config){
    //TODO Config
    Jedis jedis = new Jedis("127.0.0.1", 6379);
    jedis.connect();
    return jedis;
  }

  @Provides @RedisChannel
  public String channelName(){
    return this.channelName;
  }
  
}
