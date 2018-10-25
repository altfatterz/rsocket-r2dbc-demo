package com.example.customerclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouteConfiguration {

    @Autowired
    private CustomerHandler customerHandler;

    @Bean
    WebClient webClient(WebClient.Builder builder) {
        return builder.build();
    }

    @Bean
    RouterFunction<ServerResponse> routes(CustomerClient client) {
       return route(GET("/customers").and(accept(MediaType.APPLICATION_JSON)), customerHandler::getCustomers);
    }

}
