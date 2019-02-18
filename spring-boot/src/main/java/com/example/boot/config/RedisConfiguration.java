package com.example.boot.config;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 启用Spring Cache, 可以使用Spring Cache注解的方式来使用Redis缓存
 */
@Configuration
@EnableCaching
public class RedisConfiguration extends CachingConfigurerSupport {

    @Value("${spring.redis.lettuce.pool.max-active}")
    private int maxActive;

    @Value("${spring.redis.lettuce.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.lettuce.pool.min-idle}")
    private int minIdle;

    @Value("${spring.redis.sentinel.master}")
    private String sentinelMaster;

    @Value("${spring.redis.sentinel.nodes}")
    private String sentinelNodes;

    @Bean(name = "standaloneRedisTemplate")
    public RedisTemplate<String, String> standaloneRedisTemplate(@Value("${spring.redis.standalone.host}") String host,
                                                                  @Value("${spring.redis.standalone.port}") int port) {
        RedisTemplate<String, String> redisTemplate = buildRedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory(host, port));
        return redisTemplate;
    }

    public JedisConnectionFactory redisConnectionFactory(String host, int port) {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);

        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder builder =
                (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder)JedisClientConfiguration.builder();
        builder.poolConfig(jedisPoolConfig());
        JedisClientConfiguration jedisClientConfiguration = builder.build();

        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
    }

    @Bean(name = "sentinelRedisTemplate")
    public RedisTemplate<String, String> sentinelRedisTemplate(@Value("${spring.redis.sentinel.master}") String master,
                                                               @Value("${spring.redis.sentinel.nodes}") String nodes) {
        RedisTemplate<String, String> redisTemplate = buildRedisTemplate();
        redisTemplate.setConnectionFactory(sentinelRedisFactory());
        return redisTemplate;
    }

    @Bean(name = "sentinelRedisFactory")
    public RedisConnectionFactory sentinelRedisFactory() {
        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration().master(sentinelMaster);
        String[] sentinels = sentinelNodes.split(",");
        for(int i = 0; i < sentinels.length; i++) {
            String[] settings = sentinels[i].split(":");
            redisSentinelConfiguration.sentinel(settings[0], NumberUtils.toInt(settings[1]));
        }

        LettuceClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
                .poolConfig(jedisPoolConfig())
                .build();
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisSentinelConfiguration, clientConfig);
        return lettuceConnectionFactory;
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle); //最大空闲数
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxTotal(maxActive);  //连接池的最大数据库连接数
        jedisPoolConfig.setMaxWaitMillis(5000);  //最大建立连接等待时间
        jedisPoolConfig.setMinEvictableIdleTimeMillis(300000);  //逐出连接的最小空闲时间
        jedisPoolConfig.setNumTestsPerEvictionRun(1024);
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(30000);
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setTestWhileIdle(true);
        return jedisPoolConfig;
    }

    private RedisTemplate<String, String> buildRedisTemplate() {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        RedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(stringRedisSerializer);
        return redisTemplate;
    }
}
