package com.gateway.app.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class GlobalFilters implements GlobalFilter{
	
	private final Logger log = LoggerFactory.getLogger(GlobalFilters.class);

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		
		log.info("ejecutando el filtro antes del request");
		return chain.filter(exchange).then(Mono.fromRunnable(()-> {
			log.info("ejecutando filtro despues del request");
			
		}));
	}

}
