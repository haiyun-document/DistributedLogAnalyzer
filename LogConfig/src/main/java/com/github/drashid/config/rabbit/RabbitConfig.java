package com.github.drashid.config.rabbit;

import com.github.drashid.config.Config;

@Config("rabbitmq")
public class RabbitConfig {

  public static final String DEFAULT_ENCODING = "UTF-8";
  
  private String logExchange;
  private String logQueue;
  private String logRoute;
  
  private String username;
  private String password;
  private String virtualHost;
  private String host;
  private int port;
  
  public RabbitConfig() {
  
  }
  
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
  
  public String getPassword() {
    return password;
  }
  
  public void setPassword(String password) {
    this.password = password;
  }
  
  public String getVirtualHost() {
    return virtualHost;
  }
  
  public void setVirtualHost(String virtualHost) {
    this.virtualHost = virtualHost;
  }

  public String getHost() {
    return host;
  }
  
  public void setHost(String host) {
    this.host = host;
  }
  
  public int getPort() {
    return port;
  }
  
  public void setPort(int port) {
    this.port = port;
  }
  
  public String getLogExchange() {
    return logExchange;
  }

  public void setLogExchange(String logExchange) {
    this.logExchange = logExchange;
  }
  
  public String getLogQueue() {
    return logQueue;
  }
  
  public void setLogQueue(String logQueue) {
    this.logQueue = logQueue;
  }
  
  public String getLogRoute() {
    return logRoute;
  }

  public void setLogRoute(String logRoute) {
    this.logRoute = logRoute;
  }
  
  
}
