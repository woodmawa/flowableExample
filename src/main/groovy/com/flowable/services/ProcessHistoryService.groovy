package com.flowable.services

import groovy.util.logging.Slf4j
import org.flowable.engine.HistoryService
import org.flowable.engine.history.HistoricActivityInstance
import org.flowable.engine.runtime.ProcessInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Slf4j
class ProcessHistoryService {
    @Autowired
    HistoryService historyService

    @Transactional
    List completedProcessActivities (ProcessInstance  processInstance) {
        assert processInstance

        List<HistoricActivityInstance> activities = historyService
                .createHistoricActivityInstanceQuery()
                .processInstanceId(processInstance.getId())
                .finished()
                .orderByHistoricActivityInstanceEndTime()
                .asc()
                .list()
    }
}
