/*
 * Copyleft 2015 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jam.metrics.applicationmetricsjavaseapitest;

import java.util.HashMap;
import org.jam.metrics.applicationmetricsapi.CodeParamsApi;
import org.jam.metrics.applicationmetricsapi.MetricsCacheApi;
import org.jam.metrics.applicationmetricsapi.MetricsPropertiesApi;
import org.jam.metrics.applicationmetricsproperties.MetricProperties;
import org.junit.Test;
import org.jboss.eap.additional.testsuite.annotations.EapAdditionalTestsuite;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
@EapAdditionalTestsuite({"modules/testcases/jdkAll/master/jam/src/main/java"})
public class ApplicationMetricsJavaSeApi5Test {

    private String groupName = "myTestGroup";
    
    @Test
    public void test() {
        try {
            initializeMetricProperties();
            MetricsApiSeTestClass5 mTC = new MetricsApiSeTestClass5();
            mTC.countMethod();
            mTC.countMethod();
            mTC.countMethod();
            mTC.countMethod();
            mTC.countMethod();
            mTC.countMethod();
            mTC.countMethod();
            mTC.countMethod();
            System.out.println(MetricsCacheApi.printMetricsCache(groupName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeMetricProperties() {
        HashMap<String,String> rhqScheduleIds = new HashMap<String,String>();
        rhqScheduleIds.put("count", "11391");
        rhqScheduleIds.put("count2", "11392");
        MetricProperties metricProperties = new MetricProperties();
        metricProperties.setRhqMonitoring("false");
        metricProperties.setCacheStore("true");
        metricProperties.setFilterMetrics("true");
        metricProperties.setRhqServerUrl("lz-panos-jon33.bc.jonqe.lab.eng.bos.redhat.com");
        metricProperties.setRhqScheduleIds(rhqScheduleIds);
        CodeParamsApi.addUserName("Niki");
        MetricsPropertiesApi.storeProperties(groupName, metricProperties);
    }
}
