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
public class Player implements Serializable {

    @Id
    @Indexed
    private Integer id;
    @Indexed
    private String firstName;
    @Indexed
    private String lastName;
    @Indexed
    private Integer teamId;
}
