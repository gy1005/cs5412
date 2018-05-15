package cs5412.tweetanalysis.sentiment.repository;

import com.google.common.annotations.VisibleForTesting;
import cs5412.tweetanalysis.sentiment.document.Sentiment;
import cs5412.tweetanalysis.sentiment.document.SentimentResponse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SentimentRepository extends MongoRepository<Sentiment, String> {
    List<Sentiment> findAllByTeamIdAndTimestampBetween(Integer teamId,
                                                       Long startTime,
                                                       Long endTime);

}
