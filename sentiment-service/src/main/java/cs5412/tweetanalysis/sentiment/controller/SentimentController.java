package cs5412.tweetanalysis.sentiment.controller;

import cs5412.tweetanalysis.sentiment.document.Sentiment;
import cs5412.tweetanalysis.sentiment.service.SentimentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
@CacheConfig(cacheNames = "Sentiment")
@RequiredArgsConstructor
public class SentimentController {
    private final SentimentService service;


    @PostMapping(value = "/save")
    @CachePut(value = "Sentiment", key = "#sentiment.id")
    public Sentiment save(@Valid @RequestBody Sentiment sentiment) {
        return this.service.save(sentiment);
    }

    @GetMapping(value = "/id/{id}")
    @Cacheable(value = "Sentiment", key = "#id", unless = "#result == null")
    public Optional<Sentiment> findById(
            @PathVariable(value = "id") String id) {
        return this.service.findById(id);
    }

    @GetMapping(value = "/team/{teamId}/start/{startTime}/end/{endTime}")
    public List<Sentiment> findAllByTeamIdAndTimestampBetween(@PathVariable(value = "teamId") Integer teamId
                                                                ,@PathVariable(value = "startTime") Long startTime
                                                                ,@PathVariable(value = "endTime") Long endTime)
    {
        return this.service.findAllByTeamIdAndTimestampBetween(teamId, startTime, endTime);
    }
}
