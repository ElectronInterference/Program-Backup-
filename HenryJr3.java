/* Copyright (c) 2017 FIRST. All rights reserved.
 */


package org.firstinspires.ftc.teamcode;

import java.util.TimerTask;
import java.util.Timer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.teamcode.MoldugaHardware;
import java.util.List;



/**
 * This is the autonomous that starts near the depot
 */
@Autonomous(name="DepotAuto", group="Linear Opmode")

public class HenryJr3 extends LinearOpMode {

    //acces our hardware program
    MoldugaHardware robot   = new MoldugaHardware();

    //create a timer to send whether the program is stopped to the hardware class
    Timer timer = new Timer("Check For Stop");
    TimerTask checkForStop = new TimerTask() {
        @Override
        public void run() {
            //If needed, change a variable in MoldugaHardware that stores whether stop is requested
            robot.isStopRequested = isStopRequested();
            
            //Stop this timer if the program is stopped.
            if(robot.isStopRequested) {
                timer.cancel();
            }
        }
    };
    
    //Wait 50 millis between each loop of the timer and don't delay anything besides that.
    long timerPeriod = 50L;
    long delay = 0L;

    
     
    @Override
    public void runOpMode() {
        
        //Schedule a time to tell MoldugaHardware whether the program is stopped
        timer.schedule(checkForStop, delay, timerPeriod );
        
        //use our hardwaremap program
        robot.init(hardwareMap);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        
        waitForStart(); 
        

        Land();
        navigateToDepot();
        DeployMarker();
        NavigateToCrater();
        timer.cancel();

    }
    
    //unlatch from the lander
    public void Land(){
        //move the lift up
        robot.liftUp();
        //strafe out of the latch
        robot.driveRight(1,5);
        //turn towards the depot
        robot.turnDegrees(-6);
    }
    
    
    
    
     public void FindAndKnockOffCheeseblock(){
         
     
    }
    
    
    
    
    //go the the depot
    public void navigateToDepot() {
        //send telemetry for where we are in the program
        telemetry.addData("Status", "leaving");
        telemetry.update();
        //drive the the depot
        robot.driveForward(0.6,56);
        //turn so that the team marker always falls into the depot
        robot.turnDegrees(-90);
    }
    
    //Deploy the marker
    public void DeployMarker() {
        //Push the team marker off of it's pedestal
        robot.markerRelease();
        //Wait until the marker has definitely fallen
        robot.wait(1500);
        //turn towards the crater
        robot.turnDegrees(-50);
        //strafe to make sure we do not run over our team marker
        robot.driveLeft(0.4, 5);
    }
    
    //Drive to the Crater
    public void NavigateToCrater(){ 
        //Tell the driver where we are
        telemetry.addData("Status", "backing up");
        telemetry.update();
        //Drive into the crater
        robot.driveBackward(1,120);
    }
}
