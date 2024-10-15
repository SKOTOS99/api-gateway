package com.gateway.app.filters;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class GlobalFilters implements GlobalFilter, Ordered{
	
	private final Logger log = LoggerFactory.getLogger(GlobalFilters.class);

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		
		log.info("ejecutando el filtro antes del request");
		//agrega un header al request que se envia al gateway
		exchange.getRequest().mutate().headers( h -> h.add("token", "1234")).build();
		
		return chain.filter(exchange).then(Mono.fromRunnable(()-> {
			log.info("ejecutando filtro despues del request");
			log.info("token: {}", exchange.getRequest().getHeaders().get("token"));
			
			//agrega una cookie al response
			exchange.getResponse().getCookies()
			.add("color", ResponseCookie.from("color", "red").build());
			
			//agregamos el token a la cabecera del response
			Optional.ofNullable(exchange.getRequest().getHeaders().getFirst("token"))
			.ifPresent(tok ->{
				log.info(tok);
				exchange.getResponse().getHeaders().add("token", tok);
			});
			
			exchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN);
		}));
	}

	@Override
	public int getOrder() {
		return 100;
	}

}
