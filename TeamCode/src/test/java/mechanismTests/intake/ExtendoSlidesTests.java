package mechanismTests.intake;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.jetbrains.annotations.TestOnly;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ExtendoSlidesTests {
    // extendo needs to retract out and back in, test full extension
    // likely going to need to be able to adjust slides by little increments
    // extendo slides powered by a servo, triangle things attach to slides for support & servo moves that
    @Mock
    Servo rightExtendo;

    // may need this? See when cad is done
    // @Mock
    // Servo leftExtendo;

    @Test
    public void testExtendoFullExpansion() {
        // TODO get this from hardware/CAD when done
    }

    @Test
    public void testExtendoCanSwitchStates() {
        // Make states, full extension, locked, etc.
    }

    @Test
    public void testExtendoFullyRetract() {
        // see above
    }

    @Test
    public void testExtendoLocks() {
        // this will just set target pos for the servo to be at the retraction position (test for this too)
    }
}
