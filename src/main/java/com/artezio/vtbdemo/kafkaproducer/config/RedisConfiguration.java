package com.artezio.vtbdemo.kafkaproducer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.data.redis.stream.StreamMessageListenerContainer;
import org.springframework.data.redis.stream.Subscription;

import java.time.Duration;

@Configuration
public class RedisConfiguration {

    @Value("${app.redis.stream.name}")
    private String streamName;
    private final StreamListener<String, MapRecord<String, String, String>> streamListener;

    public RedisConfiguration(StreamListener<String, MapRecord<String, String, String>> streamListener) {
        this.streamListener = streamListener;
    }

    @Bean
    public Subscription subscription(RedisConnectionFactory redisConnectionFactory) {
        StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, MapRecord<String, String, String>> options = StreamMessageListenerContainer.StreamMessageListenerContainerOptions.builder().pollTimeout(Duration.ofSeconds(1)).build();
        StreamMessageListenerContainer<String, MapRecord<String, String, String>> listenerContainer = StreamMessageListenerContainer.create(redisConnectionFactory, options);
        Subscription subscription = listenerContainer.receive(StreamOffset.create(streamName, ReadOffset.lastConsumed()), streamListener);
        listenerContainer.start();

        return subscription;
    }
}
