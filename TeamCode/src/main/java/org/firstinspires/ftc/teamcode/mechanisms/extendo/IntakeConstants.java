package org.firstinspires.ftc.teamcode.mechanisms.extendo;

public class IntakeConstants {
    public enum IntakeStates {
        FULLY_RETRACTED, // add the servo pos values for these, have fancy enums like alex bui :)
        EXTENDING,
        INTAKING,
        RETRACTING,
        WRONG_ALLIANCE_COLOR_SAMPLE,
        FULLY_EXTENDED,
        TRANSFER,
        BASE_STATE // we're trying this, hopefully the same state in each mechanism that resets that specific mechanism (keyed to same button across robot)
    }
}
