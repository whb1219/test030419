package LCT;

public class Parameter_O {
//Properties
	String 	Transportation_fuel_type;
	String 	Electricity_type;		
	String 	Fuel_type;
	String 	LO_type;
	
	double	Number		=0;
	double	SFOC		=0;
	double	Ohour		=0;
    double 	Engine_No	=0;
    double	Eload		=0;
	double	SLOC		=0;
	
	double	SFOC_2		=0;
	double	Ohour_2		=0;
    double 	Engine_No_2	=0;
    double	Eload_2		=0;
	double	SLOC_2		=0;
	
	double	SFOC_3		=0;
	double	Ohour_3		=0;
    double 	Engine_No_3	=0;
    double	Eload_3		=0;
	double	SLOC_3		=0;
	
	double	Fuel_price	=0;
	double	LO_price	=0;
	double	Transportation_distance	=0;
	double	Transportation_fee	=0;
	double	Transportation_SFOC	=0;
	double	Transportation_fuel_price	=0;
	double 	Electricity_quantity = 0;
	
	double	Life_span	=0;
	double	Interest	=0;
	double	PV=0;
	double  Cost_O=0;
	
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
	
	double Spec_GWP_LO=0;
	double Spec_AP_LO=0;
	double Spec_EP_LO=0;
	double Spec_POCP_LO=0;

	double GWP	=0;
	double AP 	=0;
	double EP	=0;
	double POCP	=0;
	
	double C_Factor=0;
	double S_Factor=0;
        double N_Factor=0;
	
	public void run(){
		//Interim results
		double	Fuel_consumption_1	= Engine_No*SFOC*Ohour*Eload/1000000;
		double	Fuel_consumption_2	= Engine_No_2*SFOC_2*Ohour_2*Eload_2/1000000;
		double	Fuel_consumption_3	= Engine_No_3*SFOC_3*Ohour_3*Eload_3/1000000;
		double Fuel_consumption = Fuel_consumption_1+Fuel_consumption_2+Fuel_consumption_3;
		
		//double	LO_consumption	= Engine_No*Ohour*Eload*SLOC/1000000;
		double	LO_consumption_1	= Engine_No*SLOC*Ohour*Eload/1000000;
		double	LO_consumption_2	= Engine_No_2*SLOC_2*Ohour_2*Eload_2/1000000;
		double	LO_consumption_3	= Engine_No_3*SLOC_3*Ohour_3*Eload_3/1000000;
		double LO_consumption = LO_consumption_1+LO_consumption_2+LO_consumption_3;
		
		
		double	Transportation_fuel_quantity = 	(Fuel_consumption+LO_consumption)*1000*Transportation_distance/100*Transportation_SFOC/1000;
				
		double	Fuel_cost 	= Fuel_consumption*Fuel_price;
		double	LO_cost	= LO_consumption*LO_price;
		double	Transportation_fuel_cost	= Transportation_fuel_quantity*Transportation_fuel_price;
		
		//Final result	
		//LCA
		GWP	= (Spec_GWP_Trans * (Fuel_consumption+LO_consumption)*Transportation_distance/100 + Spec_GWP_E*Electricity_quantity + Spec_GWP_FO*Fuel_consumption +C_Factor*Fuel_consumption + Spec_GWP_LO*LO_consumption)*Life_span;
		AP 	= (Spec_AP_Trans * (Fuel_consumption+LO_consumption)*Transportation_distance/100 + Spec_AP_E*Electricity_quantity + Spec_AP_FO*Fuel_consumption+1.2*S_Factor*Fuel_consumption+0.5*N_Factor*Fuel_consumption+ Spec_AP_LO*LO_consumption)*Life_span;
		EP	= (Spec_EP_Trans * (Fuel_consumption+LO_consumption)*Transportation_distance/100 + Spec_EP_E*Electricity_quantity+ Spec_EP_FO*Fuel_consumption+0.13*N_Factor*Fuel_consumption+ Spec_EP_LO*LO_consumption)*Life_span;
		POCP= (Spec_POCP_Trans * (Fuel_consumption+LO_consumption)*Transportation_distance/100 + Spec_POCP_E*Electricity_quantity+ Spec_POCP_FO*Fuel_consumption+0.048*S_Factor*Fuel_consumption+0.028*N_Factor*Fuel_consumption+ Spec_POCP_LO*LO_consumption)*Life_span;
		//LCCA	
		double  Cost_O_Annual = Fuel_cost+LO_cost+Transportation_fuel_cost;
	
		if (PV==0){
			Cost_O = Cost_O_Annual*Life_span;
			}
		else{
			Cost_O = Cost_O_Annual/(Math.pow((1+Interest),Life_span))*(Math.pow((1+Interest),Life_span)-1)/Interest;
			}
	
		
//                System.out.println("FC is : " + Fuel_consumption +"ton");
//                System.out.println("T" + Spec_GWP_Trans * (Fuel_consumption+LO_consumption)*Transportation_distance/100*30 + "ton");
//                System.out.println("P" + Spec_GWP_FO*Fuel_consumption*30 + "ton");
//                System.out.println("Em" +C_Factor*Fuel_consumption*30+ "ton");
//                System.out.println("Operation: cost is : " + Cost_O +"Euro");
//		System.out.println("Operation: GWP is :" + GWP + "ton CO2e"); 
//		System.out.println("Operation: AP is :" + AP + "ton SO2e"); 
//		System.out.println("Operation: EP is :" + EP + "ton PO4e"); 
//		System.out.println("Operation: POCP is :" + POCP + "ton C2H6e"); 	
	}
	
	
}
