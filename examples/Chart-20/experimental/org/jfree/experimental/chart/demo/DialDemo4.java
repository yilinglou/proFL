/* --------------
 * DialDemo4.java
 * --------------
 * (C) Copyright 2006, 2007, by Object Refinery Limited.
 */

package org.jfree.experimental.chart.demo;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.util.GradientPaintTransformType;
import org.jfree.chart.util.StandardGradientPaintTransformer;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.experimental.chart.plot.dial.DialBackground;
import org.jfree.experimental.chart.plot.dial.DialPlot;
import org.jfree.experimental.chart.plot.dial.DialPointer;
import org.jfree.experimental.chart.plot.dial.StandardDialFrame;
import org.jfree.experimental.chart.plot.dial.StandardDialScale;

/**
 * A sample application showing the use of a {@link DialPlot}.
 */
public class DialDemo4 extends JFrame implements ChangeListener {
    
    /** A slider to update the dataset value. */
    JSlider slider;
    
    /** The dataset. */
    DefaultValueDataset dataset;
    
    /** 
     * Creates a new instance.
     * 
     * @param title  the frame title.
     */
    public DialDemo4(String title) {
        super(title);
        
        this.dataset = new DefaultValueDataset(50);
        // get data for diagrams
        DialPlot plot = new DialPlot();
        plot.setView(0.78, 0.37, 0.22, 0.26);
        plot.setDataset(this.dataset);
        
        StandardDialFrame dialFrame = new StandardDialFrame(-10.0, 20.0);
        dialFrame.setInnerRadius(0.70);
        dialFrame.setOuterRadius(0.90);
        dialFrame.setForegroundPaint(Color.darkGray);
        dialFrame.setStroke(new BasicStroke(3.0f));
        plot.setDialFrame(dialFrame);
        
        GradientPaint gp = new GradientPaint(new Point(), 
                new Color(255, 255, 255), new Point(), 
                new Color(240, 240, 240));
        DialBackground sdb = new DialBackground(gp);
        sdb.setGradientPaintTransformer(new StandardGradientPaintTransformer(
                GradientPaintTransformType.VERTICAL));
        plot.addLayer(sdb);
        
        StandardDialScale scale = new StandardDialScale(0, 100, -8, 16.0);
        scale.setTickRadius(0.82);
        scale.setTickLabelOffset(-0.04);
        scale.setMajorTickIncrement(25.0);
        scale.setTickLabelFont(new Font("Dialog", Font.PLAIN, 14));
        
        plot.addScale(0, scale);
        
        DialPointer needle = new DialPointer.Pin();
        needle.setRadius(0.84);
        plot.addLayer(needle);
        JFreeChart chart1 = new JFreeChart(plot);
        chart1.setTitle("Dial Demo 4");
        ChartPanel cp1 = new ChartPanel(chart1);

        cp1.setPreferredSize(new Dimension(400, 250));
        this.slider = new JSlider(0, 100);
        this.slider.setMajorTickSpacing(10);
        this.slider.setPaintLabels(true);
        this.slider.addChangeListener(this);
        JPanel content = new JPanel(new BorderLayout());
        content.add(cp1);
        content.add(this.slider, BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(content);

    }
    
    /**
     * Handle a change in the slider by updating the dataset value.  This
     * automatically triggers a chart repaint.
     * 
     * @param e  the event.
     */
    public void stateChanged(ChangeEvent e) {
        this.dataset.setValue(new Integer(this.slider.getValue()));
    }

    /**
     * Starting point for the demo application.
     * 
     * @param args  ignored.
     */
    public static void main(String[] args) {

        DialDemo4 app = new DialDemo4("JFreeChart - Demo Dial 4");
        app.pack();
        app.setVisible(true);

    }
}
