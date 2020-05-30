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
 * ImageMapUtilities.java
 * ----------------------
 * (C) Copyright 2004-2007, by Richard Atkinson and Contributors.
 *
 * Original Author:  Richard Atkinson;
 * Contributor(s):   David Gilbert (for Object Refinery Limited);
 *
 * $Id: ImageMapUtilities.java,v 1.3.2.3 2007/02/06 11:28:53 mungady Exp $
 *
 * Changes
 * -------
 * 02-Aug-2004 : Initial version (RA);
 * 13-Jan-2005 : Renamed ImageMapUtilities (DG);
 * 19-Jan-2005 : Reversed order of tags for chart entities to get correct
 *               layering (DG);
 * ------------- JFREECHART 1.0.x ---------------------------------------------
 * 06-Feb-2006 : API doc updates (DG);
 * 21-Jun-2007 : Removed JCommon dependencies (DG);
 * 
 */

package org.jfree.chart.imagemap;

import java.io.IOException;
import java.io.PrintWriter;

import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.util.StringUtilities;

/**
 * Collection of utility methods related to producing image maps.  
 * Functionality was originally in {@link org.jfree.chart.ChartUtilities}.
 */
public class ImageMapUtilities {

    /**
     * Writes an image map to an output stream.
     *
     * @param writer  the writer (<code>null</code> not permitted).
     * @param name  the map name (<code>null</code> not permitted).
     * @param info  the chart rendering info (<code>null</code> not permitted).
     *
     * @throws java.io.IOException if there are any I/O errors.
     */
    public static void writeImageMap(PrintWriter writer, String name, 
                                     ChartRenderingInfo info)
        throws IOException {

        // defer argument checking...
        ImageMapUtilities.writeImageMap(writer, name, info,
                new StandardToolTipTagFragmentGenerator(),
                new StandardURLTagFragmentGenerator());

    }

    /**
     * Writes an image map to an output stream.
     *
     * @param writer  the writer (<code>null</code> not permitted).
     * @param name  the map name (<code>null</code> not permitted).
     * @param info  the chart rendering info (<code>null</code> not permitted).
     * @param useOverLibForToolTips  whether to use OverLIB for tooltips
     *                               (http://www.bosrup.com/web/overlib/).
     *
     * @throws java.io.IOException if there are any I/O errors.
     */
    public static void writeImageMap(PrintWriter writer,
                                     String name,
                                     ChartRenderingInfo info,
                                     boolean useOverLibForToolTips) 
        throws IOException {

        ToolTipTagFragmentGenerator toolTipTagFragmentGenerator = null;
        if (useOverLibForToolTips) {
            toolTipTagFragmentGenerator 
                    = new OverLIBToolTipTagFragmentGenerator();
        }
        else {
            toolTipTagFragmentGenerator 
                    = new StandardToolTipTagFragmentGenerator();
        }
        ImageMapUtilities.writeImageMap(writer, name, info, 
                toolTipTagFragmentGenerator, 
                new StandardURLTagFragmentGenerator());

    }

    /**
     * Writes an image map to an output stream.
     *
     * @param writer  the writer (<code>null</code> not permitted).
     * @param name  the map name (<code>null</code> not permitted).
     * @param info  the chart rendering info (<code>null</code> not permitted).
     * @param toolTipTagFragmentGenerator  a generator for the HTML fragment
     *     that will contain the tooltip text (<code>null</code> not permitted 
     *     if <code>info</code> contains tooltip information).
     * @param urlTagFragmentGenerator  a generator for the HTML fragment that
     *     will contain the URL reference (<code>null</code> not permitted if 
     *     <code>info</code> contains URLs).
     *
     * @throws java.io.IOException if there are any I/O errors.
     */
    public static void writeImageMap(PrintWriter writer, String name, 
            ChartRenderingInfo info,
            ToolTipTagFragmentGenerator toolTipTagFragmentGenerator,
            URLTagFragmentGenerator urlTagFragmentGenerator) 
        throws IOException {

        writer.println(ImageMapUtilities.getImageMap(name, info, 
                toolTipTagFragmentGenerator, urlTagFragmentGenerator));
    }

    /**
     * Creates an image map element that complies with the XHTML 1.0
     * specification.
     *
     * @param name  the map name (<code>null</code> not permitted).
     * @param info  the chart rendering info (<code>null</code> not permitted).
     *
     * @return The map element.
     */
    public static String getImageMap(String name, ChartRenderingInfo info) {
        return ImageMapUtilities.getImageMap(name, info,
                new StandardToolTipTagFragmentGenerator(),
                new StandardURLTagFragmentGenerator());
    }

    /**
     * Creates an image map element that complies with the XHTML 1.0
     * specification.
     *
     * @param name  the map name (<code>null</code> not permitted).
     * @param info  the chart rendering info (<code>null</code> not permitted).
     * @param toolTipTagFragmentGenerator  a generator for the HTML fragment
     *     that will contain the tooltip text (<code>null</code> not permitted 
     *     if <code>info</code> contains tooltip information).
     * @param urlTagFragmentGenerator  a generator for the HTML fragment that
     *     will contain the URL reference (<code>null</code> not permitted if 
     *     <code>info</code> contains URLs).
     *
     * @return The map tag.
     */
    public static String getImageMap(String name, ChartRenderingInfo info,
            ToolTipTagFragmentGenerator toolTipTagFragmentGenerator,
            URLTagFragmentGenerator urlTagFragmentGenerator) {

        StringBuffer sb = new StringBuffer();
        sb.append("<map id=\"" + name + "\" name=\"" + name + "\">");
        sb.append(StringUtilities.getLineSeparator());
        EntityCollection entities = info.getEntityCollection();
        if (entities != null) {
            int count = entities.getEntityCount();
            for (int i = count - 1; i >= 0; i--) {
                ChartEntity entity = entities.getEntity(i);
                if (entity.getToolTipText() != null 
                        || entity.getURLText() != null) {
                    String area = entity.getImageMapAreaTag(
                            toolTipTagFragmentGenerator, 
                            urlTagFragmentGenerator);
                    if (area.length() > 0) {
                        sb.append(area);
                        sb.append(StringUtilities.getLineSeparator());
                    }
                }
            }
        }
        sb.append("</map>");
        return sb.toString();
        
    }

}
