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
 * --------------
 * LogFormat.java
 * --------------
 * (C) Copyright 2007, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: LogAxis.java,v 1.1.2.2 2007/03/22 16:39:18 mungady Exp $
 *
 * Changes
 * -------
 * 02-Aug-2007 : Version 1 (DG);
 * 
 */

package org.jfree.chart.util;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;

/**
 * A number formatter for logarithmic values.  This formatter does not support
 * parsing.
 * 
 * @since 1.0.7
 */
public class LogFormat extends NumberFormat {
    
    /** The log base value. */
    private double base;
    
    /** The natural logarithm of the base value. */
    private double baseLog;
    
    /** The label for the log base (for example, "e"). */
    private String baseLabel;
    
    /** A flag that controls whether or not the base is shown. */
    private boolean showBase;
    
    /** The number formatter for the exponent. */
    private NumberFormat formatter = new DecimalFormat("0.0");
    
    /**
     * Creates a new instance.
     * 
     * @param base  the base.
     * @param baseLabel  the base label.
     * @param showBase  a flag that controls whether or not the base value is
     *                  shown.
     */
    public LogFormat(double base, String baseLabel, boolean showBase) {
        this.base = base;
        this.baseLog = Math.log(this.base);
        this.baseLabel = baseLabel;
        this.showBase = showBase;
    }
    
    /**
     * Calculates the log of a given value.
     * 
     * @param value  the value.
     * 
     * @return The log of the value.
     */
    private double calculateLog(double value) {
        return Math.log(value) / this.baseLog;
    }
    
    /**
     * Returns a formatted representation of the specified number.
     * 
     * @param number  the number.
     * @param toAppendTo  the string buffer to append to.
     * @param pos  the position.
     * 
     * @return A string buffer containing the formatted value.
     */
    public StringBuffer format(double number, StringBuffer toAppendTo,
            FieldPosition pos) {
        StringBuffer result = new StringBuffer();
        if (this.showBase) {
            result.append(this.baseLabel);
            result.append("^");
        }
        result.append(this.formatter.format(calculateLog(number)));
        return result;
    }

    /**
     * Formats the specified number as a hexadecimal string.  The decimal 
     * fraction is ignored.
     * 
     * @param number  the number to format.
     * @param toAppendTo  the buffer to append to (ignored here).
     * @param pos  the field position (ignored here).
     * 
     * @return The string buffer.
     */
    public StringBuffer format(long number, StringBuffer toAppendTo, 
            FieldPosition pos) {
        StringBuffer result = new StringBuffer();
        if (this.showBase) {
            result.append(this.baseLabel);
            result.append("^");
        }
        result.append(this.formatter.format(calculateLog(number)));
        return result;
    }

    /**
     * Parsing is not implemented, so this method always returns 
     * <code>null</code>.
     * 
     * @param source  ignored.
     * @param parsePosition  ignored.
     * 
     * @return Always <code>null</code>.
     */
    public Number parse (String source, ParsePosition parsePosition) {
        return null; // don't bother with parsing
    }

}
