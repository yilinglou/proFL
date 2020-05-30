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
 * -----------------------
 * LabelsPackageTests.java
 * -----------------------
 * (C) Copyright 2003-2007, by Object Refinery Limited.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: LabelsPackageTests.java,v 1.1.2.3 2007/02/20 15:32:06 mungady Exp $
 *
 * Changes:
 * --------
 * 21-Mar-2003 : Version 1 (DG);
 * 13-Aug-2003 : Renamed class, added tests (DG);
 * 19-Jan-2005 : Added main() method to run JUnit in text mode (DG);
 * 20-Feb-2007 : Added MultipleXYSeriesLabelGeneratorTests (DG);
 * 19-Jun-2007 : Removed StandardContourToolTipGeneratorTests (DG);
 *
 */

package org.jfree.chart.labels.junit;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * A collection of tests for the <code>org.jfree.chart.labels</code> package.
 * <P>
 * These tests can be run using JUnit (http://www.junit.org).
 */
public class LabelsPackageTests extends TestCase {

    /**
     * Returns a test suite to the JUnit test runner.
     *
     * @return The test suite.
     */
    public static Test suite() {
        TestSuite suite = new TestSuite("org.jfree.chart.labels");
        suite.addTestSuite(BoxAndWhiskerToolTipGeneratorTests.class);
        suite.addTestSuite(BoxAndWhiskerXYToolTipGeneratorTests.class);
        suite.addTestSuite(BubbleXYItemLabelGeneratorTests.class);
        suite.addTestSuite(CustomXYItemLabelGeneratorTests.class);
        suite.addTestSuite(HighLowItemLabelGeneratorTests.class);
        suite.addTestSuite(IntervalCategoryLabelGeneratorTests.class);
        suite.addTestSuite(ItemLabelAnchorTests.class);
        suite.addTestSuite(ItemLabelPositionTests.class);
        suite.addTestSuite(MultipleXYSeriesLabelGeneratorTests.class);
        suite.addTestSuite(StandardCategoryItemLabelGeneratorTests.class);
        suite.addTestSuite(StandardCategorySeriesLabelGeneratorTests.class);
        suite.addTestSuite(StandardCategoryToolTipGeneratorTests.class);
        suite.addTestSuite(StandardPieSectionLabelGeneratorTests.class);
        suite.addTestSuite(StandardPieToolTipGeneratorTests.class);
        suite.addTestSuite(StandardXYItemLabelGeneratorTests.class);
        suite.addTestSuite(StandardXYSeriesLabelGeneratorTests.class);
        suite.addTestSuite(StandardXYToolTipGeneratorTests.class);
        suite.addTestSuite(StandardXYZToolTipGeneratorTests.class);
        suite.addTestSuite(SymbolicXYItemLabelGeneratorTests.class);
        return suite;
    }

    /**
     * Constructs the test suite.
     *
     * @param name  the suite name.
     */
    public LabelsPackageTests(String name) {
        super(name);
    }

    /**
     * Runs the test suite using JUnit's text-based runner.
     * 
     * @param args  ignored.
     */
    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

}
