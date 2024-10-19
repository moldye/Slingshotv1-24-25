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

    private CycleState cycleState = CycleState.INTAKING;
    private IntakeConstants.IntakeState intakeState = IntakeConstants.IntakeState.FULLY_RETRACTED;

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
            case INTAKING:
                if (controls.extend.value()) {
                    intake.extendoFullExtend();
                } else if (!controls.extend.value()) {
                    intake.flipUp();
                    intake.motorRollerOff();
                    intake.extendoFullRetract();
                }
                if (controls.retract.value()) {
                    intake.flipUp();
                    intake.motorRollerOff();
                    intake.extendoFullRetract();
                    intake.pivotAxon.setPosition(IntakeConstants.IntakeState.TRANSFER.pivotPos());
//                    // pivotAnalog.runToPos(IntakeConstants.IntakeState.TRANSFER.pivotPos());
                    intake.transferSample();
                }
                // a (bottom button)
                if (controls.pivot.value()) {
                    intake.flipDownFull();
                } else {
                    intake.flipUp();
                }
                if (controls.intakeOnToIntake.value()) {
                    intake.motorRollerOnToIntake();
                    intake.backRollerIdle();
                } else if (controls.intakeOnToClear.value()){
                    intake.clearIntake();
                } else {
                    intake.motorRollerOff();
                    intake.backRollerIdle();
                }
                // we may need to add an if statement here so it only does this when a sample is actually in the intake, not anytime we retract slides
//            case WRONG_ALLIANCE_COLOR_SAMPLE:
//                // probably need to do this for some amount of time, test later
//                pushOutSample();
//                break;
//                else if(intakeState.equals(IntakeConstants.IntakeState.TRANSFER)) {
//                    intakeState = IntakeConstants.IntakeState.FULLY_RETRACTED;
//                    intake.pivotAxon.setPosition(IntakeConstants.IntakeState.TRANSFER.pivotPos());
//                    // pivotAnalog.runToPos(IntakeConstants.IntakeState.TRANSFER.pivotPos());
//                    intake.transferSample();
//                }
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
                if (controls.L1hang.value()) {
                    outtake.hang();
                }
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
