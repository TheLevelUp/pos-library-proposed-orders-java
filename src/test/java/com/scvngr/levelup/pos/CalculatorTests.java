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

import com.scvngr.levelup.pos.data.CalculatorTestData;
import junit.framework.TestCase;

public class CalculatorTests extends TestCase
{
    public void testBattery() throws Exception{
        runTestArray(CalculatorTestData.TestBattery);
    }

    private void runTestArray(int[][] values)
    {
        for (int i = 0; i < values.length; i++)
        {
            int totalOutstandingAmount = values[i][0];
            int totalTaxAmount = values[i][1];
            int totalExemptionAmount = values[i][2];
            int spendAmount = values[i][3];

            int expectedSpendAmount = values[i][4];
            int expectedTaxAmount = values[i][5];
            int expectedExemptionAmount = values[i][6];

            AdjustedCheckValues expectedCheckValues =
                    new AdjustedCheckValues(expectedSpendAmount, expectedTaxAmount, expectedExemptionAmount);

            AdjustedCheckValues actualCheckValues = ProposedOrderCalculator.calculateCreateProposedOrderValues(
                    totalOutstandingAmount,
                    totalTaxAmount,
                    totalExemptionAmount,
                    spendAmount);

            assertEquals(expectedCheckValues, actualCheckValues);
        }
    }

}
