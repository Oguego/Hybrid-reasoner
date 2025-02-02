package edu.casetools.icase.mreasoner.myactuators;

import edu.casetools.icase.mreasoner.configs.data.MConfigs;
import edu.casetools.icase.mreasoner.database.core.implementations.PostgreSQL_DatabaseOperations;
import edu.casetools.icase.mreasoner.database.core.operations.DatabaseOperations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.Timestamp;

import org.json.*;
import edu.casetools.icase.mreasoner.actuators.data.Action;
import edu.casetools.icase.mreasoner.actuators.data.ActuatorConfigs;
import edu.casetools.icase.mreasoner.actuators.device.Actuator;

//Gines: Created to send messages to external RESTFUL api. 
// THIS METHOD is using SUCCESS PROJECT in case that server house sends directly the outcomes to cloud server.

public class OutsideActuator implements Actuator{
	
	//URL service;
	//URLConnection connection;
//	MConfigs configs; // IT NEED CHANGE/ADD A CLASS CONFIG FOR RESTFUL SERVER OR KEEP AS A CLASS CONSTANT AT THE MOMENT ->easier and faster
	Action lastAction;
	ActuatorConfigs configs;
	DatabaseOperations dbo;
		
	public OutsideActuator(ActuatorConfigs configs){
		this.configs = configs;
		lastAction = new Action();
		//this.databaseOperations = new PostgreSQL_DatabaseOperations(configs.getDBConfigs());
	}
	
	public void performAction(Action action) {
		DatabaseOperations db = this.getDatabaseOperation();
		if(!lastAction.equals(action)){
	//		System.out.print("PERFORM ACTION " + action.getDevice() + " time " + action.getValue());
			db.setOutsideActuatorResult(action.getDevice(), "time", action.getValue());
			System.out.println("STORING RESULT " + action.getDevice()+" TO VALUE "+action.getValue());
		    this.sendUrlServer(action);
		    
		}
		
		lastAction = action;
	}
		
	
	public void sendUrlServer(Action action){
		
		System.out.print("SENDING TO SERVER ---->");
		String url="http://10.12.102.56/server.php/outcomes";
		HttpURLConnection con;
		JSONObject json = new JSONObject();
		URL object;
		try {
			object = new URL(url);
			con = (HttpURLConnection) object.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestMethod("POST");
			con.setDoInput(true);
            con.setDoOutput(true);
			
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			
			
			json.put("iduser", "1");
			json.put("type", "1");
			json.put("value", action.getValue());
			json.put("datetime", timestamp);
			OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
			
			wr.write(json.toString());
			wr.flush();
			wr.close();
			BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
			in.close();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		System.out.print("SENDED TO SERVER ---->" + url + " :: " + json.toString());
	}
	

	public ActuatorConfigs getConfigs() {
		return this.configs;
	}

	public void setDatabaseOperations(DatabaseOperations db) {
		this.dbo=db;
		
	}
	
	public DatabaseOperations getDatabaseOperation() {
		// TODO Auto-generated method stub
		return this.dbo;
	}

	public Action readAction() {
		Action action  = new Action();
		String state   = this.getConfigs().getState();
	//	System.out.println("***************************************** " + state + " ****************************************************************** ");
		boolean status = dbo.getStatus(state);
		
		action.setDevice(state);
		action.setValue(status);
				
		return action;
	}

	public boolean isDefinedState() {
		// TODO Auto-generated method stub
		return this.dbo.hasColumn("results", this.getConfigs().getState());
		
	}
	

	
}
