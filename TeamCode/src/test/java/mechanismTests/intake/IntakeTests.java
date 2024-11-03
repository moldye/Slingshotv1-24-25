package mechanismTests.intake;

import static org.mockito.Mockito.*;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.mechanisms.intake.Intake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class IntakeTests {

    @Mock
    DcMotorEx rollerMotor;
    @Mock
    Servo pivotAxon;
    @Mock
    Servo backRollerServo; // CR Servo?

    // extendo needs to retract out and back in, test full extension
    // likely going to need to be able to adjust slides by little increments
    // extendo slides powered by a servo, triangle things attach to slides for support & servo moves that
    @Mock
    Servo rightExtendo;
    @Mock
    Servo leftExtendo;

    // one motor for rollers, one servo for pivot of intake, one servo for back roller
    // have an intaking, outtaking mode, and wrong alliance color outtake in back
    private Intake intake;

    @BeforeEach
    public void init() {
        intake = new Intake(rollerMotor, pivotAxon, backRollerServo, rightExtendo, leftExtendo);
    }

    @Test
    public void testMotorDoesRollForward() {
        // not running constantly, block held in place by the back roller
        intake.activeIntake.motorRollerOnToIntake();
        verify(rollerMotor).setPower(anyDouble());
    }

//    @Test
//    public void testServoMovesBackRollerBackwards() {
//        // servo should be set to backwards, only run when detects non-alliance colored block
//        // should already be set to reverse
//        intake.activeIntake.pushOutSample();
//        verify(backRollerServo).setPosition(anyDouble());
//    }

    @Test
    public void testPivotServoGoesToFullPos() {
        intake.activeIntake.flipDownFull();
        verify(pivotAxon).setPosition(anyDouble()); // tune with value
    }

    @Test
    public void testPivotServoGoesTotallyIn() {
        intake.activeIntake.flipUp();
        verify(pivotAxon).setPosition(anyDouble()); // tune with value
    }

    @Test
    public void resetIntakeHardware() {
        // needs to happen when we return to base state (remember there can be no moving in the transition from auton to teleop)
        intake.resetHardware();
        verify(rollerMotor).setPower(0);
        verify(rollerMotor).setDirection(DcMotorSimple.Direction.FORWARD);

        verify(pivotAxon).setPosition(0);

        // verify(backRollerServo).setPosition(0.5);
    }

    @Test
    public void testExtendoFullExpansion() {
        // TODO get this from hardware/CAD when done
        intake.extendoFullExtend();
        verify(leftExtendo).setPosition(anyDouble());
        verify(rightExtendo).setPosition(anyDouble());
    }

    @Test
    public void testExtendoFullyRetract() {
        intake.extendoFullRetract();
        verify(leftExtendo).setPosition(anyDouble());
        verify(rightExtendo).setPosition(anyDouble());
    }


}