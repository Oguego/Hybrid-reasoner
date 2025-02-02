package edu.casetools.icase.mreasoner.deployment.actuators;

import java.awt.List;
import java.util.ArrayList;
import java.util.Vector;

import edu.casetools.icase.mreasoner.configs.data.db.MDBConfigs;
import edu.casetools.icase.mreasoner.database.core.operations.DatabaseOperations;
import edu.casetools.icase.mreasoner.database.core.operations.DatabaseOperationsFactory;
import edu.casetools.icase.mreasoner.actuators.data.Action;
import edu.casetools.icase.mreasoner.actuators.data.ActuatorConfigs;
import edu.casetools.icase.mreasoner.actuators.device.Actuator;
import edu.casetools.icase.mreasoner.actuators.device.ActuatorDB;



public class MActuatorManager extends Thread{

	private boolean running;
	private DatabaseOperations databaseOperations;	
	protected Vector<Actuator> actuators;
	
	public MActuatorManager(MDBConfigs configs, Vector<Actuator> actuators){	
		
		this.actuators = actuators;
		this.databaseOperations = DatabaseOperationsFactory.getDatabaseOperations(configs);
		//Delete the actuator is not in the rules, they are not needed
		ArrayList<Actuator> deleted = new ArrayList<Actuator>();
		for(int i=0;i<actuators.size(); i++){
			Actuator act = actuators.get(i);
			act.setDatabaseOperations(this.databaseOperations);
			System.out.println("Checking sensors " + i + " of " + actuators.size() + " - " + act.getConfigs().getState() + " " + act.isDefinedState());
			
			if (!act.isDefinedState()){
				//System.out.println("Revoming... " + act.getConfigs().getState());
					deleted.add(this.actuators.get(i));
				//	this.actuators.remove(act);
			}		
		}
		for (Actuator a: deleted){
			this.actuators.remove(a);
		}
		running = true;
	}

	public void run(){
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		while (running)
		{
			for(int i=0;i<actuators.size();i++){
						Actuator act = actuators.get(i);
						//Action action = readAction(act.getConfigs());
						Action action = act.readAction();
						act.performAction(action);
						
			}
		}
	}
	
/** CREATE ACTION FOR OUTSIDEACTUATOR */
/*	private Action readAction(ActuatorConfigs actuatorConfigs, String device){
		Action action  = new Action();
		String state   = actuatorConfigs.getState();

	//	String device  = databaseOperations.getDevice(state);
		boolean status = databaseOperations.getStatus(state);
		
		if(device != null){
			action.setDevice(state);
			action.setValue(status);
		}
		
		return action;
	}*/

/** CREATE ACTION FOR VERA ACTUATOR */	
	private Action readAction(ActuatorConfigs actuatorConfigs){
		Action action  = new Action();
		String state   = actuatorConfigs.getState();

		String device  = databaseOperations.getDevice(state);
		boolean status = databaseOperations.getStatus(state);
		
		if(device != null){
			action.setDevice(device);
			action.setValue(status);
		}
		
		return action;
	}

	public void terminate(){
		running = false;
	}
	
}
