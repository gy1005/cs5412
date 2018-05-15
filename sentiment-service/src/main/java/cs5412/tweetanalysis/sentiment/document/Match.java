package cs5412.tweetanalysis.sentiment.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


import java.io.Serializable;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Match implements Serializable {
    @Id
    @Indexed
    private Integer id;
    @Indexed
    private Integer homeId;
    @Indexed
    private Integer visitId;
    private Long startTime;
    private Long endTime;
    private String name;
}
