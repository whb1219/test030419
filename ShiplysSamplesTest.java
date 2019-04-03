/*
 * ShiplysSamplesTest.java
 * 
 * @date	 26.07.2018
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

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import javax.measure.unit.NonSI;
import javax.ws.rs.core.UriBuilder;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.atlantec.binding.erm.ErmFactory;
import org.atlantec.binding.erm.LightShipMass;
import org.atlantec.binding.erm.MassAndCentreOfGravity;
import org.atlantec.binding.erm.MassItem;
import org.atlantec.binding.erm.MassMeasure;
import org.atlantec.binding.erm.Ship;
import org.atlantec.jeb.ObjectSet;
import org.atlantec.jeb.tgeb.TGEBObjectSetImplFactory;
import org.atlantec.rest.client.data.DataClient;
import org.atlantec.rest.model.data.DbContext;
import org.atlantec.rest.model.data.DbExtent;
import org.atlantec.rest.model.data.DbObject;
import org.atlantec.rest.model.data.DbSource;
import org.atlantec.util.CollectionsHelper;
import org.atlantec.util.TimeStamp;
import org.atlantec.util.logging.LoggingSystem;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 */
public class ShiplysSamplesTest
 {
    static private final Logger log = Logger.getLogger(ShiplysSamplesTest.class);
    
    /* It is assumed that the data service is running on the local machine at port 10005 in debug_notauth_notls mode
    *  (this will all be the case if the data-service is started with the example runscript provided at
    /  samples/runscripts/data/run-....)
    */
    private static final URI dataServiceURI = UriBuilder.fromUri("http://localhost:10005/data/v1").build();
    private static final String applicationName = "Java Standalone Test Application";
    private static final String applicationDescription = "a fictional application";
    private static final String applicationRevision="1";
    private static final String revision="1";


    public ShiplysSamplesTest() {
    }

    @BeforeClass
    public static void setUpClass()
    {
        LoggingSystem.setUp();
        Logger.getRootLogger().setLevel(Level.INFO);
    }

    @AfterClass
    public static void tearDownClass()
    {
    }

    @Before
    public void setUp()
    {
    }

    @After
    public void tearDown()
    {
    }

    /**
     * Test of main method, of class ShiplysSamples.
     */
    @Test
    public void testWriteHullHeightToShip() throws IOException
    {
        /*
        It is assumed that at this point the data service is running
        with the uri that is specified in dataServiceURI (line40)
         */

        //the second argument is null since the ssl-config is only needed if security is switched on
        DataClient dataClient = DataClient.getInstance(dataServiceURI, null);

        /*
        1.) select the context we would like to work with. Contexts can either be projects or organizations
        1.1) get a list of all available contexts from the data service
         */
        List<DbContext> contexts = dataClient.getDbContexts();
        contexts.forEach(c->log.info("There is a context with the name " + c.getContextName())); //print all context names
        
        /*
        1.2) select the context with the name Project1 (since we want to work in that context)
         */
        DbContext ctxProject1 = contexts.stream()
                .filter(c->c.getContextName().equals("Project1"))
                .findFirst().get();

        /*
        2.) Get or create the DbSource for our application
        DbSources are unique identifiers for applications that store data at the data service
        */
        DbSource testAppSource = null;  //the DbSource for the example application
        List<DbSource> sources = dataClient.getDbSources(ctxProject1.getDbInstance().getId());
        if (sources == null) {
            /*
            this must currently be checked because empty collections are currently not correctly delivered.
            We are working on fixing this. 
            */
            testAppSource = dataClient.createDataSource(ctxProject1, applicationDescription, applicationName,
                    applicationRevision,
                    revision);
        } else {
            Optional<DbSource> optSource = sources
                    .stream()
                    .filter(dbs -> dbs.getApplication().equals(applicationName))
                    .findFirst();
            if (optSource.isPresent()) {
                testAppSource = optSource.get();
            } else {
                testAppSource = dataClient.createDataSource(ctxProject1, applicationDescription, applicationName,
                        applicationRevision,
                        revision);
            }
        }

        /*
        2.) get the Ship with the name "Project1"
        2.1) is there already an extent of type Ship?
         */
        boolean shipExists = true;        //we assume the ship "Project1" already exists
        Ship shipProject1 = null;       //the ship "Project1"

        List<DbExtent> allExtents = dataClient.getExtents(ctxProject1);
        if (allExtents == null) {
            shipExists = false; //se line 126
        } else {
            Optional<DbExtent> optXtntShip = allExtents.stream()
                    .filter(e -> e.getTypeName().equals("Ship"))
                    .findFirst();
            if (!optXtntShip.isPresent()) {
                /*
                if optXtntShip is not present, it means there is no ship in the context
                which means we have to create the ship "Project1"
                 */
                shipExists = false;
            } else {
                /*
                this means there is at least one ship in the context,
                but we do not yet know if the ship "Project1" exists
                 */
                /*
                get all existing ships and check if there is one ship with the name "Project1";
                 */
                List<DbObject> existingShips = dataClient.getObjects(optXtntShip.get());
                Optional<DbObject> optShipProject1 = existingShips.stream()
                        .filter(s -> s.getName().equals("Project1"))
                        .findFirst();
                if (optShipProject1.isPresent()) {
                    shipProject1 = (Ship) dataClient.getObject(optShipProject1.get());
                } else {
                    shipExists = false;
                }
            }
        }

        /*
        2.2) create the ship, if it does not exist
        */
        if(!shipExists){
            /*
            create the ship "Project1"
             */
            ObjectSet os = ObjectSet.getInstance(
                    TGEBObjectSetImplFactory.implName, "Project1", ObjectSet.OpMode.REPLICA,
                    ObjectSet.VersioningModel.DEFAULT, null);
            String rcid = testAppSource.getId();
            shipProject1 = ErmFactory.create(Ship.class, os, rcid, "Project1", TimeStamp.currentTimeVersionString());
            shipProject1.setCommonName("Project1");
            /*
            POST ship to data service
            */
            Ship postedShip = dataClient.createObjectVersion(shipProject1, ctxProject1, testAppSource);
        }

        /*
        3) set the hull weight attribute
        in order to comply with the data model, a solution is to create a LightShipMass object with the ship as parent
        then create a MassItem with the name "HullMass" with the LightShipMass as parent
        then create a MassAndCentreOfGravity object that has the MassMeasure as mass attribute
        and assign it to the the MassItem HullMass
         */
        double massInTon = 3600.0;
        MassMeasure hullMassMeasure = new MassMeasure(massInTon, NonSI.METRIC_TON);
        String rcid = testAppSource.getId();
        
        /*
        3.1) creating LightShipWeight object and assigning it to the ship shipProject1
        by making shipProject1 its parent
        */
        LightShipMass lightShipMass = ErmFactory.create(LightShipMass.class, ObjectSet.getDefaultObjectSet(),rcid, "Light Ship Mass", TimeStamp.currentTimeVersionString());
        lightShipMass.setCommonName("LightShipMass");
        lightShipMass.setParents(CollectionsHelper.newSet(shipProject1));
        
        /*
        3.2) creating a MassItem object with the name "HullMass" and assigning it to mass item hierarchy lightShipMass
        by making lightShipMass its parent
        */
        MassItem hullMassItem = ErmFactory.create(MassItem.class, ObjectSet.getDefaultObjectSet(), rcid,"HullMass",TimeStamp.currentTimeVersionString());
        hullMassItem.setCommonName("HullMass");
        hullMassItem.setParents(CollectionsHelper.newSet(lightShipMass));
        
        /*
        3.3) creating a MassAndCentreOfGravity object with the given mass and assigning it to the MassAndCog attribute of hullMassItem
        by making lightShipMass its parent
        */
        MassAndCentreOfGravity hullMassAndCOG =ErmFactory.create(MassAndCentreOfGravity.class, ObjectSet.getDefaultObjectSet(), rcid,"HullMassMandCOG",TimeStamp.currentTimeVersionString());
        hullMassAndCOG.setMass(hullMassMeasure);
        hullMassItem.setMassAndCog(hullMassAndCOG);

        /*
        4) POST the data
         */
        LightShipMass postedLSM = dataClient.createObjectVersion(lightShipMass, ctxProject1, testAppSource);
        MassItem postedMI = dataClient.createObjectVersion(hullMassItem, ctxProject1, testAppSource);
    }

}   // class ShiplysSamplesTest //

