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
    public static Toggle clearIntake;
    public static boolean pivotState; // may need to change this
    public static Toggle transfer;

    // OUTTAKE
    // --------------
    public static Toggle bucketDeposit;
    public static Toggle highBasket;
    public static Toggle lowBasket;
    public static Toggle readyForDeposit;

    // SCORING
    // --------------
    public static Toggle latchSpecimen;
    public static Toggle switchClaw;
    public static Toggle L1hang;

    // LOCKED HEADING
    // -----------------
    public static Toggle toggleLockedHeading;
    public static boolean lock90 = false;
    public static boolean lock180 = false;
    public static boolean lock270 = false;
    public static boolean lock360 = false;

    // OTHER
    // --------------
    public static Toggle botToBaseState;

    // TESTING BUTTONS
    // NOT TO BE USED FOR COMP
    // -------------------------------
    public static Toggle toggleIntakePower;
    public static Toggle powerIntake;
    public static Toggle switchExtendo;
    // public static Toggle transfer;
    public static Toggle deposit;
    public static Toggle flipBucket;
    public static Toggle pivot;

    public GamepadMapping(Gamepad gamepad1, Gamepad gamepad2) {
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;

        extend = new Toggle(false);
        retract = new Toggle(false);
        readyForDeposit = new Toggle(false);


        bucketDeposit = new Toggle(false);
        highBasket = new Toggle(false);
        lowBasket = new Toggle(false);

//        latchSpecimen = new Toggle(false);
//        switchClaw = new Toggle(false);

        botToBaseState = new Toggle(false);

        // TESTING BUTTONS
        toggleIntakePower = new Toggle(false);
        pivot = new Toggle(false);
        powerIntake = new Toggle(false);
        transfer = new Toggle(false);
        deposit = new Toggle(false);
        flipBucket = new Toggle(false);
    }

    public void update() {
        joystickUpdate();

        // Intake
        //extend.update(gamepad1.right_bumper);
        retract.update(gamepad1.left_bumper);

        // Outtake (All Gamepad2)
        lowBasket.update(gamepad2.right_bumper);
        highBasket.update(gamepad2.left_bumper);
        bucketDeposit.update(gamepad2.a);

        // Specimen
//        latchSpecimen.update(gamepad2.a);
//        switchClaw.update(gamepad1.x);

        // Locked Heading
        lock90 = gamepad1.dpad_up;
        lock180 = gamepad1.dpad_left;
        lock270 = gamepad1.dpad_down;
        lock360 = gamepad1.dpad_right;

        // Reset/Fail Safes (Both controllers should have these)
        botToBaseState.update(gamepad1.b);
        botToBaseState.update(gamepad2.b);

        clearIntake.update(gamepad1.left_trigger > 0.6);
        clearIntake.update(gamepad2.left_trigger > 0.6);
    }

    public void joystickUpdate() {
        drive = gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        turn = gamepad1.right_stick_x;
    }
}
