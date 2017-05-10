package com.scvngr.levelup.pos;

import junit.framework.TestCase;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class UpdatedExemptionAmountTests extends TestCase {


    public static class TestPaidInFull{
        // Too much is exempt
        @org.junit.Test
        public void testUpdateExemption_WhenProposedOrderRequestIs_TooMuchIsExempt_MoreThanTotal()
        {
            int outstandingTotalOnCheck = 1000;
            int taxAmount = 100;
            int exemptionAmount = 1200;
            int amountCustomerIsPaying = 1000;

            assertEquals(900, ProposedOrderCalculator.calculateAdjustedExemptionAmount(outstandingTotalOnCheck, taxAmount, exemptionAmount, amountCustomerIsPaying));
        }

        @org.junit.Test
        public void testUpdateExemption_WhenProposedOrderRequestIs_TooMuchIsExempt_LessThanTotal()
        {
            int outstandingTotalOnCheck = 1000;
            int taxAmount = 100;
            int exemptionAmount = 1050;
            int amountCustomerIsPaying = 1000;

            assertEquals(900, ProposedOrderCalculator.calculateAdjustedExemptionAmount(outstandingTotalOnCheck, taxAmount, exemptionAmount, amountCustomerIsPaying));
        }

        // Order is fully exempt
        @org.junit.Test
        public void testUpdateExemption_WhenProposedOrderRequestIs_FullyExempt()
        {
            int outstandingTotalOnCheck = 1000;
            int taxAmount = 100;
            int exemptionAmount = 1000;
            int amountCustomerIsPaying = 1000;

            assertEquals(900, ProposedOrderCalculator.calculateAdjustedExemptionAmount(outstandingTotalOnCheck, taxAmount, exemptionAmount, amountCustomerIsPaying));
        }

        // Order not exempt at all
        @org.junit.Test
        public void testUpdateExemption_WhenProposedOrderRequestIs_FullyNotExempt()
        {
            int outstandingTotalOnCheck = 1000;
            int taxAmount = 100;
            int exemptionAmount = 0;
            int amountCustomerIsPaying = 1000;

            assertEquals(0, ProposedOrderCalculator.calculateAdjustedExemptionAmount(outstandingTotalOnCheck, taxAmount, exemptionAmount, amountCustomerIsPaying));
        }
        // Some of the order is exempt
        @org.junit.Test
        public void testUpdateExemption_WhenProposedOrderRequestIs_PartiallyExempt_SmallExemption()
        {
            int outstandingTotalOnCheck = 1000;
            int taxAmount = 100;
            int exemptionAmount = 100;
            int amountCustomerIsPaying = 1000;

            assertEquals(100, ProposedOrderCalculator.calculateAdjustedExemptionAmount(outstandingTotalOnCheck, taxAmount, exemptionAmount, amountCustomerIsPaying));
        }

        // Most of the order is exempt
        @org.junit.Test
        public void testUpdateExemption_WhenProposedOrderRequestIs_MostlyExempt_LessThanSubtotal()
        {
            int outstandingTotalOnCheck = 1000;
            int taxAmount = 100;
            int exemptionAmount = 899;
            int amountCustomerIsPaying = 1000;

            assertEquals(899, ProposedOrderCalculator.calculateAdjustedExemptionAmount(outstandingTotalOnCheck, taxAmount, exemptionAmount, amountCustomerIsPaying));
        }

        @org.junit.Test
        public void testUpdateExemption_WhenProposedOrderRequestIs_MostlyExempt_EqualToSubtotal()
        {
            int outstandingTotalOnCheck = 1000;
            int taxAmount = 100;
            int exemptionAmount = 900;
            int amountCustomerIsPaying = 1000;

            assertEquals(900, ProposedOrderCalculator.calculateAdjustedExemptionAmount(outstandingTotalOnCheck, taxAmount, exemptionAmount, amountCustomerIsPaying));
        }

        @org.junit.Test
        public void testUpdateExemption_WhenProposedOrderRequestIs_MostlyExempt_GreaterThanSubtotal()
        {
            int outstandingTotalOnCheck = 1000;
            int taxAmount = 100;
            int exemptionAmount = 901;
            int amountCustomerIsPaying = 1000;

            assertEquals(900,ProposedOrderCalculator.calculateAdjustedExemptionAmount(outstandingTotalOnCheck, taxAmount, exemptionAmount, amountCustomerIsPaying));
        }
    }

    public static class TestPartialPayments{
        // Too much is exempt
        @org.junit.Test
        public void testUpdateExemption_WhenProposedOrderRequestIs_TooMuchIsExempt_MoreThanTotal()
        {
            int outstandingTotalOnCheck = 1000;
            int taxAmount = 100;
            int exemptionAmount = 1200;
            int amountCustomerIsPaying = 500;

            assertEquals(500, ProposedOrderCalculator.calculateAdjustedExemptionAmount(outstandingTotalOnCheck, taxAmount, exemptionAmount, amountCustomerIsPaying));
        }

        @org.junit.Test
        public void testUpdateExemption_WhenProposedOrderRequestIs_TooMuchIsExempt_LessThanTotal()
        {
            int outstandingTotalOnCheck = 1000;
            int taxAmount = 100;
            int exemptionAmount = 1050;
            int amountCustomerIsPaying = 500;

            assertEquals(500, ProposedOrderCalculator.calculateAdjustedExemptionAmount(outstandingTotalOnCheck, taxAmount, exemptionAmount, amountCustomerIsPaying));
        }

        // Order is fully exempt
        @org.junit.Test
        public void testUpdateExemption_WhenProposedOrderRequestIs_FullyExempt()
        {
            int outstandingTotalOnCheck = 1000;
            int taxAmount = 100;
            int exemptionAmount = 1000;
            int amountCustomerIsPaying = 500;

            assertEquals(500, ProposedOrderCalculator.calculateAdjustedExemptionAmount(outstandingTotalOnCheck, taxAmount, exemptionAmount, amountCustomerIsPaying));
        }

        // Order not exempt at all
        @org.junit.Test
        public void testUpdateExemption_WhenProposedOrderRequestIs_FullyNotExempt()
        {
            int outstandingTotalOnCheck = 1000;
            int taxAmount = 100;
            int exemptionAmount = 0;
            int amountCustomerIsPaying = 500;

            assertEquals(0, ProposedOrderCalculator.calculateAdjustedExemptionAmount(outstandingTotalOnCheck, taxAmount, exemptionAmount, amountCustomerIsPaying));
        }

        // Some of the order is exempt
        @org.junit.Test
        public void testUpdateExemption_WhenProposedOrderRequestIs_PartiallyExempt_SmallExemption()
        {
            int outstandingTotalOnCheck = 1000;
            int taxAmount = 100;
            int exemptionAmount = 100;
            int amountCustomerIsPaying = 500;

            assertEquals(0, ProposedOrderCalculator.calculateAdjustedExemptionAmount(outstandingTotalOnCheck, taxAmount, exemptionAmount, amountCustomerIsPaying));
        }

        // Most of the order is exempt
        @org.junit.Test
        public void testUpdateExemption_WhenProposedOrderRequestIs_MostlyExempt_LessThan_CustomerPayment()
        {
            int outstandingTotalOnCheck = 1000;
            int taxAmount = 100;
            int exemptionAmount = 499;
            int amountCustomerIsPaying = 500;

            // of the $9.00 subtotal, only $4.99 is exempt; $9.00 - $4.99 = $4.01 can be paid before claiming responsibility for exemption amounts
            assertEquals(99, ProposedOrderCalculator.calculateAdjustedExemptionAmount(outstandingTotalOnCheck, taxAmount, exemptionAmount, amountCustomerIsPaying));
        }

        @org.junit.Test
        public void testUpdateExemption_WhenProposedOrderRequestIs_MostlyExempt_EqualTo_CustomerPayment()
        {
            int outstandingTotalOnCheck = 1000;
            int taxAmount = 100;
            int exemptionAmount = 500;
            int amountCustomerIsPaying = 500;

            // of the $9.00 subtotal, only $5.00 is exempt; $9.00 - $5.00 = $4.00 can be paid before claiming responsibility for exemption amounts
            assertEquals(100, ProposedOrderCalculator.calculateAdjustedExemptionAmount(outstandingTotalOnCheck, taxAmount, exemptionAmount, amountCustomerIsPaying));
        }

        @org.junit.Test
        public void testUpdateExemption_WhenProposedOrderRequestIs_MostlyExempt_GreaterThan_CustomerPayment()
        {
            int outstandingTotalOnCheck = 1000;
            int taxAmount = 100;
            int exemptionAmount = 501;
            int amountCustomerIsPaying = 500;

            // of the $9.00 subtotal, only $5.01 is exempt; $9.00 - $5.01 = $3.99 can be paid before claiming responsibility for exemption amounts
            assertEquals(101, ProposedOrderCalculator.calculateAdjustedExemptionAmount(outstandingTotalOnCheck, taxAmount, exemptionAmount, amountCustomerIsPaying));
        }

        // Most of the order is exempt
        @org.junit.Test
        public void testUpdateExemption_WhenProposedOrderRequestIs_MostlyExempt_LessThan_CustomerPayment_Fringe()
        {
            int outstandingTotalOnCheck = 1000;
            int taxAmount = 100;
            int exemptionAmount = 399;
            int amountCustomerIsPaying = 500;

            // of the $9.00 subtotal, only $4.99 is exempt; $9.00 - $3.99 = $5.01 can be paid before claiming responsibility for exemption amounts
            assertEquals(0, ProposedOrderCalculator.calculateAdjustedExemptionAmount(outstandingTotalOnCheck, taxAmount, exemptionAmount, amountCustomerIsPaying));
        }

        @org.junit.Test
        public void testUpdateExemption_WhenProposedOrderRequestIs_MostlyExempt_EqualTo_CustomerPayment_Fringe()
        {
            int outstandingTotalOnCheck = 1000;
            int taxAmount = 100;
            int exemptionAmount = 400;
            int amountCustomerIsPaying = 500;

            // of the $9.00 subtotal, only $5.00 is exempt; $9.00 - $4.00 = $5.00 can be paid before claiming responsibility for exemption amounts
            assertEquals(0, ProposedOrderCalculator.calculateAdjustedExemptionAmount(outstandingTotalOnCheck, taxAmount, exemptionAmount, amountCustomerIsPaying));
        }

        @org.junit.Test
        public void testUpdateExemption_WhenProposedOrderRequestIs_MostlyExempt_GreaterThan_CustomerPayment_Fringe()
        {
            int outstandingTotalOnCheck = 1000;
            int taxAmount = 100;
            int exemptionAmount = 401;
            int amountCustomerIsPaying = 500;

            // of the $9.00 subtotal, only $5.01 is exempt; $9.00 - $4.01 = $4.99 can be paid before claiming responsibility for exemption amounts
            assertEquals(1, ProposedOrderCalculator.calculateAdjustedExemptionAmount(outstandingTotalOnCheck, taxAmount, exemptionAmount, amountCustomerIsPaying));
        }
    }

    public static class TestCherryPickedPartialPayments {
        // Most of the order is exempt
        @org.junit.Test
        public void testUpdateExemption_WhenProposedOrderRequestIs_MostlyExempt_LessThan_CustomerPayment_Fringe()
        {
            int outstandingTotalOnCheck = 600;
            int taxAmount = 200;
            int exemptionAmount = 399;
            int amountCustomerIsPaying = 500;

            // of the $6.00 subtotal, only $4.99 is exempt; $6.00 - $3.99 = $2.01 can be paid before claiming responsibility for exemption amounts
            assertEquals(399, ProposedOrderCalculator.calculateAdjustedExemptionAmount(outstandingTotalOnCheck, taxAmount, exemptionAmount, amountCustomerIsPaying));
        }

        @org.junit.Test
        public void testUpdateExemption_WhenProposedOrderRequestIs_MostlyExempt_EqualTo_CustomerPayment_Fringe()
        {
            int outstandingTotalOnCheck = 600;
            int taxAmount = 200;
            int exemptionAmount = 400;
            int amountCustomerIsPaying = 500;

            // of the $6.00 subtotal, only $5.00 is exempt; $6.00 - $4.00 = $2.00 can be paid before claiming responsibility for exemption amounts
            assertEquals(400, ProposedOrderCalculator.calculateAdjustedExemptionAmount(outstandingTotalOnCheck, taxAmount, exemptionAmount, amountCustomerIsPaying));
        }

        @org.junit.Test
        public void testUpdateExemption_WhenProposedOrderRequestIs_MostlyExempt_GreaterThan_CustomerPayment_Fringe()
        {
            int outstandingTotalOnCheck = 600;
            int taxAmount = 200;
            int exemptionAmount = 401;
            int amountCustomerIsPaying = 500;

            // of the $6.00 subtotal, only $5.01 is exempt; $6.00 - $4.01 = $1.99 can be paid before claiming responsibility for exemption amounts
            assertEquals(400, ProposedOrderCalculator.calculateAdjustedExemptionAmount(outstandingTotalOnCheck, taxAmount, exemptionAmount, amountCustomerIsPaying));
        }
    }
}

