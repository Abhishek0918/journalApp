package net.dyanmo.journalApp.repository;

import net.dyanmo.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, ObjectId>{

    List<User> findByUsername(String username);


}
