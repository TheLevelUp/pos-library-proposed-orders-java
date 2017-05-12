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

public class UpdateSpendAmountCompleteOrderTests extends TestCase {
    // Payment requested > total due
    public void testUpdateSpend_WhenCompleteOrderRequestIs_PayingTooMuch_1200()
    {
        int amountCustomerIsPaying = 1200;

        int discountAmountApplied = 100;
        int outstandingTotalOnCheck = 1000;

        assertEquals(1100, ProposedOrderCalculator.calculateAdjustedSpendAmountCompleteOrder(outstandingTotalOnCheck, amountCustomerIsPaying,
                discountAmountApplied));
    }

    // Payment requested > total due, fringe case
    // The customer wanted to pay $11.50, but after applying a discount of $1.00, we see only $11.00 could have
    // been owed on the check; The `spend_amount` should have been/should be $11.00.
    public void testUpdateSpend_WhenCompleteOrderRequestIs_PayingTooMuch_1150()
    {
        int amountCustomerIsPaying = 1150;

        int discountAmountApplied = 100;
        int outstandingTotalOnCheck = 1000;

        assertEquals(1100, ProposedOrderCalculator.calculateAdjustedSpendAmountCompleteOrder(outstandingTotalOnCheck, amountCustomerIsPaying,
                discountAmountApplied));
    }

    // Paid in full
    public void testUpdateSpend_WhenCompleteOrderRequestIs_PaidInFull()
    {
        int amountCustomerIsPaying = 1100;

        int discountAmountApplied = 100;
        int outstandingTotalOnCheck = 1000;

        assertEquals(1100, ProposedOrderCalculator.calculateAdjustedSpendAmountCompleteOrder(outstandingTotalOnCheck, amountCustomerIsPaying,
                discountAmountApplied));
    }

    // Partial payment, payment requested < subtotal, fringe case
    // The customer wanted to pay $10.50, but after applying a discount of $1.00, we see that the balance was
    // $11.00; This is a partial payment, so the `spend_amount` should be equal to what the customer wanted to pay.
    public void testUpdateSpend_WhenCompleteOrderRequestIs_PartialPayment_1050()
    {
        int amountCustomerIsPaying = 1050;

        int discountAmountApplied = 100;
        int outstandingTotalOnCheck = 1000;

        assertEquals(1050, ProposedOrderCalculator.calculateAdjustedSpendAmountCompleteOrder(outstandingTotalOnCheck, amountCustomerIsPaying,
                discountAmountApplied));
    }

    // Partial payment, payment requested < subtotal
    public void testUpdateSpend_WhenCompleteOrderRequestIs_PartialPayment_1000()
    {
        int amountCustomerIsPaying = 1000;

        int discountAmountApplied = 100;
        int outstandingTotalOnCheck = 1000;

        assertEquals(1000, ProposedOrderCalculator.calculateAdjustedSpendAmountCompleteOrder(outstandingTotalOnCheck, amountCustomerIsPaying,
                discountAmountApplied));
    }

    public void testUpdateSpend_WhenCompleteOrderRequestIs_PartialPayment_900()
    {
        int amountCustomerIsPaying = 900;

        int discountAmountApplied = 100;
        int outstandingTotalOnCheck = 1000;

        assertEquals(900, ProposedOrderCalculator.calculateAdjustedSpendAmountCompleteOrder(outstandingTotalOnCheck, amountCustomerIsPaying,
                discountAmountApplied));
    }

    public void testUpdateSpend_WhenCompleteOrderRequestIs_PartialPayment_800()
    {
        int amountCustomerIsPaying = 800;

        int discountAmountApplied = 100;
        int outstandingTotalOnCheck = 1000;

        assertEquals(800, ProposedOrderCalculator.calculateAdjustedSpendAmountCompleteOrder(outstandingTotalOnCheck, amountCustomerIsPaying,
                discountAmountApplied));
    }

    // Zero dollar tender; discount credit only
    public void testUpdateSpend_WhenCompleteOrderRequestIs_PartialPayment_AllDiscount()
    {
        int amountCustomerIsPaying = 100;

        int discountAmountApplied = 100;
        int outstandingTotalOnCheck = 1000;

        assertEquals(100, ProposedOrderCalculator.calculateAdjustedSpendAmountCompleteOrder(outstandingTotalOnCheck, amountCustomerIsPaying,
                discountAmountApplied));
    }
}
