package cs5412.tweetanalysis.tweet.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import cs5412.tweetanalysis.tweet.document.Tweet;
import cs5412.tweetanalysis.tweet.repository.TweetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class TweetService {

    private final MongoTemplate mongoTemplate;
    private final TweetRepository tweetRepository;


    @HystrixCommand(fallbackMethod = "findTweetsByCritiriaOnError")
    public List<Tweet> findTweetsByCritiria(String criteria){
        log.info("Find tweet by critiria:{}", criteria);
        TextCriteria textCriteria = TextCriteria.forDefaultLanguage().matching(criteria);
        Query query = TextQuery.queryText(textCriteria);
        query.limit(10);
        query.with(new Sort(Sort.Direction.DESC, "timestamp"));
        return this.mongoTemplate.find(query, Tweet.class);

    }


    @HystrixCommand(fallbackMethod = "saveOnError")
    public Tweet save(Tweet tweet) {
        log.info("Save tweet :{}", tweet);
        return this.tweetRepository.save(tweet);
    }

    @HystrixCommand(fallbackMethod = "findByMatchIdAndTimestampBetweenOnError")
    public List<Tweet> findByMatchIdAndTimestampBetween(Integer matchId, Long startTime, Long endTime) {
        log.info("Find tweet by teamId: {} and time between {} and {}", matchId, startTime, endTime);
        return this.tweetRepository.findByMatchIdAndTimestampBetween(matchId, startTime, endTime);



    }

    public List<Tweet> findTweetsByCritiriaOnError(String criteria){
        log.error("Find tweet by critiria:{} error", criteria);
        return null;
    }


    public Tweet saveOnError(Tweet tweet) {
        log.error("Save tweet :{} error", tweet);
        return new Tweet();
    }


    public List<Tweet> findByMatchIdAndTimestampBetweenOnError(Integer matchId, Long startTime, Long endTime) {
        log.error("Find tweet by teamId: {} and time between {} and {}", matchId, startTime, endTime);
        return null;
    }


}
