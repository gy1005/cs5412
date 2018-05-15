package cs5412.tweetanalysis.hotspot.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
@Builder
public class Hotspot implements Serializable {
    @Id
    private String id;
    @Indexed
    private Integer matchId;
    @Indexed
    private Long timestamp;
    private List<Keyword> keywords;
}
