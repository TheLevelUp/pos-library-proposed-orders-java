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
package com.scvngr.levelup.pos.mocks;

/**
 * Created by Navid on 9/15/2017.
 */

public class Check {
    private int startingTotal;
    private int startingTax;

    private int totalDiscounts;
    private int totalTenders;

    private int getStartingSubTotal() {
        return startingTotal - startingTax;
    }

    private int getAdjustedSubtotal(){
        return getStartingSubTotal() - totalDiscounts;
    }

    private double getAdjustedSubtotalInDollars(){
        return getAdjustedSubtotal() / 100;
    }

    private int getTaxRate() {
        return startingTax / getStartingSubTotal();
    }

    private int getAdjustedTax(){
        return getAdjustedSubtotal() *  getTaxRate();
    }

    /**
     * The current amount, in cents, owed by a customer, at any given moment.
     */
    public int getTotalOutstandingAmount(){
        return getAdjustedSubtotal() + getAdjustedTax() - totalTenders;
    }

    /**
     * The total amount of tax due, in cents, on a check, at any given moment.
     */
    public int getTotalTaxAmount(){ return getAdjustedTax();}

    /**
     * By passing in a total amount owed and the tax due on a check, we can simulate a simplistic point of sale
     * for testing purposes.
     * @param total The initial amount due on the check including tax, in cents.
     * @param tax The initial tax due on the check, in cents.
     */
    public Check(int total, int tax)
    {
        startingTotal = total;
        startingTax = tax;
    }

    /**
     * Applies a pre-tax discount to the check.
     */
    public void applyDiscount(int discountAmountInCents){
        totalDiscounts += discountAmountInCents;
    }

    /**
     * Apply a standard tender to the check. e.g. Cash, Credit Card, etc.
     */
    public void applyTender(int tenderAmountInCents){
        totalTenders += tenderAmountInCents;
    }
}
