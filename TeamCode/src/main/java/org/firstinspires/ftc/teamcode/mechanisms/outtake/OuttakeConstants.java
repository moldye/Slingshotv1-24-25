package org.firstinspires.ftc.teamcode.mechanisms.outtake;

public class OuttakeConstants {
    private static double[] bucketPositions = {0, 1}; // tune (REMEMBER: 0.5 is NEUTRAL)
    public enum SlidePositions {
        RETRACTED,
        LOW_BASKET, // probably could work for hang
        HIGH_BASKET,
        SPECIMEN_HIGH_RACK,
        BASE_STATE
    }

    public enum BucketPositions {
        TRANSFER_READY(bucketPositions[0]),
        TRANSFERING(),
        DEPOSIT(bucketPositions[1]);

        private final double bucketPos;

        BucketPositions(double bucketPos) {
            this.bucketPos = bucketPos;
        }

        BucketPositions() {
            this.bucketPos = 0;
        }

        public double getBucketPos() {
            return bucketPos;
        }
    }

}
