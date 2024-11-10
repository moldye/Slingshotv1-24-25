package org.firstinspires.ftc.teamcode.fsm;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.mechanisms.intake.Intake;
import org.firstinspires.ftc.teamcode.mechanisms.intake.IntakeConstants;
import org.firstinspires.ftc.teamcode.mechanisms.outtake.Outtake;
import org.firstinspires.ftc.teamcode.mechanisms.specimen.SpecimenClaw;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

public class ActiveCycle {
    private Intake intake;
    private Outtake outtake;
    private GamepadMapping controls;
    private Robot robot;
    private SpecimenClaw specimenClaw;
    public ActiveCycle.TransferState transferState;
    private Telemetry telemetry;
    private ElapsedTime loopTime;
    private double startTime;
    public ActiveCycle(Telemetry telemetry, GamepadMapping controls, Robot robot) {
        this.robot = robot;
        this.intake = robot.intake;
        this.outtake = robot.outtake;
        this.controls = controls;
        this.specimenClaw = robot.specimenClaw;

        this.telemetry = telemetry;

        transferState = ActiveCycle.TransferState.BASE_STATE;

        loopTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        startTime = loopTime.milliseconds();
    }
    public void activeIntakeUpdate() {
        controls.update();
        robot.drivetrain.update();

        switch (transferState) {
            case BASE_STATE:
                // TODO: claw defaults to open?
                robot.hardwareSoftReset();
                transferState = ActiveCycle.TransferState.EXTENDO_FULLY_RETRACTED;
                break;
            case EXTENDO_FULLY_RETRACTED:
                intake.activeIntake.motorRollerOff();
                // have to constantly set power of slide motors back
                outtake.returnToRetracted();
                if (controls.extend.value()) {
                    transferState = ActiveCycle.TransferState.EXTENDO_FULLY_EXTENDED;
                    intake.extendoFullExtend();

                } else if (controls.transfer.value()) {
                    transferState = TransferState.TRANSFERING;
                    startTime = loopTime.milliseconds();
                } else if (controls.highBasket.value()) {
                    transferState = ActiveCycle.TransferState.HIGH_BASKET;
                } else if (controls.lowBasket.value()) {
                    transferState = ActiveCycle.TransferState.LOW_BASKET;
                } else if (controls.L1hang.value()) {
                    transferState = ActiveCycle.TransferState.HANGING;
                }
//                if (controls.clearFailsafe.value()) {
//                    intake.extendoFullExtend();
//                    intake.activeIntake.failsafeClear();
//                    transferState = TransferState.EXTENDO_FULLY_EXTENDED;
//                }
                if (controls.openClaw.value()) {
                    specimenClaw.openClaw();
                } else {
                    specimenClaw.closeClaw();

                }
                if (controls.scoreSpec.value()) {
                    outtake.extendToRemoveSpecFromWall();
                    specimenClaw.closeClaw();
                    transferState = ActiveCycle.TransferState.SPEC_SCORING;
                }
                break;
            case EXTENDO_FULLY_EXTENDED:
                outtake.returnToRetracted();
                if (!controls.extend.value()) {
                    intake.extendoFullRetract();
                    intake.activeIntake.flipToTransfer();
                    controls.transfer.set(false);
                    transferState = TransferState.EXTENDO_FULLY_RETRACTED;
                }
                if (controls.intakeOnToIntake.locked() || controls.intakeOnToClear.locked()) {
                    transferState = ActiveCycle.TransferState.INTAKING;
                }
//                if (!controls.clearFailsafe.value()) {
//                    intake.extendoFullRetract();
//                    intake.activeIntake.flipUp();
//                    intake.activeIntake.motorRollerOff();
//                    transferState = TransferState.EXTENDO_FULLY_RETRACTED;
//                }
                break;
            case INTAKING:
                outtake.returnToRetracted();
                if (!controls.extend.value()) {
                    transferState = ActiveCycle.TransferState.EXTENDO_FULLY_EXTENDED;
                } else if (controls.intakeOnToIntake.locked()) {
                    intake.activeIntake.flipDownFull();
                    intake.activeIntake.motorRollerOnToIntake();
                } else if (controls.intakeOnToClear.locked()) {
                    intake.activeIntake.flipDownToClear();
                    intake.activeIntake.clearIntake();
                } else if (!controls.intakeOnToIntake.locked() || !controls.intakeOnToClear.locked()) {
                    intake.activeIntake.flipUp();
                    intake.activeIntake.transferOff();
                }
                break;
            case TRANSFERING:
                outtake.returnToRetracted();
                if (loopTime.milliseconds() - startTime <= 700){
                    intake.activeIntake.transferSample();

                    if (controls.extend.value()) {
                        transferState = ActiveCycle.TransferState.EXTENDO_FULLY_EXTENDED;
                        intake.extendoFullExtend();
                        controls.transfer.set(false);
                        break;
                    }
                } else if (loopTime.milliseconds() - startTime > 700) {
                    transferState = ActiveCycle.TransferState.EXTENDO_FULLY_RETRACTED;
                    controls.transfer.set(false);
                }
//                intake.activeIntake.transferSample();
//                if (!controls.transfer.value()) {
//                    intake.activeIntake.transferOff();
//                    transferState = TransferState.EXTENDO_FULLY_RETRACTED;
//                }
                break;
            case HIGH_BASKET:
                intake.activeIntake.pivotUpForOuttake();
                intake.extendForOuttake();
                outtake.extendToHighBasket();
                if (controls.flipBucket.value()) {
                    outtake.bucketDeposit();
                }
                if (!controls.highBasket.value()) {
                    transferState = ActiveCycle.TransferState.SLIDES_RETRACTED;
                    break;
                }
                break;
            case LOW_BASKET:
                intake.activeIntake.pivotUpForOuttake();
                intake.extendForOuttake();
                outtake.extendToLowBasket();
                if (controls.flipBucket.value()) {
                    outtake.bucketDeposit();
                }
                if (!controls.lowBasket.value()) {
                    transferState = ActiveCycle.TransferState.SLIDES_RETRACTED;
                }
                break;
            case SLIDES_RETRACTED:
                controls.flipBucket.set(false);
                controls.transfer.set(false);
                // could also do to base state
                outtake.bucketToReadyForTransfer();
                outtake.returnToRetracted();
                intake.extendoFullRetract();
                transferState = ActiveCycle.TransferState.EXTENDO_FULLY_RETRACTED;
                break;
            case HANGING:
                outtake.hang();
                if (!controls.L1hang.value()) {
                    outtake.bucketToReadyForTransfer();
                    transferState = ActiveCycle.TransferState.SLIDES_RETRACTED;
                }
                break;
//            case PUSH_OUT_BAD_COLOR:
//                if (loopTime.milliseconds() - startTime <= 1000 && loopTime.milliseconds() - startTime >= 0) {
//                    intake.activeIntake.pushOutSample();
//                } else {
//                    intake.activeIntake.transferOff();
//                    transferState = ActiveCycle.TransferState.INTAKING;
//                }
//                break;
            case SPEC_SCORING:
                outtake.extendToSpecimenHighRack();
                if (!controls.scoreSpec.value()) {
                    transferState = TransferState.SPEC_RETRACTING;
                    startTime = loopTime.milliseconds();
                }
                break;
            case SPEC_RETRACTING:
                outtake.returnToRetracted();

                if (loopTime.milliseconds() - startTime <= 600 && loopTime.milliseconds() - startTime >= 300){
                    specimenClaw.openClaw();
                }
                else if (loopTime.milliseconds() - startTime > 600) {
                    transferState = ActiveCycle.TransferState.EXTENDO_FULLY_RETRACTED;
                    controls.openClaw.set(true);
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
        SPEC_SCORING("SPEC_SCORING"),
        SPEC_RETRACTING("SPEC_RETRACTING"),
        // PUSH_OUT_BAD_COLOR("PUSH_OUT_BAD_COLOR"),
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
