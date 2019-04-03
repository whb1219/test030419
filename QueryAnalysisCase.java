/*
 * QueryAnalysisCase.java
 *
 * The contents of this file are subject to the terms of Atlantec-es' Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.atlantec-es.com/aes-ddl.html
 * or http://www.atlantec-es.com/aes-ddl.txt.
 *
 * Copyright (C) 1999-2016, Atlantec Enterprise Solutions GmbH
 */
package LCT;

import java.util.List;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.atlantec.binding.POID;
import org.atlantec.binding.erm.AnalysisCase;
import org.atlantec.binding.erm.AnalysisScenario;
import org.atlantec.binding.erm.EvaluationResult;
import org.atlantec.binding.erm.KeyValue;
import org.atlantec.binding.erm.NestedKeyValue;
import org.atlantec.binding.erm.ParameterSet;
import org.atlantec.db.Session;
import org.atlantec.directory.ConnectionMode;
import org.atlantec.directory.InformationDirectory;
import org.atlantec.objects.FilterExpression;
import org.atlantec.objects.ObjectManager;
import org.atlantec.objects.Query;
import org.atlantec.util.KeyValueHelper;
import org.atlantec.util.logging.LoggingSystem;
import static org.atlantec.util.logical.LogicalOperator.EQUALS;

/**
 *3. QueryAnalysisCase.java --> code showing how to retrieve the information from the database especially regarding AnalysisScenario and the related AnalysisCase.
 */
public class QueryAnalysisCase
{

    static private final Logger log = Logger.getLogger(QueryAnalysisCase.class);

    private final Session session;
    private final ObjectManager objectManager;


    /**
     * create a new instance of QueryAnalysisCase
     */
    public QueryAnalysisCase()
    {
        super();

         // init logging
        LoggingSystem.setUp();
        Logger.getRootLogger().setLevel(Level.INFO);

        String connectionURL = "memory://localhost/dc=test";
        String username = "tgadmin";
        char[] pw = "secret".toCharArray();
        String contextName = "TestProject"; 
        String productName = "TestShip";

        session = Session.newSession(connectionURL, username, pw, ConnectionMode.PROJECT_MODE, contextName, productName);
        objectManager = session.getManager();
    }

//    public static void main(String[] args)
//    {
//
//        QueryAnalysisCase queryAnalysisCase=new QueryAnalysisCase();
//        queryAnalysisCase.runAnalysisCaseExampleRead();
//         // clean up; if you forget this, the application may not stop or release all resources
//        queryAnalysisCase.shutdown();
//    }

    public void shutdown()
    {
        if (session != null) {
            session.close();
        }

        InformationDirectory.shutdown();
    }




    public void runAnalysisCaseExampleRead()
    {
        // get the only existing analysisScenario
        Query query = objectManager.newQuery(AnalysisScenario.class);
        query.setMode(Query.PERSISTENT);
        List<POID<AnalysisScenario>> analysisScenarioPoids = (List<POID<AnalysisScenario>>) query.execute();
        AnalysisScenario analysisScenario = objectManager.getObjectById(analysisScenarioPoids.get(0));

        //get the analysis case of the analysis scenario
       FilterExpression filter = new FilterExpression(AnalysisCase.PARENTS, EQUALS, analysisScenario.getPOID());
        query = objectManager.newQuery(AnalysisCase.class, filter);
        query.setMode(Query.PERSISTENT);
        List<POID<AnalysisCase>> analysisCasePoids = (List<POID<AnalysisCase>>) query.execute();
        //load nalysis case
        AnalysisCase analysisCase = objectManager.getObjectById(analysisCasePoids.get(0));

        //---retrieve the LCA parameter sets of the analysis case
        filter = new FilterExpression(ParameterSet.PARENTS, EQUALS, analysisCase.getPOID());
        query = objectManager.newQuery(ParameterSet.class, filter);
        //since the parameter sets during the creation see "LCAdataCreation" were clustered into the analysis case and not saved autonomously,
        //the parameter sets cannot be find directly within the data base. They can be find in local mode after the related analysis case has been loaded into the memory.
        //See explanations for the different query modes in "QueryExamplesGeneral.java".
        query.setMode(Query.LOCAL);
        List<POID<ParameterSet>> parameterSetPoids = (List<POID<ParameterSet>>) query.execute();

        ParameterSet generalParameterSet = null;
        ParameterSet constructionParameterSet = null;
        ParameterSet operationParameterSet = null;
        ParameterSet scrappingParameterSet = null;
        for (int i=0; i<parameterSetPoids.size();i++){
            POID<ParameterSet> parameterSetPoid=parameterSetPoids.get(i);
            switch (parameterSetPoid.getTrailingNamePart()) {
                case "GeneralParameters":
                    generalParameterSet=objectManager.getObjectById(parameterSetPoid);
                    break;
                case "ConstructionParameters":
                    constructionParameterSet=objectManager.getObjectById(parameterSetPoid);
                    break;
                case "OperationParameters":
                    operationParameterSet=objectManager.getObjectById(parameterSetPoid);
                    break;
                case "ScrappingParameters":
                    scrappingParameterSet=objectManager.getObjectById(parameterSetPoid);
                    break;
                default:
                    break;
            }
        }

       //---log general LCA parameters of the analysis case
        log.info("******General");
        for (KeyValue kv: generalParameterSet.getParameters()) {
            log.info("Parameter name =" + kv.getKey() + ", Parameter type =" + kv.getTypeX() + ", Parameter value =" + kv
                    .getValue() + "\n");
        }

        
        //---log construction LCA parameters of the analysis case
        log.info("******Construction");
        //log the transportation and electricity representing the root nodes
        for (KeyValue kv: constructionParameterSet.getParameters()) {
            log.info("Parameter name =" + kv.getKey() + "\n");
        }

        log.info("***Transportation");
        NestedKeyValue transportationNKV= (NestedKeyValue) KeyValueHelper.getKeyValueByKey(constructionParameterSet.getParameters(), "Transportation");
        //log the transportation1 node
        for (KeyValue kv: transportationNKV.getSubParams()) {
            log.info("Sub parameter name =" + kv.getKey() + "\n");
        }

        NestedKeyValue transportation1NKV=(NestedKeyValue) KeyValueHelper.getSubParameterByKey(transportationNKV, "Transportation1");
        //log the parameters of transportation1 node
        for (KeyValue kv: transportation1NKV.getSubParams()) {
            log.info("Parameter name =" + kv.getKey() + ", Parameter type =" + kv.getTypeX() + ", Parameter value =" + kv
                    .getValue() + "\n");
        }

        log.info("***Electricity");
        NestedKeyValue electricityNKV= (NestedKeyValue) KeyValueHelper.getKeyValueByKey(constructionParameterSet.getParameters(), "Electricity");
        //log the electricity1 node
        for (KeyValue kv: electricityNKV.getSubParams()) {
            log.info("Sub parameter name =" + kv.getKey() + "\n");
        }

        NestedKeyValue electricity1NKV=(NestedKeyValue) KeyValueHelper.getSubParameterByKey(electricityNKV, "Electricity1");
        //log the parameters of electricity1 node
        for (KeyValue kv: electricity1NKV.getSubParams()) {
            log.info("Parameter name =" + kv.getKey() + ", Parameter type =" + kv.getTypeX() + ", Parameter value =" + kv
                    .getValue() + "\n");
        }


        //---log operation LCA parameters of the analysis case
        log.info("******Operation");
        for (KeyValue kv: operationParameterSet.getParameters()) {
            log.info("Parameter name =" + kv.getKey() + ", Parameter type =" + kv.getTypeX() + ", Parameter value =" + kv
                    .getValue() + "\n");
        }
      
        log.info("***Fuel consumption");
        NestedKeyValue fuelConsumptionNKV= (NestedKeyValue) KeyValueHelper.getKeyValueByKey(operationParameterSet.getParameters(), "Fuel consumption");
        //log the fuelconsumption1 node
        for (KeyValue kv: fuelConsumptionNKV.getSubParams()) {
            log.info("Sub parameter name =" + kv.getKey() + "\n");
        }

        NestedKeyValue fuelConsumption1NKV=(NestedKeyValue) KeyValueHelper.getSubParameterByKey(fuelConsumptionNKV, "FuelConsumption1");
        //log the parameters of fuelconsumption1 node
        for (KeyValue kv: fuelConsumption1NKV.getSubParams()) {
            log.info("Parameter name =" + kv.getKey() + ", Parameter type =" + kv.getTypeX() + ", Parameter value =" + kv
                    .getValue() + "\n");
        }

        log.info("***Lub consumption");
        NestedKeyValue lubConsumptionNKV= (NestedKeyValue) KeyValueHelper.getKeyValueByKey(operationParameterSet.getParameters(), "Lub consumption");
        //log the fuelconsumption1 node
        for (KeyValue kv: lubConsumptionNKV.getSubParams()) {
            log.info("Sub parameter name =" + kv.getKey() + "\n");
        }

        NestedKeyValue lubConsumption1NKV=(NestedKeyValue) KeyValueHelper.getSubParameterByKey(lubConsumptionNKV, "LubConsumption1");
        //log the parameters of fuelconsumption1 node
        for (KeyValue kv: lubConsumption1NKV.getSubParams()) {
            log.info("Parameter name =" + kv.getKey() + ", Parameter type =" + kv.getTypeX() + ", Parameter value =" + kv
                    .getValue() + "\n");
        }

        //---log scrapping LCA parameters of the analysis case
        log.info("******Scrapping");
        for (KeyValue kv: scrappingParameterSet.getParameters()) {
            log.info("Parameter name =" + kv.getKey() + ", Parameter type =" + kv.getTypeX() + ", Parameter value =" + kv
                    .getValue() + "\n");
        }

        log.info("***Material");
        NestedKeyValue materialNKV= (NestedKeyValue) KeyValueHelper.getKeyValueByKey(scrappingParameterSet.getParameters(), "Material");
        //log the material1 node
        for (KeyValue kv: materialNKV.getSubParams()) {
            log.info("Sub parameter name =" + kv.getKey() + "\n");
        }

        NestedKeyValue material1NKV=(NestedKeyValue) KeyValueHelper.getSubParameterByKey(materialNKV, "Material1");
        //log the parameters of fuelconsumption1 node
        for (KeyValue kv: material1NKV.getSubParams()) {
            log.info("Parameter name =" + kv.getKey() + ", Parameter type =" + kv.getTypeX() + ", Parameter value =" + kv
                    .getValue() + "\n");
        }

        //---retrieve the LCA evaluation result of the analysis case
        log.info("******Evaluation Result");
        filter = new FilterExpression(EvaluationResult.PARENTS, EQUALS, analysisCase.getPOID());
        query = objectManager.newQuery(EvaluationResult.class, filter);
        query.setMode(Query.PERSISTENT);
        List<POID<EvaluationResult>> evaluationResultPoids = (List<POID<EvaluationResult>>) query.execute();
        EvaluationResult evaluationResult = objectManager.getObjectById(evaluationResultPoids.get(0));

        //log the parameters of the evaluation result
        for (KeyValue kv: evaluationResult.getParameters()) {
            log.info("Parameter name =" + kv.getKey() + ", Parameter type =" + kv.getTypeX() + ", Parameter value =" + kv
                    .getValue() + "\n");
        }
    }



}   // class QueryAnalysisCase //
