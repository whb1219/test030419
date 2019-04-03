package LCT;

public class Parameter_S {
//Properties
	String 	Transportation_fuel_type;
//	String 	Electricity_type;
	String 	Fuel_type;
//	String 	LO_type;
	String 	Cut_type;
	
	double	Number	=0;
	double	Weight	=0;
	double	S_Price	=0;
	double	Transportation_distance	=0;
	double	Transportation_fee	=0;
	double	Transportation_SFOC	=0;
	double	Transportation_fuel_price	=0;
	double	Electricity_price	=0;
	
	double 	Fuel_price  =0;
	double 	Cutting_power=0;
	double  Cutting_hours=0;
	double  Cutting_material=0;
	double 	Cutting_M_price=0;
	double	Life_span	=0;
	double	Interest	=0;
	double 	PV=0;
	double  Cost_S=0;
	
	double Spec_GWP_Trans=0;
	double Spec_AP_Trans=0;
	double Spec_EP_Trans=0;
	double Spec_POCP_Trans=0;
	double Spec_GWP_E=0;
	double Spec_AP_E=0;
	double Spec_EP_E=0;
	double Spec_POCP_E=0;
	double Spec_GWP_FO=0;
	double Spec_AP_FO=0;
	double Spec_EP_FO=0;
	double Spec_POCP_FO=0;
//	double Spec_GWP_LO;
//	double Spec_AP_LO;
//	double Spec_EP_LO;
//	double Spec_POCP_LO;

	
	double GWP	=0;
	double AP 	=0;
	double EP	=0;
	double POCP	=0;
	
	public void run(){	
		//Interim results
		double Transportation_fuel_quantity=Transportation_SFOC*Transportation_distance/100*Weight*Number*1000/1000;
		double Transportation_fuel_cost= Transportation_fuel_price*Transportation_fuel_quantity;
		double Electricity_consumption= Cutting_hours*Cutting_power;
		double Electricity_cost = Electricity_consumption/1000*Electricity_price;
		double Cutting_M_Cost = Cutting_material*Cutting_M_price;
		
		
		//Final result	
		//LCA
		GWP	= (Spec_GWP_Trans * Number*Weight*Transportation_distance/100 +Spec_GWP_E*Electricity_consumption/1000);
		AP 	= (Spec_AP_Trans * Number*Weight*Transportation_distance/100 +Spec_AP_E*Electricity_consumption/1000);
		EP	= (Spec_EP_Trans * Number*Weight*Transportation_distance/100 +Spec_EP_E*Electricity_consumption/1000);
		POCP= (Spec_POCP_Trans * Number*Weight*Transportation_distance/100 +Spec_POCP_E*Electricity_consumption/1000);		
				
		if (PV==0){
			Cost_S = Cutting_M_Cost+Electricity_cost+Transportation_fuel_cost+Number*S_Price;
			}
		else{
			Cost_S = (Cutting_M_Cost+Electricity_cost+Transportation_fuel_cost+Number*S_Price)/(Math.pow((1+Interest),Life_span));
			};
//			System.out.println("Scrapping: cost is : " + Cost_S +"Euro");
//			System.out.println("Scrapping: GWP is :" + GWP + "ton CO2e"); 
//			System.out.println("Scrapping: AP is :" + AP + "ton SO2e"); 
//			System.out.println("Scrapping: EP is :" + EP + "ton PO4e"); 
//			System.out.println("Scrapping: POCP is :" + POCP + "ton C2H6e"); 		
	}
}