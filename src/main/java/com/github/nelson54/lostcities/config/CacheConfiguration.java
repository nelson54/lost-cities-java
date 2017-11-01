package com.github.nelson54.lostcities.config;

import com.github.nelson54.lostcities.domain.CommandEntity;
import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache("users", jcacheConfiguration);
            cm.createCache(com.github.nelson54.lostcities.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.github.nelson54.lostcities.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.github.nelson54.lostcities.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.github.nelson54.lostcities.domain.SocialUserConnection.class.getName(), jcacheConfiguration);
            cm.createCache(com.github.nelson54.lostcities.domain.Match.class.getName(), jcacheConfiguration);
            cm.createCache(com.github.nelson54.lostcities.domain.Match.class.getName() + ".users", jcacheConfiguration);
            cm.createCache(com.github.nelson54.lostcities.domain.Match.class.getName() + ".commands", jcacheConfiguration);
            cm.createCache(CommandEntity.class.getName(), jcacheConfiguration);
            cm.createCache(CommandEntity.class.getName() + ".matches", jcacheConfiguration);
            cm.createCache(com.github.nelson54.lostcities.domain.GameUser.class.getName(), jcacheConfiguration);
            cm.createCache(com.github.nelson54.lostcities.domain.Match.class.getName() + ".gameUsers", jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
