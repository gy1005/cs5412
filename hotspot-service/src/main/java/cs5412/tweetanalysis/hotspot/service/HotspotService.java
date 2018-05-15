package cs5412.tweetanalysis.hotspot.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import cs5412.tweetanalysis.hotspot.document.Hotspot;
import cs5412.tweetanalysis.hotspot.repository.HotspotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotspotService {
    private final HotspotRepository hotspotRepository;

    @HystrixCommand(fallbackMethod = "saveOnError")
    public Hotspot save(Hotspot hotspot) {
        log.info("Save hotspot :{}", hotspot);
        return hotspotRepository.save(hotspot);
    }

    @HystrixCommand(fallbackMethod = "findByMatchIdAndTimestampOnError")
    public Hotspot findByMatchIdAndTimestamp(Integer matchId, Long timestamp) {
        log.info("Find hotspot by matchid: {} and timestamp: {}", matchId, timestamp);
        return this.hotspotRepository.findByMatchIdAndTimestamp(matchId, timestamp);
    }

    @HystrixCommand(fallbackMethod = "findByMatchIdAndTimestampBetweenOnError")
    public List<Hotspot> findByMatchIdAndTimestampBetween(Integer matchId, Long startTime, Long endTime) {
        log.info("Find hotspot by matchid: {} and timestamp between {} nnd {}", matchId, startTime, endTime);
        return this.hotspotRepository.findByMatchIdAndTimestampBetween(matchId, startTime, endTime);
    }

    @HystrixCommand(fallbackMethod = "findFirstByMatchIdOrderByTimestampDescOnError")
    public Hotspot findFirstByMatchIdOrderByTimestampDesc(Integer matchId) {
        log.info("Find most recent hotspot by matchid: {}", matchId);
        return this.hotspotRepository.findFirstByMatchIdOrderByTimestampDesc(matchId);
    }


    public Hotspot saveOnError(Hotspot hotspot) {
        log.error("Save hotspot :{} error", hotspot);
        return new Hotspot();
    }


    public Hotspot findByMatchIdAndTimestampOnError(Integer matchId, Long timestamp) {
        log.error("Find hotspot by matchid: {} and timestamp: {}", matchId, timestamp);
        return new Hotspot();
    }

    public List<Hotspot> findByMatchIdAndTimestampBetweenOnError(Integer matchId, Long startTime, Long endTime) {
        log.error("Find hotspot by matchid: {} and timestamp between {} nnd {}", matchId, startTime, endTime);
        return null;
    }


    public Hotspot findFirstByMatchIdOrderByTimestampDescOnError(Integer matchId) {
        log.error("Find most recent hotspot by matchid: {} error", matchId);
        return new Hotspot();
    }



}
