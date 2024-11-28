package com.vipspeciall.reportingapiconsumer.enums;

public enum FilterField {

     TRANSACTION_UUID("Transaction UUID"),
     CUSTOMER_EMAIL("Customer Email"),
     REFERENCE_NO("Reference No"),
     CUSTOMER_DATA("Custom Data"),
     CARD_PAN("Card PAN");

     private String value;

     FilterField(String value) {
         this.value = value;
     }
     public String getValue() {
         return value;
     }
}
