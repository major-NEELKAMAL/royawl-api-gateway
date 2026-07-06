package com.aryanlab.royawl.gateway.configs;

import java.util.Arrays;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class WebSecurity {
  
  private static final Logger log = LoggerFactory.getLogger(WebSecurity.class);

  @Value("${app.allowed-origins}")
  private String allowedOrigins;
  
  @Value("${app.exposed-headers}")
  private String exposedHeaders;

  @Bean
  public CorsWebFilter corsWebFilter() {
      log.info("⚙️ [CORS-SETUP] Initializing Gateway CorsWebFilter...");
      CorsConfiguration config = new CorsConfiguration();

      // Parse and register Allowed Origins using patterns for robust matching
      log.info("⚙️ [CORS-SETUP] Processing Allowed Origins: [{}]", allowedOrigins);
      Arrays.stream(allowedOrigins.split(","))
            .map(String::trim)
            .forEach(origin -> {
                config.addAllowedOriginPattern(origin);
                log.info("   ↳ Allowed Origin Registered: {}", origin);
            });
      
      // Parse and register Exposed Headers
      if (exposedHeaders != null && !exposedHeaders.isBlank()) {
          log.info("⚙️ [CORS-SETUP] Processing Exposed Headers: [{}]", exposedHeaders);
          Arrays.stream(exposedHeaders.split(","))
                .map(String::trim)
                .forEach(header -> {
                    config.addExposedHeader(header);
                    log.info("   ↳ Exposed Header Registered: {}", header);
                });
      }

      // Configure methods, headers, and credentials safety check
      config.setAllowedMethods(Arrays.asList("*"));
      config.setAllowedHeaders(Arrays.asList("*"));
      config.setAllowCredentials(true);
      
      log.info("⚙️ [CORS-SETUP] Global Strategy Applied: AllowedMethods=[*], AllowedHeaders=[*], AllowCredentials=[true]");

      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      source.registerCorsConfiguration("/**", config);

      log.info("✅ [CORS-SETUP] CorsWebFilter successfully mapped to path: /**");
      return new CorsWebFilter(source);
  }
  
  @Bean(name = "jasyptStringEncryptor")
  public StringEncryptor stringEncryptor(
      @Value("${jasypt.encryptor.password}") String masterPassword) {
    PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
    SimpleStringPBEConfig config = new SimpleStringPBEConfig();
    config.setPassword(masterPassword);
    config.setAlgorithm("PBEWITHHMACSHA512ANDAES_256");
    config.setKeyObtentionIterations("1000");
    config.setPoolSize("1");
    config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
    config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator");
    config.setStringOutputType("base64");
    encryptor.setConfig(config);
    return encryptor;
  }
}