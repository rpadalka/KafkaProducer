package com.artezio.vtbdemo.kafkaproducer.service;

import com.artezio.vtbdemo.kafkaproducer.model.SomeMessageEntity;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.stream.StreamListener;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class SomeMessageService implements StreamListener<String, MapRecord<String, String, String>> {

    private final CrudRepository<SomeMessageEntity, Long> messageRepository;

    private SomeMessageEntity mapValues(String name, String message) {
        SomeMessageEntity someMessageEntity = new SomeMessageEntity();
        someMessageEntity.setName(name);
        someMessageEntity.setMessage(message);

        return someMessageEntity;
    }

    @Override
    public void onMessage(MapRecord<String, String, String> record) {
        Map<String, String> value = record.getValue();
        messageRepository.save(mapValues(value.get("name"), value.get("message")));
    }
}
