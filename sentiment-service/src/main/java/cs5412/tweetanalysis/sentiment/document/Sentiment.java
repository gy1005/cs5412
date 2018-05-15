package cs5412.tweetanalysis.sentiment.document;

import java.util.Date;
import java.util.List;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


import java.io.Serializable;



@Document
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Sentiment implements Serializable {

    @Id
    private String id;
    @Indexed
    private Integer teamId;
    @Indexed
    private Long timestamp;
    private Double polarity;
    private Long tweetId;
    private Tweet tweet;
}
