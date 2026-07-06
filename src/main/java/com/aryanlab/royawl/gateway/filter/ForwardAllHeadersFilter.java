//package com.aryanlab.royawl.gateway.filter;
//
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//@Component
//public class ForwardAllHeadersFilter implements GlobalFilter, Ordered {
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
//                .headers(headers -> {
//                    headers.addAll(exchange.getRequest().getHeaders());
//                    headers.set("X-Forwarded-For", exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
//                    headers.set("X-Forwarded-Host", exchange.getRequest().getURI().getHost());
//                    headers.set("X-Forwarded-Proto", exchange.getRequest().getURI().getScheme());
//                })
//                .build();
//
//        return chain.filter(exchange.mutate().request(mutatedRequest).build());
//    }
//
//    @Override
//    public int getOrder() {
//        return -2;
//    }
//}