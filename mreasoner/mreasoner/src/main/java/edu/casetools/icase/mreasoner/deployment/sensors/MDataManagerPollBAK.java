package edu.casetools.icase.mreasoner.deployment.sensors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import edu.casetools.icase.mreasoner.configs.data.MConfigs;
import edu.casetools.icase.mreasoner.database.core.operations.DatabaseOperations;
import edu.casetools.icase.mreasoner.database.core.operations.DatabaseOperationsFactory;



public class MDataManagerPollBAK implements Runnable {

	DatabaseOperations databaseOperations;
	ArrayList<Sensors> sensorsList; 
	ArrayList<String> sensorsError;
	Boolean running;
	
	public MDataManagerPollBAK(MConfigs configs){
		//this.databaseOperations = databaseOperations;
		
		this.databaseOperations = DatabaseOperationsFactory.getDatabaseOperations(configs.getDBConfigs());
		
		this.sensorsList = new ArrayList<Sensors>();
		this.sensorsError = new ArrayList<String>();
		ResultSet dbsensors = this.databaseOperations.getSensors();
	
		try {
			while (dbsensors.next()) {
				//System.out.println("Sensors Checking for %" + dbsensors.getString(2) +"%");
				if(this.databaseOperations.hasColumn("results",dbsensors.getString(2))){
					//this.sensorsList.add( new Sensors(dbsensors.getString(1),dbsensors.getString(2), "0",dbsensors.getString(3), dbsensors.getString(4)));
				}
			}			
			this.running=true;	
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		
	}
	

	public void run(){
		String status;
	
		while (this.running){
			long start = System.currentTimeMillis();
			for (Sensors sensor: this.sensorsList) {
				if (!this.sensorsError.contains(sensor.getId())){
					status = this.poll(sensor);
				
					if (status != null){
						status = this.statusToStringBool(status);
			
						if (!status.equals(sensor.getStatus())){
							System.out.println("*********  Change on State: "+ status +" ******* INFO: " + sensor.getId() + ": " + sensor.getState() + " change " + sensor.getStatus() + " to "  +  status);
					    	//System.out.println(sensor.getId() + ": " + sensor.getState() + " change " + sensor.getStatus() + " to "  +  status);
					       	sensor.setStatus(status);
					    	this.createEvent(sensor);
					    	
					    }
					}
				 }
			}
			long sleeptime = System.currentTimeMillis() - start;
			System.out.println(" ****************************** POLLER PROCESS SPAN " + sleeptime + " *********************************");
			try {
				Thread.sleep(1000 - Math.abs(sleeptime));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public void terminate(){
		this.running = false;
		
	}
	
	/** Request vera the device state **/
	public String poll(Sensors s){
		
		URL service;
		URLConnection connection = null;
		String url ="http://10.12.102.56:3480/data_request?id=variableget&serviceId=" + s.getService() + "&Variable=" + s.getVariable() + "&DeviceNum=" + s.getId();
		BufferedReader in = null;
		String state = "";
	//	System.out.print("Pollllliiiinng " + url);
		try {
			service = new URL(url);
			connection = service.openConnection();
			
			in = new BufferedReader(new InputStreamReader(
			                            connection.getInputStream()));
			
			state = in.readLine();
			in.close();
		} catch (IOException e) {
			
			this.sensorsError.add(s.getId());
			System.out.print("-->" + s.getId() + ": Bad Device or not responding " + url);
			//this.sensors.remove(s);
			return null;
			
			//e.printStackTrace();
		}
        return state;
	}
	
//If the value from vera is not boolean. normally is Float, but this method there is to think better for do it more general.
	public String statusToStringBool(String status){
		if (status.equals("0") || status.equals("1")){
			return status;
		}else{
		
			float fs = Float.parseFloat(status);
			if (fs<30){ //24 watt it is the minimal power without turn on the microwave, when electric is on is 
				
				return "0";
			}
			else {
				return "1";
			}
		}
	}
	
	
	protected void createEvent(Sensors sensor) {
		 	
		 if(sensor.getState() != null && sensor.getStatus() != null){
				
		     	 databaseOperations.insertEvent(sensor.getState(), ""+sensor.getStatus(),"-1", sensor.getDate(), sensor.getTime());
		     	// printVariable(variable, state);
		  } else {
		     		 // printVariableWarning(variable);
		  }
		 	//      System.out.println("***************************************************************************");	
	}


}
