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

import org.junit.Assert;
import org.junit.Test;

public class CalculateAdjustedExemptionTests extends TestCase {
    public void testPaymentZero_ReturnsZero() throws Exception{
        CheckData checkData = new CheckData();
        checkData.OutstandingAmount = 1000;
        checkData.TaxAmount = 0;
        checkData.ExemptionAmount = 500;
        checkData.PaymentAmount = 0;

        int result = ProposedOrderCalculator.calculateAdjustedExemptionAmount(checkData);
        assertEquals(result, 0);

        checkData.ExemptionAmount = 1;
        int result2 = ProposedOrderCalculator.calculateAdjustedExemptionAmount(checkData);
        assertEquals(result2, 0);
    }

    public void testPaymentLessThanNonExempt_ReturnsZero() throws Exception{
        CheckData checkData = new CheckData();
        checkData.OutstandingAmount = 1000;
        checkData.TaxAmount = 0;
        checkData.ExemptionAmount = 500;
        checkData.PaymentAmount = 1;

        assertEquals(
                ProposedOrderCalculator.calculateAdjustedExemptionAmount(checkData),
                0);

        checkData.PaymentAmount = 400;
        assertEquals(
                ProposedOrderCalculator.calculateAdjustedExemptionAmount(checkData),
                0);

        checkData.PaymentAmount = 499;
        assertEquals(
                ProposedOrderCalculator.calculateAdjustedExemptionAmount(checkData),
                0);
    }

    public void testPaymentGreaterThanNonExemptButLessThanPreTaxSubtotal_ReturnsPartialAmount()
    throws Exception{
        CheckData checkData = new CheckData();
        checkData.OutstandingAmount = 1000;
        checkData.TaxAmount = 0;
        checkData.ExemptionAmount = 200;
        checkData.PaymentAmount = 801;

        // In this case, the partial amount should be payment - 800

        assertEquals(
                ProposedOrderCalculator.calculateAdjustedExemptionAmount(checkData),
                1);

        checkData.PaymentAmount = 900;
        assertEquals(ProposedOrderCalculator.calculateAdjustedExemptionAmount(checkData), 100);

        checkData.PaymentAmount = 999;
        assertEquals(ProposedOrderCalculator.calculateAdjustedExemptionAmount(checkData), 199);
    }

    public void testPaymentAmountGreaterThanOrEqualToPreTaxSubtotal_ReturnsExemptionUnchanged()
    throws Exception{
        CheckData checkData = new CheckData();
        checkData.OutstandingAmount = 1000;
        checkData.TaxAmount = 0;
        checkData.ExemptionAmount = 500;
        checkData.PaymentAmount = 1000;

        // PreTaxSubtotal = 1000

        // Payment = PreTaxSubtotal
        assertEquals(ProposedOrderCalculator.calculateAdjustedExemptionAmount(checkData), 500);

        // Payment > PreTaxSubtotal
        checkData.PaymentAmount = 1100;
        assertEquals(ProposedOrderCalculator.calculateAdjustedExemptionAmount(checkData), 500);
    }

    public void testExemptEqualsPreTaxSubtotal_PaymentLessThanPreTaxSubtotal_ReturnPaymentAmount()
    throws Exception{
        CheckData checkData = new CheckData();
        checkData.OutstandingAmount = 1000;
        checkData.TaxAmount = 0;
        checkData.ExemptionAmount = 1000;
        checkData.PaymentAmount = 1;

        assertEquals(ProposedOrderCalculator.calculateAdjustedExemptionAmount(checkData), 1);

        checkData.PaymentAmount = 500;
        assertEquals(ProposedOrderCalculator.calculateAdjustedExemptionAmount(checkData), 500);

        checkData.PaymentAmount = 999;
        assertEquals(ProposedOrderCalculator.calculateAdjustedExemptionAmount(checkData), 999);
    }

    public void testExemptAmountGreaterThanPreTaxOutstanding_ThrowsException()
            throws Exception{
        CheckData checkData = new CheckData();
        checkData.OutstandingAmount = 1000;
        checkData.TaxAmount = 200;
        checkData.ExemptionAmount = 900;
        checkData.PaymentAmount = 500;

        boolean threwException = false;
        try
        {
            ProposedOrderCalculator.calculateAdjustedExemptionAmount(checkData);
        } catch (Exception e)
        {
            threwException = true;
        }

        assertTrue(threwException);
    }
}
