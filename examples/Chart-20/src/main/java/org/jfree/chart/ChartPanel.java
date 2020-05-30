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
 * ---------------
 * ChartPanel.java
 * ---------------
 * (C) Copyright 2000-2007, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   Andrzej Porebski;
 *                   Soren Caspersen;
 *                   Jonathan Nash;
 *                   Hans-Jurgen Greiner;
 *                   Andreas Schneider;
 *                   Daniel van Enckevort;
 *                   David M O'Donnell;
 *                   Arnaud Lelievre;
 *                   Matthias Rose;
 *                   Onno vd Akker;
 *                   Sergei Ivanov;
 *
 * $Id: ChartPanel.java,v 1.20.2.15 2007/06/06 15:08:23 mungady Exp $
 *
 * Changes (from 28-Jun-2001)
 * --------------------------
 * 28-Jun-2001 : Integrated buffering code contributed by S???ren 
 *               Caspersen (DG);
 * 18-Sep-2001 : Updated header and fixed DOS encoding problem (DG);
 * 22-Nov-2001 : Added scaling to improve display of charts in small sizes (DG);
 * 26-Nov-2001 : Added property editing, saving and printing (DG);
 * 11-Dec-2001 : Transferred saveChartAsPNG method to new ChartUtilities 
 *               class (DG);
 * 13-Dec-2001 : Added tooltips (DG);
 * 16-Jan-2002 : Added an optional crosshair, based on the implementation by 
 *               Jonathan Nash. Renamed the tooltips class (DG);
 * 23-Jan-2002 : Implemented zooming based on code by Hans-Jurgen Greiner (DG);
 * 05-Feb-2002 : Improved tooltips setup.  Renamed method attemptSaveAs() 
 *               --> doSaveAs() and made it public rather than private (DG);
 * 28-Mar-2002 : Added a new constructor (DG);
 * 09-Apr-2002 : Changed initialisation of tooltip generation, as suggested by 
 *               Hans-Jurgen Greiner (DG);
 * 27-May-2002 : New interactive zooming methods based on code by Hans-Jurgen 
 *               Greiner. Renamed JFreeChartPanel --> ChartPanel, moved 
 *               constants to ChartPanelConstants interface (DG);
 * 31-May-2002 : Fixed a bug with interactive zooming and added a way to 
 *               control if the zoom rectangle is filled in or drawn as an 
 *               outline. A mouse drag gesture towards the top left now causes 
 *               an autoRangeBoth() and is a way to undo zooms (AS);
 * 11-Jun-2002 : Reinstated handleClick method call in mouseClicked() to get 
 *               crosshairs working again (DG);
 * 13-Jun-2002 : Added check for null popup menu in mouseDragged method (DG);
 * 18-Jun-2002 : Added get/set methods for minimum and maximum chart 
 *               dimensions (DG);
 * 25-Jun-2002 : Removed redundant code (DG);
 * 27-Aug-2002 : Added get/set methods for popup menu (DG);
 * 26-Sep-2002 : Fixed errors reported by Checkstyle (DG);
 * 22-Oct-2002 : Added translation methods for screen <--> Java2D, contributed
 *               by Daniel van Enckevort (DG);
 * 05-Nov-2002 : Added a chart reference to the ChartMouseEvent class (DG);
 * 22-Nov-2002 : Added test in zoom method for inverted axes, supplied by 
 *               David M O'Donnell (DG);
 * 14-Jan-2003 : Implemented ChartProgressListener interface (DG);
 * 14-Feb-2003 : Removed deprecated setGenerateTooltips method (DG);
 * 12-Mar-2003 : Added option to enforce filename extension (see bug id 
 *               643173) (DG);
 * 08-Sep-2003 : Added internationalization via use of properties 
 *               resourceBundle (RFE 690236) (AL);
 * 18-Sep-2003 : Added getScaleX() and getScaleY() methods (protected) as 
 *               requested by Irv Thomae (DG);
 * 12-Nov-2003 : Added zooming support for the FastScatterPlot class (DG);
 * 24-Nov-2003 : Minor Javadoc updates (DG);
 * 04-Dec-2003 : Added anchor point for crosshair calculation (DG);
 * 17-Jan-2004 : Added new methods to set tooltip delays to be used in this 
 *               chart panel. Refer to patch 877565 (MR);
 * 02-Feb-2004 : Fixed bug in zooming trigger and added zoomTriggerDistance 
 *               attribute (DG);
 * 08-Apr-2004 : Changed getScaleX() and getScaleY() from protected to 
 *               public (DG);
 * 15-Apr-2004 : Added zoomOutFactor and zoomInFactor (DG);
 * 21-Apr-2004 : Fixed zooming bug in mouseReleased() method (DG);
 * 13-Jul-2004 : Added check for null chart (DG);
 * 04-Oct-2004 : Renamed ShapeUtils --> ShapeUtilities (DG); 
 * 11-Nov-2004 : Moved constants back in from ChartPanelConstants (DG);
 * 12-Nov-2004 : Modified zooming mechanism to support zooming within 
 *               subplots (DG);
 * 26-Jan-2005 : Fixed mouse zooming for horizontal category plots (DG);
 * 11-Apr-2005 : Added getFillZoomRectangle() method, renamed 
 *               setHorizontalZoom() --> setDomainZoomable(), 
 *               setVerticalZoom() --> setRangeZoomable(), added 
 *               isDomainZoomable() and isRangeZoomable(), added 
 *               getHorizontalAxisTrace() and getVerticalAxisTrace(),
 *               renamed autoRangeBoth() --> restoreAutoBounds(),
 *               autoRangeHorizontal() --> restoreAutoDomainBounds(),
 *               autoRangeVertical() --> restoreAutoRangeBounds() (DG);
 * 12-Apr-2005 : Removed working areas, added getAnchorPoint() method,
 *               added protected accessors for tracelines (DG);
 * 18-Apr-2005 : Made constants final (DG);
 * 26-Apr-2005 : Removed LOGGER (DG);
 * 01-Jun-2005 : Fixed zooming for combined plots - see bug report 
 *               1212039, fix thanks to Onno vd Akker (DG);
 * 25-Nov-2005 : Reworked event listener mechanism (DG);
 * ------------- JFREECHART 1.0.x ---------------------------------------------
 * 01-Aug-2006 : Fixed minor bug in restoreAutoRangeBounds() (DG);
 * 04-Sep-2006 : Renamed attemptEditChartProperties() --> 
 *               doEditChartProperties() and made public (DG);
 * 13-Sep-2006 : Don't generate ChartMouseEvents if the panel's chart is null
 *               (fixes bug 1556951) (DG);
 * 05-Mar-2007 : Applied patch 1672561 by Sergei Ivanov, to fix zoom rectangle
 *               drawing for dynamic charts (DG);
 * 17-Apr-2007 : Fix NullPointerExceptions in zooming for combined plots (DG);
 * 24-May-2007 : When the look-and-feel changes, update the popup menu if there 
 *               is one (DG);
 * 06-Jun-2007 : Fixed coordinates for drawing buffer image (DG);
 * 24-Sep-2007 : Added zoomAroundAnchor flag, and handle clearing of chart
 *               buffer (DG);
 *               
 */

package org.jfree.chart;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.EventListener;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.event.EventListenerList;

import org.jfree.chart.editor.ChartEditor;
import org.jfree.chart.editor.ChartEditorManager;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.event.ChartChangeEvent;
import org.jfree.chart.event.ChartChangeListener;
import org.jfree.chart.event.ChartProgressEvent;
import org.jfree.chart.event.ChartProgressListener;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.Zoomable;
import org.jfree.chart.ui.ExtensionFileFilter;

/**
 * A Swing GUI component for displaying a {@link JFreeChart} object.
 * <P>
 * The panel registers with the chart to receive notification of changes to any
 * component of the chart.  The chart is redrawn automatically whenever this 
 * notification is received.
 */
public class ChartPanel extends JPanel 
                        implements ChartChangeListener,
                                   ChartProgressListener,
                                   ActionListener,
                                   MouseListener,
                                   MouseMotionListener,
                                   Printable,
                                   Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 6046366297214274674L;
    
    /** Default setting for buffer usage. */
    public static final boolean DEFAULT_BUFFER_USED = false;

    /** The default panel width. */
    public static final int DEFAULT_WIDTH = 680;

    /** The default panel height. */
    public static final int DEFAULT_HEIGHT = 420;

    /** The default limit below which chart scaling kicks in. */
    public static final int DEFAULT_MINIMUM_DRAW_WIDTH = 300;

    /** The default limit below which chart scaling kicks in. */
    public static final int DEFAULT_MINIMUM_DRAW_HEIGHT = 200;

    /** The default limit below which chart scaling kicks in. */
    public static final int DEFAULT_MAXIMUM_DRAW_WIDTH = 800;

    /** The default limit below which chart scaling kicks in. */
    public static final int DEFAULT_MAXIMUM_DRAW_HEIGHT = 600;

    /** The minimum size required to perform a zoom on a rectangle */
    public static final int DEFAULT_ZOOM_TRIGGER_DISTANCE = 10;

    /** Properties action command. */
    public static final String PROPERTIES_COMMAND = "PROPERTIES";

    /** Save action command. */
    public static final String SAVE_COMMAND = "SAVE";

    /** Print action command. */
    public static final String PRINT_COMMAND = "PRINT";

    /** Zoom in (both axes) action command. */
    public static final String ZOOM_IN_BOTH_COMMAND = "ZOOM_IN_BOTH";

    /** Zoom in (domain axis only) action command. */
    public static final String ZOOM_IN_DOMAIN_COMMAND = "ZOOM_IN_DOMAIN";

    /** Zoom in (range axis only) action command. */
    public static final String ZOOM_IN_RANGE_COMMAND = "ZOOM_IN_RANGE";

    /** Zoom out (both axes) action command. */
    public static final String ZOOM_OUT_BOTH_COMMAND = "ZOOM_OUT_BOTH";

    /** Zoom out (domain axis only) action command. */
    public static final String ZOOM_OUT_DOMAIN_COMMAND = "ZOOM_DOMAIN_BOTH";

    /** Zoom out (range axis only) action command. */
    public static final String ZOOM_OUT_RANGE_COMMAND = "ZOOM_RANGE_BOTH";

    /** Zoom reset (both axes) action command. */
    public static final String ZOOM_RESET_BOTH_COMMAND = "ZOOM_RESET_BOTH";

    /** Zoom reset (domain axis only) action command. */
    public static final String ZOOM_RESET_DOMAIN_COMMAND = "ZOOM_RESET_DOMAIN";

    /** Zoom reset (range axis only) action command. */
    public static final String ZOOM_RESET_RANGE_COMMAND = "ZOOM_RESET_RANGE";

    /** The chart that is displayed in the panel. */
    private JFreeChart chart;

    /** Storage for registered (chart) mouse listeners. */
    private EventListenerList chartMouseListeners;

    /** A flag that controls whether or not the off-screen buffer is used. */
    private boolean useBuffer;

    /** A flag that indicates that the buffer should be refreshed. */
    private boolean refreshBuffer;

    /** A buffer for the rendered chart. */
    private Image chartBuffer;

    /** The height of the chart buffer. */
    private int chartBufferHeight;

    /** The width of the chart buffer. */
    private int chartBufferWidth;

    /** 
     * The minimum width for drawing a chart (uses scaling for smaller widths). 
     */
    private int minimumDrawWidth;

    /** 
     * The minimum height for drawing a chart (uses scaling for smaller 
     * heights). 
     */
    private int minimumDrawHeight;

    /** 
     * The maximum width for drawing a chart (uses scaling for bigger 
     * widths). 
     */
    private int maximumDrawWidth;

    /** 
     * The maximum height for drawing a chart (uses scaling for bigger 
     * heights). 
     */
    private int maximumDrawHeight;

    /** The popup menu for the frame. */
    private JPopupMenu popup;

    /** The drawing info collected the last time the chart was drawn. */
    private ChartRenderingInfo info;
    
    /** The chart anchor point. */
    private Point2D anchor;

    /** The scale factor used to draw the chart. */
    private double scaleX;

    /** The scale factor used to draw the chart. */
    private double scaleY;

    /** The plot orientation. */
    private PlotOrientation orientation = PlotOrientation.VERTICAL;
    
    /** A flag that controls whether or not domain zooming is enabled. */
    private boolean domainZoomable = false;

    /** A flag that controls whether or not range zooming is enabled. */
    private boolean rangeZoomable = false;

    /** 
     * The zoom rectangle starting point (selected by the user with a mouse 
     * click).  This is a point on the screen, not the chart (which may have
     * been scaled up or down to fit the panel).  
     */
    private Point zoomPoint = null;

    /** The zoom rectangle (selected by the user with the mouse). */
    private transient Rectangle2D zoomRectangle = null;

    /** Controls if the zoom rectangle is drawn as an outline or filled. */
    private boolean fillZoomRectangle = false;

    /** The minimum distance required to drag the mouse to trigger a zoom. */
    private int zoomTriggerDistance;
    
    /** A flag that controls whether or not horizontal tracing is enabled. */
    private boolean horizontalAxisTrace = false;

    /** A flag that controls whether or not vertical tracing is enabled. */
    private boolean verticalAxisTrace = false;

    /** A vertical trace line. */
    private transient Line2D verticalTraceLine;

    /** A horizontal trace line. */
    private transient Line2D horizontalTraceLine;

    /** Menu item for zooming in on a chart (both axes). */
    private JMenuItem zoomInBothMenuItem;

    /** Menu item for zooming in on a chart (domain axis). */
    private JMenuItem zoomInDomainMenuItem;

    /** Menu item for zooming in on a chart (range axis). */
    private JMenuItem zoomInRangeMenuItem;

    /** Menu item for zooming out on a chart. */
    private JMenuItem zoomOutBothMenuItem;

    /** Menu item for zooming out on a chart (domain axis). */
    private JMenuItem zoomOutDomainMenuItem;

    /** Menu item for zooming out on a chart (range axis). */
    private JMenuItem zoomOutRangeMenuItem;

    /** Menu item for resetting the zoom (both axes). */
    private JMenuItem zoomResetBothMenuItem;

    /** Menu item for resetting the zoom (domain axis only). */
    private JMenuItem zoomResetDomainMenuItem;

    /** Menu item for resetting the zoom (range axis only). */
    private JMenuItem zoomResetRangeMenuItem;

    /** A flag that controls whether or not file extensions are enforced. */
    private boolean enforceFileExtensions;

    /** A flag that indicates if original tooltip delays are changed. */
    private boolean ownToolTipDelaysActive;  
    
    /** Original initial tooltip delay of ToolTipManager.sharedInstance(). */
    private int originalToolTipInitialDelay;

    /** Original reshow tooltip delay of ToolTipManager.sharedInstance(). */
    private int originalToolTipReshowDelay;  

    /** Original dismiss tooltip delay of ToolTipManager.sharedInstance(). */
    private int originalToolTipDismissDelay;

    /** Own initial tooltip delay to be used in this chart panel. */
    private int ownToolTipInitialDelay;
    
    /** Own reshow tooltip delay to be used in this chart panel. */
    private int ownToolTipReshowDelay;  

    /** Own dismiss tooltip delay to be used in this chart panel. */
    private int ownToolTipDismissDelay;    

    /** The factor used to zoom in on an axis range. */
    private double zoomInFactor = 0.5;
    
    /** The factor used to zoom out on an axis range. */
    private double zoomOutFactor = 2.0;
    
    /**
     * A flag that controls whether zoom operations are centred on the
     * current anchor point, or the centre point of the relevant axis.
     *
     * @since 1.0.7
     */
    private boolean zoomAroundAnchor;
    
    /** The resourceBundle for the localization. */
    protected static ResourceBundle localizationResources 
        = ResourceBundle.getBundle("org.jfree.chart.LocalizationBundle");

    /**
     * Constructs a panel that displays the specified chart.
     *
     * @param chart  the chart.
     */
    public ChartPanel(JFreeChart chart) {

        this(
            chart,
            DEFAULT_WIDTH,
            DEFAULT_HEIGHT,
            DEFAULT_MINIMUM_DRAW_WIDTH,
            DEFAULT_MINIMUM_DRAW_HEIGHT,
            DEFAULT_MAXIMUM_DRAW_WIDTH,
            DEFAULT_MAXIMUM_DRAW_HEIGHT,
            DEFAULT_BUFFER_USED,
            true,  // properties
            true,  // save
            true,  // print
            true,  // zoom
            true   // tooltips
        );

    }

    /**
     * Constructs a panel containing a chart.
     *
     * @param chart  the chart.
     * @param useBuffer  a flag controlling whether or not an off-screen buffer
     *                   is used.
     */
    public ChartPanel(JFreeChart chart, boolean useBuffer) {

        this(chart,
             DEFAULT_WIDTH,
             DEFAULT_HEIGHT,
             DEFAULT_MINIMUM_DRAW_WIDTH,
             DEFAULT_MINIMUM_DRAW_HEIGHT,
             DEFAULT_MAXIMUM_DRAW_WIDTH,
             DEFAULT_MAXIMUM_DRAW_HEIGHT,
             useBuffer,
             true,  // properties
             true,  // save
             true,  // print
             true,  // zoom
             true   // tooltips
             );

    }

    /**
     * Constructs a JFreeChart panel.
     *
     * @param chart  the chart.
     * @param properties  a flag indicating whether or not the chart property
     *                    editor should be available via the popup menu.
     * @param save  a flag indicating whether or not save options should be
     *              available via the popup menu.
     * @param print  a flag indicating whether or not the print option
     *               should be available via the popup menu.
     * @param zoom  a flag indicating whether or not zoom options should
     *              be added to the popup menu.
     * @param tooltips  a flag indicating whether or not tooltips should be
     *                  enabled for the chart.
     */
    public ChartPanel(JFreeChart chart,
                      boolean properties,
                      boolean save,
                      boolean print,
                      boolean zoom,
                      boolean tooltips) {

        this(chart,
             DEFAULT_WIDTH,
             DEFAULT_HEIGHT,
             DEFAULT_MINIMUM_DRAW_WIDTH,
             DEFAULT_MINIMUM_DRAW_HEIGHT,
             DEFAULT_MAXIMUM_DRAW_WIDTH,
             DEFAULT_MAXIMUM_DRAW_HEIGHT,
             DEFAULT_BUFFER_USED,
             properties,
             save,
             print,
             zoom,
             tooltips
             );

    }

    /**
     * Constructs a JFreeChart panel.
     *
     * @param chart  the chart.
     * @param width  the preferred width of the panel.
     * @param height  the preferred height of the panel.
     * @param minimumDrawWidth  the minimum drawing width.
     * @param minimumDrawHeight  the minimum drawing height.
     * @param maximumDrawWidth  the maximum drawing width.
     * @param maximumDrawHeight  the maximum drawing height.
     * @param useBuffer  a flag that indicates whether to use the off-screen
     *                   buffer to improve performance (at the expense of 
     *                   memory).
     * @param properties  a flag indicating whether or not the chart property
     *                    editor should be available via the popup menu.
     * @param save  a flag indicating whether or not save options should be
     *              available via the popup menu.
     * @param print  a flag indicating whether or not the print option
     *               should be available via the popup menu.
     * @param zoom  a flag indicating whether or not zoom options should be 
     *              added to the popup menu.
     * @param tooltips  a flag indicating whether or not tooltips should be 
     *                  enabled for the chart.
     */
    public ChartPanel(JFreeChart chart,
                      int width,
                      int height,
                      int minimumDrawWidth,
                      int minimumDrawHeight,
                      int maximumDrawWidth,
                      int maximumDrawHeight,
                      boolean useBuffer,
                      boolean properties,
                      boolean save,
                      boolean print,
                      boolean zoom,
                      boolean tooltips) {

        this.setChart(chart);
        this.chartMouseListeners = new EventListenerList();
        this.info = new ChartRenderingInfo();
        setPreferredSize(new Dimension(width, height));
        this.useBuffer = useBuffer;
        this.refreshBuffer = false;
        this.minimumDrawWidth = minimumDrawWidth;
        this.minimumDrawHeight = minimumDrawHeight;
        this.maximumDrawWidth = maximumDrawWidth;
        this.maximumDrawHeight = maximumDrawHeight;
        this.zoomTriggerDistance = DEFAULT_ZOOM_TRIGGER_DISTANCE;

        // set up popup menu...
        this.popup = null;
        if (properties || save || print || zoom) {
            this.popup = createPopupMenu(properties, save, print, zoom);
        }

        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        enableEvents(AWTEvent.MOUSE_MOTION_EVENT_MASK);
        setDisplayToolTips(tooltips);
        addMouseListener(this);
        addMouseMotionListener(this);

        this.enforceFileExtensions = true;

        // initialize ChartPanel-specific tool tip delays with
        // values the from ToolTipManager.sharedInstance()
        ToolTipManager ttm = ToolTipManager.sharedInstance();       
        this.ownToolTipInitialDelay = ttm.getInitialDelay();
        this.ownToolTipDismissDelay = ttm.getDismissDelay();
        this.ownToolTipReshowDelay = ttm.getReshowDelay();

        this.zoomAroundAnchor = false;
    }

    /**
     * Returns the chart contained in the panel.
     *
     * @return The chart (possibly <code>null</code>).
     */
    public JFreeChart getChart() {
        return this.chart;
    }

    /**
     * Sets the chart that is displayed in the panel.
     *
     * @param chart  the chart (<code>null</code> permitted).
     */
    public void setChart(JFreeChart chart) {

        // stop listening for changes to the existing chart
        if (this.chart != null) {
            this.chart.removeChangeListener(this);
            this.chart.removeProgressListener(this);
        }

        // add the new chart
        this.chart = chart;
        if (chart != null) {
            this.chart.addChangeListener(this);
            this.chart.addProgressListener(this);
            Plot plot = chart.getPlot();
            this.domainZoomable = false;
            this.rangeZoomable = false;
            if (plot instanceof Zoomable) {
                Zoomable z = (Zoomable) plot;
                this.domainZoomable = z.isDomainZoomable();
                this.rangeZoomable = z.isRangeZoomable();
                this.orientation = z.getOrientation();
            }
        }
        else {
            this.domainZoomable = false;
            this.rangeZoomable = false;
        }
        if (this.useBuffer) {
            this.refreshBuffer = true;
        }
        repaint();

    }

    /**
     * Returns the minimum drawing width for charts.
     * <P>
     * If the width available on the panel is less than this, then the chart is
     * drawn at the minimum width then scaled down to fit.
     *
     * @return The minimum drawing width.
     */
    public int getMinimumDrawWidth() {
        return this.minimumDrawWidth;
    }

    /**
     * Sets the minimum drawing width for the chart on this panel.
     * <P>
     * At the time the chart is drawn on the panel, if the available width is
     * less than this amount, the chart will be drawn using the minimum width
     * then scaled down to fit the available space.
     *
     * @param width  The width.
     */
    public void setMinimumDrawWidth(int width) {
        this.minimumDrawWidth = width;
    }

    /**
     * Returns the maximum drawing width for charts.
     * <P>
     * If the width available on the panel is greater than this, then the chart
     * is drawn at the maximum width then scaled up to fit.
     *
     * @return The maximum drawing width.
     */
    public int getMaximumDrawWidth() {
        return this.maximumDrawWidth;
    }

    /**
     * Sets the maximum drawing width for the chart on this panel.
     * <P>
     * At the time the chart is drawn on the panel, if the available width is
     * greater than this amount, the chart will be drawn using the maximum
     * width then scaled up to fit the available space.
     *
     * @param width  The width.
     */
    public void setMaximumDrawWidth(int width) {
        this.maximumDrawWidth = width;
    }

    /**
     * Returns the minimum drawing height for charts.
     * <P>
     * If the height available on the panel is less than this, then the chart
     * is drawn at the minimum height then scaled down to fit.
     *
     * @return The minimum drawing height.
     */
    public int getMinimumDrawHeight() {
        return this.minimumDrawHeight;
    }

    /**
     * Sets the minimum drawing height for the chart on this panel.
     * <P>
     * At the time the chart is drawn on the panel, if the available height is
     * less than this amount, the chart will be drawn using the minimum height
     * then scaled down to fit the available space.
     *
     * @param height  The height.
     */
    public void setMinimumDrawHeight(int height) {
        this.minimumDrawHeight = height;
    }

    /**
     * Returns the maximum drawing height for charts.
     * <P>
     * If the height available on the panel is greater than this, then the
     * chart is drawn at the maximum height then scaled up to fit.
     *
     * @return The maximum drawing height.
     */
    public int getMaximumDrawHeight() {
        return this.maximumDrawHeight;
    }

    /**
     * Sets the maximum drawing height for the chart on this panel.
     * <P>
     * At the time the chart is drawn on the panel, if the available height is
     * greater than this amount, the chart will be drawn using the maximum
     * height then scaled up to fit the available space.
     *
     * @param height  The height.
     */
    public void setMaximumDrawHeight(int height) {
        this.maximumDrawHeight = height;
    }

    /**
     * Returns the X scale factor for the chart.  This will be 1.0 if no 
     * scaling has been used.
     * 
     * @return The scale factor.
     */
    public double getScaleX() {
        return this.scaleX;
    }
    
    /**
     * Returns the Y scale factory for the chart.  This will be 1.0 if no 
     * scaling has been used.
     * 
     * @return The scale factor.
     */
    public double getScaleY() {
        return this.scaleY;
    }
    
    /**
     * Returns the anchor point.
     * 
     * @return The anchor point (possibly <code>null</code>).
     */
    public Point2D getAnchor() {
        return this.anchor;   
    }
    
    /**
     * Sets the anchor point.  This method is provided for the use of 
     * subclasses, not end users.
     * 
     * @param anchor  the anchor point (<code>null</code> permitted).
     */
    protected void setAnchor(Point2D anchor) {
        this.anchor = anchor;   
    }
    
    /**
     * Returns the popup menu.
     *
     * @return The popup menu.
     */
    public JPopupMenu getPopupMenu() {
        return this.popup;
    }

    /**
     * Sets the popup menu for the panel.
     *
     * @param popup  the popup menu (<code>null</code> permitted).
     */
    public void setPopupMenu(JPopupMenu popup) {
        this.popup = popup;
    }

    /**
     * Returns the chart rendering info from the most recent chart redraw.
     *
     * @return The chart rendering info.
     */
    public ChartRenderingInfo getChartRenderingInfo() {
        return this.info;
    }

    /**
     * A convenience method that switches on mouse-based zooming.
     *
     * @param flag  <code>true</code> enables zooming and rectangle fill on 
     *              zoom.
     */
    public void setMouseZoomable(boolean flag) {
        setMouseZoomable(flag, true);
    }

    /**
     * A convenience method that switches on mouse-based zooming.
     *
     * @param flag  <code>true</code> if zooming enabled
     * @param fillRectangle  <code>true</code> if zoom rectangle is filled,
     *                       false if rectangle is shown as outline only.
     */
    public void setMouseZoomable(boolean flag, boolean fillRectangle) {
        setDomainZoomable(flag);
        setRangeZoomable(flag);
        setFillZoomRectangle(fillRectangle);
    }

    /**
     * Returns the flag that determines whether or not zooming is enabled for 
     * the domain axis.
     * 
     * @return A boolean.
     */
    public boolean isDomainZoomable() {
        return this.domainZoomable;
    }
    
    /**
     * Sets the flag that controls whether or not zooming is enable for the 
     * domain axis.  A check is made to ensure that the current plot supports
     * zooming for the domain values.
     *
     * @param flag  <code>true</code> enables zooming if possible.
     */
    public void setDomainZoomable(boolean flag) {
        if (flag) {
            Plot plot = this.chart.getPlot();
            if (plot instanceof Zoomable) {
                Zoomable z = (Zoomable) plot;
                this.domainZoomable = flag && (z.isDomainZoomable());  
            }
        }
        else {
            this.domainZoomable = false;
        }
    }

    /**
     * Returns the flag that determines whether or not zooming is enabled for 
     * the range axis.
     * 
     * @return A boolean.
     */
    public boolean isRangeZoomable() {
        return this.rangeZoomable;
    }
    
    /**
     * A flag that controls mouse-based zooming on the vertical axis.
     *
     * @param flag  <code>true</code> enables zooming.
     */
    public void setRangeZoomable(boolean flag) {
        if (flag) {
            Plot plot = this.chart.getPlot();
            if (plot instanceof Zoomable) {
                Zoomable z = (Zoomable) plot;
                this.rangeZoomable = flag && (z.isRangeZoomable());  
            }
        }
        else {
            this.rangeZoomable = false;
        }
    }

    /**
     * Returns the flag that controls whether or not the zoom rectangle is
     * filled when drawn.
     * 
     * @return A boolean.
     */
    public boolean getFillZoomRectangle() {
        return this.fillZoomRectangle;
    }
    
    /**
     * A flag that controls how the zoom rectangle is drawn.
     *
     * @param flag  <code>true</code> instructs to fill the rectangle on
     *              zoom, otherwise it will be outlined.
     */
    public void setFillZoomRectangle(boolean flag) {
        this.fillZoomRectangle = flag;
    }

    /**
     * Returns the zoom trigger distance.  This controls how far the mouse must
     * move before a zoom action is triggered.
     * 
     * @return The distance (in Java2D units).
     */
    public int getZoomTriggerDistance() {
        return this.zoomTriggerDistance;
    }
    
    /**
     * Sets the zoom trigger distance.  This controls how far the mouse must 
     * move before a zoom action is triggered.
     * 
     * @param distance  the distance (in Java2D units).
     */
    public void setZoomTriggerDistance(int distance) {
        this.zoomTriggerDistance = distance;
    }
    
    /**
     * Returns the flag that controls whether or not a horizontal axis trace
     * line is drawn over the plot area at the current mouse location.
     * 
     * @return A boolean.
     */
    public boolean getHorizontalAxisTrace() {
        return this.horizontalAxisTrace;    
    }
    
    /**
     * A flag that controls trace lines on the horizontal axis.
     *
     * @param flag  <code>true</code> enables trace lines for the mouse
     *      pointer on the horizontal axis.
     */
    public void setHorizontalAxisTrace(boolean flag) {
        this.horizontalAxisTrace = flag;
    }
    
    /**
     * Returns the horizontal trace line.
     * 
     * @return The horizontal trace line (possibly <code>null</code>).
     */
    protected Line2D getHorizontalTraceLine() {
        return this.horizontalTraceLine;   
    }
    
    /**
     * Sets the horizontal trace line.
     * 
     * @param line  the line (<code>null</code> permitted).
     */
    protected void setHorizontalTraceLine(Line2D line) {
        this.horizontalTraceLine = line;   
    }

    /**
     * Returns the flag that controls whether or not a vertical axis trace
     * line is drawn over the plot area at the current mouse location.
     * 
     * @return A boolean.
     */
    public boolean getVerticalAxisTrace() {
        return this.verticalAxisTrace;    
    }
    
    /**
     * A flag that controls trace lines on the vertical axis.
     *
     * @param flag  <code>true</code> enables trace lines for the mouse
     *              pointer on the vertical axis.
     */
    public void setVerticalAxisTrace(boolean flag) {
        this.verticalAxisTrace = flag;
    }

    /**
     * Returns the vertical trace line.
     * 
     * @return The vertical trace line (possibly <code>null</code>).
     */
    protected Line2D getVerticalTraceLine() {
        return this.verticalTraceLine;   
    }
    
    /**
     * Sets the vertical trace line.
     * 
     * @param line  the line (<code>null</code> permitted).
     */
    protected void setVerticalTraceLine(Line2D line) {
        this.verticalTraceLine = line;   
    }

    /**
     * Returns <code>true</code> if file extensions should be enforced, and 
     * <code>false</code> otherwise.
     *
     * @return The flag.
     */
    public boolean isEnforceFileExtensions() {
        return this.enforceFileExtensions;
    }

    /**
     * Sets a flag that controls whether or not file extensions are enforced.
     *
     * @param enforce  the new flag value.
     */
    public void setEnforceFileExtensions(boolean enforce) {
        this.enforceFileExtensions = enforce;
    }
    
    /**
     * Returns the flag that controls whether or not zoom operations are 
     * centered around the current anchor point.
     * 
     * @return A boolean.
     * 
     * @since 1.0.7
     * 
     * @see #setZoomAroundAnchor(boolean)
     */
    public boolean getZoomAroundAnchor() {
        return this.zoomAroundAnchor;
    }
    
    /**
     * Sets the flag that controls whether or not zoom operations are
     * centered around the current anchor point.
     * 
     * @param zoomAroundAnchor  the new flag value.
     * 
     * @since 1.0.7
     * 
     * @see #getZoomAroundAnchor()
     */
    public void setZoomAroundAnchor(boolean zoomAroundAnchor) {
        this.zoomAroundAnchor = zoomAroundAnchor;
    }

    /**
     * Switches the display of tooltips for the panel on or off.  Note that 
     * tooltips can only be displayed if the chart has been configured to
     * generate tooltip items.
     *
     * @param flag  <code>true</code> to enable tooltips, <code>false</code> to
     *              disable tooltips.
     */
    public void setDisplayToolTips(boolean flag) {
        if (flag) {
            ToolTipManager.sharedInstance().registerComponent(this);
        }
        else {
            ToolTipManager.sharedInstance().unregisterComponent(this);
        }
    }

    /**
     * Returns a string for the tooltip.
     *
     * @param e  the mouse event.
     *
     * @return A tool tip or <code>null</code> if no tooltip is available.
     */
    public String getToolTipText(MouseEvent e) {

        String result = null;
        if (this.info != null) {
            EntityCollection entities = this.info.getEntityCollection();
            if (entities != null) {
                Insets insets = getInsets();
                ChartEntity entity = entities.getEntity(
                        (int) ((e.getX() - insets.left) / this.scaleX),
                        (int) ((e.getY() - insets.top) / this.scaleY));
                if (entity != null) {
                    result = entity.getToolTipText();
                }
            }
        }
        return result;

    }

    /**
     * Translates a Java2D point on the chart to a screen location.
     *
     * @param java2DPoint  the Java2D point.
     *
     * @return The screen location.
     */
    public Point translateJava2DToScreen(Point2D java2DPoint) {
        Insets insets = getInsets();
        int x = (int) (java2DPoint.getX() * this.scaleX + insets.left);
        int y = (int) (java2DPoint.getY() * this.scaleY + insets.top);
        return new Point(x, y);
    }

    /**
     * Translates a panel (component) location to a Java2D point.
     *
     * @param screenPoint  the screen location (<code>null</code> not 
     *                     permitted).
     *
     * @return The Java2D coordinates.
     */
    public Point2D translateScreenToJava2D(Point screenPoint) {
        Insets insets = getInsets();
        double x = (screenPoint.getX() - insets.left) / this.scaleX;
        double y = (screenPoint.getY() - insets.top) / this.scaleY;
        return new Point2D.Double(x, y);
    }

    /**
     * Applies any scaling that is in effect for the chart drawing to the
     * given rectangle.
     *  
     * @param rect  the rectangle.
     * 
     * @return A new scaled rectangle.
     */
    public Rectangle2D scale(Rectangle2D rect) {
        Insets insets = getInsets();
        double x = rect.getX() * getScaleX() + insets.left;
        double y = rect.getY() * this.getScaleY() + insets.top;
        double w = rect.getWidth() * this.getScaleX();
        double h = rect.getHeight() * this.getScaleY();
        return new Rectangle2D.Double(x, y, w, h);
    }

    /**
     * Returns the chart entity at a given point.
     * <P>
     * This method will return null if there is (a) no entity at the given 
     * point, or (b) no entity collection has been generated.
     *
     * @param viewX  the x-coordinate.
     * @param viewY  the y-coordinate.
     *
     * @return The chart entity (possibly <code>null</code>).
     */
    public ChartEntity getEntityForPoint(int viewX, int viewY) {

        ChartEntity result = null;
        if (this.info != null) {
            Insets insets = getInsets();
            double x = (viewX - insets.left) / this.scaleX;
            double y = (viewY - insets.top) / this.scaleY;
            EntityCollection entities = this.info.getEntityCollection();
            result = entities != null ? entities.getEntity(x, y) : null; 
        }
        return result;

    }

    /**
     * Returns the flag that controls whether or not the offscreen buffer
     * needs to be refreshed.
     * 
     * @return A boolean.
     */
    public boolean getRefreshBuffer() {
        return this.refreshBuffer;
    }
    
    /**
     * Sets the refresh buffer flag.  This flag is used to avoid unnecessary
     * redrawing of the chart when the offscreen image buffer is used.
     *
     * @param flag  <code>true</code> indicates that the buffer should be 
     *              refreshed.
     */
    public void setRefreshBuffer(boolean flag) {
        this.refreshBuffer = flag;
    }

    /**
     * Paints the component by drawing the chart to fill the entire component,
     * but allowing for the insets (which will be non-zero if a border has been
     * set for this component).  To increase performance (at the expense of
     * memory), an off-screen buffer image can be used.
     *
     * @param g  the graphics device for drawing on.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (this.chart == null) {
            return;
        }
        Graphics2D g2 = (Graphics2D) g.create();

        // first determine the size of the chart rendering area...
        Dimension size = getSize();
        Insets insets = getInsets();
        Rectangle2D available = new Rectangle2D.Double(insets.left, insets.top,
                size.getWidth() - insets.left - insets.right,
                size.getHeight() - insets.top - insets.bottom);

        // work out if scaling is required...
        boolean scale = false;
        double drawWidth = available.getWidth();
        double drawHeight = available.getHeight();
        this.scaleX = 1.0;
        this.scaleY = 1.0;

        if (drawWidth < this.minimumDrawWidth) {
            this.scaleX = drawWidth / this.minimumDrawWidth;
            drawWidth = this.minimumDrawWidth;
            scale = true;
        }
        else if (drawWidth > this.maximumDrawWidth) {
            this.scaleX = drawWidth / this.maximumDrawWidth;
            drawWidth = this.maximumDrawWidth;
            scale = true;
        }

        if (drawHeight < this.minimumDrawHeight) {
            this.scaleY = drawHeight / this.minimumDrawHeight;
            drawHeight = this.minimumDrawHeight;
            scale = true;
        }
        else if (drawHeight > this.maximumDrawHeight) {
            this.scaleY = drawHeight / this.maximumDrawHeight;
            drawHeight = this.maximumDrawHeight;
            scale = true;
        }

        Rectangle2D chartArea = new Rectangle2D.Double(0.0, 0.0, drawWidth, 
                drawHeight);

        // are we using the chart buffer?
        if (this.useBuffer) {

            // if buffer is being refreshed, it needs clearing unless it is
            // new - use the following flag to track this...
            boolean clearBuffer = true;
            
            // do we need to resize the buffer?
            if ((this.chartBuffer == null) 
                    || (this.chartBufferWidth != available.getWidth())
                    || (this.chartBufferHeight != available.getHeight())) {
                this.chartBufferWidth = (int) available.getWidth();
                this.chartBufferHeight = (int) available.getHeight();
                this.chartBuffer = createImage(this.chartBufferWidth, 
                        this.chartBufferHeight);
//                GraphicsConfiguration gc = g2.getDeviceConfiguration();
//                this.chartBuffer = gc.createCompatibleImage(
//                        this.chartBufferWidth, this.chartBufferHeight, 
//                        Transparency.TRANSLUCENT);
                this.refreshBuffer = true;
                clearBuffer = false;  // buffer is new, no clearing required
            }

            // do we need to redraw the buffer?
            if (this.refreshBuffer) {

                Rectangle2D bufferArea = new Rectangle2D.Double(
                        0, 0, this.chartBufferWidth, this.chartBufferHeight);

                Graphics2D bufferG2 = (Graphics2D) 
                        this.chartBuffer.getGraphics();
                if (clearBuffer) {
                    bufferG2.clearRect(0, 0, this.chartBufferWidth, 
                            this.chartBufferHeight);
                }
                if (scale) {
                    AffineTransform saved = bufferG2.getTransform();
                    AffineTransform st = AffineTransform.getScaleInstance(
                            this.scaleX, this.scaleY);
                    bufferG2.transform(st);
                    this.chart.draw(bufferG2, chartArea, this.anchor, 
                            this.info);
                    bufferG2.setTransform(saved);
                }
                else {
                    this.chart.draw(bufferG2, bufferArea, this.anchor, 
                            this.info);
                }

                this.refreshBuffer = false;

            }

            // zap the buffer onto the panel...
            g2.drawImage(this.chartBuffer, insets.left, insets.top, this);

        }

        // or redrawing the chart every time...
        else {

            AffineTransform saved = g2.getTransform();
            g2.translate(insets.left, insets.top);
            if (scale) {
                AffineTransform st = AffineTransform.getScaleInstance(
                        this.scaleX, this.scaleY);
                g2.transform(st);
            }
            this.chart.draw(g2, chartArea, this.anchor, this.info);
            g2.setTransform(saved);

        }
        
        // Redraw the zoom rectangle (if present)
        drawZoomRectangle(g2);
        
        g2.dispose();

        this.anchor = null;
        this.verticalTraceLine = null;
        this.horizontalTraceLine = null;

    }

    /**
     * Receives notification of changes to the chart, and redraws the chart.
     *
     * @param event  details of the chart change event.
     */
    public void chartChanged(ChartChangeEvent event) {
        this.refreshBuffer = true;
        Plot plot = this.chart.getPlot();
        if (plot instanceof Zoomable) {
            Zoomable z = (Zoomable) plot;
            this.orientation = z.getOrientation();
        }
        repaint();
    }

    /**
     * Receives notification of a chart progress event.
     *
     * @param event  the event.
     */
    public void chartProgress(ChartProgressEvent event) {
        // does nothing - override if necessary
    }

    /**
     * Handles action events generated by the popup menu.
     *
     * @param event  the event.
     */
    public void actionPerformed(ActionEvent event) {

        String command = event.getActionCommand();

        // many of the zoom methods need a screen location - all we have is 
        // the zoomPoint, but it might be null.  Here we grab the x and y
        // coordinates, or use defaults...
        double screenX = -1.0;
        double screenY = -1.0;
        if (this.zoomPoint != null) {
            screenX = this.zoomPoint.getX();
            screenY = this.zoomPoint.getY();
        }
        
        if (command.equals(PROPERTIES_COMMAND)) {
            doEditChartProperties();
        }
        else if (command.equals(SAVE_COMMAND)) {
            try {
                doSaveAs();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (command.equals(PRINT_COMMAND)) {
            createChartPrintJob();
        }
        else if (command.equals(ZOOM_IN_BOTH_COMMAND)) {
            zoomInBoth(screenX, screenY);
        }
        else if (command.equals(ZOOM_IN_DOMAIN_COMMAND)) {
            zoomInDomain(screenX, screenY);
        }
        else if (command.equals(ZOOM_IN_RANGE_COMMAND)) {
            zoomInRange(screenX, screenY);
        }
        else if (command.equals(ZOOM_OUT_BOTH_COMMAND)) {
            zoomOutBoth(screenX, screenY);
        }
        else if (command.equals(ZOOM_OUT_DOMAIN_COMMAND)) {
            zoomOutDomain(screenX, screenY);
        }
        else if (command.equals(ZOOM_OUT_RANGE_COMMAND)) {
            zoomOutRange(screenX, screenY);
        }
        else if (command.equals(ZOOM_RESET_BOTH_COMMAND)) {
            restoreAutoBounds();
        }
        else if (command.equals(ZOOM_RESET_DOMAIN_COMMAND)) {
            restoreAutoDomainBounds();
        }
        else if (command.equals(ZOOM_RESET_RANGE_COMMAND)) {
            restoreAutoRangeBounds();
        }

    }

    /**
     * Handles a 'mouse entered' event. This method changes the tooltip delays
     * of ToolTipManager.sharedInstance() to the possibly different values set 
     * for this chart panel. 
     *
     * @param e  the mouse event.
     */
    public void mouseEntered(MouseEvent e) {
        if (!this.ownToolTipDelaysActive) {
            ToolTipManager ttm = ToolTipManager.sharedInstance();
            
            this.originalToolTipInitialDelay = ttm.getInitialDelay();
            ttm.setInitialDelay(this.ownToolTipInitialDelay);
    
            this.originalToolTipReshowDelay = ttm.getReshowDelay();
            ttm.setReshowDelay(this.ownToolTipReshowDelay);
            
            this.originalToolTipDismissDelay = ttm.getDismissDelay();
            ttm.setDismissDelay(this.ownToolTipDismissDelay);
    
            this.ownToolTipDelaysActive = true;
        }
    }

    /**
     * Handles a 'mouse exited' event. This method resets the tooltip delays of
     * ToolTipManager.sharedInstance() to their
     * original values in effect before mouseEntered()
     *
     * @param e  the mouse event.
     */
    public void mouseExited(MouseEvent e) {
        if (this.ownToolTipDelaysActive) {
            // restore original tooltip dealys 
            ToolTipManager ttm = ToolTipManager.sharedInstance();       
            ttm.setInitialDelay(this.originalToolTipInitialDelay);
            ttm.setReshowDelay(this.originalToolTipReshowDelay);
            ttm.setDismissDelay(this.originalToolTipDismissDelay);
            this.ownToolTipDelaysActive = false;
        }
    }

    /**
     * Handles a 'mouse pressed' event.
     * <P>
     * This event is the popup trigger on Unix/Linux.  For Windows, the popup
     * trigger is the 'mouse released' event.
     *
     * @param e  The mouse event.
     */
    public void mousePressed(MouseEvent e) {
        if (this.zoomRectangle == null) {
            Rectangle2D screenDataArea = getScreenDataArea(e.getX(), e.getY());
            if (screenDataArea != null) {
                this.zoomPoint = getPointInRectangle(e.getX(), e.getY(), 
                        screenDataArea);
            }
            else {
                this.zoomPoint = null;
            }
            if (e.isPopupTrigger()) {
                if (this.popup != null) {
                    displayPopupMenu(e.getX(), e.getY());
                }
            }
        }
    }
    
    /**
     * Returns a point based on (x, y) but constrained to be within the bounds
     * of the given rectangle.  This method could be moved to JCommon.
     * 
     * @param x  the x-coordinate.
     * @param y  the y-coordinate.
     * @param area  the rectangle (<code>null</code> not permitted).
     * 
     * @return A point within the rectangle.
     */
    private Point getPointInRectangle(int x, int y, Rectangle2D area) {
        x = (int) Math.max(Math.ceil(area.getMinX()), Math.min(x, 
                Math.floor(area.getMaxX())));   
        y = (int) Math.max(Math.ceil(area.getMinY()), Math.min(y, 
                Math.floor(area.getMaxY())));
        return new Point(x, y);
    }

    /**
     * Handles a 'mouse dragged' event.
     *
     * @param e  the mouse event.
     */
    public void mouseDragged(MouseEvent e) {

        // if the popup menu has already been triggered, then ignore dragging...
        if (this.popup != null && this.popup.isShowing()) {
            return;
        }
        // if no initial zoom point was set, ignore dragging...
        if (this.zoomPoint == null) {
            return;
        }
        Graphics2D g2 = (Graphics2D) getGraphics();

        // Erase the previous zoom rectangle (if any)...
        drawZoomRectangle(g2);

        boolean hZoom = false;
        boolean vZoom = false;
        if (this.orientation == PlotOrientation.HORIZONTAL) {
            hZoom = this.rangeZoomable;
            vZoom = this.domainZoomable;
        }
        else {
            hZoom = this.domainZoomable;              
            vZoom = this.rangeZoomable;
        }
        Rectangle2D scaledDataArea = getScreenDataArea(
                (int) this.zoomPoint.getX(), (int) this.zoomPoint.getY());
        if (hZoom && vZoom) {
            // selected rectangle shouldn't extend outside the data area...
            double xmax = Math.min(e.getX(), scaledDataArea.getMaxX());
            double ymax = Math.min(e.getY(), scaledDataArea.getMaxY());
            this.zoomRectangle = new Rectangle2D.Double(
                    this.zoomPoint.getX(), this.zoomPoint.getY(),
                    xmax - this.zoomPoint.getX(), ymax - this.zoomPoint.getY());
        }
        else if (hZoom) {
            double xmax = Math.min(e.getX(), scaledDataArea.getMaxX());
            this.zoomRectangle = new Rectangle2D.Double(
                    this.zoomPoint.getX(), scaledDataArea.getMinY(),
                    xmax - this.zoomPoint.getX(), scaledDataArea.getHeight());
        }
        else if (vZoom) {
            double ymax = Math.min(e.getY(), scaledDataArea.getMaxY());
            this.zoomRectangle = new Rectangle2D.Double(
                    scaledDataArea.getMinX(), this.zoomPoint.getY(),
                    scaledDataArea.getWidth(), ymax - this.zoomPoint.getY());
        }

        // Draw the new zoom rectangle...
        drawZoomRectangle(g2);
        
        g2.dispose();

    }

    /**
     * Handles a 'mouse released' event.  On Windows, we need to check if this 
     * is a popup trigger, but only if we haven't already been tracking a zoom
     * rectangle.
     *
     * @param e  information about the event.
     */
    public void mouseReleased(MouseEvent e) {

        if (this.zoomRectangle != null) {
            boolean hZoom = false;
            boolean vZoom = false;
            if (this.orientation == PlotOrientation.HORIZONTAL) {
                hZoom = this.rangeZoomable;
                vZoom = this.domainZoomable;
            }
            else {
                hZoom = this.domainZoomable;              
                vZoom = this.rangeZoomable;
            }
            
            boolean zoomTrigger1 = hZoom && Math.abs(e.getX() 
                - this.zoomPoint.getX()) >= this.zoomTriggerDistance;
            boolean zoomTrigger2 = vZoom && Math.abs(e.getY() 
                - this.zoomPoint.getY()) >= this.zoomTriggerDistance;
            if (zoomTrigger1 || zoomTrigger2) {
                if ((hZoom && (e.getX() < this.zoomPoint.getX())) 
                    || (vZoom && (e.getY() < this.zoomPoint.getY()))) {
                    restoreAutoBounds();
                }
                else {
                    double x, y, w, h;
                    Rectangle2D screenDataArea = getScreenDataArea(
                            (int) this.zoomPoint.getX(), 
                            (int) this.zoomPoint.getY());
                    // for mouseReleased event, (horizontalZoom || verticalZoom)
                    // will be true, so we can just test for either being false;
                    // otherwise both are true
                    if (!vZoom) {
                        x = this.zoomPoint.getX();
                        y = screenDataArea.getMinY();
                        w = Math.min(this.zoomRectangle.getWidth(),
                                screenDataArea.getMaxX() 
                                - this.zoomPoint.getX());
                        h = screenDataArea.getHeight();
                    }
                    else if (!hZoom) {
                        x = screenDataArea.getMinX();
                        y = this.zoomPoint.getY();
                        w = screenDataArea.getWidth();
                        h = Math.min(this.zoomRectangle.getHeight(),
                                screenDataArea.getMaxY() 
                                - this.zoomPoint.getY());
                    }
                    else {
                        x = this.zoomPoint.getX();
                        y = this.zoomPoint.getY();
                        w = Math.min(this.zoomRectangle.getWidth(),
                                screenDataArea.getMaxX() 
                                - this.zoomPoint.getX());
                        h = Math.min(this.zoomRectangle.getHeight(),
                                screenDataArea.getMaxY() 
                                - this.zoomPoint.getY());
                    }
                    Rectangle2D zoomArea = new Rectangle2D.Double(x, y, w, h);
                    zoom(zoomArea);
                }
                this.zoomPoint = null;
                this.zoomRectangle = null;
            }
            else {
                // Erase the zoom rectangle
                Graphics2D g2 = (Graphics2D) getGraphics();
                drawZoomRectangle(g2);
                g2.dispose();
                this.zoomPoint = null;
                this.zoomRectangle = null;
            }

        }

        else if (e.isPopupTrigger()) {
            if (this.popup != null) {
                displayPopupMenu(e.getX(), e.getY());
            }
        }

    }

    /**
     * Receives notification of mouse clicks on the panel. These are
     * translated and passed on to any registered chart mouse click listeners.
     *
     * @param event  Information about the mouse event.
     */
    public void mouseClicked(MouseEvent event) {

        Insets insets = getInsets();
        int x = (int) ((event.getX() - insets.left) / this.scaleX);
        int y = (int) ((event.getY() - insets.top) / this.scaleY);

        this.anchor = new Point2D.Double(x, y);
        if (this.chart == null) {
            return;
        }
        this.chart.setNotify(true);  // force a redraw 
        // new entity code...
        Object[] listeners = this.chartMouseListeners.getListeners(
                ChartMouseListener.class);
        if (listeners.length == 0) {
            return;
        }

        ChartEntity entity = null;
        if (this.info != null) {
            EntityCollection entities = this.info.getEntityCollection();
            if (entities != null) {
                entity = entities.getEntity(x, y);
            }
        }
        ChartMouseEvent chartEvent = new ChartMouseEvent(getChart(), event, 
                entity);
        for (int i = listeners.length - 1; i >= 0; i -= 1) {
            ((ChartMouseListener) listeners[i]).chartMouseClicked(chartEvent);
        }

    }

    /**
     * Implementation of the MouseMotionListener's method.
     *
     * @param e  the event.
     */
    public void mouseMoved(MouseEvent e) {
        Graphics2D g2 = (Graphics2D) getGraphics();
        if (this.horizontalAxisTrace) {
            drawHorizontalAxisTrace(g2, e.getX());
        }
        if (this.verticalAxisTrace) {
            drawVerticalAxisTrace(g2, e.getY());
        }
        g2.dispose();
        
        Object[] listeners = this.chartMouseListeners.getListeners(
                ChartMouseListener.class);
        if (listeners.length == 0) {
            return;
        }
        Insets insets = getInsets();
        int x = (int) ((e.getX() - insets.left) / this.scaleX);
        int y = (int) ((e.getY() - insets.top) / this.scaleY);

        ChartEntity entity = null;
        if (this.info != null) {
            EntityCollection entities = this.info.getEntityCollection();
            if (entities != null) {
                entity = entities.getEntity(x, y);
            }
        }
        
        // we can only generate events if the panel's chart is not null
        // (see bug report 1556951)
        if (this.chart != null) {
            ChartMouseEvent event = new ChartMouseEvent(getChart(), e, entity);
            for (int i = listeners.length - 1; i >= 0; i -= 1) {
                ((ChartMouseListener) listeners[i]).chartMouseMoved(event);
            }
        }

    }

    /**
     * Zooms in on an anchor point (specified in screen coordinate space).
     *
     * @param x  the x value (in screen coordinates).
     * @param y  the y value (in screen coordinates).
     */
    public void zoomInBoth(double x, double y) {
        zoomInDomain(x, y);
        zoomInRange(x, y);
    }

    /**
     * Decreases the length of the domain axis, centered about the given
     * coordinate on the screen.  The length of the domain axis is reduced
     * by the value of {@link #getZoomInFactor()}.
     *
     * @param x  the x coordinate (in screen coordinates).
     * @param y  the y-coordinate (in screen coordinates).
     */
    public void zoomInDomain(double x, double y) {
        Plot p = this.chart.getPlot();
        if (p instanceof Zoomable) {
            Zoomable plot = (Zoomable) p;
            plot.zoomDomainAxes(this.zoomInFactor, this.info.getPlotInfo(), 
                    translateScreenToJava2D(new Point((int) x, (int) y)),
                    this.zoomAroundAnchor);
        }
    }

    /**
     * Decreases the length of the range axis, centered about the given
     * coordinate on the screen.  The length of the range axis is reduced by
     * the value of {@link #getZoomInFactor()}.
     *
     * @param x  the x-coordinate (in screen coordinates).
     * @param y  the y coordinate (in screen coordinates).
     */
    public void zoomInRange(double x, double y) {
        Plot p = this.chart.getPlot();
        if (p instanceof Zoomable) {
            Zoomable z = (Zoomable) p;
            z.zoomRangeAxes(this.zoomInFactor, this.info.getPlotInfo(), 
                    translateScreenToJava2D(new Point((int) x, (int) y)), 
                    this.zoomAroundAnchor);
        }
    }

    /**
     * Zooms out on an anchor point (specified in screen coordinate space).
     *
     * @param x  the x value (in screen coordinates).
     * @param y  the y value (in screen coordinates).
     */
    public void zoomOutBoth(double x, double y) {
        zoomOutDomain(x, y);
        zoomOutRange(x, y);
    }

    /**
     * Increases the length of the domain axis, centered about the given
     * coordinate on the screen.  The length of the domain axis is increased
     * by the value of {@link #getZoomOutFactor()}.
     *
     * @param x  the x coordinate (in screen coordinates).
     * @param y  the y-coordinate (in screen coordinates).
     */
    public void zoomOutDomain(double x, double y) {
        Plot p = this.chart.getPlot();
        if (p instanceof Zoomable) {
            Zoomable z = (Zoomable) p;
            z.zoomDomainAxes(this.zoomOutFactor, this.info.getPlotInfo(), 
                    translateScreenToJava2D(new Point((int) x, (int) y)),
                    this.zoomAroundAnchor);
        }
    }

    /**
     * Increases the length the range axis, centered about the given
     * coordinate on the screen.  The length of the range axis is increased
     * by the value of {@link #getZoomOutFactor()}.
     *
     * @param x  the x coordinate (in screen coordinates).
     * @param y  the y-coordinate (in screen coordinates).
     */
    public void zoomOutRange(double x, double y) {
        Plot p = this.chart.getPlot();
        if (p instanceof Zoomable) {
            Zoomable z = (Zoomable) p;
            z.zoomRangeAxes(this.zoomOutFactor, this.info.getPlotInfo(), 
                    translateScreenToJava2D(new Point((int) x, (int) y)),
                    this.zoomAroundAnchor);
        }
    }

    /**
     * Zooms in on a selected region.
     *
     * @param selection  the selected region.
     */
    public void zoom(Rectangle2D selection) {

        // get the origin of the zoom selection in the Java2D space used for
        // drawing the chart (that is, before any scaling to fit the panel)
        Point2D selectOrigin = translateScreenToJava2D(new Point(
                (int) Math.ceil(selection.getX()), 
                (int) Math.ceil(selection.getY())));
        PlotRenderingInfo plotInfo = this.info.getPlotInfo();
        Rectangle2D scaledDataArea = getScreenDataArea(
                (int) selection.getCenterX(), (int) selection.getCenterY());
        if ((selection.getHeight() > 0) && (selection.getWidth() > 0)) {

            double hLower = (selection.getMinX() - scaledDataArea.getMinX()) 
                / scaledDataArea.getWidth();
            double hUpper = (selection.getMaxX() - scaledDataArea.getMinX()) 
                / scaledDataArea.getWidth();
            double vLower = (scaledDataArea.getMaxY() - selection.getMaxY()) 
                / scaledDataArea.getHeight();
            double vUpper = (scaledDataArea.getMaxY() - selection.getMinY()) 
                / scaledDataArea.getHeight();

            Plot p = this.chart.getPlot();
            if (p instanceof Zoomable) {
                Zoomable z = (Zoomable) p;
                if (z.getOrientation() == PlotOrientation.HORIZONTAL) {
                    z.zoomDomainAxes(vLower, vUpper, plotInfo, selectOrigin);
                    z.zoomRangeAxes(hLower, hUpper, plotInfo, selectOrigin);
                }
                else {
                    z.zoomDomainAxes(hLower, hUpper, plotInfo, selectOrigin);
                    z.zoomRangeAxes(vLower, vUpper, plotInfo, selectOrigin);
                }
            }

        }

    }

    /**
     * Restores the auto-range calculation on both axes.
     */
    public void restoreAutoBounds() {
        restoreAutoDomainBounds();
        restoreAutoRangeBounds();
    }

    /**
     * Restores the auto-range calculation on the domain axis.
     */
    public void restoreAutoDomainBounds() {
        Plot p = this.chart.getPlot();
        if (p instanceof Zoomable) {
            Zoomable z = (Zoomable) p;
            // we need to guard against this.zoomPoint being null
            Point zp = (this.zoomPoint != null ? this.zoomPoint : new Point());
            z.zoomDomainAxes(0.0, this.info.getPlotInfo(), zp);
        }
    }

    /**
     * Restores the auto-range calculation on the range axis.
     */
    public void restoreAutoRangeBounds() {
        Plot p = this.chart.getPlot();
        if (p instanceof Zoomable) {
            Zoomable z = (Zoomable) p;
            // we need to guard against this.zoomPoint being null
            Point zp = (this.zoomPoint != null ? this.zoomPoint : new Point());
            z.zoomRangeAxes(0.0, this.info.getPlotInfo(), zp);
        }
    }

    /**
     * Returns the data area for the chart (the area inside the axes) with the
     * current scaling applied (that is, the area as it appears on screen).
     *
     * @return The scaled data area.
     */
    public Rectangle2D getScreenDataArea() {
        Rectangle2D dataArea = this.info.getPlotInfo().getDataArea();
        Insets insets = getInsets();
        double x = dataArea.getX() * this.scaleX + insets.left;
        double y = dataArea.getY() * this.scaleY + insets.top;
        double w = dataArea.getWidth() * this.scaleX;
        double h = dataArea.getHeight() * this.scaleY;
        return new Rectangle2D.Double(x, y, w, h);
    }
    
    /**
     * Returns the data area (the area inside the axes) for the plot or subplot,
     * with the current scaling applied.
     *
     * @param x  the x-coordinate (for subplot selection).
     * @param y  the y-coordinate (for subplot selection).
     * 
     * @return The scaled data area.
     */
    public Rectangle2D getScreenDataArea(int x, int y) {
        PlotRenderingInfo plotInfo = this.info.getPlotInfo();
        Rectangle2D result;
        if (plotInfo.getSubplotCount() == 0) {
            result = getScreenDataArea();
        } 
        else {
            // get the origin of the zoom selection in the Java2D space used for
            // drawing the chart (that is, before any scaling to fit the panel)
            Point2D selectOrigin = translateScreenToJava2D(new Point(x, y));
            int subplotIndex = plotInfo.getSubplotIndex(selectOrigin);
            if (subplotIndex == -1) {
                return null;
            }
            result = scale(plotInfo.getSubplotInfo(subplotIndex).getDataArea());
        }
        return result;
    }
    
    /**
     * Returns the initial tooltip delay value used inside this chart panel.
     *
     * @return An integer representing the initial delay value, in milliseconds.
     * 
     * @see javax.swing.ToolTipManager#getInitialDelay()
     */
    public int getInitialDelay() {
        return this.ownToolTipInitialDelay;
    }
    
    /**
     * Returns the reshow tooltip delay value used inside this chart panel.
     *
     * @return An integer representing the reshow  delay value, in milliseconds.
     * 
     * @see javax.swing.ToolTipManager#getReshowDelay()
     */
    public int getReshowDelay() {
        return this.ownToolTipReshowDelay;  
    }

    /**
     * Returns the dismissal tooltip delay value used inside this chart panel.
     *
     * @return An integer representing the dismissal delay value, in 
     *         milliseconds.
     * 
     * @see javax.swing.ToolTipManager#getDismissDelay()
     */
    public int getDismissDelay() {
        return this.ownToolTipDismissDelay; 
    }
    
    /**
     * Specifies the initial delay value for this chart panel.
     *
     * @param delay  the number of milliseconds to delay (after the cursor has 
     *               paused) before displaying. 
     * 
     * @see javax.swing.ToolTipManager#setInitialDelay(int)
     */
    public void setInitialDelay(int delay) {
        this.ownToolTipInitialDelay = delay;
    }
    
    /**
     * Specifies the amount of time before the user has to wait initialDelay 
     * milliseconds before a tooltip will be shown.
     *
     * @param delay  time in milliseconds
     * 
     * @see javax.swing.ToolTipManager#setReshowDelay(int)
     */
    public void setReshowDelay(int delay) {
        this.ownToolTipReshowDelay = delay;  
    }

    /**
     * Specifies the dismissal delay value for this chart panel.
     *
     * @param delay the number of milliseconds to delay before taking away the 
     *              tooltip
     * 
     * @see javax.swing.ToolTipManager#setDismissDelay(int)
     */
    public void setDismissDelay(int delay) {
        this.ownToolTipDismissDelay = delay; 
    }
    
    /**
     * Returns the zoom in factor.
     * 
     * @return The zoom in factor.
     * 
     * @see #setZoomInFactor(double)
     */
    public double getZoomInFactor() {
        return this.zoomInFactor;   
    }
    
    /**
     * Sets the zoom in factor.
     * 
     * @param factor  the factor.
     * 
     * @see #getZoomInFactor()
     */
    public void setZoomInFactor(double factor) {
        this.zoomInFactor = factor;
    }
    
    /**
     * Returns the zoom out factor.
     * 
     * @return The zoom out factor.
     * 
     * @see #setZoomOutFactor(double)
     */
    public double getZoomOutFactor() {
        return this.zoomOutFactor;   
    }
    
    /**
     * Sets the zoom out factor.
     * 
     * @param factor  the factor.
     * 
     * @see #getZoomOutFactor()
     */
    public void setZoomOutFactor(double factor) {
        this.zoomOutFactor = factor;
    }
    
    /**
     * Draws zoom rectangle (if present).
     * The drawing is performed in XOR mode, therefore
     * when this method is called twice in a row,
     * the second call will completely restore the state
     * of the canvas.
     * 
     * @param g2 the graphics device. 
     */
    private void drawZoomRectangle(Graphics2D g2) {
        // Set XOR mode to draw the zoom rectangle
        g2.setXORMode(Color.gray);
        if (this.zoomRectangle != null) {
            if (this.fillZoomRectangle) {
                g2.fill(this.zoomRectangle);
            }
            else {
                g2.draw(this.zoomRectangle);
            }
        }
        // Reset to the default 'overwrite' mode
        g2.setPaintMode();
    }
    
    /**
     * Draws a vertical line used to trace the mouse position to the horizontal 
     * axis.
     *
     * @param g2 the graphics device.
     * @param x  the x-coordinate of the trace line.
     */
    private void drawHorizontalAxisTrace(Graphics2D g2, int x) {

        Rectangle2D dataArea = getScreenDataArea();

        g2.setXORMode(Color.orange);
        if (((int) dataArea.getMinX() < x) && (x < (int) dataArea.getMaxX())) {

            if (this.verticalTraceLine != null) {
                g2.draw(this.verticalTraceLine);
                this.verticalTraceLine.setLine(x, (int) dataArea.getMinY(), x, 
                        (int) dataArea.getMaxY());
            }
            else {
                this.verticalTraceLine = new Line2D.Float(x, 
                        (int) dataArea.getMinY(), x, (int) dataArea.getMaxY());
            }
            g2.draw(this.verticalTraceLine);
        }

        // Reset to the default 'overwrite' mode
        g2.setPaintMode();
    }

    /**
     * Draws a horizontal line used to trace the mouse position to the vertical
     * axis.
     *
     * @param g2 the graphics device.
     * @param y  the y-coordinate of the trace line.
     */
    private void drawVerticalAxisTrace(Graphics2D g2, int y) {

        Rectangle2D dataArea = getScreenDataArea();

        g2.setXORMode(Color.orange);
        if (((int) dataArea.getMinY() < y) && (y < (int) dataArea.getMaxY())) {

            if (this.horizontalTraceLine != null) {
                g2.draw(this.horizontalTraceLine);
                this.horizontalTraceLine.setLine((int) dataArea.getMinX(), y, 
                        (int) dataArea.getMaxX(), y);
            }
            else {
                this.horizontalTraceLine = new Line2D.Float(
                        (int) dataArea.getMinX(), y, (int) dataArea.getMaxX(), 
                        y);
            }
            g2.draw(this.horizontalTraceLine);
        }

        // Reset to the default 'overwrite' mode
        g2.setPaintMode();
    }

    /**
     * Displays a dialog that allows the user to edit the properties for the
     * current chart.
     * 
     * @since 1.0.3
     */
    public void doEditChartProperties() {

        ChartEditor editor = ChartEditorManager.getChartEditor(this.chart);
        int result = JOptionPane.showConfirmDialog(this, editor, 
                localizationResources.getString("Chart_Properties"),
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            editor.updateChart(this.chart);
        }

    }

    /**
     * Opens a file chooser and gives the user an opportunity to save the chart
     * in PNG format.
     *
     * @throws IOException if there is an I/O error.
     */
    public void doSaveAs() throws IOException {

        JFileChooser fileChooser = new JFileChooser();
        ExtensionFileFilter filter = new ExtensionFileFilter(
                localizationResources.getString("PNG_Image_Files"), ".png");
        fileChooser.addChoosableFileFilter(filter);

        int option = fileChooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            String filename = fileChooser.getSelectedFile().getPath();
            if (isEnforceFileExtensions()) {
                if (!filename.endsWith(".png")) {
                    filename = filename + ".png";
                }
            }
            ChartUtilities.saveChartAsPNG(new File(filename), this.chart, 
                    getWidth(), getHeight());
        }

    }

    /**
     * Creates a print job for the chart.
     */
    public void createChartPrintJob() {

        PrinterJob job = PrinterJob.getPrinterJob();
        PageFormat pf = job.defaultPage();
        PageFormat pf2 = job.pageDialog(pf);
        if (pf2 != pf) {
            job.setPrintable(this, pf2);
            if (job.printDialog()) {
                try {
                    job.print();
                }
                catch (PrinterException e) {
                    JOptionPane.showMessageDialog(this, e);
                }
            }
        }

    }

    /**
     * Prints the chart on a single page.
     *
     * @param g  the graphics context.
     * @param pf  the page format to use.
     * @param pageIndex  the index of the page. If not <code>0</code>, nothing 
     *                   gets print.
     *
     * @return The result of printing.
     */
    public int print(Graphics g, PageFormat pf, int pageIndex) {

        if (pageIndex != 0) {
            return NO_SUCH_PAGE;
        }
        Graphics2D g2 = (Graphics2D) g;
        double x = pf.getImageableX();
        double y = pf.getImageableY();
        double w = pf.getImageableWidth();
        double h = pf.getImageableHeight();
        this.chart.draw(g2, new Rectangle2D.Double(x, y, w, h), this.anchor, 
                null);
        return PAGE_EXISTS;

    }

    /**
     * Adds a listener to the list of objects listening for chart mouse events.
     *
     * @param listener  the listener (<code>null</code> not permitted).
     */
    public void addChartMouseListener(ChartMouseListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Null 'listener' argument.");
        }
        this.chartMouseListeners.add(ChartMouseListener.class, listener);
    }

    /**
     * Removes a listener from the list of objects listening for chart mouse 
     * events.
     *
     * @param listener  the listener.
     */
    public void removeChartMouseListener(ChartMouseListener listener) {
        this.chartMouseListeners.remove(ChartMouseListener.class, listener);
    }

    /**
     * Returns an array of the listeners of the given type registered with the
     * panel.
     * 
     * @param listenerType  the listener type.
     * 
     * @return An array of listeners.
     */
    public EventListener[] getListeners(Class listenerType) {
        if (listenerType == ChartMouseListener.class) {
            // fetch listeners from local storage
            return this.chartMouseListeners.getListeners(listenerType);
        }
        else {
            return super.getListeners(listenerType);
        }
    }

    /**
     * Creates a popup menu for the panel.
     *
     * @param properties  include a menu item for the chart property editor.
     * @param save  include a menu item for saving the chart.
     * @param print  include a menu item for printing the chart.
     * @param zoom  include menu items for zooming.
     *
     * @return The popup menu.
     */
    protected JPopupMenu createPopupMenu(boolean properties, 
                                         boolean save, 
                                         boolean print,
                                         boolean zoom) {

        JPopupMenu result = new JPopupMenu("Chart:");
        boolean separator = false;

        if (properties) {
            JMenuItem propertiesItem = new JMenuItem(
                    localizationResources.getString("Properties..."));
            propertiesItem.setActionCommand(PROPERTIES_COMMAND);
            propertiesItem.addActionListener(this);
            result.add(propertiesItem);
            separator = true;
        }

        if (save) {
            if (separator) {
                result.addSeparator();
                separator = false;
            }
            JMenuItem saveItem = new JMenuItem(
                    localizationResources.getString("Save_as..."));
            saveItem.setActionCommand(SAVE_COMMAND);
            saveItem.addActionListener(this);
            result.add(saveItem);
            separator = true;
        }

        if (print) {
            if (separator) {
                result.addSeparator();
                separator = false;
            }
            JMenuItem printItem = new JMenuItem(
                    localizationResources.getString("Print..."));
            printItem.setActionCommand(PRINT_COMMAND);
            printItem.addActionListener(this);
            result.add(printItem);
            separator = true;
        }

        if (zoom) {
            if (separator) {
                result.addSeparator();
                separator = false;
            }

            JMenu zoomInMenu = new JMenu(
                    localizationResources.getString("Zoom_In"));

            this.zoomInBothMenuItem = new JMenuItem(
                    localizationResources.getString("All_Axes"));
            this.zoomInBothMenuItem.setActionCommand(ZOOM_IN_BOTH_COMMAND);
            this.zoomInBothMenuItem.addActionListener(this);
            zoomInMenu.add(this.zoomInBothMenuItem);

            zoomInMenu.addSeparator();

            this.zoomInDomainMenuItem = new JMenuItem(
                    localizationResources.getString("Domain_Axis"));
            this.zoomInDomainMenuItem.setActionCommand(ZOOM_IN_DOMAIN_COMMAND);
            this.zoomInDomainMenuItem.addActionListener(this);
            zoomInMenu.add(this.zoomInDomainMenuItem);

            this.zoomInRangeMenuItem = new JMenuItem(
                    localizationResources.getString("Range_Axis"));
            this.zoomInRangeMenuItem.setActionCommand(ZOOM_IN_RANGE_COMMAND);
            this.zoomInRangeMenuItem.addActionListener(this);
            zoomInMenu.add(this.zoomInRangeMenuItem);

            result.add(zoomInMenu);

            JMenu zoomOutMenu = new JMenu(
                    localizationResources.getString("Zoom_Out"));

            this.zoomOutBothMenuItem = new JMenuItem(
                    localizationResources.getString("All_Axes"));
            this.zoomOutBothMenuItem.setActionCommand(ZOOM_OUT_BOTH_COMMAND);
            this.zoomOutBothMenuItem.addActionListener(this);
            zoomOutMenu.add(this.zoomOutBothMenuItem);

            zoomOutMenu.addSeparator();

            this.zoomOutDomainMenuItem = new JMenuItem(
                    localizationResources.getString("Domain_Axis"));
            this.zoomOutDomainMenuItem.setActionCommand(
                    ZOOM_OUT_DOMAIN_COMMAND);
            this.zoomOutDomainMenuItem.addActionListener(this);
            zoomOutMenu.add(this.zoomOutDomainMenuItem);

            this.zoomOutRangeMenuItem = new JMenuItem(
                    localizationResources.getString("Range_Axis"));
            this.zoomOutRangeMenuItem.setActionCommand(ZOOM_OUT_RANGE_COMMAND);
            this.zoomOutRangeMenuItem.addActionListener(this);
            zoomOutMenu.add(this.zoomOutRangeMenuItem);

            result.add(zoomOutMenu);

            JMenu autoRangeMenu = new JMenu(
                    localizationResources.getString("Auto_Range"));

            this.zoomResetBothMenuItem = new JMenuItem(
                    localizationResources.getString("All_Axes"));
            this.zoomResetBothMenuItem.setActionCommand(
                    ZOOM_RESET_BOTH_COMMAND);
            this.zoomResetBothMenuItem.addActionListener(this);
            autoRangeMenu.add(this.zoomResetBothMenuItem);

            autoRangeMenu.addSeparator();
            this.zoomResetDomainMenuItem = new JMenuItem(
                    localizationResources.getString("Domain_Axis"));
            this.zoomResetDomainMenuItem.setActionCommand(
                    ZOOM_RESET_DOMAIN_COMMAND);
            this.zoomResetDomainMenuItem.addActionListener(this);
            autoRangeMenu.add(this.zoomResetDomainMenuItem);

            this.zoomResetRangeMenuItem = new JMenuItem(
                    localizationResources.getString("Range_Axis"));
            this.zoomResetRangeMenuItem.setActionCommand(
                    ZOOM_RESET_RANGE_COMMAND);
            this.zoomResetRangeMenuItem.addActionListener(this);
            autoRangeMenu.add(this.zoomResetRangeMenuItem);

            result.addSeparator();
            result.add(autoRangeMenu);

        }

        return result;

    }

    /**
     * The idea is to modify the zooming options depending on the type of chart 
     * being displayed by the panel.
     *
     * @param x  horizontal position of the popup.
     * @param y  vertical position of the popup.
     */
    protected void displayPopupMenu(int x, int y) {

        if (this.popup != null) {

            // go through each zoom menu item and decide whether or not to 
            // enable it...
            Plot plot = this.chart.getPlot();
            boolean isDomainZoomable = false;
            boolean isRangeZoomable = false;
            if (plot instanceof Zoomable) {
                Zoomable z = (Zoomable) plot;
                isDomainZoomable = z.isDomainZoomable();
                isRangeZoomable = z.isRangeZoomable();
            }
            
            if (this.zoomInDomainMenuItem != null) {
                this.zoomInDomainMenuItem.setEnabled(isDomainZoomable);
            }
            if (this.zoomOutDomainMenuItem != null) {
                this.zoomOutDomainMenuItem.setEnabled(isDomainZoomable);
            } 
            if (this.zoomResetDomainMenuItem != null) {
                this.zoomResetDomainMenuItem.setEnabled(isDomainZoomable);
            }

            if (this.zoomInRangeMenuItem != null) {
                this.zoomInRangeMenuItem.setEnabled(isRangeZoomable);
            }
            if (this.zoomOutRangeMenuItem != null) {
                this.zoomOutRangeMenuItem.setEnabled(isRangeZoomable);
            }

            if (this.zoomResetRangeMenuItem != null) {
                this.zoomResetRangeMenuItem.setEnabled(isRangeZoomable);
            }

            if (this.zoomInBothMenuItem != null) {
                this.zoomInBothMenuItem.setEnabled(isDomainZoomable 
                        && isRangeZoomable);
            }
            if (this.zoomOutBothMenuItem != null) {
                this.zoomOutBothMenuItem.setEnabled(isDomainZoomable 
                        && isRangeZoomable);
            }
            if (this.zoomResetBothMenuItem != null) {
                this.zoomResetBothMenuItem.setEnabled(isDomainZoomable 
                        && isRangeZoomable);
            }

            this.popup.show(this, x, y);
        }

    }
    
    /* (non-Javadoc)
     * @see javax.swing.JPanel#updateUI()
     */
    public void updateUI() {
        // here we need to update the UI for the popup menu, if the panel
        // has one...
        if (this.popup != null) {
            SwingUtilities.updateComponentTreeUI(this.popup);
        }
        super.updateUI();
    }

}
