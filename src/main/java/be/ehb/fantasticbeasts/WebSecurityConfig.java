package be.ehb.fantasticbeasts;

import be.ehb.fantasticbeasts.controllers.LogoutHandler;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final LogoutHandler logoutHandler;

    public WebSecurityConfig(LogoutHandler logoutHandler) {
        this.logoutHandler = logoutHandler;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.oauth2Login().and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).addLogoutHandler(logoutHandler);
    }

}
