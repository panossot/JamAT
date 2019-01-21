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

import org.jam.metrics.applicationmetricsapi.CodeParamsApi;
import org.jam.metrics.applicationmetricsapi.MetricsCacheApi;

import org.jam.metrics.applicationmetricsapi.MetricsPropertiesApi;
import org.jam.metrics.applicationmetricslibrary.MetricsCacheCollection;
import org.jam.metrics.applicationmetricsproperties.MetricProperties;
import org.junit.Test;
import org.jboss.eap.additional.testsuite.annotations.EapAdditionalTestsuite;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
@EapAdditionalTestsuite({"modules/testcases/jdkAll/master/jam/src/main/java"})
public class ApplicationMetricsJavaSeApi8Test {

    private String groupName = "myTestGroup";
    
    @Test
    public void test() {
        try {
            initializeMetricProperties();
            MetricsApiSeTestClass8 mTC = new MetricsApiSeTestClass8();
            MetricsThreads8 mTreads =  new MetricsThreads8("1",mTC);
            mTreads.start();
         
            MetricsThreads8 mTreads2 =  new MetricsThreads8("2",mTC);
            mTreads2.start();
            
            MetricsThreads8 mTreads3 =  new MetricsThreads8("3",mTC);
            mTreads3.start();
            
            while (mTreads.getT().isAlive() || mTreads2.getT().isAlive() || mTreads3.getT().isAlive()){};
            
            if (MetricsCacheCollection.getMetricsCacheCollection().getMetricsCacheInstance(groupName)!=null)
                System.out.println(MetricsCacheApi.printMetricsCache(groupName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void initializeMetricProperties() {
        MetricProperties metricProperties = new MetricProperties();
        metricProperties.setHawkularMonitoring("false");
        metricProperties.setHawkularMonitoringRefreshRate(100);
        metricProperties.setHawkularTenant("hawkular");
        metricProperties.setHawkularServerUsername("hawkular");
        metricProperties.setHawkularServerPassword("hawkular");
        metricProperties.setCacheStore("true");
        metricProperties.setCacheMaxSize(10000);
        CodeParamsApi.addUserName("Niki");
        MetricsPropertiesApi.storeProperties(groupName, metricProperties);
    }
    
}
