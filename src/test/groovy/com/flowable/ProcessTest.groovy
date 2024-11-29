package com.flowable

import groovy.util.logging.Slf4j
import org.flowable.engine.ProcessEngine
import org.flowable.engine.ProcessEngines
import org.flowable.engine.RuntimeService
import org.flowable.engine.TaskService
import org.flowable.engine.test.Deployment
import org.flowable.engine.test.FlowableTest
import org.flowable.task.api.Task
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.flowable.spring.impl.test.FlowableSpringExtension
import org.flowable.spring.SpringProcessEngineConfiguration
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

import static org.junit.jupiter.api.Assertions.*

@FlowableTest
@DirtiesContext
@Slf4j
class ProcessTest {

    private ProcessEngine processEngine
    private RuntimeService runtimeService
    private TaskService taskService

    @BeforeEach
    void setUp(ProcessEngine processEngine) {
        this.processEngine = processEngine
        this.runtimeService = processEngine.getRuntimeService()
        this.taskService = processEngine.getTaskService()
    }

    @Test
    @Deployment (resources = [ "processes/one-task-process-test.bpmn20.xml" ])
    void testOneTaskProcess() {

        log.info ("---> running one-task-process-test")
        runtimeService.startProcessInstanceByKey("oneTaskProcessTest")

        Task task = taskService.createTaskQuery().singleResult()

        assertEquals("my task", task.getName())

        taskService.complete(task.getId())
        assertEquals(0, runtimeService.createProcessInstanceQuery().count())
    }

    @AfterAll
    static void  shutdown() {
        //clear down all engines
        ProcessEngines.destroy()
    }

}
