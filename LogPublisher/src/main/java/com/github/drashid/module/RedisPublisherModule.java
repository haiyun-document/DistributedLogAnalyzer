package com.github.drashid.module;

import java.net.InetAddress;
import com.github.drashid.parse.LogParser;
import com.github.drashid.parse.Slf4jLogParser;
import com.github.drashid.redis.RedisChannel;
import com.github.drashid.redis.RedisConfig;
import com.github.drashid.redis.RedisModule;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class RedisPublisherModule extends AbstractModule {
  
  private final String channelName;
  
  private static String getMachineIp(){
    try{
      return InetAddress.getLocalHost().getHostAddress();
    }catch(Exception e){
      throw new RuntimeException(e);
    }
  }
  
  public RedisPublisherModule(String channelName) {
    this.channelName = RedisModule.LOG_CHANNEL_ROOT + getMachineIp() + "->" + channelName;
  }
  
  @Override
  protected void configure() {
    install(new RedisModule(new RedisConfig()));
    bind(LogParser.class).to(Slf4jLogParser.class);
  }

  @Provides @RedisChannel
  public String channelName(){
    return this.channelName;
  }
  
}
