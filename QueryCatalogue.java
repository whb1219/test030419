/*
 * ReadCatalogue.java
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
import java.util.Set;
import static java.util.stream.Collectors.joining;
import javax.measure.quantity.Quantity;
import javax.measure.unit.Unit;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.atlantec.binding.POID;
import org.atlantec.binding.erm.Catalogue;
import org.atlantec.binding.erm.CatalogueItem;
import org.atlantec.binding.erm.CatalogueItemClass;
import org.atlantec.binding.erm.CatalogueItemInstance;
import org.atlantec.binding.erm.CatalogueProperty;
import org.atlantec.binding.erm.InformationObject;
import org.atlantec.binding.erm.KeyValue;
import org.atlantec.binding.erm.ValueType;
import org.atlantec.catalogue.CatalogueManager;
import org.atlantec.db.Session;
import org.atlantec.directory.ConnectionMode;
import org.atlantec.directory.InformationDirectory;
import org.atlantec.jeb.ObjectNotFoundException;
import org.atlantec.objects.FilterExpression;
import org.atlantec.objects.ObjectManager;
import org.atlantec.util.CollectionsHelper;
import org.atlantec.util.StringHelper;
import org.atlantec.util.logging.LoggingSystem;
import static org.atlantec.util.logical.LogicalOperator.CONTAINS;

/**
 * 4. QueryCatalogue.java --> code showing how to retrieve Information from catalogue containing standard equipment etc. 
 * Here the so called CatalogueManager can be used to retrieve the information, whereas behind the scenes the ObjectManager is still used.
 * 
 * a sample class to demonstrate access to Catalogue data stored on Information Server via the Session and Catalogue API
 */
public class QueryCatalogue
{
    private static final Logger log = Logger.getLogger(QueryCatalogue.class);

    private final Session session;
    private final ObjectManager objectManager;

    /**
     * create a new instance of QueryCatalogue
     */
    public QueryCatalogue()
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

    /**
     * accept the typical connection args
     * @param args the command line arguments
     */
//    public static void main(String[] args)
//    {
//        // create a ReadCatalogue object
//        QueryCatalogue readCatalogue = new QueryCatalogue();
//        // run sample method
//        readCatalogue.run();
//        // clean up; if you forget this, the application may not stop or release all resources
//        readCatalogue.shutdown();
//    }

    public void shutdown()
    {
        if (session != null) {
            session.close();
        }

        InformationDirectory.shutdown();
    }

    public void run()
    {
        CatalogueManager cm = CatalogueManager.getInstance(objectManager);
        Set<POID<Catalogue>> catPOIDs = cm.list();
        if (CollectionsHelper.isNullOrEmpty(catPOIDs)) {
            throw new ObjectNotFoundException("no catalogues defined in context");
        }
        Catalogue cat = cm.open(catPOIDs.iterator().next());

        log.info("Catalogue " + cat.getPathName());
        Set<CatalogueItemClass> classes = cat.getClasses();
        log.info(classes.size() + " classes");
        Set<CatalogueItemClass> topLevelClasses = cat.getTopLevelClasses();
        log.info("top level classes : " + topLevelClasses.stream().map(InformationObject::getCommonName).collect(joining(", ")));
        Set<CatalogueProperty> properties = cat.getProperties();
        log.info(properties.size() + " properties");
        Set<CatalogueItemInstance> items = cat.getItems();
        log.info(items.size() + " items");

        Set<CatalogueItemClass> engineClasses = cm.findClass(cat, POID.STRUCTDIVIDER + "ENGINE");
        log.info(engineClasses.size() + " classes named ...ENGINE: " + engineClasses.stream()
                .map(InformationObject::getPathName).collect(joining(", ")));
        if (!engineClasses.isEmpty()) {
            CatalogueItemClass engineClass = engineClasses.iterator().next();
            Catalogue catalogue = engineClass.getCatalogue();
            log.info("ENGINE class " + engineClass.getPathName() + " in Catalogue " + catalogue.getPathName());

            List<CatalogueItemClass> parentClassPath = engineClass.getParentClassPath();
            log.info("parent classes: " + parentClassPath.stream().map(InformationObject::getCommonName).collect(
                    joining(" --- ")));

            Set<CatalogueProperty> engineClassProps = engineClass.getAllProperties();
            for (CatalogueProperty engineClassProp: engineClassProps) {
                ValueType dataType = engineClassProp.getDataType();
                String defaultValue = engineClassProp.getDefaultValue();
                if (dataType == ValueType.T_MEASURE) {
                    Unit<? extends Quantity> unit = null;
                    if (defaultValue != null) {
                        unit = Unit.valueOf(defaultValue);
                    }
                    log.info("property " + engineClassProp.getCommonName() + ", type " + dataType + " unit " + unit);
                } else {
                    log.info("property " + engineClassProp.getCommonName() + ", type " + dataType
                            + (StringHelper.isNotEmpty(defaultValue) ? " defaultValue " + defaultValue : ""));
                }
            }
            log.info("properties: " + engineClassProps.stream().map(InformationObject::getCommonName).collect(joining(", ")));

            Set<CatalogueItem> engineItems = engineClass.getItems();
            log.info(engineItems.size() + " items of exactly this class");

            CatalogueItem engineItem = engineItems.iterator().next();

            Set<KeyValue> engineItemProps = engineItem.getProperties();

            log.info("Properties of the Engine Item");
            for (KeyValue engingeItemProp: engineItemProps) {
                log.info("Parameter name =" + engingeItemProp.getKey() + ", Parameter type =" + engingeItemProp.getTypeX()
                        + ", Parameter value =" + engingeItemProp
                                .getValue() + "\n");
            }

            Set<CatalogueItemInstance> engines = cm.queryItem(engineClass, new FilterExpression(CatalogueItemInstance.PROPERTIES, CONTAINS, "engineType:LNG"));

            log.info(engines.size() + " items with property 'engineType' = LNG");

        }

    }

}  // class ReadCatalogue //
