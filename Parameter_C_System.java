package LCT;

public class Parameter_C_System {
//Properties
	String 	Engine_type;
	String 	Transportation_fuel_type;
	String 	Transportation_type;
	String 	Electricity_type;
//	String 	Fuel_type;
//	String 	LO_type;
	
	double	Number	=0;
	double	Weight	=0;
	double	Price	=0;
	double	Transportation_distance	=0;
	double	Transportation_fee	=0;
	double	Transportation_SFOC	=0;//(kg/100km/kg cargo)
	double	Transportation_fuel_price	=0;
	double	Installation_energy_price	=0;
	
	double Spec_GWP_Trans=0;
	double Spec_AP_Trans=0;
	double Spec_EP_Trans=0;
	double Spec_POCP_Trans=0;
	double Spec_GWP_E=0;
	double Spec_AP_E=0;
	double Spec_EP_E=0;
	double Spec_POCP_E=0;
//	double Spec_GWP_FO;
//	double Spec_AP_FO;
//	double Spec_EP_FO;
//	double Spec_POCP_FO;
//	double Spec_GWP_LO;
//	double Spec_AP_LO;
//	double Spec_EP_LO;
//	double Spec_POCP_LO;	
	
	double 	Cost_C_System=0;
	double GWP	=0;
	double AP 	=0;
	double EP	=0;
	double POCP	=0;
	
	int H=54;
	int i=0;
	double F[]={0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0
       };
	double C[]={0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0
       };//new double[H];
	double M[]={0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0,0,
        0,0,0,0,0,0,0
       };
	double RPN[]=new double[H];
	double RA=0;
	double CoTL=0;
	double CRA=0;
	
		
	public void run(){
		//Interim results
		
		double Transportation_fuel_quantity=Transportation_SFOC*Transportation_distance/100*Weight*Number*1000/1000;//ton
		double Transportation_fuel_cost= Transportation_fuel_price*Transportation_fuel_quantity;
		double Installation_energy = Number*Weight*1000; //every ton of machinery need 1000kWh of installation energy 
		double Installation_energy_cost=Installation_energy_price*Installation_energy;
		
		//Final result	
		//LCA
		GWP	= (Spec_GWP_Trans * Number*Weight + Spec_GWP_E*Installation_energy);
		AP 	= (Spec_AP_Trans * Number*Weight + Spec_AP_E*Installation_energy);
		EP	= (Spec_EP_Trans * Number*Weight + Spec_EP_E*Installation_energy);
		POCP= (Spec_POCP_Trans * Number*Weight + Spec_POCP_E*Installation_energy);
		
		//LCCA
		Cost_C_System = Number*Price+Transportation_fuel_cost+Installation_energy_cost;
		
		//RA
		for(i=0;i<H;i++){
		RPN[i]=F[i]*C[i]*M[i];
		RA= RA + RPN[i];}
		CRA = RA/1000*CoTL;
		
//		System.out.println("Construction system: cost is : " + Cost_C_System +"Euro");
//		System.out.println("Construction system: GWP is :" + GWP + "ton CO2e"); 
//		System.out.println("Construction system: AP is :" + AP + "ton SO2e"); 
//		System.out.println("Construction system: EP is :" + EP + "ton PO4e"); 
//		System.out.println("Construction system: POCP is :" + POCP + "ton C2H6e"); 	
//		System.out.println("Construction system: RA is :" + RA);
//		System.out.println("Construction system: Risk cost is :" + CRA);
		}
	
		
}
