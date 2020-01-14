package edu.casetools.icase.mreasoner.gui;


import java.io.File;
import java.util.Vector;

import javax.swing.JSplitPane;

import edu.casetools.icase.mreasoner.configs.data.MConfigs;
import edu.casetools.icase.mreasoner.gui.controller.Controller;
import edu.casetools.icase.mreasoner.gui.model.Model;
import edu.casetools.icase.mreasoner.gui.view.View;
import edu.casetools.icase.mreasoner.myactuators.LampActuator;
import edu.casetools.icase.mreasoner.myactuators.LampConfigs;
import edu.casetools.icase.argumentation.RulesCompiler; // imported to use in loading file path content
import edu.casetools.icase.mreasoner.actuators.device.Actuator;

public class Main {
	
	public static void main(String[] args) {
		//declare variable and store path of the file
		
		String session="";
		if(args.length>0){
			//Controller controller1 = new Controller(null, null, null);			
			//controller1.setDividersAtDefaultLocation();
			RulesCompiler.mainFile= args[0];
			session = args[1];
			System.out.println("ARGS: " + args[0] + " - " + args[1]);
		
		}

			Vector<Actuator> actuators = new Vector<Actuator>();

			//Add to the actuators vector your own list of actuators.
			LampConfigs lampConfigs = new LampConfigs("LBigLamp");
//			//LampConfigs RadioConfigs = new LampConfigs("Radio");
			LampConfigs MicrowaveConfigs = new LampConfigs("Television");
//			LampConfigs SmallLampConfigs = new LampConfigs("SmallLamp");
			LampConfigs ToiletLightConfigs = new LampConfigs("ToiletLight");
			LampConfigs BedRoomLightConfigs = new LampConfigs("BedRoomLight");
			LampConfigs KitchenLightConfigs = new LampConfigs("KitchenLight");
			LampConfigs KitchenMovementConfigs = new LampConfigs("KitchenMovement");
			LampConfigs ShowerRoomLightConfigs = new LampConfigs("ShowerRoomLight");
			LampConfigs LivingRoomLightConfigs = new LampConfigs("LivingroomLight");
			LampConfigs LivingRoomMotionConfigs = new LampConfigs("LivingRoomMotion");
			//LampConfigs LivingRoomDoorConfigs = new LampConfigs("LivingRoomDoor"); 
			LampConfigs BedroomMotionConfigs = new LampConfigs("BedRoomMotion");
			LampConfigs ToiletMotionConfigs = new LampConfigs("ToiletMotion");
			LampConfigs ShowerMotionConfigs = new LampConfigs("ShowerMotion");
			LampConfigs FrontdoorMotionConfigs = new LampConfigs("FrontdoorMotion");
			//LampConfigs EntranceDoorConfigs = new LampConfigs("EntranceDoor");
			LampConfigs CorridorMovementConfigs =new LampConfigs("CorridorMotion");
			LampConfigs CorridorLightConfigs = new LampConfigs("CorridorLight");
//			//LampConfigs MS_MotionSensorConfigs = new LampConfigs("MS_MotionSensor");
//			//LampConfigs MS_LightSensorConfigs = new LampConfigs("MS_LightSensor");
//			LampConfigs BigPadIdleConfigs = new LampConfigs("BedroomBedPressure");
//			LampConfigs PadIdleConfigs = new LampConfigs("PadIdle");
//			LampConfigs ToiletDoorConfigs = new LampConfigs("ToiletDoor");
//			LampConfigs TableLampConfigs = new LampConfigs("TableLamp");
			
			
			LampActuator lampActuator = new LampActuator(lampConfigs);
//			//LampActuator RadioActuator = new LampActuator(RadioConfigs);
			LampActuator MicrowaveActuator = new LampActuator(MicrowaveConfigs);
//			LampActuator smalllampActuator = new LampActuator(SmallLampConfigs);
			LampActuator ToiletLightActuator = new LampActuator(ToiletLightConfigs);
			LampActuator BedRoomLightActuator = new LampActuator(BedRoomLightConfigs);
			LampActuator KitchenLightActuator = new LampActuator(KitchenLightConfigs);
			LampActuator KitchenMovementActuator = new LampActuator(KitchenMovementConfigs);
			LampActuator ShowerRoomLightActuator = new LampActuator(ShowerRoomLightConfigs);
			LampActuator LivingRoomLightActuator = new LampActuator(LivingRoomLightConfigs);
			LampActuator LivingRoomMotionActuator = new LampActuator(LivingRoomMotionConfigs);
			//LampActuator LivingRoomDoorActuator = new LampActuator(LivingRoomDoorConfigs);
			LampActuator BedroomMotionActuator = new LampActuator(BedroomMotionConfigs);
			LampActuator ToiletMotionActuator = new LampActuator(ToiletMotionConfigs);
			LampActuator ShowerMotionActuator = new LampActuator(ShowerMotionConfigs);
			LampActuator FrontdoorMotionActuator = new LampActuator(FrontdoorMotionConfigs);
			//LampActuator EntranceDoorActuator = new LampActuator(EntranceDoorConfigs);
			LampActuator CorridorMovementActuator = new LampActuator(CorridorMovementConfigs);
			LampActuator CorridorLightActuator = new LampActuator(CorridorLightConfigs);
//			LampActuator MS_MotionSensorActuator = new LampActuator(MS_MotionSensorConfigs);
//			LampActuator MS_LightSensorActuator = new LampActuator(MS_LightSensorConfigs);
//			LampActuator BigPadIdleActuator = new LampActuator(BigPadIdleConfigs);
//			LampActuator PadIdleActuator = new LampActuator(PadIdleConfigs);
//			LampActuator ToiletDoorActuator = new LampActuator(ToiletDoorConfigs);
//			LampActuator TableLampActuator = new LampActuator(TableLampConfigs);
//			
			
			actuators.add(lampActuator);
			//actuators.add(RadioActuator);
			actuators.add(MicrowaveActuator);
//			actuators.add(smalllampActuator);
			actuators.add(ToiletLightActuator);
			actuators.add(BedRoomLightActuator);
			actuators.add(KitchenLightActuator);
			actuators.add(KitchenMovementActuator);
			actuators.add(ShowerRoomLightActuator);
			actuators.add(LivingRoomLightActuator);
			actuators.add(LivingRoomMotionActuator);
			//actuators.add(LivingRoomDoorActuator);
			actuators.add(BedroomMotionActuator);
			actuators.add(ToiletMotionActuator);
			actuators.add(ShowerMotionActuator);
			actuators.add(FrontdoorMotionActuator);
			//actuators.add(EntranceDoorActuator);
			actuators.add(CorridorMovementActuator);
			actuators.add(CorridorLightActuator);
//			//actuators.add(MS_MotionSensorActuator);
//			//actuators.add(MS_LightSensorActuator);
//			actuators.add(BigPadIdleActuator);
//			actuators.add(PadIdleActuator);
//			actuators.add(ToiletDoorActuator);
//			actuators.add(TableLampActuator);
			
			MConfigs configs = new MConfigs();
			
			Model model = new Model(configs.getDBConfigs(), actuators);
			View view = new View(configs);
			Controller controller = new Controller(view,model,configs.getFilesConfigs());
			
			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			controller.setDividersAtDefaultLocation();
			
			//Start New Addition
			if(!session.equals(""))
				{
				//Controller controller1 = new Controller(view,model,configs.getFilesConfigs());
				MConfigs configs1 = controller.getModel().getConfigsReader().readConfigs(session);
				
				if(configs1!=null){
					controller.getView().getMainWindow().getMainPanel().setConfigs(configs1);
					controller.getView().getMainWindow().getMainPanel().getMainMenu().enableConfigurationButtons(true);
					controller.loadConfigs();
					controller.getView().getMainWindow().getMainPanel().getMainMenu().enableButtons(true);
					}
				controller.setDividersAtDefaultLocation();
				}
			//END New Addition
	}
}
