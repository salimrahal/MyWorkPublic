/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import pack.Rotate;

/**
 *
 * @author salim
 */
public class RotateJUnitTestClass {

    public RotateJUnitTestClass() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }


    @Test
    public void testRotate() {
        assertArrayEquals("Array={1,2,3} ; rotation= 1 must be {3,1,2}", new Integer[]{3, 1, 2}, Rotate.rotateOriginal(new Integer[]{1, 2, 3}, 1));
        assertArrayEquals("Array={1,2,3,4,5,6,7} ; rotation= 3 must be {5, 6, 7, 1, 2, 3, 4}", new Integer[]{5, 6, 7, 1, 2, 3, 4}, Rotate.rotateOriginal(new Integer[]{1,2,3,4,5,6,7}, 3));
    }
}
