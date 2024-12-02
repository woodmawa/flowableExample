package com.flowable.security

import org.flowable.engine.IdentityService;
import org.flowable.idm.api.Group;
import org.flowable.idm.api.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlowableUserAndRolesConfig {

    @Autowired
    private IdentityService identityService

    public void init() {
        if (identityService.createUserQuery().userId("admin").singleResult() == null) {
            User admin = identityService.newUser("admin")
            admin.setPassword("admin")
            identityService.saveUser(admin)

            Group adminGroup = identityService.newGroup("adminGroup")
            adminGroup.setName("AdminGroup")
            adminGroup.setType("security-role")
            identityService.saveGroup(adminGroup)

            identityService.createMembership("admin", "adminGroup")
        }
    }
}
