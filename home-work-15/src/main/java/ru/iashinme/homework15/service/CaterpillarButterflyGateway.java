package ru.iashinme.homework15.service;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.iashinme.homework15.domain.Butterfly;
import ru.iashinme.homework15.domain.Caterpillar;

import java.util.Collection;

@MessagingGateway
public interface CaterpillarButterflyGateway {

    @Gateway(requestChannel = "caterpillarChannel", replyChannel = "butterflyChannel")
    Collection<Butterfly> process(Collection<Caterpillar> caterpillars);
}
