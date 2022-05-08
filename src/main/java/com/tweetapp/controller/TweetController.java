package com.tweetapp.controller;

import com.tweetapp.model.Likes;
import com.tweetapp.model.User;
import com.tweetapp.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.tweetapp.model.Tweet;

import java.util.List;


@RestController
@CrossOrigin(origins = "*")
public class TweetController {
    
    @Autowired
    private TweetService tweetService;
    
    @RequestMapping(value = "/post/tweet", produces = {"application/json"},
            method = RequestMethod.POST)
    public ResponseEntity<Tweet> postATweet(@RequestBody Tweet tweet) {
        try {
        	Tweet response = tweetService.postTweet(tweet);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(),
                    e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }
    
    
    @RequestMapping(value = "/tweets/all", produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<List<Tweet>> getAllTweet() {
        try {
        	List<Tweet> response = tweetService.getAllTweets();
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (AmazonServiceException e) {
            throw new ResponseStatusException(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(),
                    e);
        } catch (AmazonClientException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
        }
    }

    @PostMapping("/post/tweet")
    public Tweet postTweet(@RequestBody Tweet tweet) {
        return tweetService.postTweet(tweet);
    }

    @GetMapping("/tweets/all")
    public List<Tweet> getAllTweets() {
        return tweetService.getAllTweets();
    }

    @GetMapping("/myTweets/{email}")
    public List<Tweet> getMyTweets(@PathVariable String email) {
        return tweetService.getMyTweets(email);
    }

    @PostMapping("/reply/tweet/{id}")
    public List<Tweet> replyTweet(@PathVariable  String id, @RequestBody Tweet replyTweet) {
        return tweetService.replyTweet(id, replyTweet);
    }

    @PostMapping("/tweet/replies/{id}")
    public List<Tweet> replies(@PathVariable String id) {
        return tweetService.getReplies(id);
    }

    @PostMapping("/tweet/like/{id}")
    public Likes getLikes(@PathVariable String id, @RequestBody User user) {
        return tweetService.likeTweet(id, user);
    }

    @PutMapping("/update/tweet")
    public Tweet updateTweet(@RequestBody Tweet tweet) {
        return tweetService.updateTweet(tweet);
    }

    @DeleteMapping("/delete/tweet/{id}")
    public boolean deleteTweet(@PathVariable String id) {
        return tweetService.deleteTweet(id);
    }


}
