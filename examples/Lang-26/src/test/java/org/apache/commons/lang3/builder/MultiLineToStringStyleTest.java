/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.lang3.builder;

import java.util.ArrayList;
import java.util.HashMap;

import junit.framework.TestCase;

import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.builder.ToStringStyleTest.Person;

/**
 * Unit tests {@link org.apache.commons.lang3.builder.MultiLineToStringStyleTest}.
 *
 * @author Apache Software Foundation
 * @version $Id$
 */
public class MultiLineToStringStyleTest extends TestCase {

    private final Integer base = new Integer(5);
    private final String baseStr = base.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(base));
    
    public MultiLineToStringStyleTest(String name) {
        super(name);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        ToStringBuilder.setDefaultStyle(ToStringStyle.MULTI_LINE_STYLE);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        ToStringBuilder.setDefaultStyle(ToStringStyle.DEFAULT_STYLE);
    }

    //----------------------------------------------------------------
    
    public void testBlank() {
        assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "]", new ToStringBuilder(base).toString());
    }

    public void testAppendSuper() {
        assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "]", new ToStringBuilder(base).appendSuper("Integer@8888[" + SystemUtils.LINE_SEPARATOR + "]").toString());
        assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  <null>" + SystemUtils.LINE_SEPARATOR + "]", new ToStringBuilder(base).appendSuper("Integer@8888[" + SystemUtils.LINE_SEPARATOR + "  <null>" + SystemUtils.LINE_SEPARATOR + "]").toString());
        
        assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  a=hello" + SystemUtils.LINE_SEPARATOR + "]", new ToStringBuilder(base).appendSuper("Integer@8888[" + SystemUtils.LINE_SEPARATOR + "]").append("a", "hello").toString());
        assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  <null>" + SystemUtils.LINE_SEPARATOR + "  a=hello" + SystemUtils.LINE_SEPARATOR + "]", new ToStringBuilder(base).appendSuper("Integer@8888[" + SystemUtils.LINE_SEPARATOR + "  <null>" + SystemUtils.LINE_SEPARATOR + "]").append("a", "hello").toString());
        assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  a=hello" + SystemUtils.LINE_SEPARATOR + "]", new ToStringBuilder(base).appendSuper(null).append("a", "hello").toString());
    }
    
    public void testObject() {
        Integer i3 = new Integer(3);
        Integer i4 = new Integer(4);
        assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  <null>" + SystemUtils.LINE_SEPARATOR + "]", new ToStringBuilder(base).append((Object) null).toString());
        assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  3" + SystemUtils.LINE_SEPARATOR + "]", new ToStringBuilder(base).append(i3).toString());
        assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  a=<null>" + SystemUtils.LINE_SEPARATOR + "]", new ToStringBuilder(base).append("a", (Object) null).toString());
        assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  a=3" + SystemUtils.LINE_SEPARATOR + "]", new ToStringBuilder(base).append("a", i3).toString());
        assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  a=3" + SystemUtils.LINE_SEPARATOR + "  b=4" + SystemUtils.LINE_SEPARATOR + "]", new ToStringBuilder(base).append("a", i3).append("b", i4).toString());
        assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  a=<Integer>" + SystemUtils.LINE_SEPARATOR + "]", new ToStringBuilder(base).append("a", i3, false).toString());
        assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  a=<size=0>" + SystemUtils.LINE_SEPARATOR + "]", new ToStringBuilder(base).append("a", new ArrayList<Object>(), false).toString());
        assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  a=[]" + SystemUtils.LINE_SEPARATOR + "]", new ToStringBuilder(base).append("a", new ArrayList<Object>(), true).toString());
        assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  a=<size=0>" + SystemUtils.LINE_SEPARATOR + "]", new ToStringBuilder(base).append("a", new HashMap<Object, Object>(), false).toString());
        assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  a={}" + SystemUtils.LINE_SEPARATOR + "]", new ToStringBuilder(base).append("a", new HashMap<Object, Object>(), true).toString());
        assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  a=<size=0>" + SystemUtils.LINE_SEPARATOR + "]", new ToStringBuilder(base).append("a", (Object) new String[0], false).toString());
        assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  a={}" + SystemUtils.LINE_SEPARATOR + "]", new ToStringBuilder(base).append("a", (Object) new String[0], true).toString());
    }

    public void testPerson() {
        Person p = new Person();
        p.name = "Jane Doe";
        p.age = 25;
        p.smoker = true;
        String pBaseStr = p.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(p));
        assertEquals(pBaseStr + "[" + SystemUtils.LINE_SEPARATOR + "  name=Jane Doe" + SystemUtils.LINE_SEPARATOR + "  age=25" + SystemUtils.LINE_SEPARATOR + "  smoker=true" + SystemUtils.LINE_SEPARATOR + "]", new ToStringBuilder(p).append("name", p.name).append("age", p.age).append("smoker", p.smoker).toString());
    }

    public void testLong() {
        assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  3" + SystemUtils.LINE_SEPARATOR + "]", new ToStringBuilder(base).append(3L).toString());
        assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  a=3" + SystemUtils.LINE_SEPARATOR + "]", new ToStringBuilder(base).append("a", 3L).toString());
        assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  a=3" + SystemUtils.LINE_SEPARATOR + "  b=4" + SystemUtils.LINE_SEPARATOR + "]", new ToStringBuilder(base).append("a", 3L).append("b", 4L).toString());
    }

    public void testObjectArray() {
        Object[] array = new Object[] {null, base, new int[] {3, 6}};
        assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  {<null>,5,{3,6}}" + SystemUtils.LINE_SEPARATOR + "]", new ToStringBuilder(base).append(array).toString());
        assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  {<null>,5,{3,6}}" + SystemUtils.LINE_SEPARATOR + "]", new ToStringBuilder(base).append((Object) array).toString());
        array = null;
        assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  <null>" + SystemUtils.LINE_SEPARATOR + "]", new ToStringBuilder(base).append(array).toString());
        assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  <null>" + SystemUtils.LINE_SEPARATOR + "]", new ToStringBuilder(base).append((Object) array).toString());
    }

    public void testLongArray() {
        long[] array = new long[] {1, 2, -3, 4};
        assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  {1,2,-3,4}" + SystemUtils.LINE_SEPARATOR + "]", new ToStringBuilder(base).append(array).toString());
        assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  {1,2,-3,4}" + SystemUtils.LINE_SEPARATOR + "]", new ToStringBuilder(base).append((Object) array).toString());
        array = null;
        assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  <null>" + SystemUtils.LINE_SEPARATOR + "]", new ToStringBuilder(base).append(array).toString());
        assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  <null>" + SystemUtils.LINE_SEPARATOR + "]", new ToStringBuilder(base).append((Object) array).toString());
    }

    public void testLongArrayArray() {
        long[][] array = new long[][] {{1, 2}, null, {5}};
        assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  {{1,2},<null>,{5}}" + SystemUtils.LINE_SEPARATOR + "]", new ToStringBuilder(base).append(array).toString());
        assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  {{1,2},<null>,{5}}" + SystemUtils.LINE_SEPARATOR + "]", new ToStringBuilder(base).append((Object) array).toString());
        array = null;
        assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  <null>" + SystemUtils.LINE_SEPARATOR + "]", new ToStringBuilder(base).append(array).toString());
        assertEquals(baseStr + "[" + SystemUtils.LINE_SEPARATOR + "  <null>" + SystemUtils.LINE_SEPARATOR + "]", new ToStringBuilder(base).append((Object) array).toString());
    }

}
