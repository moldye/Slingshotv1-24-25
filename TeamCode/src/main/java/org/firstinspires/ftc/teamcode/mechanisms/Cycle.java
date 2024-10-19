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

    // FAILSAFES
    private boolean pivotUp;
    private boolean extendoIn;
    private boolean outtakeIn;

    public Cycle(Telemetry telemetry, GamepadMapping controls, Robot robot) {
        this.robot = robot;
        this.intake = robot.intake;
        this.outtake = robot.outtake;
        this.controls = controls;

        pivotUp = true;
        extendoIn = true;
        outtakeIn = true;
    }

    public void update() {
        controls.update();
        robot.drivetrain.update();

        switch(cycleState){
            case INTAKING:
                if (controls.extend.value()) {
                    extendoIn = true;
                    intake.extendoFullExtend();
                } else if (!controls.extend.value()) {
                    extendoIn = true;
                    pivotUp = true;
                    intake.flipUp();
                    intake.motorRollerOff();
                    intake.extendoFullRetract();
                }
                if (controls.retract.value()) {
                    pivotUp = true;
                    extendoIn = true;
                    intake.flipUp();
                    intake.motorRollerOff();
                    intake.extendoFullRetract();
                    intake.pivotAxon.setPosition(IntakeConstants.IntakeState.TRANSFER.pivotPos());
//                    // pivotAnalog.runToPos(IntakeConstants.IntakeState.TRANSFER.pivotPos());
                    intake.transferSample();
                }
                // a (bottom button)
                if (controls.pivot.value()) {
                    pivotUp = false;
                    intake.flipDownFull();
                } else {
                    pivotUp = true;
                    intake.flipUp();
                }
                if (controls.intakeOnToIntake.value() && extendoIn == false) {
                    intake.motorRollerOnToIntake();
                    intake.backRollerIdle();
                } else if (controls.intakeOnToClear.value()){
                    intake.clearIntake();
                } else {
                    intake.motorRollerOff();
                    intake.backRollerIdle();
                }
                // TODO ASK JAMS
//                if (colorSensorSensesBadColor) {
//                    pushOutSample
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
                    extendoIn = false;
                    outtakeIn = false;
                    // TODO tune this
                    intake.extendForOuttake();
                    outtake.extendToHighBasket();
                    if (controls.flipBucket.value() &&
                            outtake.outtakeSlideLeft.getCurrentPosition() <= OuttakeConstants.SlidePositions.HIGH_BASKET.getSlidePos() + 10) {
                        outtake.bucketDeposit();
                    } else {
                        outtake.bucketToReadyForTransfer();
                    }
                } else if (controls.lowBasket.value()) {
                    extendoIn = false;
                    outtakeIn = false;
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
                    extendoIn = true;
                    outtakeIn = true;
                    outtake.returnToRetracted();
                    // this should move the intake back in
                    intake.extendoFullRetract();
                }
                if (controls.botToBaseState.changed()) {
                    cycleState = CycleState.BASE_STATE;
                }
                if (controls.L1hang.value()) {
                    outtake.hang();
                }
                break;
            case BASE_STATE:
                // bucket to ready for transfer & returns to retracted
                outtake.resetHardware();

                // motor off, sets direction back, flips pivot up, back roller still, full retract
                intake.resetHardware();

                if (controls.L1hang.value()) {
                    outtake.hang();
                }

                cycleState = CycleState.INTAKING;

                break;
        }
        robot.updateTelemetry();
    }

    public enum CycleState {
        INTAKING,
        OUTTAKING,
        BASE_STATE
    }

}
