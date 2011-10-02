package com.daisyworks.temp;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * @author Troy T. Collinsworth
 * 
 *         <pre>
 * 
 * y = temp color
 * 
 * y = mx + b
 * 
 *      T2 - T1
 * m = --------
 *     1/4W - 0
 *     
 * T1 = (m x 0) + b
 * 
 * b = T1
 *     
 *      T2 - T1
 * y = -------- (x) + T1
 *     1/4W - 0
 * 
 * T1 = tank center (centerVal)
 * T2 = 1/4 tank (midVal)
 * 
 * T1 T2
 * == ==
 * 13  0
 * 12  1
 * 11  2
 * 10  3
 *  9  4
 *  8  5
 *  7  6
 *  
 *                 midVal - centerVal
 * unscaled val = -------------------- (x) + centerVal
 *                   (0.25)width
 *  
 *                        botVal - topVal
 *  cur center|mid val = ----------------- (x) + topVal
 *                          bucketSize
 * </pre>
 */
public class HeatMap {

    BufferedImage bi;

    // The scaling should be runtime adjustable
    // derived from min and max values to be scaled to color value range
    int minScalingValue = -190;
    int maxScalingValue = 8500;
    final double scaleValue = ColorScale.ColorValueRange / (double)(maxScalingValue - minScalingValue);

    int maxVal = Integer.MIN_VALUE;
    int minVal = Integer.MAX_VALUE;

    // TODO clean this up - make it testable
    public BufferedImage render(final Rectangle r, final int[] data, final int minScalingValue,
            final int maxScalingValue) {
        if (bi != null && bi.getWidth() == r.getWidth() && bi.getHeight() == r.getHeight()) {
            return bi;
        }

        bi = new BufferedImage(r.width, r.height, BufferedImage.TYPE_INT_RGB);

        this.minScalingValue = minScalingValue;
        this.maxScalingValue = maxScalingValue;

        // render top to bottom, center toward edge
        System.out.println("width " + bi.getWidth() + ", height " + bi.getHeight());
        final int startX = (int) (.5 * bi.getWidth());
        final RowEquation rowEquation = new RowEquation(bi.getWidth(), bi.getHeight(), data);
        for (int y = 0; y < bi.getHeight(); y++) {
            rowEquation.setY(y);
            // center to right - center to left rendered with mirror image
            int leftX = startX;
            for (int x = leftX; x < bi.getWidth(); x++) {
                // TODO clean this up - make it testable
                final int value = rowEquation.getValue(x - (bi.getWidth() / 2));
                final int scaledValue = scaleValue(value);
                final int color = ColorScale.getColor(scaledValue);
                bi.setRGB(x, y, color);
                --leftX;
                if (leftX >= 0) {
                    try {
                        bi.setRGB(leftX, y, color);
                    } catch (Exception e) {
                        System.out.println("x " + x + ", y " + y);
                        System.out.println("leftX " + leftX);
                    }
                }
            }
        }
        return bi;
    }

    /**
     * @param value
     * @return
     */
    private int scaleValue(final int value) {
        if (value > maxVal) {
            maxVal = value;
        }
        if (value < minVal) {
            minVal = value;
        }

        final int newVal = (int)(value * scaleValue);
        return newVal;
    }

    /**
     * <pre>
     *                        botVal - topVal
     *  cur center|mid val = ----------------- (x) + topVal
     *                          bucketSize
     * </pre>
     */
    static class RowEquation {

        final double widthConstant;

        final int yBucketSize;
        final int[] data;

        int curY = -1; // for diagnostics
        int curBucket = -1; // when y in new bucket, update variables
        int curBucketY = -1; // where within bucket - for diagnostics

        int topCenterVal; // update when y exceeds yBucketBottom
        int topMidVal;// update when y exceeds yBucketBottom
        int botCenterVal;// update when y exceeds yBucketBottom
        int botMidVal;// update when y exceeds yBucketBottom

        // interpolated/calculated vales for interim rows with no actual sample
        // data
        int curCenterVal; // update every y change
        int curMidVal; // update on y change

        public RowEquation(final int width, final int length, final int[] data) {
            widthConstant = (0.25) * width;
            yBucketSize = (int) (length / 6);
            this.data = data;
        }

        public void setY(int y) {
            curY = y;
            // if at beginning of bucket, use top
            // if between top and bottom, calc new current row
            // if at end of bucket, shift bottom to top and load new bottom

            final int bucket = y / yBucketSize;

            if (curBucket != bucket) {
                curBucket = bucket;
                topCenterVal = data[13 - bucket];
                topMidVal = data[bucket];

                if (bucket <= 5) {
                    botCenterVal = data[13 - 1 - bucket];
                    botMidVal = data[bucket + 1];
                } else {
                    botCenterVal = data[13 - bucket];
                    botMidVal = data[bucket];
                }
            }

            curBucketY = y % yBucketSize;
            // always interpolate values - the result is the same and quicker
            // most rows require interpolation, therefore testing for actual
            // values would be a greater burden
            curCenterVal = (int) ((((botCenterVal - topCenterVal) / (double) yBucketSize) * curBucketY) + topCenterVal);
            curMidVal = (int) (((((botMidVal - topMidVal)) / (double) yBucketSize) * curBucketY) + topMidVal);
        }

        public int getValue(final int xWithZeroAtCenter) {
            final int value = (int) ((((curMidVal - curCenterVal) / widthConstant) * xWithZeroAtCenter) + curCenterVal);
            return value;
        }

        @Override
        public String toString() {
            final StringBuilder b = new StringBuilder(1000);
            b.append("y=" + curY + ", yBucketSize=" + yBucketSize + ", bucketY=" + curBucketY + "\r\n");
            b.append("bucket=" + curBucket + ", dataLength=" + data.length + "\r\n");
            b.append(String.format("top c=%d, mid=%d\r\n", topCenterVal, topMidVal));
            b.append(String.format("bot c=%d, mid=%d\r\n", botCenterVal, botMidVal));
            b.append("cur center=" + curCenterVal + ", mid=" + curMidVal + "\r\n");
            b.append("--------------------");
            return b.toString();
        }
    }

    @Override
    public String toString() {
        return String.format("maxVal=%d, minVal=%d\r\n", maxVal, minVal);
    }
}
