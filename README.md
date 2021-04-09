# LevelUp Proposed Orders Calculator for Java
**[This Library is No Longer Being Maintained]**

- [Introduction](#introduction)
- [Usage & Order Flow](#usage--order-flow)
- [Build Instructions](#build-instructions)
- [Links](#links)

## Introduction
For point-of-sale and point-of-sale-integration developers working with LevelUp and LevelUp's Proposed Orders flow, this library simplifies what a developer needs to know to create orders. Some common inbound questions include, "What happens if a customer only wants to pay part of the bill? What do I send LevelUp?" This tool will take care of that for you.

The [`ProposedOrderCalculator`](src/main/java/com/scvngr/levelup/pos/ProposedOrderCalculator.java) accepts known check values such as:

- The current outstanding amount (including tax) due on the check.
- The current tax amount on the check.
- The total amount of items exempt from earning loyalty (e.g. tobacco, alcohol).
- The amount your customer wants to pay.

...and gives you the [`AdjustedCheckValues`](src/main/java/com/scvngr/levelup/pos/AdjustedCheckValues.java) needed to either "[create a Proposed Order](http://developer.thelevelup.com/api-reference/v15/orders-create-proposed/)" or "[complete an Order](http://developer.thelevelup.com/api-reference/v15/orders-create-completed/)".

- The `spend_amount`
- The `tax_amount`
- The `exemption_amount`

## Usage & Order Flow
Using this library with Proposed Orders is simple.

1. Just before a customer pays with LevelUp, retrieve the total due (including tax), the tax due, the total amount of exempted items, and the amount your customer wants to pay and put them into variables. Pass those values to [`ProposedOrderCalculator.calculateCreateProposedOrderValues(...)`](src/main/java/com/scvngr/levelup/pos/ProposedOrderCalculator.java#L28) before calling [`/v15/proposed_orders`](http://developer.thelevelup.com/api-reference/v15/orders-create-proposed/).

2. After applying any LevelUp discount available (returned in the previous API response), the total due (including tax), and the tax due have likely been changed; retrieve those again. Send the original exemption amount and customer payment amount along with the discount you applied to [`ProposedOrderCalculator.calculateCompleteOrderValues(...)`](src/main/java/com/scvngr/levelup/pos/ProposedOrderCalculator.java#L54) before calling [`/v15/completed_orders`](http://developer.thelevelup.com/api-reference/v15/orders-create-completed/).

```java
public class Program
{
    public static void main(String[] args) {
        // $9.90 is owed, $0.90 of that is tax.
        // $3.00 of that is tobacco/alcohol
        // The customer wants to pay $9.00 towards the check
        // Note: All of these values can be retrieved from the check
        int totalOutstandingAmount = 990;
        int taxAmountDue = 90;
        int exemptionAmount = 300;
        int customerPaymentAmount = 900;

        // Calculate adjusted values *before* making the /v15/proposed_orders request
        AdjustedCheckValues proposedOrderValues = ProposedOrderCalculator.calculateCreateProposedOrderValues(
            totalOutstandingAmount,
            taxAmountDue,
            exemptionAmount,
            customerPaymentAmount);

        // proposedOrderValues now contains three adjusted values needed for /v15/proposed_orders
        // proposedOrderValues.getSpendAmount() is 900
        // proposedOrderValues.getTaxAmount() is 0
        // proposedOrderValues.getExemptionAmount() is 300

        // -> Make request to LevelUp API /v15/proposed_orders using values within proposedOrderValues
        // <- Response contains the discount amount to apply

        // available discount amount $1
        int availableDiscountAmount = 100;

        // Apply the discount to the Point Of Sale

        // After applying the $1.00 discount, the subtotal has been reduced and the tax amount is recalculated 
        // by the Point Of Sale. Retrieve those values from the Point Of Sale again.

        // The "totalOutstandingAmount" is now $8.80; retrieve this updated value from the check 
        // The "taxAmountDue" is now $0.80; retrieve this updated value from the check

        // Calculate adjusted values *before* making the /v15/completed_orders request
        AdjustedCheckValues completedOrderValues = ProposedOrderCalculator.calculateCompleteOrderValues(
            totalOutstandingAmount,
            taxAmountDue,
            exemptionAmount,
            customerPaymentAmount,
            availableDiscountAmount);

        // completedOrderValues now contains three adjusted values needed for /v15/completed_orders
        // completedOrderValues.getSpendAmount() is 900
        // completedOrderValues.getTaxAmount() is 80
        // completedOrderValues.getExemptionAmount() is 300

        // -> Make request to LevelUp API /v15/completed_orders using values within completedOrderValues
        // <- Response
    }
}
```

## Build Instructions
- To build the jar:
```sh
gradle build
```

- To build the tests:
```sh
gradle test
```

- To add [COPYRIGHT](COPYRIGHT) header to new source files using [Gradle License Plugin](https://github.com/hierynomus/license-gradle-plugin):
```sh
gradle licenseFormat
```

## Links
- [developer.thelevelup.com](http://developer.thelevelup.com)
  - [Create Proposed Order](http://developer.thelevelup.com/api-reference/v15/orders-create-proposed/)
  - [Complete Order](http://developer.thelevelup.com/api-reference/v15/orders-create-completed/)
- [LevelUp Proposed Orders Calculator for C#](https://github.com/TheLevelUp/pos-proposed-orders-csharp)
