package no.gainmaster.user.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class OAuthConfiguration extends ResourceServerConfigurerAdapter {

    @Autowired
    @Value("#{oauthDataSource}")
    DataSource oauthDataSource;

    @Override
    public void configure(ResourceServerSecurityConfigurer oauthProvider) throws Exception {
        oauthProvider.resourceId("gainmaster").tokenServices(resourceTokenService());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest()
                    .authenticated();
    //        .and()
    //            .requestMatcher(new AntPathRequestMatcher("/users", "POST")).anonymous();
    //     TODO: Authenticate only client on add user
    }

    @Bean
    public ResourceServerTokenServices resourceTokenService() {
        DefaultTokenServices tokenService = new DefaultTokenServices();
        tokenService.setTokenStore(tokenStore());
        return tokenService;
    }

    @Bean
    public JdbcTokenStore tokenStore() {
        return new JdbcTokenStore(oauthDataSource);
    }

}

