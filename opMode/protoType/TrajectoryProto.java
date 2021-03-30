  package org.firstinspires.ftc.teamcode.opMode.protoType;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.DelayCommand;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.hardware.CompleteIntake;
import org.firstinspires.ftc.teamcode.hardware.Drivetrain;
import org.firstinspires.ftc.teamcode.hardware.Flywheel;
import org.firstinspires.ftc.teamcode.hardware.FlywheelServo;
import org.firstinspires.ftc.teamcode.hardware.FlywheelWEncoders;
import org.firstinspires.ftc.teamcode.hardware.Linkage;
import org.firstinspires.ftc.teamcode.hardware.WobbleGoal;

@Autonomous(name="TrajectoryProto")
@Config
public class TrajectoryProto extends LinearOpMode {
    private boolean backyard = false;
    private FlywheelWEncoders flywheel = new FlywheelWEncoders();
    private FlywheelServo flywheelServo = new FlywheelServo();
    private Linkage linkage = new Linkage();
    private CompleteIntake intake = new CompleteIntake();
    private DelayCommand delay = new DelayCommand();
    private WobbleGoal wobbleMech = new WobbleGoal();
    private int startWobbleTrajec = 0;
    public void runOpMode() throws InterruptedException{
        Runnable toggleShooting = new Runnable() {
            @Override
            public void run() {
                linkage.toggle();
                flywheel.toggle();
            }
        };
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intake.init(hardwareMap);
        linkage.init(hardwareMap);
        flywheel.init(hardwareMap);
        flywheelServo.init(hardwareMap);
        wobbleMech.init(hardwareMap);
        Pose2d startPose = new Pose2d(0, -12, Math.toRadians(0));
        drive.setPoseEstimate(startPose);
        Runnable unClamp = new Runnable() {
            @Override
            public void run() {
                wobbleMech.unClamp();
            }
        };
        Runnable raise = new Runnable() {
            @Override
            public void run() {
                wobbleMech.raise();
            }
        };
        Runnable clamp = new Runnable() {
            @Override
            public void run() {
                wobbleMech.clamp();
            }
        };
        int startFourRings;
        int offsetPathInit = 0;

        if(backyard){
            offsetPathInit = 3;
        }

        Trajectory pathInit = drive.trajectoryBuilder(startPose).
                addTemporalMarker(0,()->{
                    flywheel.toggle();
                    linkage.toggle();
                })
                .splineTo(new Vector2d(40,-20),0)
                .splineToConstantHeading(new Vector2d(54,3),0).
                addDisplacementMarker(()->{
                             flywheelServo.burst(3);
                             telemetry.addData("Flywheel Velocity",flywheel.veloc());
                             telemetry.update();
                })
                .build();

        Trajectory fourRings = drive.trajectoryBuilder(pathInit.end())
                .lineToLinearHeading(new Pose2d(103, 17, Math.toRadians(45))).
                addTemporalMarker(1,()-> {
                    if(wobbleMech.armRaised) wobbleMech.toggleArm();
                }).
                addDisplacementMarker(()->{
                    wobbleMech.onetime();
                })
                .build();
        Trajectory afterWobble1 = drive.trajectoryBuilder(fourRings.end()).
                lineToLinearHeading(new Pose2d(48, 0, Math.toRadians(180)))
                .addDisplacementMarker(()->{
                    intake.intake(0.8);
                    wobbleMech.unClamp();
                })
                .build();
        Trajectory getRingsV4 = drive.trajectoryBuilder(afterWobble1.end()).
                addDisplacementMarker(()->{
                    intake.intake(1);
                    wobbleMech.raise();
                }).
                forward(15).
                addDisplacementMarker(()->{
                    Runnable temp2 = new Runnable() {
                        @Override
                        public void run() {
                            intake.outake(1);
                        }
                    };
                    Runnable temp = new Runnable() {
                        @Override
                        public void run() {
                            intake.outake(1);
                        }
                    };
                    delay.delay(temp,2300);
                }).
                build();
        Trajectory getRingsV3 = drive.trajectoryBuilder(afterWobble1.end()).
                addDisplacementMarker(()->{
                    intake.intake(0.8);
                    wobbleMech.raise();
                }).
                lineToSplineHeading(new Pose2d(24,5,Math.toRadians(160))).
                addDisplacementMarker(()->{
                    Runnable temp2 = new Runnable() {
                        @Override
                        public void run() {
                            intake.intake(0);
                        }
                    };
                    Runnable temp = new Runnable() {
                        @Override
                        public void run() {
                            intake.outake(1);
                            delay.delay(temp2,2000);
                        }
                    };
                    delay.delay(temp,3000);
                }).
                build();

        Trajectory getRingsV2 = drive.trajectoryBuilder(afterWobble1.end()).
                lineToSplineHeading(new Pose2d(21,0,Math.toRadians(140))).
                addDisplacementMarker(()->{
                    wobbleMech.clamp();
                    Runnable temp2 = new Runnable() {
                        @Override
                        public void run() {
                            intake.intake(0);
                        }
                    };
                    Runnable temp = new Runnable() {
                        @Override
                        public void run() {
                            intake.outake(1);
                            delay.delay(temp2,2000);
                        }
                    };

                    delay.delay(temp,2800);
                }).
                build();

        Trajectory getRings = drive.trajectoryBuilder(afterWobble1.end())
                .forward(12)
                .addTemporalMarker(2,()->{
                    intake.intake(0);

                }).
                build();
        Trajectory getWobblePostRings = drive.trajectoryBuilder(getRings.end()).
                addTemporalMarker(0.3,()->{
                    //intake.intake(1);
                }).
                addTemporalMarker(1,()->{
                    //intake.intake(0);
                }).
                lineToSplineHeading(new Pose2d(24,4,Math.toRadians(152))).
                addDisplacementMarker(()->{
                    wobbleMech.clamp();
                }).
                build();
        Trajectory shootPostWobbleRetrieval = drive.trajectoryBuilder(getRingsV3.end()).
                addTemporalMarker(0.5,()->{
                    intake.outake(1);
                    flywheel.toggle();
                    linkage.toggle();
                }).
                lineToSplineHeading(new Pose2d(54,3 ,Math.toRadians(-3))).
                addDisplacementMarker(()->{
                    intake.intake(0);
                    flywheelServo.burst(3);
                }).
                build();
        Trajectory wobble2Retrieval = drive.trajectoryBuilder(shootPostWobbleRetrieval.end())
                .lineToSplineHeading(new Pose2d(22,5,Math.toRadians(180-12)))
                .addDisplacementMarker(()->{
                    wobbleMech.unClamp();
                    wobbleMech.lower();
                    Runnable temp = new Runnable() {
                        @Override
                        public void run() {
                            wobbleMech.clamp();
                        }
                    };
                    delay.delay(temp,1000);
                    
                })
                .build();
        Trajectory getWobble2 = drive.trajectoryBuilder(afterWobble1.end()).
                forward(12.5)
                .addDisplacementMarker(()->{
                    wobbleMech.clamp();
                }).addTemporalMarker(5,()->{
                    intake.intake(0);
                })
                .build();
        Trajectory getWobble2V2 = drive.trajectoryBuilder(shootPostWobbleRetrieval.end()).
                lineToSplineHeading(new Pose2d(26,6.5,Math.toRadians(180)))
                .addTemporalMarker(0.5,()->{
                    wobbleMech.lower();
                })
                .addDisplacementMarker(()->{
                    wobbleMech.clamp();
                }).
                build();


        Trajectory fourRings2 = drive.trajectoryBuilder(getWobble2V2.end())
                .lineToLinearHeading(new Pose2d(103, 17, Math.toRadians(45))).
                        addTemporalMarker(1,()-> {
                            if(wobbleMech.armRaised) wobbleMech.toggleArm();
                        }).
                        addDisplacementMarker(()->{
                            wobbleMech.unClamp();
                        })
                .build();
        Trajectory fourRingsDupe = drive.trajectoryBuilder(wobble2Retrieval.end())
                .lineToLinearHeading(new Pose2d(103, 17, Math.toRadians(45))).
                        addTemporalMarker(1,()-> {
                            if(wobbleMech.armRaised) wobbleMech.toggleArm();
                        }).
                        addDisplacementMarker(()->{
                            wobbleMech.unClamp();
                        })
                .build();
        Trajectory endAuton = drive.trajectoryBuilder(fourRingsDupe.end()).
                lineTo(new Vector2d(72,0)).
                build();
        telemetry.addData("Status", "Initialized");
        waitForStart();


        if (isStopRequested()) return;

        drive.followTrajectory(pathInit);
        sleep(2000);
        flywheel.toggle();
        linkage.toggle();
        drive.followTrajectory(fourRings);

        drive.followTrajectory(afterWobble1);
        drive.followTrajectory(getRingsV4);
        sleep(1000);
        drive.followTrajectory(shootPostWobbleRetrieval);
        sleep(2000);
        linkage.toggle();
        flywheel.toggle();
        drive.followTrajectory(wobble2Retrieval);
        sleep(1000);
        drive.followTrajectory(fourRingsDupe);
        drive.followTrajectory(endAuton);

        /*
        drive.followTrajectory(getRingsV3);


        sleep(500);
        linkage.toggle();
        flywheel.toggle();
        drive.followTrajectory(getWobble2V2);
        sleep(200);
        drive.followTrajectory(fourRings2);
        drive.followTrajectory(endAuton);
         */
        Pose2d poseEstimate = drive.getPoseEstimate();
        telemetry.addData("finalX", poseEstimate.getX());
        telemetry.addData("finalY", poseEstimate.getY());
        telemetry.addData("finalHeading", poseEstimate.getHeading());
        telemetry.update();

        while (!isStopRequested() && opModeIsActive()) ;

    }

    private void shoot(int discs){
        Runnable flick = new Runnable() {
            @Override
            public void run() {
                flywheelServo.flick();
            }
        };
        int normalGap = 350;
        int offset = 100;
        delay.delay(flick,offset);
        for(int i = 1; i < discs; i++){
            delay.delay(flick,i*normalGap + offset);
        }
        Runnable toggleShooting = new Runnable() {
            @Override
            public void run() {
                if(linkage.inAir()) {
                    flywheel.toggle();
                }
                else{
                    flywheel.toggle();
                }
                linkage.toggle();
            }
        };
        delay.delay(toggleShooting,discs*normalGap+offset);
        startWobbleTrajec = discs*normalGap+offset;
    }


}
