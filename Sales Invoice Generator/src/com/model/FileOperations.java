package com.model;

import com.view.HomeFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FileOperations extends JFrame {
    public static void main(String[] args) {
        HomeFrame HF = new HomeFrame();
        for (int i = 0; i < 5; i++) {
            int x = HF.invoiceItems.getRowCount();
            x++;
            System.out.println(x);
        }
        HF.setVisible(true);
    }

    public ArrayList<InvoiceHeader> readFile(File invoiceHeadersFile) {
        ArrayList<InvoiceHeader> invoiceHeaders = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(invoiceHeadersFile));
            String line;
            while ((line = br.readLine()) != null) {
                String[] arr = line.split(",");
                int id = Integer.parseInt(arr[0]);
                String date = arr[1];
                String name = arr[2];
                InvoiceHeader header = new InvoiceHeader(id, date, name);
                invoiceHeaders.add(header);
            }
            br.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return invoiceHeaders;
    }
    public ArrayList<InvoiceLine> readInvoiceLineFile(File invoiceLinesFile) {
        ArrayList<InvoiceLine> invoiceLines = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(invoiceLinesFile));
            String line;
            while ((line = br.readLine()) != null) {
                String[] arr = line.split(",");
                int id = Integer.parseInt(arr[0]);
                String itemName = arr[1];
                double itemPrice = Double.parseDouble(arr[2]);
                int itemCount = Integer.parseInt(arr[3]);

                InvoiceLine lines = new InvoiceLine(id, itemName, itemPrice,itemCount);
                invoiceLines.add(lines);
            }
            br.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return invoiceLines;
    }

}
