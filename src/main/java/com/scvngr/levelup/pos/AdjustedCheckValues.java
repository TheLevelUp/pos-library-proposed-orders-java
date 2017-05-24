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

    public int getExemptionsAmount() {
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

        boolean haveSameExemptionAmount = adjustedCheckValues.getExemptionsAmount() == this.getExemptionsAmount();
        boolean haveSameSpendAmount = adjustedCheckValues.getSpendAmount() == this.getSpendAmount();
        boolean haveSameTaxAmount = adjustedCheckValues.getTaxAmount() == this.getTaxAmount();

        return haveSameExemptionAmount && haveSameSpendAmount && haveSameTaxAmount;
    }
}
