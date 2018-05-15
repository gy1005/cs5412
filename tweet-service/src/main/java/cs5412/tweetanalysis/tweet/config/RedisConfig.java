package cs5412.tweetanalysis.tweet.config;


import cs5412.tweetanalysis.tweet.document.Tweet;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;



@Configuration
public class RedisConfig extends CachingConfigurerSupport {



    @Bean
    public RedisTemplate<String, Tweet> jsonRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Tweet> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Tweet.class));
        redisTemplate.setHashKeySerializer(redisTemplate.getKeySerializer());
        redisTemplate.setHashValueSerializer(redisTemplate.getValueSerializer());
        return redisTemplate;
    }



}

