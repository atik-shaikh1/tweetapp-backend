package com.tweetapp.service;

import com.tweetapp.dao.TweetRepository;
import com.tweetapp.model.Likes;
import com.tweetapp.model.Tweet;
import com.tweetapp.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TweetService {

    public static final String LOG_TWEET = "tweet {}";
    @Autowired
    private TweetRepository tweetRepository;

    public Tweet postTweet(Tweet tweet) {
        tweet.setPostTime(Instant.now().toString());
//        tweet.setPostTime(LocalDateTime.now().toString());
        log.info(LOG_TWEET, tweet.toString());
        return tweetRepository.save(tweet);
    }

    public List<Tweet> getAllTweets() {
        return tweetRepository.findAll();
    }

    public List<Tweet> getMyTweets(String email) {
        log.info("email: {}", email);
        if (email == null) return null;

        return tweetRepository.findAll().stream()
                .filter(tweet ->  tweet.getUser() != null && email.equals(tweet.getUser().getEmail()))
                .collect(Collectors.toList());
    }

    public List<Tweet> replyTweet(String id, Tweet replyTweet) {
        replyTweet.setPostTime(Instant.now().toString());
//        replyTweet.setPostTime(LocalDateTime.now().toString());
        log.info("reply tweet {}", replyTweet);
        Optional<Tweet> tweetOptional = tweetRepository.findById(id);

        if (tweetOptional.isPresent()) {
            Tweet tweet = tweetOptional.get();
            List<Tweet> replies = tweet.getReplies();

            if (replies == null) replies = new ArrayList<>();

            replies.add(replyTweet);
            tweet.setReplies(replies);
            log.info(LOG_TWEET, tweet);
            log.info("replies {}", replies);

            tweetRepository.save(tweet);
            return replies;
        }

        return null;
    }

    public List<Tweet> getReplies(String id) {

        Optional<Tweet> tweetOptional = tweetRepository.findById(id);

        if (tweetOptional.isPresent()) {
            Tweet tweet = tweetOptional.get();
            List<Tweet> replies = tweet.getReplies();

            log.info(LOG_TWEET, tweet);
            log.info("replies {}", replies);

            tweetRepository.save(tweet);
            return replies;
        }

        return null;
    }

    public Likes likeTweet(String id, User user) {
        Optional<Tweet> tweetOptional = tweetRepository.findById(id);

        log.info("id {}", id);
        log.info("user {}", user);

        if (tweetOptional.isPresent()) {

            Tweet tweet = tweetOptional.get();
            Likes likes = tweet.getLikes();

            if (likes == null) likes = new Likes();

            List<User> users = likes.getUsers();

            if (users == null) users = new ArrayList<>();

            boolean userLikedTweetAlready = users.stream()
                    .anyMatch(dbUser -> dbUser.getEmail().equalsIgnoreCase(user.getEmail()));

            if (!userLikedTweetAlready) {
                users.add(user);
                likes.setCount(likes.getCount() + 1);
                likes.setUsers(users);
                tweet.setLikes(likes);

                tweetRepository.save(tweet);
            }

            log.info("userLikedTweetAlready {}", userLikedTweetAlready);

            log.info(LOG_TWEET, tweet);

            return tweetOptional.get().getLikes();
        }

        return null;
    }
//
    public Tweet updateTweet(Tweet tweet) {
        log.info(LOG_TWEET, tweet);
        return tweetRepository.save(tweet);
    }

    public boolean deleteTweet(String id) {
        tweetRepository.deleteTweet(tweetRepository.getTweet(id));
        return true;
    }
}
