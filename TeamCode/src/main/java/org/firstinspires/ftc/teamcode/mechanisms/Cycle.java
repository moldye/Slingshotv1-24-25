package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.mechanisms.intake.Intake;
import org.firstinspires.ftc.teamcode.mechanisms.intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.mechanisms.outtake.Outtake;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

public class Cycle {
    private Intake intake;
    private Outtake outtake;
    private GamepadMapping controls;
    private CycleState cycleState = CycleState.BASE_STATE;

    public Cycle(HardwareMap hardwareMap, Telemetry telemetry, GamepadMapping controls) {
//        intake = Robot.intake;
//        outtake = Robot.outtake;
//        this.controls = controls;
    }

//    public void update() {
//        switch(cycleState){
//            case FULLY_RETRACTED:
//                extendoFullRetract();
//                if (controls.extend.value()) {
//                    intakeState = IntakeConstants.IntakeState.EXTENDING;
//                    break;
//                }
//                if (controls.retract.value()){
//                    intakeState = IntakeConstants.IntakeState.RETRACTING;
//                    break;
//                }
//            case FULLY_EXTENDED:
//                intakeState = IntakeConstants.IntakeState.INTAKING;
//                if (controls.clearIntake.value()) {
//                    clearIntake();
//                } else {
//                    motorRollerOff();
//                    backRollerIdle();
//                }
//                // should come back from pushing out wrong sample and go immediately back to intaking again
////                if (controls.botToBaseState.value()) {
////                    intakeState = IntakeConstants.IntakeState.BASE_STATE;
////                }
//                break;
//            case EXTENDING:
//                intakeState = IntakeConstants.IntakeState.FULLY_EXTENDED;
//                if (controls.clearIntake.value()) {
//                    clearIntake();
//                } else {
//                    motorRollerOff();
//                    backRollerIdle();
//                }
//                extendoFullExtend();
////                if (controls.botToBaseState.value()) {
////                    intakeState = IntakeConstants.IntakeState.BASE_STATE;
////                }
//                break;
//            case INTAKING:
//                if (controls.pivot.value()) {
//                    flipDownFull();
//                } else {
//                    flipUp();
//                }
//                if (controls.intakeOnToIntake.value()) {
//                    motorRollerOnToIntake();
//                } else {
//                    motorRollerOff();
//                }
//                if (controls.intakeOnToClear.value()) {
//                    clearIntake();
//                } else {
//                    motorRollerOff();
//                    backRollerIdle();
//                }
//                if (controls.retract.value()) {
//                    intakeState = IntakeConstants.IntakeState.RETRACTING;
//                }
////                if (controls.botToBaseState.value()) {
////                    intakeState = IntakeConstants.IntakeState.BASE_STATE;
////                }
//                break;
//            case RETRACTING:
//                intakeState = IntakeConstants.IntakeState.TRANSFER;
//                flipUp();
//                motorRollerOff();
//                extendoFullRetract();
//                // we may need to add an if statement here so it only does this when a sample is actually in the intake, not anytime we retract slides
//                break;
//            case WRONG_ALLIANCE_COLOR_SAMPLE:
//                intakeState = IntakeConstants.IntakeState.FULLY_EXTENDED;
//                // probably need to do this for some amount of time, test later
//                pushOutSample();
//                break;
//            case BASE_STATE:
//                intakeState = IntakeConstants.IntakeState.FULLY_RETRACTED;
//                resetHardware();
//                break;
//            case TRANSFER:
//                intakeState = IntakeConstants.IntakeState.FULLY_RETRACTED;
//                pivotAxon.setPosition(IntakeConstants.IntakeState.TRANSFER.pivotPos());
//                // pivotAnalog.runToPos(IntakeConstants.IntakeState.TRANSFER.pivotPos());
//                // automatically, already verified a right colored sample, rolls it into the bucket
//                transferSample();
//                break;
//            case OUTTAKING:
//                extendForOuttake();
//                break;
//        }
//        updateTelemetry();
//    }

    public enum CycleState {
        INTAKING,
        OUTTAKING,
        BASE_STATE
    }

}
