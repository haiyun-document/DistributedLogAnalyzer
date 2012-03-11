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
    this.channel = publisher.newChannel();
  }

  @Override
  protected void publishMessage(String message) {
    try{
      publisher.sendMessage(message.getBytes(), channel);
    }catch(Exception e){ 
      e.printStackTrace();
    }
  }
  
}
