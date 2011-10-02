package com.daisyworks.temp;

/**
 * <pre>
 * RGB
 * +-- Yellow - Red    (1021-1275)+255
 * ++- Green  - Yellow ( 766-1020)+255
 * -+- LtBlue - Green  ( 511- 765)+255
 * -++ Blue   - LtBlue ( 256- 510)+255
 * --+ Black  - Blue   (   0- 255)
 * --- Black 0
 * 
 * 1021-1275 = 16776704 - 16711680 yellow to red    (-256)
 *  766-1020 =   130816 - 16776960 green to yellow  (+65536)
 *  511- 765 =    65534 - 65281    lt blue to green (-1)
 *  256- 510 =      511 - 65535    blue to lt blue  (+256)
 *    0- 255 =        0 - 255      black to blue    (+1)
 * </pre>
 * 
 * @author Troy T. Collinsworth
 */
public class ColorScale {

    public static final int ColorValueRange = 1275;
    
    private static final ColorStateStrategy[] strategy = { new BlackBlueStrategy(), new BlueLtBlueStrategy(),
            new LtBlueGreenStrategy(), new GreenYellowStrategy(), new YellowRedStrategy() };

    /**
     * Computes a color from a value between 0 and 1275.
     * 
     * @param value
     *            between 0 and 1275, less than zero returns black, greater than
     *            1275 returns white
     * @return color value between black and white
     */
    public static int getColor(final int value) {
        if (value <= 0) {
            return 0;
        }
        final int strategyIndex = ((value - 1) / 255);
        if (strategyIndex <= 4) {
            final int color = strategy[strategyIndex].computeColor(value);
            return color;
        }
        // value > 1275
        return 16711680;
    }

    private static interface ColorStateStrategy {

        int computeColor(int value);
    }

    private static class BlackBlueStrategy implements ColorStateStrategy {

        @Override
        public int computeColor(final int value) {
            // step = +1;
            return value;
        }
    }

    private static class BlueLtBlueStrategy implements ColorStateStrategy {

        @Override
        public int computeColor(final int value) {
            // step = +256;
            // subtract low end and multiply by step
            final int color = (value - 255) * 256 + 255;
            return color;
        }
    }

    private static class LtBlueGreenStrategy implements ColorStateStrategy {

        @Override
        public int computeColor(final int value) {
            // step = -1;
            // subtract low end and multiply by step, then add to high end
            final int color = (value - 510) * -1 + 65535;
            return color;
        }
    }

    private static class GreenYellowStrategy implements ColorStateStrategy {

        @Override
        public int computeColor(final int value) {
            // step = +65536;
            // subtract low end and multiply by step, then add to low end
            final int color = (value - 766) * 65536 + 130816;
            return color;
        }
    }

    private static class YellowRedStrategy implements ColorStateStrategy {

        @Override
        public int computeColor(final int value) {
            if (value >= 1275) {
                return 16711680;
            }
            // step = -256;
            // subtract low end and multiply by step, then add to high end
            final int color = (value - 1021) * -256 + 16776704;

            return color;
        }
    }
}
