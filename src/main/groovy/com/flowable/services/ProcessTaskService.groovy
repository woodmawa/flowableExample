package com.flowable.services

import groovy.util.logging.Slf4j
import org.flowable.engine.HistoryService
import org.flowable.engine.TaskService
import org.flowable.task.api.Task
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Slf4j
class ProcessTaskService {
    @Autowired
    TaskService taskService

    @Autowired
    HistoryService historyService

    /**
     * list of current tasks across all processes for this assignee
     */
    @Transactional
    List<String> processesWithPendingTasksByAssignee (String assignee) {
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(assignee).list()
        List<String> processIds = new ArrayList<>()
        for(Task t in tasks) {
            processIds.add(t.getProcessInstanceId())
        }
        return processIds

    }

    @Transactional
    void completeTask (String processInstanceId, String taskName, Optional<Map<String, Object>> variables) {

        Map taskVariables = variables.orElse([:])
        Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).taskName(taskName).singleResult()

        taskService.complete(task.getId(), variables)
        log.debug "ProcessTaskService.completeTask completed task: ${task.getId()} with variables $taskVariables"

    }

}
