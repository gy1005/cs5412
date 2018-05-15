package cs5412.tweetanalysis.sentiment.repository;

import cs5412.tweetanalysis.sentiment.document.Sentiment;
import cs5412.tweetanalysis.sentiment.document.SentimentResponse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SentimentResponseRepository extends MongoRepository<SentimentResponse, String> {
    public SentimentResponse findByTeamIdAndTimestamp(Integer teamId, Long time);
    public List<SentimentResponse> findByTeamIdAndTimestampBetween(Integer teamId, Long startTime, Long endTime);
    public SentimentResponse findFirstByTeamIdOrderByTimestampDesc(Integer teamId);
}
