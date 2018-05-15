package cs5412.tweetanalysis.sentiment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableCaching
@EnableCircuitBreaker
public class SentimentApplication {

    public static void main(String[] args) {
        SpringApplication.run(SentimentApplication.class, args);
    }
}
