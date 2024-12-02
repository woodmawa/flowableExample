package com.flowable.security

/*
import org.flowable.idm.api.Group
import org.flowable.idm.api.User
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.provisioning.InMemoryUserDetailsManager

import java.util.List

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private IdentityService identityService

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .httpBasic()
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        List<User> users = identityService.createUserQuery().list()
        List<Group> groups = identityService.createGroupQuery().list()

        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager()

        for (User user : users) {
            List<String> roles = identityService.createGroupQuery().groupMember(user.getId()).list()
                    .stream().map(Group::getId).collect(Collectors.toList());
            manager.createUser(org.springframework.security.core.userdetails.User
                    .withUsername(user.getId())
                    .password("{noop}" + user.getPassword()) // {noop} is used for plain text passwords in examples
                    .roles(roles.toArray(new String[0]))
                    .build())
        }

        return manager
    }
}
*/

