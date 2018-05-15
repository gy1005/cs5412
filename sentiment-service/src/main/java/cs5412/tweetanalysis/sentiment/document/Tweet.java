package cs5412.tweetanalysis.sentiment.document;

import java.util.List;
import lombok.*;
import java.io.Serializable;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Tweet implements Serializable {
    private Long id;
    private Long userId;
    private String userName;
    private String text;
    private List <Double> geo;
    private List<String> hashTags;
    private Integer matchId;
    private Integer teamId;
    private Long timestamp;
}
