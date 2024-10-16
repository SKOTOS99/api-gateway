package com.gateway.app.filters.factory;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import com.gateway.app.models.cookie.ConfigurationCookie;

import reactor.core.publisher.Mono;

@Component
public class CookieGatewayFilterFactory extends AbstractGatewayFilterFactory<ConfigurationCookie>{

	private final Logger log = LoggerFactory.getLogger(CookieGatewayFilterFactory.class);

	public CookieGatewayFilterFactory() {
		super(ConfigurationCookie.class);
	}

	@Override
	public GatewayFilter apply(ConfigurationCookie config) {
		return new OrderedGatewayFilter((exchange, chain) ->{
			
			log.info("ejecutando filter pre factory: {}",  config.getMessage());
			return chain.filter(exchange).then(Mono.fromRunnable(() ->{
				log.info("ejecutando filter pre factory: {}",  config.getMessage());
				
				Optional.ofNullable(config.getValue()).ifPresent(cookie ->{
					exchange.getResponse().addCookie(ResponseCookie.from(config.getName(), cookie).build());
				});

			}));
		}, 100);
	}

}
