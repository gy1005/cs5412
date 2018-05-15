package cs5412.tweetanalysis.tweet.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
@Builder
public class Tweet implements Serializable {
    @Id
    private Long id;
    @Indexed
    private Long userId;
    @Indexed
    private String userName;
    @TextIndexed
    private String text;
    private List<Double> geo;
    private List<String> hashTags;
    @Indexed
    private Integer matchId;
    private Integer teamId;
    private Long timestamp;

}
