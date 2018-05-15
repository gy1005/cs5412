package cs5412.tweetanalysis.tweet.controller;


import cs5412.tweetanalysis.tweet.document.Tweet;
import cs5412.tweetanalysis.tweet.service.TweetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping
@CacheConfig(cacheNames = "tweet")
@RequiredArgsConstructor
public class TweetController {
    private final TweetService tweetService;

    @PostMapping("/save")
    @CachePut(value = "tweet", key = "#tweet.id")
    public Tweet save(@Valid @RequestBody Tweet tweet) {
        return this.tweetService.save(tweet);
    }

    @GetMapping("/api/search/{critiria}")
    public List<Tweet> findTweetsByCritiria(@PathVariable(value = "critiria") String critiria) {
        return this.tweetService.findTweetsByCritiria(critiria);

    }

    @GetMapping("/api/match/{matchId}/between/{startTime}/and/{endTime}")
    public List<Tweet> findByMatchIdAndTimestampBetween(@PathVariable(value = "matchId") Integer matchId,
                                                        @PathVariable(value = "startTime") Long startTime,
                                                        @PathVariable(value = "endTime") Long endTime) {
        return this.tweetService.findByMatchIdAndTimestampBetween(matchId, startTime, endTime);

    }



}
