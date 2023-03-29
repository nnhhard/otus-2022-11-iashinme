package ru.iashinme.homework15.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.iashinme.homework15.domain.Butterfly;
import ru.iashinme.homework15.domain.Caterpillar;

@Slf4j
@Service
public class ButterflyServiceImpl implements ButterflyService {

    @Override
    public Butterfly hutch(Caterpillar caterpillar) {
        log.info("Caterpillars = {}", caterpillar.getName());
        log.info("The transformation of a butterfly from a caterpillar = {} done", caterpillar.getName());
        return new Butterfly("New Butterfly from " + caterpillar.getName());
    }
}