package com.flowable

import org.flowable.engine.RepositoryService
import org.flowable.engine.RuntimeService
import org.flowable.engine.TaskService
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
//import org.springframework.security.config.web.server.ServerHttpSecurity
//import org.springframework.security.web.server.SecurityWebFilterChain

@SpringBootApplication(proxyBeanMethods = false)
class FlowableApp {
    static void main(String[] args) {
        SpringApplication.run(FlowableApp.class, args)
    }

    @Bean
    CommandLineRunner init(final RepositoryService repositoryService,
                                  final RuntimeService runtimeService,
                                  final TaskService taskService) {

        return new CommandLineRunner() {
            @Override
            void run(String... strings) throws Exception {
                System.out.println("Number of process definitions : "
                        + repositoryService.createProcessDefinitionQuery().count())
                System.out.println("Number of tasks : " + taskService.createTaskQuery().count())
                runtimeService.startProcessInstanceByKey("oneTaskProcess")
                System.out.println("Number of tasks after process start: "
                        + taskService.createTaskQuery().count())
                println "-->start say hello service process task  .."
                runtimeService.startProcessInstanceByKey("simple-service-delegate")

                println "-->start simple groovy script task "
                Map<String, String> processVariables = [var1: "william"]
                runtimeService.startProcessInstanceByKey("simple-script-task", processVariables)

            }
        }
    }

    /*@Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .authorizeExchange()
                .pathMatchers("/actuator/**").permitAll()
                .anyExchange().authenticated()
                .and().build();
    }*/
}
