package LCT;

public class Parameter_M {
	
	double 	Weight	=0;
//	double	DR_H	=0;
//	double  R_HC	=0;
//	double	DR_HC	=0;
//	double	DR_C	=0;
	double	P		=0;
	double	E_price	=1;
	
	
	double	E_Cutting	=0;
	double	E_Bending	=0;
	double	E_Welding	=0;
	double	E_Painting	=0;
	double	E_Blasting	=0;

	double	H_Cutting_Ma	=0;
	double	H_Bending_Ma	=0;
	double	H_Welding_Ma	=0;
	double	H_Painting_Ma	=0;
	
	double	H_Cutting_Hull	=0;
	double	H_Bending_Hull	=0;
	double	H_Welding_Hull	=0;
	double	H_Painting_Hull	=0;
	double	H_Blasting_Hull	=0;
	
	double	H_Cutting_O	=0;
	double	H_Bending_O	=0;
	double	H_Welding_O	=0;
	double	H_Painting_O	=0;
	
//	double	M_Welding	=0;
//	double	MP_Welding	=0;
//	double	M_Painting	=0;
//	double	MP_Painting	=0;
	double	Life_span	=0;
//	double	I1_Painting	=0;
//	double	I2_Painting	=0;
//	double	I1_Patching	=0;
//	double	I2_Patching	=0;
	
	double EM_Price = 0;
	double E_Power = 0;
	double BM_Price = 0;
	double B_Power = 0;
	double GEM_Price = 0;
	double GE_Power = 0;
	double Boiler_Price = 0;
	double Boiler_Power = 0;
	double other_Power = 0;
	double other_Price = 0;

	double E_Working_hours =0;
	double GE_Working_hours =0;
	double B_Working_hours =0;
	double Boiler_Working_hours =0;
	double other_Working_hours =0;
	
	double interval=1;
	double docking_fee = 0;

	double Spec_GWP_E=0;
	double Spec_AP_E=0;
	double Spec_EP_E=0;
	double Spec_POCP_E=0; 
	double GWP	=0;
	double AP 	=0;
	double EP	=0;
	double POCP	=0;
	
	double Labour_fee_cutting =0;
	double Labour_fee_bending =0;
	double Labour_fee_blasting =0;
	double Labour_fee_welding =0;
	double Labour_fee_coating =0;

	double Spare_cost = 0;
	double engine_price=0;
	 

	double Cost_M = 0;
	public void run(){
		
//		double MC_Painting = M_Painting*MP_Painting	double EC_Painting_Ma = H_Painting_Ma*E_Painting_Ma*E_price;
		double EC_Cutting_Ma = H_Cutting_Ma*E_Cutting*E_price;
		double EC_Bending_Ma = H_Bending_Ma*E_Bending*E_price;
		double EC_Welding_Ma = H_Welding_Ma*E_Welding*E_price;
		double EC_Painting_Ma = H_Painting_Ma*E_Painting*E_price;
		
		double EC_Painting_Hull = H_Painting_Hull*E_Painting*E_price;
		double EC_Cutting_Hull = H_Cutting_Hull*E_Cutting*E_price;
		double EC_Bending_Hull = H_Bending_Hull*E_Bending*E_price;
		double EC_Welding_Hull = H_Welding_Hull*E_Welding*E_price;
		double EC_Blasting_Hull = H_Blasting_Hull*E_Blasting*E_price;
		
		double EC_Painting_O = H_Painting_O*E_Painting*E_price;
		double EC_Cutting_O = H_Cutting_O*E_Cutting*E_price;
		double EC_Bending_O = H_Bending_O*E_Bending*E_price;
		double EC_Welding_O = H_Welding_O*E_Welding*E_price;

		double EC_Hull_Activity = EC_Painting_Hull+EC_Cutting_Hull +EC_Bending_Hull+EC_Welding_Hull+EC_Blasting_Hull;
		double EC_Ma_Activity = EC_Painting_Ma+EC_Cutting_Ma +EC_Bending_Ma+EC_Welding_Ma;
		double EC_O_Activity = EC_Painting_O+EC_Cutting_O +EC_Bending_O+EC_Welding_O;

		double Activity_cost = EC_Hull_Activity+EC_Ma_Activity+EC_O_Activity;
				
//		double MC_Patching = DR_H*I1_Patching*(1-R_HC)*Weight*P+I1_Patching*M_Welding*MP_Welding*DR_H;
//		double MC2_Patching = DR_HC*I2_Patching*R_HC*Weight*P+I2_Patching*M_Welding*MP_Welding*DR_HC;
//		double EC_Patching = DR_H*I1_Patching*(1-R_HC)*(E_Cutting+E_Welding)*E_price;
//		double EC2_Patching = DR_HC*I2_Patching*R_HC*(E_Cutting+E_Welding)*E_price;
					
		double EM_cost = EM_Price*E_Power*E_Working_hours;
		double BM_cost = BM_Price*B_Power*B_Working_hours;
		double GEM_cost = GEM_Price*GE_Power*GE_Working_hours;
		double Boiler_cost = Boiler_Price*Boiler_Power*Boiler_Working_hours;
		double other_cost = other_Price*other_Power*other_Working_hours;
		
		double Ma_Cost = EM_cost + BM_cost+ GEM_cost + Boiler_cost+ other_cost;
		
		double Labour_cost =	Labour_fee_cutting*(H_Cutting_Ma+H_Cutting_Hull+H_Cutting_O)+
								Labour_fee_bending*(H_Bending_Ma+H_Bending_Hull+H_Bending_O)+
								Labour_fee_blasting*H_Blasting_Hull+
								Labour_fee_welding*(H_Welding_Ma+H_Welding_Hull+H_Welding_O)+
								Labour_fee_coating*(H_Painting_Ma+H_Painting_Hull+H_Painting_O);
		
		double spare_cost = Spare_cost;
		Cost_M = Ma_Cost +Activity_cost*Life_span +docking_fee*Life_span/interval+Labour_cost*Life_span +spare_cost;
		
		GWP = Spec_GWP_E*Activity_cost/E_price;
		AP = Spec_AP_E*Activity_cost/E_price;
		EP = Spec_EP_E*Activity_cost/E_price;
		POCP = Spec_POCP_E*Activity_cost/E_price;
	}
	
		
}
