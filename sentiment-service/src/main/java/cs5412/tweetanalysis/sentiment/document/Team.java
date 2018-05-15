package cs5412.tweetanalysis.sentiment.document;

import java.util.List;
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
public class Team implements Serializable {
    @Id
    @Indexed
    private Integer id;
    @Indexed
    private String name;
    private List<Integer> playersId;
}
