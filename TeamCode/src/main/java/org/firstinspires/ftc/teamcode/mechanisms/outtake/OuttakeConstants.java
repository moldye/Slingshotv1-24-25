package org.firstinspires.ftc.teamcode.mechanisms.outtake;

public class OuttakeConstants {
    // deposit: 0
    // tilt: .5477610423136522
    // transfer ready: .7231126346979301

    // transfer ready, tilt, deposit
    private static double[] bucketPositions = {.7231126346979301, .5477610423136522, 0};
    public enum SlidePositions {
        RETRACTED,
        LOW_BASKET, // probably could work for hang
        HIGH_BASKET,
        SPECIMEN_HIGH_RACK,
        BASE_STATE
    }

    public enum BucketPositions {
        TRANSFER_READY(bucketPositions[0]),
        TRANSFERING(bucketPositions[1]),
        DEPOSIT(bucketPositions[2]);

        private final double bucketPos;

        BucketPositions(double bucketPos) {
            this.bucketPos = bucketPos;
        }

        public double getBucketPos() {
            return bucketPos;
        }
    }

}
