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
public class ProposedOrderCalculator {
    /**
     * Accepts known values from the point-of-sale and gives you an AdjustedCheckValues object containing the
     * spend_amount, tax_amount, and exemption_amount to submit a LevelUp Create Proposed Order API request.
     *
     * @param outstandingAmount The current total amount of the check, including tax, in cents.
     * @param taxAmount         The current tax due on the check, in cents.
     * @param exemptAmount   The current total of exempted items on the check, in cents.
     * @param paymentAmount  The amount the customer would like to spend, in cents.
     * @throws Exception
     */
    public static AdjustedCheckValues calculateCreateProposedOrderValues(
            int outstandingAmount,
            int taxAmount,
            int exemptAmount,
            int paymentAmount) throws Exception {

        return calculateOrderValues(outstandingAmount, taxAmount, exemptAmount, paymentAmount);
    }

    /**
     * Accepts known values from the point-of-sale and gives you an AdjustedCheckValues object containing the
     * spend_amount, tax_amount, and exemption_amount to submit a LevelUp Complete Order API request.
     *
     * @param outstandingAmount The current total amount of the check, including tax, in cents.
     * @param taxAmount         The current tax due on the check, in cents.
     * @param exemptAmount   The current total of exempted items on the check, in cents.
     * @param paymentAmount  The amount the customer would like to spend, in cents.
     * @param appliedDiscountAmount  The discount amount applied to the point of sale for the customer.
     * @throws Exception
     */
    public static AdjustedCheckValues calculateCompleteOrderValues(
            int outstandingAmount,
            int taxAmount,
            int exemptAmount,
            int paymentAmount,
            int appliedDiscountAmount) throws Exception{
        int outstandingAmountWithDiscount = outstandingAmount + Math.abs(appliedDiscountAmount);

        return calculateOrderValues(
                outstandingAmountWithDiscount,
                taxAmount,
                exemptAmount,
                paymentAmount);
    }

    static AdjustedCheckValues calculateOrderValues(int outstandingAmount,
                                                    int taxAmount,
                                                    int exemptionAmount,
                                                    int paymentAmount) throws Exception
    {
        CheckData checkData = sanitizeData(outstandingAmount, taxAmount, exemptionAmount, paymentAmount);

        int adjustedTaxAmount = calculateAdjustedTaxAmount(checkData);

        int adjustedExemptionAmount = calculateAdjustedExemptionAmount(checkData);

        return new AdjustedCheckValues(
                checkData.PaymentAmount,
                adjustedTaxAmount,
                adjustedExemptionAmount);
    }

    static CheckData sanitizeData(int outstandingAmount,
                                  int taxAmount,
                                  int exemptionAmount,
                                  int paymentAmount)
    {
        CheckData checkData = new CheckData();
        checkData.ExemptionAmount = exemptionAmount;
        checkData.OutstandingAmount = outstandingAmount;
        checkData.PaymentAmount = paymentAmount;
        checkData.TaxAmount = taxAmount;

        checkData.PaymentAmount = paymentAmountCannotBeGreaterThanOutstandingAmount(
                checkData.OutstandingAmount,
                checkData.PaymentAmount);

        checkData.TaxAmount = taxAmountCannotBeGreaterThanOutstandingAmount(
                checkData.OutstandingAmount,
                checkData.TaxAmount);

        checkData.ExemptionAmount = exemptionAmountCannotBeGreaterThanPreTaxSubtotal(
                checkData.ExemptionAmount,
                checkData.getPreTaxSubtotal());

        return checkData;
    }

    /**
     * For LevelUp Proposed/Complete Order, with partial payments, the last user to pay is responsible
     * for paying the tax.
     * @param checkData
     * @return tax amount
     * @throws Exception if tax amount greater than outstanding amount
     */
    static int calculateAdjustedTaxAmount(CheckData checkData) throws Exception
    {
        if(checkData.TaxAmount > checkData.OutstandingAmount)
        {
            throw new Exception("Tax amount cannot be greater than total outstanding amount.");
        }

        boolean isTaxFullyPaid = checkData.PaymentAmount >= checkData.OutstandingAmount;
        if(isTaxFullyPaid)
        {
            return checkData.TaxAmount;
        }

        return zeroIfNegative(checkData.PaymentAmount - checkData.getPreTaxSubtotal());
    }

    /**
     * For LevelUp Proposed/Complete Order, with partial payments, the last user to pay bears the burden of
     * exemption amounts. We allow anyone paying to use discount credit until the remaining amount owed is
     * less than or equal to the amount of exempted items on the check.
     * @param checkData
     * @return
     * @throws Exception if the exemption amount greater than the pre-tax total
     */
    static int calculateAdjustedExemptionAmount(CheckData checkData) throws Exception
    {
        if(checkData.ExemptionAmount > checkData.getPreTaxSubtotal())
        {
            throw new Exception("Exemption amount cannot be greater than the pre-tax total on the check.");
        }

        boolean isExemptFullyPaid = checkData.PaymentAmount >= checkData.getPreTaxSubtotal();
        if(isExemptFullyPaid)
        {
            return checkData.ExemptionAmount;
        }

        return zeroIfNegative(checkData.PaymentAmount - checkData.getNonExemptSubtotal());
    }

    private static int paymentAmountCannotBeGreaterThanOutstandingAmount(int outstandingAmount, int paymentAmount) {
        return smallerOrZeroIfNegative(outstandingAmount, paymentAmount);
    }

    private static int taxAmountCannotBeGreaterThanOutstandingAmount(int outstandingAmount, int taxAmount) {
        return smallerOrZeroIfNegative(taxAmount, outstandingAmount);
    }

    private static int exemptionAmountCannotBeGreaterThanPreTaxSubtotal(int exemptAmount, int outstandingAmount) {
        return smallerOrZeroIfNegative(exemptAmount, outstandingAmount);
    }

    private static int smallerOrZeroIfNegative(int a, int b){
        return zeroIfNegative(Math.min(a, b));
    }

    private static int zeroIfNegative(int val) {
        return Math.max(0, val);
    }
}
