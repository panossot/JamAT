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
package org.jam.metrics.applicationmetricsjavaseapitest16;

import org.jam.metrics.applicationmetricsapi.JMathPlotAdapter;
import org.jam.metrics.applicationmetricslibrary.DeploymentMetricProperties;
import org.jam.metrics.applicationmetricsproperties.MetricProperties;
import org.jboss.eap.additional.testsuite.annotations.EapAdditionalTestsuite;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
@EapAdditionalTestsuite({"modules/testcases/jdkAll/master/jam/src/main/java"})
public class MetricsApiSeTestClass16 {

    private double count[][];
    
    private double count2[][];
    
    final MetricProperties properties;

    public MetricsApiSeTestClass16() {
        properties = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty("myTestGroup");
    }

    public void countMethod() throws IllegalArgumentException, IllegalAccessException {
        count = new double[10][2];
        count2 = new double[10][2];
        
        for(int i=0; i<10; i++) {
            for(int j=0; j<2; j++) {
                count[i][j]=i*j+Math.random()*5;
                count2[i][j]=i*j +2*i+Math.random()*5;
            }
            
        }
        
        JMathPlotAdapter.jMathPlotAdapter(count2, "myTestGroup", properties, "plot25", "count2", "red", "scatter", false);
        JMathPlotAdapter.jMathPlotAdapter(count, "myTestGroup", properties, "plot25", "count", "green", "scatter", false);
        JMathPlotAdapter.jMathPlotAdapter(count, "myTestGroup", properties, "plot26", "count", "cyan", "line", false);
        JMathPlotAdapter.jMathPlotAdapter(count2, "myTestGroup", properties, "plot26", "count2", "magenta", "line", false);
    }

}
