package com.flowable.security

import groovy.util.logging.Slf4j
import jakarta.annotation.PostConstruct
import org.apache.tomcat.util.IntrospectionUtils
import org.flowable.engine.IdentityService;
import org.flowable.idm.api.Group;
import org.flowable.idm.api.User;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@Slf4j
public class FlowableConfig {

    @Autowired
    private IdentityService identityService

    //read default password from system properties - set this on your machine
    @Value ('${FLOWABLE_ADMIN_PASSWORD:admin}')
    private String adminPassword

    @PostConstruct  //auto start the init routine
    public void init() {

        if (identityService.createUserQuery().userId("admin").singleResult() == null) {
            log.info ("FlowableUserAndRolesConfig:  adding admin user with default password ")
            User admin = identityService.newUser("admin")
            assert adminPassword
            admin.setPassword(adminPassword)
            identityService.saveUser(admin)

            Group adminGroup = identityService.newGroup("adminGroup")
            adminGroup.setName("AdminGroup")
            adminGroup.setType("security-role")
            identityService.saveGroup(adminGroup)

            log.info ("FlowableUserAndRolesConfig:  adding admin to adminGroup ")
            identityService.createMembership("admin", "adminGroup")
        } else {
            log.info "FlowableUserAndRolesConfig: noop - admin user id already created "
        }
    }


}
