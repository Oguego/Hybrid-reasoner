package edu.casetools.icase.argumentation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JOptionPane;

import edu.casetools.icase.mreasoner.core.elements.MRules;
import edu.casetools.icase.mreasoner.core.elements.rules.SameTimeRule;
import edu.casetools.icase.mreasoner.core.elements.states.State;
import QueryData.OccurrenceDetector;


public class ConflictAnalyser {
	private SameTimeRule rule1;
	private SameTimeRule rule2;
	private SameTimeRule winner;
	private OccurrenceDetector sugarResult;
	
	private String rule1Begin, rule1End, rule2Begin, rule2End, conflictTimeBegin, conflictTimeEnd, display;
	private List<Preference> preferences;
	
	private MRules systemRules;
	protected Vector<State>    antecedents;
	private Connection conn = null;
	private Statement stmt = null;
	
	private Connection connPG = null;
	
	
	public ConflictAnalyser() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Class.forName("org.postgresql.Driver"); //added Postgres
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/preferences", "root", "root");
			//connPG = DriverManager.getConnection( "jdbc:postgresql://localhost:5432/sensors", "postgres", "123456");//added Postgres
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ConflictAnalyser(SameTimeRule rule1, SameTimeRule rule2) {
		this.rule1 = rule1;
		this.rule2 = rule2;
	}
	
	public void setGroups(SameTimeRule rule1, SameTimeRule rule2) {
		this.rule1 = rule1;
		this.rule2 = rule2;
	}

	public String getConflictTimeBegin() {
		return conflictTimeBegin;
	}

	public String getConflictTimeEnd() {
		return conflictTimeEnd;
	}

	public boolean isConflict(){
		//if(rule1.getConsequence().opposite(rule2.getConsequence()) || timeConflict()){
		if(rule1.getConsequence().opposite(rule2.getConsequence()) ){
			//setConflicTime();
			//printRulesTimes();
			//printConflictTimes();
			
			//checkAntecedent();
			//preferences();
			return true;
		}
		return false;
	}
	
	public int solveConflict1(){
		if(specificity()){
			return 1;
		}else{
			return preferences1();
		}
	}
	
	public boolean solveConflict2(){
		if(specificity()){
			//return specificity();
			return true;
		}else{
			return preferences();
		}
	}
	
	private boolean specificity(){
		rule1.printRule();
		rule2.printRule();
		int minAntecedents = rule1.getAntecedents().size();
		int sameAntecedents=0;
		if(rule1.getAntecedents().size()>rule2.getAntecedents().size()){
			minAntecedents = rule2.getAntecedents().size();
		}

		for(int i=0;i<rule1.getAntecedents().size();i++){
			for(int j=0;j<rule2.getAntecedents().size();j++){
				if (rule1.getAntecedents().get(i).equals(rule2.getAntecedents().get(j))){
					sameAntecedents++;
				}
			}
		}
		
		if(sameAntecedents == minAntecedents){
			if(rule1.getAntecedents().size() != rule2.getAntecedents().size()){
				if(rule1.getAntecedents().size()==minAntecedents){
					winner = rule2;
//					rule2.getConsequence().print();
//					display = rule2.getConsequence().getWholeName();
				}else{
//					rule1.getConsequence().print();
					winner = rule1;
//					display = rule1.getConsequence().getWholeName();
				}
//				System.out.print(" is more informed!!!");
				display = "Using Specificity, " + winner.getConsequence().getWholeName() + ", is more informed!!!";
				return true;
			}
		}
		
		//display = "Specificity cannot solve this conflict, Preference will be applied!!!";
		return false;
	}
	
	private boolean preferences(){
		getPreferences();
		int sumPreferences1,sumPreferences2;
		sumPreferences1 = sumPreferences2 = 0;
		for(int i=0;i<rule1.getAntecedents().size();i++){
			if(rule1.getAntecedents().get(i).getName().substring(0,4).equals("pref")){
				sumPreferences1 = sumPreferences1 + getPriorityFromPreference(rule1.getAntecedents().get(i).getName().substring(4));
			}
		}
		
		for(int i=0;i<rule2.getAntecedents().size();i++){
			if(rule2.getAntecedents().get(i).getName().substring(0,4).equals("pref")){
				sumPreferences2 = sumPreferences2 + getPriorityFromPreference(rule2.getAntecedents().get(i).getName().substring(4));
			}
		}
		
		if(sumPreferences1 != sumPreferences2){
			if(sumPreferences1 > sumPreferences2){
				winner = rule1;
				display = winner.getConsequence().getWholeName() + " ("+sumPreferences1+")";
				display = "Specificity could not resolve this, using PREFERENCES, " + display + ", is more informed!!!";
				return true;
			}else{
				winner = rule2;
				display = winner.getConsequence().getWholeName() + " ("+sumPreferences2+")";
				display = "Specificity could not resolve this, using PREFERENCES, " + display + ", is more informed!!!";
				return true;
			}
			
		}else{
			display = "Specificity nor preferences could not resolve this.";
			//coinToss();
			return false;
		}
	}
	
	private int preferences1(){
		getPreferences();
		int sumPreferences1,sumPreferences2;
		sumPreferences1 = sumPreferences2 = 0;
		
		//System.out.println("Rule 1 antecedents:"); //CommentedOnWednesday
		for(int i=0;i<rule1.getAntecedents().size();i++){
			//System.out.println(rule1.getAntecedents().get(i).getName());//CommentedOnWednesday			
		}
		//System.out.println("Rule 2 antecedents:");//CommentedOnWednesday
		for(int i=0;i<rule2.getAntecedents().size();i++){
			//System.out.println(rule2.getAntecedents().get(i).getName());//CommentedOnWednesday			
		}
		
		for(int i=0;i<rule1.getAntecedents().size();i++){
			if(rule1.getAntecedents().get(i).getName().substring(0,4).equals("pref")){
				sumPreferences1 = sumPreferences1 + getPriorityFromPreference(rule1.getAntecedents().get(i).getName().substring(4));
			}
		}
		
		for(int i=0;i<rule2.getAntecedents().size();i++){
			if(rule2.getAntecedents().get(i).getName().substring(0,4).equals("pref")){
				sumPreferences2 = sumPreferences2 + getPriorityFromPreference(rule2.getAntecedents().get(i).getName().substring(4));
			}
		}
		
		if(sumPreferences1 != sumPreferences2){
			//System.out.println(sumPreferences1 + " - " + sumPreferences2); //CommentedOnWednesday
			if(sumPreferences1 > sumPreferences2){
				winner = rule1;
				display = winner.getConsequence().getWholeName() + " ("+sumPreferences1+")";
				display = "Specificity could not resolve this, using PREFERENCES, " + display + ", is more informed!!!";
				return 2;
			}else{
				winner = rule2;
				display = winner.getConsequence().getWholeName() + " ("+sumPreferences2+")";
				display = "Specificity could not resolve this, using PREFERENCES, " + display + ", is more informed!!!";
				return 2;
			}
			
		}else{
			display = "Specificity nor preferences cannot resolve this!!!";
			return 3;
		}
	}
		
//		if (rule1.getAntecedents().size()>equals){
//			if(rule1.getConsequence().getStatus()){
//			//System.out.println(rule1.getConsequence().getName() + " is more informed");
//				//infoBox(rule1.getConsequence().getName() + " is more informed", "Conflict Resolved" );
//				display = (rule1.getConsequence().getName() + " is more informed");
//			}else{
//				//System.out.println("!" + rule1.getConsequence().getName() + " is more informed");
//				//infoBox("!" + rule1.getConsequence().getName() + " is more informed", "Conflict Resolved" );
//				display = ("!" + rule1.getConsequence().getName() + " is more informed");
//			}
//			
//		}else{
//			if (rule2.getAntecedents().size()>equals){
//				if(rule2.getConsequence().getStatus()){
//					//System.out.println(rule2.getConsequence().getName() + " is more informed");
////					infoBox(rule2.getConsequence().getName() + " is more informed", "Conflict Resolved" );
//					display = (rule2.getConsequence().getName() + " is more informed");
//				}else{
//					//System.out.println("!" + rule2.getConsequence().getName() + " is more informed");
////					infoBox("!" + rule2.getConsequence().getName() + " is more informed", "Conflict Resolved" );
//					display = ("!" + rule2.getConsequence().getName() + " is more informed");
//				}
//			}
//		}
		
//		if (rule1.getAntecedents().size() > rule2.getAntecedents().size()) {
////			System.out.println("Oggy Ante1 " + rule1.getAntecedents().get(equals++).getName());
//			
//			System.out.println("Oggy Ante1: ");
//			for(int i=0;i<rule1.getAntecedents().size();i++){
//				System.out.println(rule1.getAntecedents().get(i).getName());
//			}
//		}else{
////			System.out.println("Oggy Ante2 " + rule2.getAntecedents().get(equals++).getName());
//			
//			System.out.println("Oggy Ante2: ");
//			for(int i=0;i<rule2.getAntecedents().size();i++){
//				System.out.println(rule2.getAntecedents().get(i).getName());
//			}
//		}
		
	
	
	private void getPreferences() {
		preferences = new ArrayList<Preference>();
		
		try {
//			Class.forName("com.mysql.jdbc.Driver");

			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/preferences", "mysql", "123456");
			
			//connPG = DriverManager.getConnection( "jdbc:postgresql://localhost:5432/sensors", "postgres", "123456");// added    
			//conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/preferences", "root", "root");
			stmt = conn.createStatement();
			
			String query, result = null;

			ResultSet resultSet;
			query = "select user.idUser,user.userName,preference.idPreference,preferenceName,priority "
					+ "from preference,priority,user "
					+ "where user.idUser = priority.idUser "
					+ "and priority.idPreference = preference.idPreference "
					//+ "and user.userName = 'Jose'";
					+ "and user.userName = 'Sara'";
			resultSet = stmt.executeQuery(query);
			
			while(resultSet.next()){
				String preferenceName = resultSet.getString("preferenceName");
				int priority = resultSet.getInt("priority");
//				System.out.println(preferenceName + " - " + priority);
				preferences.add(new Preference(preferenceName,priority));
			}
			stmt.close();
			
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
			
	}
	
	private int getPriorityFromPreference(String preference){
		for(int i=0;i<preferences.size();i++){
			if(preferences.get(i).getPreference().equals(preference)){
				return preferences.get(i).getPriority();
			}
		}
		return 0;
	}
	
	
	private void persistency(){
		
	}
	
	private void coinToss() {
		double coin = Math.random()*2;
		//System.out.println("Coin value: " + coin);
		if (coin<=1){
//			rule1.getConsequence().print();
			display = rule1.getConsequence().getWholeName();
			winner = rule1;
		}else{
//			rule2.getConsequence().print();
			display = rule2.getConsequence().getWholeName();
			winner = rule2;
		}
//		System.out.print(" was chosen using COIN TOSS!!!");
		display = display + " was chosen using COIN TOSS, as Specificity and Preferences could not resolve this!!!";
	}

	private boolean timeConflict() {
		if((rule1Begin.compareTo(rule2Begin)>0 & rule1Begin.compareTo(rule2End)<0) ||
				(rule1End.compareTo(rule2Begin)>0 & rule1End.compareTo(rule2End)<0) ||
				(rule2Begin.compareTo(rule1Begin)>0 & rule2Begin.compareTo(rule1End)<0) ||
				(rule2End.compareTo(rule1Begin)>0 & rule2End.compareTo(rule1End)<0) ||
				(rule1Begin.equals(rule2Begin) & rule1End.equals(rule2End))){			
			return true;
		}
		return false;
	}

	private void setConflicTime() {
		conflictTimeBegin = rule1Begin;
		if(conflictTimeBegin.compareTo(rule2Begin)<0){
			conflictTimeBegin = rule2Begin;
		}
		
		conflictTimeEnd = rule1End;
		if(conflictTimeEnd.compareTo(rule2End)>0){
			conflictTimeEnd = rule2End;
		}
	}

//	private void printRulesTimes() {
//		System.out.println(rule1.getTemporalOperators().size() + " - " + rule2.getTemporalOperators().size());
//		System.out.println("Rule 1 Time: " + rule1Begin + " - " + rule1End);
//		System.out.println("Rule 2 Time: " + rule2Begin + " - " + rule2End);
//	}
//	
//	private void printConflictTimes(){
//		System.out.println("Conflict time: " + conflictTimeBegin + " - " + conflictTimeEnd);
//	}
	public void infoBox(String infoMessage, String titleBar){
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
	
	public void infoBox2(String infoMessage, String titleBar){
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.WARNING_MESSAGE);
    }
	
	public String getDisplay(){
		return display;
	}

	public SameTimeRule getRule1() {
		return rule1;
	}

	public SameTimeRule getRule2() {
		return rule2;
	}

	public SameTimeRule getWinner() {
		return winner;
	}
	
	
	
}
