package com.scvngr.levelup.pos;

public class AdjustedCheckValues {
    private int _spendAmount;
    private int _taxAmount;
    private int _excemptionsAmount;

    AdjustedCheckValues(int spendAmount, int taxAmount, int exemptionAmount)
    {
        _spendAmount = spendAmount;
        _taxAmount = taxAmount;
        _excemptionsAmount = exemptionAmount;
    }

    public int getSpendAmount() {
        return _spendAmount;
    }

    public void setSpendAmount(int _spendAmount) {
        this._spendAmount = _spendAmount;
    }

    public int getTaxAmount() {
        return _taxAmount;
    }

    public int getExcemptionsAmount() {
        return _excemptionsAmount;
    }

    @Override
    public String toString()
    {
        return String.format("SpendAmount=%s;TaxAmount=%s;ExemptionAmount=%s", _spendAmount, _taxAmount, _excemptionsAmount);
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
