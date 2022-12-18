package com.model;

import java.util.ArrayList;

public class InvoiceHeader {
    private int invoiceNum;
    private String invoiceDate;
    private String customerName;
    private Double sumTotal = 0.0;

    private ArrayList<InvoiceLine> lines;

    public InvoiceHeader(int invoiceNum, String invoiceDate, String customerName) {
        this.invoiceNum = invoiceNum;
        this.invoiceDate = invoiceDate;
        this.customerName = customerName;
        this.lines = new ArrayList<>();
    }

    public int getInvoiceNum() {
        return invoiceNum;
    }

    public void setInvoiceNum(int invoiceNum) {
        this.invoiceNum = invoiceNum;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Double getSumTotal() {
        return sumTotal;
    }

    public void addLine(InvoiceLine line) {
        this.lines.add(line);
        setSumTotal(line);
    }

    private void setSumTotal(InvoiceLine line) {
        this.sumTotal += line.getTotal();
    }
}
