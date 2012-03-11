package com.github.drashid.amqp;

import java.io.IOException;
import com.github.drashid.config.rabbit.RabbitConfig;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


@Singleton
public class RabbitPublisher {

  @Inject
  private ConnectionFactory factory;  
  @Inject 
  private BasicProperties props;
  @Inject
  private RabbitConfig config;
  
  public void sendMessage(byte[] body, Channel channel) throws IOException{
    channel.basicPublish(config.getLogExchange(), config.getLogRoute(), props, body);
  }
  
  public void sendMessage(byte[] body) throws IOException{
    Channel channel = newChannel();
    try{
      sendMessage(body, channel);
    }finally{
      closeQuietly(channel.getConnection());
      closeQuietly(channel);
    }
  }

  public Channel newChannel() throws IOException{
    Connection conn = factory.newConnection();
    Channel channel = conn.createChannel();
    
    //ensure queue to publish to
    channel.exchangeDeclare(config.getLogExchange(), "direct", true);
    channel.queueDeclare(config.getLogQueue(), true, false, false, null);
    channel.queueBind(config.getLogQueue(),config.getLogExchange(), config.getLogRoute());
    
    return channel;
  }
  
  public void closeQuietly(Channel channel) {
    try{ if(channel != null) channel.close(); }
    catch(Exception e){ }
  }

  public void closeQuietly(Connection conn) {
    try{ if(conn != null) conn.close(); }
    catch(Exception e){ }
  }
  
}
