package com.gateway.app.filters.factory;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import com.gateway.app.models.cookie.ConfigurationCookie;

import reactor.core.publisher.Mono;

@Component
public class CookieGatewayFilterFactory extends AbstractGatewayFilterFactory<ConfigurationCookie>{

	
	public CookieGatewayFilterFactory() {
		super(ConfigurationCookie.class);
	}

	@Override
	public GatewayFilter apply(ConfigurationCookie config) {
		return (exchange, chain) ->{
			
			
			return chain.filter(exchange).then(Mono.fromRunnable(() ->{
				
				
			}));
		};
	}

}
