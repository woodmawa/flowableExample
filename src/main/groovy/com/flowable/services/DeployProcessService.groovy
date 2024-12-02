package com.flowable.services
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class DeployProcessService {

    @Autowired
    private RepositoryService repositoryService

    public boolean deployProcessDefinition(MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            Deployment deployment = repositoryService.createDeployment()
                    .addInputStream(file.getOriginalFilename(), inputStream)
                    .deploy();

            // Verify that the deployment contains at least one process definition
            long processDefinitionCount = repositoryService.createProcessDefinitionQuery()
                    .deploymentId(deployment.getId())
                    .count();

            return processDefinitionCount > 0
        } catch (Exception e) {
            return false
        }
    }
}
