package org.firstinspires.ftc.teamcode;

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

    // INTAKE (constructor when we have gamepads)
    public static Toggle switchExtendo; // extend & retract extendo

    // OUTTAKE
    public static Toggle outtakeSlides;
    public static Toggle bucketRelease;

    // SCORING
    public static Toggle latchSpecimen;
    public static Toggle switchClaw;


    public GamepadMapping(Gamepad gamepad1, Gamepad gamepad2) {
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;

        switchExtendo = new Toggle(false);

        outtakeSlides = new Toggle(false);
        bucketRelease = new Toggle(false);

        latchSpecimen = new Toggle(false);
        switchClaw = new Toggle(false);
    }

    public void update() {
        switchExtendo.update(gamepad1.left_bumper);

        outtakeSlides.update(gamepad1.right_bumper);
        bucketRelease.update(gamepad1.a);

        latchSpecimen.update(gamepad2.a);
        switchClaw.update(gamepad1.x);
    }
}
