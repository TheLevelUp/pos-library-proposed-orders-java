package com.scvngr.levelup.pos.tests;

import com.scvngr.levelup.pos.tests.data.CalculatorTestData;
import junit.framework.TestCase;

public class CalculatorTests extends TestCase
{
    public void testBattery() throws Exception{
        runTestArray(CalculatorTestData.TestBattery);
    }

    public void testTest123(){
        int totalOutstandingAmount = 1;
        int totalTaxAmount = 10;
        int totalExemptionAmount = 0;
        int spendAmount = 1;

        AdjustedCheckValues expectedCheckValues =
                new AdjustedCheckValues(1, 1, 0);

        AdjustedCheckValues actualCheckValues = ProposedOrderCalculator.calculateCreateProposedOrderValues(
                totalOutstandingAmount,
                totalTaxAmount,
                totalExemptionAmount,
                spendAmount);

        assertEquals(expectedCheckValues, actualCheckValues);

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
