package com.example.customerclient;

import org.springframework.http.MediaType;
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
        Flux<CustomerName> customers = customerClient.getAllCustomers()
                .map(customer -> new CustomerName(customer.getFirstName() + " " + customer.getLastName()));

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromPublisher(customers, CustomerName.class));
    }
}
