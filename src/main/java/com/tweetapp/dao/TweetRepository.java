package com.tweetapp.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.tweetapp.model.Tweet;

import java.util.Optional;


@Repository
public class TweetRepository {

    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    public TweetRepository(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public Tweet getTweet(String tweetId) {
        return dynamoDBMapper.load(Tweet.class, tweetId);
    }

    public Tweet save(Tweet tweet) {
        dynamoDBMapper.save(tweet);
        return tweet;
    }

	public PaginatedScanList<Tweet> findAll() {
		return dynamoDBMapper.scan(Tweet.class, new DynamoDBScanExpression());
	}

    public Optional<Tweet> findById(String id) {
        return Optional.of(dynamoDBMapper.load(Tweet.class, id));
    }

    public void deleteTweet(Tweet tweet) {
        dynamoDBMapper.delete(tweet);
    }
}
