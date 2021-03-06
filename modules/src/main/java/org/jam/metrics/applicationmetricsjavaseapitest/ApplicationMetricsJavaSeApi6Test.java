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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
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
public class ApplicationMetricsJavaSeApi6Test {

    private String groupName = "myTestGroup";
    
    @Test
    public void test() {
        try {
            initializeMetricProperties();
            MetricsApiSeTestClass6 mTC = new MetricsApiSeTestClass6();
            MetricsThreads6 mTreads =  new MetricsThreads6("1",mTC);
            mTreads.start();
         
            MetricsThreads6 mTreads2 =  new MetricsThreads6("2",mTC);
            mTreads2.start();
            
            MetricsThreads6 mTreads3 =  new MetricsThreads6("3",mTC);
            mTreads3.start();
            
            while (mTreads.getT().isAlive() || mTreads2.getT().isAlive() || mTreads3.getT().isAlive()){};
            
            if (MetricsCacheCollection.getMetricsCacheCollection().getMetricsCacheInstance(groupName)!=null)
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
        metricProperties.setRhqServerUrl("lz-panos-jon33.bc.jonqe.lab.eng.bos.redhat.com");
        metricProperties.setRhqScheduleIds(rhqScheduleIds);
        metricProperties.setDatabaseStore("false");
        metricProperties.setUpdateRateOfDbQuery("StoreDBMetric", 3);
        CodeParamsApi.addUserName("Niki");
        try {
            Connection  connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306", "root", "");
            Statement stmt = connection.createStatement();
            createDbTable(stmt);
            HashMap<String,Statement> dbStmt = new HashMap<String,Statement>();
            dbStmt.put("statement_1", stmt);
            metricProperties.setDatabaseStatement(dbStmt);
            HashMap<String,String> query1 = new HashMap<String,String>();
            query1.put("StoreDBMetric", "INSERT INTO MyMETRICS.metricValues(SEQUENCE_NUM,METRIC_NAME,METRIC_VALUE,METRIC_INSTANCE,RECORD_TIME) VALUES(#sequenceNum#, '{1}', [1], '{instance}', '{time}');");
            metricProperties.setUpdateDbQueries(query1);
        } catch(Exception e) {
            e.printStackTrace();
        }
        MetricsPropertiesApi.storeProperties(groupName, metricProperties);
    }
    
    private void createDbTable(Statement stmt) {
        try {
            String query = "DROP DATABASE MyMETRICS;";
            
            try {
                stmt.executeUpdate(query);      
            } catch (Exception e) {
            }
            
            query = "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = 'MyMETRICS' AND table_name = 'metricValues'";
            ResultSet rs = stmt.executeQuery(query);                  
            rs.next();
            boolean exists = rs.getInt("COUNT(*)") > 0;
            
            if (!exists) {
                String sql = "CREATE DATABASE MyMETRICS";
                stmt.executeUpdate(sql);
                System.out.println("Database created successfully...");

                sql = sql = "CREATE TABLE MyMETRICS.metricValues(ID int NOT NULL AUTO_INCREMENT, SEQUENCE_NUM int, METRIC_NAME varchar(255) NOT NULL," +
                      " METRIC_VALUE varchar(255) NOT NULL, METRIC_INSTANCE varchar(255), RECORD_TIME DATETIME, PRIMARY KEY(ID));"; 
                
                stmt.executeUpdate(sql);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
