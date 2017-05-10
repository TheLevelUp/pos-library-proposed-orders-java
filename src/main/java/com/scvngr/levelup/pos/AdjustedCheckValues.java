package com.scvngr.levelup.pos;

public class AdjustedCheckValues {
    private int spendAmount;
    private int taxAmount;
    private int exemptionAmount ;

    AdjustedCheckValues(int spendAmount, int taxAmount, int exemptionAmount)
    {
        this.spendAmount = spendAmount;
        this.taxAmount = taxAmount;
        this.exemptionAmount = exemptionAmount;
    }

    public int getSpendAmount() {
        return spendAmount;
    }

    void setSpendAmount(int _spendAmount) {
        this.spendAmount = _spendAmount;
    }

    public int getTaxAmount() {
        return taxAmount;
    }

    public int getExcemptionsAmount() {
        return exemptionAmount ;
    }

    @Override
    public String toString()
    {
        return String.format("SpendAmount=%s;TaxAmount=%s;ExemptionAmount=%s", spendAmount, taxAmount, exemptionAmount );
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null || !(obj instanceof AdjustedCheckValues)){
            return false;
        }

        AdjustedCheckValues adjustedCheckValues = (AdjustedCheckValues) obj;

        boolean haveSameExcemptionAmount = adjustedCheckValues.getExcemptionsAmount() == this.getExcemptionsAmount();
        boolean haveSameSpendAmount = adjustedCheckValues.getSpendAmount() == this.getSpendAmount();
        boolean haveSameTaxAmount = adjustedCheckValues.getTaxAmount() == this.getTaxAmount();

        return haveSameExcemptionAmount && haveSameSpendAmount && haveSameTaxAmount;
    }
}
