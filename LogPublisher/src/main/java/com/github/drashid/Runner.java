package com.github.drashid;

import java.io.File;
import java.util.concurrent.Executors;
import org.apache.commons.io.input.Tailer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.drashid.module.RabbitPublisherModule;
import com.github.drashid.publisher.RabbitLogPublisher;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class Runner {

  public static final Logger LOG = LoggerFactory.getLogger(Runner.class);
  
  public static void main(String[] args) throws InterruptedException {
    if(args.length == 1){
      File tailFile = new File(args[0]);
      LOG.info("Listening on file {}...", tailFile.getAbsolutePath());
      
      Injector inject = Guice.createInjector(new RabbitPublisherModule()); 
      RabbitLogPublisher handler = inject.getInstance(RabbitLogPublisher.class);
      
      Tailer tailer = new Tailer(tailFile, handler, 10, true);
      Executors.newSingleThreadExecutor().execute(tailer);
    }else{
      LOG.info("Usage: ./program [filename]");
    }
  }
  
}
