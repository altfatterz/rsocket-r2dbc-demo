package com.example.customerservice;

import io.rsocket.RSocketFactory;
import io.rsocket.SocketAcceptor;
import io.rsocket.transport.netty.server.TcpServerTransport;
import io.rsocket.transport.netty.server.WebsocketServerTransport;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class CustomerService implements ApplicationListener<ApplicationReadyEvent> {

    private final CustomerRSocket customerRSocket;

    public CustomerService(CustomerRSocket customerRSocket) {
        this.customerRSocket = customerRSocket;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        SocketAcceptor sa = (connectionSetupPayload, rSocket) -> Mono.just(customerRSocket);

        RSocketFactory
                .receive()
                .acceptor(sa)
                //.transport(WebsocketServerTransport.create("localhost", 8787))
                .transport(TcpServerTransport.create("localhost", 8787))
                .start()
                .onTerminateDetach()
                .subscribe();
    }

}
