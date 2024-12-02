package com.flowable.security

import groovy.util.logging.Slf4j
import jakarta.annotation.PostConstruct
import org.flowable.engine.IdentityService;
import org.flowable.idm.api.Group;
import org.flowable.idm.api.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class FlowableConfig {

    @Autowired
    private IdentityService identityService

    @PostConstruct  //auto start the init routine
    public void init() {
        if (identityService.createUserQuery().userId("admin").singleResult() == null) {
            log.info ("FlowableUserAndRolesConfig:  adding admin user with default password ")
            User admin = identityService.newUser("admin")
            admin.setPassword("admin")
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
