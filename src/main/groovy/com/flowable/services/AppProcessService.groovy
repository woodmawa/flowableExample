package com.flowable.services

import groovy.util.logging.Slf4j
import org.flowable.engine.RepositoryService
import org.flowable.engine.RuntimeService
import org.flowable.engine.TaskService
import org.flowable.engine.repository.ProcessDefinition
import org.flowable.engine.runtime.ProcessInstance
import org.flowable.task.api.Task
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
@Slf4j
class AppProcessService {
    @Autowired
    private RuntimeService runtimeService
    @Autowired
    private TaskService taskService
    @Autowired
    private RepositoryService repositoryService

    @Transactional
    public ProcessInstance startProcess(String procName, Optional<Map> variables) {
        Map processVariables = variables.orElse([:])
        log.info "AppProcessService : starting process '$procName' with variables: $processVariables "
        ProcessInstance pid = runtimeService.startProcessInstanceByKey(procName, processVariables)
        pid
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
