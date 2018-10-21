package com.example.customerclient;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CustomerHandler {

    private final CustomerClient customerClient;

    public CustomerHandler(CustomerClient customerClient) {
        this.customerClient = customerClient;
    }

    public Mono<ServerResponse> getCustomers(ServerRequest request) {
        Flux<String> customers = customerClient.getAllCustomers()
                .map(customer -> customer.getFirstName() + " " + customer.getLastName());

        return ServerResponse.ok().body(BodyInserters.fromPublisher(customers, String.class));
    }
}
