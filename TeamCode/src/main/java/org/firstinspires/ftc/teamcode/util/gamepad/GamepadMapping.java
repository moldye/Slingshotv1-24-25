package org.firstinspires.ftc.teamcode.util.gamepad;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.util.gamepad.Toggle;

public class GamepadMapping {

    private Gamepad gamepad1;
    private Gamepad gamepad2;

    // INTAKE
    // Extend/Retract Intake (Intake automatically runs, this extends linkage) -> left bumper

    // Pivot Down - comes out in the middle of extension, automatic
    // Pivot Out
    // Outtake bad sample (automatically does this)

    // OUTTAKE
    // Slides go up/down -> toggle
    // Bucket flips & releases sample -> one of the buttons on the right side

    // SCORING
    // slides increment pos -> button to latch on (button once to go up, again to latch)
    // claw releases/closes -> on misc controller, toggle, buttons on right side

    // DRIVETRAIN
    public double drive = 0.0;
    public double strafe = 0.0;
    public double turn = 0.0;

    // INTAKE
    public static Toggle switchExtendo; // extend & retract extendo

    // OUTTAKE
    public static Toggle resetSlides;
    public static Toggle bucketRelease;

    public boolean outtakeSlidesButton;

    // SCORING
    public static Toggle latchSpecimen;
    public static Toggle switchClaw;
    public static Toggle toBaseState;

    public static boolean lock90 = false;
    public static boolean lock180 = false;
    public static boolean lock270 = false;
    public static boolean lock360 = false;

    public GamepadMapping(Gamepad gamepad1, Gamepad gamepad2) {
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;

        switchExtendo = new Toggle(false);

        resetSlides = new Toggle(false); // this might actually need to be true, idk
        bucketRelease = new Toggle(false);

        latchSpecimen = new Toggle(false);
        switchClaw = new Toggle(false);

        toBaseState = new Toggle(false);
    }

    public void update() {
        drive = gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        turn = gamepad1.right_stick_x;

        switchExtendo.update(gamepad1.left_bumper);

        outtakeSlidesButton = gamepad1.right_bumper; // this may not work bc of goofy loop time, idk
        resetSlides.update(gamepad1.right_trigger > 0.8);
        bucketRelease.update(gamepad1.a);

        latchSpecimen.update(gamepad2.a);
        switchClaw.update(gamepad1.x);

        toBaseState.update(gamepad1.b);
    }
}
