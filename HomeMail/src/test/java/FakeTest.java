package test.java;


import static org.junit.Assert.*;

import main.common.Clap;
import org.junit.Test;

public class FakeTest {
    @Test
    public void test(){
        assertTrue(2==2);
        Clap c = new Clap();
        c.setWXID("123");
        c.setRID(456);
        assertTrue(c.getWXID().equals("123"));
    }
}
