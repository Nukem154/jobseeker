package com.nukem.jobseeker.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableCaching
@EnableScheduling
public class CacheConfig {
    private final Logger logger = LoggerFactory.getLogger(CacheConfig.class);

    public static final String VACANCIES = "VACANCIES";

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(VACANCIES);
    }

    @CacheEvict(allEntries = true, value = {VACANCIES})
    @Scheduled(fixedDelay = 10 * 60 * 1000, initialDelay = 500)
    public void reportVacanciesCacheEvict() {
        logger.info("Flushing cache");
    }


}
