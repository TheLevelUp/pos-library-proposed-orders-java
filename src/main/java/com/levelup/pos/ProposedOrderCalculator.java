package com.levelup.pos;

public class ProposedOrderCalculator {
    /**
     * Accepts known values from the point-of-sale and gives you an AdjustedCheckValues object containing the
     * spend_amount, tax_amount, and exemption_amount to submit a LevelUp Create Proposed Order API request.
     * @param totalOutstandingAmount The current total amount of the check, including tax, in cents.
     * @param totalTaxAmount The current tax due on the check, in cents.
     * @param totalExemptionAmount The current total of exempted items on the check, in cents.
     * @param customerPaymentAmount The amount the customer would like to spend, in cents.
     */
    public static AdjustedCheckValues calculateCreateProposedOrderValues(int totalOutstandingAmount,
                                                                         int totalTaxAmount,
                                                                         int totalExemptionAmount,
                                                                         int customerPaymentAmount)
    {
        int adjustedSpendAmount =
                calculateAdjustedCustomerPaymentAmount(customerPaymentAmount, totalOutstandingAmount);

        int adjustedTaxAmount = calculateAdjustedTaxAmount(totalOutstandingAmount, totalTaxAmount,
                adjustedSpendAmount);

        int adjustedExemptionAmount = calculateAdjustedExemptionAmount(totalOutstandingAmount, totalTaxAmount,
                totalExemptionAmount, adjustedSpendAmount);

        return new AdjustedCheckValues(adjustedSpendAmount, adjustedTaxAmount, adjustedExemptionAmount);
    }

    /**
     * Accepts known values from the point-of-sale and gives you an AdjustedCheckValues object containing the
     * spend_amount, tax_amount, and exemption_amount to submit a LevelUp Complete Order API request.
     * @param totalOutstandingAmount The current total amount of the check, including tax, in cents.
     * @param totalTaxAmount The current tax due on the check, in cents.
     * @param totalExemptionAmount The current total of exempted items on the check, in cents.
     * @param customerPaymentAmount The amount the customer would like to spend, in cents.
     * @param appliedDiscountAmount The discount amount applied to the point of sale for the customer.
     */
    public static AdjustedCheckValues calculateCompleteOrderValues(int totalOutstandingAmount,
                                                                   int totalTaxAmount,
                                                                   int totalExemptionAmount,
                                                                   int customerPaymentAmount,
                                                                   int appliedDiscountAmount)
    {
        AdjustedCheckValues values = calculateCreateProposedOrderValues(
                totalOutstandingAmount,
                totalTaxAmount,
                totalExemptionAmount,
                customerPaymentAmount);

        values.setSpendAmount(
                calculateAdjustedSpendAmountCompleteOrder(
                        totalOutstandingAmount, customerPaymentAmount, appliedDiscountAmount));

        return values;
    }

    private static int calculateAdjustedCustomerPaymentAmount(int totalOutstandingAmount, int customerPaymentAmount)
    {
        return Math.max(0,Math.min(customerPaymentAmount, totalOutstandingAmount));
    }

    private static int calculateAdjustedTaxAmount(int totalOutstandingAmount,
                                                  int totalTaxAmount,
                                                  int postAdjustedCustomerPaymentAmount)
    {

        totalTaxAmount = Math.max(0, Math.min(totalTaxAmount, totalOutstandingAmount));

        boolean wasPartialPaymentRequested = postAdjustedCustomerPaymentAmount < totalOutstandingAmount;

        if(wasPartialPaymentRequested)
        {
            int remainingAmountOwedAfterSpend = totalOutstandingAmount - postAdjustedCustomerPaymentAmount;
            totalTaxAmount = Math.max(0, totalTaxAmount - remainingAmountOwedAfterSpend);
        }

        return totalTaxAmount;
    }

    private static int calculateAdjustedExemptionAmount(int totalOutstandingAmount,
                                                        int totalTaxAmount,
                                                        int totalExemptionAmount,
                                                        int postAdjustedCustomerPaymentAmount)
    {
        int totalOutstandingAmountLessTax = totalOutstandingAmount - totalTaxAmount;

        boolean wasPartialPaymentRequestedWrtSubtotal = postAdjustedCustomerPaymentAmount < totalOutstandingAmountLessTax;

        if (wasPartialPaymentRequestedWrtSubtotal)
        {
            // defer the exemption amount to last possible paying customer or customers
            int totalOutstandingLessTaxAfterPayment =
                    Math.max(0, totalOutstandingAmountLessTax - postAdjustedCustomerPaymentAmount);

            totalExemptionAmount = Math.max(0, totalExemptionAmount - totalOutstandingLessTaxAfterPayment);
        }

        int adjustedExemptionAmount = Math.min(Math.min(totalExemptionAmount, totalOutstandingAmountLessTax),
                postAdjustedCustomerPaymentAmount);

        return Math.max(0, adjustedExemptionAmount);
    }

    /**
     * Adjusts the `spend_amount` by considering the amount a customer wants to pay, the total due on the check
     * after applying the discount (0 if none was available), and the discount amount applied. The user will never
     * pay more than their requested spend amount, including any discount amount applied.
     * <p>
     *     Note: If we know what is owed now, and we know what discount was applied, then we know what was originally owed.
     *     Using that information, we can determine if the customer attempted a partial payment or not. If a customer attempts a partial payment,
     *     the `spend_amount` is equal to the customerSpendAmount.
     *     If the customer is paying the balance in full, the `spend_amount` is equal to the totalOutstandingAmount + appliedDiscountAmount.
     * </p>
     * @param totalOutstandingAmount The current total amount of the check, including tax, in cents.
     * @param customerSpendAmount The amount the customer would like to spend, in cents.
     * @param appliedDiscountAmount The discount amount applied to the point of sale for the customer.
     */
    private static int calculateAdjustedSpendAmountCompleteOrder(int totalOutstandingAmount,
                                                                 int customerSpendAmount,
                                                                 int appliedDiscountAmount)
    {
        int theoreticalTotalOutstandingAmount = totalOutstandingAmount + Math.abs(appliedDiscountAmount);

        return Math.max(0, Math.min(customerSpendAmount, theoreticalTotalOutstandingAmount));
    }
}
