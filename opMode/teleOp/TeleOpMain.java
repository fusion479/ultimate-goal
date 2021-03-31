package org.firstinspires.ftc.teamcode.opMode.teleOp;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.hardware.CompleteIntake;
import org.firstinspires.ftc.teamcode.hardware.Drivetrain;
import org.firstinspires.ftc.teamcode.hardware.Flywheel;
import org.firstinspires.ftc.teamcode.hardware.FlywheelServo;
import org.firstinspires.ftc.teamcode.hardware.FlywheelWEncoders;
import org.firstinspires.ftc.teamcode.hardware.Linkage;
import org.firstinspires.ftc.teamcode.hardware.WobbleGoal;
@Config
@TeleOp(name="TeleOpMain",group="TeleOp")
public class TeleOpMain extends LinearOpMode {
    public static double powerSpeed= 1200;
    public static double highSpeed = 1400;
    //shoot 2-3 inches from line
    private boolean singleshot = false;
    private Drivetrain drive = new Drivetrain(this);
    private FlywheelWEncoders flywheel = new FlywheelWEncoders();
    private FlywheelServo flywheelServo = new FlywheelServo();
    private Linkage linkage = new Linkage();
    private CompleteIntake intake = new CompleteIntake();
    private WobbleGoal wobble = new WobbleGoal();
    @Override
    public void runOpMode() throws InterruptedException{
        intake.init(hardwareMap);
        drive.init(hardwareMap);
        linkage.init(hardwareMap);
        flywheel.init(hardwareMap);
        flywheelServo.init(hardwareMap);
        wobble.init(hardwareMap);

        boolean formerLeftClick = false;
        boolean formerRightClick = false;
        boolean formerA = false;
        boolean formerY = false;
        boolean formerLBump = false;
        boolean formerX = false;
        boolean formerRBump = false;
        boolean formerB = false;
        boolean formerLStick = false;
        double r, robotAngle, rightX;
        while(!opModeIsActive() && !isStopRequested()){
            telemetry.addData("Status", "Waiting in init");
            telemetry.update();
        }
//why doesnt my code work
        while(opModeIsActive()){
            //ADD flywheelpidf.update()
            if(gamepad1.right_trigger > 0.5) {
                intake.intake(1);
            }

            else if(gamepad1.left_trigger > 0.5){
                intake.outake(1);
            }

            else{
                intake.outake(0);
            }

            r = Math.hypot(gamepad1.left_stick_x,gamepad1.left_stick_y);
            robotAngle = Math.atan2(gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;
            rightX = -1*gamepad1.right_stick_x;
            telemetry.addData("Reverse?",drive.getReverse());
            telemetry.addData("Flywheel DesiredSpeed:",flywheel.speed);
            telemetry.addData("RealSpeed",flywheel.veloc());
            telemetry.addData("left",gamepad1.dpad_left);
            telemetry.addData("right",gamepad1.dpad_right);
            telemetry.addData("Singleshot?",singleshot);
            telemetry.update();


            if(gamepad1.x){
                formerX = true;
            }
            if(formerX){
                if(!gamepad1.x) {
                    formerX = false;
                    drive.reverse();
                }
            }

            if(gamepad1.b){
                formerB = true;
            }
            if(formerB){
                if(!gamepad1.b){
                    wobble.toggleClamp();
                    formerB = false;
                }
            }

            if(gamepad1.right_bumper){
                formerRBump = true;
            }
            if(formerRBump){
                if(!gamepad1.right_bumper){
                    wobble.toggleArm();
                    formerRBump = false;
                }
            }

            if(gamepad1.left_bumper){
                formerLBump = true;
            }
            if(formerLBump){
                if(!gamepad1.left_bumper){
                    flywheel.toggle();
                    formerLBump = false;
                }
            }
            if(gamepad1.left_stick_button){
                formerLStick = true;
            }
            if(formerLStick){
                if(!gamepad1.left_stick_button){
                    singleshot = !singleshot;
                    if(singleshot){
                        flywheel.speed = powerSpeed;
                    }
                    else{
                        flywheel.speed = highSpeed;
                    }
                    formerLStick = false;
                }
            }
            if(gamepad1.y){
                formerY = true;
            }
            if(formerY){
                if(!gamepad1.y){

                    if(flywheel.running() && linkage.inAir()){
                        if(singleshot){
                            flywheelServo.flick();
                        }
                        else{
                            flywheelServo.burst(3);
                        }

                    }
                    formerY = false;
                }
            }

            if(gamepad1.a){
                formerA = true;
            }
            if(formerA){
                if(!gamepad1.a){
                    linkage.toggle();
                    formerA = false;
                }
            }

            if(gamepad1.dpad_left){
                formerLeftClick = true;
            }

            if(formerLeftClick){
                if(!gamepad1.dpad_left){
                    flywheel.speed -= 0.05;
                    formerLeftClick = false;
                }
            }

            if(gamepad1.dpad_right){
                formerRightClick = true;
            }

            if(formerRightClick){
                if(!gamepad1.dpad_right){
                    flywheel.speed += 0.05;
                    formerRightClick = false;
                }
            }

            drive.teleDrive(r,robotAngle,rightX);


        }
    }
}
