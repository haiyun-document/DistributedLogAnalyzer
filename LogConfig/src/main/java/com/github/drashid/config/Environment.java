package com.github.drashid.config;

public enum Environment {

  DEV, STAGING, PRODUCTION;

  public boolean isDevelopment(){
    return this == Environment.DEV;
  }
  
  public boolean isStaging(){
    return this == Environment.STAGING;
  }
  
  public boolean isProduction(){
    return this == Environment.PRODUCTION;
  }
  
}
