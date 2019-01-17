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
package org.jam.metrics.applicationmetricsjavaseapitest14;

import org.jam.metrics.applicationmetricsapi.JMathPlotAdapter;
import org.jam.metrics.applicationmetricslibrary.DeploymentMetricProperties;
import org.jam.metrics.applicationmetricsproperties.MetricProperties;
import org.jboss.eap.additional.testsuite.annotations.EapAdditionalTestsuite;

/**
 *
 * @author Panagiotis Sotiropoulos
 */
@EapAdditionalTestsuite({"modules/testcases/jdkAll/master/jam/src/main/java"})
public class MetricsApiSeTestClass14 {

    private double count[][];
    
    private double count2[][];
    
    private double count3[][];
    
    private double count4[][];
    
    final MetricProperties properties;

    public MetricsApiSeTestClass14() {
        properties = DeploymentMetricProperties.getDeploymentMetricProperties().getDeploymentMetricProperty("myTestGroup");
    }

    public void countMethod() throws IllegalArgumentException, IllegalAccessException {
        count = new double[10][2];
        count2 = new double[10][2];
        count3 = new double[10][3];
        count4 = new double[10][4];
        
        for(int i=0; i<10; i++) {
            for(int j=0; j<2; j++) {
                count[i][j]=i*j+Math.random()*5;
                count2[i][j]=i*j +2*i+Math.random()*5;
            }
            
            for(int j=0; j<3; j++) {
                if(j<2)
                    count3[i][j]=i*j+Math.random()*5;
                else
                    count3[i][j]=1;
            }
            
            for(int j=0; j<4; j++) {
                if(j<2)
                    count4[i][j]=i*j+Math.random()*5;
                else
                    count4[i][j]=1;
            }
        }
        
        JMathPlotAdapter.jMathPlotAdapter(count4, "myTestGroup", properties, "plot14", "count4", "red", "box", false);
        JMathPlotAdapter.jMathPlotAdapter(count, "myTestGroup", properties, "plot15", "count", "blue", "bar", false);
        JMathPlotAdapter.jMathPlotAdapter(count2, "myTestGroup", properties, "plot15", "count2", "red", "bar", false);
        JMathPlotAdapter.jMathPlotAdapter(count, "myTestGroup", properties, "plot16", "count", "red", "scatter", false);
        JMathPlotAdapter.jMathPlotAdapter(count2, "myTestGroup", properties, "plot16", "count2", "green", "scatter", false);
        JMathPlotAdapter.jMathPlotAdapter(count2, "myTestGroup", properties, "plot17", "count2", "yellow", "stair", false);
        JMathPlotAdapter.jMathPlotAdapter(count, "myTestGroup", properties, "plot17", "count", "green", "stair", false);
        JMathPlotAdapter.jMathPlotAdapter(count3, "myTestGroup", properties, "plot18", "count3", "magenta", "histogram", false);
        JMathPlotAdapter.jMathPlotAdapter(count, "myTestGroup", properties, "plot19", "count", "cyan", "line", false);
        JMathPlotAdapter.jMathPlotAdapter(count2, "myTestGroup", properties, "plot19", "count2", "magenta", "line", false);
    }

}
