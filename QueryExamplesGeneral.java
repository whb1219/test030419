/*
 * QueryExamplesGeneral.java
 * 
 * The contents of this file are subject to the terms of Atlantec-es' Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 * 
 * You can obtain a copy of the License at http://www.atlantec-es.com/aes-ddl.html
 * or http://www.atlantec-es.com/aes-ddl.txt.
 * 
 * Copyright (C) 1999-2018, Atlantec Enterprise Solutions GmbH
 */
package LCT;

import java.util.List;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.atlantec.binding.POID;
import org.atlantec.binding.erm.CatalogueItem;
import org.atlantec.binding.erm.ProductComponent;
import org.atlantec.db.Session;
import org.atlantec.directory.ConnectionMode;
import org.atlantec.directory.InformationDirectory;
import org.atlantec.objects.FilterExpression;
import org.atlantec.objects.ObjectManager;
import org.atlantec.objects.Query;
import org.atlantec.util.logging.LoggingSystem;
import static org.atlantec.util.logical.LogicalOperator.*;
import org.jscience.economics.money.Currency;
import static org.jscience.economics.money.Currency.EUR;


/**
 * 2.QueryExamplesGeneral.java --> code showing how Queries against the generated database can be executed by the help of the so called ObjectManager I've already mentioned
 * 
To execute a Query the following steps need to be done:
- Start Session
- Init ObjectManager
- Create query
- Set search mode
- Set search filter
- Execute query
- Do processing with query result
- Close session

Queries can be executed in three different modes:
- Persistent:
* Search in the data base
* Search does not include uncommitted local changes
* Search filter can only be set on indexed attributes (f.e. POID, Parent, Type, TypeX, CommonName, ...)
- Local: Searches the local application memory for objects
* Setting filters on every attribute (f.e. Length, weight) of the object is possible
* Loading clustered objects
* Does not include changes in the data base probably committed by another user at the same time
- Full:
* Persistent and local search
* If there are two different versions of an InformationObject in the data base and in application memory the latest version of the object is returned
 */
public class QueryExamplesGeneral
{
    static private final Logger log = Logger.getLogger(QueryExamplesGeneral.class);

    private final Session session;
    private final ObjectManager objectManager;

    /**
     * create a new instance of QueryExamplesGeneral
     */
    public QueryExamplesGeneral()
    {
        super();

        // init logging
        LoggingSystem.setUp();
        Logger.getRootLogger().setLevel(Level.INFO);

        // init reference currency, otherwise working with cost-based measures will fail.
        Currency.setReferenceCurrency(EUR);
        String connectionURL = "memory://localhost/dc=test";
        String username = "tgadmin";
        char[] pw = "secret".toCharArray();
        String contextName = "TestProject";
        String productName = "TestShip";

        session = Session.newSession(connectionURL, username, pw, ConnectionMode.PROJECT_MODE, contextName, productName);
        objectManager = session.getManager();
    }


    public static void main(String args[])
    {
        QueryExamplesGeneral queryExamplesGeneral = new QueryExamplesGeneral();
        queryExamplesGeneral.run();
        queryExamplesGeneral.shutdown();
    }

    public void run()
    {
        persistentQueryWithoutFilter();
        persistentQueryWithSimpleFilter();
        persistentQueryWithCombinedFilter();
        persistentQueryWilcardFilter();
        localQueryWithFilterOnNonIndexedAttribute();
        localQueryWithCombinedFilterOnNonIndexedAttribute();
        queryProductComponentsHavingLNGengine();
    }

    public void shutdown()
    {
        if (session != null) {
            session.close();
        }

        InformationDirectory.shutdown();
    }

    //Loads the product components and catalogue items for: engine and battery
    private void persistentQueryWithoutFilter()
    {
        log.info("******persistentQueryWithoutFilter");
        // create Query for getting ProductComponents
        Query query = objectManager.newQuery(ProductComponent.class);
        // set search mode to PERSISTENT ()
        query.setMode(Query.PERSISTENT);
        // execute the Query, retrieve a List of typed POIDs of existing product components within database
        //POID is the object unique identifier
        List<POID<ProductComponent>> result = (List<POID<ProductComponent>>) query.execute();
        log.info("Found " + result.size() + " Product Components:");
        for (POID<ProductComponent> poid: result) {
            // load the ProductComponent by the use of ObjectManager and the already retreived POID
            ProductComponent productComponent = objectManager.getObjectById(poid);
            log.info(productComponent.getCommonName());
        }
        
        
       
    }

    /**
     * List of filter operators:
    // String
    STARTS_WITH, ENDS_WITH, MATCHES,
    NOT_STARTS_WITH, NOT_ENDS_WITH, NOT_MATCHES,
    // String and Collection
    CONTAINS, NOT_CONTAINS,
    // any type
    EQUALS, NOT_EQUALS,
    // String, Number, Date
    LESS, BEFORE, LESS_OR_EQUAL, NOT_GREATER, GREATER, AFTER, GREATER_OR_EQUAL, NOT_LESS, EXACT, APPROXIMATELY,
    // Date
    SAME_DAY,
    // Range, Date Range
    BETWEEN, EXCEPT,
    // type comparison
    IS_A, IS_NOT_A,
    // Boolean
    AND, OR, XOR, NOT_AND, NOT_OR, NOT_XOR,
    // masks
    BIT_AND, BIT_OR;
     */
    private void persistentQueryWithSimpleFilter()
    {
        log.info("******persistentQueryWithSimpleFilter");
        // create a FilterExpression for getting the Engine having the name "EngineXYZ"
        FilterExpression filter = new FilterExpression(ProductComponent.COMMONNAME, EXACT, "EngineXYZ");
        // create a filtered Query for getting Product components
        Query query = objectManager.newQuery(ProductComponent.class, filter);
        // set search mode to PERSISTENT
        query.setMode(Query.PERSISTENT);
        // execute the Query, retrieve a List of typed POIDs
        List<POID<ProductComponent>> result = (List<POID<ProductComponent>>) query.execute();
        log.info("Found " + result.size() + " Engine");
        for (POID<ProductComponent> poid: result) {
            // load the engine ProductComponent
            ProductComponent engine = objectManager.getObjectById(poid);
            log.info(engine.getCommonName());
        }

            // create Query for getting CatalogueItems and to load them into memory in order to enbale the local search for the
        //non indexed attribute CatalogueItem.PROPERTIES afterwards
        query = objectManager.newQuery(CatalogueItem.class);
        // set search mode to PERSISTENT ()
        query.setMode(Query.PERSISTENT);
        // execute the Query, retrieve a List of typed POIDs of existing catalogue items within database
        //POID is the object unique identifier
        List<POID<CatalogueItem>> result2 = (List<POID<CatalogueItem>>) query.execute();
        log.info("Found " + result2.size() + " CatalogueItem:");
        for (POID<CatalogueItem> poid: result2) {
            // load the CatalogueItem by the use of ObjectManager and the already retreived POID
            CatalogueItem catalogueItem = objectManager.getObjectById(poid);
            log.info(catalogueItem.getCommonName());
        }
    }

    private void persistentQueryWithCombinedFilter()
    {
         log.info("******persistentQueryWithCombinedFilter");
        // create FilterExpressions for COMMONNAME starting with Eng and ending with Z
        FilterExpression f1 = new FilterExpression(ProductComponent.COMMONNAME, STARTS_WITH, "Eng");
        FilterExpression f2 = new FilterExpression(ProductComponent.COMMONNAME, ENDS_WITH, "Z");
        FilterExpression filter = new FilterExpression(f1, "&&", f2);

        // create Query for getting Product components
        Query query = objectManager.newQuery(ProductComponent.class, filter);
        // set search mode to PERSISTENT
        query.setMode(Query.PERSISTENT);
        // execute the Query, retrieve a List of typed POIDs
        List<POID<ProductComponent>> result = (List<POID<ProductComponent>>) query.execute();
        log.info("Found " + result.size() + " Engine, COMMONNAME starting with Eng and ending with Z:");
        for (POID<ProductComponent> poid: result) {
            // load the Engine
            ProductComponent engine = objectManager.getObjectById(poid);
            log.info(engine.getCommonName());
        }

    }


    private void persistentQueryWilcardFilter()
    {
        log.info("******persistentQueryWilcardFilter");
        // create FilterExpressions filtering on COMMONNAME staring with Eng and ENDING with Z
        FilterExpression filter = new FilterExpression(ProductComponent.COMMONNAME, MATCHES, "Eng\\w*Z");
        // create Query for getting Product components
        Query query = objectManager.newQuery(ProductComponent.class, filter);
        // set search mode to PERSISTENT
        query.setMode(Query.PERSISTENT);
        // execute the Query, retrieve a List of typed POIDs
        List<POID<ProductComponent>> result = (List<POID<ProductComponent>>) query.execute();
        log.info("Found " + result.size() + " Engine, COMMONNAME starting with Eng and ending with Z:");
        for (POID<ProductComponent> poid: result) {
            // load the Engine
            ProductComponent engine = objectManager.getObjectById(poid);
            log.info(engine.getCommonName());
        }

    }

    /**
     * To perform a query on a non indexed attribute (these are all attributes which do not derive from information_object -->
     * see data model):
     * - First perform a PERSISTENT query to load the InformationObjects into memory.
     * - Then perform a LOCAL query with a filter on the attribute.
     * In this example the none indexed attribute is the "mass" attribute of the product component.
     * Note: the mass attribute of product component engine and also battery was not set during the creation of it directly
     * (see "LCAdataCreation.java"). Instead of using the direct attribute mass the attribute catalogue_item_ref of the product
     * components was set which references a standard catalogue item containing the mass information. This was done in order to
     * show how standard parts can be managed by the use of catalogue items containing defualt values which can be rewritten afterwards.
     * Nevertheless, the following example shows how it would be possible to search for the non indexed "mass" attribute of the product component engine if it would be set directly.
     * Whereas in this case, as we did not set the mass attribute, we search for product components having mass=null.
     */
    private void localQueryWithFilterOnNonIndexedAttribute()
    {
        log.info("******localQueryWithFilterOnNonIndexedAttribute");
        // Execute PERSISTENT search for loading objects into application memory
        persistentQueryWithoutFilter();

        // Execute LOCAL search for fitering objects
        // create a FilterExpression for search of Product components with MASS==null
        FilterExpression filter = new FilterExpression(ProductComponent.MASS, EQUALS , null);

        // create a filtered Query for getting Plates
        Query query = objectManager.newQuery(ProductComponent.class, filter);
        // set search mode to Local
        query.setMode(Query.LOCAL);
        // execute the Query, retrieve a List of typed POIDs
        List<POID<ProductComponent>> result = (List<POID<ProductComponent>>) query.execute();
        log.info("Found " + result.size() + " Product components with MASS == null:");
        for (POID<ProductComponent> poid: result) {
            // load the Engine
            ProductComponent productComponent = objectManager.getObjectById(poid);
        
            log.info(productComponent.getCommonName() + " - " + productComponent.getMass());
        }

    }


    private void localQueryWithCombinedFilterOnNonIndexedAttribute()
    {
        log.info("******localQueryWithCombinedFilterOnNonIndexedAttribute");
        // Execute PERSISTENT search for loading objects into application memory
        persistentQueryWithoutFilter();

        // Execute LOCAL search for fitering objects
        // create a FilterExpression for filtering on Plates of type BENT_PLATE
        FilterExpression f1 = new FilterExpression(ProductComponent.MASS, EQUALS, null);
        // create a FilterExpression for filtering on Plates of type BENT_PLATE
        FilterExpression f2 = new FilterExpression(ProductComponent.VOLUME, EQUALS, null);
        // create a FilterExpression for search of Plates with MATERIALTHICKNESS > 0.015 m
        FilterExpression f3 = new FilterExpression(ProductComponent.DIMENSIONS, EQUALS, null);
        // combine the filters f1 and f2
        FilterExpression f4 = new FilterExpression(f1, "&&", f2);
        // combine the filters f3 and f4
        FilterExpression filter = new FilterExpression(f3, "&&", f4);
        // create a filtered Query for getting Plates
        Query query = objectManager.newQuery(ProductComponent.class, filter);
        // set search mode to PERSISTENT
        query.setMode(Query.LOCAL);
        // execute the Query, retrieve a List of typed POIDs
        List<POID<ProductComponent>> result = (List<POID<ProductComponent>>) query.execute();
        log.info("Found " + result.size()
                + " Product components with mass=null, volume=null abd dimensions=null:");
        for (POID<ProductComponent> poid: result) {
            // load the Plate
            ProductComponent productComponent = objectManager.getObjectById(poid);
          // Measure<Double, Length> materialThickness = Measure.valueOf(plate.getMaterialThickness().doubleValue(), SI.METER);
            log.info(productComponent.getCommonName() + " - " + productComponent.getMass() + " - " + productComponent.getVolume()+ " - " + productComponent.getDimensions());
        }

    }

    private void queryProductComponentsHavingLNGengine()
    {
        log.info("******queryProductComponentsHavingLNGengine");
        //load product components and catalogue items into the memory in order to enable the search for non indexed attributes
        //which is "catalogueitemref" regarding the product component and "properties" regarding catalogueitem.
        persistentQueryWithSimpleFilter();

        //create a FilterExpression for getting the Engines of having the type LNG
        FilterExpression filter = new FilterExpression(ProductComponent.CATALOGUEITEMREF + "." +CatalogueItem.PROPERTIES, CONTAINS, "engineType:LNG");
        // create a filtered Query for getting Product components
        Query query = objectManager.newQuery(ProductComponent.class, filter);

        // set search mode to LOCAL
        query.setMode(Query.Mode.LOCAL);
        // execute the Query, retrieve a List of typed POIDs
        List<POID<ProductComponent>> result = (List<POID<ProductComponent>>) query.execute();
        log.info("Found " + result.size() + " Engine");
        for (POID<ProductComponent> poid: result) {
            // load the engine ProductComponent
            ProductComponent engine = objectManager.getObjectById(poid);
            log.info(engine.getCommonName()+ "having the engine type=LNG");
        }         
    }


}   // class QueryExamplesGeneral //

