package cs5412.tweetanalysis.tweet.repository;

import cs5412.tweetanalysis.tweet.document.Tweet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetRepository extends MongoRepository<Tweet, Long> {
    public List<Tweet> findByMatchIdAndTimestampBetween(Integer matchId, Long startTime, Long endTime);
}
