/*
 * Copyright OpenSearch Contributors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.opensearch.commons.alerting.action

import org.junit.Assert.assertEquals
import org.junit.jupiter.api.Test
import org.opensearch.common.io.stream.BytesStreamOutput
import org.opensearch.commons.alerting.model.InputRunResults
import org.opensearch.commons.alerting.randomDocumentLevelTriggerRunResult
import org.opensearch.core.common.io.stream.StreamInput

class DocLevelMonitorFanOutResponseTests {

    @Test
    fun `test doc level monitor fan out response with errors as stream`() {
        val docLevelMonitorFanOutResponse = DocLevelMonitorFanOutResponse(
            "nodeid",
            "eid",
            "monitorId",
            mutableMapOf("index" to mutableMapOf("1" to "1")),
            InputRunResults(error = null),
            mapOf("1" to randomDocumentLevelTriggerRunResult(), "2" to randomDocumentLevelTriggerRunResult())
        )
        val out = BytesStreamOutput()
        docLevelMonitorFanOutResponse.writeTo(out)
        val sin = StreamInput.wrap(out.bytes().toBytesRef().bytes)
        val newDocLevelMonitorFanOutResponse = DocLevelMonitorFanOutResponse(sin)
        assertEquals(docLevelMonitorFanOutResponse.nodeId, newDocLevelMonitorFanOutResponse.nodeId)
        assertEquals(docLevelMonitorFanOutResponse.executionId, newDocLevelMonitorFanOutResponse.executionId)
        assertEquals(docLevelMonitorFanOutResponse.monitorId, newDocLevelMonitorFanOutResponse.monitorId)
        assertEquals(docLevelMonitorFanOutResponse.lastRunContexts, newDocLevelMonitorFanOutResponse.lastRunContexts)
        assertEquals(docLevelMonitorFanOutResponse.inputResults, newDocLevelMonitorFanOutResponse.inputResults)
        assertEquals(docLevelMonitorFanOutResponse.triggerResults, newDocLevelMonitorFanOutResponse.triggerResults)
    }

    @Test
    fun `test doc level monitor fan out response as stream`() {
        val workflow = DocLevelMonitorFanOutResponse(
            "nodeid",
            "eid",
            "monitorId",
            mapOf("index" to mapOf("1" to "1")) as MutableMap<String, Any>,
            InputRunResults(),
            mapOf("1" to randomDocumentLevelTriggerRunResult(), "2" to randomDocumentLevelTriggerRunResult())
        )
        val out = BytesStreamOutput()
        workflow.writeTo(out)
        val sin = StreamInput.wrap(out.bytes().toBytesRef().bytes)
        val newWorkflow = DocLevelMonitorFanOutResponse(sin)
        assertEquals(workflow.nodeId, newWorkflow.nodeId)
        assertEquals(workflow.executionId, newWorkflow.executionId)
        assertEquals(workflow.monitorId, newWorkflow.monitorId)
        assertEquals(workflow.lastRunContexts, newWorkflow.lastRunContexts)
        assertEquals(workflow.inputResults, newWorkflow.inputResults)
        assertEquals(workflow.triggerResults, newWorkflow.triggerResults)
    }
}
