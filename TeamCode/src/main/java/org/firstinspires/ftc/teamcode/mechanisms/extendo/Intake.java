package org.firstinspires.ftc.teamcode.mechanisms.extendo;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {
    // motor spins the intake rollers
    // servo that pivots intake
    // servo that pushes samples out back

    // flip down & up intake
    // spin the rollers to intake samples
    // push out samples from back

    private DcMotorEx rollerMotor;
    private Servo pivotAxon;
    private CRServo backRollerServo;


    public Intake(HardwareMap hwMap) {
        rollerMotor = hwMap.get(DcMotorEx.class, "rollerMotor");
        pivotAxon = hwMap.get(Servo.class, "pivotAxon");
        backRollerServo = hwMap.get(CRServo.class, "backRollerServo");

        rollerMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        rollerMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        pivotAxon.setDirection(Servo.Direction.FORWARD);

        backRollerServo.setDirection(DcMotorSimple.Direction.REVERSE); // reverse for servo to push samples out -> tune
    }

    // This is for testing only :)
    public Intake(DcMotorEx rollerMotor, Servo pivotAxon, CRServo backRollerServo) {
        this.rollerMotor = rollerMotor;
        this.pivotAxon = pivotAxon;
        this.backRollerServo = backRollerServo;
    }

    public void flipDown() {
        pivotAxon.setPosition(1); // this will need to be tuned
    }

    public void flipUp() {
        pivotAxon.setPosition(0); // this will need to be tuned
    }

    public void pushOutSample() {
        backRollerServo.setPower(1);
    }

    public void motorRollerOn() {
        rollerMotor.setPower(1); // want this to be as fast as possible to intake well
    }

    public void resetHardware() {
        rollerMotor.setPower(0);
        rollerMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        pivotAxon.setPosition(0);
        pivotAxon.setDirection(Servo.Direction.FORWARD);

        backRollerServo.setPower(0);
        backRollerServo.setDirection(DcMotorSimple.Direction.REVERSE);
    }
}
