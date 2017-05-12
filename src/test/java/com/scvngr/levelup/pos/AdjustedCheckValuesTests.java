/**
 * Copyright 2017 SCVNGR, Inc. d/b/a LevelUp. All rights reserved.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
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

    public void testNotInstanceOfEqualOperationForAdjustedCheckValues(){
        AdjustedCheckValues firstOne = new AdjustedCheckValues(0,0,0);
        assertFalse(firstOne.equals(new Object()));
    }
}
