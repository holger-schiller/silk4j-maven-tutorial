/**
 * (C) Copyright 1992-2021 Micro Focus or one of its affiliates.
 * The only warranties for products and services of Micro Focus and its 
 * affiliates and licensors ("Micro Focus") are as may be set forth in the
 * express warranty statements accompanying such products and services. Nothing
 * herein should be construed as constituting an additional warranty.
 * Micro Focus shall not be liable for technical or editorial errors or 
 * omissions contained herein. The information contained herein is subject to 
 * change without notice.
 */
package com.borland.silk4j.maven.demo;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.borland.silktest.jtf.SilkTestSuite;

/**
 * <p>
 * This test suite demonstrates using the Silk4J native API to test a simple browser application. This test suite
 * contains all test classes.
 * </p>
 * <p>
 * For details about the test classes {@link TestGMOShop} and {@link SynchronizationSample},
 * refer to the documentation of the classes.
 * </p>
 * @author Borland Software Corporation (a Micro Focus company)
 */
@RunWith(SilkTestSuite.class)
// Synchronization web page not available
// @Suite.SuiteClasses( { TestGMOShop.class, SynchronizationSample.class})
@Suite.SuiteClasses( { TestGMOShop.class} )
public class TestSuiteAllTests {

}
