package ru.iashinme.homework15.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.iashinme.homework15.domain.Butterfly;
import ru.iashinme.homework15.domain.Caterpillar;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CaterpillarServiceImpl implements CaterpillarService {

    private final CaterpillarButterflyGateway butterflyEggGateway;

    @Override
    public void startGenerateCaterpillarLoop() {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        for (int i = 0; i < 10; i++) {
            delay();
            int num = i + 1;
            pool.execute(() -> {
                Collection<Caterpillar> caterpillars = generateCaterpillars(num);

                log.info("{}, Caterpillar: {} total = {}",
                        num,
                        caterpillars.stream()
                                .map(Caterpillar::getName)
                                .collect(Collectors.joining(" ,")),
                        caterpillars.size());

                Collection<Butterfly> butterflies = butterflyEggGateway.process(caterpillars);

                log.info("{}, Total tracks: {} total butterflies = {} Butterfly = {}",
                        num,
                        caterpillars.size(),
                        butterflies.size(),
                        butterflies
                                .stream()
                                .map(Butterfly::getName)
                                .collect(Collectors.joining(" ,")));
            });
        }
    }

    private static Collection<Caterpillar> generateCaterpillars(int size) {
        List<Caterpillar> caterpillars = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            caterpillars.add(new Caterpillar(String.format("Caterpillar %d", i + 1)));
        }
        return caterpillars;
    }

    private void delay() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}