package com.github.drashid.inject;

import com.google.inject.Inject;
import com.google.inject.Injector;

public class InjectorManager {

  @Inject 
  private static Injector injector;
  
  public static Injector getInjector(){
    return injector;
  }
  
  public static <E> E get(Class<E> cls){
    return injector.getInstance(cls);
  }
  
}
