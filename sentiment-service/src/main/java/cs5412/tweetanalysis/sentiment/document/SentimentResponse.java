package cs5412.tweetanalysis.sentiment.document;


import lombok.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Document
@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SentimentResponse implements Serializable {
    @Id
    @Indexed
    private String id;
    @Indexed
    private Integer teamId;
    private Double polarity;
    private List<Sentiment> posSentiments;
    private List<Sentiment> negSentiments;
    private Long timestamp;
    private Integer count;
    private Integer posCount;
    private Integer negCount;



}
