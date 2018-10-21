package com.example.customerservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.rsocket.AbstractRSocket;
import io.rsocket.Payload;
import io.rsocket.util.DefaultPayload;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class CustomerRSocket extends AbstractRSocket {

    private final CustomerRepository customerRepository;
    private final ObjectMapper objectMapper;

    public CustomerRSocket(CustomerRepository customerRepository, ObjectMapper objectMapper) {
        this.customerRepository = customerRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public Flux<Payload> requestStream(Payload payload) {
        return customerRepository
                        .findAll()
                        .map(x -> {
                            try {
                                return objectMapper.writeValueAsString(x);
                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .map(DefaultPayload::create);
    }
}
