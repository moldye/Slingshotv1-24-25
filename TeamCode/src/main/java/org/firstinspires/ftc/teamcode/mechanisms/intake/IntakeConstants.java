package org.firstinspires.ftc.teamcode.mechanisms.intake;

public class IntakeConstants {

    // pivoted up, pivoted down, transfer pos, pivot down initial
    // axon programmed for 0-255, 66 PMW
    private static final double[] pivotPositions = {0, .92, .2};

    // neutral pos, back roller push out sample (extendo), back roller transfer
    private static final double[] backRollerPositions = {0.5, 1, -1};

    // right linkage in, right linkage extended
    // axon programmed for 0-255, 66 PMW
    private static final double[] rightLinkagePositions = {.325, -1};

    // left linkage in, left linkage extended
    // axon programmed for 0-255, 66 PMW
    private static final double[] leftLinkagePositions = {.325, -1};

    public enum IntakeState {
        FULLY_RETRACTED(pivotPositions[0], backRollerPositions[0], rightLinkagePositions[0], leftLinkagePositions[0]), // pivoted up, idle back roller, retracted
        EXTENDING(),
        INTAKING(pivotPositions[1], backRollerPositions[0], rightLinkagePositions[1], leftLinkagePositions[1]), // pivoted down, idle back roller, extended
        RETRACTING(),
        WRONG_ALLIANCE_COLOR_SAMPLE(pivotPositions[1], backRollerPositions[1], rightLinkagePositions[1], leftLinkagePositions[1]), // pivoted down, pushing out sample, extended
        FULLY_EXTENDED(pivotPositions[1], rightLinkagePositions[1], backRollerPositions[0], leftLinkagePositions[1]), // pivoted down, idle back roller, extended
        TRANSFER(pivotPositions[0], rightLinkagePositions[0], backRollerPositions[2], leftLinkagePositions[0]), // pivoted up, back roller push, retracted
        BASE_STATE(pivotPositions[0], rightLinkagePositions[0], backRollerPositions[0], leftLinkagePositions[0]); // we're trying this, hopefully the same state in each mechanism that resets that specific mechanism (keyed to same button across robot)

        private final double pivotPos;
        private final double rLinkagePos;
        private final double backRollerPos;
        private final double lLinkagePos;

        IntakeState(double pivotPos, double backRollerPos, double rLinkagePos, double lLinkagePos) {
            this.pivotPos = pivotPos;
            this.backRollerPos = backRollerPos;
            this.rLinkagePos = rLinkagePos;
            this.lLinkagePos = lLinkagePos;
        }

        IntakeState() {
            this.pivotPos = 0;
            this.backRollerPos = 0;
            this.rLinkagePos = 0;
            this.lLinkagePos = 0;
        }

        public double pivotPos() { return pivotPos; }
        public double backRollerPos() { return backRollerPos;}
        public double rLinkagePos() { return rLinkagePos; }
        public double lLinkagePos() { return lLinkagePos; }

    }
}
