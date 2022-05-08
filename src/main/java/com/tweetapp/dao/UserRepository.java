package com.tweetapp.dao;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.tweetapp.model.Tweet;
import com.tweetapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UserRepository {

    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    public UserRepository(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public User save(User user) {
        dynamoDBMapper.save(user);
        return user;
    }

    public PaginatedScanList<User> findAll() {
        return dynamoDBMapper.scan(User.class, new DynamoDBScanExpression());
    }

    public Optional<User> findByEmail(String email) {
        Optional<User> optionalUser = dynamoDBMapper.scan(User.class, new DynamoDBScanExpression())
                .stream().filter(user -> email != "" && email != null && email.equals(user.getEmail()))
                .findFirst();

        return optionalUser;
    }
}
