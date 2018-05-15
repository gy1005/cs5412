package cs5412.tweetanalysis.hotspot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableCaching
@EnableCircuitBreaker
public class HotspotApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotspotApplication.class, args);
    }
}
