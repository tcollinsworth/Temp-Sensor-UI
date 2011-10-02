package com.daisyworks.temp;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ColorScaleTest {

    @Test
    public void test() {
        assertEquals(0, ColorScale.getColor(-1));
        assertEquals(0, ColorScale.getColor(0));
        assertEquals(255, ColorScale.getColor(255));
        assertEquals(511, ColorScale.getColor(256));
        assertEquals(767, ColorScale.getColor(257));
        assertEquals(65535, ColorScale.getColor(510));
        assertEquals(65534, ColorScale.getColor(511));
        assertEquals(65533, ColorScale.getColor(512));
        assertEquals(65280, ColorScale.getColor(765));
        assertEquals(130816, ColorScale.getColor(766));
        assertEquals(196352, ColorScale.getColor(767));
        assertEquals(16776960, ColorScale.getColor(1020));
        assertEquals(16776704, ColorScale.getColor(1021));
        assertEquals(16776448, ColorScale.getColor(1022));
        assertEquals(16711936, ColorScale.getColor(1274));
        assertEquals(16711680, ColorScale.getColor(1275));
        assertEquals(16711680, ColorScale.getColor(1276));
    }
}
