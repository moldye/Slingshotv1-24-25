package org.firstinspires.ftc.teamcode.misc.gamepad;

import com.qualcomm.robotcore.hardware.Gamepad;

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
    // --------------
    public static double drive = 0.0;
    public static double strafe = 0.0;
    public static double turn = 0.0;

    // INTAKE
    // --------------
    public static Toggle extend; // extend intake
    public static Toggle retract; // retract intake

    // OUTTAKE
    // --------------
    public static Toggle resetSlides;
    public static Toggle bucketRelease;

    public static boolean outtakeSlidesButton;

    // SCORING
    // --------------
    public static Toggle latchSpecimen;
    public static Toggle switchClaw;

    public static boolean lock90 = false;
    public static boolean lock180 = false;
    public static boolean lock270 = false;
    public static boolean lock360 = false;

    // OTHER
    // --------------
    public static Toggle botToBaseState;
    public static Toggle pivot;

    // TESTING BUTTONS
    // NOT TO BE USED FOR COMP
    // -------------------------------
    public static Toggle toggleIntakePower;
    public static Toggle powerIntake;
    public static Toggle switchExtendo;
    public static Toggle transfer;

    public GamepadMapping(Gamepad gamepad1, Gamepad gamepad2) {
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;

        extend = new Toggle(false);
        retract = new Toggle(false);

        resetSlides = new Toggle(false); // this might actually need to be true, idk
        bucketRelease = new Toggle(false);

        latchSpecimen = new Toggle(false);
        switchClaw = new Toggle(false);

        botToBaseState = new Toggle(false);

        // TESTING BUTTONS
        toggleIntakePower = new Toggle(false);
        pivot = new Toggle(false);
        powerIntake = new Toggle(false);
        switchExtendo = new Toggle(false);
        transfer = new Toggle(false);
    }

    public void update() {
        joystickUpdate();

        extend.update(gamepad1.x);
        retract.update(gamepad1.y);

        outtakeSlidesButton = gamepad1.right_bumper; // this may not work bc of goofy loop time, idk
        resetSlides.update(gamepad1.left_bumper);
        bucketRelease.update(gamepad1.a);

        latchSpecimen.update(gamepad2.a);
        switchClaw.update(gamepad1.x);

        lock90 = gamepad1.dpad_up;
        lock180 = gamepad1.dpad_left;
        lock270 = gamepad1.dpad_down;
        lock360 = gamepad1.dpad_right;

        botToBaseState.update(gamepad1.b);
    }

    public void joystickUpdate() {
        drive = gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        turn = gamepad1.right_stick_x;
    }
}
