package com.github.drashid.module;

import com.github.drashid.parse.LogParser;
import com.github.drashid.parse.Slf4jLogParser;
import com.google.inject.AbstractModule;



public class RabbitPublisherModule extends AbstractModule{

  @Override
  protected void configure() {
    bind(LogParser.class).to(Slf4jLogParser.class); 
  }
  
}
