package org.firstinspires.ftc.teamcode.opMode.teleOp;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.hardware.CompleteIntake;
import org.firstinspires.ftc.teamcode.hardware.Drivetrain;
import org.firstinspires.ftc.teamcode.hardware.Flywheel;
import org.firstinspires.ftc.teamcode.hardware.FlywheelPIDF;
import org.firstinspires.ftc.teamcode.hardware.FlywheelServo;
import org.firstinspires.ftc.teamcode.hardware.FlywheelWEncoders;
import org.firstinspires.ftc.teamcode.hardware.Linkage;
import org.firstinspires.ftc.teamcode.hardware.WobbleGoal;
import org.firstinspires.ftc.teamcode.hardware.WobbleGoalV2;

@Config
@TeleOp(name="TeleOpV2",group="TeleOp")
public class TeleOpV2 extends LinearOpMode {
    public static double powerSpeed= 1200;
    public static double highSpeed = 1360;
    //shoot 2-3 inches from line
    private boolean singleshot = false;
    private FlywheelPIDF flywheel = new FlywheelPIDF();
    private FlywheelServo flywheelServo = new FlywheelServo();
    private Linkage linkage = new Linkage();
    private CompleteIntake intake = new CompleteIntake();
    private WobbleGoalV2 wobble = new WobbleGoalV2();
    private Pose2d startPose = new Pose2d(0, 0, Math.toRadians(0));
    @Override
    public void runOpMode() throws InterruptedException{
        Pose2d controls = new Pose2d(
                -gamepad1.left_stick_y,
                -gamepad1.left_stick_x,
                -gamepad1.right_stick_x
        );
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intake.init(hardwareMap);
        linkage.init(hardwareMap);
        flywheel.init(hardwareMap);
        flywheelServo.init(hardwareMap);
        wobble.init(hardwareMap);
        drive.setFlywheel(flywheel);
        boolean toggleWobble = false;
        boolean formerLeftClick = false;
        boolean formerRightClick = false;
        boolean formerA = false;
        boolean formerY = false;
        boolean formerLBump = false;
        boolean formerX = false;
        boolean formerRBump = false;
        boolean formerB = false;
        boolean formerLStick = false;
        boolean formerRStick = false;
        double r, robotAngle, rightX;
        while(!opModeIsActive() && !isStopRequested()){
            telemetry.addData("Status", "Waiting in init");
            telemetry.update();
        }
//why doesnt my code work
        while(opModeIsActive()){
            Trajectory shootStance;
            if(gamepad1.right_stick_button){
                formerRStick = true;
            }

            if(formerRStick){

                if(!gamepad1.right_stick_button){
                    drive.cancelFollowing();
                    startPose = drive.getPoseEstimate();
                    formerRStick = false;
                }
            }
            if(gamepad1.x){
                formerX = true;
            }
            if(formerX){
                if(!gamepad1.x){
                    shootStance = drive.trajectoryBuilder(drive.getPoseEstimate()).
                            lineToLinearHeading(startPose).
                            build();
                    formerX = false;
                    drive.followTrajectoryAsync(shootStance);
                }
            }
            if(gamepad1.right_trigger > 0.5) {
                intake.intake(1);
            }

            else if(gamepad1.left_trigger > 0.5){
                intake.outake(1);
            }

            else{
                intake.outake(0);
            }
            controls = new Pose2d(
                    -gamepad1.left_stick_y,
                    -gamepad1.left_stick_x,
                    -gamepad1.right_stick_x
            );
            /*
            if(!toggleWobble){
                controls = new Pose2d(
                        -gamepad1.left_stick_y,
                        -gamepad1.left_stick_x,
                        -gamepad1.right_stick_x
                );
            }
            else{
                controls = new Pose2d(
                        -gamepad1.left_stick_x,
                       gamepad1.left_stick_y,
                        -gamepad1.right_stick_x
                );
            }

             */
            if(!drive.isBusy())
            drive.setWeightedDrivePower(
                    controls
            );
            

            drive.update();
            telemetry.addData("DesiredSpeed:",highSpeed);
            telemetry.addData("RealSpeed",flywheel.veloc());
            telemetry.addData("left",gamepad1.dpad_left);
            telemetry.addData("right",gamepad1.dpad_right);
            telemetry.addData("Singleshot?",singleshot);
            telemetry.update();

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
                    flywheel.toggle(highSpeed);
                    formerLBump = false;
                }
            }
            if(gamepad1.left_stick_button){
                formerLStick = true;
            }
            if(formerLStick){
                if(!gamepad1.left_stick_button){
                    toggleWobble = !toggleWobble;
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
                    formerLeftClick = false;
                }
            }


        }
    }
}
