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

import com.scvngr.levelup.pos.mocks.Check;

import junit.framework.TestCase;

import org.junit.Test;
import org.omg.CORBA.Environment;

public class SplitTenderTests extends TestCase {
    private static final int TAX_RATE = 10; //Tax rate as percent

    public void testSplitTenderExample_LevelUp_Then_Cash() throws Exception {
        // Split tender example: partial payment to LevelUp ($10) and remaining balance tendered to cash.
        // Check details (prior to LevelUp Scan)
        //   Item subtotal: $20
        //   tax (10%):      $2

        int itemsSubtotalAmount = 2000;
        int taxAmountDue = itemsSubtotalAmount / TAX_RATE;

        // User pays $10 towards LevelUp

        // Create proposed order: expected values
        int expectedTaxAmount = 0;
        int expectedSpendAmount = 1000;
        int expectedExemptionAmount = 0;
        AdjustedCheckValues expectedProposedOderValues =
                new AdjustedCheckValues(expectedSpendAmount, expectedTaxAmount, expectedExemptionAmount);

        // Total amount due on check, including tax
        int totalOutstandingAmount = itemsSubtotalAmount + taxAmountDue;

        // The current tax due on the check
        int totalTaxAmount = taxAmountDue;

        // example does not consider exemptions
        int totalExemptionAmount = 0;

        // The amount the user would like to spend
        int spendAmount = 1000;

        AdjustedCheckValues proposedOrderValues = ProposedOrderCalculator.calculateCreateProposedOrderValues(
                totalOutstandingAmount,
                totalTaxAmount,
                totalExemptionAmount,
                spendAmount);

        assertEquals(expectedProposedOderValues.toString(), proposedOrderValues.toString());

        // available discount amount $1
        int availableDiscountAmount = 100;

        // POS applies pretax discount to check and updates subtotal and tax
        itemsSubtotalAmount -= availableDiscountAmount;
        taxAmountDue = itemsSubtotalAmount / TAX_RATE;
        totalOutstandingAmount = itemsSubtotalAmount + taxAmountDue;

        // Create completed order: expected values
        // tax amount unchanged
        // spend amount unchanged
        // exemption amount unchanged

        AdjustedCheckValues expectedCompletedOrderValues =
                new AdjustedCheckValues(expectedSpendAmount, expectedTaxAmount, expectedExemptionAmount);

        AdjustedCheckValues completedOrderValues = ProposedOrderCalculator.calculateCompleteOrderValues(
                totalOutstandingAmount,
                totalTaxAmount,
                totalExemptionAmount,
                spendAmount,
                availableDiscountAmount);

        assertEquals(expectedCompletedOrderValues, completedOrderValues);
    }

    public void testSplitTenderExample_Cash_Then_LevelUp() throws Exception {
        // Split tender example: partial payment to cash ($10) and remaining balance tendered to LevelUp.
        // Check details (prior to LevelUp Scan)
        //   Item subtotal: $20
        //   tax (10%):      $2
        //   TOTAL DUE:     $22

        int itemsSubtotalAmount = 2000;
        int taxAmountDue = itemsSubtotalAmount / TAX_RATE;

        // Cashier tenders $10 to cash
        // Updated check:
        //   PAID cash:     $10
        //   TOTAL DUE:     $12

        // User pays remaining balance ($12) towards LevelUp

        // Create proposed order: expected values
        int expectedTaxAmount = 200;
        int expectedSpendAmount = 1200;
        int expectedExemptionAmount = 0;
        AdjustedCheckValues expectedProposedOderValues =
                new AdjustedCheckValues(expectedSpendAmount, expectedTaxAmount, expectedExemptionAmount);

        // Total amount due on check, including tax
        int paidCashAmount = 1000;
        int totalOutstandingAmount = itemsSubtotalAmount + taxAmountDue - paidCashAmount;

        // The current tax due on the check
        int totalTaxAmount = taxAmountDue;

        // example does not consider exemptions
        int totalExemptionAmount = 0;

        // The amount the user would like to spend (remaining balance)
        int spendAmount = totalOutstandingAmount;

        AdjustedCheckValues proposedOrderValues = ProposedOrderCalculator.calculateCreateProposedOrderValues(
                totalOutstandingAmount,
                totalTaxAmount,
                totalExemptionAmount,
                spendAmount);

        assertEquals(expectedProposedOderValues, proposedOrderValues);

        // available discount amount $1
        int availableDiscountAmount = 100;

        // POS applies pretax discount to check and updates subtotal and tax
        itemsSubtotalAmount -= availableDiscountAmount;
        totalTaxAmount = itemsSubtotalAmount / TAX_RATE;
        totalOutstandingAmount = itemsSubtotalAmount + totalTaxAmount - paidCashAmount;
        spendAmount = totalOutstandingAmount + availableDiscountAmount;

        // Create completed order: expected values
        // adjusted tax amount: $1.90
        // spend amount unchanged: $11.90
        // exemption amount unchanged

        expectedTaxAmount = 190;
        expectedSpendAmount = 1190;

        AdjustedCheckValues expectedCompletedOrderValues =
                new AdjustedCheckValues(expectedSpendAmount, expectedTaxAmount, expectedExemptionAmount);

        AdjustedCheckValues completedOrderValues = ProposedOrderCalculator.calculateCompleteOrderValues(
                totalOutstandingAmount,
                totalTaxAmount,
                totalExemptionAmount,
                spendAmount,
                availableDiscountAmount);

        assertEquals(expectedCompletedOrderValues, completedOrderValues);
    }

    public void testSplitTenderExample_NoChangeInSpendOrTax_AfterLevelUpPaidFirst() throws Exception
    {
        // $9.90 is owed, $0.90 of that is tax. $3.00 of that is tobacco/alcohol, and the customer wants to pay
        // $9.00 towards the check
        Check check = new Check(990,90);
        int exemptionAmount = 300;
        int spendAmount = 900;

        // Create proposed order: expected values
        AdjustedCheckValues expectedProposedOrderValues =
                new AdjustedCheckValues(900, 0, 300);

        AdjustedCheckValues proposedOrderValues = ProposedOrderCalculator.calculateCreateProposedOrderValues(
                check.getTotalOutstandingAmount(),
                check.getTotalTaxAmount(),
                exemptionAmount,
                spendAmount);


        assertTrue(proposedOrderValues.equals(expectedProposedOrderValues));

        // available discount amount $1
        int availableDiscountAmount = 100;

        // POS applies $1.00 pretax discount to check and updates subtotal and tax
        check.applyDiscount(100);

        // $8.00 (Subtotal)
        // $0.80 (Tax)
        // $8.80 (Total)
        // $9.80 (Total + Applied Discount)

        AdjustedCheckValues expectedCompletedOrderValues =
                new AdjustedCheckValues(900, 0, 300);

        AdjustedCheckValues completedOrderValues = ProposedOrderCalculator.calculateCompleteOrderValues(
                check.getTotalOutstandingAmount(),
                check.getTotalTaxAmount(),
                exemptionAmount,
                spendAmount,
                availableDiscountAmount);

        assertTrue(completedOrderValues.equals(expectedCompletedOrderValues));
    }
}

