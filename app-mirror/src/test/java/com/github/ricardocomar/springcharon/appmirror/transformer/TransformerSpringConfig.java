package com.github.ricardocomar.springcharon.appmirror.transformer;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.github.ricardocomar.springcharon.appmirror.mapper.MapperSpringConfig;

@Configuration
@ComponentScan(basePackageClasses = { TransformerSpringConfig.class, MapperSpringConfig.class })
public class TransformerSpringConfig {

}
