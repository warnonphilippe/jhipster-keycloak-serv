package be.phw.serv.multitenancy;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;


@Configuration
public class MultiTenantConfig {

    @Bean(name = "multiResourceServerProperties")
    @Primary
    public ResourceServerProperties multiResourceServerProperties(){
        return new MultiResourceServerProperties();
    }

    @Bean
    @ConfigurationProperties("spring.oauth2.client")
    @Primary
    public ClientCredentialsResourceDetails oauth2RemoteResource() {
        ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
        return details;
    }

    @Bean
    public OAuth2ClientContext oauth2ClientContext() {
        return new DefaultOAuth2ClientContext(new DefaultAccessTokenRequest());
    }

    @Bean(name = "multiAuthorizationCodeResourceDetails")
    public AuthorizationCodeResourceDetails multiAuthorizationCodeResourceDetails(){
        return new MultiAuthorizationCodeResourceDetails();
    }

    @Bean
    @Primary
    public OAuth2RestTemplate oauth2RestTemplate(@Qualifier("multiAuthorizationCodeResourceDetails") OAuth2ProtectedResourceDetails details,
                                                 OAuth2ClientContext oauth2ClientContext) {
        OAuth2RestTemplate template = new OAuth2RestTemplate(details, oauth2ClientContext);
        return template;
    }

    @Bean
    @Primary
    public UserInfoRestTemplateFactory userInfoRestTemplateFactory(){
        return new UserInfoRestTemplateFactory() {
            @Override
            public OAuth2RestTemplate getUserInfoRestTemplate() {
                return oauth2RestTemplate(multiAuthorizationCodeResourceDetails(), oauth2ClientContext());
            }
        };
    }


}
