package com.example.customerclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.rsocket.Payload;
import io.rsocket.RSocketFactory;
import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.util.DefaultPayload;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.io.IOException;

@Component
public class CustomerClient {

    private final ObjectMapper objectMapper;

    public CustomerClient(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Flux<Customer> getAllCustomers() {

        return RSocketFactory
                .connect()
                .transport(TcpClientTransport.create("localhost", 8787))
                .start()
                .flatMapMany(socket ->
                        socket
                                .requestStream(DefaultPayload.create(new byte[0]))
                                .map(Payload::getDataUtf8)
                                .map(obj -> {
                                    try {
                                        return this.objectMapper.readValue(obj, Customer.class);
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                })
                                .doFinally(signal -> socket.dispose())
                );
    }

}
