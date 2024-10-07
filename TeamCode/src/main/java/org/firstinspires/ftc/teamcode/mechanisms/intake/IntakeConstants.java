package org.firstinspires.ftc.teamcode.mechanisms.intake;

public class IntakeConstants {

    // pivoted up, pivoted down
    // axon programmed for 0-255, 66 PMW
    private static final double[] pivotPositions = {0, 1};

    // idle back roller, back roller push out
    // don't think we need this, CR Servo works fine
    // private static final double[] backRollerPositions = {0.25, 1};

    // right linkage in, right linkage extended
    // axon programmed for 0-255, 66 PMW
    private static final double[] rightLinkagePositions = {.325, -1};

    // left linkage in, left linkage extended
    // axon programmed for 0-255, 66 PMW
    private static final double[] leftLinkagePositions = {.325, -1};

    public enum IntakeState {
        FULLY_RETRACTED(pivotPositions[0], rightLinkagePositions[0], leftLinkagePositions[0]), // pivoted up, idle back roller, retracted
        EXTENDING(),
        INTAKING(pivotPositions[1], rightLinkagePositions[1], leftLinkagePositions[1]), // pivoted down, idle back roller, extended
        RETRACTING(),
        WRONG_ALLIANCE_COLOR_SAMPLE(pivotPositions[1], rightLinkagePositions[1], leftLinkagePositions[1]), // pivoted down, pushing out sample, extended
        FULLY_EXTENDED(pivotPositions[1], rightLinkagePositions[1], leftLinkagePositions[1]), // pivoted down, idle back roller, extended
        TRANSFER(pivotPositions[0], rightLinkagePositions[0], leftLinkagePositions[0]), // pivoted up, back roller push, retracted
        BASE_STATE(pivotPositions[0], rightLinkagePositions[0], leftLinkagePositions[0]); // we're trying this, hopefully the same state in each mechanism that resets that specific mechanism (keyed to same button across robot)

        private final double pivotPos;
        private final double rLinkagePos;
        private final double lLinkagePos;

        IntakeState(double pivotPos, double rLinkagePos, double lLinkagePos) {
            this.pivotPos = pivotPos;
            this.rLinkagePos = rLinkagePos;
            this.lLinkagePos = lLinkagePos;
        }

        IntakeState() {
            this.pivotPos = 0;
            this.rLinkagePos = 0;
            this.lLinkagePos = 0;
        }

        public double pivotPos() { return pivotPos; }
        public double rLinkagePos() { return rLinkagePos; }
        public double lLinkagePos() { return lLinkagePos; }

        public void setExtendLinkagePositions(double newRLinkagePos, double newLLinkagePos) {
            rightLinkagePositions[1] = newRLinkagePos;
            leftLinkagePositions[1] = newLLinkagePos;
        }

        public void setRetractLinkagePositions(double newRLinkagePos, double newLLinkagePos) {
            rightLinkagePositions[0] = newRLinkagePos;
            leftLinkagePositions[0] = newLLinkagePos;
        }


    }
}
