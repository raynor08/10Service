package com.ten.service.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.support.ConnectionFactoryRegistry;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;

import com.ten.service.configuration.SocialConfig.SocialProperties;

@Configuration
@EnableSocial
@EnableConfigurationProperties(SocialProperties.class)
public class SocialConfig extends SocialConfigurerAdapter {

    @Autowired
    private SocialProperties config;

    @Bean
    public ConnectionFactoryLocator connectionFactoryLocator() {
        ConnectionFactoryRegistry registry = new ConnectionFactoryRegistry();

        registry.addConnectionFactory(new FacebookConnectionFactory(config.getFacebookClientId(),
                config.getFacebookClientSecrect()));

        registry.addConnectionFactory(new TwitterConnectionFactory(config.getTwitterClientId(),
                config.getTwitterClientSecret()));
        
        return registry;
    }

    @ConfigurationProperties(prefix = "social.config")
    public static class SocialProperties {
        private String facebookClientId;
        private String facebookClientSecrect;
        private String twitterClientId;
        private String twitterClientSecret;
        private String googleClientId;
        private String googleClientSecret;

        public String getFacebookClientId() {
            return facebookClientId;
        }

        public void setFacebookClientId(String facebookClientId) {
            this.facebookClientId = facebookClientId;
        }

        public String getFacebookClientSecrect() {
            return facebookClientSecrect;
        }

        public void setFacebookClientSecrect(String facebookClientSecrect) {
            this.facebookClientSecrect = facebookClientSecrect;
        }

        public String getTwitterClientId() {
            return twitterClientId;
        }

        public void setTwitterClientId(String twitterClientId) {
            this.twitterClientId = twitterClientId;
        }

        public String getTwitterClientSecret() {
            return twitterClientSecret;
        }

        public void setTwitterClientSecret(String twitterClientSecret) {
            this.twitterClientSecret = twitterClientSecret;
        }

        public String getGoogleClientId() {
            return googleClientId;
        }

        public void setGoogleClientId(String googleClientId) {
            this.googleClientId = googleClientId;
        }

        public String getGoogleClientSecret() {
            return googleClientSecret;
        }

        public void setGoogleClientSecret(String googleClientSecret) {
            this.googleClientSecret = googleClientSecret;
        }
    }
}
