package cs5412.tweetanalysis.tweet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableCaching
@EnableCircuitBreaker
public class TweetApplication {

    public static void main(String[] args) {
        SpringApplication.run(TweetApplication.class, args);
    }
}
