package cs5412.tweetanalysis.sentiment.controller;

import cs5412.tweetanalysis.sentiment.document.Sentiment;
import cs5412.tweetanalysis.sentiment.document.SentimentResponse;
import cs5412.tweetanalysis.sentiment.service.SentimentResponseService;
import cs5412.tweetanalysis.sentiment.service.SentimentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping
@CacheConfig(cacheNames = "SentimentResponse")
@RequiredArgsConstructor
@Slf4j
public class SentimentResponseController {
    private final SentimentResponseService sentimentResponseService;


    @PostMapping(value = "/saveresp")
    @CachePut(value = "SentimentResponse", key = "#sentimentResponse.teamId + '_' + #sentimentResponse.timestamp")
    public SentimentResponse save(@Validated @RequestBody SentimentResponse sentimentResponse) {
        return this.sentimentResponseService.save(sentimentResponse);
    }

    @GetMapping(value = "/api/team/{teamId}/at/{time}")
    @Cacheable(value = "SentimentResponse", key = "#teamId + '_' + #time", unless = "null")
    public SentimentResponse findByTeamIdAndTimestamp(@PathVariable(value = "teamId") Integer teamId,
                                                      @PathVariable(value = "time") Long time) {
        return this.sentimentResponseService.findByTeamIdAndTimestamp(teamId, time);
    }

    @GetMapping(value = "/api/team/{teamId}/between/{startTime}/and/{endTime}")
    @Cacheable(value = "SentimentResponse", key = "#teamId + '_' + #startTime + '_' +#endTime", unless = "null")
    public List<SentimentResponse> findByTeamIdAndTimestampBetween(@PathVariable(value = "teamId") Integer teamId,
                                                                @PathVariable(value = "startTime") Long startTime,
                                                                @PathVariable(value = "endTime") Long endTime) {
        return this.sentimentResponseService.findByTeamIdAndTimestampBetween(teamId, startTime, endTime);
    }

    @GetMapping(value = "/api/team/{teamId}")
    public SentimentResponse findLatestHotspot(@PathVariable(value = "teamId") Integer teamId) {
        return this.sentimentResponseService.findFirstByTeamIdOrderByTimestampDesc(teamId);
    }
}

