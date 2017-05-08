package com.levelup.pos;

public class AdjustedCheckValues {
    private int _spendAmount;
    private int _taxAmount;
    private int _excemptionsAmount;

    public AdjustedCheckValues(int spendAmount, int taxAmount, int exemptionAmount)
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
}
