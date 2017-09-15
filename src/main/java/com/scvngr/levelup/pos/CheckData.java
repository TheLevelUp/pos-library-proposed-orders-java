package com.scvngr.levelup.pos;

/**
 * Created by Navid on 9/15/2017.
 */

class CheckData {
    public int PaymentAmount;

    public int TaxAmount;

    public int ExemptionAmount;

    public int OutstandingAmount;

    public int getPreTaxSubtotal(){
        return OutstandingAmount - TaxAmount;
    }

    public int getNonExemptSubtotal() {
        return getPreTaxSubtotal() - ExemptionAmount;
    }
}
