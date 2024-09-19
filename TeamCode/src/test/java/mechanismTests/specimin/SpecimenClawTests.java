package mechanismTests.specimin;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.mechanisms.specimen.SpecimenClaw;
import org.jetbrains.annotations.TestOnly;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class SpecimenClawTests {
    @Mock
    Servo specimenClawServo;
    // specimen claw powered by a servo, opens and closes (only vertical specimens)
    // if so, two states for servo position, closed & vertical
    // okay, so since specimens need to be clipped on with some force, we probably need the claw to be able to move up
    // with the slides to move the specimen down
    private SpecimenClaw claw;

    @BeforeEach
    public void init() {
        claw = new SpecimenClaw(specimenClawServo);
    }

    @Test
    public void testSpecimenClawCanClose() {
        // this will set target pos of servo to whatever lock pos is, likely 0 (this servo probably won't be an axon)
        claw.closeClaw();
        verify(specimenClawServo).setPosition(anyDouble());
    }

    @Test
    public void testSpecimenClawCanChangeStates() {
        // make states first, then come here (this will be in fsm)
    }

    @Test
    public void testSpecimenClawCanGoToSpecimenPos() {
        // once again, test this once CAD's done
        claw.openClaw();
        verify(specimenClawServo).setPosition(anyDouble());
    }
}
