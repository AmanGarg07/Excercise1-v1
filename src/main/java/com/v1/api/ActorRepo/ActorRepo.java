package com.v1.api.ActorRepo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.v1.api.model.Actor;

public interface ActorRepo extends MongoRepository<Actor,String>{

}
