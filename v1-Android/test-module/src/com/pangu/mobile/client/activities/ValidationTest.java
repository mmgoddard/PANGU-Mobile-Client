package com.pangu.mobile.client.activities;

import android.annotation.TargetApi;
import android.os.Build;
import android.test.AndroidTestCase;
import android.widget.EditText;

import com.pangu.mobile.client.utils.Validation;

/**
 * Created by Mark on 18/02/15.
 */
public class ValidationTest extends AndroidTestCase {
    private Validation validation;

    /**
     * Setups the testing conditions and is called before any tests are executed
     * @throws Exception
     */
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        validation = Validation.getInstance();
    }

    /**
     * The method is called after the tests are executed.
     * @throws Exception
     */
    public void tearDown() throws Exception{
        super.tearDown();
    }

    /**
     * Testing if the validation instance is created correctly.
     */
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public void testValidationInstance() {
        //Create validation instance
        Validation testValid1 = Validation.getInstance();
        assertNotNull(testValid1);

        //Test that you can't create another instance and the first instance is returned.
        Validation testValid2 = Validation.getInstance();
        assertEquals(testValid1, testValid2);
    }

    /**
     * Test to check if a string is parsable to a Integer.
     */
    public void testIsIntParsable() {
        //Testing correct input
        boolean correctValue = validation.isIntParsable("8080");
        assertEquals(correctValue, true);

        //Testing incorrect input
        boolean incorrectValue = validation.isIntParsable("Config1");
        assertEquals(incorrectValue, false);
    }

    /**
     * Test to check if a string is parsable to a Double.
     */
    public void testIsDoubleParsable() {
        //Testing correct input
        boolean correctValue = validation.isDoubleParsable("9.8");
        assertEquals(correctValue, true);

        //Testing incorrect input
        boolean incorrectValue = validation.isDoubleParsable("Config1");
        assertEquals(incorrectValue, false);
    }

    /**
     * Test to check whether or not a string is alphanumeric.
     */
    public void testIsAlphaNumeric() {
        //Testing correct input
        boolean correctValue = validation.isAlphaNumeric("Config1");
        assertEquals(correctValue, true);

        //Testing incorrect input
        boolean incorrectValue = validation.isAlphaNumeric("@£$%");
        assertEquals(incorrectValue, false);
    }
}
