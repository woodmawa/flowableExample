package com.flowable.processDelegates

import groovy.util.logging.Slf4j
import org.flowable.engine.delegate.DelegateExecution
import org.flowable.engine.delegate.JavaDelegate

@Slf4j
class SayHelloDelegate implements JavaDelegate  {
    @Override
    void execute(DelegateExecution execution) {
        log.info "serviceTask: hello william from service task delegate"
        println "hello william from service task delegate"

    }
}
