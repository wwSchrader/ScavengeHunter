package com.wwschrader.android.scavengehunter;

import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Warren on 12/17/2016.
 * Test for methods in Utilities class
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class UtilitiesTest {
    private static final String TEST_CALENDAR_STRING = "2016-01-16-15-35";
    private long TEST_CALENDAR_MILLISECONDS = 1452987300000L;

    @Test
    public void convertCalendarStringToLong() throws Exception {
        long convertedString = Utilities.convertCalendarStringToLong(TEST_CALENDAR_STRING);
        assertEquals(convertedString, TEST_CALENDAR_MILLISECONDS);

    }

}