package com.github.drashid.config;

import java.io.File;
import java.io.IOException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import com.google.inject.AbstractModule;

public class ConfigModule extends AbstractModule {

  private JsonNode config;
  private Class<?>[] clsz; 
  private static ObjectMapper mapper = new ObjectMapper();
  
  public ConfigModule(JsonNode config, Class<?>...configClasses) {
    try{
      this.config = config;
      clsz = configClasses;
    }catch(Exception ex){
      throw new RuntimeException("Failed to initialize config", ex);
    }
  }
  
  public static JsonNode readResource(String resourcePath){
    try {
      return mapper.readTree(ConfigModule.class.getResourceAsStream(resourcePath));
    }
    catch (Exception e) { return null; }
  }
  
  public static JsonNode readFile(String filename){
    return readFile(new File(filename));
  }
  
  public static JsonNode readFile(File file){
    try {
      return mapper.readTree(file);
    }
    catch (Exception e) { return null; }
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
