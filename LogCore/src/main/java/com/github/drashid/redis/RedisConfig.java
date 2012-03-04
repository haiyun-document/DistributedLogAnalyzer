package com.github.drashid.redis;

public class RedisConfig {

  private String host;
  private int port;

  public RedisConfig(int port, String host){
    this.port = port;
    this.host = host;
  }
  
  public RedisConfig() {
    this.port = 6379;
    this.host = "127.0.0.1";
  }
  
  public String getHost() {
    return host;
  }
  
  public int getPort() {
    return port;
  }
}
