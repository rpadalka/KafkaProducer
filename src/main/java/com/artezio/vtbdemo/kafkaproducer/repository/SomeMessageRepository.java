package com.artezio.vtbdemo.kafkaproducer.repository;

import com.artezio.vtbdemo.kafkaproducer.model.SomeMessageEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SomeMessageRepository extends CrudRepository<SomeMessageEntity, Long> {
}