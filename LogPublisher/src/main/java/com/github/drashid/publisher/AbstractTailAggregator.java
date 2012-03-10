package com.github.drashid.publisher;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.commons.io.input.TailerListenerAdapter;
import com.github.drashid.parse.LogParser;
import com.google.common.util.concurrent.AbstractScheduledService;
import com.google.inject.Inject;

public abstract class AbstractTailAggregator extends TailerListenerAdapter{

  @Inject
  protected LogParser logParser;
  
  private final MessageFlushService flusher;
  
  private final AtomicReference<StringBuilder> sbRef = new AtomicReference<StringBuilder>(new StringBuilder()); 
  
  private long lastHandledTime = System.currentTimeMillis();
  private static final long FLUSH_WINDOW_IN_MS = TimeUnit.SECONDS.toMillis(1);
  
  @Inject
  public AbstractTailAggregator() {
    flusher = new MessageFlushService(this);
    flusher.start();
  }
  
  protected abstract void publishMessage(String message);
  
  private void _publishMessage(String message){
    if(message != null && message.trim().length() > 0){
      publishMessage(message);
    }
  }
  
  @Override
  public void fileRotated() {
    _publishMessage(getAndReset(sbRef));
  }
  
  private String getAndReset(AtomicReference<StringBuilder> sbRef){
    return sbRef.getAndSet(new StringBuilder()).toString();
  }
  
  @Override
  public void handle(String line) {
    lastHandledTime = System.currentTimeMillis();
    if(logParser.isStartLine(line)){
      _publishMessage(getAndReset(sbRef));
    }
    synchronized (sbRef) {
      sbRef.get().append(line).append("\n");
    }    
  }

  public void flushMessages(){
    if( System.currentTimeMillis() - lastHandledTime > FLUSH_WINDOW_IN_MS && sbRef.get().length() > 0){
      synchronized (sbRef) { //contention is unlikely here given we have to have waited past the window
        _publishMessage(getAndReset(sbRef));
      }
    }
  }
  
  private static class MessageFlushService extends AbstractScheduledService {

    private AbstractTailAggregator logger;
    
    public MessageFlushService(AbstractTailAggregator logger) {
      this.logger = logger;
    }
    
    @Override
    protected void runOneIteration() throws Exception {
      logger.flushMessages();
    }

    @Override
    protected Scheduler scheduler() {
      return Scheduler.newFixedRateSchedule(0, 1, TimeUnit.SECONDS);
    }

    @Override
    protected void shutDown() throws Exception { }

    @Override
    protected void startUp() throws Exception { }

  }
  
}
