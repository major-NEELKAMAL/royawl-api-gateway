package com.aryanlab.royawl.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class RequestLoggingFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();

        log.info("=== Gateway Received Request ===");
        log.info("Method: {} | URL: {}", request.getMethod(), request.getURI());
        log.info("Origin: {}", headers.getFirst("Origin"));
        log.info("Cookies: {}", request.getCookies());
        log.info("All Headers: {}", headers);

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -10; // Run very early
    }
}