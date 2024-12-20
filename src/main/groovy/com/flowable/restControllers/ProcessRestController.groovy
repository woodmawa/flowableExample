package com.flowable.restControllers

import com.flowable.services.AppProcessService
import com.flowable.services.DeployProcessService
import com.flowable.services.ProcessVariableService
import groovy.util.logging.Slf4j
import org.flowable.engine.runtime.ProcessInstance
import org.flowable.task.api.Task
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile


@Slf4j
@RestController @RequestMapping("/api/process")
class ProcessRestController {
    @Autowired
    private AppProcessService processService

    @Autowired private
    ProcessVariableService processVariableService

    @Autowired
    private DeployProcessService deployProcessService

    @PostMapping("/deploy")
    public ResponseEntity<String> deployProcess(@RequestParam("file") MultipartFile file) {
        try {
            boolean deployed = deployProcessService.deployProcessDefinition(file)
            if (deployed) {
                return ResponseEntity.ok("Process deployed successfully.")
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to deploy process.")
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing the file.")
        }
    }

    @PostMapping(value="/start")
    public String startProcessInstance(@RequestParam("name") String processName, @RequestParam("variable") String value) {

        if (processService.checkProcessExists(processName)) {
            Optional<Map> variables = Optional.of([var1: value])
            log.info ("starting $processName, with variable $value")
            ProcessInstance pid = processService.startProcess(processName, variables)
            def id = pid.getProcessInstanceId ()
            def procName = pid.getProcessDefinitionName()
            log.info ("AppProcessService: started process instance $procName, with pid ${id}")
            id
        } else {
            log.error "startProcess controller action, could not find deployed process $processName (/startProcess?name=<name>&variable=<var>)"
        }
    }

    @GetMapping("/variables") public Map<String, Object> getProcessVariables (@RequestParam ("pid") String processInstanceId) {
        log.debug "reading process variables for pid $processInstanceId"
        return processVariableService.getProcessVariablesFromFinishedProcess(processInstanceId)
    }

    @GetMapping("/exists/{processKey}") public boolean checkProcessExists(@PathVariable("processKey") String processKey) {
        return processService.checkProcessExists(processKey)
    }

    @RequestMapping(value="/hello", method= RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    String sayHello (@RequestParam("name") String name) {
        String result = "hello  " + name
        println result
        result
    }

    @RequestMapping(value="/tasks", method= RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    public List<TaskRepresentation> getTasks(@RequestParam("assignee") String assignee) {
        println "getTasks assignee = " +assignee
        List<Task> tasks = processService.getTasks(assignee);
        List<TaskRepresentation> dtos = new ArrayList<TaskRepresentation>()
        for (Task task : tasks)
            dtos.add(new TaskRepresentation(task.getId(), task.getName()))
        return dtos
    }

    static class TaskRepresentation {
        private String id
        private String name
        public TaskRepresentation(String id, String name) {
            this.id = id
            this.name = name
        }
        public String getId() { return id }
        public void setId(String id) { this.id = id }
        public String getName() { return name }
        public void setName(String name) { this.name = name }
    }
}
