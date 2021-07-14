package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Robot {
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotor shooterDrive = null;
    // private DcMotor intakeDrive = null;

    private Servo shooterServo = null;
    private ElapsedTime shooterTimer = new ElapsedTime();
    boolean isShooterInTransition = false;

    public void init(HardwareMap hwMap) {
        leftDrive  = hwMap.get(DcMotor.class, "dc0");
        rightDrive = hwMap.get(DcMotor.class, "dc1");
        shooterDrive = hwMap.get(DcMotor.class, "dc2");
        // intakeDrive = hwMap.get(DcMotor.class, "dc3");
        shooterServo = hwMap.get(Servo.class, "servo0");

        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        shooterDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        // intakeDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        shooterServo.setPosition(0.35); // start position = 0.35, shooting position = 0.75

        leftDrive.setPower(0);
        rightDrive.setPower(0);
        shooterDrive.setPower(0);
        // intakeDrive.setPower(0);
    }

    public void controlDrivetrain(double leftPower, double rightPower) {
        leftDrive.setPower(leftPower);
        rightDrive.setPower(rightPower);
    }

    public void controlShooter(boolean isShoot) {
        shooterDrive.setPower(1.0);

        if (!isShooterInTransition && isShoot) {
            shooterServo.setPosition(0.75);
            shooterTimer.reset();
            isShooterInTransition = true;
        }

        if (isShooterInTransition) {
            if (shooterTimer.milliseconds() > 500) {
                shooterServo.setPosition(0.35);
            }
            if (shooterTimer.milliseconds() > 1000) {
                isShooterInTransition = false;
            }
        }
    }

    public void controlIntake() {
        // intakeDrive.setPower(1.0);
    }
}
