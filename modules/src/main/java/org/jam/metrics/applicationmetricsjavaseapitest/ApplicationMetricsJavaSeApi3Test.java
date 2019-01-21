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
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class ApplicationMetricsJavaSeApi3Test {

    private String groupName = "myTestGroup";
    
    @Test
    public void test() {
        try {
            initializeMetricProperties();
            MetricsApiSeTestClass3 mTC = new MetricsApiSeTestClass3();
            MetricsThreads3 mTreads =  new MetricsThreads3("1",mTC);
            mTreads.start();
         
            MetricsThreads3 mTreads2 =  new MetricsThreads3("2",mTC);
            mTreads2.start();
            
            MetricsThreads3 mTreads3 =  new MetricsThreads3("3",mTC);
            mTreads3.start();
            
            while (mTreads.getT().isAlive() || mTreads2.getT().isAlive() || mTreads3.getT().isAlive()){};
            
            if (MetricsCacheCollection.getMetricsCacheCollection().getMetricsCacheInstance(groupName)!=null)
                System.out.println(MetricsCacheApi.printMetricsCache(groupName));
            
            if (MetricsCacheCollection.getMetricsCacheCollection().getMetricsCacheInstance(groupName)!=null)
                System.out.println("Count GroupObjects In MetricCache : " +  MetricsCacheApi.countGroupObjectsInMetricCache(groupName));
            
            if (MetricsCacheCollection.getMetricsCacheCollection().getMetricsCacheInstance(groupName)!=null)
                MetricsCacheApi.deleteGroupInMetricsCache(groupName);
            
            if (MetricsCacheCollection.getMetricsCacheCollection().getMetricsCacheInstance(groupName)!=null)
                System.out.println("Count GroupObjects In MetricCache : " +  MetricsCacheApi.countGroupObjectsInMetricCache(groupName));
            else
                System.out.println("Group " + groupName + " does not exist.");
            
            
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
        metricProperties.setRhqMonitoringRefreshRate(100);
        metricProperties.setRhqServerUrl("lz-panos-jon33.bc.jonqe.lab.eng.bos.redhat.com");
        metricProperties.setRhqScheduleIds(rhqScheduleIds);
        metricProperties.setDatabaseStore("false");
        MetricsPropertiesApi.storeProperties(groupName, metricProperties);
    }
    
}
