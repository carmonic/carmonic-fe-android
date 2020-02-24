package com.camsys.carmonic;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ComputationTest {

    Computation computation =  new Computation();

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void sum() {
        assertEquals(4,computation.Sum(2,2));
    }

    @Test
    public void multiply() {
        assertEquals(6,computation.Multiply(2,3));
    }
}