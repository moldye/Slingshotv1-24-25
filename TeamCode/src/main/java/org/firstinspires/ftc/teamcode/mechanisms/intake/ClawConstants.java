package org.firstinspires.ftc.teamcode.mechanisms.intake;

public class ClawConstants {
    // idle, transfer, hover, picking up
    private static final double[] clawPositions = {1, 1, 1, 1};

    // idle, transfer, hover, picking up
    private static final double[] wristPositions = {1, 1, 1, 1};

    // idle, transfer, hover, picking up
    private static final double[] v4bPositions = {1, 1, 1, 1};

    public enum ClawStates {
        BASE_STATE(clawPositions[0], wristPositions[0], v4bPositions[0]),
        TRANSFER(clawPositions[1], wristPositions[1], v4bPositions[1]),
        HOVER_OVER_SAMPLES(clawPositions[2], wristPositions[2], v4bPositions[2]),
        PICKING_UP_SAMPLE(clawPositions[3], wristPositions[3], v4bPositions[3]);

        private final double clawPos;
        private final double wristPos;
        private final double v4bPos;

        ClawStates(double clawPos, double wristPos, double v4bPos) {
            this.clawPos = clawPos;
            this.wristPos = wristPos;
            this.v4bPos = v4bPos;
        }

        public double getClawPos() { return clawPos; }
        public double getWristPos() { return wristPos; }
        public double getV4bPos() { return v4bPos; }
    }
}
