package cs5412.tweetanalysis.hotspot.repository;

import cs5412.tweetanalysis.hotspot.document.Hotspot;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotspotRepository extends MongoRepository<Hotspot, String> {
    Hotspot findByMatchIdAndTimestamp(Integer matchId, Long timestamp);
    List<Hotspot> findByMatchIdAndTimestampBetween(Integer matchId, Long startTime, Long endTime);
    Hotspot findFirstByMatchIdOrderByTimestampDesc(Integer matchId);
}
