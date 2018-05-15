package cs5412.tweetanalysis.hotspot.controller;

import cs5412.tweetanalysis.hotspot.document.Hotspot;
import cs5412.tweetanalysis.hotspot.service.HotspotService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping
@CacheConfig(cacheNames = "hotspot")
@RequiredArgsConstructor
public class HotspotController {
    private final HotspotService hotspotService;

    @PostMapping("/save")
    @CachePut(value = "hotspot", key = "#hotspot.id")
    public Hotspot save(@Valid @RequestBody Hotspot hotspot) {
        return this.hotspotService.save(hotspot);
    }

    @GetMapping(value = "/api/match/{matchId}/at/{time}")
    @Cacheable(value = "hotspot", key = "#matchId + '_' + #time", unless = "null")
    public Hotspot findByMatchIdAndTimestamp(@PathVariable(value = "matchId") Integer matchId,
                                            @PathVariable(value = "time") Long time) {
        return this.hotspotService.findByMatchIdAndTimestamp(matchId, time);
    }

    @GetMapping(value = "/api/match/{matchId}/between/{startTime}/and/{endTime}")
    @Cacheable(value = "hotspot", key = "#matchId + '_' + #startTime + '_' +#endTime", unless = "null")
    public List<Hotspot> findByMatchIdAndTimestampBetween(@PathVariable(value = "matchId") Integer matchId,
                                                                   @PathVariable(value = "startTime") Long startTime,
                                                                   @PathVariable(value = "endTime") Long endTime) {
        return this.hotspotService.findByMatchIdAndTimestampBetween(matchId, startTime, endTime);
    }

    @GetMapping(value = "/api/match/{matchId}")
    public Hotspot findLatestHotspot(@PathVariable(value = "matchId") Integer matchId) {
        return this.hotspotService.findFirstByMatchIdOrderByTimestampDesc(matchId);
    }
}
