import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;


public class MainTest {



    @org.junit.Test
    public void main() {
        add();
        helloWorld();
        PersonTest pt = new PersonTest();
        pt.getAge();
        pt.getName();
    }

    @Test
    void add() {
        Main m = new Main();
        assertEquals(3,m.add(1,2));
    }


    @Test
    void helloWorld() {
        Main m = new Main();
        assertEquals("Hello World!",m.helloWorld());
    }

}