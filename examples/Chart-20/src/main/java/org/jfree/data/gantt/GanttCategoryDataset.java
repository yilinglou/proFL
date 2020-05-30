/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2005, by Object Refinery Limited and Contributors.
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
 * -------------------------
 * GanttCategoryDataset.java
 * -------------------------
 * (C) Copyright 2003-2005, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: GanttCategoryDataset.java,v 1.2.2.1 2005/10/25 21:31:44 mungady Exp $
 *
 * Changes
 * -------
 * 16-Sep-2003 : Version 1, based on MultiIntervalCategoryDataset (DG);
 * 23-Sep-2003 : Fixed Checkstyle issues (DG);
 *
 */

 package org.jfree.data.gantt;

import org.jfree.data.category.IntervalCategoryDataset;

/**
 * An extension of the {@link IntervalCategoryDataset} interface that adds 
 * support for multiple sub-intervals.
 */
public interface GanttCategoryDataset extends IntervalCategoryDataset {

    /**
     * Returns the percent complete for a given item.
     *
     * @param row  the row index (zero-based).
     * @param column  the column index (zero-based).
     *
     * @return The percent complete.
     */
    public Number getPercentComplete(int row, int column);

    /**
     * Returns the percent complete for a given item.
     *
     * @param rowKey  the row key.
     * @param columnKey  the column key.
     *
     * @return The percent complete.
     */
    public Number getPercentComplete(Comparable rowKey, Comparable columnKey);

    /**
     * Returns the number of sub-intervals for a given item.
     *
     * @param row  the row index (zero-based).
     * @param column  the column index (zero-based).
     *
     * @return The sub-interval count.
     */
    public int getSubIntervalCount(int row, int column);

    /**
     * Returns the number of sub-intervals for a given item.
     *
     * @param rowKey  the row key.
     * @param columnKey  the column key.
     *
     * @return The sub-interval count.
     */
    public int getSubIntervalCount(Comparable rowKey, Comparable columnKey);

    /**
     * Returns the start value of a sub-interval for a given item.
     *
     * @param row  the row index (zero-based).
     * @param column  the column index (zero-based).
     * @param subinterval  the sub-interval index (zero-based).
     *
     * @return The start value (possibly <code>null</code>).
     */
    public Number getStartValue(int row, int column, int subinterval);

    /**
     * Returns the start value of a sub-interval for a given item.
     *
     * @param rowKey  the row key.
     * @param columnKey  the column key.
     * @param subinterval  the sub-interval.
     *
     * @return The start value (possibly <code>null</code>).
     */
    public Number getStartValue(Comparable rowKey, Comparable columnKey, 
                                int subinterval);

    /**
     * Returns the end value of a sub-interval for a given item.
     *
     * @param row  the row index (zero-based).
     * @param column  the column index (zero-based).
     * @param subinterval  the sub-interval.
     *
     * @return The end value (possibly <code>null</code>).
     */
    public Number getEndValue(int row, int column, int subinterval);

    /**
     * Returns the end value of a sub-interval for a given item.
     *
     * @param rowKey  the row key.
     * @param columnKey  the column key.
     * @param subinterval  the sub-interval.
     *
     * @return The end value (possibly <code>null</code>).
     */
    public Number getEndValue(Comparable rowKey, Comparable columnKey, 
                              int subinterval);

    /**
     * Returns the percentage complete value of a sub-interval for a given item.
     *
     * @param row  the row index (zero-based).
     * @param column  the column index (zero-based).
     * @param subinterval  the sub-interval.
     *
     * @return The percent complete value (possibly <code>null</code>).
     */
    public Number getPercentComplete(int row, int column, int subinterval);

    /**
     * Returns the percentage complete value of a sub-interval for a given item.
     *
     * @param rowKey  the row key.
     * @param columnKey  the column key.
     * @param subinterval  the sub-interval.
     *
     * @return The precent complete value (possibly <code>null</code>).
     */
    public Number getPercentComplete(Comparable rowKey, Comparable columnKey, 
                                     int subinterval);

}
