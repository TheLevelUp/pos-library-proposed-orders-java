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
