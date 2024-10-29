package org.firstinspires.ftc.teamcode.fsm;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Robot;
import org.firstinspires.ftc.teamcode.mechanisms.intake.Claw;
import org.firstinspires.ftc.teamcode.mechanisms.intake.Intake;
import org.firstinspires.ftc.teamcode.mechanisms.outtake.Outtake;
import org.firstinspires.ftc.teamcode.misc.gamepad.GamepadMapping;

public class ClawCycle {
    private Robot robot;
    private Intake intake;
    private Outtake outtake;
    private GamepadMapping controls;
    private Claw claw;
    private ClawCycle.TransferState transferState;
    private Telemetry telemetry;
    private ElapsedTime loopTime;
    private double startTime;

    // TODO when we go back from hovering, automatic position of claw should be straight

    public ClawCycle(Telemetry telemetry, GamepadMapping controls, Robot robot) {
        this.robot = robot;
        this.intake = robot.intake;
        this.outtake = robot.outtake;
        this.claw = intake.claw;
        this.controls = controls;

        this.telemetry = telemetry;

        transferState = ClawCycle.TransferState.BASE_STATE;

        loopTime = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
        startTime = loopTime.milliseconds();
    }
    public void clawIntakeUpdate() {
        controls.clawUpdate();
        robot.drivetrain.update();

        switch (transferState) {
            case BASE_STATE:
                robot.hardwareSoftReset();
                transferState = ClawCycle.TransferState.EXTENDO_FULLY_RETRACTED;
                break;
            case EXTENDO_FULLY_RETRACTED:
                // have to constantly set power of slide motors back
                outtake.returnToRetracted();
                claw.closeClaw();
                // may need to change this, but anytime we retract, we move to transfering
                transferState = ClawCycle.TransferState.TRANSFERING;

                if (controls.extend.value()) {
                    transferState = ClawCycle.TransferState.EXTENDO_FULLY_EXTENDED;
                    intake.extendoFullExtend();
                }
                else if (controls.highBasket.value()) {
                    transferState = ClawCycle.TransferState.HIGH_BASKET;
                } else if (controls.lowBasket.value()) {
                    transferState = ClawCycle.TransferState.LOW_BASKET;
                } else if (controls.L1hang.value()) {
                    transferState = ClawCycle.TransferState.HANGING;
                }
                if (controls.transferHover.value()) {
                    claw.moveToHovering();
                }
                if (controls.pivot.locked()) {
                    transferState = ClawCycle.TransferState.INTAKING;
                }
                break;
            case EXTENDO_FULLY_EXTENDED:
                outtake.returnToRetracted();
                if (!controls.extend.value()) {
                    claw.moveToTransfer();
                    intake.extendoFullRetract();
                    transferState = ClawCycle.TransferState.TRANSFERING;
                }
                if (controls.botToBaseState.value()) {
                    transferState = ClawCycle.TransferState.BASE_STATE;
                }
                if (controls.pivot.locked()) {
                    claw.moveToPickingSample();
                    transferState = ClawCycle.TransferState.INTAKING;
                }
                if (controls.openClaw.value()) {
                    claw.openClaw();
                } else {
                    claw.closeClaw();
                }
                break;
            case INTAKING:
                outtake.returnToRetracted();
                claw.controlWristPos();
                if (!controls.extend.value()) {
                    transferState = ClawCycle.TransferState.EXTENDO_FULLY_EXTENDED;
                }
                if (!controls.pivot.locked()) {
                    claw.moveToHovering();
                }
                if (controls.openClaw.value()) {
                    claw.openClaw();
                } else {
                    claw.closeClaw();
                }
                break;

            case TRANSFERING:
                // this is automatic, TODO MAY NEED TO ADD ELAPSED TIME, MAKE SURE THAT DOES NOT GET IN THE WAY OF FAILSAFES
                outtake.returnToRetracted();
                // block in long-way
                claw.moveToTransfer();
                intake.extendoFullRetract();
                claw.turnWristToTransfer();
                claw.openClaw();
                transferState = ClawCycle.TransferState.EXTENDO_FULLY_RETRACTED;
                break;
            case HIGH_BASKET:
                outtake.extendToHighBasket();
                if (controls.flipBucket.value()) {
                    outtake.bucketDeposit();
                }
                if (!controls.highBasket.value()) {
                    transferState = ClawCycle.TransferState.SLIDES_RETRACTED;
                    break;
                }
                break;
            case LOW_BASKET:
                outtake.extendToLowBasket();
                if (controls.flipBucket.value()) {
                    outtake.bucketDeposit();
                }
                if (!controls.lowBasket.value()) {
                    transferState = ClawCycle.TransferState.SLIDES_RETRACTED;
                }
                break;
            case SLIDES_RETRACTED:
                controls.flipBucket.set(false);

                outtake.bucketToReadyForTransfer();
                outtake.returnToRetracted();
                intake.extendoFullRetract();
                transferState = ClawCycle.TransferState.EXTENDO_FULLY_RETRACTED;
                break;
            case HANGING:
                outtake.hang();
                if (!controls.L1hang.value()) {
                    transferState = ClawCycle.TransferState.SLIDES_RETRACTED;
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
