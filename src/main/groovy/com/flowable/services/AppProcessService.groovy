package com.flowable.services

import groovy.util.logging.Slf4j
import org.flowable.engine.RuntimeService
import org.flowable.engine.TaskService
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

    @Transactional
    public void startProcess(String procName, Optional<Map> variables ) {
        Map processVariables = variables.orElse([:])
        log.info "rest API : starting process " +  procName + " with variables $processVariables "
        runtimeService.startProcessInstanceByKey(procName, processVariables)
    }

    @Transactional
    public List<Task> getTasks(String assignee) {

        return taskService.createTaskQuery().taskAssignee(assignee).list()
    }

}
