package com.lubuntum.guesswhoapp;

import org.junit.Test;

import static org.junit.Assert.*;

import com.lubuntum.guesswhoapp.utils.SessionKeyGenerator;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void testKeyGenerator(){
        String key = SessionKeyGenerator.generateStr(7);
        System.out.println(key);
        assertEquals(7, key.length());
    }
}