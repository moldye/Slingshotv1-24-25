package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.mechanisms.intake.Intake;
import org.firstinspires.ftc.teamcode.mechanisms.intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.mechanisms.outtake.Outtake;
import org.firstinspires.ftc.teamcode.mechanisms.outtake.OuttakeConstants;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

public class Cycle {
    private Intake intake;
    private Outtake outtake;
    private GamepadMapping controls;
    private Robot robot;

    private CycleState cycleState = CycleState.BASE_STATE;
    private IntakeConstants.IntakeState intakeState = IntakeConstants.IntakeState.BASE_STATE;

    public Cycle(HardwareMap hardwareMap, Telemetry telemetry, GamepadMapping controls, Robot robot) {
        this.robot = robot;
        this.intake = robot.intake;
        this.outtake = robot.outtake;
        this.controls = controls;
    }

    public void update() {
        controls.update();
        robot.drivetrain.update();

        switch(cycleState){
            // I think else ifs will be good here? may need to adjust depending on loop time :)
            case INTAKING:
                if (intakeState.equals(IntakeConstants.IntakeState.FULLY_RETRACTED)) {
                    intake.extendoFullRetract();
                    if (controls.extend.value()) {
                        intakeState = IntakeConstants.IntakeState.EXTENDING;
                    } else {
                        intakeState = IntakeConstants.IntakeState.RETRACTING;
                    }
                }
                else if (intakeState.equals(IntakeConstants.IntakeState.FULLY_EXTENDED)) {
                    intakeState = IntakeConstants.IntakeState.INTAKING;
                    if (controls.clearIntake.value()) {
                        intake.clearIntake();
                    } else {
                        intake.motorRollerOff();
                        intake.backRollerIdle();
                    }
                }
                else if (intakeState.equals(IntakeConstants.IntakeState.EXTENDING)) {
                    intakeState = IntakeConstants.IntakeState.FULLY_EXTENDED;
                    if (controls.clearIntake.value()) {
                        intake.clearIntake();
                    } else {
                        intake.motorRollerOff();
                        intake.backRollerIdle();
                    }
                    intake.extendoFullExtend();
                }
                else if (intakeState.equals(IntakeConstants.IntakeState.INTAKING)) {
                    if (controls.pivot.value()) {
                        intake.flipDownFull();
                    } else {
                        intake.flipUp();
                    }
                    if (controls.intakeOnToIntake.value()) {
                        intake.motorRollerOnToIntake();
                    } else {
                        intake.motorRollerOff();
                    }
                    if (controls.intakeOnToClear.value()) {
                        intake.clearIntake();
                    } else {
                        intake.motorRollerOff();
                        intake.backRollerIdle();
                    }
                    if (controls.retract.value()) {
                        intakeState = IntakeConstants.IntakeState.RETRACTING;
                    }
                }
                else if (intakeState.equals(IntakeConstants.IntakeState.RETRACTING)) {
                    intakeState = IntakeConstants.IntakeState.TRANSFER;
                    intake.flipUp();
                    intake.motorRollerOff();
                    intake.extendoFullRetract();
                }
                // we may need to add an if statement here so it only does this when a sample is actually in the intake, not anytime we retract slides
//            case WRONG_ALLIANCE_COLOR_SAMPLE:
//                intakeState = IntakeConstants.IntakeState.FULLY_EXTENDED;
//                // probably need to do this for some amount of time, test later
//                pushOutSample();
//                break;
                if(intakeState.equals(IntakeConstants.IntakeState.TRANSFER)) {
                    intakeState = IntakeConstants.IntakeState.FULLY_RETRACTED;
                    intake.pivotAxon.setPosition(IntakeConstants.IntakeState.TRANSFER.pivotPos());
                    // pivotAnalog.runToPos(IntakeConstants.IntakeState.TRANSFER.pivotPos());
                    intake.transferSample();
                }
                if (controls.highBasket.value()) {
                    cycleState = CycleState.OUTTAKING;
                }
                if (controls.lowBasket.value()) {
                    cycleState = CycleState.OUTTAKING;
                }
                if (controls.botToBaseState.changed()) {
                    cycleState = CycleState.BASE_STATE;
                }
                break;
            case OUTTAKING:
                if (controls.highBasket.value()) {
                    intake.extendForOuttake();
                    outtake.extendToHighBasket();
                    if (controls.flipBucket.value() &&
                            outtake.outtakeSlideLeft.getCurrentPosition() <= OuttakeConstants.SlidePositions.HIGH_BASKET.getSlidePos() + 10) {
                        outtake.bucketDeposit();
                    } else {
                        outtake.bucketToReadyForTransfer();
                    }
                } else {
                    outtake.returnToRetracted();// this should move the intake back in
                }

                if (controls.lowBasket.value()) {
                    intake.extendForOuttake();
                    outtake.extendToLowBasket();
                    // prob need to tune 10 as threshold
                    if (controls.flipBucket.value() &&
                            outtake.outtakeSlideLeft.getCurrentPosition() <= OuttakeConstants.SlidePositions.LOW_BASKET.getSlidePos() + 10) {
                        outtake.bucketDeposit();
                    } else {
                        outtake.bucketToReadyForTransfer();
                    }
                } else {
                    outtake.returnToRetracted();
                }
                if (controls.botToBaseState.changed()) {
                    cycleState = CycleState.BASE_STATE;
                }
                if (controls.L1hang.value()) {
                    outtake.hang();
                }
                break;
            case BASE_STATE:
                outtake.resetHardware();
                intake.resetHardware();
                break;
        }
        intake.updateTelemetry();
    }

    public enum CycleState {
        INTAKING,
        OUTTAKING,
        BASE_STATE
    }

}
