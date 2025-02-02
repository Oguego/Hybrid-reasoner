package edu.casetools.icase.mreasoner.core.elements.rules;

import java.util.Vector;

import edu.casetools.icase.mreasoner.core.elements.MStatus;
import edu.casetools.icase.mreasoner.core.elements.states.DefaultState;
import edu.casetools.icase.mreasoner.core.elements.states.State;
import edu.casetools.icase.mreasoner.core.elements.time.top.TemporalOperator;
import edu.casetools.icase.mreasoner.database.MDBInterface;

public class SameTimeRule {

	protected Vector<State>  		    antecedents;
	protected Vector<TemporalOperator>  temporalOperators;
	protected Vector<DefaultState>		internalStates;
	protected State 	     		    consequence; 
	protected boolean 				    firstTime;
	//public Vector<SameTimeRule> rules; //created the SamTimeRule Field from AbstarctMReasoner
	
	public SameTimeRule (){
		antecedents 	  = new Vector<State>();
		temporalOperators = new Vector<TemporalOperator>();
		internalStates 	  = new Vector<DefaultState>();
		firstTime = true;
		
	}
	
/**********************************************************************************************************************************************
 *  INCLUDED TO DETECT POTENTIAL CONFLICTS, THEN PASSES IT TO THE CONFLICT-ANALYZER (ARGUMENTATION SYSTEM)
 ********************************************************************************************************************************************/
//	
//	private Connection conn = null;
//	private Statement stmt = null;
//	public Vector<SameTimeRule> rules;
//	
//	   public void connect(){
//	   
//		try {
//			Class.forName("com.mysql.jdbc.Driver");
//			conn = DriverManager.getConnection( "jdbc:mysql://localhost:3306/sensors", "mysql", "123456");
//		    stmt = conn.createStatement();
//		} catch (ClassNotFoundException | SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//}
//		
//	   public void compareRules(){
//		   int numberConflicts = 0;
//		   List<String> displayList = new ArrayList<>();
//		   ConflictAnalyser conflictAnalyser;
//		   for(int i=0;i<rules.size();i++){
//			   for(int j=i+1;j<rules.size();j++){
//				   conflictAnalyser = new ConflictAnalyser(rules.get(i),rules.get(j));
//				   //			if(rules.get(i).isConflict(rules.get(j))){ //not recent comment
//				   if(conflictAnalyser.isConflict()){
//					   //Save conflicts
//					   this.connect();
//
//					   long millis=System.currentTimeMillis();  
//					   java.sql.Date todayDate=new java.sql.Date(millis);  				
//					   String temporalDate = todayDate.toString();	
//
//					   String consequence1, consequence2;
//					   consequence1 = rules.get(i).getConsequence().getName();
//					   if(!rules.get(i).getConsequence().getStatus()){
//						   consequence1 = "!" + consequence1;
//					   }
//
//					   consequence2 = rules.get(j).getConsequence().getName();
//					   if(!rules.get(j).getConsequence().getStatus()){
//						   consequence2 = "!" + consequence2;
//					   }				
//
//					   try{	
//
//						   String query = "INSERT INTO conflict (idconflict, rule1Consequence, rule2Consequence, occurrenceTime)"
//								   +" VALUES(null, '" + consequence1 + "', '" + consequence2 +  "', current_timestamp)";
//
//
//						   System.out.println(query);
//						   stmt.executeUpdate(query);
//					   }
//					   catch (SQLException e1) {
//						   // TODO Auto-generated catch block
//						   e1.printStackTrace();
//					   }
//					   numberConflicts ++;
//				   }
//
//				   displayList.add(conflictAnalyser.getDisplay());
//			   }
//		   }
//		   System.out.println("There are " + numberConflicts + " conflicts.");
//		   if(numberConflicts == 1){
//			   infoBox("There is only " + numberConflicts + " conflict.", "Warning: Conflict Detected" );
//		   }else{
//			   infoBox("There are " + numberConflicts + " conflicts.", "Warning: Conflict Detected" );
//		   }
//		   String allConflict = ""; 
//		   int conflictNo = 0;
//		   for(int i=0;i<displayList.size();i++){
//			   if(displayList.get(i)!=null){
//				   allConflict = allConflict + "Conflict" + " " + ++conflictNo + ":" + " " + displayList.get(i) + "\n";
//				   //infoBox(displayList.get(i),"Conflict Resolved");
//			   }			// Chimezie 
//		   }
//		   infoBox(allConflict, "Conflict Resolved");
//	   }
//	   
////	   public void compareRules(){
////		   int numberConflicts = 0;
////		   List<String> displayList = new ArrayList<>();
////		   ConflictAnalyser conflictAnalyser;
////		   List<ConflictAnalyser> conflicts = new ArrayList<>();
////		   for(int i=0;i<rules.size();i++){
////			   for(int j=i+1;j<rules.size();j++){
////				   conflictAnalyser = new ConflictAnalyser(rules.get(i),rules.get(j));
////				   //			if(rules.get(i).isConflict(rules.get(j))){ //not recent comment
////				   if(conflictAnalyser.isConflict() && isNotRepeated(conflicts, conflictAnalyser)){
////					   conflicts.add(conflictAnalyser);
////					   //Save conflicts
////					   this.connect();
////
////					   long millis=System.currentTimeMillis();  
////					   java.sql.Date todayDate=new java.sql.Date(millis);  				
////					   String temporalDate = todayDate.toString();	
////
////					   String consequence1, consequence2;
////					   consequence1 = rules.get(i).getConsequence().getName();
////					   if(!rules.get(i).getConsequence().getStatus()){
////						   consequence1 = "!" + consequence1;
////					   }
////
////					   consequence2 = rules.get(j).getConsequence().getName();
////					   if(!rules.get(j).getConsequence().getStatus()){
////						   consequence2 = "!" + consequence2;
////					   }				
////
////					   try{	
////
////						   String query = "INSERT INTO conflict (idconflict, rule1Consequence, rule2Consequence, occurrenceTime)"
////								   +" VALUES(null, '" + consequence1 + "', '" + consequence2 +  "', current_timestamp)";
////
////
////						   System.out.println(query);
////						   stmt.executeUpdate(query);
////					   }
////					   catch (SQLException e1) {
////						   // TODO Auto-generated catch block
////						   e1.printStackTrace();
////					   }
////					   numberConflicts ++;
////				   }
////
////				   displayList.add(conflictAnalyser.getDisplay());
////			   }
////		   }
////		   System.out.println("There are " + conflicts.size() + " conflicts.");
////		   if(conflicts.size() == 1){
////			   infoBox("There is only " + conflicts.size() + " conflict.", "Warning: Conflict Detected" );
////		   }else{
////			   infoBox("There are " + conflicts.size() + " conflicts.", "Warning: Conflict Detected" );
////		   }
////		   String allConflict = ""; 
////		   int conflictNo = 0;
////		   for(int i=0;i<displayList.size();i++){
////			   if(displayList.get(i)!=null){
////				   allConflict = allConflict + "Conflict" + " " + conflictNo + ":" + " " + displayList.get(i) + "\n";
////				   //infoBox(displayList.get(i),"Conflict Resolved");
////			   }			// Chimezie 
////		   }
////		   infoBox(allConflict, "Conflict Resolved");
////	   }
//
//		private boolean isNotRepeated(List<ConflictAnalyser> conflicts, ConflictAnalyser conflictAnalyser) {
//			// TODO Auto-generated method stub
//			for(int i=0;i<conflicts.size();i++){
//				if(conflicts.get(i).getRule1().consequence.equals(conflictAnalyser.getRule1().consequence) || conflicts.get(i).getRule1().consequence.equals(conflictAnalyser.getRule2().consequence)){
//					return false;
//				}
//			}
//			return true;
//		}
//
//		public void infoBox(String infoMessage, String titleBar){
//		JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
//}
//	
/************************************************************************************************************************************************
*  END OF CONFLICTS DETECTION
*************************************************************************************************************************************************/
	
	
	
	
	
	
	public Vector<TemporalOperator> getTemporalOperators() {
		return temporalOperators;
	}

	public Vector<DefaultState> getInternalStates() {
		return internalStates;
	}


	public void setInternalStates(Vector<DefaultState> internalStates) {
		this.internalStates = internalStates;
	}


	public void setTemporalOperators(
			Vector<TemporalOperator> pastBoundedAntecedents) {
		this.temporalOperators = pastBoundedAntecedents;
	}

	public void addAntecedent(State condition){
		this.antecedents.add(condition);
	}
	
	public void addInternalState(DefaultState condition){
		this.internalStates.add(condition);
	}
	
	public void addConsequence(State finalState){
		this.consequence = finalState;
	}
	
	public State getConsequence(){
		return this.consequence;
	}
	
	public Vector<State> getAntecedents(){
		return antecedents;
	}
	
	public void setAntecedents(Vector<State> antecedents){
		this.antecedents = antecedents;
	}
	
	
		

	public MStatus assertRule(MStatus systemStatus, MDBInterface database){
		boolean result = true;

		result = checkAntecedents(systemStatus);
		
		if(result) 
			result = checkPastBoundedAntecedents(systemStatus,database);
		if(!result) 
			firstTime = true;	
		if(result){
			systemStatus.occurs(consequence.getName(), consequence.getStatus(),false );
			//System.out.println("Testing Leonard" +consequence.getName());
			if(firstTime){
				printRuleChange();
				firstTime = false;				
			}
		}
		return systemStatus;	
	}

	protected boolean checkAntecedents(MStatus systemStatus){
		boolean result = true;
		boolean systemStatusValue;
		boolean antecedentsValue;
		
		for(int i=0;i<antecedents.size();i++){
			systemStatusValue = this.getStatus(systemStatus, antecedents.get(i).getName());
			antecedentsValue  = antecedents.get(i).getStatus();
			//System.out.println("Testing Chimezie Leonard" +antecedents.get(i).getName());
			if( systemStatusValue != antecedentsValue){
				result = false;
				
			}
		}
		
		for(int i=0;i<internalStates.size();i++){
			result = result && internalStates.get(i).assertState( systemStatus.getTime() );
			//System.out.println("Oguego **** " +result);
		}
		
		return result;
		
	}
	
	public boolean getStatus( MStatus systemStatus, String state){
		
		for(int i=0;i<systemStatus.getSystemStatus().size();i++){
			if( systemStatus.getSystemStatus().get(i).getName().equalsIgnoreCase(state) ) return systemStatus.getSystemStatus().get(i).getStatus();
		}
		System.out.println("WARNING: THE STATE: ( "+state+" ) COULD NOT BE FOUND IN THE SYSTEM WHEN ASSERTING RULES");
		return false; //Warning: This can cause future errors.
	}
	
	protected boolean checkPastBoundedAntecedents(MStatus systemStatus, MDBInterface database){
		boolean result = true;
		boolean systemStatusValue;
		
		for(int i=0;i<temporalOperators.size();i++){
			//THIS CHANGES THE COMMENTS FROM SCREEN
			//temporalOperators.get(i).print();//.printTOp(systemStatus.getTime());
			systemStatusValue = database.checkTemporalOperator(temporalOperators.get(i),systemStatus.getTime());
			if( !systemStatusValue ){
				result = false;
			}

		}
		return result;
	}
	
/**********************************************************************************************************************************************
 *  THESE ARE ONLY PRINTING FUNCTIONS
 ********************************************************************************************************************************************/

	public  void printRule(){
		System.out.print("( ");
		for(int i=0;i<antecedents.size();i++){ 
			if(i!=0) System.out.print(" , ");
			antecedents.get(i).print();
						
		}
		for(int i=0;i<internalStates.size();i++){ 
			if((antecedents.size()>0)&&(i!=0)) System.out.print(" , ");
			internalStates.get(i).print();
		}
		for(int i=0;i<temporalOperators.size();i++){
			if(((antecedents.size()>0)||(temporalOperators.size()>0))&&(i!=0)) System.out.print(" , ");
			temporalOperators.get(i).print();
		}
		
		System.out.print(" -> ");
		consequence.print();
		System.out.println(" )");
			
	}




	private void printRuleChange() {
		String negation = "";
		
		if(!this.consequence.getStatus()) negation = "!";
		System.out.print("\t "+negation+this.consequence.getName()+" TRIGGERED BY ( ");
		negation = "";
		for(int i=0;i<antecedents.size();i++){ 
			if(i!=0) System.out.print(" , ");
			antecedents.get(i).print();
		}
		for(int i=0;i<internalStates.size();i++){ 
			if((antecedents.size()>0)&&(i!=0)) System.out.print(" , ");
			internalStates.get(i).print();
		}
		for(int i=0;i<temporalOperators.size();i++){
			if(((antecedents.size()>0)||(temporalOperators.size()>0))&&(i!=0))  System.out.print(" , ");
			temporalOperators.get(i).print();
		}
		
		System.out.println(" )\n");
	}
	
}
