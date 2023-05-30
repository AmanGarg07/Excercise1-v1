package com.api.twitterversion1.MongoDb.UserRepo;

import com.api.twitterversion1.MongoDb.User.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<User,String> {
}
