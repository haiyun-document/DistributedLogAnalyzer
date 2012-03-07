package com.github.drashid.amqp;

import java.io.IOException;
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
  
  public void sendMessage(byte[] body, Channel channel) throws IOException{
    channel.basicPublish(RabbitConstants.LOG_EXCHANGE, RabbitConstants.LOG_ROUTE, props, body);
  }
  
  public void sendMessage(byte[] body) throws IOException{
    Connection conn = factory.newConnection();
    Channel channel = null;
    try{
      channel = conn.createChannel();
      sendMessage(body, channel);
    }finally{
      closeQuietly(conn);
      closeQuietly(channel);
    }
  }

  public Channel newChannel() throws IOException{ //TODO leaking connections?
    Connection conn = factory.newConnection();
    Channel channel = conn.createChannel();
    
    //ensure queue to publish to
    channel.exchangeDeclare(RabbitConstants.LOG_EXCHANGE, "direct", true);
    channel.queueDeclare(RabbitConstants.LOG_QUEUE, true, false, false, null);
    channel.queueBind(RabbitConstants.LOG_QUEUE, RabbitConstants.LOG_EXCHANGE, RabbitConstants.LOG_ROUTE);
    
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
