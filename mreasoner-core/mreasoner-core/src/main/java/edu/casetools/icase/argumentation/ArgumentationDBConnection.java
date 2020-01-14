package edu.casetools.icase.argumentation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ArgumentationDBConnection {
	
	private Connection conn = null;
	   private Statement stmt = null;
	   
	   public ArgumentationDBConnection(){
		   		
	   }
	   
	   public void connect() throws SQLException, ClassNotFoundException{
		   
		    Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection( "jdbc:mysql://localhost:3306/sensors", "mysql", "123456");
//		    stmt = conn.createStatement();
		   
	   }
	   
	   public void executeUpdate(String query) throws SQLException{
		   if(conn != null){
			   stmt = conn.createStatement();
			   if(stmt != null){
				   int rows = stmt.executeUpdate(query);
				   System.out.println("Rows affected: " + rows);
//				   stmt.close();
			   }
			   
		   }
	   }
	   
	   public ResultSet executeQuery(String query) throws SQLException{
		   ResultSet resultSet = null;
		   if(conn != null){
			   stmt = conn.createStatement();
			   if(stmt != null){
				   resultSet = stmt.executeQuery(query);
//				   stmt.close();
			   }
		   }
		   return resultSet;
		}
	   
	   public ResultSet executeQueryOpenStatement(String query) throws SQLException{
		  
		   ResultSet resultSet = null;
		   if(conn != null){
			   stmt = conn.createStatement();
			   resultSet = stmt.executeQuery(query);
//			   stmt.close();
		   }else{
			   System.out.println("WARNING CONNECTION = NULL");
		   }
		   return resultSet;
		}
	   
	  public void closeStatement() throws SQLException{
		   try { stmt.close(); } catch (Exception e) { /* ignored */ };
	   }
	   
	   public void disconnect() throws SQLException{
		   try { conn.close(); } catch (Exception e) { /* ignored */ };
	   }	   

}