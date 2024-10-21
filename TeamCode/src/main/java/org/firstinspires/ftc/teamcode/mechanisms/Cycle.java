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

    private TransferState transferState;
    private Telemetry telemetry;

    public Cycle(Telemetry telemetry, GamepadMapping controls, Robot robot) {
        this.robot = robot;
        this.intake = robot.intake;
        this.outtake = robot.outtake;
        this.controls = controls;

        this.telemetry = telemetry;

        transferState = TransferState.BASE_STATE;
    }

    public void update() {
        controls.update();
        robot.drivetrain.update();

        switch(transferState){
            case BASE_STATE:
                robot.hardwareSoftReset();
                transferState = TransferState.EXTENDO_FULLY_RETRACTED;
                break;
            case EXTENDO_FULLY_RETRACTED:
                if (controls.extend.value()) {
                    transferState = TransferState.EXTENDO_FULLY_EXTENDED;
                    intake.extendoFullExtend();
                }
                else if (controls.transfer.value()) {
                    transferState = TransferState.TRANSFERING;
                }
                else if (controls.highBasket.value()) {
                    transferState = TransferState.HIGH_BASKET;
                }
                else if (controls.lowBasket.value()) {
                    transferState = TransferState.LOW_BASKET;
                }
                break;
            case EXTENDO_FULLY_EXTENDED:
                if (!controls.extend.value()) {
                    intake.flipUp();
                    intake.motorRollerOff();
                    intake.extendoFullRetract();
                    transferState = TransferState.EXTENDO_FULLY_RETRACTED;
                    // break;
                }
                else if (controls.pivot.value()) {
                    intake.flipDownFull();
                    transferState = TransferState.INTAKING;
                    // break;
                }
                else if (controls.botToBaseState.value()) {
                    transferState = TransferState.BASE_STATE;
                    // break;
                }
                break;
            case INTAKING:
                // a (bottom button)
                if (!controls.pivot.value()) {
                    intake.flipUp();
                    transferState = TransferState.EXTENDO_FULLY_EXTENDED;
                    // break;
                }
                // TODO try pressing these at the same time
                else if (controls.intakeOnToIntake.value()) {
                    intake.motorRollerOnToIntake();
                    intake.backRollerIdle();
                } else if (controls.intakeOnToClear.value()) {
                    intake.clearIntake();
                } else if (!controls.intakeOnToIntake.value() || !controls.intakeOnToClear.value()){
                    intake.motorRollerOff();
                    intake.backRollerIdle();
                }
                // TODO ASK JAMS
//                if (colorSensorSensesBadColor) {
//                    pushOutSample
//                }
                break;
            case TRANSFERING:
                intake.transferSample();
                if (!controls.transfer.value()) {
                    intake.motorRollerOff();
                    intake.backRollerIdle();
                    transferState = TransferState.EXTENDO_FULLY_RETRACTED;
                }
                break;
            case HIGH_BASKET:
                intake.extendForOuttake();
                outtake.extendToHighBasket();
                if (controls.flipBucket.value()) {
                    outtake.bucketDeposit();
                }
                else if (!controls.highBasket.value()) {
                    transferState = TransferState.SLIDES_RETRACTED;
                    // break;
                }
                break;
            case LOW_BASKET:
                intake.extendForOuttake();
                outtake.extendToLowBasket();
                if (controls.flipBucket.value()) {
                    outtake.bucketDeposit();
                }
                else if (!controls.lowBasket.value()) {
                    transferState = TransferState.SLIDES_RETRACTED;
                    // break;
                }
                break;
            case SLIDES_RETRACTED:
                // could also do to base state
                outtake.bucketToReadyForTransfer();
                outtake.returnToRetracted();
                intake.extendoFullRetract();
                transferState = TransferState.EXTENDO_FULLY_RETRACTED;
                break;
        }
        robot.updateTelemetry();
        telemetry.addData("Transfer State: ", transferState.stateName());
    }

//    public void update() {
//        controls.update();
//        robot.drivetrain.update();

//        switch(cycleState){
//            case INTAKING:
//                if (controls.extend.value()) {
//                    extendoIn = true;
//                    intake.extendoFullExtend();
//                } else if (!controls.extend.value()) {
//                    extendoIn = true;
//                    pivotUp = true;
//                    intake.flipUp();
//                    intake.motorRollerOff();
//                    intake.extendoFullRetract();
//                }
////                if (controls.retract.value()) {
////                    pivotUp = true;
////                    extendoIn = true;
////                    intake.flipUp();
////                    intake.pivotAxon.setPosition(IntakeConstants.IntakeState.TRANSFER.pivotPos());
//////                    // pivotAnalog.runToPos(IntakeConstants.IntakeState.TRANSFER.pivotPos());
////                }
//                // a (bottom button)
//                if (controls.pivot.value()) {
//                    pivotUp = false;
//                    intake.flipDownFull();
//                } else {
//                    pivotUp = true;
//                    intake.flipUp();
//                }
//                if (controls.intakeOnToIntake.value()) {
//                    intake.motorRollerOnToIntake();
//                    intake.backRollerIdle();
//                } else if (controls.intakeOnToClear.value()) {
//                    intake.clearIntake();
//                } else if (controls.transfer.value()) {
//                    intake.transferSample();
//                } else {
//                    intake.motorRollerOff();
//                    intake.backRollerIdle();
//                }
//                // TODO ASK JAMS
////                if (colorSensorSensesBadColor) {
////                    pushOutSample
////                }
//                if (controls.highBasket.value()) {
//                    cycleState = CycleState.OUTTAKING;
//                }
//                if (controls.lowBasket.value()) {
//                    cycleState = CycleState.OUTTAKING;
//                }
//                if (controls.botToBaseState.changed()) {
//                    cycleState = CycleState.BASE_STATE;
//                }
//                break;
//            case OUTTAKING:
//                if (controls.highBasket.value()) {
//                    extendoIn = false;
//                    outtakeIn = false;
//                    // TODO tune this
//                    intake.extendForOuttake();
//                    outtake.extendToHighBasket();
//                    if (controls.flipBucket.value()) {
//                        outtake.bucketDeposit();
//                    }
////                } else if (controls.lowBasket.value()) {
////                    extendoIn = false;
////                    outtakeIn = false;
////                    intake.extendForOuttake();
////                    outtake.extendToLowBasket();
////                    // prob need to tune 10 as threshold
////                    if (controls.flipBucket.value() &&
////                            outtake.outtakeSlideLeft.getCurrentPosition() <= OuttakeConstants.SlidePositions.LOW_BASKET.getSlidePos() + 10) {
////                        outtake.bucketDeposit();
////                    } else {
////                        outtake.bucketToReadyForTransfer();
////                    }
//                }
//                else {
//                    extendoIn = true;
//                    outtakeIn = true;
//                    outtake.returnToRetracted();
//                    // this should move the intake back in
//                    intake.extendoFullRetract();
//                    outtake.bucketToReadyForTransfer();
//                }
//                if (controls.botToBaseState.changed()) {
//                    cycleState = CycleState.BASE_STATE;
//                }
//                if (controls.L1hang.value()) {
//                    outtake.hang();
//                }
//                if (controls.extend.value()) {
//                    cycleState = CycleState.INTAKING;
//                }
//                break;
//            case BASE_STATE:
//                // bucket to ready for transfer & returns to retracted
//                outtake.resetHardware();
//
//                // motor off, sets direction back, flips pivot up, back roller still, full retract
//                intake.resetHardware();
//
//                if (controls.L1hang.value()) {
//                    outtake.hang();
//                }
//
//                cycleState = CycleState.INTAKING;
//
//                break;
//        }
//        robot.updateTelemetry();
//    }

    public enum TransferState {
        BASE_STATE("BASE_STATE"),
        EXTENDO_FULLY_RETRACTED("EXTENDO_FULLY_RETRACTED"),
        EXTENDO_FULLY_EXTENDED("EXTENDO_FULLY_EXTENDED"),
        INTAKING("INTAKING"),
        TRANSFERING("TRANSFERING"),
        SLIDES_RETRACTED("SLIDES_RETRACTED"),
        HIGH_BASKET("HIGH_BASKET"),
        LOW_BASKET("LOW_BASKET");

        private String state;

        TransferState(String stateName) {
            state = stateName;
        }

        public String stateName() {
            return state;
        }

    }
}
