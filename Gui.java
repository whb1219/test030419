package LCT;

import static org.jscience.economics.money.Currency.EUR;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import javax.measure.unit.NonSI;
import javax.measure.unit.SI;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Hour;
import org.jscience.economics.money.Currency;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor.WHITE;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFTable;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.atlantec.objects.ObjectManager;
import org.atlantec.objects.Query;
import org.drools.compiler.lang.dsl.DSLMapParser.value_chunk_return;
import org.apache.log4j.Logger;
import org.atlantec.binding.POID;
import org.atlantec.binding.erm.AnalysisCase;
import org.atlantec.binding.erm.AnalysisScenario;
import org.atlantec.binding.erm.CatalogueItem;
import org.atlantec.binding.erm.EvaluationResult;
import org.atlantec.binding.erm.KeyValue;
import org.atlantec.binding.erm.MassMeasure;
import org.atlantec.binding.erm.MoneyMeasure;
import org.atlantec.binding.erm.ParameterSet;
import org.atlantec.binding.erm.PowerMeasure;
import org.atlantec.binding.erm.ProductComponent;
import org.atlantec.binding.erm.SpecificConsumptionMeasure;
import org.atlantec.db.Session;
import org.atlantec.directory.ConnectionMode;
import org.atlantec.directory.DirectoryTestSetup;
import org.atlantec.directory.InformationDirectory;


public class Gui extends JPanel {
	/*
	 create a CUI with frame, panels, labels, drop lists, text fields and buttons 
	 create action to read date and calculate and generate results base on data read
	 */
	private static final long serialVersionUID = 1L;
    static private final Logger log = Logger.getLogger(GUIexample.class);
    private static Session session;
    private static ObjectManager objectManager;
    DecimalFormat formatter = new DecimalFormat("#0.00");
	
//interface	
	private static JTabbedPane tp	= new JTabbedPane();	//main tab panel
	private static JTabbedPane tp0	= new JTabbedPane();	//sub tab panel
	private static JTabbedPane tp0r	= new JTabbedPane();	//sub tab panel
	private static JTabbedPane      tp_plot1    = new JTabbedPane();
	private static JTabbedPane      tp_plot2    = new JTabbedPane();
	protected int result_size =10;    
    private JCheckBox[] checkBox= new JCheckBox[result_size];
    

		public JCheckBox[] getCheckBox() {
			return checkBox;
		}
	
		public void setCheckBox(JCheckBox[] checkBox) {
			this.checkBox = checkBox;
		}
		
		public static JTabbedPane getTp() {
				return tp;
		}
		public static void setTp(JTabbedPane tp) {
			Gui.tp = tp;
		}
		public JTabbedPane getTp0() {
			return tp0;
		}
		public void setTp0(JTabbedPane tp0) {
			Gui.tp0 = tp0;
		}
		public static JTabbedPane getTp_plot1() {
			return tp_plot1;
		}
		public static void setTp_plot1(JTabbedPane tp_plot1) {
			Gui.tp_plot1 = tp_plot1;
		}
		public static JTabbedPane getTp_plot2() {
			return tp_plot2;
		}
		public static void setTp_plot2(JTabbedPane tp_plot2) {
			Gui.tp_plot2 = tp_plot2;
		}
		public static JTabbedPane getTp_plot3() {
			return tp_plot3;
		}
		public static void setTp_plot3(JTabbedPane tp_plot3) {
			Gui.tp_plot3 = tp_plot3;
		}
		public static JTabbedPane getTp_plot4() {
			return tp_plot4;
		}
		public static void setTp_plot4(JTabbedPane tp_plot4) {
			Gui.tp_plot4 = tp_plot4;
		}
		public static JTabbedPane getTp_plot5() {
			return tp_plot5;
		}
		public static void setTp_plot5(JTabbedPane tp_plot5) {
			Gui.tp_plot5 = tp_plot5;
		}
		public static JTabbedPane getTp_plot6() {
			return tp_plot6;
		}
		public static void setTp_plot6(JTabbedPane tp_plot6) {
			Gui.tp_plot6 = tp_plot6;
		}
		public static JTabbedPane getTp_plot7() {
			return tp_plot7;
		}
		public static void setTp_plot7(JTabbedPane tp_plot7) {
			Gui.tp_plot7 = tp_plot7;
		}
		public static JFrame getFrame() {
			return Frame;
		}
		public static void setFrame11(JFrame frame) {
			Frame = frame;
		}
		public static JFrame getFrame11() {
			return frame;
		}
		public static void setFrame(JFrame frame) {
			Gui.frame = frame;
		}
		public static JFrame getFrame1() {
			return frame1;
		}
		public static void setFrame1(JFrame frame1) {
			Gui.frame1 = frame1;
		}
		public static JFrame getFrame2() {
			return frame2;
		}
		public static void setFrame2(JFrame frame2) {
			Gui.frame2 = frame2;
		}
		public static JFrame getFrame3() {
			return frame3;
		}
		public static void setFrame3(JFrame frame3) {
			Gui.frame3 = frame3;
		}
		public static JFrame getFrame4() {
			return frame4;
		}
		public static void setFrame4(JFrame frame4) {
			Gui.frame4 = frame4;
		}
		public static JFrame getFrame5() {
			return frame5;
		}
		public static void setFrame5(JFrame frame5) {
			Gui.frame5 = frame5;
		}
		public static JFrame getFrame6() {
			return frame6;
		}
		public static void setFrame6(JFrame frame6) {
			Gui.frame6 = frame6;
		}
		public static JFrame getFrame7() {
			return frame7;
		}
		public static void setFrame7(JFrame frame7) {
			Gui.frame7 = frame7;
		}
		public static JFrame getFrame_r() {
			return frame_r;
		}
		public static void setFrame_r(JFrame frame_r) {
			Gui.frame_r = frame_r;
		}
		public static JFrame getFrame_db() {
			return frame_db;
		}
		public static void setFrame_db(JFrame frame_db) {
			Gui.frame_db = frame_db;
		}
		public static JFrame getFrame_il() {
			return frame_il;
		}
		public static void setFrame_il(JFrame frame_il) {
			Gui.frame_il = frame_il;
		}
		public static JPanel getPanel_m() {
			return panel_m;
		}
		public static void setPanel_m(JPanel panel_m) {
			Gui.panel_m = panel_m;
		}
		public static JPanel getPanel_n() {
			return panel_n;
		}
		public static void setPanel_n(JPanel panel_n) {
			Gui.panel_n = panel_n;
		}
		public static JPanel getPanel_FC() {
			return panel_FC;
		}
		public static void setPanel_FC(JPanel panel_FC) {
			Gui.panel_FC = panel_FC;
		}
		public static JPanel getPanel() {
			return panel;
		}
		public static void setPanel(JPanel panel) {
			Gui.panel = panel;
		}
		public static JPanel getPanel0() {
			return panel0;
		}
		public static void setPanel0(JPanel panel0) {
			Gui.panel0 = panel0;
		}
		public static JPanel getPanel0_0() {
			return panel0_0;
		}
		public static void setPanel0_0(JPanel panel0_0) {
			Gui.panel0_0 = panel0_0;
		}
		public static JPanel getPanel_w() {
			return panel_w;
		}
		public static void setPanel_w(JPanel panel_w) {
			Gui.panel_w = panel_w;
		}
		public static JPanel getPanel1() {
			return panel1;
		}
		public static void setPanel1(JPanel panel1) {
			Gui.panel1 = panel1;
		}
		public static JPanel getPanel1_0() {
			return panel1_0;
		}
		public static void setPanel1_0(JPanel panel1_0) {
			Gui.panel1_0 = panel1_0;
		}
		public static JPanel getPanel1_1() {
			return panel1_1;
		}
		public static void setPanel1_1(JPanel panel1_1) {
			Gui.panel1_1 = panel1_1;
		}
		public static JPanel getPanel1_2() {
			return panel1_2;
		}
		public static void setPanel1_2(JPanel panel1_2) {
			Gui.panel1_2 = panel1_2;
		}
		public static JPanel getPanel2() {
			return panel2;
		}
		public static void setPanel2(JPanel panel2) {
			Gui.panel2 = panel2;
		}
		public static JPanel getPanel2_0() {
			return panel2_0;
		}
		public static void setPanel2_0(JPanel panel2_0) {
			Gui.panel2_0 = panel2_0;
		}
		public static JPanel getPanel3() {
			return panel3;
		}
		public static void setPanel3(JPanel panel3) {
			Gui.panel3 = panel3;
		}
		public static JPanel getPanel3_0() {
			return panel3_0;
		}
		public static void setPanel3_0(JPanel panel3_0) {
			Gui.panel3_0 = panel3_0;
		}
		public static JPanel getPanel4() {
			return panel4;
		}
		public static void setPanel4(JPanel panel4) {
			Gui.panel4 = panel4;
		}
		public static JPanel getPanel4_0() {
			return panel4_0;
		}
		public static void setPanel4_0(JPanel panel4_0) {
			Gui.panel4_0 = panel4_0;
		}
		public static JPanel getPanel5() {
			return panel5;
		}
		public static void setPanel5(JPanel panel5) {
			Gui.panel5 = panel5;
		}
		public static JPanel getPanel6() {
			return panel6;
		}
		public static void setPanel6(JPanel panel6) {
			Gui.panel6 = panel6;
		}
		public static JPanel getTest() {
			return test;
		}
		public static void setTest(JPanel test) {
			Gui.test = test;
		}
		public static JPanel getPanel_chart1() {
			return panel_chart1;
		}
		public static void setPanel_chart1(JPanel panel_chart1) {
			Gui.panel_chart1 = panel_chart1;
		}
		public static JPanel getPanel_chart2() {
			return panel_chart2;
		}
		public static void setPanel_chart2(JPanel panel_chart2) {
			Gui.panel_chart2 = panel_chart2;
		}
		public static JPanel getPanel_chart3() {
			return panel_chart3;
		}
		public static void setPanel_chart3(JPanel panel_chart3) {
			Gui.panel_chart3 = panel_chart3;
		}
		public static JPanel getPanel_chart4() {
			return panel_chart4;
		}
		public static void setPanel_chart4(JPanel panel_chart4) {
			Gui.panel_chart4 = panel_chart4;
		}
		public static JPanel getPanel_chart5() {
			return panel_chart5;
		}
		public static void setPanel_chart5(JPanel panel_chart5) {
			Gui.panel_chart5 = panel_chart5;
		}
		public static JPanel getPanel_chart6() {
			return panel_chart6;
		}
		public static void setPanel_chart6(JPanel panel_chart6) {
			Gui.panel_chart6 = panel_chart6;
		}
		public static JPanel getPanel_chart7() {
			return panel_chart7;
		}
		public static void setPanel_chart7(JPanel panel_chart7) {
			Gui.panel_chart7 = panel_chart7;
		}
		public static JPanel getPanel_chart_r() {
			return panel_chart_r;
		}
		public static void setPanel_chart_r(JPanel panel_chart_r) {
			Gui.panel_chart_r = panel_chart_r;
		}
		public static JPanel getPanel_db() {
			return panel_db;
		}
		public static void setPanel_db(JPanel panel_db) {
			Gui.panel_db = panel_db;
		}
		public static JPanel getPanel_db_1() {
			return panel_db_1; 
		}
		public static void setPanel_db_1(JPanel panel_db_1) {
			Gui.panel_db_1 = panel_db_1;
		}
		public static JPanel getPanel_plot1() {
			return panel_plot1;
		}
		public static void setPanel_plot1(JPanel panel_plot1) {
			Gui.panel_plot1 = panel_plot1;
		}
		public static JPanel getPanel_plot2() {
			return panel_plot2;
		}
		public static void setPanel_plot2(JPanel panel_plot2) {
			Gui.panel_plot2 = panel_plot2;
		}
		public static JPanel getPanel_plot3() {
			return panel_plot3;
		}
		public static void setPanel_plot3(JPanel panel_plot3) {
			Gui.panel_plot3 = panel_plot3;
		}
		public static JPanel getPanel_plot4() {
			return panel_plot4;
		}
		public static void setPanel_plot4(JPanel panel_plot4) {
			Gui.panel_plot4 = panel_plot4;
		}
		public static JPanel getPanel_plot5() {
			return panel_plot5;
		}
		public static void setPanel_plot5(JPanel panel_plot5) {
			Gui.panel_plot5 = panel_plot5;
		}
		public static JPanel getPanel_plot6() {
			return panel_plot6;
		}
		public static void setPanel_plot6(JPanel panel_plot6) {
			Gui.panel_plot6 = panel_plot6;
		}
		public static JPanel getPanel_plot7() {
			return panel_plot7;
		}
		public static void setPanel_plot7(JPanel panel_plot7) {
			Gui.panel_plot7 = panel_plot7;
		}
		public static JPanel getPanel_il() {
			return panel_il;
		}
		public static void setPanel_il(JPanel panel_il) {
			Gui.panel_il = panel_il;
		}
		public static JPanel getPanel_s() {
			return panel_s;
		}
		public static void setPanel_s(JPanel panel_s) {
			Gui.panel_s = panel_s;
		}
		public static JButton getImportData() {
			return importData;
		}
		public static void setImportData(JButton importData) {
			Gui.importData = importData;
		}
		public static JButton getButton_search() {
			return button_search;
		}
		public static void setButton_search(JButton button_search) {
			Gui.button_search = button_search;
		}
		public static JButton getButton_download() {
			return button_download;
		}
		public static void setButton_download(JButton button_download) {
			Gui.button_download = button_download;
		}
		public static Dimension getScreenSize() {
			return screenSize;
		}
		public static void setScreenSize(Dimension screenSize) {
			Gui.screenSize = screenSize;
		}
		public Dimension getSize() {
			return size;
		}
		public void setSize(Dimension size) {
			Gui.size = size;
		}
		public JTextField getField() {
			return field;
		}
		public void setField(JTextField field) {
			this.field = field;
		}
		public JTextField getField_search() {
			return field_search;
		}
		public void setField_search(JTextField field_search) {
			this.field_search = field_search;
		}
		public JTextArea getArea() {
			return area;
		}
		public void setArea(JTextArea area) {
			this.area = area;
		}
		public Font getFont_0() {
			return font_0;
		}
		public void setFont_0(Font font_0) {
			this.font_0 = font_0;
		}
		public Font getFont_1() {
			return font_1;
		}
		public void setFont_1(Font font_1) {
			this.font_1 = font_1;
		}
		public Font getFont_2() {
			return font_2;
		}
		public void setFont_2(Font font_2) {
			this.font_2 = font_2;
		}
		public Date getD() {
			return d;
		}
		public void setD(Date d) {
			this.d = d;
		}
		public SimpleDateFormat getDateFormat() {
			return dateFormat;
		}
		public void setDateFormat(SimpleDateFormat dateFormat) {
			this.dateFormat = dateFormat;
		}
		public static JMenuBar getMenuBar() {
			return menuBar;
		}
		public static void setMenuBar(JMenuBar menuBar) {
			Gui.menuBar = menuBar;
		}
		public static JMenu getMenu() {
			return menu;
		}
		public static void setMenu(JMenu menu) {
			Gui.menu = menu;
		}
		public static JMenu getSubmenu() {
			return submenu;
		}
		public static void setSubmenu(JMenu submenu) {
			Gui.submenu = submenu;
		}
		public static JMenuItem getMenuItem() {
			return menuItem;
		}
		public static void setMenuItem(JMenuItem menuItem) {
			Gui.menuItem = menuItem;
		}
		public static JRadioButtonMenuItem getRbMenuItem() {
			return rbMenuItem;
		}
		public static void setRbMenuItem(JRadioButtonMenuItem rbMenuItem) {
			Gui.rbMenuItem = rbMenuItem;
		}
		public static JCheckBoxMenuItem getCbMenuItem() {
			return cbMenuItem;
		}
		public static void setCbMenuItem(JCheckBoxMenuItem cbMenuItem) {
			Gui.cbMenuItem = cbMenuItem;
		}
		public static ActionListener getAL_1() {
			return AL_1;
		}
		public static void setAL_1(ActionListener aL_1) {
			AL_1 = aL_1;
		}
		public static ActionListener getAL_2() {
			return AL_2;
		}
		public static void setAL_2(ActionListener aL_2) {
			AL_2 = aL_2;
		}
		public static ActionListener getAL_3() {
			return AL_3;
		}
		public static void setAL_3(ActionListener aL_3) {
			AL_3 = aL_3;
		}
		public String getSelection_Number() {
			return selection_Number;
		}
		public void setSelection_Number(String selection_Number) {
			this.selection_Number = selection_Number;
		}
		public String getSelection_Name() {
			return selection_Name;
		}
		public void setSelection_Name(String selection_Name) {
			this.selection_Name = selection_Name;
		}
		public static String getCwd() {
			return cwd;
		}
		public static void setCwd(String cwd) {
			Gui.cwd = cwd;
		}
		public String[] getPhase() {
			return Phase;
		}
		public void setPhase(String[] phase) {
			Phase = phase;
		}
		public String[] getWelcome() {
			return Welcome;
		}
		public void setWelcome(String[] welcome) {
			Welcome = welcome;
		}
		public String[] getDescription() {
			return Description;
		}
		public void setDescription(String[] description) {
			Description = description;
		}
		public String[] getDesign() {
			return Design;
		}
		public void setDesign(String[] design) {
			Design = design;
		}
		public String[] getC_H() {
			return C_H;
		}
		public void setC_H(String[] c_H) {
			C_H = c_H;
		}
		public String[] getC_M() {
			return C_M;
		}
		public void setC_M(String[] c_M) {
			C_M = c_M;
		}
		public String[] getC_A() {
			return C_A;
		}
		public void setC_A(String[] c_A) {
			C_A = c_A;
		}
		public String[] getOperation() {
			return Operation;
		}
		public void setOperation(String[] operation) {
			Operation = operation;
		}
		public String[] getMaintenance() {
			return Maintenance;
		}
		public void setMaintenance(String[] maintenance) {
			Maintenance = maintenance;
		}
		public String[] getScrapping() {
			return Scrapping;
		}
		public void setScrapping(String[] scrapping) {
			Scrapping = scrapping;
		}
		public ArrayList<String> getQ() {
			return q;
		}
		public void setQ(ArrayList<String> q) {
			this.q = q;
		}
		public int getActivity_length() {
			return activity_length;
		}
		public void setActivity_length(int activity_length) {
			this.activity_length = activity_length;
		}
		public JComboBox<String> getCb() {
			return cb;
		}
		public void setCb(JComboBox<String> cb) {
			this.cb = cb;
		}
		public JComboBox<String>[] getCb_m() {
			return cb_m;
		}
		public void setCb_m(JComboBox<String>[] cb_m) {
			this.cb_m = cb_m;
		}
		public JComboBox<String> getCbtest() {
			return cbtest;
		}
		public void setCbtest(JComboBox<String> cbtest) {
			this.cbtest = cbtest;
		}
		public JTable getTable_db1() {
			return table_db1;
		}
		public void setTable_db1(JTable table_db1) {
			this.table_db1 = table_db1;
		}
		public JTable getTable_db2() {
			return table_db2;
		}
		public void setTable_db2(JTable table_db2) {
			this.table_db2 = table_db2;
		}
		public Object[][] getTableData() {
			return tableData;
		}
		public void setTableData(Object[][] tableData) {
			this.tableData = tableData;
		}
		public TableModel getDTM() {
			return DTM;
		}
		public void setDTM(TableModel dTM) {
			DTM = dTM;
		}
		public int getI() {
			return i;
		}
		public void setI(int i) {
			this.i = i;
		}
		public File getFile() {
			return file;
		}
		public void setFile(File file) {
			this.file = file;
		}
		public File[] getFile_group() {
			return file_group;
		}
		public void setFile_group(File[] file_group) {
			this.file_group = file_group;
		}
		public Workbook getWb_num() {
			return wb_num;
		}
		public void setWb_num(Workbook wb_num) {
			this.wb_num = wb_num;
		}
		public int getData_length() {
			return data_length;
		}
		public void setData_length(int data_length) {
			this.data_length = data_length;
		}
		public Workbook[] getWb() {
			return wb;
		}
		public void setWb(Workbook[] wb) {
			this.wb = wb;
		}
		public Sheet[] getSheet() {
			return sheet;
		}
		public void setSheet(Sheet[] sheet) {
			this.sheet = sheet;
		}
		public Cell[][] getItem0() {
			return item0;
		}
		public void setItem0(Cell[][] item0) {
			this.item0 = item0;
		}
		public Cell[][] getCell0() {
			return cell0;
		}
		public void setCell0(Cell[][] cell0) {
			this.cell0 = cell0;
		}
		public Cell[][] getCell1() {
			return cell1;
		}
		public void setCell1(Cell[][] cell1) {
			this.cell1 = cell1;
		}
		public Cell[][] getCell2() {
			return cell2;
		}
		public void setCell2(Cell[][] cell2) {
			this.cell2 = cell2;
		}
		public Cell[][] getUnit0() {
			return unit0;
		}
		public void setUnit0(Cell[][] unit0) {
			this.unit0 = unit0;
		}
		public String[][] getContent0() {
			return content0;
		}
		public void setContent0(String[][] content0) {
			this.content0 = content0;
		}
		public String[][] getContent1() {
			return content1;
		}
		public void setContent1(String[][] content1) {
			this.content1 = content1;
		}
		public String[][] getContent2() {
			return content2;
		}
		public void setContent2(String[][] content2) {
			this.content2 = content2;
		}
		public String[][] getContent3() {
			return content3;
		}
		public void setContent3(String[][] content3) {
			this.content3 = content3;
		}
		public String[][] getContent4() {
			return content4;
		}
		public void setContent4(String[][] content4) {
			this.content4 = content4;
		}
		public String[] getChoices() {
			return choices;
		}
		public void setChoices(String[] choices) {
			this.choices = choices;
		}
		public String[] getChoices_NAME() {
			return choices_NAME;
		}
		public void setChoices_NAME(String[] choices_NAME) {
			this.choices_NAME = choices_NAME;
		}
		public double[] getA_result() {
			return a_result;
		}
		public void setA_result(double[] a_result) {
			this.a_result = a_result;
		}
		public double[] getB_result() {
			return b_result;
		}
		public void setB_result(double[] b_result) {
			this.b_result = b_result;
		}
		public double[] getC_result() {
			return c_result;
		}
		public void setC_result(double[] c_result) {
			this.c_result = c_result;
		}
		public String[] getData0() {
			return data0;
		}
		public void setData0(String[] data0) {
			this.data0 = data0;
		}
		public String[] getData1() {
			return data1;
		}
		public void setData1(String[] data1) {
			this.data1 = data1;
		}
		public String[] getData2() {
			return data2;
		}
		public void setData2(String[] data2) {
			this.data2 = data2;
		}
		public String[] getData3() {
			return data3;
		}
		public void setData3(String[] data3) {
			this.data3 = data3;
		}
		public String[] getData4() {
			return data4;
		}
		public void setData4(String[] data4) {
			this.data4 = data4;
		}
		public Object[][] getData() {
			return data;
		}
		public void setData(Object[][] data) {
			this.data = data;
		}
		public String[][] getData_m1() {
			return data_m1;
		}
		public void setData_m1(String[][] data_m1) {
			this.data_m1 = data_m1;
		}
		public String[][] getData_m2() {
			return data_m2;
		}
		public void setData_m2(String[][] data_m2) {
			this.data_m2 = data_m2;
		}
		public String[][] getData_m3() {
			return data_m3;
		}
		public void setData_m3(String[][] data_m3) {
			this.data_m3 = data_m3;
		}
		public String[][] getData_m() {
			return data_m;
		}
		public void setData_m(String[][] data_m) {
			this.data_m = data_m;
		}
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		public static Logger getLog() {
			return log;
		}

		public static Session getSession() {
			return session;
		}

		public static void setSession(Session session) {
			Gui.session = session;
		}

		public static ObjectManager getObjectManager() {
			return objectManager;
		}

		public static void setObjectManager(ObjectManager objectManager) {
			Gui.objectManager = objectManager;
		}

		public DecimalFormat getFormatter() {
			return formatter;
		}

		public void setFormatter(DecimalFormat formatter) {
			this.formatter = formatter;
		}

		public static JTabbedPane getTp0r() {
			return tp0r;
		}

		public static void setTp0r(JTabbedPane tp0r) {
			Gui.tp0r = tp0r;
		}

		public int getResult_size() {
			return result_size;
		}

		public void setResult_size(int result_size) {
			this.result_size = result_size;
		}

		public static JPanel getPanel1r() {
			return panel1r;
		}

		public static void setPanel1r(JPanel panel1r) {
			Gui.panel1r = panel1r;
		}

		public static JPanel getPanel1r_0() {
			return panel1r_0;
		}

		public static void setPanel1r_0(JPanel panel1r_0) {
			Gui.panel1r_0 = panel1r_0;
		}

		public static JPanel getPanel1r_1() {
			return panel1r_1;
		}

		public static void setPanel1r_1(JPanel panel1r_1) {
			Gui.panel1r_1 = panel1r_1;
		}

		public static JPanel getPanel1r_2() {
			return panel1r_2;
		}

		public static void setPanel1r_2(JPanel panel1r_2) {
			Gui.panel1r_2 = panel1r_2;
		}

		public String[] getR_H() {
			return R_H;
		}

		public void setR_H(String[] r_H) {
			R_H = r_H;
		}

		public String[] getR_M() {
			return R_M;
		}

		public void setR_M(String[] r_M) {
			R_M = r_M;
		}

		public String[] getR_A() {
			return R_A;
		}

		public void setR_A(String[] r_A) {
			R_A = r_A;
		}

		public String[][] getData_m0() {
			return data_m0;
		}

		public void setData_m0(String[][] data_m0) {
			this.data_m0 = data_m0;
		}

		public String[][] getData_m4() {
			return data_m4;
		}

		public void setData_m4(String[][] data_m4) {
			this.data_m4 = data_m4;
		}

		private static JTabbedPane      tp_plot3    = new JTabbedPane();
		private static JTabbedPane      tp_plot4    = new JTabbedPane();
		private static JTabbedPane      tp_plot5    = new JTabbedPane();
		private static JTabbedPane      tp_plot6    = new JTabbedPane(); 
		private static JTabbedPane      tp_plot7    = new JTabbedPane();
		private static JFrame Frame, frame, frame1,frame2,frame3,frame4,frame5,frame6,frame7, frame_r,frame_db, frame_il ;
		private static JPanel 	 panel_m, panel_n,panel_FC,	panel, 				
	panel0, panel0_0, 
	panel_w,
	panel1, panel1_0, 
			panel1_1, 
			panel1_2,
	panel1r, panel1r_0, 
			panel1r_1, 
			panel1r_2,			
	panel2, panel2_0, 
	panel3,	panel3_0, 
	panel4,	panel4_0,
	panel5,
	panel6, test,
	panel_chart1, panel_chart2, panel_chart3,panel_chart4,panel_chart5,panel_chart6,panel_chart7, panel_chart_r,panel_db,panel_db_1,
                panel_plot1,panel_plot2,panel_plot3,panel_plot4,panel_plot5,panel_plot6,panel_plot7,
                panel_il,panel_s;									//all panels
	
		private static JButton importData, button_search, button_download;
		private static Dimension screenSize = new Dimension (1280,800);//800,700);
			//Toolkit.getDefaultToolkit().getScreenSize();//new Dimension (300,200); //
		private static Dimension size = new Dimension (800,200); //frame size
		private JTextField field, field_search;						//textfield
		private JTextArea area;
	
		private Font font_0 = new Font("Arial", Font.PLAIN,14);
		private Font font_1 = new Font("Arial", Font.BOLD,24);
		private Font font_2 = new Font("Arial", Font.BOLD,36);
	
		private Date d = new Date();
		private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;	
	
	private static JMenuBar menuBar;
	private static JMenu menu, submenu;
	private static JMenuItem menuItem;
	private static JRadioButtonMenuItem rbMenuItem;
	private static JCheckBoxMenuItem cbMenuItem;
	private static ActionListener AL_1, AL_2, AL_3,AL_4;// AL_4, AL_5, AL_6, AL_7, AL_8, AL_9, AL_10, AL_11, AL_12, AL_13, AL_14, AL_15;
	private String selection_Number ;
	private String selection_Name;

    
//    static File currentJavaJarFile = new File(Gui.class.getProtectionDomain().getCodeSource().getLocation().getPath());
//    
//    static String currentJavaJarFilePath = currentJavaJarFile.getAbsolutePath();
//    static String currentRootDirectoryPath = currentJavaJarFilePath.replace(currentJavaJarFile.getName(), "");
//    
	private static String cwd = System.getProperty("user.dir");
	
	
	
//define all the strings based on phases and activities	

	public static ActionListener getAL_4() {
		return AL_4;
	}
	public static void setAL_4(ActionListener aL_4) {
		AL_4 = aL_4;
	}

	private String [] Phase = 		{"Welcome","Design", "C_H","C_M","C_A","R_H", "R_M", "R_A", "Operation","Maintenance","Scrapping", "Report","Comparisons"};
	
	private String [] Welcome = 	{"Objectives", "Impact", "Approaches"};
	private String [] Description = 	{	"Developed models of life cycel approaches (LCCA, LCA and RA) can compare different design/"
			+"\n"+ "maintenance/replacement strategiesfor ships from a through life perspecitvie by quantifying:"
			+"\n"+ "  	-Direct economic costs of production (Capex)"
			+"\n"+ "  	-Operation and maintenance cost (Opex;regular costs)"
			+"\n"+ "  	-Repair and refurbishmeng costs and end-of-life costs (Opex; one-off costs)"
			+"\n"+ "  	-Impact on environment"
			+"\n"+ " 	-Risks", 
			
			"The SHIPLYS system is intended to be used by the shipyards and associated design consultants, "
					+"\n"+ "in conjunction with their clients the owners and operators, to provide optimised design and costing"
										+"\n"+ "at the early stages of design, leading to improved life cycle"
												+ " management through production, operation,"
										+"\n"+ "refits and end of life disposal and to meet the increasing requirements for LCCA (lifecycle cost analyses), "
										+"\n"+ "environmental assessments, risk assessments and end-of-life considerations."};

	//these names should be consistent with Database folder's names 
	private String [] Design = 		{"Case Name","Life Span", "Financial data","Sensitivity level", "Ship total price","Ship Particulars"};
	private String [] C_H = 		{"Hull Cutting", "Hull Bending", "Hull Welding", "Hull Blasting","Hull Coating", "Transportation"};
	private String [] C_M = 		{"Engine", "Generator","Auxiliary", "Propulsion System Transportation","Auxiliary System Transportation", "Electricity"};
	private String [] C_A = 		{"Purchase", "Outfitting Cutting", "Outfitting Welding", "Outfitting Coating", "Transportation","Copy"};
	private String [] R_H = 		{"Hull Cutting", "Hull Bending", "Hull Welding", "Hull Blasting","Hull Coating", "Transportation"};
	private String [] R_M = 		{"Engine", "Auxiliary", "Scrubber","Transportation", "Electricity", "Copy"};
	private String [] R_A = 		{"Purchase", "Outfitting Cutting", "Outfitting Welding", "Outfitting Coating", "Transportation","Copy"};
	private String [] Operation = 	{"Operation", "Fuel oil", "Lub oil","Transportation","Copy","Copy"};
	private String [] Maintenance = {"Machinery", "Hull", "Outfitting", "Docking", "Spare","Copy"}; 
	private String [] Scrapping = 	{"Scrapping","Transportation", "Electricity", "Hull","Machinery","Outfitting"}; 
	
	
	private ArrayList<String> q = new ArrayList<String>();
	
//	String Trans_type, Electricity_type, FO_type, LO_type,Trans_type_LO, Cut_type, Trans_type_S, E_type_S, F_type_S;
	
	
	private int activity_length = Design.length+C_H.length+C_M.length+C_A.length+R_H.length+R_M.length+R_A.length+Operation.length+Maintenance.length+Scrapping.length;

//combobox	= droplist
	private JComboBox<String> cb;					 
	private JComboBox<String>[] cb_m;	
	private JComboBox<String> cbtest;
	private JTable table_db1 ;
	private JTable table_db2 ;
	private Object[][] tableData;
	private TableModel DTM;
	private int i  = 0;
	

//set up database in a default folder	
	private File file = new File (cwd+"/db/");
	private File[]file_group = file.listFiles();
	private Workbook wb_num = Workbook.getWorkbook(new File(cwd+"/db/Template/Template.xls"));
//	Workbook trans,electricity, FO, LO,trans_lo, cut, trans_s, E_s,F_s;	
//set up data length of database	
	private int data_length = wb_num.getSheet(0).getRows() ;
	private Workbook [] wb = new Workbook[file_group.length];
	private Sheet[] sheet = new Sheet[file_group.length];
	private Cell[][]	item0  = new Cell[file_group.length][data_length];
	private Cell[][]	cell0  = new Cell[file_group.length][data_length];
	private Cell[][]	cell1  = new Cell[file_group.length][data_length];
	private Cell[][]	cell2  = new Cell[file_group.length][data_length];
	private Cell[][]	unit0  = new Cell[file_group.length][data_length];
	private String[][]	content0  = new String[file_group.length][data_length];
	private String[][]	content1  = new String[file_group.length][data_length];
	private String[][]	content2  = new String[file_group.length][data_length];
	private String[][]	content3  = new String[file_group.length][data_length];
	private String[][]	content4  = new String[file_group.length][data_length];

	private String[] 	choices = new String[file_group.length];
	private String[] 	choices_NAME = new String[file_group.length];
	
	private  double [] a_result =new double[5];
	private  double [] b_result =new double[5];
	private  double [] c_result =new double[5];
	

//	WritableSheet RS = RB.createSheet("Sheet1", 0);
//	WritableCell RC = RS.getWritableCell(0, 0);
	
	
//  Workbook RB = Workbook.getWorkbook(new File(("C:/Users/tjb12178/workspace/04012018/result.xls")));

//build a template column and data comes from excel database
	
	private  String[] data0 = new String[data_length];
	private  String[] data1 = new String[data_length];	
	private  String[] data2 = new String[data_length];
	private  String[] data3 = new String[data_length];
	private  String[] data4 = new String[data_length];

//build a template table include previous column	
	private Object[][] 	data;
//build a column include the select column for each activity		
	private String[][] data_m0	= new String[data_length][activity_length];
	private String[][] data_m1	= new String[data_length][activity_length];
	private String[][] data_m2	= new String[data_length][activity_length];
	private String[][] data_m3	= new String[data_length][activity_length];
	private String[][] data_m4	= new String[data_length][activity_length];
	private String[][] data_m	= new String[data_length][activity_length];
	

                            
//construction function
	@SuppressWarnings("unchecked")
	private  Gui() throws BiffException, IOException{
		
//		System.out.println(file_group[0]);
            
            for(i=0;i<data_length;i++) {
                for(int j=0;j<activity_length;j++){
                    data_m1[i][j]="0";
                    data_m2[i][j]="0";
                    data_m3[i][j]="0";
                }
            }
            
            data_m1[4][5]="1"; data_m2[4][5]="1"; data_m3[4][5]="1";
            data_m1[2][6]="1"; data_m2[2][6]="1"; data_m3[2][6]="1";
            data_m1[2][7]="1"; data_m2[2][7]="1"; data_m3[2][7]="1";
            data_m1[2][8]="1"; data_m2[2][8]="1"; data_m3[2][8]="1";
            data_m1[2][9]="1"; data_m2[2][9]="1"; data_m3[2][9]="1";
            data_m1[2][10]="1"; data_m2[2][10]="1"; data_m3[2][10]="1";
            
            data_m1[1][17]="1"; data_m2[1][17]="1"; data_m3[1][17]="1";
            
            data_m1[2][19]="1"; data_m2[2][19]="1"; data_m3[2][19]="1";
            data_m1[2][20]="1"; data_m2[2][20]="1"; data_m3[2][20]="1";
            data_m1[2][21]="1"; data_m2[2][21]="1"; data_m3[2][21]="1";
            
            data_m1[2][24]="1"; data_m2[2][24]="1"; data_m3[2][24]="1";
            data_m1[2][25]="1"; data_m2[2][25]="1"; data_m3[2][25]="1";
            data_m1[2][26]="1"; data_m2[2][26]="1"; data_m3[2][26]="1";
            data_m1[2][27]="1"; data_m2[2][27]="1"; data_m3[2][27]="1";
            data_m1[2][28]="1"; data_m2[2][28]="1"; data_m3[2][28]="1";

            data_m1[2][37]="1"; data_m2[2][37]="1"; data_m3[2][37]="1";
            data_m1[2][38]="1"; data_m2[2][38]="1"; data_m3[2][38]="1";
            data_m1[2][39]="1"; data_m2[2][39]="1"; data_m3[2][39]="1";

            
            
//icon		
		ImageIcon ii = createImageIcon(cwd+"/pic/icon.png");	

		
//Welcome page
		panel_w = createPanel("");	//add panel 
	     tp.addTab("Welcome",ii,panel_w,"Welcome");
	     add(tp);
	     tp.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	     tp.setTabPlacement(JTabbedPane.TOP);
	     
	     panel_w.setLayout(new GridBagLayout());
	     GridBagConstraints c_w = new GridBagConstraints();
	     JPanel[] j_w = new JPanel[Welcome.length];
	     JTextArea[] area_w = new JTextArea[Welcome.length];
	     c_w.fill = GridBagConstraints.VERTICAL;

	     for (i=0;i<Welcome.length-1;i++){
	    	 j_w[i] = createNewPanel(Welcome[i]);
	    	 j_w[i].setName(Welcome[i]);
		        
			    c_w.weighty = 1;
			    c_w.gridy = i;
			    c_w.gridx = 0;
			    panel_w.add(j_w[i], c_w);
				area_w[i]=area;			    
				area_w[i].setText(Description[i]);
				area_w[i].setSelectedTextColor(Color.WHITE);
				area_w[i].setSelectionColor(Color.RED);
				area_w[i].setWrapStyleWord(true);
				area_w[i].setBackground(Color.WHITE);
				area_w[i].setFont(font_0);
				//area_w[i].setPreferredSize(new Dimension(1400, 300));//setSize(new Dimension(2400,300));
				}
			JLabel TP_lbl = new JLabel(Welcome[2]);	
			TP_lbl.setFont(font_1);
			//TP_lbl.setPreferredSize(new Dimension(300,35));
			//TP_lbl.setPreferredSize(new Dimension(50,35));
			c_w.weightx = 0;
		    c_w.gridy = 2;
		    c_w.gridx = 0;
			panel_w.add(TP_lbl,c_w);
	     	JTextPane TP = new JTextPane();
	     	
			TP.insertIcon(new ImageIcon ( cwd+"/pic/approach.png" ));
			TP.setLayout(new BorderLayout());
			//TP.add( new ImageIcon ( cwd+"/pic/approach.png" ),BorderLayout.CENTER );
			TP.setEditable(false);
			Dimension D_approach = new Dimension();
			D_approach.setSize(screenSize.getWidth()/2,screenSize.getHeight()/2);
			TP.setPreferredSize(D_approach);
		    c_w.weightx = 0;
		    c_w.weighty = 0;
		    c_w.gridy = 3;
		    c_w.gridx = 0;
			panel_w.add(TP,c_w);
			    //field_w[i]= new JTextField(Description[i]) ;

			    

	     
			    	    
	       
	     int xxx=2;
		
		
		
		
		
//Design phase
		 panel0 = createPanel("");	//add panel 
	     tp.addTab("Design",ii,panel0,"Design");
	     add(tp);
	     tp.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	     tp.setTabPlacement(JTabbedPane.TOP);
          
	     panel0.setLayout(new GridBagLayout());
	     GridBagConstraints c = new GridBagConstraints();
	     JPanel[] j0 = new JPanel[Design.length];
	     JTextField[] field0 = new JTextField[Design.length];
	     JComboBox<String>[]  cb0	=	new JComboBox[Design.length];
	     ActionListener[] db0 = new ActionListener[Design.length];
	     JButton[] IL0 = new JButton[Design.length];
	     ActionListener[] il0 = new ActionListener[Design.length];
	     
	     for (i=0;i<Design.length;i++){
	    	 j0[i] = createsubPanel(String.valueOf(i)+". "+Design[i]);
	    	 //j0[i].setName(Design[i]);

		        c.fill = GridBagConstraints.HORIZONTAL;
			    c.weightx = 2;
			    c.gridx = i%3;
			    c.gridy = Math.round(i/3);
			    if (j0[i].getName()==""){}
			    else {panel0.add(j0[i], c);}
			    field.setText("0");
			    field.setFont(font_0);
			    field0[i] = field;
			    cb0[i] = cb;
			    IL0[i] = importData;
				
//				File file_new = new File (cwd+"/db2/Template/");
//				file_group_1 =  file_new.listFiles();
//				System.out.println(cb0[0]);
				
				}    
	     
	     
//	     //add button 1 	     
//	     JButton importDataButton = new JButton("Import data from server");
//	     importDataButton.setName("importDataButton");
//	     importDataButton.setAlignmentY(Component.BOTTOM_ALIGNMENT);
//	     importDataButton.setPreferredSize(new Dimension(400,40));
//	     c.fill = GridBagConstraints.BASELINE;
//	     c.anchor = GridBagConstraints.LAST_LINE_START;
//	     c.insets = new Insets(30,85,0,0);  //top padding
//		    c.weightx = 0;
//		    c.gridx = 0;
//		    c.gridy = 3;
//	     panel0.add(importDataButton,c);
//	     
//	     importDataButton.addActionListener(new ActionListener() {
//				
//					public void actionPerformed(ActionEvent e) {
//						QueryExamplesGeneral queryExamplesGeneral = new QueryExamplesGeneral();
//						queryExamplesGeneral.run();
//				        queryExamplesGeneral.shutdown();
//					}
//	     } );

	     
//	     //add button 2	     
//	     JButton importCaseButton = new JButton("Import case from server");
//	     importCaseButton.setName("importCaseButton");
//	     importCaseButton.setAlignmentY(Component.BOTTOM_ALIGNMENT);
//	     importCaseButton.setPreferredSize(new Dimension(400,40));
//	     c.fill = GridBagConstraints.BASELINE;
//	     c.anchor = GridBagConstraints.PAGE_END;
//	     c.insets = new Insets(30,0,0,0);  //top padding
//		    c.weightx = 0;
//		    c.gridx = 1;
//		    c.gridy = 3;
//	     panel0.add(importCaseButton,c);
//	     
//	     importCaseButton.addActionListener(new ActionListener() {
//				
//					public void actionPerformed(ActionEvent e) {
//						QueryAnalysisCase queryAnalysisCase=new QueryAnalysisCase();
//				        queryAnalysisCase.runAnalysisCaseExampleRead();
//				         // clean up; if you forget this, the application may not stop or release all resources
//				        queryAnalysisCase.shutdown();
//					}
//	     } );	
//	     
//		 //add button 3	      
//	     JButton importCatalogueButton = new JButton("Import catalogue from server");
//	     importCatalogueButton.setName("importCatalogueButton");
//	     importCatalogueButton.setAlignmentX(Component.BOTTOM_ALIGNMENT);
//	     importCatalogueButton.setPreferredSize(new Dimension(400,40));
//	     c.fill = GridBagConstraints.BASELINE;
//	     c.anchor = GridBagConstraints.LAST_LINE_END;
//	     c.insets = new Insets(30,0,0,85);  //top padding
//		    c.weightx = 0;
//		    c.gridx = 2;
//		    c.gridy = 3;
//	     panel0.add(importCatalogueButton,c);
//	     
//	     importCatalogueButton.addActionListener(new ActionListener() {
//				
//					public void actionPerformed(ActionEvent e) {
//						// create a ReadCatalogue object
//				        QueryCatalogue readCatalogue = new QueryCatalogue();
//				        // run sample method
//				        readCatalogue.run();
//				        // clean up; if you forget this, the application may not stop or release all resources
//				        readCatalogue.shutdown();
//					}
//	     } );

//Construction	     
	     //C_H phase	    
	     panel1 = createPanel("");	//add panel 
	     tp.addTab("Construction",ii,panel1,"Construction");
	     panel1.add(tp0);
		 tp.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	     tp.setTabPlacement(JTabbedPane.TOP);
	     
	     panel1_0 = createPanel("");
	     tp0.addTab("Hull",ii,panel1_0,"Hull");
	     panel1_0.setLayout(new GridBagLayout());
	     GridBagConstraints c1 = new GridBagConstraints();
	     JPanel[] j1_0 = new JPanel[C_H.length];
	     JTextField[] field1_0 = new JTextField[C_H.length];
	     JComboBox<String>[]  cb1_0	=	new JComboBox[C_H.length];
	     ActionListener[] db1_0 = new ActionListener[C_H.length];
	     JButton[] IL1_0 = new JButton[C_H.length];
	     ActionListener[] il1_0 = new ActionListener[C_H.length];

	     for (i=0;i<C_H.length;i++){
	    	 j1_0[i] = createsubPanel(String.valueOf(Design.length+i)+". "+C_H[i]);
	    	 //j1_0[i].setName(C_H[i]);
		        c1.fill = GridBagConstraints.HORIZONTAL;
			    c1.weightx = 2;
			    //c1.weighty = 1;
			    c1.gridx = i%3;
			    c1.gridy = Math.round(i/3);
			    if ("".equals(j1_0[i].getName())){}
			    else {panel1_0.add(j1_0[i], c1);}
//			    File file_new = new File (cwd+"/db2/"+C_H[i]);
//			    file_group_1 =  file_new.listFiles();
			    field1_0[i] = field;
			    cb1_0[i]=cb;}
	             	     
	     //C_M phase
	     panel1_1 = createPanel("");
	     tp0.addTab("Machinery",ii,panel1_1,"Machinery");
	     panel1_1.setLayout(new GridBagLayout());
	     GridBagConstraints c2 = new GridBagConstraints();
	     JPanel[] j1_1 = new JPanel[C_M.length];
	     JTextField[] field1_1 = new JTextField[C_M.length];
	     JComboBox<String>[]  cb1_1	=	new JComboBox[C_M.length];
	     ActionListener[] db1_1 = new ActionListener[C_M.length];
	     JButton[] IL1_1 = new JButton[C_M.length];
	     ActionListener[] il1_1 = new ActionListener[C_M.length];

	     for (i=0;i<C_M.length;i++){
	    	 j1_1[i] = createsubPanel(String.valueOf(Design.length+C_H.length+i)+". "+C_M[i]);
	    	 //j1_1[i].setName(C_M[i]);
		        c2.fill = GridBagConstraints.HORIZONTAL;
			    c2.weightx = 2;
			    //c2.weighty = 1;
			    c2.gridx = i%3;
			    c2.gridy = Math.round(i/3);
			    if ("".equals(j1_1[i].getName())){}
			    else {panel1_1.add(j1_1[i], c2);}
			    field1_1[i] = field;
//			    System.out.println(C_M[i]);
//			    File file_new = new File (cwd+"/db2/"+C_M[i]);
//			    file_group_1 =  file_new.listFiles();
			    cb1_1[i]=cb;

			    }

	     //C_A phase
	     panel1_2 = createPanel("");
	     tp0.addTab("Outfitting",ii,panel1_2,"Outfitting");
	     panel1_2.setLayout(new GridBagLayout());
	     GridBagConstraints c3 = new GridBagConstraints();
	     JPanel[] j1_2 = new JPanel[C_A.length];
	     JTextField[] field1_2 = new JTextField[C_A.length];
	     JComboBox<String>[]  cb1_2	=	new JComboBox[C_A.length];
	     ActionListener[] db1_2 = new ActionListener[C_A.length];
	     JButton[] IL1_2 = new JButton[C_A.length];
	     ActionListener[] il1_2 = new ActionListener[C_A.length];

	     for (i=0;i<C_A.length;i++){
	    	 j1_2[i] = createsubPanel(String.valueOf(Design.length+C_H.length+C_M.length+i)+". "+C_A[i]);
	    	 j1_2[i].setName(C_A[i]);
		        c3.fill = GridBagConstraints.HORIZONTAL;
			    c3.weightx = 2;
			    //c3.weighty = 1;
			    c3.gridx = i%3;
			    c3.gridy = Math.round(i/3);
			    if ("".equals(j1_2[i].getName())){}
			    else {panel1_2.add(j1_2[i], c3);}
			    field1_2[i] = field;
//			    File file_new = new File (cwd+"/db2/"+C_A[i]);
//			    file_group_1 =  file_new.listFiles();
			    cb1_2[i]=cb;}
//Retrofitting
	     //R_H phase	    
	     panel1r = createPanel("");	//add panel 
	     tp.addTab("Retrofitting",ii,panel1r,"Retrofitting");
	     panel1r.add(tp0r);
		 tp.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	     tp.setTabPlacement(JTabbedPane.TOP);
	     
	     panel1r_0 = createPanel("");
	     tp0r.addTab("Hull-Retrofitting",ii,panel1r_0,"Hull-Retrofitting");
	     panel1r_0.setLayout(new GridBagLayout());
	     GridBagConstraints c1r = new GridBagConstraints();
	     JPanel[] j1r_0 = new JPanel[C_H.length];
	     JTextField[] field1r_0 = new JTextField[C_H.length];
	     JComboBox<String>[]  cb1r_0	=	new JComboBox[C_H.length];
	     ActionListener[] db1r_0 = new ActionListener[C_H.length];
	     JButton[] IL1r_0 = new JButton[C_H.length];
	     ActionListener[] il1r_0 = new ActionListener[C_H.length];

	     for (i=0;i<R_H.length;i++){
	    	 j1r_0[i] = createsubPanel(String.valueOf(Design.length+C_H.length+C_M.length+C_A.length+i)+". "+R_H[i]);
	    	 j1r_0[i].setName(R_H[i]);
		        c1r.fill = GridBagConstraints.HORIZONTAL;
			    c1r.weightx = 2;
			    //c1.weighty = 1;
			    c1r.gridx = i%3;
			    c1r.gridy = Math.round(i/3);
			    if ("".equals(j1r_0[i].getName())){}
			    else {panel1r_0.add(j1r_0[i], c1r);}
//			    File file_new = new File (cwd+"/db2/"+C_H[i]);
//			    file_group_1 =  file_new.listFiles();
			    field1r_0[i] = field;
			    cb1r_0[i]=cb;}
	             	     
//R_M phase
	     panel1r_1 = createPanel("");
	     tp0r.addTab("Machinery-Retrofitting",ii,panel1r_1,"Machinery-Retrofitting");
	     panel1r_1.setLayout(new GridBagLayout());
	     GridBagConstraints c2r = new GridBagConstraints();
	     JPanel[] j1r_1 = new JPanel[C_M.length];
	     JTextField[] field1r_1 = new JTextField[C_M.length];
	     JComboBox<String>[]  cb1r_1	=	new JComboBox[C_M.length];
	     ActionListener[] db1r_1 = new ActionListener[C_M.length];
	     JButton[] IL1r_1 = new JButton[C_M.length];
	     ActionListener[] il1r_1 = new ActionListener[C_M.length];

	     for (i=0;i<R_M.length;i++){
	    	 j1r_1[i] = createsubPanel(String.valueOf(Design.length+C_H.length+C_M.length+C_A.length+R_H.length+i)+". "+R_M[i]);
	    	 j1r_1[i].setName(R_M[i]);
		        c2r.fill = GridBagConstraints.HORIZONTAL;
			    c2r.weightx = 2;
			    //c2.weighty = 1;
			    c2r.gridx = i%3;
			    c2r.gridy = Math.round(i/3);
			    if ("".equals(j1r_1[i].getName())){}
			    else {panel1r_1.add(j1r_1[i], c2r);}
			    field1r_1[i] = field;
//			    System.out.println(C_M[i]);
//			    File file_new = new File (cwd+"/db2/"+C_M[i]);
//			    file_group_1 =  file_new.listFiles();
			    cb1r_1[i]=cb;

			    }

//R_A phase
	     panel1r_2 = createPanel("");
	     tp0r.addTab("Outfitting-Retrofitting",ii,panel1r_2,"Outfitting-Retrofitting");
	     panel1r_2.setLayout(new GridBagLayout());
	     GridBagConstraints c3r = new GridBagConstraints();
	     JPanel[] j1r_2 = new JPanel[C_A.length];
	     JTextField[] field1r_2 = new JTextField[C_A.length];
	     JComboBox<String>[]  cb1r_2	=	new JComboBox[C_A.length];
	     ActionListener[] db1r_2 = new ActionListener[C_A.length];
	     JButton[] IL1r_2 = new JButton[C_A.length];
	     ActionListener[] il1r_2 = new ActionListener[C_A.length];

	     for (i=0;i<R_A.length;i++){
	    	 j1r_2[i] = createsubPanel(String.valueOf(Design.length+C_H.length+C_M.length+C_A.length+R_H.length+R_M.length+i)+". "+R_A[i]);
	    	 j1r_2[i].setName(R_A[i]);
		        c3r.fill = GridBagConstraints.HORIZONTAL;
			    c3r.weightx = 2;
			    //c3.weighty = 1;
			    c3r.gridx = i%3;
			    c3r.gridy = Math.round(i/3);
			    if ("".equals(j1r_2[i].getName())){}
			    else {panel1r_2.add(j1r_2[i], c3r);}
			    field1r_2[i] = field;
//			    File file_new = new File (cwd+"/db2/"+C_A[i]);
//			    file_group_1 =  file_new.listFiles();
			    cb1r_2[i]=cb;}	     
	     
	     
	     
//Operation phase	     
	     panel2 = createPanel("");	//add panel 
		 tp.addTab("Operation",ii,panel2,"Operation");
	     tp.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	     tp.setTabPlacement(JTabbedPane.TOP);
         panel2.setLayout(new GridBagLayout());
	     GridBagConstraints c4 = new GridBagConstraints();
	     JPanel[] j2 = new JPanel[Operation.length];
	     JTextField[] field2 = new JTextField[Operation.length];
	     JComboBox<String>[]  cb2	=	new JComboBox[Operation.length];
	     ActionListener[] db2 = new ActionListener[Operation.length];
	     JButton[] IL2 = new JButton[Operation.length];
	     ActionListener[] il2 = new ActionListener[Operation.length];

//	     for(i=0;i<Design.length+C_H.length+C_M.length+C_A.length+R_H.length+R_M.length+R_A.length+i)+". "+Operation[i]);
	     for (i=0;i<Operation.length;i++){
	    	 j2[i] = createsubPanel(String.valueOf(Design.length+C_H.length+C_M.length+C_A.length+R_H.length+R_M.length+R_A.length+i)+". "+Operation[i]);
	    	 j2[i].setName(Operation[i]);
		        c4.fill = GridBagConstraints.HORIZONTAL;
			    c4.weightx = 2;
			    //c4.weighty = 1;
			    c4.gridx = i%3;
			    c4.gridy = Math.round(i/3);
			    if ("".equals(j2[i].getName())){}
			    else {panel2.add(j2[i], c4);}
//			    File file_new = new File (cwd+"/db2/"+Operation[i]);
//			    file_group_1 =  file_new.listFiles();
			    field2[i] = field;
			    cb2[i]=cb;}
	     
//Maintenance phase	    
	     panel3 = createPanel("");	//add panel 
		 tp.addTab("Maintenance",ii,panel3,"Maintenance");
	     tp.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	     tp.setTabPlacement(JTabbedPane.TOP);
	     panel3.setLayout(new GridBagLayout());
	     GridBagConstraints c5 = new GridBagConstraints();
	     JPanel[] j3 = new JPanel[Maintenance.length];
	     JTextField[] field3 = new JTextField[Maintenance.length];
	     JComboBox<String>[]  cb3	=	new JComboBox[Maintenance.length];
	     ActionListener[] db3 = new ActionListener[Maintenance.length];
	     JButton[] IL3 = new JButton[Maintenance.length];
	     ActionListener[] il3 = new ActionListener[Maintenance.length];

	     for (i=0;i<Maintenance.length;i++){
	    	 j3[i] = createsubPanel(String.valueOf(Design.length+C_H.length+C_M.length+C_A.length+R_H.length+R_M.length+R_A.length+Operation.length+i)+". "+Maintenance[i]);
	    	 j3[i].setName(Maintenance[i]);
		        c5.fill = GridBagConstraints.HORIZONTAL;
			    c5.weightx = 2;
			    //c5.weighty = 1;
			    c5.gridx = i%3;
			    c5.gridy = Math.round(i/3);
			    if ("".equals(j3[i].getName())){}
			    else {panel3.add(j3[i], c5);}
//			    File file_new = new File (cwd+"/db2/"+Maintenance[i]);
//			    file_group_1 =  file_new.listFiles();
			    field3[i] = field;
			    cb3[i]=cb;}
	     
//Scrapping phase	    
	     panel4 = createPanel("");	//add panel 
		 tp.addTab("Scrapping",ii,panel4,"Scrapping");
	     tp.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	     tp.setTabPlacement(JTabbedPane.TOP);
	     panel4.setLayout(new GridBagLayout());
	     GridBagConstraints c6 = new GridBagConstraints();
	     JPanel[] j4 = new JPanel[Scrapping.length];
	     JTextField[] field4 = new JTextField[Scrapping.length];
	     JComboBox<String>[]  cb4	=	new JComboBox[Scrapping.length];
	     ActionListener[] db4 = new ActionListener[Scrapping.length];
	     JButton[] IL4 = new JButton[Scrapping.length];
	     ActionListener[] il4 = new ActionListener[Scrapping.length];

	     for (i=0;i<Scrapping.length;i++){
	    	 if(i>=Scrapping.length){
	    		 break;
	    	 }
	    	 j4[i] = createsubPanel(String.valueOf(Design.length+C_H.length+C_M.length+C_A.length+R_H.length+R_M.length+R_A.length+Operation.length+Maintenance.length+i)+". "+Scrapping[i]);
		      j4[i].setName(Scrapping[i]);
		        c6.fill = GridBagConstraints.HORIZONTAL;
			    c6.weightx = 2;
			    //c6.weighty = 1;
			    c6.gridx = i%3;
			    c6.gridy = Math.round(i/3);
			    if ("".equals(j4[i].getName())){}
			    else {panel4.add(j4[i], c6);}
//			    File file_new = new File (cwd+"/db2/"+Scrapping[i]);
//			    file_group_1 =  file_new.listFiles();
			    field4[i] = field;
			    cb4[i]=cb;}
	     
//list all combobox in a array and add action for each combobox
	     cb_m = new JComboBox[activity_length];
	     for(int j=0; j<activity_length;j++)
	     {
	    	 if(j<Design.length){
	    		 cb_m[j]=cb0[j];
	    		 db0[j]=createActionListener(j);
//	    		 File file_new = new File (cwd+"/db2/"+String.valueOf(j)+ ". "+Design[j]);
//	    		 file_group_1 =  file_new.listFiles();
	    	 	 cb0[j].addActionListener(db0[j]);
	    	 	 
	    	 	 //cb0[j].setName(db0[j].getClass().getDeclaredAnnotations().toString());
	    	 	 }
	    	 
	    	 if(Design.length<=j&&j<Design.length+C_H.length){
	    		 cb_m[j]=cb1_0[j-Design.length];
	    	 	 db1_0[j-Design.length]=createActionListener(j);
//	    	 	File file_new = new File (cwd+"/db2/"+String.valueOf(j)+ ". "+C_H[j-Design.length]);
//	    	 	file_group =  file_new.listFiles();
	    	 	 cb1_0[j-Design.length].addActionListener(db1_0[j-Design.length]);}
	    	 
	    	 if(Design.length+C_H.length<=j&&j<Design.length+C_H.length+C_M.length){
	    		 cb_m[j]=cb1_1[j-Design.length-C_H.length];
	    		 db1_1[j-Design.length-C_H.length]=createActionListener(j);
//	    		 File file_new = new File (cwd+"/db2/"+String.valueOf(j)+". "+C_M[j-Design.length-C_H.length]);
//	    		 file_group_1 =  file_new.listFiles();
	    	 	 cb1_1[j-Design.length-C_H.length].addActionListener(db1_1[j-Design.length-C_H.length]);}
	    	 
	    	 if(Design.length+C_H.length+C_M.length<=j&&j<Design.length+C_H.length+C_M.length+C_A.length){
	    		 cb_m[j]=cb1_2[j-Design.length-C_H.length-C_M.length];
	    		 db1_2[j-Design.length-C_H.length-C_M.length]=createActionListener(j);
//	    		 File file_new = new File (cwd+"/db2/"+String.valueOf(j)+". "+C_A[j-Design.length-C_H.length-C_M.length]);
//	    		 file_group_1 =  file_new.listFiles();
	    	 	 cb1_2[j-Design.length-C_H.length-C_M.length].addActionListener(db1_2[j-Design.length-C_H.length-C_M.length]);}
	    	 
	    	 
	    	 //////////////////
	    	 if(Design.length+C_H.length+C_M.length+C_A.length<=j&&j<Design.length+C_H.length+C_M.length+C_A.length+R_H.length){
	    		 cb_m[j]=cb1r_0[j-Design.length-C_H.length-C_M.length-C_A.length];
	    	 	 db1r_0[j-Design.length-C_H.length-C_M.length-C_A.length]=createActionListener(j);
//	    	 	File file_new = new File (cwd+"/db2/"+String.valueOf(j)+ ". "+C_H[j-Design.length+C_H.length+C_M.length+C_A.length]);
//	    	 	file_group =  file_new.listFiles();
	    	 	 cb1r_0[j-Design.length-C_H.length-C_M.length-C_A.length].addActionListener(db1r_0[j-Design.length-C_H.length-C_M.length-C_A.length]);}
	    	 
	    	 if(Design.length+C_H.length+C_M.length+C_A.length+R_H.length<=j&&j<Design.length+C_H.length+C_M.length+C_A.length+R_H.length+R_M.length){
	    		 cb_m[j]=cb1r_1[j-Design.length-C_H.length-C_M.length-C_A.length-R_H.length];
	    		 db1r_1[j-Design.length-C_H.length-C_M.length-C_A.length-R_H.length]=createActionListener(j);
//	    		 File file_new = new File (cwd+"/db2/"+String.valueOf(j)+". "+C_M[j-Design.length+C_H.length+C_M.length+C_A.length-C_H.length]);
//	    		 file_group_1 =  file_new.listFiles();
	    	 	 cb1r_1[j-Design.length-C_H.length-C_M.length-C_A.length-R_H.length].addActionListener(db1r_1[j-Design.length-C_H.length-C_M.length-C_A.length-R_H.length]);}
	    	 
	    	 if(Design.length+C_H.length+C_M.length+C_A.length+R_H.length+R_M.length<=j&&j<Design.length+C_H.length+C_M.length+C_A.length+R_H.length+R_M.length+R_A.length){
	    		 cb_m[j]=cb1r_2[j-Design.length-C_H.length-C_M.length-C_A.length-R_H.length-R_M.length];
	    		 db1r_2[j-Design.length-C_H.length-C_M.length-C_A.length-R_H.length-R_M.length]=createActionListener(j);
//	    		 File file_new = new File (cwd+"/db2/"+String.valueOf(j)+". "+C_A[j-Design.length+C_H.length+C_M.length+C_A.length-C_H.length-C_M.length]);
//	    		 file_group_1 =  file_new.listFiles();
	    	 	 cb1r_2[j-Design.length-C_H.length-C_M.length-C_A.length-R_H.length-R_M.length].addActionListener(db1r_2[j-Design.length-C_H.length-C_M.length-C_A.length-R_H.length-R_M.length]);}
	    	 /////////////////
	    	 
	    	 if(Design.length+C_H.length+C_M.length+C_A.length+R_H.length+R_M.length+R_A.length<=j&&j<Design.length+C_H.length+C_M.length+C_A.length+R_H.length+R_M.length+R_A.length+Operation.length){
	    		 cb_m[j]=cb2[j-Design.length-C_H.length-C_M.length-C_A.length-R_H.length-R_M.length-R_A.length];
	    		 db2[j-Design.length-C_H.length-C_M.length-C_A.length-R_H.length-R_M.length-R_A.length]=createActionListener(j);
//	    		 File file_new = new File (cwd+"/db2/"+String.valueOf(j)+". "+Operation[j-Design.length-C_H.length-C_M.length-C_A.length-R_H.length-R_M.length-R_A.length]);
//	    		 file_group_1 =  file_new.listFiles();
	    	 	 cb2[j-Design.length-C_H.length-C_M.length-C_A.length-R_H.length-R_M.length-R_A.length].addActionListener(db2[j-Design.length-C_H.length-C_M.length-C_A.length-R_H.length-R_M.length-R_A.length]);}
	    	 
	    	 if(Design.length+C_H.length+C_M.length+C_A.length+R_H.length+R_M.length+R_A.length+Operation.length<=j&&j<Design.length+C_H.length+C_M.length+C_A.length+R_H.length+R_M.length+R_A.length+Operation.length+Maintenance.length){
	    		 cb_m[j]=cb3[j-Design.length-C_H.length-C_M.length-C_A.length-R_H.length-R_M.length-R_A.length-Operation.length];
//	    		 File file_new = new File (cwd+"/db2/"+String.valueOf(j)+". "+Maintenance[j-Design.length-C_H.length-C_M.length-C_A.length-R_H.length-R_M.length-R_A.length-Operation.length]);
//	    		 file_group_1 =  file_new.listFiles();
	    		 db3[j-Design.length-C_H.length-C_M.length-C_A.length-R_H.length-R_M.length-R_A.length-Operation.length]=createActionListener(j);
	    		 cb3[j-Design.length-C_H.length-C_M.length-C_A.length-R_H.length-R_M.length-R_A.length-Operation.length].addActionListener(db3[j-Design.length-C_H.length-C_M.length-C_A.length-R_H.length-R_M.length-R_A.length-Operation.length]);}
	    	 
	    	 else if(Design.length+C_H.length+C_M.length+C_A.length+R_H.length+R_M.length+R_A.length+Operation.length+Maintenance.length<=j&&j<activity_length){
	    		 cb_m[j]=cb4[j-Design.length-C_H.length-C_M.length-C_A.length-R_H.length-R_M.length-R_A.length-Operation.length-Maintenance.length];
	    		 db4[j-Design.length-C_H.length-C_M.length-C_A.length-R_H.length-R_M.length-R_A.length-Operation.length-Maintenance.length]=createActionListener(j);
//	    		 File file_new = new File (cwd+"/db2/"+String.valueOf(j)+". "+Scrapping[j-Design.length-C_H.length-C_M.length-C_A.length-R_H.length-R_M.length-R_A.length-Operation.length-Maintenance.length]);
//	    		 file_group_1 =  file_new.listFiles();
	    		 cb4[j-Design.length-C_H.length-C_M.length-C_A.length-R_H.length-R_M.length-R_A.length-Operation.length-Maintenance.length].addActionListener(db4[j-Design.length-C_H.length-C_M.length-C_A.length-R_H.length-R_M.length-R_A.length-Operation.length-Maintenance.length]);}
	     }
		
// 
//Report	    
	     panel5 = createPanel("");	//add panel 
		 tp.addTab("Report",ii,panel5,"Report");
	     tp.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	     tp.setTabPlacement(JTabbedPane.TOP);
	     panel5.setLayout(new BoxLayout(panel5, BoxLayout.PAGE_AXIS));
	     setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
	     
	     JTextPane textReport = new JTextPane();
	     textReport.setAlignmentX(Component.CENTER_ALIGNMENT);
//	     textReport.setFont(new Font("Arial", Font.PLAIN, 12));
//	     textReport.setContentType("text/html");
	     //textReport.setEditable(true);
//	     textReport.s;;
	     JScrollPane sp = new JScrollPane(textReport);
	     sp.setPreferredSize(new Dimension(800,400));
//	     areaReport.setLineWrap(true);
//	     areaReport.setAutoscrolls(true);
//		 textReport.setPreferredSize(new Dimension (200,200));
  
	     panel5.add(sp);
	     
//add button	     
	     JButton calcButton = new JButton("Calculate");
	     calcButton.setName("calcButton");
	     calcButton.setAlignmentX(Component.CENTER_ALIGNMENT);
	     panel5.add(calcButton);
	     
//	     JButton saveButton = new JButton("Save");
//	     saveButton.setName("saveButton");
//	     saveButton.setAlignmentX(Component.BOTTOM_ALIGNMENT);
//	     panel5.add(saveButton);
//	     
//	     JButton compButton = new JButton("Compare");
//	     compButton.setName("compButton");
//	     compButton.setAlignmentX(Component.CENTER_ALIGNMENT);
//	     panel5.add(compButton);

//add action for clicking button
	     //calc
	     calcButton.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent e) {

                            try { 
                                //include all the equations here
//				Parameter_C_System CS1 = new Parameter_C_System();
//				Parameter_C_Material CM1 = new Parameter_C_Material();
//				Parameter_O O1 = new Parameter_O();
//				Parameter_S S1 = new Parameter_S();

//Initialise the general value xxxxxx delete while publishing new versions xxxxxxx
String case_name =	"Test Case";
double Life_span = 28;
double SL = 0;
double CoTL =1222222;

double PV=0;
double Interest =0;
                            	
//double PV =Double.parseDouble(data_m1[1][2]); //0 means not using present value; 1 means using;
//double Life_span = Double.parseDouble(field0[1].getText()); //Life span of target (year)
//double Interest= Double.parseDouble(data_m1[2][2]); //Interest rate (100%)
//String case_name = field0[0].getText();						//project name
//	LCAdataCreation.dts.setProjectName(case_name);
//uncertainty level
//double SL= Double.parseDouble(field0[3].getText());			//sensitivity level
//double CoTL = Double.parseDouble(field0[4].getText());		//ship total price
String PV_state, SL_state = null;
if(PV==0){PV_state = "Present value is not applied;";
System.out.println(PV_state);}
else{PV_state = "Present value is applied;";
System.out.println(PV_state);}
if(SL==0){SL_state="Average value is applied;";
System.out.println(SL_state);
for(int i = 0; i<data_length; i++){
    for(int j = 0; j<activity_length; j++){
        data_m[i][j]=data_m1[i][j];			}}}
if(SL==1){SL_state="Minimum value is applied;";
System.out.println(SL_state);
for(int i = 0; i<data_length; i++){
    for(int j = 0; j<activity_length; j++){
        data_m[i][j]=data_m2[i][j];			}}}
if(SL==2){SL_state="Maximum value is applied;";
System.out.println(SL_state);
for(int i = 0; i<data_length; i++){
    for(int j = 0; j<activity_length; j++){
        data_m[i][j]=data_m3[i][j];			}}}
//Construction
//System: ME, MG, Boiler...
Parameter_C_System CS1 = new Parameter_C_System();
CS1.	Engine_type	=	data_m[0][12];					//Engine type
//	LCAdataCreation.createCatalogueAndProductComponents();
CS1.	Number	=	Double.parseDouble(data_m[1][12])	; //Number of items
CS1.	Weight	=	Double.parseDouble(data_m[2][12])	; //Weight of item (ton)
CS1.	Price	=	Double.parseDouble(data_m[3][12])	; //Item price (Euro/ton)
CS1.	Transportation_type = cb_m[15].getName();							//transportation type
CS1.	Transportation_distance	=	Double.parseDouble(data_m[1][15])	; //Distance of item transportation (km)
CS1.	Transportation_fee	=	Double.parseDouble(data_m[2][15])	; //Transportation price per km (Euro/km)
CS1.	Transportation_SFOC	=	Double.parseDouble(data_m[3][15])	; //Transportation fuel consumption (ton/km)
CS1.	Transportation_fuel_price	=	Double.parseDouble(data_m[4][15])	; //Fuel price of transportation (Euro/ton)
CS1.	Electricity_type = cb_m[17].getName(); //Electricity_type
CS1.	Installation_energy_price	=	Double.parseDouble(data_m[1][17])	; //Energy price (Euro/unit)
CS1. 	Spec_GWP_Trans 	=	Double.parseDouble(data_m[5][15])	; //Specific GWP of transportation (ton CO2e/ ton fuel used for transportation)
CS1. 	Spec_AP_Trans 	=	Double.parseDouble(data_m[6][15])	; //Specific AP of transportation (ton SO2e/ ton fuel used for transportation)
CS1. 	Spec_EP_Trans 	=	Double.parseDouble(data_m[7][15])	; //Specific EP of transportation (ton PO4e/ ton fuel used for transportation)
CS1. 	Spec_POCP_Trans 	=	Double.parseDouble(data_m[8][15])	; //Specific POCP of transportation (ton C2H6e/ ton fuel used for transportation)
CS1. 	Spec_GWP_E 	=	Double.parseDouble(data_m[2][17])	; //Specific GWP of energy consumption (ton CO2e/ MJ energy used)
CS1. 	Spec_AP_E 	=	Double.parseDouble( data_m[3][17]); //Specific AP of energy consumption (ton SO2e/ MJ energy used)
CS1. 	Spec_EP_E 	=	Double.parseDouble( data_m[4][17])	; //Specific EP of energy consumption (ton PO4e/ MJ energy used)
CS1. 	Spec_POCP_E 	=	Double.parseDouble(data_m[5][17])	; //Specific POCP of energy consumption (ton C2H6e/ MJ energy used)
CS1.H = 7;
CS1.F[0]=Double.parseDouble(data_m1[10][12]);
CS1.F[1]=Double.parseDouble(data_m1[11][12]);
CS1.F[2]=Double.parseDouble(data_m1[12][12]);
CS1.F[3]=Double.parseDouble(data_m1[13][12]);
CS1.F[4]=Double.parseDouble(data_m1[14][12]);
CS1.F[5]=Double.parseDouble(data_m1[15][12]);
CS1.F[6]=Double.parseDouble(data_m1[16][12]);
CS1.C[0]=Double.parseDouble(data_m2[10][12]);
CS1.C[1]=Double.parseDouble(data_m2[11][12]);
CS1.C[2]=Double.parseDouble(data_m2[12][12]);
CS1.C[3]=Double.parseDouble(data_m2[13][12]);
CS1.C[4]=Double.parseDouble(data_m2[14][12]);
CS1.C[5]=Double.parseDouble(data_m2[15][12]);
CS1.C[6]=Double.parseDouble(data_m2[16][12]);
CS1.M[0]=Double.parseDouble(data_m3[10][12]);
CS1.M[1]=Double.parseDouble(data_m3[11][12]);
CS1.M[2]=Double.parseDouble(data_m3[12][12]);
CS1.M[3]=Double.parseDouble(data_m3[13][12]);
CS1.M[4]=Double.parseDouble(data_m3[14][12]);
CS1.M[5]=Double.parseDouble(data_m3[15][12]);
CS1.M[6]=Double.parseDouble(data_m3[16][12]);
CS1.CoTL =CoTL;
CS1.run(); //Run the calculation
Parameter_C_System CS2 = new Parameter_C_System();
CS2.	Engine_type = data_m[0][14];					//battery name
CS2.	Number	=	Double.parseDouble(data_m[1][14])	; //Number of items
CS2.	Weight	=	Double.parseDouble(data_m[2][14])	; //Weight of item (ton)
CS2.	Price	=	Double.parseDouble(data_m[3][14])	; //Item price (Euro/ton)
CS2.	Transportation_type = data_m[0][16];							//transportation type
CS2.	Transportation_distance	=	Double.parseDouble(data_m[1][16])	; //Distance of item transportation (km)
CS2.	Transportation_fee	=	Double.parseDouble(data_m[2][16])	; //Transportation fee per km (Euro/km)
CS2.	Transportation_SFOC	=	Double.parseDouble(data_m[3][16])	; //Transportation fuel consumption (ton/km)
CS2.	Transportation_fuel_price	=	Double.parseDouble(data_m[4][16])	; //Fuel price of transportation (Euro/ton)
CS2.	Electricity_type = data_m[0][17]; //Electricity_type
CS2.	Installation_energy_price	=	Double.parseDouble(data_m[1][17])	; //Energy price (Euro/unit)
CS2. Spec_GWP_Trans 	=	Double.parseDouble(data_m[5][16])	; //Specific GWP of transportation (ton CO2e/ ton fuel used for transportation)
CS2. Spec_AP_Trans 	=	Double.parseDouble(data_m[6][16])	; //Specific AP of transportation (ton SO2e/ ton fuel used for transportation)
CS2. Spec_EP_Trans 	=	Double.parseDouble(data_m[7][16])	; //Specific EP of transportation (ton PO4e/ ton fuel used for transportation)
CS2. Spec_POCP_Trans 	=	Double.parseDouble(data_m[8][16])	; //Specific POCP of transportation (ton C2H6e/ ton fuel used for transportation)
CS2. Spec_GWP_E 	=	Double.parseDouble(data_m[2][17])	; //Specific GWP of energy consumption (ton CO2e/ MJ energy used)
CS2. Spec_AP_E 	=	Double.parseDouble( data_m[3][17]); //Specific AP of energy consumption (ton SO2e/ MJ energy used)
CS2. Spec_EP_E 	=	Double.parseDouble( data_m[4][17])	; //Specific EP of energy consumption (ton PO4e/ MJ energy used)
CS2. Spec_POCP_E 	=	Double.parseDouble(data_m[5][17])	; //Specific POCP of energy consumption (ton C2H6e/ MJ energy used)
CS2.H = 7;
CS2.F[0]=Double.parseDouble(data_m1[12][14]);
CS2.F[1]=Double.parseDouble(data_m1[13][14]);
CS2.F[2]=Double.parseDouble(data_m1[14][14]);
CS2.F[3]=Double.parseDouble(data_m1[15][14]);
CS2.F[4]=Double.parseDouble(data_m1[16][14]);
CS2.F[5]=Double.parseDouble(data_m1[17][14]);
CS2.F[6]=Double.parseDouble(data_m1[18][14]);
CS2.C[0]=Double.parseDouble(data_m2[12][14]);
CS2.C[1]=Double.parseDouble(data_m2[13][14]);
CS2.C[2]=Double.parseDouble(data_m2[14][14]);
CS2.C[3]=Double.parseDouble(data_m2[15][14]);
CS2.C[4]=Double.parseDouble(data_m2[16][14]);
CS2.C[5]=Double.parseDouble(data_m2[17][14]);
CS2.C[6]=Double.parseDouble(data_m2[18][14]);
CS2.M[0]=Double.parseDouble(data_m3[12][14]);
CS2.M[1]=Double.parseDouble(data_m3[13][14]);
CS2.M[2]=Double.parseDouble(data_m3[14][14]);
CS2.M[3]=Double.parseDouble(data_m3[15][14]);
CS2.M[4]=Double.parseDouble(data_m3[16][14]);
CS2.M[5]=Double.parseDouble(data_m3[17][14]);
CS2.M[6]=Double.parseDouble(data_m3[18][14]);
CS2.CoTL =CoTL;
CS2.run(); //Run the calculation


Parameter_C_System CS3 = new Parameter_C_System();
CS3.	Engine_type = data_m[0][13];					//Genset name
CS3.	Number	=	Double.parseDouble(data_m[1][13])	; //Number of items
CS3.	Weight	=	Double.parseDouble(data_m[2][13])	; //Weight of item (ton)
CS3.	Price	=	Double.parseDouble(data_m[3][13])	; //Item price (Euro/ton)
CS3.	Transportation_type = data_m[0][15];							//transportation type
CS3.	Transportation_distance	=	Double.parseDouble(data_m[1][15])	; //Distance of item transportation (km)
CS3.	Transportation_fee	=	Double.parseDouble(data_m[2][15])	; //Transportation fee per km (Euro/km)
CS3.	Transportation_SFOC	=	Double.parseDouble(data_m[3][15])	; //Transportation fuel consumption (ton/km)
CS3.	Transportation_fuel_price	=	Double.parseDouble(data_m[4][15])	; //Fuel price of transportation (Euro/ton)
CS3.	Electricity_type = data_m[0][17]; //Electricity_type
CS3.	Installation_energy_price	=	Double.parseDouble(data_m[1][17])	; //Energy price (Euro/unit)
CS3. Spec_GWP_Trans 	=	Double.parseDouble(data_m[5][15])	; //Specific GWP of transportation (ton CO2e/ ton fuel used for transportation)
CS3. Spec_AP_Trans 	=	Double.parseDouble(data_m[6][15])	; //Specific AP of transportation (ton SO2e/ ton fuel used for transportation)
CS3. Spec_EP_Trans 	=	Double.parseDouble(data_m[7][15])	; //Specific EP of transportation (ton PO4e/ ton fuel used for transportation)
CS3. Spec_POCP_Trans 	=	Double.parseDouble(data_m[8][15])	; //Specific POCP of transportation (ton C2H6e/ ton fuel used for transportation)
CS3. Spec_GWP_E 	=	Double.parseDouble(data_m[2][17])	; //Specific GWP of energy consumption (ton CO2e/ MJ energy used)
CS3. Spec_AP_E 	=	Double.parseDouble( data_m[3][17]); //Specific AP of energy consumption (ton SO2e/ MJ energy used)
CS3. Spec_EP_E 	=	Double.parseDouble( data_m[4][17])	; //Specific EP of energy consumption (ton PO4e/ MJ energy used)
CS3. Spec_POCP_E 	=	Double.parseDouble(data_m[5][17])	; //Specific POCP of energy consumption (ton C2H6e/ MJ energy used)
CS3.H = 7;
CS3.F[0]=Double.parseDouble(data_m1[10][13]);
CS3.F[1]=Double.parseDouble(data_m1[11][13]);
CS3.F[2]=Double.parseDouble(data_m1[12][13]);
CS3.F[3]=Double.parseDouble(data_m1[13][13]);
CS3.F[4]=Double.parseDouble(data_m1[14][13]);
CS3.F[5]=Double.parseDouble(data_m1[15][13]);
CS3.F[6]=Double.parseDouble(data_m1[16][13]);
CS3.C[0]=Double.parseDouble(data_m2[10][13]);
CS3.C[1]=Double.parseDouble(data_m2[11][13]);
CS3.C[2]=Double.parseDouble(data_m2[12][13]);
CS3.C[3]=Double.parseDouble(data_m2[13][13]);
CS3.C[4]=Double.parseDouble(data_m2[14][13]);
CS3.C[5]=Double.parseDouble(data_m2[15][13]);
CS3.C[6]=Double.parseDouble(data_m2[16][13]);
CS3.M[0]=Double.parseDouble(data_m3[10][13]);
CS3.M[1]=Double.parseDouble(data_m3[11][13]);
CS3.M[2]=Double.parseDouble(data_m3[12][13]);
CS3.M[3]=Double.parseDouble(data_m3[13][13]);
CS3.M[4]=Double.parseDouble(data_m3[14][13]);
CS3.M[5]=Double.parseDouble(data_m3[15][13]);
CS3.M[6]=Double.parseDouble(data_m3[16][13]);
CS3.CoTL =CoTL;
CS3.run(); //Run the calculation
//Material: steel, aluminium, composite material...
Parameter_C_Material CM1 = new Parameter_C_Material();
//

CM1.	LOA	=Double.parseDouble(data_m[	1	][5]);
CM1.	LBP	=Double.parseDouble(data_m[	2	][5]);
CM1.	B	=Double.parseDouble(data_m[	3	][5]);	
CM1.	D	=Double.parseDouble(data_m[	4	][5]);
CM1.	T	=Double.parseDouble(data_m[	5	][5]);
CM1.	Cb	=Double.parseDouble(data_m[	6	][5]);
CM1.	Vs	=Double.parseDouble(data_m[	7	][5]);
CM1.	NJ	=Double.parseDouble(data_m[	8	][5]);
CM1.	NE	=Double.parseDouble(data_m[	9	][5]);
CM1.	kA	=Double.parseDouble(data_m[	10	][5]);
CM1.	Pw	=Double.parseDouble(data_m[	11	][5]);

if(Double.parseDouble(data_m[	12	][5])!=0) {
	CM1. 	W1 = Double.parseDouble(data_m[	12	][5]);
}



//CM1.	kC	=Double.parseDouble(data_m[	1	][18]); // add an option to use user's input of steel weight
CM1.	E_price		= Double.parseDouble(data_m[	1	][17]);
CM1.	Spec_GWP_E 	= Double.parseDouble(data_m[	2	][17]);
CM1.	Spec_AP_E 	= Double.parseDouble(data_m[	3	][17]);
CM1.	Spec_EP_E 	= Double.parseDouble(data_m[	4	][17]);
CM1.	Spec_POCP_E = Double.parseDouble(data_m[	5	][17]);

CM1.	P_Cutting	=Double.parseDouble(data_m[	5	][6]);
CM1.	V_Cutting	=Double.parseDouble(data_m[	2	][6]);
CM1.	L_Cutting	=Double.parseDouble(data_m[	1	][6]);
CM1.	SMC_Cutting	=Double.parseDouble(data_m[	3	][6]);
CM1.	MP_Cutting 	= Double.parseDouble(data_m[	4	][6]);
//CM1.	H_Cutting_Hull	= Double.parseDouble(data_m[	7	][6]);
if(Double.parseDouble(data_m[	7	][6])!=0) {
	//CM1.H_Cutting_Hull=CM1.P_Cutting*CM1.L_Cutting/CM1.V_Cutting;
	CM1.	H_Cutting_Hull	= Double.parseDouble(data_m[	7	][6]);
}
System.out.println(CM1.	H_Cutting_Hull);
//if(data_m[	7	][6]!=null) {
//	CM1.E_Cutting = Double.parseDouble(data_m[	7	][6]) * CM1.P_Cutting;
//}

CM1.P_Bending=Double.parseDouble(data_m[	3	][7]);
CM1.V_Bending=Double.parseDouble(data_m[	2	][7]);
CM1.L_Bending=Double.parseDouble(data_m[	1	][7]);
//CM1.H_Bending_Hull= Double.parseDouble(data_m[	5	][7]);
if(Double.parseDouble(data_m[	5	][7])!=0) {
	//CM1.H_Bending_Hull=CM1.P_Bending*CM1.L_Bending/CM1.V_Bending;
	CM1.H_Bending_Hull= Double.parseDouble(data_m[	5	][7]);
}

CM1.P_Welding=Double.parseDouble(data_m[	5	][8]);
CM1.V_Welding=Double.parseDouble(data_m[	2	][8]);
CM1.L_Welding=Double.parseDouble(data_m[	1	][8]);
CM1.SMC_Welding=Double.parseDouble(data_m[	3	][8]);
CM1.MP_Welding = Double.parseDouble(data_m[	4	][8]);
//CM1.H_Welding_Hull= Double.parseDouble(data_m[	7	][8]);
System.out.println("H"+CM1.H_Welding_Hull);
//if the hours of welding is inputed just use this hours to determine energy consumption (E=P*H). 
//If it leave as 0, use speed and quantity to find energy consumption (E=P*L/V) 
if(Double.parseDouble(data_m[	7	][8])!=0) {
	//CM1.H_Welding_Hull=CM1.P_Welding*CM1.L_Welding/CM1.V_Welding;
	CM1.H_Welding_Hull= Double.parseDouble(data_m[	7	][8]);

}
//System.out.println("H"+CM1.H_Welding_Hull);

CM1.P_Blasting=Double.parseDouble(data_m[	3	][9]);
CM1.V_Blasting=Double.parseDouble(data_m[	2	][9]);
CM1.L_Blasting=Double.parseDouble(data_m[	1	][9]);
//CM1.H_Blasting_Hull= Double.parseDouble(data_m[	5	][9]);
if(Double.parseDouble(data_m[	5	][9])!=0) {
//	CM1.H_Blasting_Hull=CM1.P_Blasting*CM1.L_Blasting/CM1.V_Blasting;
	CM1.H_Blasting_Hull= Double.parseDouble(data_m[	5	][9]);

}

CM1.P_Painting=Double.parseDouble(data_m[	5	][10]);
CM1.V_Painting=Double.parseDouble(data_m[	2	][10]);
CM1.L_Painting=Double.parseDouble(data_m[	1	][10]);
CM1.SMC_Painting=Double.parseDouble(data_m[	3	][10]);
CM1.MP_Painting = Double.parseDouble(data_m[	4	][10]);
//CM1.H_Painting_Hull= Double.parseDouble(data_m[	7	][10]);
if(Double.parseDouble(data_m[	7	][10])!=0) {
	//CM1.H_Painting_Hull=CM1.P_Painting*CM1.L_Painting/CM1.V_Painting;
	CM1.H_Painting_Hull= Double.parseDouble(data_m[	7	][10]);
}

CM1.Transportation_distance=Double.parseDouble(data_m[	1	][11]);
CM1.Transportation_price = Double.parseDouble(data_m[	2	][11]);
CM1.Transportation_SFOC = Double.parseDouble(data_m[	3	][11]);
CM1.Transportation_fuel_price = Double.parseDouble(data_m[	4	][11]);
CM1.Spec_GWP_Trans=Double.parseDouble(data_m[	5	][11]);
CM1.Spec_AP_Trans=Double.parseDouble(data_m[	6	][11]);
CM1.Spec_EP_Trans=Double.parseDouble(data_m[	7	][11]);
CM1.Spec_POCP_Trans=Double.parseDouble(data_m[	8	][11]);

CM1. Labour_fee_cutting =Double.parseDouble(data_m[	6	][6]);
CM1. Labour_fee_bending =Double.parseDouble(data_m[	4	][7]);
CM1. Labour_fee_welding =Double.parseDouble(data_m[	6	][8]);
CM1. Labour_fee_blasting =Double.parseDouble(data_m[	4	][9]);
CM1. Labour_fee_painting =Double.parseDouble(data_m[	6	][10]);

CM1.run(); //Run the calculation


Parameter_C_Material CM2 = new Parameter_C_Material();
//
CM2.	LOA	=Double.parseDouble(data_m[	1	][5]);
CM2.	LBP	=Double.parseDouble(data_m[	2	][5]);
CM2.	B	=Double.parseDouble(data_m[	3	][5]);	
CM2.	D	=Double.parseDouble(data_m[	4	][5]);
CM2.	T	=Double.parseDouble(data_m[	5	][5]);
CM2.	Cb	=Double.parseDouble(data_m[	6	][5]);
CM2.	Vs	=Double.parseDouble(data_m[	7	][5]);
CM2.	NJ	=Double.parseDouble(data_m[	8	][5]);
CM2.	NE	=Double.parseDouble(data_m[	9	][5]);
CM2.	Pw	=Double.parseDouble(data_m[	11	][5]);

CM2.kB=Double.parseDouble(data_m[	1	][18]);
CM2.E_price=Double.parseDouble(data_m[	1	][17]);
CM2.Spec_GWP_E = Double.parseDouble(data_m[	2	][17]);
CM2.Spec_AP_E = Double.parseDouble(data_m[	3	][17]);
CM2.Spec_EP_E = Double.parseDouble(data_m[	4	][17]);
CM2.Spec_POCP_E = Double.parseDouble(data_m[	5	][17]);

CM2.P_Cutting=Double.parseDouble(data_m[	5	][19]);
CM2.V_Cutting=Double.parseDouble(data_m[	2	][19]);
CM2.L_Cutting=Double.parseDouble(data_m[	1	][19]);
CM2.SMC_Cutting=Double.parseDouble(data_m[	3	][19]);
CM2.MP_Cutting = Double.parseDouble(data_m[	4	][19]);
//CM2.H_Cutting_Hull= Double.parseDouble(data_m[	7	][19]);
if(Double.parseDouble(data_m[	7	][19])!=0) {
	//CM2.H_Cutting_Hull=CM2.P_Cutting*CM2.L_Cutting/CM2.V_Cutting;
	CM2.H_Cutting_Hull= Double.parseDouble(data_m[	7	][19]);
}
CM2.P_Welding=Double.parseDouble(data_m[	5	][20]);
CM2.V_Welding=Double.parseDouble(data_m[	2	][20]);
CM2.L_Welding=Double.parseDouble(data_m[	1	][20]);
CM2.SMC_Welding=Double.parseDouble(data_m[	3	][20]);
CM2.MP_Welding = Double.parseDouble(data_m[	4	][20]);
//CM2.H_Welding_Hull= Double.parseDouble(data_m[	7	][20]);
if(Double.parseDouble(data_m[	7	][20])!=0) {
	//CM2.H_Welding_Hull=CM2.P_Welding*CM2.L_Welding/CM2.V_Welding;
	CM2.H_Welding_Hull= Double.parseDouble(data_m[	7	][20]);
}

CM2.P_Painting=Double.parseDouble(data_m[	5	][21]);
CM2.V_Painting=Double.parseDouble(data_m[	2	][21]);
CM2.L_Painting=Double.parseDouble(data_m[	1	][21]);
CM2.SMC_Painting=Double.parseDouble(data_m[	3	][21]);
CM2.MP_Painting = Double.parseDouble(data_m[	4	][21]);
//CM2.H_Painting_Hull= Double.parseDouble(data_m[	7	][21]);
if(Double.parseDouble(data_m[	7	][21])!=0) {
	//CM2.H_Painting_Hull=CM2.P_Painting*CM2.L_Painting/CM2.V_Painting;
	CM2.H_Painting_Hull= Double.parseDouble(data_m[	7	][21]);
}

CM2. Labour_fee_cutting =Double.parseDouble(data_m[	6	][19]);
//CM2. Labour_fee_bending =Double.parseDouble(data_m[	4	][7]);
CM2. Labour_fee_welding =Double.parseDouble(data_m[	6	][20]);
//CM2. Labour_fee_blasting =Double.parseDouble(data_m[	4	][9]);
CM2. Labour_fee_painting =Double.parseDouble(data_m[	6	][21]);

CM2.run(); //Run the calculation


//				CM1.	Number	=	3.00	; //Number of purchase
//				CM1.	Weight	=	1.33	; //Weight of material (ton)	
//				CM1.	Price	=	10000.00	; //Material price (Euro/ton)
//				CM1.	Transportation_distance	=	1000.00	; //Distance of material transportation (km)
//				CM1.	Transportation_price	=	1.62	; //Transportation price per km (Euro/km)
//				CM1.	Transportation_SFOC	=	1.64E-03	; //Transportation specification fuel consumption (ton/km)
//				CM1.	Transportation_fuel_price	=	1350.00	; //Fuel price of transportation (Euro/ton)
//				CM1.	Processing_energy_price	=	0.01	; //Energy price (Euro/unit)
//
//				CM1. Spec_GWP_Trans 	=	0.005227659	; //Specific GWP of transportation (ton CO2e/ ton fuel used for transportation)
//				CM1. Spec_AP_Trans 	=	1.20711E-06	; //Specific AP of transportation (ton SO2e/ ton fuel used for transportation)
//				CM1. Spec_EP_Trans 	=	2.93753E-07	; //Specific EP of transportation (ton PO4e/ ton fuel used for transportation)
//				CM1. Spec_POCP_Trans 	=	1.87014E-07	; //Specific POCP of transportation (ton C2H6e/ ton fuel used for transportation)
//							
//				CM1. Spec_GWP_E 	=	0.002341819	; //Specific GWP of energy consumption (ton CO2e/ MJ energy used)
//				CM1. Spec_AP_E 	=	5.79774E-06	; //Specific AP of energy consumption (ton SO2e/ MJ energy used)
//				CM1. Spec_EP_E 	=	4.69704E-07	; //Specific EP of energy consumption (ton PO4e/ MJ energy used)
//				CM1. Spec_POCP_E 	=	5.11746E-07	; //Specific POCP of energy consumption (ton C2H6e/ MJ energy used)
//

//Retrofitting
//System: ME, Aux, Scrubber...
Parameter_C_System RS1 = new Parameter_C_System();
RS1.	Engine_type	=	data_m[0][30];					//Engine type
//	LCAdataCreation.createCatalogueAndProductComponents();
RS1.	Number	=	Double.parseDouble(data_m[1][30])	; //Number of items
RS1.	Weight	=	Double.parseDouble(data_m[2][30])	; //Weight of item (ton)
RS1.	Price	=	Double.parseDouble(data_m[3][30])	; //Item price (Euro/ton)
RS1.	Transportation_type = cb_m[32].getName();							//transportation type
RS1.	Transportation_distance	=	Double.parseDouble(data_m[1][32])	; //Distance of item transportation (km)
RS1.	Transportation_fee	=	Double.parseDouble(data_m[2][32])	; //Transportation price per km (Euro/km)
RS1.	Transportation_SFOC	=	Double.parseDouble(data_m[3][32])	; //Transportation fuel consumption (ton/km)
RS1.	Transportation_fuel_price	=	Double.parseDouble(data_m[4][32])	; //Fuel price of transportation (Euro/ton)
RS1.	Electricity_type = cb_m[34].getName(); //Electricity_type
RS1.	Installation_energy_price	=	Double.parseDouble(data_m[1][34])	; //Energy price (Euro/unit)
RS1. 	Spec_GWP_Trans 	=	Double.parseDouble(data_m[5][32])	; //Specific GWP of transportation (ton CO2e/ ton fuel used for transportation)
RS1. 	Spec_AP_Trans 	=	Double.parseDouble(data_m[6][32])	; //Specific AP of transportation (ton SO2e/ ton fuel used for transportation)
RS1. 	Spec_EP_Trans 	=	Double.parseDouble(data_m[7][32])	; //Specific EP of transportation (ton PO4e/ ton fuel used for transportation)
RS1. 	Spec_POCP_Trans 	=	Double.parseDouble(data_m[8][32])	; //Specific POCP of transportation (ton C2H6e/ ton fuel used for transportation)
RS1. 	Spec_GWP_E 	=	Double.parseDouble(data_m[2][34])	; //Specific GWP of energy consumption (ton CO2e/ MJ energy used)
RS1. 	Spec_AP_E 	=	Double.parseDouble( data_m[3][34]); //Specific AP of energy consumption (ton SO2e/ MJ energy used)
RS1. 	Spec_EP_E 	=	Double.parseDouble( data_m[4][34])	; //Specific EP of energy consumption (ton PO4e/ MJ energy used)
RS1. 	Spec_POCP_E 	=	Double.parseDouble(data_m[5][34])	; //Specific POCP of energy consumption (ton C2H6e/ MJ energy used)
RS1.H = 7;
RS1.F[0]=Double.parseDouble(data_m1[10][30]);
RS1.F[1]=Double.parseDouble(data_m1[11][30]);
RS1.F[2]=Double.parseDouble(data_m1[12][30]);
RS1.F[3]=Double.parseDouble(data_m1[13][30]);
RS1.F[4]=Double.parseDouble(data_m1[14][30]);
RS1.F[5]=Double.parseDouble(data_m1[15][30]);
RS1.F[6]=Double.parseDouble(data_m1[16][30]);
RS1.C[0]=Double.parseDouble(data_m2[10][30]);
RS1.C[1]=Double.parseDouble(data_m2[11][30]);
RS1.C[2]=Double.parseDouble(data_m2[12][30]);
RS1.C[3]=Double.parseDouble(data_m2[13][30]);
RS1.C[4]=Double.parseDouble(data_m2[14][30]);
RS1.C[5]=Double.parseDouble(data_m2[15][30]);
RS1.C[6]=Double.parseDouble(data_m2[16][30]);
RS1.M[0]=Double.parseDouble(data_m3[10][30]);
RS1.M[1]=Double.parseDouble(data_m3[11][30]);
RS1.M[2]=Double.parseDouble(data_m3[12][30]);
RS1.M[3]=Double.parseDouble(data_m3[13][30]);
RS1.M[4]=Double.parseDouble(data_m3[14][30]);
RS1.M[5]=Double.parseDouble(data_m3[15][30]);
RS1.M[6]=Double.parseDouble(data_m3[16][30]);
RS1.CoTL =CoTL;
RS1.run(); //Run the calculation


Parameter_C_System RS2 = new Parameter_C_System();
RS2.	Engine_type = data_m[0][31];					//battery name
RS2.	Number	=	Double.parseDouble(data_m[1][31])	; //Number of items
RS2.	Weight	=	Double.parseDouble(data_m[2][31])	; //Weight of item (ton)
RS2.	Price	=	Double.parseDouble(data_m[3][31])	; //Item price (Euro/ton)
RS2.	Transportation_type = data_m[0][33];							//transportation type
RS2.	Transportation_distance	=	Double.parseDouble(data_m[1][33])	; //Distance of item transportation (km)
RS2.	Transportation_fee	=	Double.parseDouble(data_m[2][33])	; //Transportation fee per km (Euro/km)
RS2.	Transportation_SFOC	=	Double.parseDouble(data_m[3][33])	; //Transportation fuel consumption (ton/km)
RS2.	Transportation_fuel_price	=	Double.parseDouble(data_m[4][33])	; //Fuel price of transportation (Euro/ton)
RS2.	Electricity_type = data_m[0][34]; //Electricity_type
RS2.	Installation_energy_price	=	Double.parseDouble(data_m[1][34])	; //Energy price (Euro/unit)
RS2. Spec_GWP_Trans 	=	Double.parseDouble(data_m[5][33])	; //Specific GWP of transportation (ton CO2e/ ton fuel used for transportation)
RS2. Spec_AP_Trans 	=	Double.parseDouble(data_m[6][33])	; //Specific AP of transportation (ton SO2e/ ton fuel used for transportation)
RS2. Spec_EP_Trans 	=	Double.parseDouble(data_m[7][33])	; //Specific EP of transportation (ton PO4e/ ton fuel used for transportation)
RS2. Spec_POCP_Trans 	=	Double.parseDouble(data_m[8][33])	; //Specific POCP of transportation (ton C2H6e/ ton fuel used for transportation)
RS2. Spec_GWP_E 	=	Double.parseDouble(data_m[2][34])	; //Specific GWP of energy consumption (ton CO2e/ MJ energy used)
RS2. Spec_AP_E 	=	Double.parseDouble( data_m[3][34]); //Specific AP of energy consumption (ton SO2e/ MJ energy used)
RS2. Spec_EP_E 	=	Double.parseDouble( data_m[4][34])	; //Specific EP of energy consumption (ton PO4e/ MJ energy used)
RS2. Spec_POCP_E 	=	Double.parseDouble(data_m[5][34])	; //Specific POCP of energy consumption (ton C2H6e/ MJ energy used)
RS2.H = 7;
RS2.F[0]=Double.parseDouble(data_m1[12][31]);
RS2.F[1]=Double.parseDouble(data_m1[13][31]);
RS2.F[2]=Double.parseDouble(data_m1[14][31]);
RS2.F[3]=Double.parseDouble(data_m1[15][31]);
RS2.F[4]=Double.parseDouble(data_m1[16][31]);
RS2.F[5]=Double.parseDouble(data_m1[17][31]);
RS2.F[6]=Double.parseDouble(data_m1[18][31]);
RS2.C[0]=Double.parseDouble(data_m2[12][31]);
RS2.C[1]=Double.parseDouble(data_m2[13][31]);
RS2.C[2]=Double.parseDouble(data_m2[14][31]);
RS2.C[3]=Double.parseDouble(data_m2[15][31]);
RS2.C[4]=Double.parseDouble(data_m2[16][31]);
RS2.C[5]=Double.parseDouble(data_m2[17][31]);
RS2.C[6]=Double.parseDouble(data_m2[18][31]);
RS2.M[0]=Double.parseDouble(data_m3[12][31]);
RS2.M[1]=Double.parseDouble(data_m3[13][31]);
RS2.M[2]=Double.parseDouble(data_m3[14][31]);
RS2.M[3]=Double.parseDouble(data_m3[15][31]);
RS2.M[4]=Double.parseDouble(data_m3[16][31]);
RS2.M[5]=Double.parseDouble(data_m3[17][31]);
RS2.M[6]=Double.parseDouble(data_m3[18][31]);
RS2.CoTL =CoTL;
RS2.run(); //Run the calculation

Parameter_C_System RS3 = new Parameter_C_System();
RS3.	Engine_type = data_m[0][32];					//battery name
RS3.	Number	=	Double.parseDouble(data_m[1][32])	; //Number of items
RS3.	Weight	=	Double.parseDouble(data_m[2][32])	; //Weight of item (ton)
RS3.	Price	=	Double.parseDouble(data_m[3][32])	; //Item price (Euro/ton)
RS3.	Transportation_type = data_m[0][33];							//transportation type
RS3.	Transportation_distance	=	Double.parseDouble(data_m[1][33])	; //Distance of item transportation (km)
RS3.	Transportation_fee	=	Double.parseDouble(data_m[2][33])	; //Transportation fee per km (Euro/km)
RS3.	Transportation_SFOC	=	Double.parseDouble(data_m[3][33])	; //Transportation fuel consumption (ton/km)
RS3.	Transportation_fuel_price	=	Double.parseDouble(data_m[4][33])	; //Fuel price of transportation (Euro/ton)
RS3.	Electricity_type = data_m[0][34]; //Electricity_type
RS3.	Installation_energy_price	=	Double.parseDouble(data_m[1][34])	; //Energy price (Euro/unit)
RS3. Spec_GWP_Trans 	=	Double.parseDouble(data_m[5][33])	; //Specific GWP of transportation (ton CO2e/ ton fuel used for transportation)
RS3. Spec_AP_Trans 	=	Double.parseDouble(data_m[6][33])	; //Specific AP of transportation (ton SO2e/ ton fuel used for transportation)
RS3. Spec_EP_Trans 	=	Double.parseDouble(data_m[7][33])	; //Specific EP of transportation (ton PO4e/ ton fuel used for transportation)
RS3. Spec_POCP_Trans 	=	Double.parseDouble(data_m[8][33])	; //Specific POCP of transportation (ton C2H6e/ ton fuel used for transportation)
RS3. Spec_GWP_E 	=	Double.parseDouble(data_m[2][34])	; //Specific GWP of energy consumption (ton CO2e/ MJ energy used)
RS3. Spec_AP_E 	=	Double.parseDouble( data_m[3][34]); //Specific AP of energy consumption (ton SO2e/ MJ energy used)
RS3. Spec_EP_E 	=	Double.parseDouble( data_m[4][34])	; //Specific EP of energy consumption (ton PO4e/ MJ energy used)
RS3. Spec_POCP_E 	=	Double.parseDouble(data_m[5][34])	; //Specific POCP of energy consumption (ton C2H6e/ MJ energy used)
RS3.H = 7;
RS3.F[0]=Double.parseDouble(data_m1[12][32]);
RS3.F[1]=Double.parseDouble(data_m1[13][32]);
RS3.F[2]=Double.parseDouble(data_m1[14][32]);
RS3.F[3]=Double.parseDouble(data_m1[15][32]);
RS3.F[4]=Double.parseDouble(data_m1[16][32]);
RS3.F[5]=Double.parseDouble(data_m1[17][32]);
RS3.F[6]=Double.parseDouble(data_m1[18][32]);
RS3.C[0]=Double.parseDouble(data_m2[12][32]);
RS3.C[1]=Double.parseDouble(data_m2[13][32]);
RS3.C[2]=Double.parseDouble(data_m2[14][32]);
RS3.C[3]=Double.parseDouble(data_m2[15][32]);
RS3.C[4]=Double.parseDouble(data_m2[16][32]);
RS3.C[5]=Double.parseDouble(data_m2[17][32]);
RS3.C[6]=Double.parseDouble(data_m2[18][32]);
RS3.M[0]=Double.parseDouble(data_m3[12][32]);
RS3.M[1]=Double.parseDouble(data_m3[13][32]);
RS3.M[2]=Double.parseDouble(data_m3[14][32]);
RS3.M[3]=Double.parseDouble(data_m3[15][32]);
RS3.M[4]=Double.parseDouble(data_m3[16][32]);
RS3.M[5]=Double.parseDouble(data_m3[17][32]);
RS3.M[6]=Double.parseDouble(data_m3[18][32]);
RS3.CoTL =CoTL;
RS3.run(); //Run the calculation

//Material: steel, aluminium, composite material...
Parameter_C_Material RM1 = new Parameter_C_Material();
//

//RM1.	LOA	=Double.parseDouble(data_m[	1	][5]);
//RM1.	LBP	=Double.parseDouble(data_m[	2	][5]);
//RM1.	B	=Double.parseDouble(data_m[	3	][5]);	
//RM1.	D	=Double.parseDouble(data_m[	4	][5]);
//RM1.	T	=Double.parseDouble(data_m[	5	][5]);
//RM1.	Cb	=Double.parseDouble(data_m[	6	][5]);
//RM1.	Vs	=Double.parseDouble(data_m[	7	][5]);
//RM1.	NJ	=Double.parseDouble(data_m[	8	][5]);
//RM1.	NE	=Double.parseDouble(data_m[	9	][5]);
//RM1.	kA	=Double.parseDouble(data_m[	10	][5]);
//RM1.	Pw	=Double.parseDouble(data_m[	11	][5]);
//
//if(Double.parseDouble(data_m[	12	][5])!=0) {
	RM1. 	W1 = Double.parseDouble(data_m[	12	][5]);
//}



//RM1.	kC	=Double.parseDouble(data_m[	1	][18]); // add an option to use user's input of steel weight
RM1.	E_price		= Double.parseDouble(data_m[	1	][34]);
RM1.	Spec_GWP_E 	= Double.parseDouble(data_m[	2	][34]);
RM1.	Spec_AP_E 	= Double.parseDouble(data_m[	3	][34]);
RM1.	Spec_EP_E 	= Double.parseDouble(data_m[	4	][34]);
RM1.	Spec_POCP_E = Double.parseDouble(data_m[	5	][34]);

RM1.	P_Cutting	=Double.parseDouble(data_m[	5	][24]);
RM1.	V_Cutting	=Double.parseDouble(data_m[	2	][24]);
RM1.	L_Cutting	=Double.parseDouble(data_m[	1	][24]);
RM1.	SMC_Cutting	=Double.parseDouble(data_m[	3	][24]);
RM1.	MP_Cutting 	= Double.parseDouble(data_m[	4	][24]);
//RM1.	H_Cutting_Hull	= Double.parseDouble(data_m[	7	][6]);
if(Double.parseDouble(data_m[	7	][24])!=0) {
	//RM1.H_Cutting_Hull=RM1.P_Cutting*RM1.L_Cutting/RM1.V_Cutting;
	RM1.	H_Cutting_Hull	= Double.parseDouble(data_m[	7	][24]);

}

//if(data_m[	7	][6]!=null) {
//	RM1.E_Cutting = Double.parseDouble(data_m[	7	][6]) * RM1.P_Cutting;
//}

RM1.P_Bending=Double.parseDouble(data_m[	3	][25]);
RM1.V_Bending=Double.parseDouble(data_m[	2	][25]);
RM1.L_Bending=Double.parseDouble(data_m[	1	][25]);
//RM1.H_Bending_Hull= Double.parseDouble(data_m[	5	][7]);
if(Double.parseDouble(data_m[	5	][25])!=0) {
	//RM1.H_Bending_Hull=RM1.P_Bending*RM1.L_Bending/RM1.V_Bending;
	RM1.H_Bending_Hull= Double.parseDouble(data_m[	5	][25]);
}

RM1.P_Welding=Double.parseDouble(data_m[	5	][26]);
RM1.V_Welding=Double.parseDouble(data_m[	2	][26]);
RM1.L_Welding=Double.parseDouble(data_m[	1	][26]);
RM1.SMC_Welding=Double.parseDouble(data_m[	3	][26]);
RM1.MP_Welding = Double.parseDouble(data_m[	4	][26]);
//RM1.H_Welding_Hull= Double.parseDouble(data_m[	7	][8]);
//System.out.println("H"+RM1.H_Welding_Hull);
//if the hours of welding is inputed just use this hours to determine energy consumption (E=P*H). 
//If it leave as 0, use speed and quantity to find energy consumption (E=P*L/V) 
if(Double.parseDouble(data_m[	7	][26])!=0) {
	//RM1.H_Welding_Hull=RM1.P_Welding*RM1.L_Welding/RM1.V_Welding;
	RM1.H_Welding_Hull= Double.parseDouble(data_m[	7	][26]);
}
//System.out.println("H"+RM1.H_Welding_Hull);

RM1.P_Blasting=Double.parseDouble(data_m[	3	][27]);
RM1.V_Blasting=Double.parseDouble(data_m[	2	][27]);
RM1.L_Blasting=Double.parseDouble(data_m[	1	][27]);
//RM1.H_Blasting_Hull= Double.parseDouble(data_m[	5	][9]);
if(Double.parseDouble(data_m[	5	][27])!=0) {
//	RM1.H_Blasting_Hull=RM1.P_Blasting*RM1.L_Blasting/RM1.V_Blasting;
	RM1.H_Blasting_Hull= Double.parseDouble(data_m[	5	][27]);
}

RM1.P_Painting=Double.parseDouble(data_m[	5	][28]);
RM1.V_Painting=Double.parseDouble(data_m[	2	][28]);
RM1.L_Painting=Double.parseDouble(data_m[	1	][28]);
RM1.SMC_Painting=Double.parseDouble(data_m[	3	][28]);
RM1.MP_Painting = Double.parseDouble(data_m[	4	][28]);
//RM1.H_Painting_Hull= Double.parseDouble(data_m[	7	][10]);
if(Double.parseDouble(data_m[	7	][28])!=0) {
//	RM1.H_Painting_Hull=RM1.P_Painting*RM1.L_Painting/RM1.V_Painting;
	RM1.H_Painting_Hull= Double.parseDouble(data_m[	7	][28]);
}

RM1.Transportation_distance=Double.parseDouble(data_m[	1	][29]);
RM1.Transportation_price = Double.parseDouble(data_m[	2	][29]);
RM1.Transportation_SFOC = Double.parseDouble(data_m[	3	][29]);
RM1.Transportation_fuel_price = Double.parseDouble(data_m[	4	][29]);
RM1.Spec_GWP_Trans=Double.parseDouble(data_m[	5	][29]);
RM1.Spec_AP_Trans=Double.parseDouble(data_m[	6	][29]);
RM1.Spec_EP_Trans=Double.parseDouble(data_m[	7	][29]);
RM1.Spec_POCP_Trans=Double.parseDouble(data_m[	8	][29]);

RM1. Labour_fee_cutting =Double.parseDouble(data_m[	6	][24]);
RM1. Labour_fee_bending =Double.parseDouble(data_m[	4	][25]);
RM1. Labour_fee_welding =Double.parseDouble(data_m[	6	][26]);
RM1. Labour_fee_blasting =Double.parseDouble(data_m[	4	][27]);
RM1. Labour_fee_painting =Double.parseDouble(data_m[	6	][28]);

RM1.run(); //Run the calculation


Parameter_C_Material RM2 = new Parameter_C_Material();
//
//RM2.	LOA	=Double.parseDouble(data_m[	1	][5]);
//RM2.	LBP	=Double.parseDouble(data_m[	2	][5]);
//RM2.	B	=Double.parseDouble(data_m[	3	][5]);	
//RM2.	D	=Double.parseDouble(data_m[	4	][5]);
//RM2.	T	=Double.parseDouble(data_m[	5	][5]);
//RM2.	Cb	=Double.parseDouble(data_m[	6	][5]);
//RM2.	Vs	=Double.parseDouble(data_m[	7	][5]);
//RM2.	NJ	=Double.parseDouble(data_m[	8	][5]);
//RM2.	NE	=Double.parseDouble(data_m[	9	][5]);
//RM2.	Pw	=Double.parseDouble(data_m[	11	][5]);

RM2.kB=Double.parseDouble(data_m[	1	][36]);
RM2.E_price=Double.parseDouble(data_m[	1	][34]);
RM2.Spec_GWP_E = Double.parseDouble(data_m[	2	][34]);
RM2.Spec_AP_E = Double.parseDouble(data_m[	3	][34]);
RM2.Spec_EP_E = Double.parseDouble(data_m[	4	][34]);
RM2.Spec_POCP_E = Double.parseDouble(data_m[	5	][34]);

RM2.P_Cutting=Double.parseDouble(data_m[	5	][37]);
RM2.V_Cutting=Double.parseDouble(data_m[	2	][37]);
RM2.L_Cutting=Double.parseDouble(data_m[	1	][37]);
RM2.SMC_Cutting=Double.parseDouble(data_m[	3	][37]);
RM2.MP_Cutting = Double.parseDouble(data_m[	4	][37]);
//RM2.H_Cutting_Hull= Double.parseDouble(data_m[	7	][37]);
if(Double.parseDouble(data_m[	7	][37])!=0) {
//	RM2.H_Cutting_Hull=RM2.P_Cutting*RM2.L_Cutting/RM2.V_Cutting;
	RM2.H_Cutting_Hull= Double.parseDouble(data_m[	7	][37]);

}
RM2.P_Welding=Double.parseDouble(data_m[	5	][38]);
RM2.V_Welding=Double.parseDouble(data_m[	2	][38]);
RM2.L_Welding=Double.parseDouble(data_m[	1	][38]);
RM2.SMC_Welding=Double.parseDouble(data_m[	3	][38]);
RM2.MP_Welding = Double.parseDouble(data_m[	4	][38]);
//RM2.H_Welding_Hull= Double.parseDouble(data_m[	7	][38]);
if(Double.parseDouble(data_m[	7	][38])!=0) {
//	RM2.H_Welding_Hull=RM2.P_Welding*RM2.L_Welding/RM2.V_Welding;
	RM2.H_Welding_Hull= Double.parseDouble(data_m[	7	][38]);

}

RM2.P_Painting=Double.parseDouble(data_m[	5	][39]);
RM2.V_Painting=Double.parseDouble(data_m[	2	][39]);
RM2.L_Painting=Double.parseDouble(data_m[	1	][39]);
RM2.SMC_Painting=Double.parseDouble(data_m[	3	][39]);
RM2.MP_Painting = Double.parseDouble(data_m[	4	][39]);
//RM2.H_Painting_Hull= Double.parseDouble(data_m[	7	][39]);
if(Double.parseDouble(data_m[	7	][39])!=0) {
//	RM2.H_Painting_Hull=RM2.P_Painting*RM2.L_Painting/RM2.V_Painting;
	RM2.H_Painting_Hull= Double.parseDouble(data_m[	7	][39]);
}

RM2. Labour_fee_cutting =Double.parseDouble(data_m[	6	][37]);
//RM2. Labour_fee_bending =Double.parseDouble(data_m[	4	][7]);
RM2. Labour_fee_welding =Double.parseDouble(data_m[	6	][38]);
//RM2. Labour_fee_blasting =Double.parseDouble(data_m[	4	][9]);
RM2. Labour_fee_painting =Double.parseDouble(data_m[	6	][39]);

RM2.run(); //Run the calculation


//Operation
Parameter_O O1 = new Parameter_O();
O1.	Life_span	=	Life_span	;
O1.	Interest	=	Interest	;
O1.	PV 			=	PV	;
O1.	Number 		=	CS1.Number;
O1. Fuel_type 	= 	data_m[0][43];
O1. LO_type  	= 	data_m[0][44];

O1.	Ohour		=	Double.parseDouble(data_m[1][42]); //Operation hours (h)
O1.	Ohour_2		=	Double.parseDouble(data_m[9][42]); //Operation hours (h)
O1.	Ohour_3		=	Double.parseDouble(data_m[17][42]); //Operation hours (h)

O1. Engine_No	=   Double.parseDouble(data_m[2][42]); //Number of operated engines (h)
O1. Engine_No_2	=   Double.parseDouble(data_m[10][42]); //Number of operated engines (h)
O1. Engine_No_3	=   Double.parseDouble(data_m[18][42]); //Number of operated engines (h)

O1.	SFOC		=	Double.parseDouble(data_m[5][42])	; //Specific fuel oil consumption (g/kWh)
O1.	SFOC_2		=	Double.parseDouble(data_m[13][42])	; //Specific fuel oil consumption (g/kWh)
O1.	SFOC_3		=	Double.parseDouble(data_m[21][42])	; //Specific fuel oil consumption (g/kWh)


O1. C_Factor = Double.parseDouble(data_m[6][43]); 	//carbon content
O1. S_Factor = Double.parseDouble(data_m[7][43]);	//sulfer content
O1. N_Factor = Double.parseDouble(data_m[8][43]);        //nitrogen content
O1.	Fuel_price	=	Double.parseDouble(data_m[1][43])	; //Fuel oil price (Euro/ton)

O1.	Eload		=	Double.parseDouble(data_m[4][42])	; //Power (kW) (Engine output)
O1.	Eload_2		=	Double.parseDouble(data_m[12][42])	; //Power (kW) (Engine output)
O1.	Eload_3		=	Double.parseDouble(data_m[20][42])	; //Power (kW) (Engine output)

O1.	SLOC		=	Double.parseDouble(data_m[6][42])	; //Specific lubricating oil consumption (g/kWh)
O1.	SLOC_2		=	Double.parseDouble(data_m[14][42])	; //Specific lubricating oil consumption (g/kWh)
O1.	SLOC_3		=	Double.parseDouble(data_m[22][42])	; //Specific lubricating oil consumption (g/kWh)

O1.	LO_price	=	Double.parseDouble(data_m[1][44])	; //Lub oil price (Euro/ton)
O1.	Transportation_fee	=	Double.parseDouble(data_m[2][45])	; //Transportation fee per km (Euro/km)
O1.	Transportation_SFOC	=	Double.parseDouble(data_m[3][45])	; //Transportation specification fuel consumption (ton/km)
O1.	Transportation_fuel_price	=	Double.parseDouble(data_m[4][45])	; //Fuel price of transportation (Euro/ton)
O1.	Transportation_distance	=	Double.parseDouble(data_m[1][45])	; //Distance of material transportation (km)
//System.out.println(O1.Cost_O);
//System.out.println(O1.Transportation_distance);

//Specific GWP of activity (ton CO2e/ ton fuel used for activity)
//Specific AP of activity (ton SO2e/ ton fuel used for activity)
//Specific EP of activity (ton PO4e/ ton fuel used for activity)
//Specific POCP of activity (ton C2H6e/ ton fuel used for activity)

O1. Spec_GWP_Trans 	=	Double.parseDouble(data_m[5][45])	;
O1. Spec_AP_Trans 	=	Double.parseDouble(data_m[6][45])	;
O1. Spec_EP_Trans 	=	Double.parseDouble(data_m[7][45])	;
O1. Spec_POCP_Trans =	Double.parseDouble(data_m[8][45])	;
O1. Spec_GWP_FO 	=	Double.parseDouble(data_m[2][43])		;
O1. Spec_AP_FO 		=	Double.parseDouble(data_m[3][43])		;
O1. Spec_EP_FO 		=	Double.parseDouble(data_m[4][43])		;
O1. Spec_POCP_FO 	=	Double.parseDouble(data_m[5][43])		;
O1. Spec_GWP_LO 	=	Double.parseDouble(data_m[2][44])		;
O1. Spec_AP_LO 		=	Double.parseDouble(data_m[3][44])		;
O1. Spec_EP_LO 		=	Double.parseDouble(data_m[4][44])		;
O1. Spec_POCP_LO 	=	Double.parseDouble(data_m[5][44])		;
O1.run(); //Run the calculation
//Maintenance

Parameter_M M1 = new Parameter_M();
M1.Life_span = Life_span;
M1.GE_Power= Double.parseDouble(data_m[4][42]);
M1.GEM_Price= Double.parseDouble(data_m[1][48]);
M1.B_Power= Double.parseDouble(data_m[7][14]);
M1.BM_Price= Double.parseDouble(data_m[3][48]);

M1.	E_Cutting	=Double.parseDouble(data_m[5][6]);
M1.	E_Bending	=Double.parseDouble(data_m[3][7]);
M1.	E_Welding	=Double.parseDouble(data_m[5][8]);
M1. E_Blasting = Double.parseDouble(data_m[3][9]);
M1.	E_Painting	=Double.parseDouble(data_m[5][10]);

M1.	H_Cutting_Ma	=Double.parseDouble(data_m[8][48]);
M1.	H_Bending_Ma	=Double.parseDouble(data_m[9][48]);
M1.	H_Welding_Ma	=Double.parseDouble(data_m[10][48]);
M1.	H_Painting_Ma	=Double.parseDouble(data_m[11][48]);

M1.	H_Cutting_O	=Double.parseDouble(data_m[1][50]);
M1.	H_Bending_O	=Double.parseDouble(data_m[2][50]);
M1.	H_Welding_O	=Double.parseDouble(data_m[3][50]);
M1.	H_Painting_O	=Double.parseDouble(data_m[4][50]);

M1.	H_Cutting_Hull	=Double.parseDouble(data_m[1][49]);
M1.	H_Bending_Hull	=Double.parseDouble(data_m[2][49]);
M1.	H_Welding_Hull	=Double.parseDouble(data_m[4][49]);
M1.	H_Painting_Hull	=Double.parseDouble(data_m[5][49]);
M1.	H_Blasting_Hull	=Double.parseDouble(data_m[3][49]);

M1. Spec_GWP_E = Double.parseDouble(data_m[2][17]);
M1. Spec_AP_E = Double.parseDouble(data_m[3][17]);
M1. Spec_EP_E = Double.parseDouble(data_m[4][17]);
M1. Spec_POCP_E = Double.parseDouble(data_m[5][17]);

M1.E_Working_hours= Double.parseDouble(data_m[1][42]);
M1.Boiler_Working_hours= Double.parseDouble(data_m[4][42]);
M1.GE_Working_hours= Double.parseDouble(data_m[2][42]);
M1.B_Working_hours= Double.parseDouble(data_m[3][42]);


M1.Labour_fee_cutting = Double.parseDouble(data_m[6][6]);
M1.Labour_fee_bending = Double.parseDouble(data_m[4][7]);
M1.Labour_fee_welding = Double.parseDouble(data_m[6][8]);
M1.Labour_fee_blasting = Double.parseDouble(data_m[4][9]);
M1.Labour_fee_coating = Double.parseDouble(data_m[6][10]);

M1.engine_price = Double.parseDouble(data_m[3][12])	;
double Spare_Cost = 0;
System.out.println(Double.parseDouble(data_m[1][52]));
for (int i=1; i<data_length;i++) {
	Spare_Cost+=Double.parseDouble(data_m[i][52]);
	}
M1.Spare_cost =Spare_Cost;
System.out.println(M1.Spare_cost);
//double sum_ratio=0;
//double[] num = new double[data_length-1];
//double summ=0;
//for(int i=0;i<data_length-1;i++) {
////	if(Double.parseDouble(data_m[i][34])==0)	{
////		 break;
////	}
//	
//	num[i]=Double.parseDouble(data_m[i+1][34]);
//	summ += num[i];
//	sum_ratio=summ;
//}

//M1.spare_to_engine = 1;

M1.run();
//Scrapping
Parameter_S S1 = new Parameter_S();
S1.	Number	=	CS1.Number	; //Number of item for scrapping
S1.	Weight	=	CS1.Weight	; //Weight of item (ton)
S1.	S_Price	=	Double.parseDouble(data_m[6][54])	; //Price of scrapping (Euro/set)
S1.	Transportation_distance	=	Double.parseDouble(data_m[1][55])	; //Distance of material transportation (km)
S1.	Transportation_fee	=	Double.parseDouble(data_m[2][55])	; //Transportation fee per km (Euro/km)
S1.	Transportation_SFOC	=	Double.parseDouble(data_m[3][55]); //Transportation specification fuel consumption (ton/km)
S1.	Transportation_fuel_price	=	Double.parseDouble(data_m[4][55])	; //Fuel price of transportation (Euro/ton)
//S1.	Cut_type	= data_m[0][57];
S1.	Electricity_price	=	Double.parseDouble(data_m[1][56])	;  //Fuel oil price (Euro/ton) natural gas
//	S1.Fuel_price = Double.parseDouble(data_m[1][56]);
S1.Cutting_power=Double.parseDouble(data_m[5][6]);
S1.Cutting_hours = Double.parseDouble(data_m[1][57]);

S1.	Life_span	=	Life_span	;
S1.	Interest	=	Interest	;
S1.	PV 	=PV;
//Specific GWP of activity (ton CO2e/ ton fuel used for activity)
//Specific AP of activity (ton SO2e/ ton fuel used for activity)
//Specific EP of activity (ton PO4e/ ton fuel used for activity)
//Specific POCP of activity (ton C2H6e/ ton fuel used for activity)

S1. Spec_GWP_Trans 	=	Double.parseDouble(data_m[5][55])	;
S1. Spec_AP_Trans 	=	Double.parseDouble(data_m[6][55])	;
S1. Spec_EP_Trans 	=	Double.parseDouble(data_m[7][55])	;
S1. Spec_POCP_Trans 	=	Double.parseDouble(data_m[8][55])	;
S1. Spec_GWP_E 	=	Double.parseDouble(data_m[2][56])	;
S1. Spec_AP_E 	=	Double.parseDouble(data_m[3][56])	;
S1. Spec_EP_E 	=	Double.parseDouble(data_m[4][56])	;
S1. Spec_POCP_E =	Double.parseDouble(data_m[5][56])	;
S1.run(); //Run the calculation


Parameter_S S2 = new Parameter_S();
S2.	Number	=	CS1.Number	; //Number of item for scrapping
S2.	Weight	=	CS1.Weight	; //Weight of item (ton)
S2.	S_Price	=	Double.parseDouble(data_m[8][54])	; //Price of scrapping (Euro/ton)
S2.	Transportation_distance	=	Double.parseDouble(data_m[1][55])	; //Distance of material transportation (km)
S2.	Transportation_fee	=	Double.parseDouble(data_m[2][55])	; //Transportation price per km (Euro/km)
S2.	Transportation_SFOC	=	Double.parseDouble(data_m[3][55]); //Transportation specification fuel consumption (ton/km)
S2.	Transportation_fuel_price	=	Double.parseDouble(data_m[4][55])	; //Fuel price of transportation (Euro/ton)
//S2.	Cut_type	= data_m[0][57];
S2.	Electricity_price	=	Double.parseDouble(data_m[1][56])	;  //Fuel oil price (Euro/ton) natural gas
//S2.Fuel_price = Double.parseDouble(data_m[1][56]);
S2.Cutting_power=Double.parseDouble(data_m[5][6]);
//S2.Cutting_hours = Double.parseDouble(data_m[1][57]);

S2.	Life_span	=	Life_span	;
S2.	Interest	=	Interest	;
S2.	PV 	=PV;
//Specific GWP of activity (ton CO2e/ ton fuel used for activity)
//Specific AP of activity (ton SO2e/ ton fuel used for activity)
//Specific EP of activity (ton PO4e/ ton fuel used for activity)
//Specific POCP of activity (ton C2H6e/ ton fuel used for activity)

S2. Spec_GWP_Trans 	=	Double.parseDouble(data_m[5][55])	;
S2. Spec_AP_Trans 	=	Double.parseDouble(data_m[6][55])	;
S2. Spec_EP_Trans 	=	Double.parseDouble(data_m[7][55])	;
S2. Spec_POCP_Trans 	=	Double.parseDouble(data_m[8][55])	;
S2. Spec_GWP_E 	=	Double.parseDouble(data_m[2][56])	;
S2. Spec_AP_E 	=	Double.parseDouble(data_m[3][56])	;
S2. Spec_EP_E 	=	Double.parseDouble(data_m[4][56])	;
S2. Spec_POCP_E =	Double.parseDouble(data_m[5][56])	;
S2.run(); //Run the calculation
//Results
double Total_cost = CM1.Cost_C_Material+CM2.Cost_C_Material+CS1.Cost_C_System + CS2.Cost_C_System+
					RM1.Cost_C_Material+RM2.Cost_C_Material+RS1.Cost_C_System + RS2.Cost_C_System+
					O1.Cost_O+M1.Cost_M+S1.Cost_S+S2.Cost_S; //Total cost (Euro)  	CM1.Cost_C_Material+
double Total_GWP = 	CM1.GWP+CM2.GWP+CS1.GWP+CS2.GWP+
					RM1.GWP+RM2.GWP+RS1.GWP+RS2.GWP+	
					M1.GWP+O1.GWP+S1.GWP+S2.GWP; //Total GWP    							CM1.GWP+
double Total_AP = 	CM1.AP+CM2.AP+CS1.AP+CS2.AP+
					RM1.AP+RM2.AP+RS1.AP+RS2.AP+
					M1.AP+O1.AP+S1.AP+S2.AP; //Total AP									CM1.AP+
double Total_EP = 	CM1.EP+CM2.EP+CS1.EP+CS2.EP+
					RM1.EP+RM2.EP+RS1.EP+RS2.EP+
					M1.EP+O1.EP+S1.EP+S2.EP; //Total EP									CM1.EP+
double Total_POCP = CM1.POCP+CM2.POCP+CS1.POCP+CS2.POCP+
					RM1.POCP+RM2.POCP+RS1.POCP+RS2.POCP+
					M1.POCP+O1.POCP+S1.POCP+S2.POCP; //Total POCP							CM1.POCP+
double Total_RA = CS1.RA+ CS2.RA+RS1.RA+RS2.RA; //Total RA
double Total_CRA = Total_RA/1000*CoTL;

//Outputs print
//System.out.println("The total Life Cycle Cost is : " + Total_cost + "Euro");
//System.out.println("The total GWP is :" + Total_GWP + "ton CO2e");
//System.out.println("The total AP is :" + Total_AP + "ton SO2e");
//System.out.println("The total EP is :" + Total_EP + "ton PO4e");
//System.out.println("The total POCP is :" + Total_POCP + "ton C2H6e");
//System.out.println("The total RA is :" + Total_RA + "RPN");
//System.out.println("The total Risk Cost is :" + Total_CRA + "Euro");
/*design*/		double sum0 = 0;
/*C_H*/			double sum1 = CM1.Cost_C_Material; 
/*C_M*/			double sum2 = CS1.Cost_C_System+CS2.Cost_C_System ; //+ CM1.Cost_C_Material
/*C_A*/			double sum3 = CM2.Cost_C_Material;
				double sumC = sum1+sum2+sum3;
				/*R_H*/			double sum1r = RM1.Cost_C_Material; 
				/*R_M*/			double sum2r = RS1.Cost_C_System+RS2.Cost_C_System ; //+ CM1.Cost_C_Material
				/*R_A*/			double sum3r = RM2.Cost_C_Material;
								double sumR = sum1r+sum2r+sum3r;
/*O*/			double sum4 = O1.Cost_O;
/*M*/			double sum5 = M1.Cost_M;
/*S*/			double sum6 = S1.Cost_S+S2.Cost_S;
/*Sum*/			double sum = Total_cost;
/*design*/		double GWP0 = 0;				
/*C_H*/			double GWP1 = CM1.GWP;
/*C_M*/			double GWP2 = CS1.GWP + CS2.GWP; //+ CM1.Cost_C_Material
/*C_A*/			double GWP3 = CM2.GWP;
				double GWP_C=GWP1+GWP2+GWP3;
				/*R_H*/			double GWP1r = RM1.GWP;
				/*R_M*/			double GWP2r = RS1.GWP + RS2.GWP; //+ CM1.Cost_C_Material
				/*R_A*/			double GWP3r = RM2.GWP;
								double GWP_R=GWP1r+GWP2r+GWP3r;
/*O*/			double GWP4 = O1.GWP;
/*M*/			double GWP5 = 0;
/*S*/			double GWP6 = S1.GWP+S2.GWP;
/*Sum*/			double GWP = Total_GWP;
double P_GWP = 24; //Euro per ton
/*design*/		double AP0 = 0;				
/*C_H*/			double AP1 = CM1.AP;
/*C_M*/			double AP2 = CS1.AP +CS2.AP; //+ CM1.Cost_C_Material
/*C_A*/			double AP3 = CM2.AP;
				double AP_C = AP1+AP2+AP3;
				/*R_H*/			double AP1r = RM1.AP;
				/*R_M*/			double AP2r = RS1.AP +RS2.AP; //+ CM1.Cost_C_Material
				/*R_A*/			double AP3r = RM2.AP;
								double AP_R = AP1r+AP2r+AP3r;
/*O*/			double AP4 = O1.AP;
/*M*/			double AP5 = 0;
/*S*/			double AP6 = S1.AP +S2.AP;
/*AP*/			double AP = Total_AP;
double P_AP = 7788;
/*design*/		double EP0 = 0;				
/*C_H*/			double EP1 = CM1.EP;
/*C_M*/			double EP2 = CS1.EP +CS2.EP; //+ CM1.Cost_C_Material
/*C_A*/			double EP3 = CM2.EP;
				double EP_C = EP1+EP2+EP3;
				/*R_H*/			double EP1r = RM1.EP;
				/*R_M*/			double EP2r = RS1.EP +RS2.EP; //+ CM1.Cost_C_Material
				/*R_A*/			double EP3r = RM2.EP;
								double EP_R = EP1r+EP2r+EP3r;
/*O*/			double EP4 = O1.EP;
/*M*/			double EP5 = 0;
/*S*/			double EP6 = S1.EP +S2.EP;
/*EP*/			double EP = Total_EP;
double P_EP = 0;
/*design*/		double POCP0 = 0;				
/*C_H*/			double POCP1 = CM1.POCP;
/*C_M*/			double POCP2 = CS1.POCP +CS2.POCP; //+ CM1.Cost_C_Material
/*C_A*/			double POCP3 = CM2.POCP;
				double POCP_C=POCP1+POCP2+POCP3;
				/*R_H*/			double POCP1r = RM1.POCP;
				/*R_M*/			double POCP2r = RS1.POCP +RS2.POCP; //+ CM1.Cost_C_Material
				/*R_A*/			double POCP3r = RM2.POCP;
								double POCP_R=POCP1r+POCP2r+POCP3r;
/*O*/			double POCP4 = O1.POCP;
/*M*/			double POCP5 = 0;
/*S*/			double POCP6 = S1.POCP +S2.POCP;
/*POCP*/		double POCP = Total_POCP;
double P_POCP = 0;
/*design*/		double RA0 = 0;				
/*C_H*/			double RA1 = 0;
/*C_M*/			double RA2 = 0; //+ CM1.Cost_C_Material
/*C_A*/			double RA3 = 0;
/*O*/			double RA4 = CS1.RA +CS2.RA;
/*O_r*/			double RA4r = RS1.RA+RS2.RA;	
/*M*/			double RA5 = 0;
/*S*/			double RA6 = 0;
/*RA*/			double RA = Total_RA;
q.add(String.valueOf(sum0));
q.add(String.valueOf(sum1));
q.add(String.valueOf(sum2));
q.add(String.valueOf(sum3));
q.add(String.valueOf(sum4));
q.add(String.valueOf(sum5));
q.add(String.valueOf(sum6));
q.add(String.valueOf(sum));
//    static public final Logger log = Logger.getLogger(LCAdataCreation.class);
//    static public final DirectoryTestSetup dts = new DirectoryTestSetup(LCAdataCreation.class);
//    static public  ObjectManager projOM;



//    lCAdataCreation.log=log;
//    lCAdataCreation.dts=dts;
//    lCAdataCreation.projOM=projOM;

textReport.setText( 	"SHIPLYS LCT Report"+"\n"+
        "Case Name: "+ case_name +"\n"+
        "Date: " +dateFormat.format(d) +"\n"+"\n"+
        
        PV_state+"\n"+
        SL_state+"\n"+"\n"+
        
        "Cost of Machinery  (Euro):"+"\n"+
        "	Construction (Structure): 	" + formatter.format(sum1+sum3) +"\n"+
        "	Construction (Machinery): 	" + formatter.format(sum2) +"\n"+
        "	Retrofitting (Structure): 	" + formatter.format(sum1r+sum3r) +"\n"+
        "	Retrofitting (Machinery): 	" + formatter.format(sum2r) +"\n"+
        "	Operation: 		" + formatter.format(sum4) + "\n"+
        "	Maintenance: 	" + formatter.format(sum5) + "\n"+
        "	Scrapping: 		" + formatter.format(sum6) + "\n"+
        "	Total cost: 		" + formatter.format(sum) + "\n"+"\n"+
        
        "Global Warming Potential (GWP) of Machinery  (ton CO2e):"+"\n"+
        "	Construction: 	" + formatter.format(GWP_C) +"\n"+
        "	Retrofitting: 		" + formatter.format(GWP_R) +"\n"+
        "	Operation: 		" + formatter.format(GWP4) +"\n"+
        "	Maintenance: 	" +formatter.format(GWP5) +"\n"+
        "	Scrapping: 		" + formatter.format(GWP6) +"\n"+
        "	Total GWP: 		" + formatter.format(GWP) +"\n"+"\n"+
        
        "Acidification Potential (AP) of Machinery  (ton SO2e):"+"\n"+
        "	Construction: 	" + formatter.format(AP_C)+"\n"+
        "	Retrofitting:		" + formatter.format(AP_R)+"\n"+
        "	Operation: 		" + formatter.format(AP4) +"\n"+
        "	Maintenance: 	" +formatter.format(AP5) +"\n"+
        "	Scrapping: 		" + formatter.format(AP6) +"\n"+
        "	Total AP: 		" + formatter.format(AP) +"\n"+"\n"+
        
        "Eutrophication Potential(EP) of Machinery  (ton PO4e):"+"\n"+
        "	Construction: 	" + formatter.format(EP_C) +"\n"+
        "	Retrofitting:		" + formatter.format(EP_R) +"\n"+
        "	Operation:		" + formatter.format(EP4) +"\n"+
        "	Maintenance: 	" +formatter.format(EP5) +"\n"+
        "	Scrapping: 	" + formatter.format(EP6) +"\n"+
        "	Total EP: 		" + formatter.format(EP) +"\n"+"\n"+
        
        
        "Photochemical Ozone Creation Potential (POCP) of Machinery  (ton C2H6e):"+"\n"+
        "	Construction: 	" + formatter.format(POCP_C) +"\n"+
        "	Retrofitting:		" + formatter.format(POCP_R) +"\n"+
        "	Operation: 		" + formatter.format(POCP4 )+"\n"+
        "	Maintenance: 	" +formatter.format(POCP5) +"\n"+
        "	Scrapping: 		" + formatter.format(POCP6) +"\n"+
        "	Total POCP: 		" + formatter.format(POCP) +"\n"+"\n"+
        
        "Risk Priority Number (RPN) of Machinery  (RPN):"+"\n"+
        "	Construction: 	" + formatter.format(RA2) +"\n"+
        "	Retrofitting:		" + formatter.format(RA2) +"\n"+
        "	Operation: 		" + formatter.format(RA4+RA4r) +"\n"+
        "	Maintenance: 	" +formatter.format(RA5) +"\n"+
        "	Scrapping: 		" + formatter.format(RA6) +"\n"+
        "	Total RPN 		" + formatter.format(RA) +"\n"+"\n"+
        
        
        "Total results"+"\n"+
        "	Life cycle cost (Euro): 	"+ formatter.format(sum) +"\n"+
        "	GWP 	(ton CO2e): 	"+ formatter.format(Total_GWP) +"\n"+
        "	GWP 	(Euro): 	"+ formatter.format(GWP*P_GWP) +"\n"+
        "	AP 	(ton SO2e): 	" + formatter.format(Total_AP) +"\n"+
        "	AP 	(Euro): 	" + formatter.format(AP*P_AP) +"\n"+
        "	EP 	(ton PO4e): 	" + formatter.format(Total_EP) +"\n"+
        "	EP 	(Euro): 	" + formatter.format(EP*P_EP) +"\n"+
        "	POCP 	(ton C2H6e): " + formatter.format(Total_POCP) +"\n"+
        "	POCP 	(Euro): 	" + formatter.format(POCP*P_POCP) +"\n"+
        "	RPN:  		" + formatter.format(Total_RA) +"\n"+
        "	Risk Cost 	(Euro): 	" + formatter.format(Total_CRA) +"\n"+
        "	Life cycle total cost (Euro): 	"+ formatter.format(sum+GWP*P_GWP+AP*P_AP+EP*P_EP+POCP*P_POCP+RA/1000*CoTL) +"\n"+"\n"+
        "*Note: now there is no credit regulation for EP and POCP so these results are zero."+"\n"
);
////generate a piechart
//			     ArrayList<Segment> values_1 = new ArrayList<Segment>();
//			     values_1.add(new Segment(sum0/sum*100, Phase[0], Color.RED));
//			     values_1.add(new Segment(sum1/sum*100, Phase[1], Color.ORANGE));
//			     values_1.add(new Segment(sum2/sum*100, Phase[2], Color.YELLOW));
//			     values_1.add(new Segment(sum3/sum*100, Phase[3], Color.GREEN));
//			     values_1.add(new Segment(sum4/sum*100, Phase[4], Color.CYAN));
//			     values_1.add(new Segment(sum5/sum*100, Phase[5], Color.BLUE));
//			     values_1.add(new Segment(sum6/sum*100, Phase[6], Color.PINK));
//			     PieChart pieChart = new PieChart(values_1, "Pie Chart");
//			     pieChart.setAlignmentX(Component.CENTER_ALIGNMENT);
//			     pieChart.setPreferredSize(new Dimension(800, 600));
//			     
//			     panel_chart1 = new JPanel();
//			     panel_chart1.add(pieChart);
//			     //create a window for piechart
//			     frame1 = new JFrame("Pie Charts");
//			     //frame1.setName("Charts");
//			     frame1.setLayout(new BorderLayout());
//			     frame1.add(panel_chart1,BorderLayout.WEST);
//			     frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//			     frame1.setSize(new Dimension(820, 620));
//			     frame1.setResizable(true);
//			     panel_chart1.setPreferredSize(new Dimension(frame1.getWidth()-20, frame1.getHeight()-200));
//			     frame1.pack();
//			     frame1.setVisible(true);
//create LCA data in server

//add XY charts
DefaultCategoryDataset dataset1 =
        new DefaultCategoryDataset( );
dataset1.addValue( sum1+sum2+sum3 , "Construction" , "" );
dataset1.addValue( sum4 , "Operation" , "" );
dataset1.addValue( sum5 , "Maintenance" , "");
dataset1.addValue( sum6 , "Scrapping" , "");
dataset1.addValue( sum , "Total" , "");
// Generate the graph
JFreeChart chart1 = ChartFactory.createBarChart(
        "Life Cycle Cost - " + case_name, // Title
        "Life Stages", // x-axis Label
        "Cost (Euro)", // y-axis Label
        dataset1, // Dataset
        PlotOrientation.HORIZONTAL, // Plot Orientation
        true, // Show Legend
        true, // Use tooltips
        false // Configure chart to generate URLs?
);                              ChartPanel CP1 = new ChartPanel(chart1);
panel_chart1 = new JPanel();
panel_chart1.add(CP1);
//create a window for piechart
//frame1 = new JFrame("Life Cycle Cost");
//frame1.setLayout(new BorderLayout());
//frame1.add(panel_chart1, BorderLayout.WEST);
//frame1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//frame1.setSize(new Dimension(820, 620));
//frame1.setResizable(true);
//panel_chart1.setPreferredSize(new Dimension(frame1.getWidth()-120, frame1.getHeight()-150));
//frame1.pack();
//frame1.setVisible(true);	
//add XY charts 2
DefaultCategoryDataset dataset2 =
        new DefaultCategoryDataset( );
dataset2.addValue( GWP_C , "Construction" , "" );
dataset2.addValue( GWP4 , "Operation" , "" );
dataset2.addValue( GWP5 , "Maintenance" , "" );
dataset2.addValue( GWP6 , "Scrapping" , "" );
dataset2.addValue( GWP , "Total" , "" );
// Generate the graph
JFreeChart chart2 = ChartFactory.createBarChart(
        "Global Warming Potential - " + case_name, // Title
        "Life Stages", // x-axis Label
        "GWP (ton CO2e)", // y-axis Label
        dataset2, // Dataset
        PlotOrientation.HORIZONTAL, // Plot Orientation
        true, // Show Legend
        true, // Use tooltips
        false // Configure chart to generate URLs?
);                              ChartPanel CP2 = new ChartPanel(chart2);
panel_chart2 = new JPanel();
panel_chart2.add(CP2);
//create a window for piechart
//frame2 = new JFrame("Global Warming Potential");
//frame2.setLayout(new BorderLayout());
//frame2.add(panel_chart2, BorderLayout.WEST);
//frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//frame2.setSize(new Dimension(820, 620));
//frame2.setResizable(true);
//panel_chart2.setPreferredSize(new Dimension(frame2.getWidth()-120, frame2.getHeight()-150));
//frame2.pack();
//frame2.setVisible(true);
//add XY charts 3
DefaultCategoryDataset dataset3 =
        new DefaultCategoryDataset( );
dataset3.addValue( AP_C , "Construction" , "" );
dataset3.addValue( AP4 , "Operation" , "" );
dataset3.addValue( AP5 , "Maintenance" , "" );
dataset3.addValue( AP6 , "Scrapping" , "" );
dataset3.addValue( AP , "Total" , "" );
// Generate the graph
JFreeChart chart3 = ChartFactory.createBarChart(
        "Acidification Potential - " + case_name, // Title
        "Life Stages", // x-axis Label
        "AP (ton SO2e)", // y-axis Label
        dataset3, // Dataset
        PlotOrientation.HORIZONTAL, // Plot Orientation
        true, // Show Legend
        true, // Use tooltips
        false // Configure chart to generate URLs?
);                              ChartPanel CP3 = new ChartPanel(chart3);
panel_chart3 = new JPanel();
panel_chart3.add(CP3);
//create a window for piechart
//frame3 = new JFrame("Acidification Potential");
//frame3.setLayout(new BorderLayout());
//frame3.add(panel_chart3, BorderLayout.WEST);
//frame3.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//frame3.setSize(new Dimension(800, 600));
//frame3.setResizable(true);
//panel_chart3.setPreferredSize(new Dimension(frame3.getWidth()-120, frame3.getHeight()-150));
//frame3.pack();
//frame3.setVisible(true);
//add XY charts 4
DefaultCategoryDataset dataset4 =
        new DefaultCategoryDataset( );
dataset4.addValue( EP_C , "Construction" , "" );
dataset4.addValue( EP4 , "Operation" , "" );
dataset4.addValue( EP5 , "Maintenance" , "" );
dataset4.addValue( EP6 , "Scrapping" , "" );
dataset4.addValue( EP , "Total" , "" );
// Generate the graph
JFreeChart chart4 = ChartFactory.createBarChart(
        "Eutrophication Potential - " + case_name, // Title
        "Life Stages", // x-axis Label
        "EP (ton PO4e)", // y-axis Label
        dataset4, // Dataset
        PlotOrientation.HORIZONTAL, // Plot Orientation
        true, // Show Legend
        true, // Use tooltips
        false // Configure chart to generate URLs?
);                              ChartPanel CP4 = new ChartPanel(chart4);
panel_chart4 = new JPanel();
panel_chart4.add(CP4);
//create a window for piechart
//frame4 = new JFrame("Eutrophication Potential");
//frame4.setLayout(new BorderLayout());
//frame4.add(panel_chart4, BorderLayout.WEST);
//frame4.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//frame4.setSize(new Dimension(800, 600));
//frame4.setResizable(true);
//panel_chart4.setPreferredSize(new Dimension(frame4.getWidth()-120, frame4.getHeight()-150));
//frame4.pack();
//frame4.setVisible(true);
//add XY charts 5
DefaultCategoryDataset dataset5 =
        new DefaultCategoryDataset( );
dataset5.addValue( POCP_C , "Construction" , "" );
dataset5.addValue( POCP4 , "Operation" , "" );
dataset5.addValue( POCP5 , "Maintenance" , "" );
dataset5.addValue( POCP6 , "Scrapping" , "" );
dataset5.addValue( POCP , "Total" , "" );
// Generate the graph
JFreeChart chart5 = ChartFactory.createBarChart(
        "Photochemical Ozone Creation Potential - " + case_name, // Title
        "Life Stages", // x-axis Label
        "POCP (ton C2H6e)", // y-axis Label
        dataset5, // Dataset
        PlotOrientation.HORIZONTAL, // Plot Orientation
        true, // Show Legend
        true, // Use tooltips
        false // Configure chart to generate URLs?
);                              ChartPanel CP5 = new ChartPanel(chart5);
panel_chart5 = new JPanel();
panel_chart5.add(CP5);
//create a window for piechart
//frame5 = new JFrame("Photochemical Ozone Creation Potential");
//frame5.setLayout(new BorderLayout());
//frame5.add(panel_chart5, BorderLayout.WEST);
//frame5.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//frame5.setSize(new Dimension(800, 600));
//frame5.setResizable(true);
//panel_chart5.setPreferredSize(new Dimension(frame5.getWidth()-120, frame5.getHeight()-150));
//frame5.pack();
//frame5.setVisible(true);
//add XY charts 6
DefaultCategoryDataset dataset6 =
        new DefaultCategoryDataset( );
dataset6.addValue( RA2 , "Construction" , "" );
dataset6.addValue( RA4 , "Operation" , "" );
dataset6.addValue( RA5 , "Maintenance" , "" );
dataset6.addValue( RA6 , "Scrapping" , "" );
dataset6.addValue( RA , "Total" , "" );
// Generate the graph
JFreeChart chart6 = ChartFactory.createBarChart(
        "Risk Assessment- RPN - " + case_name, // Title
        "Life Stages", // x-axis Label
        "Risk Priority Number", // y-axis Label
        dataset6, // Dataset
        PlotOrientation.HORIZONTAL, // Plot Orientation
        true, // Show Legend
        true, // Use tooltips
        false // Configure chart to generate URLs?
);                              ChartPanel CP6 = new ChartPanel(chart6);
panel_chart6 = new JPanel();
panel_chart6.add(CP6);
//create a window for piechart
//frame6 = new JFrame("Risk Assessment- RPN");
//frame6.setLayout(new BorderLayout());
//frame6.add(panel_chart6, BorderLayout.WEST);
//frame6.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//frame6.setSize(new Dimension(800, 600));
//frame6.setResizable(true);
//panel_chart6.setPreferredSize(new Dimension(frame6.getWidth()-120, frame6.getHeight()-150));
//frame6.pack();
//frame6.setVisible(true);
//add XY charts 7
DefaultCategoryDataset dataset7 =
        new DefaultCategoryDataset( );
dataset7.addValue( sum1+sum3+sum2+(GWP1+GWP3+GWP2)*P_GWP+(AP1+AP3+AP2)*P_AP+(EP1+EP+EP2)*P_EP+(POCP1+POCP3+POCP2)*P_POCP+RA2/1000*CoTL, "Construction" , "" );        //construction total cost
dataset7.addValue( sum4+GWP4*P_GWP+AP4*P_AP+EP4*P_EP+POCP4*P_POCP+RA4/1000*CoTL , "Operation" , "" );        	//operation total cost
dataset7.addValue( sum5, "Maintenance", "");																		//maintenance total cost
dataset7.addValue( sum6+GWP6*P_GWP+AP6*P_AP+EP6*P_EP+POCP6*P_POCP+RA6/1000*CoTL , "Scrapping" , "" ); 			//scrapping total cost
dataset7.addValue( sum+GWP*P_GWP+AP*P_AP+EP*P_EP+POCP*P_POCP+RA/1000*CoTL , "Total" , "" );     				//life cycle total cost
// Generate the graph
JFreeChart chart7 = ChartFactory.createBarChart(
        "Total Life Cycle Cost - " + case_name, // Title
        "Life Stages", // x-axis Label
        "Cost (Euro)", // y-axis Label
        dataset7, // Dataset
        PlotOrientation.HORIZONTAL, // Plot Orientation
        true, // Show Legend
        true, // Use tooltips
        false // Configure chart to generate URLs?
);                              ChartPanel CP7 = new ChartPanel(chart7);
panel_chart7 = new JPanel();

panel_chart7.add(CP7);
//create a window for piechart
//frame7 = new JFrame("Total Life Cycle Cost");
//frame7.setLayout(new BorderLayout());
//frame7.add(panel_chart7, BorderLayout.WEST);
//frame7.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//frame7.setSize(new Dimension(800, 600));
//frame7.setResizable(true);
//panel_chart7.setPreferredSize(new Dimension(frame7.getWidth()-120, frame7.getHeight()-150));
//frame7.pack();
//frame7.setVisible(true);
//plot results in one frame
Frame = new JFrame("Results plots");
tp_plot1= new JTabbedPane();
tp_plot1.addTab("Cost",ii,panel_chart1,"Cost");
tp_plot1.addTab("GWP",ii,panel_chart2,"GWP");
tp_plot1.addTab("AP",ii,panel_chart3,"AP");
tp_plot1.addTab("EP",ii,panel_chart4,"EP");
tp_plot1.addTab("POCP",ii,panel_chart5,"POCP");
tp_plot1.addTab("RPN",ii,panel_chart6,"RPN");
tp_plot1.addTab("Total cost",ii,panel_chart7,"Total cost");
JPanel panel_plot1 = new JPanel();
tp_plot1.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
tp_plot1.setTabPlacement(JTabbedPane.TOP);
panel_plot1.add(tp_plot1);
//JButton updateButton = new JButton();
//updateButton.setText("update");
//updateButton.addActionListener(createUpdateListener(0));

//panel_plot1.add(updateButton);
//panel_plot1.add(tp_plot2);
//tp_plot2.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
//tp_plot2.setTabPlacement(JTabbedPane.TOP);
//panel_plot1.add(tp_plot3);
//tp_plot3.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
//tp_plot3.setTabPlacement(JTabbedPane.TOP);
//panel_plot1.add(tp_plot4);
//tp_plot4.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
//tp_plot4.setTabPlacement(JTabbedPane.TOP);
//panel_plot1.add(tp_plot5);
//tp_plot5.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
//tp_plot5.setTabPlacement(JTabbedPane.TOP);
//panel_plot1.add(tp_plot6);
//tp_plot6.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
//tp_plot6.setTabPlacement(JTabbedPane.TOP);
//panel_plot1.add(tp_plot7);
//tp_plot7.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
//tp_plot7.setTabPlacement(JTabbedPane.TOP);
//Frame.setLayout(new BorderLayout());
Frame= new JFrame();
Frame.add(panel_plot1);
//Frame.add(updateButton);
// Frame.add(tp_plot2);
// Frame.add(tp_plot3);
// Frame.add(tp_plot4);
// Frame.add(tp_plot5);
// Frame.add(tp_plot6);
// Frame.add(tp_plot7);
Frame.setExtendedState(JFrame.NORMAL);
Frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
Frame.setResizable(false);
Frame.pack();
Frame.setVisible(true);

//Frame.revalidate();
//Frame = new JFrame("Results plots");
//Frame.setLayout(new GridBagLayout());
//GridBagConstraints c = new GridBagConstraints();
//c.fill = GridBagConstraints.HORIZONTAL;
//c.anchor = GridBagConstraints.FIRST_LINE_START; //bottom of space
//c.gridx = 0;       //aligned with button 2
//c.gridy = 0;       //third row
//Frame.add(CP1, c);
//
//GridBagConstraints c1 = new GridBagConstraints();
//c1.fill = GridBagConstraints.HORIZONTAL;
//c.anchor = GridBagConstraints.PAGE_START; //bottom of space
//
//c.gridx = 0;       //aligned with button 2
//c.gridy = 1;       //third row
//Frame.add(CP2, c1);
//
//GridBagConstraints c2 = new GridBagConstraints();
//c2.fill = GridBagConstraints.HORIZONTAL;
//c.anchor = GridBagConstraints.FIRST_LINE_END; //bottom of space
//
//c.gridx = 0;       //aligned with button 2
//c.gridy = 2;       //third row
//Frame.add(CP3, c2);
//
//GridBagConstraints c3 = new GridBagConstraints();
//c3.fill = GridBagConstraints.HORIZONTAL;
//c.anchor = GridBagConstraints.LINE_START; //bottom of space
//
//c.gridx = 1;       //aligned with button 2
//c.gridy = 0;       //third row
//Frame.add(CP4, c3);
//
//GridBagConstraints c4 = new GridBagConstraints();
//c4.fill = GridBagConstraints.HORIZONTAL;
//c.anchor = GridBagConstraints.CENTER; //bottom of space
//
//c.gridx = 1;       //aligned with button 2
//c.gridy = 1;       //third row
//Frame.add(CP5, c4);
//
//GridBagConstraints c5 = new GridBagConstraints();
//c5.fill = GridBagConstraints.HORIZONTAL;
//c.anchor = GridBagConstraints.LINE_END; //bottom of space
//
//c.gridx = 1;       //aligned with button 2
//c.gridy = 2;       //third row
//Frame.add(CP6, c5);
//
//GridBagConstraints c6 = new GridBagConstraints();
//c6.fill = GridBagConstraints.HORIZONTAL;
//c.anchor = GridBagConstraints.PAGE_END; //bottom of space
//
//c.gridx = 2;       //aligned with button 2
//c.gridy = 1;       //third row
//Frame.add(CP7, c6);
//Frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//Frame.setSize(new Dimension(screenSize));
//Frame.setResizable(true);
////Frame.pack();
//Frame.setVisible(true);
FileOutputStream fout;
fout = new FileOutputStream(cwd+"/reports/result_"+case_name+"_" +dateFormat.format(d)+".xls");
//Total spreadsheet
HSSFWorkbook wb1 = new HSSFWorkbook();
HSSFSheet sheet1 = wb1.createSheet("Total");
HSSFRow row0 = sheet1.createRow(0);
HSSFRow row1 = sheet1.createRow(1);
HSSFRow row2 = sheet1.createRow(2);
HSSFCell item0 = row0.createCell(0);
HSSFCell item1 = row0.createCell(1);
HSSFCell item2 = row0.createCell(2);
HSSFCell item3 = row0.createCell(3);
HSSFCell item4 = row0.createCell(4);
HSSFCell item5 = row0.createCell(5);
HSSFCell num0 = row1.createCell(0);
HSSFCell num1 = row1.createCell(1);
HSSFCell num2 = row1.createCell(2);
HSSFCell num3 = row1.createCell(3);
HSSFCell num4 = row1.createCell(4);
HSSFCell num5 = row1.createCell(5);
HSSFCell unit0 = row2.createCell(0);
HSSFCell unit1 = row2.createCell(1);
HSSFCell unit2 = row2.createCell(2);
HSSFCell unit3 = row2.createCell(3);
HSSFCell unit4 = row2.createCell(4);
HSSFCell unit5 = row2.createCell(5);

num0.setCellType(CellType.STRING);
num1.setCellType(CellType.NUMERIC);
num2.setCellType(CellType.NUMERIC);
num3.setCellType(CellType.NUMERIC);
num4.setCellType(CellType.NUMERIC);
num5.setCellType(CellType.NUMERIC);
num0.setCellValue("Total life cycle cost");
num1.setCellValue(sum1+sum2+sum3+(GWP1+GWP3+GWP2)*P_GWP+(AP1+AP3+AP2)*P_AP+(EP1+EP3+EP2)*P_EP+(POCP1+POCP3+POCP2)*P_POCP+RA2/1000*CoTL);
num2.setCellValue(sum4+GWP4*P_GWP+AP4*P_AP+EP4*P_EP+POCP4*P_POCP+RA4/1000*CoTL);
num3.setCellValue(sum5);
num4.setCellValue(sum6+GWP6*P_GWP+AP6*P_AP+EP6*P_EP+POCP6*P_POCP+RA6/1000*CoTL);
num5.setCellValue(sum+GWP*P_GWP+AP*P_AP+EP*P_EP+POCP*P_POCP+RA/1000*CoTL);
//num1.setCellValue(12222);//num5.setCellValue(12222);
item0.setCellType(CellType.STRING);
item1.setCellType(CellType.STRING);
item2.setCellType(CellType.STRING);
item3.setCellType(CellType.STRING);
item4.setCellType(CellType.STRING);
item5.setCellType(CellType.STRING);
item0.setCellValue("Life stages");
item1.setCellValue("Construction");
item2.setCellValue("Operation");
item3.setCellValue("Maintenance");
item4.setCellValue("Scrapping");
item5.setCellValue("Total cost");
unit0.setCellType(CellType.STRING);
unit1.setCellType(CellType.STRING);
unit2.setCellType(CellType.STRING);
unit3.setCellType(CellType.STRING);
unit4.setCellType(CellType.STRING);
unit5.setCellType(CellType.STRING);
unit0.setCellValue("Unit");
unit1.setCellValue("Euro");
unit2.setCellValue("Euro");
unit3.setCellValue("Euro");
unit4.setCellValue("Euro");
unit5.setCellValue("Euro");
//Detail report spreadsheet
HSSFSheet sheet2 = wb1.createSheet("Report");
HSSFRow row0_1 = sheet2.createRow(0);
HSSFRow row1_1 = sheet2.createRow(1);
HSSFRow row2_1 = sheet2.createRow(2);

//HSSFRow row4_1 = sheet2.createRow(4);
//HSSFRow row5_1 = sheet2.createRow(5);
//HSSFRow row6_1 = sheet2.createRow(6);
//
//HSSFRow row8_1 = sheet2.createRow(8);
//HSSFRow row9_1 = sheet2.createRow(9);
//HSSFRow row10_1 = sheet2.createRow(10);
//
//HSSFRow row12_1 = sheet2.createRow(12);
//HSSFRow row13_1 = sheet2.createRow(13);
//HSSFRow row14_1 = sheet2.createRow(14);
//
//HSSFRow row16_1 = sheet2.createRow(16);
//HSSFRow row17_1 = sheet2.createRow(17);
//HSSFRow row18_1 = sheet2.createRow(18);
//
//HSSFRow row20_1 = sheet2.createRow(20);
//HSSFRow row21_1 = sheet2.createRow(21);
//HSSFRow row22_1 = sheet2.createRow(22);
//
//HSSFRow row0_1 = sheet2.createRow(24);
//HSSFRow row1_1 = sheet2.createRow(25);
//HSSFRow row2_1 = sheet2.createRow(26);
HSSFCell item0_1 = row0_1.createCell(0);
HSSFCell item1_1 = row0_1.createCell(1);
HSSFCell item2_1 = row0_1.createCell(2);
HSSFCell item3_1 = row0_1.createCell(3);
HSSFCell item4_1 = row0_1.createCell(4);
HSSFCell item5_1 = row0_1.createCell(5);
HSSFCell item6_1 = row0_1.createCell(6);
HSSFCell item7_1 = row0_1.createCell(7);
HSSFCell item8_1 = row0_1.createCell(8);
HSSFCell item9_1 = row0_1.createCell(9);
HSSFCell item10_1 = row0_1.createCell(10);
HSSFCell item11_1 = row0_1.createCell(11);
HSSFCell item12_1 = row0_1.createCell(12);
HSSFCell item13_1 = row0_1.createCell(13);
HSSFCell item14_1 = row0_1.createCell(14);
HSSFCell item15_1 = row0_1.createCell(15);
HSSFCell item16_1 = row0_1.createCell(16);
HSSFCell item17_1 = row0_1.createCell(17);
HSSFCell item18_1 = row0_1.createCell(18);
HSSFCell item19_1 = row0_1.createCell(19);
HSSFCell item20_1 = row0_1.createCell(20);
HSSFCell item21_1 = row0_1.createCell(21);
HSSFCell item22_1 = row0_1.createCell(22);
HSSFCell item23_1 = row0_1.createCell(23);
HSSFCell item24_1 = row0_1.createCell(24);
HSSFCell item25_1 = row0_1.createCell(25);
HSSFCell item26_1 = row0_1.createCell(26);
HSSFCell item27_1 = row0_1.createCell(27);
HSSFCell item28_1 = row0_1.createCell(28);
HSSFCell item29_1 = row0_1.createCell(29);
HSSFCell item30_1 = row0_1.createCell(30);
HSSFCell item31_1 = row0_1.createCell(31);
HSSFCell item32_1 = row0_1.createCell(32);
HSSFCell item33_1 = row0_1.createCell(33);
HSSFCell item34_1 = row0_1.createCell(34);
HSSFCell item35_1 = row0_1.createCell(35);

HSSFCell num0_1 = row1_1.createCell(0);
HSSFCell num1_1 = row1_1.createCell(1);
HSSFCell num2_1 = row1_1.createCell(2);
HSSFCell num3_1 = row1_1.createCell(3);
HSSFCell num4_1 = row1_1.createCell(4);
HSSFCell num5_1 = row1_1.createCell(5);
HSSFCell num6_1 = row1_1.createCell(6);
HSSFCell num7_1 = row1_1.createCell(7);
HSSFCell num8_1 = row1_1.createCell(8);
HSSFCell num9_1 = row1_1.createCell(9);
HSSFCell num10_1 = row1_1.createCell(10);
HSSFCell num11_1 = row1_1.createCell(11);
HSSFCell num12_1 = row1_1.createCell(12);
HSSFCell num13_1 = row1_1.createCell(13);
HSSFCell num14_1 = row1_1.createCell(14);
HSSFCell num15_1 = row1_1.createCell(15);
HSSFCell num16_1 = row1_1.createCell(16);
HSSFCell num17_1 = row1_1.createCell(17);
HSSFCell num18_1 = row1_1.createCell(18);
HSSFCell num19_1 = row1_1.createCell(19);
HSSFCell num20_1 = row1_1.createCell(20);
HSSFCell num21_1 = row1_1.createCell(21);
HSSFCell num22_1 = row1_1.createCell(22);
HSSFCell num23_1 = row1_1.createCell(23);
HSSFCell num24_1 = row1_1.createCell(24);
HSSFCell num25_1 = row1_1.createCell(25);
HSSFCell num26_1 = row1_1.createCell(26);
HSSFCell num27_1 = row1_1.createCell(27);
HSSFCell num28_1 = row1_1.createCell(28);
HSSFCell num29_1 = row1_1.createCell(29);
HSSFCell num30_1 = row1_1.createCell(30);
HSSFCell num31_1 = row1_1.createCell(31);
HSSFCell num32_1 = row1_1.createCell(32);
HSSFCell num33_1 = row1_1.createCell(33);
HSSFCell num34_1 = row1_1.createCell(34);
HSSFCell num35_1 = row1_1.createCell(35);

HSSFCell unit0_1 = row2_1.createCell(0);
HSSFCell unit1_1 = row2_1.createCell(1);
HSSFCell unit2_1 = row2_1.createCell(2);
HSSFCell unit3_1 = row2_1.createCell(3);
HSSFCell unit4_1 = row2_1.createCell(4);
HSSFCell unit5_1 = row2_1.createCell(5);
HSSFCell unit6_1 = row2_1.createCell(6);
HSSFCell unit7_1 = row2_1.createCell(7);
HSSFCell unit8_1 = row2_1.createCell(8);
HSSFCell unit9_1 = row2_1.createCell(9);
HSSFCell unit10_1 = row2_1.createCell(10);
HSSFCell unit11_1 = row2_1.createCell(11);
HSSFCell unit12_1 = row2_1.createCell(12);
HSSFCell unit13_1 = row2_1.createCell(13);
HSSFCell unit14_1 = row2_1.createCell(14);
HSSFCell unit15_1 = row2_1.createCell(15);
HSSFCell unit16_1 = row2_1.createCell(16);
HSSFCell unit17_1 = row2_1.createCell(17);
HSSFCell unit18_1 = row2_1.createCell(18);
HSSFCell unit19_1 = row2_1.createCell(19);
HSSFCell unit20_1 = row2_1.createCell(20);
HSSFCell unit21_1 = row2_1.createCell(21);
HSSFCell unit22_1 = row2_1.createCell(22);
HSSFCell unit23_1 = row2_1.createCell(23);
HSSFCell unit24_1 = row2_1.createCell(24);
HSSFCell unit25_1 = row2_1.createCell(25);
HSSFCell unit26_1 = row2_1.createCell(26);
HSSFCell unit27_1 = row2_1.createCell(27);
HSSFCell unit28_1 = row2_1.createCell(28);
HSSFCell unit29_1 = row2_1.createCell(29);
HSSFCell unit30_1 = row2_1.createCell(30);
HSSFCell unit31_1 = row2_1.createCell(31);
HSSFCell unit32_1 = row2_1.createCell(32);
HSSFCell unit33_1 = row2_1.createCell(33);
HSSFCell unit34_1 = row2_1.createCell(34);
HSSFCell unit35_1 = row2_1.createCell(35);

num0_1.setCellType(CellType.STRING);
num0_1.setCellValue("Value");
num1_1.setCellType(CellType.NUMERIC);
num2_1.setCellType(CellType.NUMERIC);
num3_1.setCellType(CellType.NUMERIC);
num4_1.setCellType(CellType.NUMERIC);
num5_1.setCellType(CellType.NUMERIC);
num6_1.setCellType(CellType.NUMERIC);
num7_1.setCellType(CellType.NUMERIC);
num8_1.setCellType(CellType.NUMERIC);
num9_1.setCellType(CellType.NUMERIC);
num10_1.setCellType(CellType.NUMERIC);
num11_1.setCellType(CellType.NUMERIC);
num12_1.setCellType(CellType.NUMERIC);
num13_1.setCellType(CellType.NUMERIC);
num14_1.setCellType(CellType.NUMERIC);
num15_1.setCellType(CellType.NUMERIC);
num16_1.setCellType(CellType.NUMERIC);
num17_1.setCellType(CellType.NUMERIC);
num18_1.setCellType(CellType.NUMERIC);
num19_1.setCellType(CellType.NUMERIC);
num20_1.setCellType(CellType.NUMERIC);
num21_1.setCellType(CellType.NUMERIC);
num22_1.setCellType(CellType.NUMERIC);
num23_1.setCellType(CellType.NUMERIC);
num24_1.setCellType(CellType.NUMERIC);
num25_1.setCellType(CellType.NUMERIC);
num26_1.setCellType(CellType.NUMERIC);
num27_1.setCellType(CellType.NUMERIC);
num28_1.setCellType(CellType.NUMERIC);
num29_1.setCellType(CellType.NUMERIC);
num30_1.setCellType(CellType.NUMERIC);
num31_1.setCellType(CellType.NUMERIC);
num32_1.setCellType(CellType.NUMERIC);
num33_1.setCellType(CellType.NUMERIC);
num34_1.setCellType(CellType.NUMERIC);
num35_1.setCellType(CellType.NUMERIC);

num1_1.setCellValue( sum1+sum2+sum3 );
num2_1.setCellValue( sum4 );
num3_1.setCellValue( sum6 );
num4_1.setCellValue( sum );
num5_1.setCellValue( GWP_C );
num6_1.setCellValue( GWP4 );
num7_1.setCellValue( GWP6 );
num8_1.setCellValue( GWP );
num9_1.setCellValue( AP_C );
num10_1.setCellValue( AP4 );
num11_1.setCellValue( AP6 );
num12_1.setCellValue( AP );
num13_1.setCellValue( EP_C );
num14_1.setCellValue( EP4 );
num15_1.setCellValue( EP6 );
num16_1.setCellValue( EP );
num17_1.setCellValue( POCP_C );
num18_1.setCellValue( POCP4 );
num19_1.setCellValue( POCP6 );
num20_1.setCellValue( POCP );
num21_1.setCellValue( RA2 );
num22_1.setCellValue( RA4 );
num23_1.setCellValue( RA6 );
num24_1.setCellValue( RA );
num25_1.setCellValue( Total_cost );
num26_1.setCellValue( Total_GWP );
num27_1.setCellValue( Total_AP );
num28_1.setCellValue( Total_EP );
num29_1.setCellValue( Total_POCP );
num30_1.setCellValue( Total_RA );
num31_1.setCellValue( Total_CRA );
num32_1.setCellValue( GWP*P_GWP );
num33_1.setCellValue( AP*P_AP );
num34_1.setCellValue( EP*P_EP );
num35_1.setCellValue( POCP*P_POCP );

item0_1.setCellType(CellType.STRING);
item1_1.setCellType(CellType.STRING);
item2_1.setCellType(CellType.STRING);
item3_1.setCellType(CellType.STRING);
item4_1.setCellType(CellType.STRING);
item5_1.setCellType(CellType.STRING);
item6_1.setCellType(CellType.STRING);
item7_1.setCellType(CellType.STRING);
item8_1.setCellType(CellType.STRING);
item9_1.setCellType(CellType.STRING);
item10_1.setCellType(CellType.STRING);
item11_1.setCellType(CellType.STRING);
item12_1.setCellType(CellType.STRING);
item13_1.setCellType(CellType.STRING);
item14_1.setCellType(CellType.STRING);
item15_1.setCellType(CellType.STRING);
item16_1.setCellType(CellType.STRING);
item17_1.setCellType(CellType.STRING);
item18_1.setCellType(CellType.STRING);
item19_1.setCellType(CellType.STRING);
item20_1.setCellType(CellType.STRING);
item21_1.setCellType(CellType.STRING);
item22_1.setCellType(CellType.STRING);
item23_1.setCellType(CellType.STRING);
item24_1.setCellType(CellType.STRING);
item25_1.setCellType(CellType.STRING);
item26_1.setCellType(CellType.STRING);
item27_1.setCellType(CellType.STRING);
item28_1.setCellType(CellType.STRING);
item29_1.setCellType(CellType.STRING);
item30_1.setCellType(CellType.STRING);
item31_1.setCellType(CellType.STRING);
item32_1.setCellType(CellType.STRING);
item33_1.setCellType(CellType.STRING);
item34_1.setCellType(CellType.STRING);
item35_1.setCellType(CellType.STRING);

item0_1.setCellValue("	Phases & items	");
item1_1.setCellValue("	Construction cost	");
item2_1.setCellValue("	Operation cost	");
item3_1.setCellValue("	Scrapping cost  	");
item4_1.setCellValue("	Total cost  	");
item5_1.setCellValue("	Construction GWP  	");
item6_1.setCellValue("	Operation GWP  	");
item7_1.setCellValue("	Scrapping GWP  	");
item8_1.setCellValue("	Total GWP  	");
item9_1.setCellValue("	Construction AP  	");
item10_1.setCellValue("	Operation AP 	");
item11_1.setCellValue("	Scrapping AP  	");
item12_1.setCellValue("	Total AP  	");
item13_1.setCellValue("	Construction EP  	");
item14_1.setCellValue("	Operation EP  	");
item15_1.setCellValue("	Scrapping EP	");
item16_1.setCellValue("	Total EP	");
item17_1.setCellValue("	Construction POCP	");
item18_1.setCellValue("	Operation POCP	");
item19_1.setCellValue("	Scrapping POCP	");
item20_1.setCellValue("	Total POCP	");
item21_1.setCellValue("	Construction RPN	");
item22_1.setCellValue("	Operation RPN	");
item23_1.setCellValue("	Scrapping RPN	");
item24_1.setCellValue("	Total RPN	");
item25_1.setCellValue("	Life Cycle Cost 	");
item26_1.setCellValue("	GWP 	");
item27_1.setCellValue("	AP 	");
item28_1.setCellValue("	EP	");
item29_1.setCellValue("	POCP	");
item30_1.setCellValue("	RPN	");
item31_1.setCellValue("	Risk Cost	");
item32_1.setCellValue("	GWP 	");
item33_1.setCellValue("	AP 	");
item34_1.setCellValue("	EP	");
item35_1.setCellValue("	POCP	");

unit0_1.setCellType(CellType.STRING);
unit1_1.setCellType(CellType.STRING);
unit2_1.setCellType(CellType.STRING);
unit3_1.setCellType(CellType.STRING);
unit4_1.setCellType(CellType.STRING);
unit5_1.setCellType(CellType.STRING);
unit6_1.setCellType(CellType.STRING);
unit7_1.setCellType(CellType.STRING);
unit8_1.setCellType(CellType.STRING);
unit9_1.setCellType(CellType.STRING);
unit10_1.setCellType(CellType.STRING);
unit11_1.setCellType(CellType.STRING);
unit12_1.setCellType(CellType.STRING);
unit13_1.setCellType(CellType.STRING);
unit14_1.setCellType(CellType.STRING);
unit15_1.setCellType(CellType.STRING);
unit16_1.setCellType(CellType.STRING);
unit17_1.setCellType(CellType.STRING);
unit18_1.setCellType(CellType.STRING);
unit19_1.setCellType(CellType.STRING);
unit20_1.setCellType(CellType.STRING);
unit21_1.setCellType(CellType.STRING);
unit22_1.setCellType(CellType.STRING);
unit23_1.setCellType(CellType.STRING);
unit24_1.setCellType(CellType.STRING);
unit25_1.setCellType(CellType.STRING);
unit26_1.setCellType(CellType.STRING);
unit27_1.setCellType(CellType.STRING);
unit28_1.setCellType(CellType.STRING);
unit29_1.setCellType(CellType.STRING);
unit30_1.setCellType(CellType.STRING);
unit31_1.setCellType(CellType.STRING);
unit32_1.setCellType(CellType.STRING);
unit33_1.setCellType(CellType.STRING);
unit34_1.setCellType(CellType.STRING);
unit35_1.setCellType(CellType.STRING);

unit0_1.setCellValue("Units");
unit1_1.setCellValue("Euro");
unit2_1.setCellValue("Euro");
unit3_1.setCellValue("Euro");
unit4_1.setCellValue("Euro");
unit5_1.setCellValue("ton CO2e");
unit6_1.setCellValue("ton CO2e");
unit7_1.setCellValue("ton CO2e");
unit8_1.setCellValue("ton CO2e");
unit9_1.setCellValue("ton SO2e");
unit10_1.setCellValue("ton SO2e");
unit11_1.setCellValue("ton SO2e");
unit12_1.setCellValue("ton SO2e");
unit13_1.setCellValue("ton PO4e");
unit14_1.setCellValue("ton PO4e");
unit15_1.setCellValue("ton PO4e");
unit16_1.setCellValue("ton PO4e");
unit17_1.setCellValue("ton C2H6e");
unit18_1.setCellValue("ton C2H6e");
unit19_1.setCellValue("ton C2H6e");
unit20_1.setCellValue("ton C2H6e");
unit21_1.setCellValue("RPN");
unit22_1.setCellValue("RPN");
unit23_1.setCellValue("RPN");
unit24_1.setCellValue("RPN");
unit25_1.setCellValue("Euro");
unit26_1.setCellValue("ton CO2e");
unit27_1.setCellValue("ton SO2e");
unit28_1.setCellValue("ton PO4e");
unit29_1.setCellValue("ton C2H6e");
unit30_1.setCellValue("RPN");
unit31_1.setCellValue("Euro");
unit32_1.setCellValue("Euro");
unit33_1.setCellValue("Euro");
unit34_1.setCellValue("Euro");
unit35_1.setCellValue("Euro");


wb1.write(fout);



GUIexample guiExample = new GUIexample();

//Shows the default property values of the engine CatalogueItem
guiExample.showDefaultEngineValues();

//Shows the default property values of the battery CatalogueItem
guiExample.showDefaultBatteryValues();

//Create new engine product component
ProductComponent engine = guiExample.createEngine(data_m[0][12]);

//Replace the default price value of the new engine
String massValue = new MassMeasure(data_m[2][12], NonSI.TON_UK).toString();
LCAdataFactory.setProductComponentsProperty(engine, "mass", massValue);

String priceValue = new MoneyMeasure(data_m[3][12], Currency.EUR).toString();
LCAdataFactory.setProductComponentsProperty(engine, "price", priceValue);

String powerValue = new PowerMeasure(data_m[4][12], SI.KILO(SI.WATT)).toString();
LCAdataFactory.setProductComponentsProperty(engine, "powerRating", powerValue);

String sfocValue = new SpecificConsumptionMeasure(data_m[6][12]).toString();
LCAdataFactory.setProductComponentsProperty(engine, "sfoc", sfocValue);

String slocValue = new SpecificConsumptionMeasure(data_m[7][12]).toString();
LCAdataFactory.setProductComponentsProperty(engine, "sfoc_lo", slocValue);

String rh1Value = Arrays.asList(data_m1[10][12],data_m2[10][12],data_m3[10][12]).toString();
LCAdataFactory.setProductComponentsProperty(engine, "rh1", rh1Value);

String rh2Value = Arrays.asList(data_m1[11][12],data_m2[11][12],data_m3[11][12]).toString();
LCAdataFactory.setProductComponentsProperty(engine, "rh2", rh2Value);

String rh3Value = Arrays.asList(data_m1[12][12],data_m2[12][12],data_m3[12][12]).toString();
LCAdataFactory.setProductComponentsProperty(engine, "rh3", rh3Value);

String rh4Value = Arrays.asList(data_m1[13][12],data_m2[13][12],data_m3[13][12]).toString();
LCAdataFactory.setProductComponentsProperty(engine, "rh4", rh4Value);

String rh5Value = Arrays.asList(data_m1[14][12],data_m2[14][12],data_m3[14][12]).toString();
LCAdataFactory.setProductComponentsProperty(engine, "rh5", rh5Value);

String rh6Value = Arrays.asList(data_m1[15][12],data_m2[15][12],data_m3[15][12]).toString();
LCAdataFactory.setProductComponentsProperty(engine, "rh6", rh6Value);

String rh7Value = Arrays.asList(data_m1[16][12],data_m2[16][12],data_m3[16][12]).toString();
LCAdataFactory.setProductComponentsProperty(engine, "rh7", rh7Value);



//Create new battery product component
ProductComponent battery = guiExample.createBattery(data_m[0][14]);

String massValue1 = new MassMeasure(data_m[2][14], NonSI.TON_UK).toString();
LCAdataFactory.setProductComponentsProperty(battery, "mass", massValue1);

String priceValue1 = new MoneyMeasure(data_m[3][14], Currency.EUR).toString();
LCAdataFactory.setProductComponentsProperty(battery, "price", priceValue1);

String powerValue1 = new PowerMeasure(data_m[8][14], SI.KILO(SI.WATT)).toString();
LCAdataFactory.setProductComponentsProperty(battery, "powerRating", powerValue1);

//String sfocValue1 = new SpecificConsumptionMeasure(data_m[6][14]).toString();
//LCAdataFactory.setProductComponentsProperty(battery, "sfoc", sfocValue1);
//
//String slocValue1 = new SpecificConsumptionMeasure(data_m[7][14]).toString();
//LCAdataFactory.setProductComponentsProperty(battery, "sfoc_lo", slocValue1);

String rh1Value1 = Arrays.asList(data_m1[12][14],data_m2[12][14],data_m3[12][14]).toString();
LCAdataFactory.setProductComponentsProperty(battery, "rh1", rh1Value1);

String rh2Value1 = Arrays.asList(data_m1[13][14],data_m2[13][14],data_m3[13][14]).toString();
LCAdataFactory.setProductComponentsProperty(battery, "rh2", rh2Value1);

String rh3Value1 = Arrays.asList(data_m1[14][14],data_m2[14][14],data_m3[14][14]).toString();
LCAdataFactory.setProductComponentsProperty(battery, "rh3", rh3Value1);

String rh4Value1 = Arrays.asList(data_m1[15][14],data_m2[15][14],data_m3[15][14]).toString();
LCAdataFactory.setProductComponentsProperty(battery, "rh4", rh4Value1);

String rh5Value1 = Arrays.asList(data_m1[16][14],data_m2[16][14],data_m3[16][14]).toString();
LCAdataFactory.setProductComponentsProperty(battery, "rh5", rh5Value1);

String rh6Value1 = Arrays.asList(data_m1[17][14],data_m2[17][14],data_m3[17][14]).toString();
LCAdataFactory.setProductComponentsProperty(battery, "rh6", rh6Value1);

String rh7Value1 = Arrays.asList(data_m1[18][14],data_m2[18][14],data_m3[18][14]).toString();
LCAdataFactory.setProductComponentsProperty(battery, "rh7", rh7Value1);





//Create Scenario
AnalysisScenario scenario = LCAdataFactory.createAnalysisScenario("lcaAnalysisScenario");

//Create Case
AnalysisCase analysisCase = LCAdataFactory.createAnalysisCase(scenario, "lcaAnalysisCase");

//Create General LCA Parameters
ParameterSet lcaGeneralParameterSet = LCAdataFactory.createParameterSet(analysisCase, "GeneralParameters",
        "This set contains the common lca parameters.", "SHIPLYS general LCA parameter definitions");

LCAdataFactory.setLifeSpan(lcaGeneralParameterSet, Life_span);
LCAdataFactory.setPresentValue(lcaGeneralParameterSet,PV);
LCAdataFactory.setInterestRate(lcaGeneralParameterSet, Interest);
LCAdataFactory.setSensitivityLevel(lcaGeneralParameterSet, (int) SL);
LCAdataFactory.setShipTotalPrice(lcaGeneralParameterSet, CoTL);

  //---log general LCA parameters of the analysis case
log.info("******General");
for (KeyValue kv: lcaGeneralParameterSet.getParameters()) {
    log.info("Parameter name =" + kv.getKey() + ", Parameter type =" + kv.getTypeX() + ", Parameter value =" + kv
            .getValue() + "\n");
}


//Create Construction LCA Parameters
//TODO: in the same way as for General LCA Parameters. The related setter methods have also to be created (see LCAdataCreation for wich parameters)
//Create Operation LCA Parameters
//TODO: in the same way as for General LCA Parameters. The related setter methods have also to be created (see LCAdataCreation for wich parameters)
//Create Scrapping LCA Parameters
//TODO: in the same way as for General LCA Parameters. The related setter methods have also to be created (see LCAdataCreation for wich parameters)
//Create Evaluation Results
//TODO: in the same way as for General LCA Parameters. The related setter methods have also to be created (see LCAdataCreation for wich parameters)

//Create Result
EvaluationResult result = LCAdataFactory.createEvaluationResult(analysisCase, "evaluationResult");
double LCTC = sum+GWP*P_GWP+AP*P_AP+EP*P_EP+POCP*P_POCP+RA/1000*CoTL;
LCAdataFactory.setLifeCycleCost(result, sum);
LCAdataFactory.setLifeCycleTotalCost(result, LCTC);
LCAdataFactory.setGWP(result, GWP);
LCAdataFactory.setGWPCost(result, GWP*P_GWP);
LCAdataFactory.setAP(result, AP);
LCAdataFactory.setAPCost(result, AP*P_AP);
LCAdataFactory.setEP(result, EP);
LCAdataFactory.setEPCost(result, EP*P_EP);
LCAdataFactory.setPOCP(result, POCP);
LCAdataFactory.setPOCPCost(result, POCP*P_POCP);
LCAdataFactory.setRPN(result, Total_RA);
LCAdataFactory.setRPNCost(result, Total_CRA);

//TODO: create in the same way as for LifeCycleCost setter method for other values (see LCAdataCreation createEvaluationResults method)


//Save
LCAdataFactory.projOM.makePersistent(scenario);
LCAdataFactory.projOM.makePersistent(analysisCase);
LCAdataFactory.projOM.makePersistent(result);
LCAdataFactory.projOM.currentTransaction().commit();

 //Tear Down
try {
	LCAdataFactory.tearDownDataBase();
} catch (Exception e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
}





////	LCAdataCreation ldc = new LCAdataCreation();
//LCAdataCreation1.dts = new DirectoryTestSetup(LCAdataCreation1.class);
//LCAdataCreation1.log = Logger.getLogger(LCAdataCreation1.class);
////results 
//
//LCAdataCreation1.Life_Cycle_Cost = sum;
//LCAdataCreation1.Global_Warming_Potentials = GWP;
//LCAdataCreation1.Acidification_Potentials = AP;
//LCAdataCreation1.Eutrophication_Potentials = EP;
//LCAdataCreation1.Photochemical_Ozone_Creation_Potentials = POCP;
//LCAdataCreation1.Risk_Priority_Number = RA;
//LCAdataCreation1.Total_Life_Cycle_Cost = Total_cost;
////Main paramenter
//
//LCAdataCreation1.Ship_name = case_name;
//LCAdataCreation1.Life_span = (int) Life_span;
//LCAdataCreation1.Present_value = (int) PV;
//LCAdataCreation1.Interest_rate = Interest;
//LCAdataCreation1.Sensitivity_level = (int) SL;
//LCAdataCreation1.Ship_total_price = CoTL;
//LCAdataCreation1.Annual_working_hours = O1.	Ohour;
//LCAdataCreation1.Scrapping_fee = S1.	S_Price;
////Engine info.
//
//LCAdataCreation1.Engine_Name =CS1.Engine_type;
//LCAdataCreation1.E_Numbers= (int) CS1.Number;
//LCAdataCreation1.E_Weight= CS1.Weight;
//LCAdataCreation1.E_Price= CS1.Price;
//LCAdataCreation1.E_Max_Output=Double.parseDouble(data_m[4][12]);
//LCAdataCreation1.E_Actual_Output=O1.Eload;
//LCAdataCreation1.E_SFOC=O1.SFOC;
//LCAdataCreation1.E_SFOC_LO=O1.SLOC;
//LCAdataCreation1.E_RH1_1=CS1.F[0];
//LCAdataCreation1.E_RH2_1=CS1.F[1];
//LCAdataCreation1.E_RH3_1=CS1.F[2];
//LCAdataCreation1.E_RH4_1=CS1.F[3];
//LCAdataCreation1.E_RH5_1=CS1.F[4];
//LCAdataCreation1.E_RH6_1=CS1.F[5];
//LCAdataCreation1.E_RH7_1=CS1.F[6];
//LCAdataCreation1.E_RH1_2=CS1.C[0];
//LCAdataCreation1.E_RH2_2=CS1.C[1];
//LCAdataCreation1.E_RH3_2=CS1.C[2];
//LCAdataCreation1.E_RH4_2=CS1.C[3];
//LCAdataCreation1.E_RH5_2=CS1.C[4];
//LCAdataCreation1.E_RH6_2=CS1.C[5];
//LCAdataCreation1.E_RH7_2=CS1.C[6];
//LCAdataCreation1.E_RH1_3=CS1.M[0];
//LCAdataCreation1.E_RH2_3=CS1.M[1];
//LCAdataCreation1.E_RH3_3=CS1.M[2];
//LCAdataCreation1.E_RH4_3=CS1.M[3];
//LCAdataCreation1.E_RH5_3=CS1.M[4];
//LCAdataCreation1.E_RH6_3=CS1.M[5];
//LCAdataCreation1.E_RH7_3=CS1.M[6];
////Battery info.
//
//LCAdataCreation1.Battery_Name = CS2.	Engine_type;
//LCAdataCreation1.B_Numbers = (int) CS2.Number;
//LCAdataCreation1.B_Weight= CS2.Weight;
//LCAdataCreation1.B_Price= CS2.Price;
//LCAdataCreation1.B_Max_Output=Double.parseDouble(data_m[7][14]);
//LCAdataCreation1.B_Actual_Output=Double.parseDouble(data_m[8][14]);
//LCAdataCreation1.B_RH1_1=CS2.F[0];
//LCAdataCreation1.B_RH2_1=CS2.F[1];
//LCAdataCreation1.B_RH3_1=CS2.F[2];
//LCAdataCreation1.B_RH4_1=CS2.F[3];
//LCAdataCreation1.B_RH5_1=CS2.F[4];
//LCAdataCreation1.B_RH6_1=CS2.F[5];
//LCAdataCreation1.B_RH7_1=CS2.F[6];
//LCAdataCreation1.B_RH1_2=CS2.C[0];
//LCAdataCreation1.B_RH2_2=CS2.C[1];
//LCAdataCreation1.B_RH3_2=CS2.C[2];
//LCAdataCreation1.B_RH4_2=CS2.C[3];
//LCAdataCreation1.B_RH5_2=CS2.C[4];
//LCAdataCreation1.B_RH6_2=CS2.C[5];
//LCAdataCreation1.B_RH7_2=CS2.C[6];
//LCAdataCreation1.B_RH1_3=CS2.M[0];
//LCAdataCreation1.B_RH2_3=CS2.M[1];
//LCAdataCreation1.B_RH3_3=CS2.M[2];
//LCAdataCreation1.B_RH4_3=CS2.M[3];
//LCAdataCreation1.B_RH5_3=CS2.M[4];
//LCAdataCreation1.B_RH6_3=CS2.M[5];
//LCAdataCreation1.B_RH7_3=CS2.M[6];
////Transportation info
////C
//LCAdataCreation1. C_Transportation_type= CS1.Transportation_type;
//LCAdataCreation1. C_Distance= CS1.Transportation_distance;
//LCAdataCreation1. C_Trans_Fee= CS1.Transportation_fee;
//LCAdataCreation1. C_Trans_SFOC= CS1.Transportation_SFOC;
//LCAdataCreation1. C_Trans_Fuel_price=CS1.Transportation_fuel_price;
//LCAdataCreation1. C_Trans_GWP=CS1.Spec_GWP_Trans;
//LCAdataCreation1. C_Trans_AP=CS1.Spec_AP_Trans;
//LCAdataCreation1. C_Trans_EP=CS1.Spec_EP_Trans;
//LCAdataCreation1. C_Trans_POCP=CS1.Spec_POCP_Trans;
////Electricity info
////C
//LCAdataCreation1. C_Electricity_type= CS1.Electricity_type;
//LCAdataCreation1. C_Elec_Price=CS1.Installation_energy_price;
//LCAdataCreation1. C_Elec_GWP= CS1.Spec_GWP_E;
//LCAdataCreation1. C_Elec_AP=CS1.Spec_AP_E;
//LCAdataCreation1. C_Elec_EP=CS1.Spec_EP_E;
//LCAdataCreation1. C_Elec_POCP=CS1.Spec_POCP_E;
////Fuel info
//LCAdataCreation1. Fuel_type= O1.Fuel_type;
//LCAdataCreation1. Fuel_price= O1.Fuel_price;
//LCAdataCreation1. Fuel_GWP=O1.Spec_GWP_FO;
//LCAdataCreation1. Fuel_AP=O1.Spec_AP_FO;
//LCAdataCreation1. Fuel_EP=O1.Spec_EP_FO;
//LCAdataCreation1. Fuel_POCP=O1.Spec_POCP_FO;
//LCAdataCreation1. Carbon_emission_factor=O1.C_Factor;
//LCAdataCreation1. Sulfer_content=O1.S_Factor;
//LCAdataCreation1. Nitrogen_content=O1.N_Factor;
////LO info
//LCAdataCreation1. LO_type= O1.LO_type;
//LCAdataCreation1. LO_price= O1.LO_price;
//LCAdataCreation1. LO_GWP= O1.Spec_GWP_LO;
//LCAdataCreation1. LO_AP=O1.Spec_AP_LO;
//LCAdataCreation1. LO_EP=O1.Spec_EP_LO;
//LCAdataCreation1. LO_POCP=O1.Spec_POCP_LO;
////Material info
//LCAdataCreation1. Cut_type= S1.Cut_type;
//LCAdataCreation1. Electricity_meter= S1.Cutting_energy_per_meter;
//LCAdataCreation1. Material_quantity= S1.Cutting_material;
//LCAdataCreation1. Material_price=S1.Cutting_M_price;
//LCAdataCreation1. Cutting_length=S1.Cutting_length;
//                                try {
//                                    LCAdataCreation1.run();
////System.out.println(LCAdataCreation.Battery_Name) ;
////System.out.println(LCAdataCreation.B_Numbers);
//                                } catch (Exception ex) {
//                                    java.util.logging.Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
//                                }
                            } catch (FileNotFoundException ex) {
                                java.util.logging.Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IOException ex) {
                                java.util.logging.Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
 });
//		//save
//		saveButton.addActionListener(new ActionListener() {
//
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//		});
//		//compare
		//System.out.println(q);
//	     saveButton.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//                            
//                        }
//             });
	     panel6 = createPanel("");	//add panel 
		 tp.addTab("Comparison",ii,panel6,"Comparison");
	     tp.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	     tp.setTabPlacement(JTabbedPane.TOP);
	     panel6.setLayout(new BoxLayout(panel6,BoxLayout.Y_AXIS));
	     //setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
   
	   

		 
	     JPanel panel61 = new JPanel();
	     panel61.setAlignmentX(Component.CENTER_ALIGNMENT);
	     panel61.setLayout(new FlowLayout());
	     JTextField F61 = new JTextField("Please rename for Alternative 1 results");
	     F61.setFont(font_1);
	     F61.setPreferredSize(new Dimension (frame.getWidth()-320,frame.getHeight()/3-100));
	     JButton B61 = new JButton("select file");
	     B61.setFont(font_1);
	     B61.setPreferredSize(new Dimension (160,frame.getHeight()/3-100));
	     B61.addActionListener(new ActionListener(){
			@Override
	 		public void actionPerformed(ActionEvent e) {
	 			JFileChooser chooser = new JFileChooser(cwd);
	 		    int filename = chooser.showOpenDialog(null);
                                if (filename == JFileChooser.APPROVE_OPTION){
	 			File f = chooser.getSelectedFile();
	//BufferedReader br=new BufferedReader(new FileReader(f));
	 			try {
					Workbook 	wb_r1 = Workbook.getWorkbook(f);
					Sheet 	sheet_r = wb_r1.getSheet(0);
					Cell 	c1=sheet_r.getCell(1, 1);
					Cell 	c2=sheet_r.getCell(2, 1);
					Cell 	c3=sheet_r.getCell(3, 1);
					Cell	c4=sheet_r.getCell(4, 1);
					Cell	c5=sheet_r.getCell(5, 1);
					
					String s1 = c1.getContents(); 
					String s2 = c2.getContents(); 
					String s3 = c3.getContents(); 
					String s4 = c4.getContents(); 
					String s5 = c5.getContents(); 
					
					double r1 = Double.parseDouble(s1);
					double r2 = Double.parseDouble(s2);
					double r3 = Double.parseDouble(s3);
					double r4 = Double.parseDouble(s4);
					double r5 = Double.parseDouble(s5);
					
					a_result[0] =r1;
					a_result[1] =r2;
					a_result[2] =r3;
					a_result[3] =r4;
					a_result[4] =r5;
					
					
				     System.out.println(a_result[0]);	 
				     System.out.println(a_result[1]);	
				     System.out.println(a_result[2]);	
				     System.out.println(a_result[3]);	
				     System.out.println(a_result[4]);	


					
					
					
				} catch (BiffException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	}}});
	
	     
	     JPanel panel62 = new JPanel();
	     panel62.setAlignmentX(Component.CENTER_ALIGNMENT);

	     panel62.setLayout(new FlowLayout());
	     JTextField F62 = new JTextField("Please rename for Alternative 2 results");
	     F62.setFont(font_1);
	     F62.setPreferredSize(new Dimension (frame.getWidth()-320,frame.getHeight()/3-100));
	     JButton B62 = new JButton("select file");
	     B62.setFont(font_1);

	     B62.setPreferredSize(new Dimension (160, frame.getHeight()/3-100));
	     B62.addActionListener(new ActionListener(){
			@Override
	 		public void actionPerformed(ActionEvent e) {
	 			JFileChooser chooser = new JFileChooser(cwd);
	 		    int filename = chooser.showOpenDialog(null);
	 			if (filename == JFileChooser.APPROVE_OPTION){
	 			File f = chooser.getSelectedFile();
	//BufferedReader br=new BufferedReader(new FileReader(f));
	 			
	 			try {
					Workbook 	wb_r1 = Workbook.getWorkbook(f);
					Sheet 	sheet_r = wb_r1.getSheet(0);
					Cell 	c1=sheet_r.getCell(1, 1);
					Cell 	c2=sheet_r.getCell(2, 1);
					Cell 	c3=sheet_r.getCell(3, 1);
					Cell	c4=sheet_r.getCell(4, 1);
					Cell	c5=sheet_r.getCell(5, 1);
					
					String s1 = c1.getContents(); 
					String s2 = c2.getContents(); 
					String s3 = c3.getContents(); 
					String s4 = c4.getContents(); 
					String s5 = c5.getContents(); 
					
					double r1 = Double.parseDouble(s1);
					double r2 = Double.parseDouble(s2);
					double r3 = Double.parseDouble(s3);
					double r4 = Double.parseDouble(s4);
					double r5 = Double.parseDouble(s5);
					
					b_result[0] =r1;
					b_result[1] =r2;
					b_result[2] =r3;
					b_result[3] =r4;
					b_result[4] =r5;
					
					
				     System.out.println(b_result[0]);	 
				     System.out.println(b_result[1]);	
				     System.out.println(b_result[2]);	
				     System.out.println(b_result[3]);	
				     System.out.println(b_result[4]);	

					
					
					
				} catch (BiffException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	 			
	 			
	 			
	}}});
	     
	     JPanel panel63 = new JPanel();
	     panel63.setAlignmentX(Component.CENTER_ALIGNMENT);

	     panel63.setLayout(new FlowLayout());
	     JTextField F63 = new JTextField("Please rename for Alternative 3 results");
	     F63.setPreferredSize(new Dimension (frame.getWidth()-320,frame.getHeight()/3-100));
	     JButton B63 = new JButton("select file");
	     F63.setFont(font_1);
	     B63.setFont(font_1);

	     B63.setPreferredSize(new Dimension (160,frame.getHeight()/3-100));
	     B63.addActionListener(new ActionListener(){
			@Override
	 		public void actionPerformed(ActionEvent e) {
	 			JFileChooser chooser = new JFileChooser(cwd);
	 		    int filename = chooser.showOpenDialog(null);
	 			if (filename == JFileChooser.APPROVE_OPTION){
	 			File f = chooser.getSelectedFile();
	//BufferedReader br=new BufferedReader(new FileReader(f));
	 			try {
					Workbook 	wb_r1 = Workbook.getWorkbook(f);
					Sheet 	sheet_r = wb_r1.getSheet(0);
					Cell 	c1=sheet_r.getCell(1, 1);
					Cell 	c2=sheet_r.getCell(2, 1);
					Cell 	c3=sheet_r.getCell(3, 1);
					Cell	c4=sheet_r.getCell(4, 1);
					Cell	c5=sheet_r.getCell(5, 1);
					
					String s1 = c1.getContents(); 
					String s2 = c2.getContents(); 
					String s3 = c3.getContents(); 
					String s4 = c4.getContents(); 
					String s5 = c5.getContents(); 
					
					double r1 = Double.parseDouble(s1);
					double r2 = Double.parseDouble(s2);
					double r3 = Double.parseDouble(s3);
					double r4 = Double.parseDouble(s4);
					double r5 = Double.parseDouble(s5);
					
					c_result[0] =r1;
					c_result[1] =r2;
					c_result[2] =r3;
					c_result[3] =r4;
					c_result[4] =r5;
					
					
				     System.out.println(c_result[0]);	 
				     System.out.println(c_result[1]);	
				     System.out.println(c_result[2]);	
				     System.out.println(c_result[3]);	
				     System.out.println(c_result[4]);	


					
					
					
				} catch (BiffException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	 			
	 			
	 			
	}}}); 
	     
	     panel61.add(F61,LEFT_ALIGNMENT);
	     panel61.add(B61,LEFT_ALIGNMENT);
	     panel62.add(F62,LEFT_ALIGNMENT);
	     panel62.add(B62,LEFT_ALIGNMENT);
	     panel63.add(F63,LEFT_ALIGNMENT);
	     panel63.add(B63,LEFT_ALIGNMENT);

	    panel6.add(panel61);
	    panel6.add(panel62);
	    panel6.add(panel63);


	    		 
	     
	     JButton Button_FC = new JButton("Compare Results");
	     Button_FC.setName("Compare Results");
	     Button_FC.setAlignmentX(Component.CENTER_ALIGNMENT);

	     panel6.add(Button_FC);
	     Button_FC.addActionListener(new ActionListener(){
				
		@Override
		public void actionPerformed(ActionEvent e) {
	    		 
				    //System.out.println(a_result[0]);
				    
				    DefaultCategoryDataset dataset_r = 	
				    	      new DefaultCategoryDataset( );
				      dataset_r.addValue( a_result[0], F61.getText(),"Construction"); 
				      dataset_r.addValue( b_result[0], F62.getText(),"Construction");
				      dataset_r.addValue( c_result[0], F63.getText(),"Construction");        	

				      dataset_r.addValue( a_result[1], F61.getText(),"Operation");      
				      dataset_r.addValue( b_result[1], F62.getText(),"Operation");
				      dataset_r.addValue( c_result[1], F63.getText(),"Operation"); 
				      
				      dataset_r.addValue( a_result[2], F61.getText(),"Maintenance");       	
				      dataset_r.addValue( b_result[2], F62.getText(),"Maintenance");
				      dataset_r.addValue( c_result[2], F63.getText(),"Maintenance"); 
 
				      dataset_r.addValue( a_result[3], F61.getText(),"Scrapping"); 	
				      dataset_r.addValue( b_result[3], F62.getText(),"Scrapping"); 
				      dataset_r.addValue( c_result[3], F63.getText(),"Scrapping"); 

				      dataset_r.addValue( a_result[4], F61.getText(),"Total");       	
				      dataset_r.addValue( b_result[4], F62.getText(),"Total"); 
				      dataset_r.addValue( c_result[4], F63.getText(),"Total"); 

				      
					
				  // Generate the graph	
				     JFreeChart chart_r = ChartFactory.createBarChart(	
				     "Comparison of Total Life Cycle Cost", // Title	
				     "Life Stages", // x-axis Label	
				     "Total Life Cycle Cost (Euro)", // y-axis Label	
				     dataset_r, // Dataset	
				     PlotOrientation.HORIZONTAL, // Plot Orientation	
				     true, // Show Legend	
				     true, // Use tooltips	
				     false // Configure chart to generate URLs?	
				  );	
				     ChartPanel CP_r = new ChartPanel(chart_r);	
				     	
				     panel_chart_r = new JPanel();	
				     panel_chart_r.add(CP_r);	
				     //create a window for piechart	
				     frame_r = new JFrame("Comparison of Total Life Cycle Cost");	
				     frame_r.setLayout(new BorderLayout());	
				     frame_r.add(panel_chart_r, BorderLayout.WEST);	
				     frame_r.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);	
				     frame_r.setSize(new Dimension(800, 600));	
				     frame_r.setResizable(true);	
				     panel_chart_r.setPreferredSize(new Dimension(frame_r.getWidth()-120, frame_r.getHeight()-150));	
				     frame_r.pack();	
				     frame_r.setVisible(true);	
				    
				    
			}});
	     
	 
	}
//create main panel	
	private JPanel createPanel(String string) {
	     
	     panel = new JPanel(true);
	     panel.setPreferredSize(new Dimension(frame.getWidth()-200,frame.getHeight()-200));
	     panel.setLayout(new BorderLayout(1,1));
	     JLabel filler = new JLabel(string);
	     filler.setFont(font_1);
	     filler.setHorizontalAlignment(JLabel.CENTER);
	     panel.add(filler);
	     return panel;
	  }
//	private JPanel createFCPanel(String string) {
//	     
//		panel_FC = createPanel(string);
//	 	panel_FC.setVisible(true);
//	 	panel_FC.setPreferredSize(new Dimension(1080, 200));
//	    panel_FC.setLayout(new GridBagLayout()); // added code
//	    GridBagConstraints c_sub = new GridBagConstraints();
//	    Dimension d = new Dimension(100,80);
//		return panel_FC;
//	    }
	
	private JPanel createNewPanel(String string) {
	     
		panel_n = createPanel(string);
	 	panel_n.setVisible(true);
	 	panel_n.setPreferredSize(new Dimension(1200, 125));
	    panel_n.setLayout(new GridBagLayout()); // added code
	    GridBagConstraints c_sub = new GridBagConstraints();
	    //Dimension d = new Dimension(100,80);

//create label
	    JLabel lbl = new JLabel();
	    lbl.setName(string);
	    
//	    lbl.setFont(new Font("Serif", Font.BOLD, 88));
//	    lbl.setFont(new Font("Serif", Font.PLAIN, 24));
	    //lbl.setPreferredSize(new Dimension(300,20));
	    c_sub.fill = GridBagConstraints.VERTICAL;
	    c_sub.weighty = 1;
	    c_sub.gridx = 0;
	    c_sub.gridy = 0;
	    panel_n.add(lbl,c_sub);
	    
	    area = new JTextArea(string);
//	    area.setFont(font_0);
	    
	    //area.setPreferredSize(new Dimension(800,300));
	    c_sub.fill = GridBagConstraints.VERTICAL;
	    c_sub.weighty = 1;
	    c_sub.gridx = 0;
	    c_sub.gridy = 1;
	    area.setEditable(false);
	    panel_n.add(area,c_sub);
	    
	    panel_n.setVisible(true); // added code
	    return panel_n; 
	  }
//create subpanels with lables, drop lists and text field 	
	private JPanel createsubPanel(String string) throws BiffException, IOException {

			panel_m = createPanel(string);
		 	panel_m.setVisible(true);
		 	panel_m.setPreferredSize(new Dimension(400, 300));
		    panel_m.setLayout(new GridBagLayout()); // added code
		    GridBagConstraints c_sub = new GridBagConstraints();
		    Dimension d = new Dimension(100,80);

//create label
		    JLabel lbl = new JLabel();
		    lbl.setName(string);
		    lbl.setFont(font_1);
		    c_sub.fill = GridBagConstraints.VERTICAL;
		    c_sub.weighty = 1;
		    c_sub.gridx = 0;
		    c_sub.gridy = 0;
		    panel_m.add(lbl,c_sub);

//create drop list for database selection
		    int position;
		    position = string.lastIndexOf(".");
		    file = new File (cwd+"/db/"+string+"/");//.substring(0, position)
		    //System.out.println(string);
		    file_group = file.listFiles();
		    choices = new String[file_group.length];
		    choices_NAME = new String[file_group.length];
		    int[] pos = new int[file_group.length];
		    for(int j=0;j<file_group.length;j++){
		    	
		    	//choices[j]=Integer.toString(j);
		    	choices_NAME[j]= file_group[j].getName();
		    	pos[j]=choices_NAME[j].lastIndexOf(".");// get rid of extention name e.g. "xls"
		    	if(pos[j] >0){
		    		choices[j]=choices_NAME[j].substring(0, pos[j]);
		    	}}

		    cb = new JComboBox<String>(choices);
		    cb.setName(string);
		    cb.setFont(font_1);
		    cb.setPreferredSize(new Dimension(400,40)); // added code
		    c_sub.fill = GridBagConstraints.VERTICAL;
		    c_sub.weighty = 1;
		    c_sub.gridx = 0;
		    c_sub.gridy = 1;
		    panel_m.add(cb,c_sub);

//create drop list for database selection
		    importData = new JButton("import from server");
		    importData.setName("import from server");
		    importData.setFont(font_1);
		    importData.setPreferredSize(new Dimension(400,20));
		    c_sub.fill = GridBagConstraints.VERTICAL;
		    c_sub.weighty = 1;
		    c_sub.gridx = 0;
		    c_sub.gridy = 2;
		    importData.addActionListener(createImportListener(0));
		    panel_m.add(importData,c_sub);
		    
		    
		    
//create field for user inputs		    		    
		    field = new JTextField("0");
		    field.setPreferredSize(new Dimension(400,40));
		    field.setFont(font_1);
		    c_sub.fill = GridBagConstraints.VERTICAL;
		    c_sub.weighty = 1;
		    c_sub.gridx = 0;
		    c_sub.gridy = 3;
		    panel_m.add(field,c_sub);

		    panel_m.setVisible(true); // added code
		    return panel_m; 
			
 }	
	
//use sub folders as database 
//	private File[] subFolderFile (String string) throws  IOException {
//		
//		File file_type = new File (cwd+"/db/"+string+ "/");
//		File[]file_group = file_type.listFiles();
//		return file_group;
//	}	
	

//create a search panel	
	private JPanel createSearchPanel(String string) throws BiffException, IOException {
		
		panel_s = new JPanel();
	 	panel_s.setVisible(true);
	 	panel_s.setPreferredSize(new Dimension(200, 100));
	    panel_s.setLayout(new GridBagLayout()); // added code
	    GridBagConstraints c_s = new GridBagConstraints();

	    //create label
	    JLabel lbl = new JLabel("Online database");
	    lbl.setName("Online database");
	    lbl.setFont(font_1);
	    lbl.setPreferredSize(new Dimension(400,60));
	    c_s.fill = GridBagConstraints.VERTICAL;
	    c_s.weighty = 0;
	    c_s.gridx = 0;
	    c_s.gridy = 0;
	    panel_s.add(lbl,c_s);	    
	  //create field for user inputs		    		    
	    field_search = new JTextField("");
	    field_search.setName("");
	    field_search.setPreferredSize(new Dimension(300,60));
	    field_search.setFont(font_1);
	    c_s.fill = GridBagConstraints.HORIZONTAL;
	    c_s.weighty = 0;
	    c_s.gridx = 0;
	    c_s.gridy = 1;
	    panel_s.add(field_search,c_s);
	    
	  //create a button for database search
	    button_search = new JButton("Search");
	    button_search.setName("Search");
	    button_search.setFont(font_1);
	    button_search.setPreferredSize(new Dimension(100,60));
	    c_s.fill = GridBagConstraints.HORIZONTAL;
	    c_s.weighty = 0;
	    c_s.gridx = 1;
	    c_s.gridy = 1;
	    button_search.addActionListener(createSearchListener(0));
	    panel_s.add(button_search,c_s);

//	//create a button for database download
//	    button_download = new JButton("Download");
//	    button_download.setName("Download");
//	    button_download.setFont(font_1);
//	    button_download.setPreferredSize(new Dimension(400,60));
//	    c_s.fill = GridBagConstraints.VERTICAL;
//	    c_s.weighty = 0;
//	    c_s.gridx = 0;
//	    c_s.gridy = 2;
//	    button_download.addActionListener(createDownloadListener(0));
//	    panel_s.add(button_download,c_s);	    
//	    panel_s.setVisible(true); // added code
		return panel_s;	
	}
//add icons	
	 @SuppressWarnings("unused")
	private ImageIcon createImageIcon(String string) {
		 string = cwd+"/pic/icon.png";
	     if(string == null)
	     {
	         System.out.println("the image "+string+" is not exist!");
	         return null;
	     }
	     else return new ImageIcon(string);
	  }
	 
	 
//add action ddbb
	 private ActionListener createActionListener(int j){
		 ActionListener ddbb=  new ActionListener() {
                                @Override
				public void actionPerformed(ActionEvent db) {
				
				selection_Number = Integer.toString(cb_m[j].getSelectedIndex());
				selection_Name = cb_m[j].getItemAt(cb_m[j].getSelectedIndex());
//				System.out.println(selection_Name);
//				System.out.println(cb_m[j].getName());
//Modify to be related to file_group.length	??
//				System.out.println(Double.parseDouble(selection));
				file = new File (cwd+"/db/"+cb_m[j].getName());
	    		 file_group =  file.listFiles();
					data = new Object[data_length][5];
					Object[] columnNames = {
							"Type", "Average", "Minimum","Maximum","Unit",
		                   };
				
	    		 for (i=0; i<file_group.length;i++){
	    			 //System.out.println(file_group[i].getName());

          
						try {
							wb[i] = Workbook.getWorkbook(file_group[i]);
						} catch (BiffException | IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						sheet[i]  = wb[i].getSheet(0);
	    				for(int j = 0 ; j<data_length ; j++){
	    					item0[i][j]=sheet[i].getCell(1, j);
	    					cell0[i][j]=sheet[i].getCell(2, j);
	    					cell1[i][j]=sheet[i].getCell(3, j);
	    					cell2[i][j]=sheet[i].getCell(4, j);
	    					unit0[i][j]=sheet[i].getCell(5, j);
	    					
	    					content0[i][j]=item0	[i][j].getContents();
	    					content1[i][j]=cell0	[i][j].getContents();
	    					content2[i][j]=cell1	[i][j].getContents();
	    					content3[i][j]=cell2	[i][j].getContents();
	    					content4[i][j]=unit0	[i][j].getContents();
	    				}

						
							for(int k=0;k<data_length;k++){
								if(Double.parseDouble(selection_Number)==i)
								{
								data0[k]	 = 	content0	[i][k];
								data1[k]	 = 	content1	[i][k];
								data2[k]	 = 	content2	[i][k];
								data3[k]	 = 	content3	[i][k];
								data4[k]	 = 	content4	[i][k];}
								
								if(Double.parseDouble(selection_Number)==1){
									//System.out.println("xxxx");
									}
								data[k][0] = data0[k];
								data[k][1] = data1[k];
								data[k][2] = data2[k];
								data[k][3] = data3[k];
								data[k][4] = data4[k];
								DTM = new DefaultTableModel(data, columnNames);
						}}
	    		 
//				for(int kk = 0;kk<file_group.length;kk++){};
		

	//add database here				     
			    
//				for(int k=0; k<data_length;k++){
//					data[k][0]=wb_num.getSheet(0).getCell(0,k).getContents();
//					data[k][2]=wb_num.getSheet(0).getCell(2,k).getContents();

//				};
			    
			     
				 JTable[] table_db1 = new JTable[activity_length];
				 table_db1 [j] = new JTable(DTM);
			     JScrollPane sp_db1 = new JScrollPane(table_db1[j]);
	     	     JLabel label_db1 = new JLabel("Database selection");
			     label_db1.setSize(new Dimension (20,20));
    
				 panel_db = new JPanel();
				// panel_db.setLayout(new BoxLayout(panel_db,BoxLayout.PAGE_AXIS));
			     panel_db.setName("database"+selection_Number);
			    // panel_db.add(label_db1);
			     label_db1.setAlignmentX(Component.CENTER_ALIGNMENT);
			     panel_db.add(sp_db1,BorderLayout.PAGE_START);
			     table_db1[j].setAlignmentX(Component.CENTER_ALIGNMENT);
		
			   //add button	  
			     JButton[] updateButton = new JButton[activity_length];
			     updateButton[j] = new JButton("Update");
			     //updateButton[k].setName("Update");
			     //updateButton[j].setAlignmentY(Component.LEFT_ALIGNMENT);
			     panel_db.add(updateButton[j],BorderLayout.LINE_START);

		//add action for clicking button	     
			     updateButton[j].addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						TableModel DTM2 = table_db1[j].getModel();
						for (int k = 0;k<data_length;k++){
							
							data_m1[k][j]=(String) DTM2.getValueAt(k, 1);
							data_m2[k][j]=(String) DTM2.getValueAt(k, 2);
							data_m3[k][j]=(String) DTM2.getValueAt(k, 3);
													
						}}});
			     
				   //add button	  
			     JButton[] saveButton = new JButton[activity_length];
			     saveButton[j] = new JButton("Save as");
			     //updateButton[k].setName("Update");
			     //saveButton[j].setAlignmentY(Component.RIGHT_ALIGNMENT);
			     panel_db.add(saveButton[j],BorderLayout.LINE_END);

		//add action for clicking button	     
			     saveButton[j].addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						TableModel DTM2 = table_db1[j].getModel();
						FileOutputStream fout;
						@SuppressWarnings("resource")
						HSSFWorkbook wb_save = new HSSFWorkbook();
						HSSFRow[] c0 = new HSSFRow[data_length];
						HSSFCell [] Column00 = new HSSFCell[data_length];
						HSSFCell [] Column0 = new HSSFCell[data_length];
						HSSFCell [] Column1 = new HSSFCell[data_length];
						HSSFCell [] Column2 = new HSSFCell[data_length];
						HSSFCell [] Column3 = new HSSFCell[data_length];
						HSSFCell [] Column4 = new HSSFCell[data_length];
						HSSFSheet sheet1 = wb_save.createSheet();
						
						for (int k = 0;k<data_length;k++){
							data_m0[k][j]=(String) DTM2.getValueAt(k, 0);
							data_m1[k][j]=(String) DTM2.getValueAt(k, 1);
							data_m2[k][j]=(String) DTM2.getValueAt(k, 2);
							data_m3[k][j]=(String) DTM2.getValueAt(k, 3);
							data_m4[k][j]=(String) DTM2.getValueAt(k, 4);
							
							
							
							
							c0[k]=sheet1.createRow(k);
							Column00 [k]=c0[k].createCell(0);
							Column0 [k]=c0[k].createCell(1);
							Column1 [k]=c0[k].createCell(2);
							Column2 [k]=c0[k].createCell(3);
							Column3 [k]=c0[k].createCell(4);
							Column4 [k]=c0[k].createCell(5);
							Column00 [k].setCellValue(k);
							Column0 [k].setCellValue(data_m0[k][j]);
							Column1 [k].setCellValue(data_m1[k][j]);
							Column2 [k].setCellValue(data_m2[k][j]);
							Column3 [k].setCellValue(data_m3[k][j]);
							Column4 [k].setCellValue(data_m4[k][j]);
							
							
							try {
								fout = new FileOutputStream(cwd+"/db/" + cb_m[j].getName()+"/"+dateFormat.format(d)+".xls");
								wb_save.write(fout);
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							//System.out.println(data_m0[k][j]);

							
						}
						JFrame F_warn0 = new JFrame("Saved");
						JPanel P_warn0 = new JPanel();
						P_warn0.setLayout(new BorderLayout());
						JLabel Fd_warn0 = new JLabel("Database saved in:" + "\n"+ cwd+"/db/" + cb_m[j].getName()+"/"+dateFormat.format(d)+".xls");
						F_warn0.setSize(620, 100);
						//Fd_warn0.setText("Database saved in:" + "\n"+ cwd+"/db/" + cb_m[j].getName()+"/"+dateFormat.format(d)+".xls");
						P_warn0.add(Fd_warn0,BorderLayout.CENTER);
						F_warn0.add(P_warn0);
						F_warn0.setVisible(true);
						F_warn0.setResizable(false);
					
					}});		     
			     
			     
			     
			     //add button 1 	     
//			     JButton importDataButton = new JButton("Import data from server");
//			     importDataButton.setName("importDataButton");
//			     importDataButton.setAlignmentY(Component.BOTTOM_ALIGNMENT);
//			     importDataButton.setPreferredSize(new Dimension(400,40));
//			     c.fill = GridBagConstraints.BASELINE;
//			     c.anchor = GridBagConstraints.LAST_LINE_START;
//			     c.insets = new Insets(30,85,0,0);  //top padding
//				    c.weightx = 0;
//				    c.gridx = 0;
//				    c.gridy = 3;
//			     panel0.add(importDataButton,c);
			     
//			     importDataButton.addActionListener(new ActionListener() {
//						
//							public void actionPerformed(ActionEvent e) {
//								QueryExamplesGeneral queryExamplesGeneral = new QueryExamplesGeneral();
//								queryExamplesGeneral.run();
//								
//								
//								for(int kk = 0;kk<file_group.length;kk++){
////								for(int kk = 0;kk<file_group.length;kk++){
//									if(Double.parseDouble(selection_Number)==kk)
//									{
//										for(int i=0;i<data_length;i++){
//											data0[i]	 = 	content0	[kk][i];
//											data1[i]	 = 	content1	[kk][i];
//											data2[i]	 = 	content2	[kk][i];
//											data3[i]	 = 	content3	[kk][i];
//											data4[i]	 = 	content4	[kk][i];
//									}}};
//						
//								Object[] columnNames = {
//										"Type", "Average", "Minimum","Maximum","Unit",
//					                   };
//					//add database here				     
//							    
//								data = new Object[data_length][5];
//								for(int k=0; k<data_length;k++){
////									data[k][0]=wb_num.getSheet(0).getCell(0,k).getContents();
////									data[k][2]=wb_num.getSheet(0).getCell(2,k).getContents();
//									data[k][0] = data0[k];
//									data[k][1] = data1[k];
//									data[k][2] = data2[k];
//									data[k][3] = data3[k];
//									data[k][4] = data4[k];
//								};
//							    
//							     DTM = new DefaultTableModel(data, columnNames);
//								 JTable[] table_db1 = new JTable[activity_length];
//								 table_db1 [j] = new JTable(DTM);
//							     JScrollPane sp_db1 = new JScrollPane(table_db1[j]);
//					     	     JLabel label_db1 = new JLabel("Database selection");
//							     label_db1.setSize(new Dimension (20,20));
//				    
//								 panel_db_1 = new JPanel();
//								 panel_db_1.setLayout(new BoxLayout(panel_db_1,BoxLayout.Y_AXIS));
//							     panel_db_1.setName("database"+selection_Number);
//							     panel_db_1.add(label_db1);
//							     label_db1.setAlignmentX(Component.CENTER_ALIGNMENT);
//							     panel_db_1.add(sp_db1);
//							     table_db1[j].setAlignmentX(Component.CENTER_ALIGNMENT);
//						
//							   //add button	  
//							     JButton[] updateButton = new JButton[activity_length];
//							     updateButton[j] = new JButton("Update");
//							     //updateButton[k].setName("Update");
//							     updateButton[j].setAlignmentX(Component.CENTER_ALIGNMENT);
//							     panel_db_1.add(updateButton[j]);
//						        
//							     //create a window for online db
//							     frame_db = new JFrame(selection_Name+" Database Details");//cb_m[j].getName()
//							     frame_db.setLayout(new BorderLayout());
//							     frame_db.add(panel_db_1,BorderLayout.WEST);
//							     frame_db.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//							     frame_db.setSize(new Dimension(820, 1020));
//							     frame_db.setResizable(true);
//							     panel_db_1.setPreferredSize(new Dimension(frame_db.getWidth()-350, frame_db.getHeight()-400));
//							     frame_db.pack();
//							     frame_db.setVisible(true);
//							     
//							     queryExamplesGeneral.shutdown();
//
//							}
//			     } );     
//			     importDataButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			   //  panel_db.add(importDataButton);
			     
			     
			     //create a window for db
			     frame_db = new JFrame(selection_Name+" - Database Details");//cb_m[j].getName()
			     frame_db.setLayout(new BorderLayout());
			     frame_db.add(panel_db,BorderLayout.WEST);
			     frame_db.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			     frame_db.setSize(new Dimension(820, 865));
			     frame_db.setResizable(true);
			     panel_db.setPreferredSize(new Dimension(frame_db.getWidth()-350, frame_db.getHeight()-400));
			     frame_db.pack();
			     
			     if(Double.parseDouble(selection_Number)==0){frame_db.setVisible(false);} 
			     else{frame_db.setVisible(true);}
			      
			
				}
				};
	return ddbb;
	 }
	 
	 
//add actionlistner to importdata button to open a window for downloading and maping data from server to local database
	 private ActionListener createImportListener(int j){
		 ActionListener importLinster=  new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent iL) {
            	// QueryExamplesGeneral QEG = new QueryExamplesGeneral();
//            	 try {
//					panel_il = createSearchPanel("");
//				} catch (BiffException | IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//            	 
//			     frame_il = new JFrame("SHIPLYS server");//cb_m[j].getName()
//			     frame_il.setLayout(new BorderLayout());
//			     frame_il.add(panel_il,BorderLayout.WEST);
//			     frame_il.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//			     frame_il.setSize(new Dimension(400,150));
//			     frame_il.setResizable(true);
//			     panel_il.setPreferredSize(new Dimension(400,150));
//			     frame_il.pack();
//			     frame_il.setVisible(true); 
            	//User selects SHIPLYS data base as Input Source
                 //--Establish connection to SHIPLYS database
                 connect();

                 //--Read from database
                 readFromDB();

                 //--Close connection at the end of the Application
                 shutdown();
            	 
             }
	 };	
	 return importLinster;
	 }

	
	protected void shutdown() {

        if (session != null) {
            session.close();
        }

        InformationDirectory.shutdown();
    		
	}

	protected void readFromDB() {

        //--TODO: read from database, convert data and save it localy
        //E.g.: read all engines and batties from data base like it is shown in example code (see following example and explanations in QueryExamplesGeneral)
        Query query = objectManager.newQuery(ProductComponent.class);
        query.setMode(Query.PERSISTENT);
        @SuppressWarnings("unchecked")
		List<POID<ProductComponent>> result = (List<POID<ProductComponent>>) query.execute();
        log.info("Found " + result.size() + " Product Components:");
        for (POID<ProductComponent> poid: result) {
            ProductComponent productComponent = objectManager.getObjectById(poid);

        
            //1. Read values of configurable properties contained in parameterSet of a specific product component (e.g. price etc.)
            ParameterSet parameterSet = productComponent.getParameters();

            List<KeyValue> configurableProperties = null;
            String[] CP_name = new String[54];
            String[] import_name = new String[54];

            String[] CP_value = new String[54];
            String[] import_value = new String[54];

            log.info("Configurable Properties of " + productComponent.getCommonName() + " are:");
            if (parameterSet != null) {
               configurableProperties = parameterSet.getParameters(); 
               CP_name= new String[configurableProperties.size()];
               CP_value= new String[configurableProperties.size()];
               import_name= new String[configurableProperties.size()];
               import_value= new String[configurableProperties.size()];
               for(int i=0;i<configurableProperties.size();i++){
            	   CP_name[i]=configurableProperties.get(i).getKey(); //parameter names
            	   CP_value[i]=configurableProperties.get(i).getValue(); //parameter values
            	   import_name[i]=CP_name[i].substring(CP_name[i].lastIndexOf(".")+1,CP_name[i].length());
            	  // System.out.println(CP_name[i].substring(CP_name[i].lastIndexOf(".")+1,CP_name[i].length()));
            	   //System.out.println(CP_value[i].substring(0, CP_value[i].indexOf(" ")));
            	   System.out.println(import_name[i]);
            	   System.out.println(CP_value[i]);
               }
               
            }
            	
            JTable table_id = new JTable();
            table_id  = new JTable(DTM);
            
				TableModel DTM2 = table_id.getModel();
				FileOutputStream fout;
				@SuppressWarnings("resource")
				HSSFWorkbook wb_save = new HSSFWorkbook();
				HSSFRow[] c0 = new HSSFRow[data_length];
				HSSFCell [] Column00 = new HSSFCell[data_length];
				HSSFCell [] Column0 = new HSSFCell[data_length];
				HSSFCell [] Column1 = new HSSFCell[data_length];

				HSSFSheet sheet1 = wb_save.createSheet();
				
				for (int k = 0;k<configurableProperties.size();k++){
					
					c0[k]=sheet1.createRow(k);
					Column00 [k]=c0[k].createCell(0);
					Column0 [k]=c0[k].createCell(1);
					Column1 [k]=c0[k].createCell(2);

					Column00 [k].setCellValue(k);
					Column0 [k].setCellValue(import_name[k]);
					Column1 [k].setCellValue(CP_value[k]);
		
					
					try {
						fout = new FileOutputStream(cwd+"/db/" + "import database"+"/"+productComponent.getCommonName()+"_"+dateFormat.format(d)+".xls");
						wb_save.write(fout);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					//System.out.println(data_m0[k][j]);

					
				}
				JFrame F_id = new JFrame("Saved");
				JPanel P_id = new JPanel();
				P_id.setLayout(new BorderLayout());
				JLabel Fd_id = new JLabel("Database saved in:" + "\n"+ cwd+"/db/" + "import database"+"/"+productComponent.getCommonName()+"_"+dateFormat.format(d)+".xls");
				F_id.setSize(620, 100);
				//Fd_warn0.setText("Database saved in:" + "\n"+ cwd+"/db/" + cb_m[j].getName()+"/"+dateFormat.format(d)+".xls");
				P_id.add(Fd_id,BorderLayout.CENTER);
				F_id.add(P_id);
				F_id.setVisible(true);
				F_id.setResizable(false);
        }
    		
	}

	protected void connect() {
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

		//add actionlistner to button to search data from server to local database
		 private ActionListener createSearchListener(int j){
			 ActionListener searchListener=  new ActionListener() {


				@Override
	             public void actionPerformed(ActionEvent iL) {
	            	//QueryExamplesGeneral QEG = new QueryExamplesGeneral();
	            	Object[] columnNames = {
							"Number", "Name","Details"
		                   };
	            	JFrame frame_odb = new JFrame("Database found");
	            	String[] result_name= new String[result_size];
	            	String[] test_name = new String[]{"aaa","bbb","ccc","ddd","eee"};
	            	Object[][] data_list = new Object[result_size][3];
	            	checkBox = new JCheckBox[result_size];
	            	JPanel checkPanel = new JPanel();
	            	checkPanel.scrollRectToVisible(getVisibleRect());
	            	JScrollPane checkScrollPanel = new JScrollPane(checkPanel);
	            	checkScrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	            	checkScrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	                
	                GridLayout cpLayout = new GridLayout(0,(int) Math.floor((result_size-1)/25+1));
	                cpLayout.setHgap(1);
	                cpLayout.setVgap(1);
	                cpLayout.minimumLayoutSize(frame_odb);
	            	checkPanel.setLayout(cpLayout);
	            	
	            	checkPanel.setPreferredSize(new Dimension(800,600));
	            	JLabel note = new JLabel("               *Please select and download database from SHIPLYS server."+"\n");
	            	note.setFont(new Font("Arial", Font.BOLD,14));
	            	if(test_name.length>result_name.length){
	            	for (i=0;i<result_size;i++){
	            		//Button[i].setSelected(true);
//	            		Button[i].setName("Database " + i);
	            		
	            		getCheckBox()[i] = new JCheckBox(i+1+". "+ test_name[i]);
	            		getCheckBox()[i].setName(i+1+". "+ test_name[i]);
	            		
	               		checkPanel.add(checkBox[i]);
	               		//checkBox[i].setAlignmentY(LEFT_ALIGNMENT);
//	            		result_name[i] = "test "+i;
//	            		data_list[i][0]=i;
//	            		data_list[i][1]=result_name[i];
	            	}}
	            	else{
	            		for (i=0;i<test_name.length;i++){
		            		//Button[i].setSelected(true);
//		            		Button[i].setName("Database " + i);
		            		
	            			getCheckBox()[i] = new JCheckBox(i+1+". "+ test_name[i]);
	            			getCheckBox()[i].setName(i+1+". "+ test_name[i]);
		            		
		            		checkPanel.add(checkBox[i]);
	            	}
	            		for (i=test_name.length;i<result_size;i++){
		            		//Button[i].setSelected(true);
//		            		Button[i].setName("Database " + i);
		            		
	            			getCheckBox()[i] = new JCheckBox(i+1+". empty");
	            			getCheckBox()[i].setName(i+1+". empty");
		            		
		               		checkPanel.add(checkBox[i]);
	            	}
	            	}
	            	DefaultTableModel DTM_list = new DefaultTableModel(data_list, columnNames);
	            	JTable table_odb = new JTable(DTM_list);
	            	JPanel panel_odb = new JPanel();
	            	
	            	JScrollPane sp_odb = new JScrollPane(table_odb);
					 panel_odb.setLayout(new BoxLayout(panel_odb,BoxLayout.Y_AXIS));
				     panel_odb.setName("online database");
				     
				     panel_odb.add(sp_odb);
				     
				     table_odb.setAlignmentX(Component.CENTER_ALIGNMENT);
				     
				 	//create a button for database download
				    button_download = new JButton("Download");
				    button_download.setName("Download");
				    button_download.setFont(font_0);
				    button_download.setPreferredSize(new Dimension(100,20));
				    button_download.addActionListener(createDownloadListener(0));

				     
				     
	             	 
	 			     //cb_m[j].getName()
	 			     frame_odb.setLayout(new BorderLayout());
	 			     //frame_odb.add(panel_odb,BorderLayout.EAST);
	 			     frame_odb.add(note,BorderLayout.PAGE_START);
	 			     frame_odb.add(checkScrollPanel,BorderLayout.CENTER);
	 			     frame_odb.add(button_download,BorderLayout.PAGE_END);
	 			     frame_odb.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	 			     //frame_odb.setSize(new Dimension(400,150));
	 			     frame_odb.setResizable(true);
	 			     //panel_odb.setPreferredSize(new Dimension(400,150));
	 			     frame_odb.pack();
	 			     frame_odb.setVisible(true); 
	            	//QEG.showDefaultEngineValues;
	            	 
	            	//System.out.println(field_search.getText());	            	 
	             }
		 };	
		 return searchListener;
		 }
		 
			
//		 private ActionListener createUpdateListener(int j){
//			 ActionListener updateLinster=  new ActionListener() {
//	             @Override
//	             public void actionPerformed(ActionEvent iL) {
//					panel_plot1.removeAll();
//					panel_plot1.add(tp_plot1);
//				}};
//			return updateLinster;
//				}
	//add actionlistner to button to download data from server to local database
		 private ActionListener createDownloadListener(int j){
			 ActionListener downloadLinster=  new ActionListener() {
	             @Override
	             public void actionPerformed(ActionEvent iL) {
	            	 for(i=0;i<result_size;i++){
	            			 if(getCheckBox()[i].isSelected()){
	            				 if(getCheckBox()[i].getName().endsWith("empty")){}
	            				 else{
	        	            		 System.out.println(getCheckBox()[i].getName());
	            				 }
	            	 }
	            	
	            	 
	            	 }
	             }
		 };	
		 return downloadLinster;
		 }	 
//main function
//main function to run to show GUI	 
	public static void main(String[] args) {
//          System.out.println(currentJavaJarFilePath);
//            
//            System.out.println(cwd);
            
            
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            
				try {
					createAndShowGUI();
                                        
				} catch (BiffException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
            
            }
		);
	}
	
//create frame
	private static void createAndShowGUI() throws BiffException, IOException {
	//Create and set up the window.
  
	//	JFrame.setDefaultLookAndFeelDecorated(true);
	frame = new JFrame("SHIPLYS");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(screenSize );
    
    //Create and set up the content pane.
    JComponent newContentPane = new Gui();
    frame.setLayout(new BorderLayout());
    frame.add(newContentPane,BorderLayout.WEST);
    frame.setExtendedState(JFrame.NORMAL);
    frame.setResizable(false);
    
    panel0.setPreferredSize(new Dimension (frame.getWidth()-50, frame.getHeight()-150));//new Dimension(frame.getWidth()-35, frame.getHeight()-120)

    
    
    //Add menu
	//Create the menu bar.
	menuBar = new JMenuBar();
	
	//Build the first menu.
	menu = new JMenu("File");
	menu.setMnemonic(KeyEvent.VK_F);
	menu.getAccessibleContext().setAccessibleDescription(
	        "Help related actions");
	menuBar.add(menu);
	
	//a group of JMenuItems

	//to db	
	
		menuItem = new JMenuItem("Database ", KeyEvent.VK_D);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_D, ActionEvent.CTRL_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription(
		        "Open database folder");
		AL_3=  new ActionListener() {

			public void actionPerformed(ActionEvent Menu) {
				Desktop d = Desktop.getDesktop();
				File folder = new File(cwd+"/db");
				
				    try {
						d.open(folder);;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}}};
		menuItem.addActionListener(AL_3);
		menu.add(menuItem);
	//to result
		menuItem = new JMenuItem("Reports ", KeyEvent.VK_R);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_R, ActionEvent.CTRL_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription(
		        "Open reports folder");
		AL_4=  new ActionListener() {

			public void actionPerformed(ActionEvent Menu) {
				Desktop d = Desktop.getDesktop();
				File folder = new File(cwd+"/reports");
				
				    try {
						d.open(folder);;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}}};
		menuItem.addActionListener(AL_4);
		menu.add(menuItem);		
		
	//exit
		menuItem = new JMenuItem("Close", KeyEvent.VK_W);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_W, ActionEvent.CTRL_MASK));
		menuItem.getAccessibleContext().setAccessibleDescription(
		        "Exit");
		AL_4=  new ActionListener() {

			public void actionPerformed(ActionEvent Menu) {
				frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
					}};
		menuItem.addActionListener(AL_4);
		menu.add(menuItem);	
		
	//Build the second menu.
	menu = new JMenu("Help");
	menu.setMnemonic(KeyEvent.VK_A);
	menu.getAccessibleContext().setAccessibleDescription(
	        "Help related actions");
	menuBar.add(menu);

	//a group of JMenuItems

//to website
	
	menuItem = new JMenuItem("About SHIPLYS", KeyEvent.VK_W);
	menuItem.setAccelerator(KeyStroke.getKeyStroke(
	        KeyEvent.VK_W, ActionEvent.CTRL_MASK));
	menuItem.getAccessibleContext().setAccessibleDescription(
	        "Open SHIPLYS website");
	AL_1=  new ActionListener() {

		public void actionPerformed(ActionEvent Menu) {
			Desktop d = Desktop.getDesktop();
			
			    try {
					d.browse(new URI("http://www.shiplys.com/"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}};
	menuItem.addActionListener(AL_1);
	menu.add(menuItem);

//to manual

	menuItem = new JMenuItem("Documentation", KeyEvent.VK_D);
	menuItem.setAccelerator(KeyStroke.getKeyStroke(
	        KeyEvent.VK_D, ActionEvent.CTRL_MASK));
	menuItem.getAccessibleContext().setAccessibleDescription(
	        "Open Manual");
	AL_2=  new ActionListener() {

		public void actionPerformed(ActionEvent Menu) {
			Desktop d = Desktop.getDesktop();
			File manual = new File(cwd+"/Help.pdf");
			
			    try {
					d.open(manual);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}}};
	menuItem.addActionListener(AL_2);
	menu.add(menuItem);

//to dir	
	
	menuItem = new JMenuItem("Directory ", KeyEvent.VK_I);
	menuItem.setAccelerator(KeyStroke.getKeyStroke(
	        KeyEvent.VK_I, ActionEvent.CTRL_MASK));
	menuItem.getAccessibleContext().setAccessibleDescription(
	        "Open installation folder");
	AL_3=  new ActionListener() {

		public void actionPerformed(ActionEvent Menu) {
			Desktop d = Desktop.getDesktop();
			File folder = new File(cwd);
			
			    try {
					d.open(folder);;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}}};
	menuItem.addActionListener(AL_3);
	//menu.add(menuItem);
	//
//	//Build the first menu.
//	menu = new JMenu("File");
//	menu.setMnemonic(KeyEvent.VK_A);
//	menu.getAccessibleContext().setAccessibleDescription(
//	        "File related actions");
//	menuBar.add(menu);
//
//	//Build the first menu.
//	menu = new JMenu("File");
//	menu.setMnemonic(KeyEvent.VK_A);
//	menu.getAccessibleContext().setAccessibleDescription(
//	        "File related actions");
//	menuBar.add(menu);
//
//	//a group of JMenuItems
//	menuItem = new JMenuItem("New", KeyEvent.VK_N);
//	menuItem.setAccelerator(KeyStroke.getKeyStroke(
//	        KeyEvent.VK_N, ActionEvent.ALT_MASK));
//	menuItem.getAccessibleContext().setAccessibleDescription(
//	        "Create a new case");
//	AL_1=  new ActionListener() {
//
//		public void actionPerformed(ActionEvent Menu) {
//			//System.out.println("test");
//			
//				frame.dispose();
//				javax.swing.SwingUtilities.invokeLater(new Runnable() {
//		            public void run() {
//		            
//						try {
//							createAndShowGUI();
//		                                        
//						} catch (BiffException | IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//		            
//				});
//				}};
//	            
//			 
//		
//	
//	menuItem.addActionListener(AL_1);
//	menu.add(menuItem);
//	
//	menuItem = new JMenuItem("Open", KeyEvent.VK_O);
//	menuItem.setAccelerator(KeyStroke.getKeyStroke(
//	        KeyEvent.VK_O, ActionEvent.ALT_MASK));
//	menuItem.getAccessibleContext().setAccessibleDescription(
//	        "Open a case");
//	AL_2=  new ActionListener() {
//
//		public void actionPerformed(ActionEvent Menu) {
//			//System.out.println("test");
//			JFileChooser chooser = new JFileChooser();
// 		    int filename = chooser.showOpenDialog(null);
// 			if (filename == JFileChooser.APPROVE_OPTION){
// 			File f = chooser.getSelectedFile();
// 			
// 			}
//		}
//	};
//	menuItem.addActionListener(AL_2);
//	menu.add(menuItem);
//	
//	menuItem = new JMenuItem("Save", KeyEvent.VK_S);
//	menuItem.setAccelerator(KeyStroke.getKeyStroke(
//	        KeyEvent.VK_S, ActionEvent.ALT_MASK));
//	menuItem.getAccessibleContext().setAccessibleDescription(
//	        "Save the case");
//	AL_3=  new ActionListener() {
//		public void actionPerformed(ActionEvent Menu) {
//			
//		}
//	};
//	menuItem.addActionListener(AL_3);
//	menu.add(menuItem);
//	
//	menuItem = new JMenuItem("Save as.", KeyEvent.VK_A);
//	menuItem.setAccelerator(KeyStroke.getKeyStroke(
//	        KeyEvent.VK_A, ActionEvent.ALT_MASK));
//	menuItem.getAccessibleContext().setAccessibleDescription(
//	        "Save the case to a folder");
//	menu.add(menuItem);
//	
//	menuItem = new JMenuItem("History", KeyEvent.VK_H);
//	menuItem.setAccelerator(KeyStroke.getKeyStroke(
//	        KeyEvent.VK_H, ActionEvent.ALT_MASK));
//	menuItem.getAccessibleContext().setAccessibleDescription(
//	        "History opened");
//	menu.add(menuItem);
//	
//	menuItem = new JMenuItem("Import", KeyEvent.VK_I);
//	menuItem.setAccelerator(KeyStroke.getKeyStroke(
//	        KeyEvent.VK_I, ActionEvent.ALT_MASK));
//	menuItem.getAccessibleContext().setAccessibleDescription(
//	        "Import a file");
//	menu.add(menuItem);
//	
//	menuItem = new JMenuItem("Export", KeyEvent.VK_E);
//	menuItem.setAccelerator(KeyStroke.getKeyStroke(
//	        KeyEvent.VK_E, ActionEvent.ALT_MASK));
//	menuItem.getAccessibleContext().setAccessibleDescription(
//	        "Export a file");
//	menu.add(menuItem);
//	
//	menuItem = new JMenuItem("Close", KeyEvent.VK_C);
//	menuItem.setAccelerator(KeyStroke.getKeyStroke(
//	        KeyEvent.VK_F4, ActionEvent.ALT_MASK));
//	menuItem.getAccessibleContext().setAccessibleDescription(
//	        "Close the software");
//	AL_3=  new ActionListener() {
//		public void actionPerformed(ActionEvent Menu) {
//			frame.dispose();;
//		}
//	};
//	menuItem.addActionListener(AL_3);
//	menu.add(menuItem);
//	
//	
//	
//
////	menuItem = new JMenuItem("Both text and icon",
////	                         new ImageIcon("images/middle.gif"));
////	menuItem.setMnemonic(KeyEvent.VK_B);
////	menu.add(menuItem);
////
////	menuItem = new JMenuItem(new ImageIcon("images/middle.gif"));
////	menuItem.setMnemonic(KeyEvent.VK_D);
////	menu.add(menuItem);
////
////	//a group of radio button menu items
////	menu.addSeparator();
////	ButtonGroup group = new ButtonGroup();
////	rbMenuItem = new JRadioButtonMenuItem("A radio button menu item");
////	rbMenuItem.setSelected(true);
////	rbMenuItem.setMnemonic(KeyEvent.VK_R);
////	group.add(rbMenuItem);
////	menu.add(rbMenuItem);
////
////	rbMenuItem = new JRadioButtonMenuItem("Another one");
////	rbMenuItem.setMnemonic(KeyEvent.VK_O);
////	group.add(rbMenuItem);
////	menu.add(rbMenuItem);
////
////	//a group of check box menu items
////	menu.addSeparator();
////	cbMenuItem = new JCheckBoxMenuItem("A check box menu item");
////	cbMenuItem.setMnemonic(KeyEvent.VK_C);
////	menu.add(cbMenuItem);
////
////	cbMenuItem = new JCheckBoxMenuItem("Another one");
////	cbMenuItem.setMnemonic(KeyEvent.VK_H);
////	menu.add(cbMenuItem);
////
////	//a submenu
////	menu.addSeparator();
////	submenu = new JMenu("A submenu");
////	submenu.setMnemonic(KeyEvent.VK_S);
////
////	menuItem = new JMenuItem("An item in the submenu");
////	menuItem.setAccelerator(KeyStroke.getKeyStroke(
////	        KeyEvent.VK_2, ActionEvent.ALT_MASK));
////	submenu.add(menuItem);
////
////	menuItem = new JMenuItem("Another item");
////	submenu.add(menuItem);
////	menu.add(submenu);
//
//	//Build second menu in the menu bar.
//	menu = new JMenu("Edit");
//	menu.setMnemonic(KeyEvent.VK_N);
//	menu.getAccessibleContext().setAccessibleDescription(
//	        "This menu does nothing");
//	menuBar.add(menu);
//	
//	//a group of JMenuItems
//		menuItem = new JMenuItem("Cut", KeyEvent.VK_X);
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(
//		        KeyEvent.VK_X, ActionEvent.CTRL_MASK));
//		menuItem.getAccessibleContext().setAccessibleDescription(
//		        "Create a new case");
//		menu.add(menuItem);
//		
//		menuItem = new JMenuItem("Copy", KeyEvent.VK_C);
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(
//		        KeyEvent.VK_C, ActionEvent.CTRL_MASK));
//		menuItem.getAccessibleContext().setAccessibleDescription(
//		        "Open a case");
//		menu.add(menuItem);
//		
//		menuItem = new JMenuItem("Paste", KeyEvent.VK_P);
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(
//		        KeyEvent.VK_P, ActionEvent.CTRL_MASK));
//		menuItem.getAccessibleContext().setAccessibleDescription(
//		        "Save the case");
//		menu.add(menuItem);
//		
//		menuItem = new JMenuItem("Delete", KeyEvent.VK_D);
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(
//		        KeyEvent.VK_D, ActionEvent.CTRL_MASK));
//		menuItem.getAccessibleContext().setAccessibleDescription(
//		        "Save the case to a folder");
//		menu.add(menuItem);
//		
//		menuItem = new JMenuItem("Preference", KeyEvent.VK_R);
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(
//		        KeyEvent.VK_R, ActionEvent.CTRL_MASK));
//		menuItem.getAccessibleContext().setAccessibleDescription(
//		        "History opened");
//		menu.add(menuItem);
//
//	menu = new JMenu("Help");
//	menu.setMnemonic(KeyEvent.VK_N);
//	menu.getAccessibleContext().setAccessibleDescription(
//	        "This menu does nothing");
//	menuBar.add(menu);
//	
//	menuItem = new JMenuItem("Tutorial", KeyEvent.VK_T);
//	menuItem.setAccelerator(KeyStroke.getKeyStroke(
//	        KeyEvent.VK_T, ActionEvent.ALT_MASK));
//	menuItem.getAccessibleContext().setAccessibleDescription(
//	        "Save the case to a folder");
//	menu.add(menuItem);
//	
//	menuItem = new JMenuItem("About", KeyEvent.VK_U);
//	menuItem.setAccelerator(KeyStroke.getKeyStroke(
//	        KeyEvent.VK_U, ActionEvent.ALT_MASK));
//	menuItem.getAccessibleContext().setAccessibleDescription(
//	        "History opened");
//	menu.add(menuItem);
//	
//
	frame.setJMenuBar(menuBar);
	frame.setIconImage(new ImageIcon("C:/Users/tjb12178/workspace/04012018/icon.png").getImage());
//    
    //Display the window.
    frame.pack();
    frame.setVisible(true);
    }
}