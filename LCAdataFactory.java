package LCT;

/*
 * LCAdataFactory.java
 * 
 * @date	 29.10.2018
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

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import javax.measure.unit.NonSI;
import javax.measure.unit.SI;
import org.apache.log4j.Logger;
import org.atlantec.binding.POID;
import org.atlantec.binding.erm.AnalysisCase;
import org.atlantec.binding.erm.AnalysisScenario;
import org.atlantec.binding.erm.Catalogue;
import org.atlantec.binding.erm.CatalogueItem;
import org.atlantec.binding.erm.CatalogueItemClass;
import org.atlantec.binding.erm.CatalogueItemInstance;
import org.atlantec.binding.erm.CatalogueProperty;
import org.atlantec.binding.erm.CataloguePropertyType;
import org.atlantec.binding.erm.EvaluationResult;
import org.atlantec.binding.erm.KeyValue;
import org.atlantec.binding.erm.LengthMeasure;
import org.atlantec.binding.erm.MassMeasure;
import org.atlantec.binding.erm.MoneyMeasure;
import org.atlantec.binding.erm.ParameterFromCatalogue;
import org.atlantec.binding.erm.ParameterSet;
import org.atlantec.binding.erm.PowerMeasure;
import org.atlantec.binding.erm.ProductComponent;
import org.atlantec.binding.erm.SpecificConsumptionMeasure;
import org.atlantec.binding.erm.TimeMeasure;
import org.atlantec.binding.erm.ValueType;
import org.atlantec.catalogue.CatalogueManager;
import org.atlantec.directory.DirectoryTestSetup;
import org.atlantec.directory.InformationServer;
import org.atlantec.directory.PooledPublisher;
import org.atlantec.jeb.ObjectNotFoundException;
import org.atlantec.objects.ObjectManager;
import org.atlantec.util.CollectionsHelper;
import org.atlantec.util.KeyValueHelper;
import org.atlantec.wrapper.erm.toolbox.ParameterSetToolBox;
import org.jscience.economics.money.Currency;

/**
 *
 */
public class LCAdataFactory
{
    static private final Logger log = Logger.getLogger(LCAdataFactory.class);

    static private final DirectoryTestSetup dts = new DirectoryTestSetup(LCAdataCreation.class);
    public static ObjectManager projOM;

    static{
        try {
            setUpDataBaseAndStartTransaction();
        } catch (Exception ex) {
            log.error(ex);
        }
        createCatalogue();
    }


//    /**
//     * create a new instance of LCAdataFactory
//     * @throws java.lang.Exception
//     */
//    public LCAdataFactory() throws Exception
//    {
//        super();
//        setUpDataBaseAndStartTransaction();
//        createCatalogue();
//    }

    private static void setUpDataBaseAndStartTransaction() throws Exception
    {
        log.info("setUpClass");

        dts.setDBProvider(InformationServer.Provider.MEMORY);
        dts.setTestDataLocation(null);
        dts.setUseTestDB(false);
        dts.setClearIS(true); // make sure the database is clean
        dts.setKeepIS(true);
        dts.setDumpDB(false);
        dts.setProjectName("TestProject");
        dts.setShipName("TestShip");
        //no need for organization context for this example
        dts.setOrganizationName(null);
        dts.setShipyardName(null);
        dts.setCatalogName(null);
        dts.setUp();

        // init reference currency, otherwise working with cost-based measures fill fail.
        Currency.setReferenceCurrency(Currency.EUR);

        PooledPublisher.setApplicationDetails("LCAdataCreationTest",
                LCAdataCreation.class, "1", LCAdataCreation.class, "1");

        projOM = ObjectManager.getInstance(dts.getProject());

        projOM.currentTransaction().begin();
    }

    public static void tearDownDataBase() throws Exception
    {
        if (projOM.currentTransaction().isActive()) {
            projOM.currentTransaction().rollback();
        }

        dts.tearDown();
    }

    /*Catalogue contains the description of various equipment which a shipyard manages (e.g. to order from its subcontractors)
    In order to define a new catalogue for a specific shipyard it is required to define the following entities:
    
    1. Catalogue itself having a name, which is here "TestCatalogue"
    
    2. CatalogueProperties to be managed within the catalogue, e.g. regarding Mass, Price, PowerRating etc. CatalogueProperties contain
    general meta information like name, descripiton, type and whether they are configurable.
    
    3. CatalogueItemClass represents a general representation of an equipment having a set of predefined CatalogueProperties. If several equipments have amongst others same CatalogueProperties,
    it is possible to use the inheritence mechanism in order to avoid the definition of the same CatalogueProperties for several times. That's why we create in the following example code a CatalogueItemClass
    having the general name "COMPONENT" which has the general CatalogueProperties Mass, Price, PowerRating and RH1 till RH7.
    Afterwards when we create a CatalogueItemClass for an engine and for a battery we can inherite
    these general CatalogueProperties from the general CatalogueItemClass "COMPONENT". Further engine and battery specific CatalogueProperties can be created afterwards and added to the related
    more specific CatalogueItemClass "ENGINE" and "BATTERY".
    
    4. The last step is to create CatalogueItems, which represents specific equipment and where the default values of CatalogueProperties are set.
    
    NOTE: For a specific project (represented by a new ship) it is possible afterwards to refer to the CatalogueItems within the Catalogue and create so called ProductComponents.
    ProductComponents contain all the default property values of the refered CatalogueItems but if it is required the values can be edited in the context of the current project.
    Whenever a new ProductComponent has to be defined it is better to define a new CatalogueItem and add it to the Catalogue and afterwards to refer to it.
    In this way it will be possible to reuse the infromation for future projects.
    */

    /**
     * The following method creates Catalogue containing an engine CatalogueItem and battery CatalogueItem.
     * In case further CatalogueItems are required, the code has to be extended.
     */
    private static void createCatalogue()
    {
        log.info("****** testCreateCatalogue");

        CatalogueManager catM = CatalogueManager.getInstance(projOM);
        Catalogue cat = catM.create("TestCatalogue");

        //-- Create general properties regarding Engine and Battery which are inherited: Mass, Price, PowerRating and RH1-7
        List<CatalogueProperty> commonProperties = createCommonProperties(catM, cat);

        //-- Create general catalogue_item_class "COMPONENT" having general properties
        CatalogueItemClass compCIC = catM.addItemClass(cat, "COMPONENT");
        compCIC.setProperties(commonProperties);

        //-------------- Engine
        //Create specific properties regarding Engine: Engine type, SFOC and SFOC_LO
        List<CatalogueProperty> engineProperties = createEngineProperties(catM, cat);
        //Create engine catalogue_item_class, whereas compCIC is used as parent and therefore inherits its general properties
        CatalogueItemClass engineCIC = catM.addItemClass(compCIC, "ENGINE");
        engineCIC.setProperties(engineProperties);

        //Create catalogue_item for an specific engine and add it to the catalogue
        //There can be different engine Catalogue Items
        CatalogueItem engineCI = catM.addItem(engineCIC, "MAN 6L45/60CR 10L");
        //Set the properties of the engine catalogue_item specified within the engine catalogue_item_class
        setPropertiesOfEngineCI(engineCI);

        //------------- Battery
        //Create specific properties regarding Battery: Battery type
        List<CatalogueProperty> baterryProperties = createBatteryProperties(catM, cat);
        //Create battery catalogue_item_class, whereas compCIC is used as parent and therefore inherits its general properties
        CatalogueItemClass batteryCIC = catM.addItemClass(compCIC, "BATTERY");
        batteryCIC.setProperties(baterryProperties);

        //Create catalogue item for an specific battery and add it to the catalogue
        CatalogueItem batteryCI = catM.addItem(batteryCIC, "BATTERY-XYZ");
        //Set the properties of the battery catalogue_item specified within the battery catalogue_item_class
        setPropertiesOfBatteryCI(batteryCI);

        //Save
        projOM.makePersistent(cat);
        projOM.makePersistent(engineCI);
        projOM.makePersistent(batteryCI);

        projOM.currentTransaction().commit();
    }


    /**
     * Creates new product component (e.g. engine, battery etc. for the current project)
     * @param catalogueItem to be referred and containing the default values
     * @param productName the name of the new product component to be created
     * @return
     */
    public static ProductComponent createProductComponent(CatalogueItem catalogueItem, String productName)
    {
        ProductComponent productComponent = projOM.createInformationObject(ProductComponent.class, productName);

        productComponent.setCatalogueItemRef(catalogueItem);

        return productComponent;
    }

    public static void setProductComponentsProperty(ProductComponent productComponent, String propertyName, String value)
    {
        CatalogueProperty property = getCataloguePropertyByName(productComponent.getCatalogueItemRef(), propertyName);

        if (property.getTypeX() == CataloguePropertyType.CONFIGURABLE) {
            ParameterSet parameterSet;
            if (productComponent.getParameters() == null) {
                parameterSet = ObjectManager.createClusteredObject(ParameterSet.class, productComponent,
                        "Configurable Properties");
                parameterSet.setDescription(
                        "This set contains catalogue properties which are determined to be configurable, e.g. the price.");
                parameterSet.setNameSpace("SHIPLYS Parameter definitions");
                productComponent.setParameters(parameterSet);
            } else {
                parameterSet = productComponent.getParameters();
            }

            ParameterFromCatalogue parameter = ObjectManager.createClusteredObject(ParameterFromCatalogue.class, parameterSet,
                    propertyName);
            parameter.setPropertyDefinition(property);

            parameter.setDefaultValue(value);
            if (CollectionsHelper.isNotEmpty(parameterSet.getParameters())) {
                ParameterSetToolBox.addParameter(parameterSet, parameter.getCommonName(), parameter.getDefaultValue());
            } else {
                ParameterSetToolBox.setParameters(parameterSet, Arrays.asList(parameter));
            }

//            ParameterSetToolBox.setParameters(parameterSet, Arrays.asList(parameter));
            System.out.println(parameterSet);
        } else {
            log.info("The property cannot be set as it is not configurable!");
        }
    }

    private static CatalogueProperty getCataloguePropertyByName(CatalogueItemInstance item, String propertyName){

            CatalogueProperty property = item.getAllPropertyDefinitions().stream()
                .filter(prop->prop.getCommonName().equals(propertyName))
                .findFirst().get();

            return property;
    }


    private static List<CatalogueProperty> createCommonProperties(CatalogueManager catM, Catalogue cat)
    {
        //Mass
        CatalogueProperty massP = catM.addProperty(cat, "mass");
        massP.setDataType(ValueType.T_MEASURE);
        massP.setDataSubType(MassMeasure.class.getName());
        massP.setTypeX(CataloguePropertyType.CONFIGURABLE);
        massP.setDescription("mass of item");

        //Price
        CatalogueProperty priceP = catM.addProperty(cat, "price");
        
        priceP.setDataType(ValueType.T_MEASURE);
        priceP.setDataSubType(MoneyMeasure.class.getName());
        //Here it is assumed that the price depends from supplier and this why it is set configurable
        priceP.setTypeX(CataloguePropertyType.CONFIGURABLE);
        priceP.setDescription("price of item");

        //PowerRating
        CatalogueProperty powerRatingP = catM.addProperty(cat, "powerRating");
        powerRatingP.setDataType(ValueType.T_MEASURE);
        powerRatingP.setDataSubType(PowerMeasure.class.getName());
        powerRatingP.setTypeX(CataloguePropertyType.CONFIGURABLE);
        powerRatingP.setDescription("power rating of item");

        //RH1
        CatalogueProperty rh1P = catM.addProperty(cat, "rh1");
        rh1P.setDataType(ValueType.T_DOUBLE_LIST);
        rh1P.setSize(3);
        rh1P.setDescription("risk hazard 1 of item");        rh1P.setTypeX(CataloguePropertyType.CONFIGURABLE);


        //RH2
        CatalogueProperty rh2P = catM.addProperty(cat, "rh2");
        rh2P.setDataType(ValueType.T_DOUBLE_LIST);
        rh2P.setSize(3);
        rh2P.setDescription("risk hazard 2 of item");        rh2P.setTypeX(CataloguePropertyType.CONFIGURABLE);


        //RH3
        CatalogueProperty rh3P = catM.addProperty(cat, "rh3");
        rh3P.setDataType(ValueType.T_DOUBLE_LIST);
        rh3P.setSize(3);
        rh3P.setDescription("risk hazard 3 of item");        rh3P.setTypeX(CataloguePropertyType.CONFIGURABLE);


        //RH4
        CatalogueProperty rh4P = catM.addProperty(cat, "rh4");
        rh4P.setDataType(ValueType.T_DOUBLE_LIST);
        rh4P.setSize(3);
        rh4P.setDescription("risk hazard 4 of item");        rh4P.setTypeX(CataloguePropertyType.CONFIGURABLE);


        //RH5
        CatalogueProperty rh5P = catM.addProperty(cat, "rh5");
        rh5P.setDataType(ValueType.T_DOUBLE_LIST);
        rh5P.setSize(3);
        rh5P.setDescription("risk hazard 5 of item");        rh5P.setTypeX(CataloguePropertyType.CONFIGURABLE);


        //RH6
        CatalogueProperty rh6P = catM.addProperty(cat, "rh6");
        rh6P.setDataType(ValueType.T_DOUBLE_LIST);
        rh6P.setSize(3);
        rh6P.setDescription("risk hazard 6 of item");        rh6P.setTypeX(CataloguePropertyType.CONFIGURABLE);


        //RH7
        CatalogueProperty rh7P = catM.addProperty(cat, "rh7");
        rh7P.setDataType(ValueType.T_DOUBLE_LIST);
        rh7P.setSize(3);
        rh7P.setDescription("risk hazard 7 of item");        rh7P.setTypeX(CataloguePropertyType.CONFIGURABLE);


        return Arrays.asList(massP, priceP, powerRatingP, rh1P, rh2P, rh3P, rh4P, rh5P, rh6P, rh7P);
    }

     private static List<CatalogueProperty> createEngineProperties(CatalogueManager catM, Catalogue cat)
    {
        //Engine type
        CatalogueProperty engineTypeP = catM.addProperty(cat, "engineType");
        engineTypeP.setDataType(ValueType.T_ENUM);
        engineTypeP.setDescription("type of engine");        engineTypeP.setTypeX(CataloguePropertyType.CONFIGURABLE);

        //Engine mass
        CatalogueProperty engineMass = catM.addProperty(cat, "engineMass");
        engineMass.setDataType(ValueType.T_MEASURE);
        engineMass.setDescription("mass of engine");		engineMass.setTypeX(CataloguePropertyType.CONFIGURABLE);
        //SFOC
        CatalogueProperty sfocP = catM.addProperty(cat, "sfoc");
        sfocP.setDataType(ValueType.T_MEASURE);
        sfocP.setDataSubType(SpecificConsumptionMeasure.class.getName());
        sfocP.setDescription("sfoc of engine");  			sfocP.setTypeX(CataloguePropertyType.CONFIGURABLE);

        //SFOC_LO
        CatalogueProperty sfoc_loP = catM.addProperty(cat, "sfoc_lo");
        sfoc_loP.setDataType(ValueType.T_MEASURE);
        sfoc_loP.setDataSubType(SpecificConsumptionMeasure.class.getName());
        sfoc_loP.setDescription("sfoc_lo of engine");  		sfoc_loP.setTypeX(CataloguePropertyType.CONFIGURABLE);

        return Arrays.asList(engineTypeP, sfocP, sfoc_loP);
    }

    private static List<CatalogueProperty> createBatteryProperties(CatalogueManager catM, Catalogue cat)
    {
        //Battery type
        CatalogueProperty batteryTypeP = catM.addProperty(cat, "batteryType");
        batteryTypeP.setDataType(ValueType.T_ENUM);
        batteryTypeP.setDescription("type of battery");		batteryTypeP.setTypeX(CataloguePropertyType.CONFIGURABLE);

        return Arrays.asList(batteryTypeP);
    }

    private static void setPropertiesOfEngineCI(CatalogueItem engineCI)
    {
        KeyValue engineMassKV = KeyValueHelper.createKeyValueOfType(engineCI, "massPropKV", "mass", ValueType.T_MEASURE, new MassMeasure(195, NonSI.TON_UK));

        KeyValue enginePriceKV = KeyValueHelper.createKeyValueOfType(engineCI, "pricePropKV", "price", ValueType.T_MEASURE, new MoneyMeasure(250000, Currency.EUR));

        KeyValue enginePowerRatingKv = KeyValueHelper.createKeyValueOfType(engineCI, "powerPropKV", "powerRating", ValueType.T_MEASURE, new PowerMeasure(13000000.0, SI.KILO(SI.WATT)));

        KeyValue engineRh1KV = KeyValueHelper.createKeyValueOfType(engineCI, "rh1PropKV", "rh1", ValueType.T_DOUBLE_LIST, Arrays.asList(1.0, 2.0, 3.0));

        KeyValue engineRh2KV = KeyValueHelper.createKeyValueOfType(engineCI, "rh2PropKV", "rh2", ValueType.T_DOUBLE_LIST, Arrays.asList(1.0, 2.0, 3.0));

        KeyValue engineRh3KV = KeyValueHelper.createKeyValueOfType(engineCI, "rh3PropKV", "rh3", ValueType.T_DOUBLE_LIST, Arrays.asList(1.0, 2.0, 3.0));

        KeyValue engineRh4KV = KeyValueHelper.createKeyValueOfType(engineCI, "rh4PropKV", "rh4", ValueType.T_DOUBLE_LIST, Arrays.asList(1.0, 2.0, 3.0));

        KeyValue engineRh5KV = KeyValueHelper.createKeyValueOfType(engineCI, "rh5PropKV", "rh5", ValueType.T_DOUBLE_LIST, Arrays.asList(1.0, 2.0, 3.0));

        KeyValue engineRh6KV = KeyValueHelper.createKeyValueOfType(engineCI, "rh6PropKV", "rh6", ValueType.T_DOUBLE_LIST, Arrays.asList(1.0, 2.0, 3.0));

        KeyValue engineRh7KV = KeyValueHelper.createKeyValueOfType(engineCI, "rh7PropKV", "rh7", ValueType.T_DOUBLE_LIST, Arrays.asList(1.0, 2.0, 3.0));

        KeyValue engineTypeKV = KeyValueHelper.createKeyValueOfType(engineCI, "engineTypePropKV", "engineType", ValueType.T_STRING, "LNG");

        KeyValue sfocKV = KeyValueHelper.createKeyValueOfType(engineCI, "sfocPropKV", "sfoc", ValueType.T_MEASURE, new SpecificConsumptionMeasure(1500));

        KeyValue sfoc_loKV = KeyValueHelper.createKeyValueOfType(engineCI, "sfoc_loPropKV", "sfoc_lo", ValueType.T_MEASURE, new SpecificConsumptionMeasure(1000));

        //Set the properties of the catalogue item class engine
        engineCI.setProperties(Arrays.asList(engineMassKV, enginePriceKV, enginePowerRatingKv, engineRh1KV, engineRh2KV, engineRh3KV, engineRh4KV, engineRh5KV, engineRh6KV, engineRh7KV, engineTypeKV, sfocKV, sfoc_loKV));
      //      engineCI.setProperties(Arrays.asList(engineMassKV, enginePowerRatingKv, engineRh1KV, engineRh2KV, engineRh3KV, engineRh4KV, engineRh5KV, engineRh6KV, engineRh7KV, engineTypeKV, sfocKV, sfoc_loKV));

    }

    private static void setPropertiesOfBatteryCI(CatalogueItem batteryCI)
    {
        KeyValue batteryMassKV = KeyValueHelper.createKeyValueOfType(batteryCI, "batteryPropKV", "mass", ValueType.T_MEASURE, new MassMeasure(25, NonSI.TON_UK));

        KeyValue batteryPriceKV = KeyValueHelper.createKeyValueOfType(batteryCI, "pricePropKV", "price", ValueType.T_MEASURE, new MoneyMeasure(100000, Currency.EUR));

        KeyValue batteryPowerRatingKv = KeyValueHelper.createKeyValueOfType(batteryCI, "powerPropKV", "powerRating", ValueType.T_MEASURE, new PowerMeasure(10000000.0, SI.KILO(SI.WATT)));

        KeyValue batteryRh1KV = KeyValueHelper.createKeyValueOfType(batteryCI, "rh1PropKV", "rh1", ValueType.T_DOUBLE_LIST, Arrays.asList(1.0, 2.0, 3.0));

        KeyValue batteryRh2KV = KeyValueHelper.createKeyValueOfType(batteryCI, "rh2PropKV", "rh2", ValueType.T_DOUBLE_LIST, Arrays.asList(1.0, 2.0, 3.0));

        KeyValue batteryRh3KV = KeyValueHelper.createKeyValueOfType(batteryCI, "rh3PropKV", "rh3", ValueType.T_DOUBLE_LIST, Arrays.asList(1.0, 2.0, 3.0));

        KeyValue batteryRh4KV = KeyValueHelper.createKeyValueOfType(batteryCI, "rh4PropKV", "rh4", ValueType.T_DOUBLE_LIST, Arrays.asList(1.0, 2.0, 3.0));

        KeyValue batteryRh5KV = KeyValueHelper.createKeyValueOfType(batteryCI, "rh5PropKV", "rh5", ValueType.T_DOUBLE_LIST, Arrays.asList(1.0, 2.0, 3.0));

        KeyValue batteryRh6KV = KeyValueHelper.createKeyValueOfType(batteryCI, "rh6PropKV", "rh6", ValueType.T_DOUBLE_LIST, Arrays.asList(1.0, 2.0, 3.0));

        KeyValue batteryRh7KV = KeyValueHelper.createKeyValueOfType(batteryCI, "rh7PropKV", "rh7", ValueType.T_DOUBLE_LIST, Arrays.asList(1.0, 2.0, 3.0));

        KeyValue batteryTypeKV = KeyValueHelper.createKeyValueOfType(batteryCI, "batteryTypePropKV", "batteryType", ValueType.T_STRING, "type xyz");

        //Set the properties of the catalogue item class engine
        batteryCI.setProperties(Arrays.asList(batteryMassKV, batteryPriceKV, batteryPowerRatingKv, batteryRh1KV, batteryRh2KV,
                batteryRh3KV, batteryRh4KV, batteryRh5KV, batteryRh6KV, batteryRh7KV, batteryTypeKV));
//        batteryCI.setProperties(Arrays.asList(batteryMassKV, batteryPowerRatingKv, batteryRh1KV, batteryRh2KV,
//                batteryRh3KV, batteryRh4KV, batteryRh5KV, batteryRh6KV, batteryRh7KV, batteryTypeKV));
    }

    /**
     * returns a set of catalogue items of specified catalogue item class name
     * @param catalogueItemClassName
     * @return
     */
    public static Set<CatalogueItem> getCatalogueItemsByClassName(String catalogueItemClassName)
    {
        CatalogueManager cm = CatalogueManager.getInstance(projOM);
        Set<POID<Catalogue>> catPOIDs = cm.list();
        if (CollectionsHelper.isNullOrEmpty(catPOIDs)) {
            throw new ObjectNotFoundException("no catalogues defined in context");
        }
        Catalogue cat = cm.open(catPOIDs.iterator().next());

        Set<CatalogueItemClass> itemClasses = cm.findClass(cat, POID.STRUCTDIVIDER + catalogueItemClassName);

        Set<CatalogueItem> engineItems = null;
        if (!itemClasses.isEmpty()) {
            CatalogueItemClass engineClass = itemClasses.iterator().next();

            engineItems = engineClass.getItems();
        }

        return engineItems;
    }

    public static AnalysisScenario createAnalysisScenario(String scenarioName)
    {
        //---- LCA Analysis Scenario
        AnalysisScenario lcaAnalysisScenario = projOM.createInformationObject(AnalysisScenario.class,
                scenarioName);

        return lcaAnalysisScenario;
    }

    public static AnalysisCase createAnalysisCase(AnalysisScenario analysisScenario, String caseName)
    {
        //--- LCA Analysis Case
        AnalysisCase lcaAanalysisCase = projOM
                .createChildInformationObject(AnalysisCase.class, analysisScenario, caseName, true);
        return lcaAanalysisCase;
    }

  

    public static ParameterSet createParameterSet(AnalysisCase analysisCase, String setName,
            String setDescription, String spaceName)
    {
        ParameterSet lcaGeneralParameterSet = ObjectManager.createClusteredChildObject(ParameterSet.class, analysisCase,
                setName);
        lcaGeneralParameterSet.setDescription(
                setDescription);
        lcaGeneralParameterSet.setNameSpace(spaceName);

        return lcaGeneralParameterSet;
    }

    public static void setLifeSpan(ParameterSet lcaGeneralParameterSet, double lifeSpan)
    {
//         - Life Span
        KeyValue lifeSpanKV = KeyValueHelper.createKeyValueOfType(lcaGeneralParameterSet, "lifeSpanKV", "lifeSpan", ValueType.T_MEASURE, new TimeMeasure(lifeSpan, NonSI.YEAR));

        //Set the parameters attribute of the parameterSet

        if (lcaGeneralParameterSet.getParameters()!=null){

        }

        lcaGeneralParameterSet.setParameters(Arrays.asList(lifeSpanKV));
        
        ParameterSetToolBox.addParameter(lcaGeneralParameterSet, "lifeSpan", new TimeMeasure(lifeSpan, NonSI.YEAR));


    }

    public static void setPresentValue(ParameterSet lcaGeneralParameterSet, double presentValue)
    {
        //- Present Value
        KeyValue presentValueKV = KeyValueHelper.createKeyValueOfType(lcaGeneralParameterSet, "presentValueKV", "presentValue", ValueType.T_DOUBLE, presentValue);

        //Set the parameters attribute of the parameterSet
        lcaGeneralParameterSet.setParameters(Arrays.asList(presentValueKV));
        
        ParameterSetToolBox.addParameter(lcaGeneralParameterSet, "presentValue", presentValue);
    }

    public static void setInterestRate(ParameterSet lcaGeneralParameterSet, double interestRate)
    {
         //- Interest Rate
        KeyValue interestRateKV = KeyValueHelper.createKeyValueOfType(lcaGeneralParameterSet, "interestRateKV", "interestRate", ValueType.T_DOUBLE, interestRate);

        //Set the parameters attribute of the parameterSet
        lcaGeneralParameterSet.setParameters(Arrays.asList(interestRateKV));

        ParameterSetToolBox.addParameter(lcaGeneralParameterSet, "interestRate", interestRate);


    }

    //It is assumed that values between 0, 1 or 2 can be set.
    public static void setSensitivityLevel(ParameterSet lcaGeneralParameterSet, int level )
    {
        if (level!=0 && level!=1 && level!=2){
            throw new IllegalArgumentException("The level "+ level + " is not supported! Only values 0, 1 or 2 can be used.");
        }
        
        //- Sensitivity Level
        KeyValue sensitivityLevelKV = KeyValueHelper.createKeyValueOfType(lcaGeneralParameterSet, "sensitivityLevelKV", "sensitivityLevel", ValueType.T_INTEGER, level);

        //Set the parameters attribute of the parameterSet
        lcaGeneralParameterSet.setParameters(Arrays.asList(sensitivityLevelKV));

        ParameterSetToolBox.addParameter(lcaGeneralParameterSet, "sensitivityLevel", level);
        
    }

    public static void setShipTotalPrice(ParameterSet lcaGeneralParameterSet, double shipPrice)
    {
       //- Ship Total Price
        KeyValue shipTotalPriceKV = KeyValueHelper.createKeyValueOfType(lcaGeneralParameterSet, "shipTotalPriceKV", "shipTotalPrice", ValueType.T_MEASURE, shipPrice);

        //Set the parameters attribute of the parameterSet
        lcaGeneralParameterSet.setParameters(Arrays.asList(shipTotalPriceKV));
        ParameterSetToolBox.addParameter(lcaGeneralParameterSet, "shipTotalPrice", shipPrice);// new MoneyMeasure(shipPrice, Currency.EUR));
    }


    public static EvaluationResult createEvaluationResult(AnalysisCase analysisCase, String resultName){

        EvaluationResult evaluationResult = projOM.createChildInformationObject(EvaluationResult.class, analysisCase, resultName, true);

        return evaluationResult;
    }
//		set LCT results  
    public static void setLifeCycleCost(EvaluationResult result, double lifeCycleCosts){
        KeyValue lifeCycleCostKV = KeyValueHelper.createKeyValueOfType(result, "lifeCycleCostKV", "lifeCycleCost", ValueType.T_MEASURE, new MoneyMeasure(lifeCycleCosts, Currency.EUR));
        
//        if (result.getParameters()==null){
            result.setResult(lifeCycleCostKV);//.setParameters(Arrays.asList(lifeCycleCostKV));
//        }else{
//            result.getParameters().add(lifeCycleCostKV);
//        } 
    }
    
    public static void setLifeCycleTotalCost(EvaluationResult result, double lifeCycleTotalCosts){
        KeyValue lifeCycleTotalCostKV = KeyValueHelper.createKeyValueOfType(result, "lifeCycleTotalCostKV", "lifeCycleTotalCost", ValueType.T_MEASURE, new MoneyMeasure(lifeCycleTotalCosts, Currency.EUR));
        
//        if (result.getParameters()==null){
            result.setParameters(Arrays.asList(lifeCycleTotalCostKV));
//        }else{
//            result.getParameters().add(lifeCycleTotalCostKV);
//        } 
    }   
    
    public static void setGWPCost(EvaluationResult result, double GWPCost){
        KeyValue GWPCostKV = KeyValueHelper.createKeyValueOfType(result, "GWPCostKV", "GWPCost", ValueType.T_MEASURE, new MoneyMeasure(GWPCost, Currency.EUR));
        
//        if (result.getParameters()==null){
            result.setParameters(Arrays.asList(GWPCostKV));
//        }else{
//            result.getParameters().add(GWPCostKV);
//        } 
    }  
    public static void setGWP(EvaluationResult result, double GWP){
        KeyValue GWPKV = KeyValueHelper.createKeyValueOfType(result, "GWPKV", "GWP", ValueType.T_MEASURE, new MassMeasure(GWP, NonSI.TON_UK));
        
//        if (result.getParameters()==null){
            result.setParameters(Arrays.asList(GWPKV));
//        }else{
//            result.getParameters().add(GWPKV);
//        } 
    }

    public static void setAPCost(EvaluationResult result, double APCost){
        KeyValue APCostKV = KeyValueHelper.createKeyValueOfType(result, "APCostKV", "APCost", ValueType.T_MEASURE, new MoneyMeasure(APCost, Currency.EUR));
        
//        if (result.getParameters()==null){
            result.setParameters(Arrays.asList(APCostKV));
//        }else{
//            result.getParameters().add(APCostKV);
//        } 
    } 
    public static void setAP(EvaluationResult result, double AP){
        KeyValue APKV = KeyValueHelper.createKeyValueOfType(result, "APKV", "AP", ValueType.T_MEASURE, new MassMeasure(AP, NonSI.TON_UK));
        
//        if (result.getParameters()==null){
            result.setParameters(Arrays.asList(APKV));
//        }else{
//            result.getParameters().add(APKV);
//        } 
    }  
     
 
    public static void setEPCost(EvaluationResult result, double EPCost){
        KeyValue EPCostKV = KeyValueHelper.createKeyValueOfType(result, "EPCostKV", "EPCost", ValueType.T_MEASURE, new MoneyMeasure(EPCost, Currency.EUR));
        
//        if (result.getParameters()==null){
            result.setParameters(Arrays.asList(EPCostKV));
//        }else{
//            result.getParameters().add(EPCostKV);
//        } 
    } 
    public static void setEP(EvaluationResult result, double EP){
        KeyValue EPKV = KeyValueHelper.createKeyValueOfType(result, "EPKV", "EP", ValueType.T_MEASURE, new MassMeasure(EP, NonSI.TON_UK));
        
//        if (result.getParameters()==null){
            result.setParameters(Arrays.asList(EPKV));
//        }else{
//            result.getParameters().add(EPKV);
//        } 
    }  
    
    public static void setPOCPCost(EvaluationResult result, double POCPCost){
        KeyValue POCPCostKV = KeyValueHelper.createKeyValueOfType(result, "POCPCostKV", "POCPCost", ValueType.T_MEASURE, new MoneyMeasure(POCPCost, Currency.EUR));
        
//        if (result.getParameters()==null){
            result.setParameters(Arrays.asList(POCPCostKV));
//        }else{
//            result.getParameters().add(POCPCostKV);
//        } 
    } 
    public static void setPOCP(EvaluationResult result, double POCP){
        KeyValue POCPKV = KeyValueHelper.createKeyValueOfType(result, "POCPKV", "POCP", ValueType.T_MEASURE, new MassMeasure(POCP, NonSI.TON_UK));
        
//        if (result.getParameters()==null){
            result.setParameters(Arrays.asList(POCPKV));
//        }else{
//            result.getParameters().add(POCPKV);
//        } 
    }  
    
    public static void setRPNCost(EvaluationResult result, double RPNCost){
        KeyValue RPNCostKV = KeyValueHelper.createKeyValueOfType(result, "RPNCostKV", "RPNCost", ValueType.T_MEASURE, new MoneyMeasure(RPNCost, Currency.EUR));
        
//        if (result.getParameters()==null){
            result.setParameters(Arrays.asList(RPNCostKV));
//        }else{
//            result.getParameters().add(RPNCostKV);
//        } 
    } 
    public static void setRPN(EvaluationResult result, double RPN){
        KeyValue RPNKV = KeyValueHelper.createKeyValueOfType(result, "RPNKV", "RPN", ValueType.T_MEASURE, new MoneyMeasure(RPN, Currency.EUR));
        
//        if (result.getParameters()==null){
            result.setParameters(Arrays.asList(RPNKV));
//        }else{
//            result.getParameters().add(RPNKV);
//        } 
    }  
    
}   // class LCAdataFactory //

