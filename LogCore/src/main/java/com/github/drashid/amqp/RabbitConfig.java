package com.github.drashid.amqp;

import com.github.drashid.config.Config;


@Config("rabbitmq")
public class RabbitConfig {

  public static final String DEFAULT_ENCODING = "UTF-8";
  
  private String logExchange;
  private String logQueue;
  private String logRoute;
  
  public RabbitConfig() {
  
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
