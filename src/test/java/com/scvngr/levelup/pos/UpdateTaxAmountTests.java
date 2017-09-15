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

public class UpdateTaxAmountTests extends TestCase {
    // Paid In Full
    public void testUpdateTax_WhenProposedOrderRequest_IsPaidInFull() throws Exception
    {
        int outstandingTotalOnCheck = 1000;
        int taxAmount = 100;
        int amountCustomerIsPaying = 1000;

        assertEquals(taxAmount,
                ProposedOrderCalculator.calculateOrderValues(
                        outstandingTotalOnCheck,
                        taxAmount,
                        0,
                        amountCustomerIsPaying).getTaxAmount());
    }

    // Partial payment, payment requested > subtotal
    public void testUpdateTax_WhenProposedOrderRequestIs_PartiallyPayingIntoTheTax() throws Exception
    {
        int outstandingTotalOnCheck = 1000;
        int taxAmount = 100;
        int amountCustomerIsPaying = 950;

        assertEquals(50,
                ProposedOrderCalculator.calculateOrderValues(
                        outstandingTotalOnCheck,
                        taxAmount,
                        0,
                        amountCustomerIsPaying).getTaxAmount());
    }

    // Partial payment, payment requested < subtotal
    public void testUpdateTax_WhenProposedOrderRequestIs_NotPaidInFull() throws Exception
    {
        int outstandingTotalOnCheck = 1000;
        int taxAmount = 100;
        int amountCustomerIsPaying = 500;

        assertEquals(0,
                ProposedOrderCalculator.calculateOrderValues(
                        outstandingTotalOnCheck,
                        taxAmount,
                        0,
                        amountCustomerIsPaying).getTaxAmount());
    }

    public void testUpdateTax_WhenProposedOrderRequestIs_PayingOneCent() throws Exception
    {
        int outstandingTotalOnCheck = 1000;
        int taxAmount = 100;
        int amountCustomerIsPaying = 1;

        assertEquals(0,
                ProposedOrderCalculator.calculateOrderValues(
                        outstandingTotalOnCheck,
                        taxAmount,
                        0,
                        amountCustomerIsPaying).getTaxAmount());
    }

    // Zero dollar payment; this order would get rejected by platform
    public void testUpdateTax_WhenProposedOrderRequestIs_PayingNothing() throws Exception
    {
        int outstandingTotalOnCheck = 1000;
        int taxAmount = 100;
        int amountCustomerIsPaying = 0;

        assertEquals(0,
                ProposedOrderCalculator.calculateOrderValues(
                        outstandingTotalOnCheck,
                        taxAmount,
                        0,
                        amountCustomerIsPaying).getTaxAmount());
    }
}
