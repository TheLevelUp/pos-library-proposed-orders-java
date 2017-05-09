package com.scvngr.levelup.pos.tests;

import junit.framework.TestCase;

/**
 * Created by Navid on 5/9/2017.
 */
public class UpdateTaxAmountTests extends TestCase {
    // Paid In Full
    public void testUpdateTax_WhenProposedOrderRequest_IsPaidInFull()
    {
        int outstandingTotalOnCheck = 1000;
        int taxAmount = 100;
        int amountCustomerIsPaying = 1000;

        assertEquals(taxAmount,
                ProposedOrderCalculator.calculateAdjustedTaxAmount(outstandingTotalOnCheck, taxAmount, amountCustomerIsPaying));
    }

    // Partial payment, payment requested > subtotal
    public void testUpdateTax_WhenProposedOrderRequestIs_PartiallyPayingIntoTheTax()
    {
        int outstandingTotalOnCheck = 1000;
        int taxAmount = 100;
        int amountCustomerIsPaying = 950;

        assertEquals(50,
                ProposedOrderCalculator.calculateAdjustedTaxAmount(outstandingTotalOnCheck, taxAmount, amountCustomerIsPaying));
    }

    // Partial payment, payment requested < subtotal
    public void testUpdateTax_WhenProposedOrderRequestIs_NotPaidInFull()
    {
        int outstandingTotalOnCheck = 1000;
        int taxAmount = 100;
        int amountCustomerIsPaying = 500;

        assertEquals(0,
                ProposedOrderCalculator.calculateAdjustedTaxAmount(outstandingTotalOnCheck, taxAmount, amountCustomerIsPaying));
    }

    public void testUpdateTax_WhenProposedOrderRequestIs_PayingOneCent()
    {
        int outstandingTotalOnCheck = 1000;
        int taxAmount = 100;
        int amountCustomerIsPaying = 1;

        assertEquals(0,
                ProposedOrderCalculator.calculateAdjustedTaxAmount(outstandingTotalOnCheck, taxAmount, amountCustomerIsPaying));
    }

    // Zero dollar payment; this order would get rejected by platform
    public void testUpdateTax_WhenProposedOrderRequestIs_PayingNothing()
    {
        int outstandingTotalOnCheck = 1000;
        int taxAmount = 100;
        int amountCustomerIsPaying = 0;

        assertEquals(0,
                ProposedOrderCalculator.calculateAdjustedTaxAmount(outstandingTotalOnCheck, taxAmount, amountCustomerIsPaying));
    }
}
