package com.github.drashid.spout;

import java.util.Map;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import com.github.drashid.amqp.RabbitPublisher;
import com.github.drashid.config.rabbit.RabbitConfig;
import com.google.inject.Inject;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.GetResponse;


public class RabbitSpout extends AbstractInjectedSpout {

  private static final long serialVersionUID = 8011961021316717583L;

  @Inject
  private RabbitPublisher publisher;
  @Inject
  private RabbitConfig config;
  
  private Channel channel;
  
  public void nextTuple() {
    try{
      boolean autoAck = false;
      GetResponse response = channel.basicGet(config.getLogQueue(), autoAck);
      if (response == null) {
          // No message retrieved.
      } else {
          byte[] body = response.getBody();
          String message = new String(body, "UTF-8");
          System.out.println("GOT: " + message); 
          
          channel.basicAck(response.getEnvelope().getDeliveryTag(), false);
      }
    }catch(Exception e){
      
    }
  }

  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    declarer.declare(new Fields("message"));
  }

  @Override
  protected void _open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
    try{
      channel = publisher.newChannel();
    }catch(Exception e){
      e.printStackTrace();
    }
  }

}
