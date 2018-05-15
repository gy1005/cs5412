package cs5412.tweetanalysis.sentiment.service;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import cs5412.tweetanalysis.sentiment.document.Sentiment;
import cs5412.tweetanalysis.sentiment.document.SentimentResponse;
import cs5412.tweetanalysis.sentiment.repository.SentimentResponseRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SentimentResponseService {
    private final SentimentResponseRepository sentimentResponseRepository;

    @HystrixCommand(fallbackMethod = "saveOnError")
    public SentimentResponse save(SentimentResponse sentimentResponse) {
        log.info("Save sentimentResponse :{}", sentimentResponse);
        return this.sentimentResponseRepository.save(sentimentResponse);
    }

    public SentimentResponse saveOnError(SentimentResponse sentimentResponse) {
        log.info("Save sentimentResponse :{} error", sentimentResponse);
        return new SentimentResponse();
    }

    @HystrixCommand(fallbackMethod = "findByTeamIdAndTimestampOnError")
    public SentimentResponse findByTeamIdAndTimestamp(Integer teamId, Long time) {
        log.info("Find sentimentResponse by teamId:{} and time: {}", teamId, time);
        return this.sentimentResponseRepository.findByTeamIdAndTimestamp(teamId, time);
    }

    public SentimentResponse findByTeamIdAndTimestampOnError(Integer teamId, Long time) {
        log.info("Find sentimentResponse by teamId:{} and time: {} error", teamId, time);
        return new SentimentResponse();
    }

    @HystrixCommand(fallbackMethod = "findByTeamIdAndTimestampBetweenOnError")
    public List<SentimentResponse> findByTeamIdAndTimestampBetween(Integer teamId, Long startTime, Long endTime) {
        log.info("Find sentimentResponse by teamId:{} and time between {} and {}", teamId, startTime, endTime);
        return this.sentimentResponseRepository.findByTeamIdAndTimestampBetween(teamId, startTime, endTime);
    }

    public List<SentimentResponse> findByTeamIdAndTimestampBetweenOnError(Integer teamId, Long startTime, Long endTime) {
        log.info("Find sentimentResponse by teamId:{} and time between {} and {} error", teamId, startTime, endTime);
        return null;
    }

    @HystrixCommand(fallbackMethod = "findFirstByTeamIdOrderByTimestampDescOnError")
    public SentimentResponse findFirstByTeamIdOrderByTimestampDesc(Integer teamId) {
        log.info("Find most recent sentimentResponse by teamid: {}", teamId);
        return this.sentimentResponseRepository.findFirstByTeamIdOrderByTimestampDesc(teamId);
    }

    public SentimentResponse findFirstByTeamIdOrderByTimestampDescOnError(Integer teamId) {
        log.info("Find most recent sentimentResponse by teamid: {} error", teamId);
        return new SentimentResponse();
    }

}