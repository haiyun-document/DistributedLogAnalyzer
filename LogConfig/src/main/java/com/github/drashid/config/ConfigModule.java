package com.github.drashid.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import com.google.inject.AbstractModule;

public class ConfigModule extends AbstractModule {

  private JsonNode config;
  private Class<?>[] clsz; 
  private ObjectMapper mapper;
  
  public ConfigModule(InputStream configStream, Class<?>...configClasses) {
    try{
      mapper = new ObjectMapper();
      config = mapper.readTree(configStream);
      clsz = configClasses;
    }catch(Exception ex){
      throw new RuntimeException("Failed to initialize config", ex);
    }
  }
  
  public ConfigModule(String configFile, Class<?>...configClasses) {
    try{
      mapper = new ObjectMapper();
      config = mapper.readTree(new FileInputStream(new File(configFile)));
      clsz = configClasses;
    }catch(Exception ex){
      throw new RuntimeException("Failed to initialize config", ex);
    }
  }
  
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  protected void configure() {
    for(Class cls : clsz){
      if(cls.isAnnotationPresent(Config.class)){
        try {
          bind(cls).toInstance(configObj(cls));
        }
        catch (Exception e) { }  
      }
    }
  }

  private <E> E configObj(Class<E> cls) throws JsonParseException, JsonMappingException, IOException {
    Config configAnn = cls.getAnnotation(Config.class);
    JsonNode objectNode = config.get(configAnn.value());
    return mapper.readValue(objectNode, cls);
  }
  
}
