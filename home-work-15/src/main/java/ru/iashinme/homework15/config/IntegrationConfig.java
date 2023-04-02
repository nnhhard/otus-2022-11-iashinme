package ru.iashinme.homework15.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import ru.iashinme.homework15.service.ButterflyService;

@Configuration
public class IntegrationConfig {

    @Bean
    public PublishSubscribeChannel butterflyChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate(1000).get();
    }

    @Bean
    public IntegrationFlow butterflyFlow(ButterflyService butterflyService) {
        return IntegrationFlows.from("caterpillarChannel")
                .split()
                .handle(butterflyService, "hutch")
                .aggregate()
                .channel("butterflyChannel")
                .get();
    }
}
