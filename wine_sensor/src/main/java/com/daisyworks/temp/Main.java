package com.daisyworks.temp;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * <pre>
 *    0    0
 * 2250 4250
 * 4250 8500
 * 4150 8500
 * 4000 7500
 * 2000 3000
 * 1000 2500
 * </pre>
 * 
 * TODO groom raw data
 * TODO auto scaling
 * TODO user scaling hi/low
 * TODO temp key
 * TODO tooltip
 * 
 * @author Troy T. Collinsworth
 * 
 */
public class Main extends JFrame {

    private static final long serialVersionUID = 1L;
    static Main m;
    double[] rawTemp = { 0, 22.5, 42.5, 41.5, 40, 20, 10, 25, 30, 75, 85, 85, 42.5, 0 };
    int[] temp = new int[rawTemp.length];
    
    // TODO should scan file for min and max
    // TODO should allow user to adjust real time
    int minScaleTemp = -1000;
    int maxScaleTemp = 5000;

    public static void main(final String[] args) {
        m = new Main();
        m.setDefaultCloseOperation(EXIT_ON_CLOSE);
        Main.thisMain(args);
    }

    public static void thisMain(final String[] args) {
        m.convertTemps();
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                m.setSize(750, 500);
                m.getContentPane().add(m.new Panel());
                m.setVisible(true);
            }
        });
    }

    void convertTemps() {
        for (int i = 0; i < rawTemp.length; i++) {
            temp[i] = (int) (rawTemp[i] * 100);
        }
    }

    class Panel extends JPanel {

        private static final long serialVersionUID = 1L;

        final HeatMap hm = new HeatMap();

        @Override
        public void paint(final Graphics g) {

            final Graphics2D g2 = (Graphics2D) g;
            g2.drawImage(hm.render(this.getBounds(), temp, minScaleTemp, maxScaleTemp), 0, 0, null);
            System.out.println(hm.toString());
            // g2.drawImage(this.render(), 0, 0, null);
        }

        // /**
        // * Gradient: Upper left black to lower right red
        // */
        // public BufferedImage render() {
        // final BufferedImage bi = new BufferedImage(this.getWidth(),
        // this.getHeight(), BufferedImage.TYPE_INT_RGB);
        // double scale = 1275d / (bi.getWidth() + bi.getHeight());
        // for (int x = 0; x < bi.getWidth(); x++) {
        // for (int y = 0; y < bi.getHeight(); y++) {
        // bi.setRGB(x, y, ColorScale.getColor((int) ((x + y) * scale)));
        // }
        // }
        // return bi;
        // }
    }
}
