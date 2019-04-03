package LCT;

public class Parameter_C_Material {
	

	//Properties
		String 	Transportation_fuel_type;
		String 	Electricity_type;
//		String 	Fuel_type;
//		String 	LO_type;
				
// Ship geometry
		double LOA=0;
		double LBP=0;
		double B=0;
		double D=1;
		double T=0;
		double Cb=0.5;
		double Vs=0;
		double Pw=0;
		double NJ=0;
		double NE=0;
		double DWT=0;
		double kA=0;
		double kB=0;
		double kC=0; //price of mateiral/machinery/material(accommodation)
		double P_Cutting=0; 
		double P_Bending=0; 
		double P_Welding=0; 
		double P_Blasting=0; 
		double P_Painting=0; //power P_Washing=0;
		double V_Cutting=1;
		double V_Bending=1; 
		double V_Welding=1;
		double V_Blasting=1; 
		double V_Painting=1; //speed V_Washing=0;
		double L_Cutting=0;
		double L_Bending=0;
		double  L_Welding=0;
		double  L_Blasting=0;
		double L_Painting=0;//length L_Washing=1
//		double E_Cutting=1,E_Bending=1, E_Welding=1, E_Painting=1 ;//E_Blasting=1, E_Washing=1
//		double EC_Cutting=1,EC_Bending=1, EC_Welding=1, EC_Painting=0; //EC_Blasting=1, EC_Washing=0;		
		double SMC_Cutting=0;
		double SMC_Bending=0;
		double  SMC_Welding=0;
		double  SMC_Blasting=0;
		double  SMC_Painting=0; // SMC_Washing=0;
		double TMC_Cutting=0;
		double TMC_Bending=0;
		double  TMC_Welding=0;
		double  TMC_Blasting=0;
		double  TMC_Painting=0; //TMC_Washing=0;
		double MP_Cutting=0;
		double MP_Bending=0;
		double  MP_Welding=0;
		double  MP_Blasting=0;
		double MP_Painting=0; // MP_Washing=0;
		double E_price=0;
		
		
		double	Number	=0;
		double	Weight	=0;
		double	Price	=0;
		double	Transportation_distance	=0;
		double	Transportation_price	=0;
		double	Transportation_SFOC	=0;
		double	Transportation_fuel_price	=0;
		double	Processing_energy_price	=0;
		
		double Spec_GWP_Trans=0;
		double Spec_AP_Trans=0;
		double Spec_EP_Trans=0;
		double Spec_POCP_Trans=0;
		double Spec_GWP_E=0;
		double Spec_AP_E=0;
		double Spec_EP_E=0;
		double Spec_POCP_E=0;
//		double Spec_GWP_FO;
//		double Spec_AP_FO;
//		double Spec_EP_FO;
//		double Spec_POCP_FO;
//		double Spec_GWP_LO;
//		double Spec_AP_LO;
//		double Spec_EP_LO;
//		double Spec_POCP_LO;	
		
		double 	Cost_C_Material=0;
		double GWP	=0;
		double AP 	=0;
		double EP	=0;
		double POCP	=0;
		
		double W1=0; double W2=0; double W3=0; double W4=0; double W5=0; double W6=0; double W9=0; 
		double LW=0; double WA=0; double WB=0; double WC=0; 
		double CA=0; double CB=0; double CC=0; double  CD=0;
		double W12=0; double W13=0; double W14=0; double W15=0;//W11=0; double 
		double Processing_energy_cost=0;
		double Processing_material_cost=0;
		double Processing_energy=0;
		double Transportation_fuel_quantity=0;
		double Transportation_fuel_cost=0;
		
		double E_Cutting_hull =0;
		double E_Bending_hull =0;
		double E_Welding_hull =0;
		double E_Painting_hull =0;
		double E_Blasting_hull =0;
		
//		double E_Cutting_O = 0;
//		double E_Bending_O = 0;
//		double E_Welding_O = 0;
//		double E_Painting_O = 0;
		
		double	H_Cutting_Hull	=0;
		double	H_Bending_Hull	=0;
		double	H_Welding_Hull	=0;
		double	H_Painting_Hull	=0;
		double	H_Blasting_Hull	=0;
		
//		double	H_Cutting_O	=0;
//		double	H_Bending_O	=0;
//		double	H_Welding_O	=0;
//		double	H_Painting_O=0;
		
		double Labour_fee_cutting =0;
		double Labour_fee_bending =0;
		double Labour_fee_blasting =0;
		double Labour_fee_welding =0;
		double Labour_fee_painting =0;
		
		double L_Cutting_hull=0;
		double L_Bending_hull=0;
		double L_Blasting_hull=0;
		double L_Welding_hull=0;
		double L_Painting_hull=0;
		
//		double L_Cutting_O=0;
//		double L_Bending_O=0;
//		double L_Welding_O=0;
//		double L_Painting_O=0;

		double Labour_cost=0;
		double W11 = 0.00072*Math.pow(Cb,0.333333)*Math.pow(LOA,2.5)*T*B/D;
		
		public void run(){
			//Interim results
			if(W1==0) {
			 W12 = 0.011*LOA*B*D;
			 W13 = 0.0198*LOA*B*D;
			 W14 = 0.0388*LOA*B*NJ;
			 W15 = 0.00275*LOA*B*D;
			 W1 = W11+W12+W13+W14+W15;}
			if(W1!=0) {
				
			}
			System.out.println(W1);
			double NCo = Math.pow(LOA*B*T,2/3)+2*B*(D-T+(NJ-1)*2.8);
			double NC = NCo+0.1*(D-T+0.588*(NJ-1))*LOA;
			double W21 = 0.0475*NC;
			double W22 = 0.0216*NC;
			double W23 = 0.0001185*LOA*T*Math.sqrt(Vs);
			double W24 = (0.00883+0.002029)*LOA*B*D;
			double W25 = 0.002325*LOA*B*D;
			double W26 = 0.116*LOA*B;
			double W27 = 0.871*NE;
			double W29 = 0.0000845*LOA*B*D;
			 W2 = W21+W22+W23+W24+W25+W26+W27+W29;
			
			double M1 = LOA*B*D/1000;
			double M2 = 0.22*LOA*B*NJ/100;
			double W31 = 1.0182*M2;
			double W32 = 0.3854*M1;
			double W34 = 0.3504*M2;
			double W36 = 0.8030*M1;
			double W38 = 2.3772*M1;			
			double W39 = 5.782*M1;
			 W3 = W31+W32+W34+W36+W38+W39;
			
			double W41 = 0.00017*Pw;
			double W42 = 0.0657*Pw;
			double W43 = 0.017066*Pw;
			double W44 = 0.000666*Pw;
			double W46 = 0.0002666*Pw;
			double W48 = 0.0002666*Pw;
			double W49 = 0.0105*Pw;
			 W4 = W41+W42+W43+W44+W46+W48+W49;
			
			double W51 = 1.0761*M1;
			double W52 = 1.2528*M1;
			double W53 = 0.8031*M1;
			double W54 = 1.4134*M1;
			double W58 = 0.8031*M1;
			double W59 = 0.0964*M1;
			 W5 = W51+W52+W53+W54+W58+W59;
			
			 W6 = 3.276*M1;
			double PI = 0.759*M1;
			double PTT = 0.85*M1;
			double W91 =5*M1;
			 W9 = PI+PTT+W91;
			
			LW = W1+W2+W3+W4+W5+W6+W9;
			 WA=W1; //steel
			 WB=W2+W5+W6+W9;//outfitting
			 WC=W4;//machinery
			
			 CA = kA*W1;
			 CB = kB*WB;
			 CC = 850000*Math.pow(Pw/1000, 0.7);
			 CD =kC*W3;
			Transportation_fuel_quantity=Transportation_SFOC*Transportation_distance/100*LW*1000/1000;
			Transportation_fuel_cost= Transportation_fuel_price*Transportation_fuel_quantity;
	
			//when only working hours are available ================================		

			
//			if(H_Cutting_Hull == 0) {
//			E_Cutting_hull = (P_Cutting * L_Cutting) *(1/V_Cutting);
//			L_Cutting_hull = Labour_fee_cutting*L_Cutting/V_Cutting;}
//			if (H_Cutting_Hull != 0){E_Cutting_hull =P_Cutting*H_Cutting_Hull;
//			L_Cutting_hull = Labour_fee_cutting*H_Cutting_Hull;}
//			
//			if(H_Bending_Hull == 0) {
//			E_Bending_hull = P_Bending/V_Bending*L_Bending;
//			L_Bending_hull = Labour_fee_bending/V_Bending*L_Bending;}
//			else {E_Bending_hull =P_Bending*H_Bending_Hull;
//			L_Bending_hull = Labour_fee_bending*H_Bending_Hull;}
//			
//			if(H_Blasting_Hull == 0) {
//			E_Blasting_hull = P_Blasting/V_Blasting*L_Blasting;
//			L_Blasting_hull = Labour_fee_blasting/V_Blasting*L_Blasting;}
//			else {E_Blasting_hull =P_Blasting*H_Blasting_Hull;
//			L_Blasting_hull = Labour_fee_blasting*H_Blasting_Hull;}
//			
//			if(H_Welding_Hull == 0) {
//			E_Welding_hull = P_Welding/V_Welding*L_Welding;
//			L_Welding_hull = Labour_fee_welding/V_Welding*L_Welding;}
//			else {E_Welding_hull =P_Welding*H_Welding_Hull;
//			L_Welding_hull = Labour_fee_welding*H_Welding_Hull;}
//			
//			if(H_Painting_Hull == 0) {
//			E_Painting_hull = P_Painting/V_Painting*L_Painting;
//			L_Painting_hull = Labour_fee_painting/V_Painting*L_Painting;}
//			else {E_Painting_hull =P_Painting*H_Painting_Hull;
//			L_Painting_hull = Labour_fee_painting*H_Painting_Hull;}

			H_Cutting_Hull = W1*20;
			H_Bending_Hull = W1*30;
			H_Welding_Hull = W1*50;
			H_Blasting_Hull = W1*4.5;
			H_Painting_Hull = W1*(4.5+0.6);
			
			
			
			
			E_Cutting_hull =P_Cutting*H_Cutting_Hull;
//			System.out.println("E"+E_Cutting_hull);
			E_Bending_hull =P_Bending*H_Bending_Hull;
			E_Blasting_hull =P_Blasting*H_Blasting_Hull;
			E_Welding_hull =P_Welding*H_Welding_Hull;
			E_Painting_hull =P_Painting*H_Painting_Hull;
			
//			System.out.println("E2"+E_Bending_hull);
//			System.out.println("E3"+E_Blasting_hull);
//			System.out.println("E4"+E_Welding_hull);
//			System.out.println("E41"+H_Welding_Hull);System.out.println("E42"+P_Welding);
//			System.out.println("E5"+E_Painting_hull);
			
			
			L_Cutting_hull = Labour_fee_cutting*H_Cutting_Hull;
			L_Bending_hull = Labour_fee_bending*H_Bending_Hull;
			L_Blasting_hull = Labour_fee_blasting*H_Blasting_Hull;
			L_Welding_hull = Labour_fee_welding*H_Welding_Hull;
			L_Painting_hull = Labour_fee_painting*H_Painting_Hull;
					//================================		
//			if(H_Cutting_O == 0) {
//			E_Cutting_O = P_Cutting/V_Cutting*L_Cutting;
//			L_Cutting_O = Labour_fee_cutting/V_Cutting*L_Cutting;}
//			else {E_Cutting_O =P_Cutting*H_Cutting_O;
//			L_Cutting_O = Labour_fee_cutting*H_Cutting_O;}
//			
//			if(H_Bending_O == 0) {
//			E_Bending_O = P_Bending/V_Bending*L_Bending;
//			L_Bending_O = Labour_fee_bending/V_Bending*L_Bending;}
//			else {E_Bending_O =P_Bending*H_Bending_O;
//			L_Bending_O = Labour_fee_bending*H_Bending_O;}
//			
//			if(H_Welding_O == 0) {
//			E_Welding_O = P_Welding/V_Welding*L_Welding;
//			L_Welding_O = Labour_fee_welding/V_Welding*L_Welding;}
//			else {E_Welding_O =P_Welding*H_Welding_O;
//			L_Welding_O = Labour_fee_welding*H_Welding_O;}
//			
//			if(H_Painting_O == 0) {
//			E_Painting_O = P_Painting/V_Painting*L_Painting;
//			L_Painting_O = Labour_fee_painting/V_Painting*L_Painting;}
//			else {E_Painting_O =P_Painting*H_Painting_O;
//			L_Painting_O = Labour_fee_painting*H_Painting_O;}	
			
			

			//E_Blasting = P_Blasting/V_Blasting*L_Blasting;
			//E_Washing = P_Washing/V_Washing*L_Washing;
			
//			EC_Cutting = E_Cutting*E_price;
//			EC_Bending = E_Bending*E_price;
//			EC_Welding = E_Welding*E_price;
//			EC_Painting = E_Painting*E_price;
			//EC_Blasting = E_Blasting*E_price;
			//EC_Washing = E_Washing*E_price;
			
			TMC_Cutting = L_Cutting*SMC_Cutting*MP_Cutting;
			TMC_Bending = L_Bending*SMC_Bending*MP_Bending;
			TMC_Welding = L_Welding*SMC_Welding*MP_Welding;
			TMC_Painting = L_Painting*SMC_Painting*MP_Painting;
			TMC_Blasting = L_Blasting*SMC_Blasting*MP_Blasting;
			//TMC_Washing = L_Washing*SMC_Washing*MP_Washing;
			

			Processing_energy = E_Cutting_hull+E_Bending_hull+E_Welding_hull+E_Painting_hull+E_Blasting_hull;
//			System.out.println("PE"+Processing_energy);
			//+E_Cutting_O+E_Bending_O+E_Welding_O+E_Painting_O;//+E_Washing;
			Processing_energy_cost=E_price*Processing_energy;
			Processing_material_cost = TMC_Cutting+TMC_Bending+TMC_Welding+TMC_Painting+TMC_Blasting;//+TMC_Washing;
			
			Labour_cost = L_Cutting_hull+L_Bending_hull+L_Blasting_hull+L_Welding_hull+L_Painting_hull;
					//+L_Cutting_O+L_Bending_O+L_Welding_O+L_Painting_O;
			//System.out.println(Processing_energy);
			//Final result	
			//LCA
			GWP	= (Spec_GWP_Trans * Transportation_fuel_quantity+Spec_GWP_E*Processing_energy)/1000;//
			AP 	= (Spec_AP_Trans * Transportation_fuel_quantity + Spec_AP_E*Processing_energy)/1000;//
			EP	= (Spec_EP_Trans * Transportation_fuel_quantity + Spec_EP_E*Processing_energy)/1000;//
			POCP= (Spec_POCP_Trans * Transportation_fuel_quantity + Spec_POCP_E*Processing_energy)/1000;//
			
			
			//LCCA
			Cost_C_Material = CA+CB+CD+
					Transportation_fuel_cost+Transportation_distance*Transportation_price+
					Processing_energy_cost+Processing_material_cost+
					Labour_cost;
//			System.out.println("C1"+CA);
//			System.out.println("C2"+CB);
//			System.out.println("C3"+CD);
//			System.out.println("C4"+Transportation_fuel_cost);
//			System.out.println("C5"+Transportation_distance);
//			System.out.println("C6"+Transportation_price);
//
//			System.out.println("C7"+Processing_energy_cost);
//			System.out.println("C8"+Processing_material_cost);
//			System.out.println("C9"+Labour_cost);

//		System.out.println("1>"+E_Cutting);
//		System.out.println("2>"+L_Bending);
//		System.out.println("3>"+L_Welding);
//		System.out.println("4>"+L_Painting);


//			System.out.println("Construction material: cost is : " + Cost_C_Material +"Euro");
//			System.out.println("Construction material: GWP is :" + GWP + "ton CO2e"); 
//			System.out.println("Construction material: AP is :" + AP + "ton SO2e"); 
//			System.out.println("Construction material: EP is :" + EP + "ton PO4e"); 
//			System.out.println("Construction material: POCP is :" + POCP + "ton C2H6e"); 	
			}
		
			
	

}
