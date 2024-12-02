package com.flowable.services

import groovy.util.logging.Slf4j
import org.flowable.engine.RepositoryService
import org.flowable.engine.RuntimeService
import org.flowable.engine.TaskService
import org.flowable.engine.repository.ProcessDefinition
import org.flowable.task.api.Task
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
@Slf4j
class ProcessService {
    @Autowired
    private RuntimeService runtimeService
    @Autowired
    private TaskService taskService
    @Autowired
    private RepositoryService repositoryService

    @Transactional
    public void startProcess(String procName, Optional<Map> variables) {
        Map processVariables = variables.orElse([:])
        log.info "rest API : starting process " + procName + " with variables $processVariables "
        runtimeService.startProcessInstanceByKey(procName, processVariables)
        log.debug ("AppProcessService: started process instance $procName")
    }

    //check if deployed process exists
    public boolean checkProcessExists(String processKey) {
        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                .processDefinitionKey(processKey)
                .latestVersion()
                .singleResult()

        return processDefinition != null
    }

    @Transactional
    public List<Task> getTasks(String assignee) {

        return taskService.createTaskQuery().taskAssignee(assignee).list()
    }

}
