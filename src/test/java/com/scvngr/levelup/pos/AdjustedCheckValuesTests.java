package com.scvngr.levelup.pos;

import junit.framework.TestCase;

public class AdjustedCheckValuesTests extends TestCase {
    public void testPositiveEqualOperationForAdjustedCheckValues(){
        AdjustedCheckValues firstOne = new AdjustedCheckValues(1,2,3);
        AdjustedCheckValues secondOne = new AdjustedCheckValues(1, 2,3);

        assertTrue(firstOne.equals(secondOne));
    }

    public void testNegativeEqualOperationForAdjustedCheckValues(){
        AdjustedCheckValues firstOne = new AdjustedCheckValues(1,2,3);
        AdjustedCheckValues secondOne = new AdjustedCheckValues(1,2,1);

        assertFalse(firstOne.equals(secondOne));
    }

    public void testDifferentObjectsEqualOperationForAdjustedCheckValues(){
        AdjustedCheckValues firstOne = new AdjustedCheckValues(1,2,3);
        String secondOne = firstOne.toString();

        assertFalse(firstOne.equals(secondOne));
    }

    public void testNullObjectEqualOperationForAdjustedCheckValues(){
        AdjustedCheckValues firstOne = new AdjustedCheckValues(0,0,0);

        assertFalse(firstOne.equals(null));
    }
}
