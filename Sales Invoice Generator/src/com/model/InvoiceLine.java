package com.model;

public class InvoiceLine {
    private int invoiceNum;
    private String itemName;
    private double itemPrice;
    private int itemCount;
    private double total = 0.0;

    public InvoiceLine(int invoiceNum, String itemName, double itemPrice, int itemCount) {
        this.invoiceNum = invoiceNum;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemCount = itemCount;
        this.total = calcTotal();

    }

    public int getInvoiceNum() {
        return invoiceNum;
    }

    public void setInvoiceNum(int invoiceNum) {
        this.invoiceNum = invoiceNum;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
        this.total = calcTotal();
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
        this.total = calcTotal();
    }

    public double getTotal() {
        return total;
    }

    private double calcTotal() {
        return getItemPrice() * getItemCount();
    }

}
