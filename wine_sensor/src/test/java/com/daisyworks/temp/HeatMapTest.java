package com.daisyworks.temp;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class HeatMapTest {

    int[] data = { 600, 300, 0, -300, -600, -300, 0, 0, 0, -300, 0, 300, 600, 900 };
    HeatMap.RowEquation e;

    @Before
    public void setUp() throws Exception {
        e = new HeatMap.RowEquation(8, 13, data);
    }

    @Test
    public void testRender0() {
        e.setY(0);
        System.out.println(e.toString());

        assertEquals(0, e.curY);
        assertEquals(0, e.curBucket);
        assertEquals(0, e.curBucketY);
        assertEquals(2, e.yBucketSize);

        assertEquals(900, e.topCenterVal);
        assertEquals(600, e.topMidVal);
        assertEquals(600, e.botCenterVal);
        assertEquals(300, e.botMidVal);

        assertEquals(900, e.curCenterVal);
        assertEquals(600, e.curMidVal);

        assertEquals(900, e.getValue(0));
        assertEquals(750, e.getValue(1));
        assertEquals(600, e.getValue(2));
        assertEquals(450, e.getValue(3));
    }

    @Test
    public void testRender1() {
        e.setY(1);
        System.out.println(e.toString());

        assertEquals(1, e.curY);
        assertEquals(0, e.curBucket);
        assertEquals(1, e.curBucketY);
        assertEquals(2, e.yBucketSize);

        assertEquals(900, e.topCenterVal);
        assertEquals(600, e.topMidVal);
        assertEquals(600, e.botCenterVal);
        assertEquals(300, e.botMidVal);

        assertEquals(750, e.curCenterVal);
        assertEquals(450, e.curMidVal);

        assertEquals(750, e.getValue(0));
        assertEquals(600, e.getValue(1));
        assertEquals(450, e.getValue(2));
        assertEquals(300, e.getValue(3));
    }

    @Test
    public void testRender2() {
        e.setY(2);
        System.out.println(e.toString());

        assertEquals(2, e.curY);
        assertEquals(1, e.curBucket);
        assertEquals(0, e.curBucketY);
        assertEquals(2, e.yBucketSize);

        assertEquals(600, e.topCenterVal);
        assertEquals(300, e.topMidVal);
        assertEquals(300, e.botCenterVal);
        assertEquals(0, e.botMidVal);

        assertEquals(600, e.curCenterVal);
        assertEquals(300, e.curMidVal);

        assertEquals(600, e.getValue(0));
        assertEquals(450, e.getValue(1));
        assertEquals(300, e.getValue(2));
        assertEquals(150, e.getValue(3));
    }

    @Test
    public void testRender3() {
        e.setY(3);
        System.out.println(e.toString());

        assertEquals(3, e.curY);
        assertEquals(1, e.curBucket);
        assertEquals(1, e.curBucketY);
        assertEquals(2, e.yBucketSize);

        assertEquals(600, e.topCenterVal);
        assertEquals(300, e.topMidVal);
        assertEquals(300, e.botCenterVal);
        assertEquals(0, e.botMidVal);

        assertEquals(450, e.curCenterVal);
        assertEquals(150, e.curMidVal);

        assertEquals(450, e.getValue(0));
        assertEquals(300, e.getValue(1));
        assertEquals(150, e.getValue(2));
        assertEquals(0, e.getValue(3));
    }

    @Test
    public void testRender11() {
        e.setY(11);
        System.out.println(e.toString());

        assertEquals(11, e.curY);
        assertEquals(5, e.curBucket);
        assertEquals(1, e.curBucketY);
        assertEquals(2, e.yBucketSize);

        assertEquals(0, e.topCenterVal);
        assertEquals(-300, e.topMidVal);
        assertEquals(0, e.botCenterVal);
        assertEquals(0, e.botMidVal);

        assertEquals(0, e.curCenterVal);
        assertEquals(-150, e.curMidVal);

        assertEquals(0, e.getValue(0));
        assertEquals(-75, e.getValue(1));
        assertEquals(-150, e.getValue(2));
        assertEquals(-225, e.getValue(3));
    }

    @Test
    public void testRender12() {
        e.setY(12);
        System.out.println(e.toString());

        assertEquals(12, e.curY);
        assertEquals(6, e.curBucket);
        assertEquals(0, e.curBucketY);
        assertEquals(2, e.yBucketSize);

        assertEquals(0, e.topCenterVal);
        assertEquals(0, e.topMidVal);
        assertEquals(0, e.botCenterVal);
        assertEquals(0, e.botMidVal);

        assertEquals(0, e.curCenterVal);
        assertEquals(0, e.curMidVal);

        assertEquals(0, e.getValue(0));
        assertEquals(0, e.getValue(1));
        assertEquals(0, e.getValue(2));
        assertEquals(0, e.getValue(3));
    }

    @Test
    public void testRender13() {
        e.setY(13);
        System.out.println(e.toString());

        assertEquals(13, e.curY);
        assertEquals(6, e.curBucket);
        assertEquals(1, e.curBucketY);
        assertEquals(2, e.yBucketSize);

        assertEquals(0, e.topCenterVal);
        assertEquals(0, e.topMidVal);
        assertEquals(0, e.botCenterVal);
        assertEquals(0, e.botMidVal);

        assertEquals(0, e.curCenterVal);
        assertEquals(0, e.curMidVal);

        assertEquals(0, e.getValue(0));
        assertEquals(0, e.getValue(1));
        assertEquals(0, e.getValue(2));
        assertEquals(0, e.getValue(3));
    }
}
