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
