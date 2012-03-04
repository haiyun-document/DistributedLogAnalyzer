package com.github.drashid.module;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import com.google.inject.BindingAnnotation;


@BindingAnnotation
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisChannel { }
