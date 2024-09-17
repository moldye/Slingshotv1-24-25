package mechanismTests.intake;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.mechanisms.extendo.Intake;
import org.jetbrains.annotations.TestOnly;
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
    CRServo backRollerServo; // CR Servo?

    // one motor for rollers, one servo for pivot of intake, one servo for back roller
    // have an intaking, outtaking mode, and wrong alliance color outtake in back
    private Intake intake;
    @BeforeEach
    public void init() {
        intake = new Intake(DcMotorEx rollerMotor, Servo pivotAxon, Servo backRollerServo);
    }

    @Test
    public void testMotorDoesRollForward() {
        // not running constantly, block held in place by the back roller
        intake.forwardRollerOn();
        verify(rollerMotor).setPower(anyDouble());
    }

    @Test
    public void testMotorDoesRollBackward() {
        // TODO runs when outaking? may not need this, since the back roller outtakes
        intake.backwardRollerOn();
        verify(rollerMotor).setDirection(DcMotorSimple.Direction.REVERSE);
        verify(rollerMotor).setPower(anyDouble());
    }

    @Test
    public void testServoMovesBackRollerBackwards() {
        // servo should be set to backwards, only run when detects non-alliance colored block
        // should already be set to reverse
        intake.pushOutSample();
        verify(backRollerServo).setPower(anyDouble());
    }

    @Test
    public void testPivotServoCanChangePositionStates() {
        // get these from testing, and Souren?
        // axon so we can use getPosition()
        // TODO Make states first, this is really a hardware thing
    }

    @Test
    public void testPivotServoCanIncrementPos() {
        // same as above
    }
}
