package com.flowable.services

import org.flowable.cmmn.api.history.HistoricVariableInstanceQuery
import org.flowable.engine.HistoryService
import org.flowable.engine.history.HistoricProcessInstance
import org.flowable.variable.api.history.HistoricVariableInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProcessVariableService {

    @Autowired
    private HistoryService historyService

    public Map<String, Object> getProcessVariablesFromFinishedProcess(String processInstanceId) {
        List<HistoricVariableInstanceQuery> variableInstances = historyService.createHistoricVariableInstanceQuery()
                .processInstanceId(processInstanceId)
                .list()

        Map<String, Object> variables = new HashMap<>();
        for (HistoricVariableInstance variableInstance in variableInstances) {
            variables.put(variableInstance.getVariableName(), variableInstance.getValue())
        }

        return variables
    }
}
