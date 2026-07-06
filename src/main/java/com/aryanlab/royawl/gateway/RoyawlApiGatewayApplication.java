package com.aryanlab.royawl.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.PropertySource;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;


@SpringBootApplication
@EnableDiscoveryClient
@EnableEncryptableProperties
@PropertySource({"classpath:application-${spring.profiles.active}.properties"})
public class RoyawlApiGatewayApplication {

  public static void main(String[] args) {
    SpringApplication.run(RoyawlApiGatewayApplication.class, args);
  }

}
