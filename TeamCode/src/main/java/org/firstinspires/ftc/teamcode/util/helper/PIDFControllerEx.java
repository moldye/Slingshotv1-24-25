package org.firstinspires.ftc.teamcode.util.helper;

import com.arcrobotics.ftclib.controller.PIDFController;

public class PIDFControllerEx extends PIDFController {
    public PIDFControllerEx(double kp, double ki, double kd, double kf) {
        super(kp, ki, kd, kf);
    }

    @Override
    public double calculate(double error) {
        // we did this to add the ability to control the state of our error so heading lock would work
        super.setSetPoint(0);
        return super.calculate(-error);
    }
}
