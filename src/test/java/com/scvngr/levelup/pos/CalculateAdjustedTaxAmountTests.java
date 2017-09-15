package com.scvngr.levelup.pos;

import junit.framework.TestCase;

/**
 * Created by Navid on 9/15/2017.
 */

public class CalculateAdjustedTaxAmountTests extends TestCase {
    public void testPaymentLessThanPreTaxSubtotal_ReturnZero() throws Exception {
        CheckData checkData = new CheckData();
        checkData.OutstandingAmount = 1000;
        checkData.ExemptionAmount = 0;
        checkData.TaxAmount = 200;
        checkData.PaymentAmount = 0;

        assertEquals(0,
                ProposedOrderCalculator.calculateAdjustedTaxAmount(checkData));
    }

    public void testPaymentGreaterThanPreTaxSubtotalButLessThanOutstanding_ReturnTaxPaid() throws Exception{
        CheckData checkData = new CheckData();
        checkData.OutstandingAmount = 1000;
        checkData.ExemptionAmount = 0;
        checkData.TaxAmount = 200;
        checkData.PaymentAmount = 801;

        assertEquals(1,
                ProposedOrderCalculator.calculateAdjustedTaxAmount(checkData));

        checkData.PaymentAmount = 900;
        assertEquals(100,
                ProposedOrderCalculator.calculateAdjustedTaxAmount(checkData));

        checkData.PaymentAmount = 999;
        assertEquals(199,
                ProposedOrderCalculator.calculateAdjustedTaxAmount(checkData));
    }

    public void testPaymentGreaterThanOrEqualToOutstandingAmount_ReturnsTaxAmount() throws Exception{
        CheckData checkData = new CheckData();
        checkData.OutstandingAmount = 1000;
        checkData.ExemptionAmount = 0;
        checkData.TaxAmount = 200;
        checkData.PaymentAmount = 1000;

        assertEquals(200,
                ProposedOrderCalculator.calculateAdjustedTaxAmount(checkData));

        checkData.PaymentAmount = 1200;
        assertEquals(200,
                ProposedOrderCalculator.calculateAdjustedTaxAmount(checkData));
    }

    public void testTaxGreaterThanOutstanding_ThrowsException()
    {
        CheckData checkData = new CheckData();
        checkData.OutstandingAmount = 1000;
        checkData.ExemptionAmount = 0;
        checkData.TaxAmount = 1100;
        checkData.PaymentAmount = 500;
        boolean threwException = false;
        try
        {
            ProposedOrderCalculator.calculateAdjustedTaxAmount(checkData);
        }
        catch (Exception e)
        {
            threwException = true;
        }

        assertTrue(threwException);
    }
}
