package com.github.drashid.amqp;

import com.github.drashid.config.rabbit.RabbitConfig;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

public class RabbitModule extends AbstractModule {

  public static final String LOG_QUEUE_NAME = "LOG_QUEUE";

  @Override
  protected void configure() {
    bind(String.class).annotatedWith(Names.named(LOG_QUEUE_NAME)).toInstance(LOG_QUEUE_NAME);
  }
  
  @Provides @Singleton
  public ConnectionFactory factory(RabbitConfig config){
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUsername(config.getUsername());
    factory.setPassword(config.getPassword());
    factory.setVirtualHost(config.getVirtualHost());
    factory.setHost(config.getHost());
    factory.setPort(config.getPort());
    return factory;
  }
  
  @Provides
  public BasicProperties props(){
    return MessageProperties.PERSISTENT_TEXT_PLAIN;
  }
  
}
