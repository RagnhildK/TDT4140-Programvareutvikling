/*import org.junit.jupiter.api.Test;
 */

import static org.junit.Assert.*;


public class MainTest {



    @org.junit.Test
    public void main() {


        add();
    }

    @org.junit.Test
    public void add() {
        Main m = new Main();
        assertEquals(3,m.add(1,2));
        //fnrfrf
    }
    /*

    @org.junit.Test
    public void helloWorld() {
        Main m = new Main();
        assertEquals("Hello World!",m.helloWorld());
    }
    */
}