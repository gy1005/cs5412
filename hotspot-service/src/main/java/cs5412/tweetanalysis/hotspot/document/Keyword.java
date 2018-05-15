package cs5412.tweetanalysis.hotspot.document;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Keyword implements Serializable {
    private String text;
    private Double freq;

}
