package com.github.drashid.publisher;

import java.io.IOException;
import com.github.drashid.amqp.RabbitPublisher;
import com.google.inject.Inject;
import com.rabbitmq.client.Channel;


public class RabbitLogPublisher extends AbstractTailAggregator {

  private RabbitPublisher publisher;
  private Channel channel;
  
  @Inject
  public RabbitLogPublisher(RabbitPublisher publisher) throws IOException {
    this.publisher = publisher;
    channel = publisher.newChannel();
  }

  @Override
  protected void publishMessage(String message) {
    try{
      if(!channel.isOpen()){ //TODO don't do this!
        channel = publisher.newChannel();
      }
      if(message != null && message.trim().length() > 0){
        publisher.sendMessage(message.getBytes());
      }
    }catch(Exception e){ 
      e.printStackTrace();
    }
  }
  
}
