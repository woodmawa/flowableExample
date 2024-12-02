package com.flowable.restControllers

import com.flowable.services.ProcessService
import org.flowable.task.api.Task
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ProcessRestController {
    @Autowired
    private ProcessService processService

    @PostMapping(value="/startProcess")
    public void startProcessInstance(@RequestParam("name") String processName, @RequestParam("variable") String value) {
        Optional<Map> variables = Optional.of ([var1:value])
        processService.startProcess(processName, variables)

    }

    @GetMapping("/exists/{processKey}") public boolean checkProcessExists(@PathVariable String processKey) {
        return processService.checkProcessExists(processKey)
    }

    @RequestMapping(value="/hello", method= RequestMethod.GET, produces= MediaType.APPLICATION_JSON_VALUE)
    void sayHello (@RequestParam("name") String name) {
        println "hello  " + name
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
