package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.mechanisms.intake.ColorSensorModule;
import org.firstinspires.ftc.teamcode.mechanisms.intake.Intake;
import org.firstinspires.ftc.teamcode.mechanisms.intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.mechanisms.outtake.Outtake;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

public class Cycle {
    private Intake intake;
    private Outtake outtake;
    private GamepadMapping controls;
    private Robot robot;

    private TransferState transferState;
    private Telemetry telemetry;
    private ElapsedTime loopTime;
    private ColorSensorModule colorSensor;
    private double startTime;

    public Cycle(Telemetry telemetry, GamepadMapping controls, Robot robot) {
        this.robot = robot;
        this.intake = robot.intake;
        this.outtake = robot.outtake;
        this.controls = controls;

        this.telemetry = telemetry;

        transferState = TransferState.BASE_STATE;

        loopTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        startTime = loopTime.milliseconds();
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
                // have to constantly set power of slide motors back
                outtake.returnToRetracted();
                if (controls.extend.value()) {
                    transferState = TransferState.EXTENDO_FULLY_EXTENDED;
                    intake.extendoFullExtend();
                    if (loopTime.milliseconds() - startTime > 0 && loopTime.milliseconds() - startTime < 500) {
                        intake.halfFlipDownToClear();
                    }
                }
                else if (controls.transfer.value()) {
                    transferState = TransferState.TRANSFERING;
                }
                else if (controls.highBasket.value()) {
                    transferState = TransferState.HIGH_BASKET;
                }
                else if (controls.lowBasket.value()) {
                    transferState = TransferState.LOW_BASKET;
                } else if (controls.L1hang.value()) {
                    transferState = TransferState.HANGING;
                }
                break;
            case EXTENDO_FULLY_EXTENDED:
                outtake.returnToRetracted();
                if (!controls.extend.value()) {
                    intake.flipUp();
                    intake.motorRollerOff();
                    intake.extendoFullRetract();
                    // this should run transfer for half a second
                    intake.pivotAxon.setPosition(IntakeConstants.IntakeState.TRANSFER.pivotPos());
                    transferState = TransferState.EXTENDO_FULLY_RETRACTED;
                }
                // TODO see if state pivot thingy I made below is better
                if (controls.intakeOnToIntake.locked() || controls.intakeOnToClear.locked()) {
                    transferState = TransferState.INTAKING;
                }
                if (controls.botToBaseState.value()) {
                    transferState = TransferState.BASE_STATE;
                }
                break;
            case INTAKING:
                outtake.returnToRetracted();
                // a (bottom button)
//                if (!controls.pivot.value()) {
//                    intake.flipUp();
//                    transferState = TransferState.EXTENDO_FULLY_EXTENDED;
//                }
                if (!controls.extend.value()) {
                    transferState = TransferState.EXTENDO_FULLY_EXTENDED;
                }
                else if (controls.intakeOnToIntake.locked()) {
                    intake.flipDownFull();
                    intake.motorRollerOnToIntake();
                    intake.backRollerIdle();
                    if (intake.colorSensor.checkSample().equals(IntakeConstants.SampleTypes.BLUE) && !intake.colorSensor.isBlue) {
                        transferState = TransferState.PUSH_OUT_BAD_COLOR;
                        startTime = loopTime.milliseconds();
                    } else if (intake.colorSensor.checkSample().equals(IntakeConstants.SampleTypes.RED) && intake.colorSensor.isBlue) {
                        transferState = TransferState.PUSH_OUT_BAD_COLOR;
                        startTime = loopTime.milliseconds();
                    }
                } else if (controls.intakeOnToClear.locked()) {
                    intake.halfFlipDownToClear();
                    intake.clearIntake();
                } else if (!controls.intakeOnToIntake.locked() || !controls.intakeOnToClear.locked()){
                    intake.flipUp();
                    intake.motorRollerOff();
                    intake.backRollerIdle();
                } else {
                    if (intake.colorSensor.checkSample().equals(IntakeConstants.SampleTypes.BLUE) && !intake.colorSensor.isBlue) {
                        transferState = TransferState.PUSH_OUT_BAD_COLOR;
                        startTime = loopTime.milliseconds();
                    } else if (intake.colorSensor.checkSample().equals(IntakeConstants.SampleTypes.RED) && intake.colorSensor.isBlue) {
                        transferState = TransferState.PUSH_OUT_BAD_COLOR;
                        startTime = loopTime.milliseconds();
                    }
                }
                break;
            case TRANSFERING:
                outtake.returnToRetracted();
                intake.flipUp();
                intake.extendoFullRetract();
                intake.transferSample();
                if (!controls.transfer.value()) {
                    intake.motorRollerOff();
                    intake.backRollerIdle();
                    transferState = TransferState.EXTENDO_FULLY_RETRACTED;
                }
                break;
            case HIGH_BASKET:
                intake.pivotUpForOuttake();
                intake.extendForOuttake();
                outtake.extendToHighBasket();
                if (loopTime.milliseconds() - startTime <= 500) {
                    outtake.bucketTilt();
                }
                if (controls.flipBucket.value()) {
                    outtake.bucketDeposit();
                }
                if (!controls.highBasket.value()) {
                    transferState = TransferState.SLIDES_RETRACTED;
                    break;
                }
                break;
            case LOW_BASKET:
                intake.pivotUpForOuttake();
                intake.extendForOuttake();
                outtake.extendToLowBasket();
                if (loopTime.milliseconds() - startTime <= 500) {
                    outtake.bucketTilt();
                }
                if (controls.flipBucket.value()) {
                    outtake.bucketDeposit();
                }
                if (!controls.lowBasket.value()) {
                    transferState = TransferState.SLIDES_RETRACTED;
                }
                break;
            case SLIDES_RETRACTED:
                controls.flipBucket.set(false);
                // could also do to base state
                outtake.bucketToReadyForTransfer();
                outtake.returnToRetracted();
                intake.extendoFullRetract();
                transferState = TransferState.EXTENDO_FULLY_RETRACTED;
                break;
            case HANGING:
                outtake.hang();
                if (!controls.L1hang.value()) {
                    transferState = TransferState.SLIDES_RETRACTED;
                }
                break;
            case PUSH_OUT_BAD_COLOR:
                if (loopTime.milliseconds() - startTime <= 1300 && loopTime.milliseconds() - startTime >= 0) {
                    intake.pushOutSample();
                    intake.motorRollerOnToIntake();
                } else {
                    intake.motorRollerOff();
                    intake.backRollerIdle();
                    transferState = TransferState.INTAKING;
                }
            break;
        }
    }

    public enum TransferState {
        BASE_STATE("BASE_STATE"),
        EXTENDO_FULLY_RETRACTED("EXTENDO_FULLY_RETRACTED"),
        EXTENDO_FULLY_EXTENDED("EXTENDO_FULLY_EXTENDED"),
        INTAKING("INTAKING"),
        TRANSFERING("TRANSFERING"),
        SLIDES_RETRACTED("SLIDES_RETRACTED"),
        HIGH_BASKET("HIGH_BASKET"),
        LOW_BASKET("LOW_BASKET"),
        PUSH_OUT_BAD_COLOR("PUSH_OUT_BAD_COLOR"),
        HANGING("HANGING");

        private String state;

        TransferState(String stateName) {
            state = stateName;
        }

        public String stateName() {
            return state;
        }

    }
}
