package com.github.drashid.config.redis;

import com.github.drashid.config.Config;

@Config("redis")
public class RedisConfig {

  private String host;
  private int port;
  
  public RedisConfig() { }
  
  public void setHost(String host) {
    this.host = host;
  }
  
  public void setPort(int port) {
    this.port = port;
  }
  
  public String getHost() {
    return host;
  }
  
  public int getPort() {
    return port;
  }
  
}
