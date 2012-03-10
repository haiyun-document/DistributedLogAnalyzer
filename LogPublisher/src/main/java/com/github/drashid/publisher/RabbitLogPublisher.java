package com.github.drashid.publisher;

import java.io.IOException;
import com.github.drashid.amqp.RabbitPublisher;
import com.google.inject.Inject;


public class RabbitLogPublisher extends AbstractTailAggregator {

  private RabbitPublisher publisher;
  
  @Inject
  public RabbitLogPublisher(RabbitPublisher publisher) throws IOException {
    this.publisher = publisher;
  }

  @Override
  protected void _publishMessage(String message) {
    try{
      publisher.sendMessage(message.getBytes());
    }catch(Exception e){ 
      e.printStackTrace();
    }
  }
  
}
