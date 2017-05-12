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

public class UpdateSpendAmountTests extends TestCase {
    // Payment requested > total due
    public void testUpdateSpend_WhenProposedOrderRequestIs_PayingTooMuch()
    {
        int outstandingTotalOnCheck = 1000;
        int amountCustomerIsPaying = 1200;

        assertEquals(outstandingTotalOnCheck,
                ProposedOrderCalculator.calculateAdjustedCustomerPaymentAmount(outstandingTotalOnCheck, amountCustomerIsPaying));
    }

    // Paid In Full
    public void testUpdateSpend_WhenProposedOrderRequestIs_PaidInFull()
    {
        int outstandingTotalOnCheck = 1000;
        int amountCustomerIsPaying = 1000;

        assertEquals(amountCustomerIsPaying,
                ProposedOrderCalculator.calculateAdjustedCustomerPaymentAmount(outstandingTotalOnCheck, amountCustomerIsPaying));
    }

    // Partial payment, payment requested < subtotal
    public void testUpdateSpend_WhenProposedOrderRequestIs_PartialPayment()
    {
        int outstandingTotalOnCheck = 1000;
        int amountCustomerIsPaying = 800;

        assertEquals(amountCustomerIsPaying,
                ProposedOrderCalculator.calculateAdjustedCustomerPaymentAmount(outstandingTotalOnCheck, amountCustomerIsPaying));
    }

    // Zero dollar payment
    public void testUpdateSpend_WhenProposedOrderRequestIs_PayingZero()
    {
        int outstandingTotalOnCheck = 1000;
        int amountCustomerIsPaying = 0;

        assertEquals(amountCustomerIsPaying,
                ProposedOrderCalculator.calculateAdjustedCustomerPaymentAmount(outstandingTotalOnCheck, amountCustomerIsPaying));
    }
}
