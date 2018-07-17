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
