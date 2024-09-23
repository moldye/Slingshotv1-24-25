package org.firstinspires.ftc.teamcode.mechanisms;

public class TeleopFSM {






    public enum TeleopStates {
        BASE_STATE,
        INTAKING,
        SCORING // call both outtake fsm and specimen claw when we have it
    }
}
