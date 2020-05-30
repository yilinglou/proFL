/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2007, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 *
 * This library is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by 
 * the Free Software Foundation; either version 2.1 of the License, or 
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, 
 * USA.  
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc. 
 * in the United States and other countries.]
 *
 * ----------------------
 * CategoryPlotTests.java
 * ----------------------
 * (C) Copyright 2003-2007, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: CategoryPlotTests.java,v 1.1.2.4 2007/03/23 14:48:12 mungady Exp $
 *
 * Changes
 * -------
 * 26-Mar-2003 : Version 1 (DG);
 * 15-Sep-2003 : Added a unit test to reproduce a bug in serialization (now 
 *               fixed) (DG);
 * 05-Feb-2007 : Added testAddDomainMarker() and testAddRangeMarker() (DG);
 * 07-Feb-2007 : Added test1654215() (DG);
 * 20-Jun-2007 : Removed JCommon dependencies (DG);
 * 
 */

package org.jfree.chart.plot.junit;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.CategoryTextAnnotation;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.AxisSpace;
import org.jfree.chart.axis.CategoryAnchor;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.event.MarkerChangeListener;
import org.jfree.chart.plot.CategoryMarker;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.renderer.category.AreaRenderer;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.util.Layer;
import org.jfree.chart.util.RectangleInsets;
import org.jfree.chart.util.SortOrder;
import org.jfree.data.Range;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Tests for the {@link CategoryPlot} class.
 */
public class CategoryPlotTests extends TestCase {

    /**
     * Returns the tests as a test suite.
     *
     * @return The test suite.
     */
    public static Test suite() {
        return new TestSuite(CategoryPlotTests.class);
    }

    /**
     * Constructs a new set of tests.
     *
     * @param name  the name of the tests.
     */
    public CategoryPlotTests(String name) {
        super(name);
    }

    /**
     * A test for a bug reported in the forum.
     */
    public void testAxisRange() {
        DefaultCategoryDataset datasetA = new DefaultCategoryDataset();
        DefaultCategoryDataset datasetB = new DefaultCategoryDataset();
        datasetB.addValue(50.0, "R1", "C1");
        datasetB.addValue(80.0, "R1", "C1");
        CategoryPlot plot = new CategoryPlot(datasetA, new CategoryAxis(null), 
                new NumberAxis(null), new LineAndShapeRenderer());
        plot.setDataset(1, datasetB);
        plot.setRenderer(1, new LineAndShapeRenderer());
        Range r = plot.getRangeAxis().getRange();
        assertEquals(84.0, r.getUpperBound(), 0.00001);
    }
    
    /**
     * Test that the equals() method differentiates all the required fields.
     */
    public void testEquals() {
        
        CategoryPlot plot1 = new CategoryPlot();
        CategoryPlot plot2 = new CategoryPlot();
        assertTrue(plot1.equals(plot2));    
        assertTrue(plot2.equals(plot1));
        
        // orientation...
        plot1.setOrientation(PlotOrientation.HORIZONTAL);
        assertFalse(plot1.equals(plot2));
        plot2.setOrientation(PlotOrientation.HORIZONTAL);
        assertTrue(plot1.equals(plot2));
        
        // axisOffset...
        plot1.setAxisOffset(new RectangleInsets(0.05, 0.05, 0.05, 0.05));
        assertFalse(plot1.equals(plot2));
        plot2.setAxisOffset(new RectangleInsets(0.05, 0.05, 0.05, 0.05));
        assertTrue(plot1.equals(plot2));

        // domainAxis - no longer a separate field but test anyway...
        plot1.setDomainAxis(new CategoryAxis("Category Axis"));
        assertFalse(plot1.equals(plot2));
        plot2.setDomainAxis(new CategoryAxis("Category Axis"));
        assertTrue(plot1.equals(plot2));

        // domainAxes...
        plot1.setDomainAxis(11, new CategoryAxis("Secondary Axis"));
        assertFalse(plot1.equals(plot2));
        plot2.setDomainAxis(11, new CategoryAxis("Secondary Axis"));
        assertTrue(plot1.equals(plot2));

        // domainAxisLocation - no longer a separate field but test anyway...
        plot1.setDomainAxisLocation(AxisLocation.TOP_OR_RIGHT);
        assertFalse(plot1.equals(plot2));
        plot2.setDomainAxisLocation(AxisLocation.TOP_OR_RIGHT);
        assertTrue(plot1.equals(plot2));

        // domainAxisLocations...
        plot1.setDomainAxisLocation(11, AxisLocation.TOP_OR_RIGHT);
        assertFalse(plot1.equals(plot2));
        plot2.setDomainAxisLocation(11, AxisLocation.TOP_OR_RIGHT);
        assertTrue(plot1.equals(plot2));

        // draw shared domain axis...
        plot1.setDrawSharedDomainAxis(!plot1.getDrawSharedDomainAxis());
        assertFalse(plot1.equals(plot2));
        plot2.setDrawSharedDomainAxis(!plot2.getDrawSharedDomainAxis());
        assertTrue(plot1.equals(plot2));
        
        // rangeAxis - no longer a separate field but test anyway...
        plot1.setRangeAxis(new NumberAxis("Range Axis"));
        assertFalse(plot1.equals(plot2));
        plot2.setRangeAxis(new NumberAxis("Range Axis"));
        assertTrue(plot1.equals(plot2));

        // rangeAxes...
        plot1.setRangeAxis(11, new NumberAxis("Secondary Range Axis"));
        assertFalse(plot1.equals(plot2));
        plot2.setRangeAxis(11, new NumberAxis("Secondary Range Axis"));
        assertTrue(plot1.equals(plot2));

        // rangeAxisLocation - no longer a separate field but test anyway...
        plot1.setRangeAxisLocation(AxisLocation.TOP_OR_RIGHT);
        assertFalse(plot1.equals(plot2));
        plot2.setRangeAxisLocation(AxisLocation.TOP_OR_RIGHT);
        assertTrue(plot1.equals(plot2));

        // rangeAxisLocations...
        plot1.setRangeAxisLocation(11, AxisLocation.TOP_OR_RIGHT);
        assertFalse(plot1.equals(plot2));
        plot2.setRangeAxisLocation(11, AxisLocation.TOP_OR_RIGHT);
        assertTrue(plot1.equals(plot2));
        
        // datasetToDomainAxisMap...
        plot1.mapDatasetToDomainAxis(11, 11);
        assertFalse(plot1.equals(plot2));
        plot2.mapDatasetToDomainAxis(11, 11);
        assertTrue(plot1.equals(plot2));

        // datasetToRangeAxisMap...
        plot1.mapDatasetToRangeAxis(11, 11);
        assertFalse(plot1.equals(plot2));
        plot2.mapDatasetToRangeAxis(11, 11);
        assertTrue(plot1.equals(plot2));
        
        // renderer - no longer a separate field but test anyway...
        plot1.setRenderer(new AreaRenderer());
        assertFalse(plot1.equals(plot2));
        plot2.setRenderer(new AreaRenderer());
        assertTrue(plot1.equals(plot2));
        
        // renderers...
        plot1.setRenderer(11, new AreaRenderer());
        assertFalse(plot1.equals(plot2));
        plot2.setRenderer(11, new AreaRenderer());
        assertTrue(plot1.equals(plot2));
        
        // rendering order...
        plot1.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
        assertFalse(plot1.equals(plot2));
        plot2.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);
        assertTrue(plot1.equals(plot2));

        // columnRenderingOrder...
        plot1.setColumnRenderingOrder(SortOrder.DESCENDING);
        assertFalse(plot1.equals(plot2));
        plot2.setColumnRenderingOrder(SortOrder.DESCENDING);
        assertTrue(plot1.equals(plot2));
        
        // rowRenderingOrder...
        plot1.setRowRenderingOrder(SortOrder.DESCENDING);
        assertFalse(plot1.equals(plot2));
        plot2.setRowRenderingOrder(SortOrder.DESCENDING);
        assertTrue(plot1.equals(plot2));
        
        // domainGridlinesVisible
        plot1.setDomainGridlinesVisible(true);
        assertFalse(plot1.equals(plot2));
        plot2.setDomainGridlinesVisible(true);
        assertTrue(plot1.equals(plot2));

        // domainGridlinePosition
        plot1.setDomainGridlinePosition(CategoryAnchor.END);
        assertFalse(plot1.equals(plot2));
        plot2.setDomainGridlinePosition(CategoryAnchor.END);
        assertTrue(plot1.equals(plot2));

        // domainGridlineStroke
        Stroke stroke = new BasicStroke(2.0f);
        plot1.setDomainGridlineStroke(stroke);
        assertFalse(plot1.equals(plot2));
        plot2.setDomainGridlineStroke(stroke);
        assertTrue(plot1.equals(plot2));
        
        // domainGridlinePaint
        plot1.setDomainGridlinePaint(new GradientPaint(1.0f, 2.0f, Color.blue, 
                3.0f, 4.0f, Color.yellow));
        assertFalse(plot1.equals(plot2));
        plot2.setDomainGridlinePaint(new GradientPaint(1.0f, 2.0f, Color.blue, 
                3.0f, 4.0f, Color.yellow));
        assertTrue(plot1.equals(plot2));
        
        // rangeGridlinesVisible
        plot1.setRangeGridlinesVisible(false);
        assertFalse(plot1.equals(plot2));
        plot2.setRangeGridlinesVisible(false);
        assertTrue(plot1.equals(plot2));

        // rangeGridlineStroke
        plot1.setRangeGridlineStroke(stroke);
        assertFalse(plot1.equals(plot2));
        plot2.setRangeGridlineStroke(stroke);
        assertTrue(plot1.equals(plot2));
        
        // rangeGridlinePaint
        plot1.setRangeGridlinePaint(new GradientPaint(1.0f, 2.0f, Color.green, 
                3.0f, 4.0f, Color.yellow));
        assertFalse(plot1.equals(plot2));
        plot2.setRangeGridlinePaint(new GradientPaint(1.0f, 2.0f, Color.green, 
                3.0f, 4.0f, Color.yellow));
        assertTrue(plot1.equals(plot2));
        
        // anchorValue
        plot1.setAnchorValue(100.0);
        assertFalse(plot1.equals(plot2));
        plot2.setAnchorValue(100.0);
        assertTrue(plot1.equals(plot2));
        
        // rangeCrosshairVisible
        plot1.setRangeCrosshairVisible(true);
        assertFalse(plot1.equals(plot2));
        plot2.setRangeCrosshairVisible(true);
        assertTrue(plot1.equals(plot2));
        
        // rangeCrosshairValue
        plot1.setRangeCrosshairValue(100.0);
        assertFalse(plot1.equals(plot2));
        plot2.setRangeCrosshairValue(100.0);
        assertTrue(plot1.equals(plot2));
        
        // rangeCrosshairStroke
        plot1.setRangeCrosshairStroke(stroke);
        assertFalse(plot1.equals(plot2));
        plot2.setRangeCrosshairStroke(stroke);
        assertTrue(plot1.equals(plot2));
        
        // rangeCrosshairPaint
        plot1.setRangeCrosshairPaint(new GradientPaint(1.0f, 2.0f, Color.white, 
                3.0f, 4.0f, Color.yellow));
        assertFalse(plot1.equals(plot2));
        plot2.setRangeCrosshairPaint(new GradientPaint(1.0f, 2.0f, Color.white, 
                3.0f, 4.0f, Color.yellow));
        assertTrue(plot1.equals(plot2));
        
        // rangeCrosshairLockedOnData
        plot1.setRangeCrosshairLockedOnData(false);
        assertFalse(plot1.equals(plot2));
        plot2.setRangeCrosshairLockedOnData(false);
        assertTrue(plot1.equals(plot2));
        
        // range markers - no longer separate fields but test anyway...
        plot1.addRangeMarker(new ValueMarker(4.0), Layer.FOREGROUND);
        assertFalse(plot1.equals(plot2));
        plot2.addRangeMarker(new ValueMarker(4.0), Layer.FOREGROUND);
        assertTrue(plot1.equals(plot2));
        
        plot1.addRangeMarker(new ValueMarker(5.0), Layer.BACKGROUND);
        assertFalse(plot1.equals(plot2));
        plot2.addRangeMarker(new ValueMarker(5.0), Layer.BACKGROUND);
        assertTrue(plot1.equals(plot2));
        
        // foreground range markers...
        plot1.addRangeMarker(1, new ValueMarker(4.0), Layer.FOREGROUND);
        assertFalse(plot1.equals(plot2));
        plot2.addRangeMarker(1, new ValueMarker(4.0), Layer.FOREGROUND);
        assertTrue(plot1.equals(plot2));
        
        // background range markers...
        plot1.addRangeMarker(1, new ValueMarker(5.0), Layer.BACKGROUND);
        assertFalse(plot1.equals(plot2));
        plot2.addRangeMarker(1, new ValueMarker(5.0), Layer.BACKGROUND);
        assertTrue(plot1.equals(plot2));

        // annotations
        plot1.addAnnotation(
            new CategoryTextAnnotation("Text", "Category", 43.0)
        );
        assertFalse(plot1.equals(plot2));
        plot2.addAnnotation(new CategoryTextAnnotation("Text", "Category", 
                43.0));
        assertTrue(plot1.equals(plot2));
        
        // weight
        plot1.setWeight(3);
        assertFalse(plot1.equals(plot2));
        plot2.setWeight(3);
        assertTrue(plot1.equals(plot2));
        
        // fixed domain axis space...
        plot1.setFixedDomainAxisSpace(new AxisSpace());
        assertFalse(plot1.equals(plot2));
        plot2.setFixedDomainAxisSpace(new AxisSpace());
        assertTrue(plot1.equals(plot2));
        
        // fixed range axis space...
        plot1.setFixedRangeAxisSpace(new AxisSpace());
        assertFalse(plot1.equals(plot2));
        plot2.setFixedRangeAxisSpace(new AxisSpace());
        assertTrue(plot1.equals(plot2));

    }

    /**
     * Confirm that cloning works.
     */
    public void testCloning() {
        CategoryPlot p1 = new CategoryPlot();
        p1.setRangeCrosshairPaint(new GradientPaint(1.0f, 2.0f, Color.white, 
                3.0f, 4.0f, Color.yellow));
        CategoryPlot p2 = null;
        try {
            p2 = (CategoryPlot) p1.clone();
        }
        catch (CloneNotSupportedException e) {
            e.printStackTrace();
            System.err.println("Failed to clone.");
        }
        assertTrue(p1 != p2);
        assertTrue(p1.getClass() == p2.getClass());
        assertTrue(p1.equals(p2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization() {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        CategoryAxis domainAxis = new CategoryAxis("Domain");
        NumberAxis rangeAxis = new NumberAxis("Range");
        BarRenderer renderer = new BarRenderer();
        CategoryPlot p1 = new CategoryPlot(dataset, domainAxis, rangeAxis, 
                renderer);
        p1.setOrientation(PlotOrientation.HORIZONTAL);
        CategoryPlot p2 = null;
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(p1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            p2 = (CategoryPlot) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(p1.equals(p2));
    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization2() {

        DefaultCategoryDataset data = new DefaultCategoryDataset();
        CategoryAxis domainAxis = new CategoryAxis("Domain");
        NumberAxis rangeAxis = new NumberAxis("Range");
        BarRenderer renderer = new BarRenderer();
        CategoryPlot p1 = new CategoryPlot(data, domainAxis, rangeAxis, 
                renderer);
        p1.setOrientation(PlotOrientation.VERTICAL);
        CategoryPlot p2 = null;

        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(p1);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            p2 = (CategoryPlot) in.readObject();
            in.close();
        }
        catch (Exception e) {
            fail(e.toString());
        }
        assertEquals(p1, p2);

    }

    /**
     * Serialize an instance, restore it, and check for equality.
     */
    public void testSerialization3() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        JFreeChart chart = ChartFactory.createBarChart(
            "Test Chart",
            "Category Axis",
            "Value Axis",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );
        JFreeChart chart2 = null;
        
        // serialize and deserialize the chart....
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(chart);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            chart2 = (JFreeChart) in.readObject();
            in.close();
        }
        catch (Exception e) {
            fail(e.toString());
        }

        // now check that the chart is usable...
        boolean passed = true;
        try {
            chart2.createBufferedImage(300, 200);
        }
        catch (Exception e) {
            passed = false;  
            e.printStackTrace();            
        }
        assertTrue(passed);
    }
    
    /**
     * This test ensures that a plot with markers is serialized correctly.
     */
    public void testSerialization4() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        JFreeChart chart = ChartFactory.createBarChart(
            "Test Chart",
            "Category Axis",
            "Value Axis",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.addRangeMarker(new ValueMarker(1.1), Layer.FOREGROUND);
        plot.addRangeMarker(new IntervalMarker(2.2, 3.3), Layer.BACKGROUND);
        JFreeChart chart2 = null;
        
        // serialize and deserialize the chart....
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(chart);
            out.close();

            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            chart2 = (JFreeChart) in.readObject();
            in.close();
        }
        catch (Exception e) {
            fail(e.toString());
        }
        assertEquals(chart, chart2);
        
        // now check that the chart is usable...
        boolean passed = true;
        try {
            chart2.createBufferedImage(300, 200);
        }
        catch (Exception e) {
            passed = false;  
            e.printStackTrace();            
        }
        assertTrue(passed);
    }
    
    /**
     * Tests a bug where the plot is no longer registered as a listener
     * with the dataset(s) and axes after deserialization.  See patch 1209475
     * at SourceForge.
     */
    public void testSerialization5() {
        DefaultCategoryDataset dataset1 = new DefaultCategoryDataset();
        CategoryAxis domainAxis1 = new CategoryAxis("Domain 1");
        NumberAxis rangeAxis1 = new NumberAxis("Range 1");
        BarRenderer renderer1 = new BarRenderer();
        CategoryPlot p1 = new CategoryPlot(dataset1, domainAxis1, rangeAxis1, 
                renderer1);
        CategoryAxis domainAxis2 = new CategoryAxis("Domain 2");
        NumberAxis rangeAxis2 = new NumberAxis("Range 2");
        BarRenderer renderer2 = new BarRenderer();
        DefaultCategoryDataset dataset2 = new DefaultCategoryDataset();
        p1.setDataset(1, dataset2);
        p1.setDomainAxis(1, domainAxis2);
        p1.setRangeAxis(1, rangeAxis2);
        p1.setRenderer(1, renderer2);
        CategoryPlot p2 = null;
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(buffer);
            out.writeObject(p1);
            out.close();
            ObjectInput in = new ObjectInputStream(
                    new ByteArrayInputStream(buffer.toByteArray()));
            p2 = (CategoryPlot) in.readObject();
            in.close();
        }
        catch (Exception e) {
            fail(e.toString());
        }
        assertEquals(p1, p2);
        
        // now check that all datasets, renderers and axes are being listened 
        // too...
        CategoryAxis domainAxisA = p2.getDomainAxis(0);
        NumberAxis rangeAxisA = (NumberAxis) p2.getRangeAxis(0);
        DefaultCategoryDataset datasetA 
                = (DefaultCategoryDataset) p2.getDataset(0);
        BarRenderer rendererA = (BarRenderer) p2.getRenderer(0);
        CategoryAxis domainAxisB = p2.getDomainAxis(1);
        NumberAxis rangeAxisB = (NumberAxis) p2.getRangeAxis(1);
        DefaultCategoryDataset datasetB 
                = (DefaultCategoryDataset) p2.getDataset(1);
        BarRenderer rendererB  = (BarRenderer) p2.getRenderer(1);
        assertTrue(datasetA.hasListener(p2));
        assertTrue(domainAxisA.hasListener(p2));
        assertTrue(rangeAxisA.hasListener(p2));
        assertTrue(rendererA.hasListener(p2));
        assertTrue(datasetB.hasListener(p2));
        assertTrue(domainAxisB.hasListener(p2));
        assertTrue(rangeAxisB.hasListener(p2));
        assertTrue(rendererB.hasListener(p2));
    }

    /**
     * A test for a bug where setting the renderer doesn't register the plot
     * as a RendererChangeListener.
     */
    public void testSetRenderer() {
        CategoryPlot plot = new CategoryPlot();
        CategoryItemRenderer renderer = new LineAndShapeRenderer();
        plot.setRenderer(renderer);
        // now make a change to the renderer and see if it triggers a plot
        // change event...
        MyPlotChangeListener listener = new MyPlotChangeListener();
        plot.addChangeListener(listener);
        renderer.setSeriesPaint(0, Color.black);
        assertTrue(listener.getEvent() != null);
    }
    
    /**
     * A test for bug report 1169972.
     */
    public void test1169972() {
        CategoryPlot plot = new CategoryPlot(null, null, null, null);
        plot.setDomainAxis(new CategoryAxis("C"));
        plot.setRangeAxis(new NumberAxis("Y"));
        plot.setRenderer(new BarRenderer());
        plot.setDataset(new DefaultCategoryDataset());
        assertTrue(plot != null);
    }
    
    /**
     * Some tests for the addDomainMarker() method(s).
     */
    public void testAddDomainMarker() {
        CategoryPlot plot = new CategoryPlot();
        CategoryMarker m = new CategoryMarker("C1");
        plot.addDomainMarker(m);
        List listeners = Arrays.asList(m.getListeners(
                MarkerChangeListener.class));
        assertTrue(listeners.contains(plot));
        plot.clearDomainMarkers();
        listeners = Arrays.asList(m.getListeners(MarkerChangeListener.class));
        assertFalse(listeners.contains(plot));
    }    
    
    /**
     * Some tests for the addRangeMarker() method(s).
     */
    public void testAddRangeMarker() {
        CategoryPlot plot = new CategoryPlot();
        Marker m = new ValueMarker(1.0);
        plot.addRangeMarker(m);
        List listeners = Arrays.asList(m.getListeners(
                MarkerChangeListener.class));
        assertTrue(listeners.contains(plot));
        plot.clearRangeMarkers();
        listeners = Arrays.asList(m.getListeners(MarkerChangeListener.class));
        assertFalse(listeners.contains(plot));
    }
    
    /**
     * A test for bug 1654215 (where a renderer is added to the plot without
     * a corresponding dataset and it throws an exception at drawing time).
     */
    public void test1654215() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        JFreeChart chart = ChartFactory.createLineChart("Title", "X", "Y",
                dataset, PlotOrientation.VERTICAL, true, false, false);
        CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setRenderer(1, new LineAndShapeRenderer());
        boolean success = false;
        try {
            BufferedImage image = new BufferedImage(200 , 100, 
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = image.createGraphics();
            chart.draw(g2, new Rectangle2D.Double(0, 0, 200, 100), null, null);
            g2.dispose();
            success = true;
        }
        catch (Exception e) {
            e.printStackTrace();
            success = false;
        }
        assertTrue(success);
    }

}
