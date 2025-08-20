import org.junit.Test;

import static fr.razi.box.BoxManager.indexToSpiral;
import fr.razi.box.boxutils.Coordinates2D;

import static org.junit.Assert.assertEquals;

public class SpiralTest {

    @Test
    public void testIndexToSpiral(){
        assertEquals(new Coordinates2D(0, 0), indexToSpiral(0));
        assertEquals(new Coordinates2D(1, 0), indexToSpiral(1));
        assertEquals(new Coordinates2D(1, 1), indexToSpiral(2));
        assertEquals(new Coordinates2D(0, 1), indexToSpiral(3));
        assertEquals(new Coordinates2D(-1, 1), indexToSpiral(4));
        assertEquals(new Coordinates2D(2, -1), indexToSpiral(9));
    }

}
