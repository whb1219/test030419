/*
 * StartScreen.java
 * 
 * @date	 14.11.2018
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

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.log4j.Logger;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.atlantec.binding.POID;
import org.atlantec.binding.erm.Catalogue;
import org.atlantec.binding.erm.CatalogueItemInstance;
import org.atlantec.binding.erm.CatalogueProperty;
import org.atlantec.binding.erm.CataloguePropertyType;
import org.atlantec.binding.erm.KeyValue;
import org.atlantec.binding.erm.ParameterSet;
import org.atlantec.binding.erm.ProductComponent;
import org.atlantec.catalogue.CatalogueManager;
import org.atlantec.db.Session;
import org.atlantec.directory.ConnectionMode;
import org.atlantec.directory.InformationDirectory;
import org.atlantec.jeb.ObjectNotFoundException;
import org.atlantec.objects.ObjectManager;
import org.atlantec.objects.Query;
import org.atlantec.util.CollectionsHelper;
import org.atlantec.util.io.TgFile;
import org.jscience.economics.money.Currency;
import static org.jscience.economics.money.Currency.EUR;

/**
 *
 */
public class StartScreen
{
    static private final Logger log = Logger.getLogger(StartScreen.class);

    private static Session session;
    private static ObjectManager objectManager;

    /**
     * create a new instance of StartScreen
     */
    public StartScreen()
    {
        super();

    }

    /**
     * Create the GUI and show it. For thread safety, this method should be invoked from the event-dispatching thread.
     */
    private static void createAndShowGUI()
    {
        //Create and set up the window.
        JFrame frame = new JFrame("Select Data Source");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(300, 200));

        JButton buttonExcel = new JButton("Read from Excel");

        JButton buttonShiplys = new JButton("Read from SHIPLYS database");

        ActionListener actionListener = new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //User selects ExcelSheet as Input Source
                if (e.getSource() == buttonExcel) {
                    // select file using FileDialog
                    final JFileChooser fc = new JFileChooser();
                    fc.setDialogTitle("Select XLS file with process schedule specification");
                    fc.putClientProperty("FileChooser.useShellFolder", Boolean.FALSE);
                    fc.setCurrentDirectory(TgFile.getWorkspace());
                    fc.setFileFilter(new FileNameExtensionFilter("XLS files", "xls", "xlsx"));
                    final AtomicInteger result = new AtomicInteger(0);
                    //  result.set(fc.showOpenDialog(AppGUI.getInstance()));
                    result.set(fc.showOpenDialog(null));
                    if (result.get() != JOptionPane.OK_OPTION) {
                        return;
                    }
                    File selectedFile = fc.getSelectedFile();

                    //--Read from Excel
                    readFromExcel(selectedFile);

                } else {
                    //User selects SHIPLYS data base as Input Source
                    //--Establish connection to SHIPLYS database
                    connect();

                    //--Read from database
                    readFromDB();

                    //--Close connection at the end of the Application
                    shutdown();

                };
            }

        };

        buttonExcel.addActionListener(actionListener);
        buttonShiplys.addActionListener(actionListener);

        frame.setLayout(new GridLayout(1, 2));
        frame.add(buttonExcel);
        frame.add(buttonShiplys);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args)
    {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(StartScreen::createAndShowGUI);
    }

    private static void readFromExcel(File selectedFile)
    {
        try {
            InputStream xlsStream = new FileInputStream(selectedFile);
            Workbook workbook = WorkbookFactory.create(xlsStream);

            //TODO: read from excel, convert data and save it localy
            
        } catch (IOException | InvalidFormatException | EncryptedDocumentException ex) {
            log.error(ex);
        }
    }

    private static void connect()
    {
        // init reference currency, otherwise working with cost-based measures will fail.
        Currency.setReferenceCurrency(EUR);
        String connectionURL = "memory://localhost/dc=test";
        String username = "tgadmin";
        char[] pw = "secret".toCharArray();
        String contextName = "TestProject";
        String productName = "TestShip";

        session = Session.newSession(connectionURL, username, pw, ConnectionMode.PROJECT_MODE, contextName,
                productName);
        objectManager = session.getManager();
    }

    private static void readFromDB()
    {
        //--TODO: read from database, convert data and save it localy
        //E.g.: read all engines and batties from data base like it is shown in example code (see following example and explanations in QueryExamplesGeneral)
        Query query = objectManager.newQuery(ProductComponent.class);
//        System.out.println("pppppp: "+ProductComponent.class);
        query.setMode(Query.PERSISTENT);
        @SuppressWarnings("unchecked")
		List<POID<ProductComponent>> result = (List<POID<ProductComponent>>) query.execute();
        log.info("Found " + result.size() + " Product Components:");
        for (POID<ProductComponent> poid: result) {
            ProductComponent productComponent = objectManager.getObjectById(poid);
//            log.info("test1:"+productComponent.getCommonName());
//            log.info("test2:"+productComponent.getContributors());
//            log.info("test3:"+productComponent.getDesignation());
        
            //1. Read values of configurable properties contained in parameterSet of a specific product component (e.g. price etc.)
//            System.out.println("1==========================================================");
            ParameterSet parameterSet = productComponent.getParameters();
//            System.out.println("11"+parameterSet);
//            //System.out.println("12"+parameterSet.getCommonName());
//            
//            System.out.println("13"+parameterSet.getParameters());
//            System.out.println("13"+parameterSet.getParameters().listIterator(1)); 
//            System.out.println("14"+parameterSet.getParameters().size());
            List<KeyValue> configurableProperties = null;
            String[] CP_name = new String[54];
            String[] import_name = new String[54];

            String[] CP_value = new String[54];
            String[] import_value = new String[54];

            log.info("Configurable Properties of " + productComponent.getCommonName() + " are:");
            if (parameterSet != null) {
               configurableProperties = parameterSet.getParameters(); 
//               System.out.println("test on KV:" + configurableProperties.get(3).getKey());
//               System.out.println("test on KV:" + configurableProperties.get(3).getValue());
//               System.out.println("cccccc: "+configurableProperties);
               
               for(int i=0;i<configurableProperties.size();i++){
            	   CP_name[i]=configurableProperties.get(i).getKey(); //parameter names
            	   CP_value[i]=configurableProperties.get(i).getValue(); //parameter values

            	   System.out.println(CP_name[i].substring(CP_name[i].lastIndexOf(".")+1,CP_name[i].length()));
            	   //System.out.println(CP_value[i].substring(0, CP_value[i].indexOf(" ")));
            	   System.out.println(CP_value[i]);

               }
                for (KeyValue engingeProductComponentProp: configurableProperties) {
                    log.info("Parameter name =" + engingeProductComponentProp.getKey() + ", Parameter type =" + engingeProductComponentProp.getTypeX() + ", Parameter value =" + engingeProductComponentProp.getValue() + "\n");
                }
            
            }

            //2. Then read standard values (which are not configurable) from refered catalogueItem
//            System.out.println("2==========================================================");
//        Set<KeyValue> allProperties = productComponent.getCatalogueItemRef().getProperties();
//        System.out.println("test: "+productComponent.getCatalogueItemRef().getProperties());
//   
//        log.info("Properties of " + productComponent.getCommonName() + " are: ");
//        for (KeyValue catalogueItemProp : allProperties){
//        	System.out.println(catalogueItemProp.getKey());
//        	System.out.println(catalogueItemProp.getValue());
//
//            log.info("Parameter name = " + catalogueItemProp.getKey() + ", Parameter type = " + catalogueItemProp.getTypeX()
//                    + ", Parameter value =" + catalogueItemProp
//                            .getValue() +"\n");
//        }





            //log.info("test3:"+productComponent.getPrice());
            
            
            

            
        }
    }

    private static void shutdown()
    {
        if (session != null) {
            session.close();
        }

        InformationDirectory.shutdown();
    }
    
    private static CatalogueProperty getCataloguePropertyByName(CatalogueItemInstance item, String propertyName)
    {
        //Load catalogue
        CatalogueManager catM = CatalogueManager.getInstance(objectManager);

        Set<POID<Catalogue>> catPOIDs = catM.list();
        if (CollectionsHelper.isNullOrEmpty(catPOIDs)) {
            throw new ObjectNotFoundException("no catalogues defined in context");
        } else if (catPOIDs.size() > 1) {
            throw new UnsupportedOperationException("There are more than one catalogue available. Make sure to sele");
        }
        catM.open(catPOIDs.iterator().next());

        CatalogueProperty property = item.getAllPropertyDefinitions().stream()
                .filter(prop -> prop.getCommonName().equals(propertyName))
                .findFirst().get();

        return property;
    }

}   // class StartScreen //
