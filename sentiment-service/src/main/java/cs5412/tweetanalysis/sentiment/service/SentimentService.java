package cs5412.tweetanalysis.sentiment.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import cs5412.tweetanalysis.sentiment.document.Sentiment;
import cs5412.tweetanalysis.sentiment.repository.SentimentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SentimentService {
    private final SentimentRepository sentimentRepository;

    @HystrixCommand(fallbackMethod = "findByIdOnError")
    public Optional<Sentiment> findById(String id) {
        log.info("Find sentiment by id : {}", id);
        return this.sentimentRepository.findById(id);
    }

    @HystrixCommand(fallbackMethod = "saveOnError")
    public Sentiment save(Sentiment sentiment) {
        log.info("Save sentiment :{}", sentiment);
        return sentimentRepository.save(sentiment);
    }

    @HystrixCommand(fallbackMethod = "findAllByTeamIdAndTimestampBetweenOnError")
    public List<Sentiment> findAllByTeamIdAndTimestampBetween(Integer teamId, Long startTime, Long endTime)
    {
        log.info("Find sentiment by teamId:{}, between {} and {}", teamId, startTime, endTime);
        return sentimentRepository.findAllByTeamIdAndTimestampBetween(teamId, startTime, endTime);
    }

    private Optional<Sentiment> findByIdOnError(String id) {
        log.info("Find sentiment error, id: {}", id);
        return Optional.empty();
    }

    private Sentiment saveOnError(Sentiment sentiment) {
        log.info("Save sentiment error: {}", sentiment);
        return new Sentiment();
    }

    private List<Sentiment> findAllByTeamIdAndTimestampBetweenOnError(Integer teamId, Long startTime, Long endTime) {
        log.info("Find sentiment by teamId:{}, between {} and {} error", teamId, startTime, endTime);
        return new ArrayList<>();
    }
}
