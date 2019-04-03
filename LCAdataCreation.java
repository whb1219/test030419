package LCT;

/*
 * LCAdataCreation.java
 * 
 * @date	 08.08.2018
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


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.measure.unit.NonSI;
import javax.measure.unit.ProductUnit;
import javax.measure.unit.SI;
import org.apache.log4j.Logger;
import org.atlantec.binding.erm.AnalysisCase;
import org.atlantec.binding.erm.AnalysisScenario;
import org.atlantec.binding.erm.Catalogue;
import org.atlantec.binding.erm.CatalogueItem;
import org.atlantec.binding.erm.CatalogueItemClass;
import org.atlantec.binding.erm.CatalogueProperty;
import org.atlantec.binding.erm.CataloguePropertyType;
import org.atlantec.binding.erm.DistributedMassMeasure;
import org.atlantec.binding.erm.EvaluationResult;
import org.atlantec.binding.erm.ForceMeasure;
import org.atlantec.binding.erm.KeyValue;
import org.atlantec.binding.erm.LengthMeasure;
import org.atlantec.binding.erm.MassMeasure;
import org.atlantec.binding.erm.MoneyMeasure;
import org.atlantec.binding.erm.Parameter;
import org.atlantec.binding.erm.ParameterFromCatalogue;
import org.atlantec.binding.erm.ParameterSet;
import org.atlantec.binding.erm.PowerMeasure;
import org.atlantec.binding.erm.PricePerEnergyMeasure;
import org.atlantec.binding.erm.PricePerLengthMeasure;
import org.atlantec.binding.erm.PricePerMassMeasure;
import org.atlantec.binding.erm.ProductComponent;
import org.atlantec.binding.erm.RatioMeasure;
import org.atlantec.binding.erm.SpecificConsumptionMeasure;
import org.atlantec.binding.erm.TimeMeasure;
import org.atlantec.binding.erm.TransportationEquipmentType;
import org.atlantec.binding.erm.ValueType;
import org.atlantec.catalogue.CatalogueManager;
import org.atlantec.directory.DirectoryTestSetup;
import org.atlantec.directory.InformationServer;
import org.atlantec.directory.PooledPublisher;
import org.atlantec.lang.Counter;
import org.atlantec.objects.ObjectManager;
import org.atlantec.util.CollectionsHelper;
import org.atlantec.util.KeyValueHelper;
import org.atlantec.wrapper.erm.toolbox.ParameterSetToolBox;
import org.jscience.economics.money.Currency;

/**
 *
 */
public class LCAdataCreation
{
    static private final Logger log = Logger.getLogger(LCAdataCreation.class);


    static private final DirectoryTestSetup dts = new DirectoryTestSetup(LCAdataCreation.class);
    private static ObjectManager projOM;

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception
    {
        setUpDataBaseAndStartTransaction();

        createCatalogueAndProductComponents();

        createLCAanalysisScenario();

        tearDownDataBase();
    }

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

    private static void tearDownDataBase() throws Exception
    {
        if (projOM.currentTransaction().isActive()) {
            projOM.currentTransaction().rollback();
        }

         dts.tearDown();
    }

    private static void createCatalogueAndProductComponents()
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
        CatalogueItem engineCI = catM.addItem(engineCIC, "MAN 6L45/60CR 10L");
        //Set the properties of the engine catalogue_item specified within the engine catalogue_item_class
        setPropertiesOfEngineCI(engineCI);

        //Based on catalogue definition a specific product component related to a specific project (project represents in most of the cases a ship) can be created
        ProductComponent engine = createProductComponent(engineCI, "EngineXYZ");
        //Change the configurable price property of the specific product component
        CatalogueProperty priceProperty = engineCI.getAllPropertyDefinitions().stream()
                .filter(prop->prop.getCommonName().equals("price"))
                .findFirst().get();
        setProductComponentsProperty(engine, priceProperty, new MoneyMeasure(267000, Currency.EUR).toString());
        
        CatalogueProperty massProperty = engineCI.getAllPropertyDefinitions().stream()
                .filter(prop->prop.getCommonName().equals("mass"))
                .findFirst().get();
        setProductComponentsProperty(engine, massProperty, new MassMeasure(12, NonSI.TON_UK).toString());


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

        //Based on catalogue definition a specific product component related to a specific project (project represents in most of the cases a ship) can be created
        ProductComponent battery = createProductComponent(batteryCI, "BatteryXYZ");

        //Save
        projOM.makePersistent(cat);
        projOM.makePersistent(engineCI);
        projOM.makePersistent(batteryCI);
        projOM.makePersistent(engine);
        projOM.makePersistent(battery);

        projOM.currentTransaction().commit();
    }

    private static void createLCAanalysisScenario()
    {
        //---- LCA Analysis Scenario
        AnalysisScenario lcaAnalysisScenario = projOM.createInformationObject(AnalysisScenario.class,
                "lcaAnalysisScenario");

        //--- LCA Analysis Case
        AnalysisCase lcaAanalysisCase = projOM.createChildInformationObject(AnalysisCase.class,lcaAnalysisScenario, "generalLCAanalysisCase",true);

        //-- General LCA Parameters
        createGeneralLCAparameters(lcaAanalysisCase);

        //-- Construction LCA Parameters
        createConstructionLCAparameters(lcaAanalysisCase);

        //-- Operation LCA Parameters
        createOperationLCAparameters(lcaAanalysisCase);

        //-- Scrapping LCA Parameters
        createScrappingLCAparameters(lcaAanalysisCase);

        //--- Evaluation Results
        EvaluationResult evaluationResult=createEvaluationResults();

        //Save
        projOM.makePersistent(lcaAnalysisScenario);
        projOM.makePersistent(lcaAanalysisCase);
        projOM.makePersistent(evaluationResult);

        projOM.currentTransaction().commit();
    }

    private static List<CatalogueProperty> createCommonProperties(CatalogueManager catM, Catalogue cat)
    {
        //Mass
        CatalogueProperty massP = catM.addProperty(cat, "mass");
        massP.setDataType(ValueType.T_MEASURE);
        massP.setDataSubType(MassMeasure.class.getName());
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
        powerRatingP.setDescription("power rating of item");

        //RH1
        CatalogueProperty rh1P = catM.addProperty(cat, "rh1");
        rh1P.setDataType(ValueType.T_DOUBLE_LIST);
        rh1P.setSize(3);
        rh1P.setDescription("risk hazard 1 of item");

        //RH2
        CatalogueProperty rh2P = catM.addProperty(cat, "rh2");
        rh2P.setDataType(ValueType.T_DOUBLE_LIST);
        rh2P.setSize(3);
        rh2P.setDescription("risk hazard 2 of item");

        //RH3
        CatalogueProperty rh3P = catM.addProperty(cat, "rh3");
        rh3P.setDataType(ValueType.T_DOUBLE_LIST);
        rh3P.setSize(3);
        rh3P.setDescription("risk hazard 3 of item");

        //RH4
        CatalogueProperty rh4P = catM.addProperty(cat, "rh4");
        rh4P.setDataType(ValueType.T_DOUBLE_LIST);
        rh4P.setSize(3);
        rh4P.setDescription("risk hazard 4 of item");

        //RH5
        CatalogueProperty rh5P = catM.addProperty(cat, "rh5");
        rh5P.setDataType(ValueType.T_DOUBLE_LIST);
        rh5P.setSize(3);
        rh5P.setDescription("risk hazard 5 of item");

        //RH6
        CatalogueProperty rh6P = catM.addProperty(cat, "rh6");
        rh6P.setDataType(ValueType.T_DOUBLE_LIST);
        rh6P.setSize(3);
        rh6P.setDescription("risk hazard 6 of item");

        //RH7
        CatalogueProperty rh7P = catM.addProperty(cat, "rh7");
        rh7P.setDataType(ValueType.T_DOUBLE_LIST);
        rh7P.setSize(3);
        rh7P.setDescription("risk hazard 7 of item");

        return Arrays.asList(massP, priceP, powerRatingP, rh1P, rh2P, rh3P, rh4P, rh5P, rh6P, rh7P);
    }

    private static List<CatalogueProperty> createEngineProperties(CatalogueManager catM, Catalogue cat)
    {
        //Engine type
        CatalogueProperty engineTypeP = catM.addProperty(cat, "engineType");
        engineTypeP.setDataType(ValueType.T_ENUM);
        engineTypeP.setDescription("type of engine");

        //SFOC
        CatalogueProperty sfocP = catM.addProperty(cat, "sfoc");
        sfocP.setDataType(ValueType.T_MEASURE);
        sfocP.setDataSubType(SpecificConsumptionMeasure.class.getName());
        sfocP.setDescription("sfoc of engine");

        //SFOC_LO
        CatalogueProperty sfoc_loP = catM.addProperty(cat, "sfoc_lo");
        sfoc_loP.setDataType(ValueType.T_MEASURE);
        sfoc_loP.setDataSubType(SpecificConsumptionMeasure.class.getName());
        sfoc_loP.setDescription("sfoc_lo of engine");

        return Arrays.asList(engineTypeP, sfocP, sfoc_loP);
    }

    private static List<CatalogueProperty> createBatteryProperties(CatalogueManager catM, Catalogue cat)
    {
        //Battery type
        CatalogueProperty batteryTypeP = catM.addProperty(cat, "batteryType");
        batteryTypeP.setDataType(ValueType.T_ENUM);
        batteryTypeP.setDescription("type of battery");

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
    }

    private static ProductComponent createProductComponent(CatalogueItem catalogueItem, String productName)
    {
        ProductComponent productComponent = projOM.createInformationObject(ProductComponent.class, productName);

        productComponent.setCatalogueItemRef(catalogueItem);

        return productComponent;
    }


    
//    public static void setProductComponentsProperty(ProductComponent productComponent, String propertyName, String value)
//    {
//        CatalogueProperty property = getCataloguePropertyByName(productComponent.getCatalogueItemRef(), propertyName);
//
//        if (property.getTypeX() == CataloguePropertyType.CONFIGURABLE) {
//            ParameterSet parameterSet;
//            if (productComponent.getParameters() == null) {
//                parameterSet = ObjectManager.createClusteredObject(ParameterSet.class, productComponent,
//                        "Configurable Properties");
//                parameterSet.setDescription(
//                        "This set contains catalogue properties which are determined to be configurable, e.g. the price.");
//                parameterSet.setNameSpace("SHIPLYS Parameter definitions");
//                productComponent.setParameters(parameterSet);
//            } else {
//                parameterSet = productComponent.getParameters();
//            }
//
//            ParameterFromCatalogue parameter = ObjectManager.createClusteredObject(ParameterFromCatalogue.class, parameterSet,
//                    propertyName);
//            parameter.setPropertyDefinition(property);
//
//            parameter.setDefaultValue(value);
//
//            if (CollectionsHelper.isNotEmpty(parameterSet.getParameters())) {
//                ParameterSetToolBox.addParameter(parameterSet, parameter.getCommonName(), parameter.getDefaultValue());
//            } else {
//                ParameterSetToolBox.setParameters(parameterSet, Arrays.asList(parameter));
//            }
//        } else {
//            log.info("The property cannot be set as it is not configurable!");
//        }
//    }
    
    
    
    
    private static void setProductComponentsProperty(ProductComponent productComponent, CatalogueProperty catalogueProperty, String value)
    {
        if (catalogueProperty.getTypeX() == CataloguePropertyType.CONFIGURABLE) {
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
                    "Price");
            parameter.setPropertyDefinition(catalogueProperty);

            parameter.setDefaultValue(value);
            if (CollectionsHelper.isNotEmpty(parameterSet.getParameters())) {
                ParameterSetToolBox.addParameter(parameterSet, parameter.getCommonName(), parameter.getDefaultValue());
            } else {
                ParameterSetToolBox.setParameters(parameterSet, Arrays.asList(parameter));
            }


//            setParameters(parameterSet, Arrays.asList(parameter));
        } else {
            log.info("The property cannot be set as it is not configurable!");
        }
    }


    private static void setParameters(ParameterSet pSet, Collection<Parameter> parameters)
    {
        List<KeyValue> keyValues = new ArrayList<>();
        Counter counter = new Counter();
        for (Parameter parameter: parameters) {
            KeyValue keyValue = KeyValueHelper.packValue(pSet, parameter.getCommonName(), parameter.getDefaultValue(), counter);
            keyValues.add(keyValue);
        }
        pSet.setParameters(keyValues);
    }

    private static void createGeneralLCAparameters(AnalysisCase lcaAanalysisCase)
    {
       ParameterSet lcaGeneralParameterSet = ObjectManager.createClusteredObject(ParameterSet.class, lcaAanalysisCase,
                "GeneralLCAParameters");
        lcaGeneralParameterSet.setDescription(
                "This set contains the common lca parameters.");
        lcaGeneralParameterSet.setNameSpace("SHIPLYS general LCA parameter definitions");

        //- Life Span
        KeyValue lifeSpanKV = KeyValueHelper.createKeyValueOfType(lcaGeneralParameterSet, "lifeSpanKV", "lifeSpan", ValueType.T_MEASURE, new MassMeasure(25, NonSI.TON_UK));
        //- Present Value
        KeyValue presentValueKV = KeyValueHelper.createKeyValueOfType(lcaGeneralParameterSet, "presentValueKV", "presentValue", ValueType.T_DOUBLE, 75000000);
        //- Interest Rate
        KeyValue interestRateKV = KeyValueHelper.createKeyValueOfType(lcaGeneralParameterSet, "interestRateKV", "interestRate", ValueType.T_DOUBLE, 5);
        //- Sensitivity Level
        KeyValue sensitivityLevelKV = KeyValueHelper.createKeyValueOfType(lcaGeneralParameterSet, "sensitivityLevelKV", "sensitivityLevel", ValueType.T_INTEGER_LIST, Arrays.asList(0,1,2));
        //- Ship Total Price
        KeyValue shipTotalPriceKV = KeyValueHelper.createKeyValueOfType(lcaGeneralParameterSet, "shipTotalPriceKV", "shipTotalPrice", ValueType.T_DOUBLE, 100000000);

        //Set the parameters attribute of the parameterSet
        lcaGeneralParameterSet.setParameters(Arrays.asList(lifeSpanKV, presentValueKV, interestRateKV, sensitivityLevelKV, shipTotalPriceKV));
    }

    private static void createConstructionLCAparameters(AnalysisCase lcaAanalysisCase)
    {
        ParameterSet lcaConstructionParameterSet = ObjectManager.createClusteredObject(ParameterSet.class, lcaAanalysisCase, "ConstructionParameters");
        lcaConstructionParameterSet.setDescription(
                "This set contains the construction related LCA parameters regarding transportation and electricity.");
        lcaConstructionParameterSet.setNameSpace("SHIPLYS LCA construction parameter definitions");

        //- Transportation
        //Note: as there are different transportation types there may exist several transportation objects.
        //E.g. transportation object 1 having the related specific values for its attributes which are "Transportation Type",
        //"Distance", "Fee" etc. A further transportation object 2 would have other attribute values.
        //In order to represent this kind of hierarchy it is possible to nest the information, whereas the root node would be
        //"Transportation" having a list of several transportation objects (transportation 1, transprotation 2, etc.) and the
        //and each of the specific transprotation objects (e.g. transportation 1) would have a list of several attributes (here called parameters).

        //create list of attributes (parameters) for transportation object 1
        Map<String, Object> transportation1ParameterMap=new HashMap();

        //besides TransportationEquipmentType see also TransportationNetworkType
        transportation1ParameterMap.put("Transportation type", TransportationEquipmentType.SHIP);
        transportation1ParameterMap.put("Distance", new LengthMeasure(15000, SI.KILOMETER));
        //Note: standard unit is €/m
        transportation1ParameterMap.put("Fee", new PricePerLengthMeasure(100));
        transportation1ParameterMap.put("SFOC", new DistributedMassMeasure(1500, new ProductUnit<>(SI.KILOGRAM.divide(SI.KILOMETER))));
        //Note: standard unit is €/kg
        transportation1ParameterMap.put("Fuel price", new PricePerMassMeasure(1500));
        //Note: the related Unit kgCO2 Equiv./kg fuel can be defined if it is necessary or it can be assumed that GWP is always related to this unit
        transportation1ParameterMap.put("TransportationGWP", new RatioMeasure(0.5));
        //Note: the related Unit kgSO2 Equiv./kg fuel can be defined if it is necessary or it can be assumed that AP is always related to this unit
        transportation1ParameterMap.put("TransportationAP", new RatioMeasure(0.5));
        //Note: the related Unit kgPO4 Equiv./kg fuel can be defined if it is necessary or it can be assumed that EP is always related to this unit
        transportation1ParameterMap.put("TransportationEP", new RatioMeasure(0.5));
        //Note: the related Unit kgC2H6 Equiv./kg fuel can be defined if it is necessary or it can be assumed that POCP is always related to this unit
        transportation1ParameterMap.put("TransportationPOCP", new RatioMeasure(0.5));

        Map<String, Object> transportationMap=new HashMap();
        //Add the transportation 1 attributes to the transportation object "Transportation1"
        transportationMap.put("Transportation1",transportation1ParameterMap);

        //Add the list with transprotation objects to the main transportation object "Transportation"
        KeyValue transportationNKV=KeyValueHelper.packValue(lcaConstructionParameterSet, "Transportation", transportationMap, null);


        //- Electricity
        //Note: see explanations regarding Transportation example above
        Map<String, Object> electricitiy1ParameterMap=new HashMap();

        electricitiy1ParameterMap.put("Electricity type", "Electricity Type XYZ");
        //Note: PricePerEnergyMeasure is a new measure type which will be provided with the next patch!!!
        electricitiy1ParameterMap.put("Price", new PricePerEnergyMeasure(2));
        //Note: the related Unit kgCO2 Equiv./kg fuel can be defined if it is necessary or it can be assumed that GWP is always related to this unit
        electricitiy1ParameterMap.put("ElectricityGWP", new RatioMeasure(0.5));
        //Note: the related Unit kgSO2 Equiv./kg fuel can be defined if it is necessary or it can be assumed that AP is always related to this unit
        electricitiy1ParameterMap.put("ElectricityAP", new RatioMeasure(0.5));
        //Note: the related Unit kgPO4 Equiv./kg fuel can be defined if it is necessary or it can be assumed that EP is always related to this unit
        electricitiy1ParameterMap.put("ElectricityEP", new RatioMeasure(0.5));
        //Note: the related Unit kgC2H6 Equiv./kg fuel can be defined if it is necessary or it can be assumed that POCP is always related to this unit
        electricitiy1ParameterMap.put("ElectricityPOCP", new RatioMeasure(0.5));

        Map<String, Object> electricityMap=new HashMap();
        electricityMap.put("Electricity1",electricitiy1ParameterMap);

        KeyValue electricitiyNKV=KeyValueHelper.packValue(lcaConstructionParameterSet, "Electricitiy", electricityMap, null);

        //Setting the parameters attribute of the parameterSet
        lcaConstructionParameterSet.setParameters(Arrays.asList(transportationNKV, electricitiyNKV));
    }

    private static void createOperationLCAparameters(AnalysisCase lcaAanalysisCase)
    {
        ParameterSet lcaOperationParameterSet = ObjectManager.createClusteredObject(ParameterSet.class, lcaAanalysisCase,
                "OperationParameters");
        lcaOperationParameterSet.setDescription(
                "This set contains the operation related LCA parameters regarding anual working hours, fuel and lub consumption.");
        lcaOperationParameterSet.setNameSpace("SHIPLYS LCA operation parameter definitions");

        //- Annual working hours
        KeyValue annualWorkingHoursKV = KeyValueHelper.createKeyValueOfType(lcaOperationParameterSet, "annualWorkingHoursKV", "annualWorkingHours", ValueType.T_MEASURE, new TimeMeasure(1000, NonSI.HOUR));

        //- Fuel consumption
        //Note: information can be also derived from simulation --> voyage_log and voyage_event data
        //Note: see explanations regarding Transportation example above
        Map<String, Object> fuelConsumption1ParameterMap=new HashMap();

        fuelConsumption1ParameterMap.put("Fuel type", "Fuel Type XYZ");
        //Note: standard unit is €/kg
        fuelConsumption1ParameterMap.put("Fuel price", new PricePerMassMeasure(1500));
        //Note: the related Unit kgCO2 Equiv./kg fuel can be defined if it is necessary or it can be assumed that GWP is always related to this unit
        fuelConsumption1ParameterMap.put("FuelGWP", new RatioMeasure(0.5));
        //Note: the related Unit kgSO2 Equiv./kg fuel can be defined if it is necessary or it can be assumed that AP is always related to this unit
        fuelConsumption1ParameterMap.put("FuelAP", new RatioMeasure(0.5));
        //Note: the related Unit kgPO4 Equiv./kg fuel can be defined if it is necessary or it can be assumed that EP is always related to this unit
        fuelConsumption1ParameterMap.put("FuelEP", new RatioMeasure(0.5));
        //Note: the related Unit kgC2H6 Equiv./kg fuel can be defined if it is necessary or it can be assumed that POCP is always related to this unit
        fuelConsumption1ParameterMap.put("FuelPOCP", new RatioMeasure(0.5));
        //Note: the related Unit kgCO2/kg fuel can be defined if it is necessary or it can be assumed that carbon emission factor is always related to this unit
        fuelConsumption1ParameterMap.put("Carbon emission factor", new RatioMeasure(0.5));
        //Note: the related Unit kgSO2/kg fuel can be defined if it is necessary or it can be assumed that carbon emission factor is always related to this unit
        fuelConsumption1ParameterMap.put("Sulfer content", new RatioMeasure(0.5));

        Map<String, Object> fuelConsumptionMap = new HashMap();
        fuelConsumptionMap.put("FuelConsumption1", fuelConsumption1ParameterMap);

        KeyValue fuelConsumptionNKV = KeyValueHelper.packValue(lcaOperationParameterSet, "Fuel consumption", fuelConsumptionMap, null);

        //- Lub consumption
        //Note: information can be also derived from simulation --> voyage_log and voyage_event data
        //Note: see explanations regarding Transportation example above
        Map<String, Object> lubConsumption1ParameterMap=new HashMap();

        lubConsumption1ParameterMap.put("Lub oil type", "Lub Oil Type XYZ");
        //Note: standard unit is €/kg
        lubConsumption1ParameterMap.put("Lub oil price", new PricePerMassMeasure(1200));
        //Note: the related Unit kgCO2 Equiv./kg fuel can be defined if it is necessary or it can be assumed that GWP is always related to this unit
        lubConsumption1ParameterMap.put("LubGWP", new RatioMeasure(0.5));
        //Note: the related Unit kgSO2 Equiv./kg fuel can be defined if it is necessary or it can be assumed that AP is always related to this unit
        lubConsumption1ParameterMap.put("LubAP", new RatioMeasure(0.5));
        //Note: the related Unit kgPO4 Equiv./kg fuel can be defined if it is necessary or it can be assumed that EP is always related to this unit
        lubConsumption1ParameterMap.put("LubEP", new RatioMeasure(0.5));
        //Note: the related Unit kgC2H6 Equiv./kg fuel can be defined if it is necessary or it can be assumed that POCP is always related to this unit
        lubConsumption1ParameterMap.put("LubPOCP", new RatioMeasure(0.5));

        Map<String, Object> lubConsumptionMap=new HashMap();
        lubConsumptionMap.put("LubConsumption1",lubConsumption1ParameterMap);

        KeyValue lubConsumptionNKV=KeyValueHelper.packValue(lcaOperationParameterSet, "Lub consumption", lubConsumptionMap, null);

        //Setting the parameters attribute of the parameterSet
        lcaOperationParameterSet.setParameters(Arrays.asList(annualWorkingHoursKV, fuelConsumptionNKV, lubConsumptionNKV));
    }

    private static void createScrappingLCAparameters(AnalysisCase lcaAanalysisCase)
    {
        ParameterSet lcaScrappingParameterSet = ObjectManager.createClusteredObject(ParameterSet.class, lcaAanalysisCase,
                "ScrappingParameters");
        lcaScrappingParameterSet.setDescription(
                "This set contains the scrapping related LCA parameters regarding fee and material.");
        lcaScrappingParameterSet.setNameSpace("SHIPLYS LCA scrapping parameter definitions");

        //- Fee
        //Note: standard unit is €/kg
        KeyValue feeKV = KeyValueHelper.createKeyValueOfType(lcaScrappingParameterSet, "feeKV", "fee", ValueType.T_MEASURE, new PricePerMassMeasure(500));


        //- Material
        //Note: information can be also derived from simulation --> voyage_log and voyage_event data
        //Note: see explanations regarding Transportation example above
        Map<String, Object> material1ParameterMap=new HashMap();

        material1ParameterMap.put("Cut type", "Cut Type XYZ");
        material1ParameterMap.put("Electricity per meter", new ForceMeasure(10,new ProductUnit<>(SI.KILO(SI.JOULE).divide(SI.METER))));
        material1ParameterMap.put("Material quantity", new MassMeasure(100000, NonSI.TON_UK));
        //Note: standard unit is €/kg
        material1ParameterMap.put("Material price", new PricePerMassMeasure(10));
        material1ParameterMap.put("Cutting length", new LengthMeasure(1000000, SI.METER));

        Map<String, Object> materialMap=new HashMap();
        materialMap.put("Material1",material1ParameterMap);

        KeyValue materialNKV=KeyValueHelper.packValue(lcaScrappingParameterSet, "Material", materialMap, null);

        //Setting the parameters attribute of the parameterSet
        lcaScrappingParameterSet.setParameters(Arrays.asList(feeKV, materialNKV));
    }

    private static EvaluationResult createEvaluationResults()
    {
        EvaluationResult evaluationResult = projOM.createInformationObject(EvaluationResult.class, "evaluationResult");

        KeyValue lifeCycleCostKV = KeyValueHelper.createKeyValueOfType(evaluationResult, "lifeCycleCostKV", "lifeCycleCost", ValueType.T_MEASURE, new MoneyMeasure(1500000, Currency.EUR));

        //Note: it is assumed that CO2 Equiv. is related to global warming potentials meassure
        KeyValue globalWarmingPotentialsKV = KeyValueHelper.createKeyValueOfType(evaluationResult, "globalWarmingPotentialsKV", "globalWarmingPotentials", ValueType.T_MEASURE, new MassMeasure(25, NonSI.TON_UK));

        //Note: it is assumed that SO2 Equiv. is related to acidification potentials meassure
        KeyValue acidificationPotentialsKV = KeyValueHelper.createKeyValueOfType(evaluationResult, "acidificationPotentialsKV", "acidificationPotentials", ValueType.T_MEASURE, new MassMeasure(25, NonSI.TON_UK));

        //Note: it is assumed that PO4 Equiv. is related to acidification potentials meassure
        KeyValue eutrophicationPotentialsKV = KeyValueHelper.createKeyValueOfType(evaluationResult, "eutrophicationPotentialsKV", "eutrophicationPotentials", ValueType.T_MEASURE, new MassMeasure(25, NonSI.TON_UK));

        //Note: it is assumed that C2H6 Equiv. is related to photochemical ozone creation potentials meassure
        KeyValue photochemicalPotentialsKV = KeyValueHelper.createKeyValueOfType(evaluationResult, "photochemicalPotentialsKV", "photochemicalPotentials", ValueType.T_MEASURE, new MassMeasure(25, NonSI.TON_UK));

        //Note: it is assumed that C2H6 Equiv. is related to photochemical ozone creation potentials meassure
        KeyValue riskPriorityKV = KeyValueHelper.createKeyValueOfType(evaluationResult, "riskPriorityKV", "riskPriority", ValueType.T_DOUBLE, "5");

        KeyValue totalLifeCycleCostKV = KeyValueHelper.createKeyValueOfType(evaluationResult, "totalLifeCycleCostKV", "totalLifeCycleCost", ValueType.T_MEASURE, new MoneyMeasure(2500000, Currency.EUR));

        evaluationResult.setParameters(Arrays.asList(lifeCycleCostKV,globalWarmingPotentialsKV,acidificationPotentialsKV,eutrophicationPotentialsKV,photochemicalPotentialsKV,riskPriorityKV,totalLifeCycleCostKV));

        return evaluationResult;
    }
}   // class LCAdataCreation //
