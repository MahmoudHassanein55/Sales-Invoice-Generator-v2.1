package com.view;

import com.controller.DataStore;
import com.model.FileOperations;
import com.model.InvoiceHeader;
import com.model.InvoiceLine;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;

public class HomeFrame extends JFrame implements ActionListener {
    private JMenuBar menuBar;
    private JMenu fileMenu;
    private JMenuItem LoadFileMenuItem;
    private JMenuItem SaveFileMenuItem;
    private JMenuItem ExitFileMenuItem;
    private JPanel panelLeft;
    public JTable invoiceTable;
    private JPanel panelRightDown;
    private JPanel panelRightUp;
    public JTable invoiceItems;
    public JTextField tfInvoiceNumber = null;
    public JTextField tfInvoiceDate = null;
    public JTextField tfInvoiceCustomer = null;
    public JTextField tfInvoiceTotal = null;
    public JTextField tfItemName = null;
    public JTextField tfItemPrice = null;
    public JTextField tfItemCount = null;
    private JButton buttonCreateNewInvoice;
    private JButton buttonDeleteInvoice;
    private JButton buttonSave;
    private JButton buttonCancel;

    public HomeFrame() {
        super("Sales Invoice Generator");
//=========================Menu Bar=====================
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        LoadFileMenuItem = new JMenuItem("Load File", 'L');
        LoadFileMenuItem.setAccelerator(KeyStroke.getKeyStroke('L', KeyEvent.CTRL_MASK));
        LoadFileMenuItem.addActionListener(this);

        SaveFileMenuItem = new JMenuItem("Save File", 'S');
        SaveFileMenuItem.setAccelerator(KeyStroke.getKeyStroke('S', KeyEvent.CTRL_MASK));
        SaveFileMenuItem.addActionListener(this);

        ExitFileMenuItem = new JMenuItem("Exit File", 'X');
        ExitFileMenuItem.setAccelerator(KeyStroke.getKeyStroke('X', KeyEvent.CTRL_MASK));
        ExitFileMenuItem.addActionListener(this);

        fileMenu.add(LoadFileMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(SaveFileMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(ExitFileMenuItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
        //===========================Left Side Panel================================
        panelLeft = new JPanel();
        panelLeft.setBounds(0, 60, 500, 440);
        add(panelLeft);
        //=============================Invoice Table===================================
        invoiceTable = new JTable();
        invoiceTable.setBounds(0, 60, 200, 200);
        panelLeft.add(new JScrollPane(invoiceTable));
        final DefaultTableModel model = (DefaultTableModel) invoiceTable.getModel();
        model.addColumn("NO.");                 // Create Column and add to Jtable
        model.addColumn("Date");                    // Create Column and add to Jtable
        model.addColumn("Customer Name");
        model.addColumn("Total");
        //===============================First button(Create)=================================
        buttonCreateNewInvoice = new JButton("Create New Invoice");
        buttonCreateNewInvoice.setBounds(100, 610, 150, 30);
        buttonCreateNewInvoice.addActionListener(this);
        add(buttonCreateNewInvoice);
        //===============================second button(Delete)=================================
        buttonDeleteInvoice = new JButton("Delete Invoice");
        buttonDeleteInvoice.setBounds(260, 610, 150, 30);
        buttonDeleteInvoice.addActionListener(this);
        add(buttonDeleteInvoice);
        //=============================Right Side Panel Up====================================================
        panelRightUp = new JPanel();
        panelRightUp.setBounds(550, 0, 500, 140);
        add(panelRightUp);
        //=====================Add label on panel=========
        //you can use this JTextFields to create new invoice or to update
        panelRightUp.add(new JLabel("you can use this to create new invoice  --- add data here"));
        panelRightUp.add(new JLabel("=====>=====>=====>=====>=====>=====>   "));
        panelRightUp.add(new JLabel("or to update on invoice table data"));
        panelRightUp.add(new JLabel("Invoice Number"));
        tfInvoiceNumber = new JTextField(10);
        panelRightUp.add(tfInvoiceNumber);
        panelRightUp.add(new JLabel("Invoice Date"));
        tfInvoiceDate = new JTextField(10);
        panelRightUp.add(tfInvoiceDate);
        panelRightUp.add(new JLabel("Customer name"));
        tfInvoiceCustomer = new JTextField(10);
        panelRightUp.add(tfInvoiceCustomer);
        //==================================
        panelRightUp.add(new JLabel("=====>=====>=====>=====>=====>=====>   "));
        panelRightUp.add(new JLabel("Item Name"));
        tfItemName = new JTextField(10);
        // tfInvoiceTotal.setEditable(false);
        panelRightUp.add(tfItemName);
        panelRightUp.add(new JLabel("Item Price"));
        tfItemPrice = new JTextField(17);
        // tfInvoiceTotal.setEditable(false);
        panelRightUp.add(tfItemPrice);
        panelRightUp.add(new JLabel("Item count"));
        tfItemCount = new JTextField(10);
        // tfInvoiceTotal.setEditable(false);
        panelRightUp.add(tfItemCount);
        //===========================Right Side Panel Down================================
        panelRightDown = new JPanel();
        panelRightDown.setBounds(550, 160, 500, 400);
        add(panelRightDown);
        //=====================================Item Table==========================================
        invoiceItems = new JTable();
        invoiceItems.setBounds(550, 0, 200, 200);
        panelRightDown.add(new JScrollPane(invoiceItems));
        final DefaultTableModel model2 = (DefaultTableModel) invoiceItems.getModel();
        model2.addColumn("NO.");                 // Create Column and add to Jtable
        model2.addColumn("item Name");                    // Create Column and add to Jtable
        model2.addColumn("item Price");
        model2.addColumn("count");
        model2.addColumn("Total");
        //===============================First button(Save)=================================
        buttonSave = new JButton("Create New Item");
        buttonSave.setBounds(700, 610, 130, 30);
        buttonSave.addActionListener(this);
        add(buttonSave);
        //===============================second button(Cancel)=================================
        buttonCancel = new JButton("Delete Item");
        buttonCancel.setBounds(835, 610, 100, 30);
        buttonCancel.addActionListener(this);
        add(buttonCancel);
        //=================Frame1 Size ,Layout ,Visible ,Location
        setSize(1050, 750);
        setLayout(null);
        setVisible(true);
        setLocation(300, 10);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //====================================================
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(LoadFileMenuItem)) //from the Pop-up screen choose InvoiceHeader file to load
        {
            JFileChooser fileOpen = new JFileChooser();
            int result = fileOpen.showDialog(null, "Choose Header file for load");
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileOpen.getSelectedFile();
                FileOperations FO = new FileOperations();
                DataStore.invoiceHeaders = FO.readFile(file);
                DefaultTableModel model = (DefaultTableModel) invoiceTable.getModel();
                model.setRowCount(0);

                int row = 0;
                for (InvoiceHeader header : DataStore.invoiceHeaders) {
                    model.addRow(new Object[0]);
                    model.setValueAt(header.getInvoiceNum(), row, 0);
                    model.setValueAt(header.getInvoiceDate(), row, 1);
                    model.setValueAt(header.getCustomerName(), row, 2);
                    model.setValueAt(header.getSumTotal(), row, 3);
                    row++;
                }
            }
            if (e.getSource().equals(LoadFileMenuItem))//from the Pop-up screen choose InvoiceLine file to load
            {
                JFileChooser fileOpen2 = new JFileChooser();
                int result2 = fileOpen.showDialog(null, "Choose Item file for load");
                if (result2 == JFileChooser.APPROVE_OPTION) {
                    File file2 = fileOpen.getSelectedFile();
                    FileOperations FO = new FileOperations();
                    ArrayList<InvoiceLine> invoiceLines = FO.readInvoiceLineFile(file2);
                    DefaultTableModel model = (DefaultTableModel) invoiceItems.getModel();
                    model.setRowCount(0);
                    int row = 0;
                    for (InvoiceLine line : invoiceLines) {
                        model.addRow(new Object[0]);
                        model.setValueAt(line.getInvoiceNum(), row, 0);
                        model.setValueAt(line.getItemName(), row, 1);
                        model.setValueAt(line.getItemPrice(), row, 2);
                        model.setValueAt(line.getItemCount(), row, 3);
                        model.setValueAt(line.getTotal(), row, 4);
                        row++;

                        // get the invoice header of the current invoice line or null if it doesn't exist
                        InvoiceHeader lineInvoiceHeader = DataStore.invoiceHeaders
                                .stream()
                                .filter(header -> header.getInvoiceNum() == line.getInvoiceNum())
                                .findFirst()
                                .orElse(null);

                        // if there is a header for this line then add the line to the header's lines
                        if (lineInvoiceHeader != null) {
                            lineInvoiceHeader.addLine(line);
                        }
                    }
                }

                int row = 0;
                DefaultTableModel model = (DefaultTableModel) invoiceTable.getModel();
                for (InvoiceHeader header : DataStore.invoiceHeaders) {
                    model.setValueAt(header.getSumTotal(), row, 3);
                    row++;
                }


            }
        } else if (e.getSource().equals(SaveFileMenuItem))// to save header file
        {
            JFileChooser fileOpen = new JFileChooser();
            int result = fileOpen.showDialog(null, "Choose Header file for save");
            if (result == JFileChooser.APPROVE_OPTION) {
                String filePath = fileOpen.getSelectedFile().getPath();
                File file = new File(filePath);
                try {
                    FileWriter fw = new FileWriter(file);
                    BufferedWriter bw = new BufferedWriter(fw);
                    for (int i = 0; i < invoiceTable.getRowCount(); i++) {
                        for (int j = 0; j < invoiceTable.getColumnCount(); j++) {
                            bw.write(invoiceTable.getValueAt(i, j).toString() + ",");
                        }
                        bw.newLine();
                    }
                    bw.close();
                    fw.close();

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "error", "error", JOptionPane.ERROR_MESSAGE);
                }
            }
            if (e.getSource().equals(SaveFileMenuItem))// to save item file
            {
                JFileChooser fileOpen2 = new JFileChooser();
                int result2 = fileOpen2.showDialog(null, "Choose Item file for save");
                System.out.println(result2);
                if (result2 == JFileChooser.APPROVE_OPTION) {
                    String filePath2 = fileOpen2.getSelectedFile().getPath();
                    File file2 = new File(filePath2);
                    try {
                        FileWriter fw = new FileWriter(file2);
                        BufferedWriter bw = new BufferedWriter(fw);
                        for (int i = 0; i < invoiceItems.getRowCount(); i++) {
                            for (int j = 0; j < invoiceItems.getColumnCount(); j++) {
                                bw.write(invoiceItems.getValueAt(i, j).toString() + ",");
                            }
                            bw.newLine();
                        }
                        bw.close();
                        fw.close();
                        JOptionPane.showMessageDialog(this, "File Saved", "Info", JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(this, "error", "error", JOptionPane.ERROR_MESSAGE);
                    }

                }
            }
        } else if (e.getSource().equals(ExitFileMenuItem))//to close app

        {
            System.exit(0);
        } else if (e.getSource().equals(buttonCreateNewInvoice))// to create Invoice take all data from the user (from JTextFields) and Insert it in the JTables

        {
            try {
                int invoiceNumber = Integer.parseInt(tfInvoiceNumber.getText());
                double total = 0;
                double price = Double.parseDouble(tfItemPrice.getText());
                int count = Integer.parseInt(tfItemCount.getText());
                total = price * count;
                final DefaultTableModel headerModel = (DefaultTableModel) invoiceTable.getModel();
                final DefaultTableModel lineModel = (DefaultTableModel) invoiceItems.getModel();

                lineModel.addRow(new Object[]{invoiceNumber, tfItemName.getText(), tfItemPrice.getText(), tfItemCount.getText(), total});

                boolean doesHeaderExists = false;
                double existingHeaderTotalSum = -1;
                // loop over the table rows to find a certain id
                for (int i = 0; i < headerModel.getRowCount(); i++) {
                    int id = Integer.parseInt(headerModel.getValueAt(i, 0).toString());

                    // if the user DOES already exist in the headers table,
                    // then UPDATE it's current totalSum by adding the price input to it
                    if (id == invoiceNumber) {
                        doesHeaderExists = true;
                        existingHeaderTotalSum = Double.parseDouble(headerModel.getValueAt(i, 3).toString());
                        double newSumTotal = existingHeaderTotalSum + total;
                        headerModel.setValueAt(newSumTotal, i, 3);
                        break;
                    }
                }
                // else if the user does not exist in the headers table,
                // then CREATE it and set its total sum to be the same as the input price
                if (!doesHeaderExists) {
                    headerModel.addRow(new Object[]{tfInvoiceNumber.getText(), tfInvoiceDate.getText(), tfInvoiceCustomer.getText(), total});
                }

                JOptionPane.showMessageDialog(this, "Invoice Created", "Info", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "error", "error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource().equals(buttonDeleteInvoice))// to delete from JTable the user must select the row from JTable named invoiceTable (Header Table) & must select the row from JTable named invoiceItems (Item Table)

        {
            DefaultTableModel headerModel = (DefaultTableModel) invoiceTable.getModel();
            DefaultTableModel lineModel = (DefaultTableModel) invoiceItems.getModel();
            try {
                headerModel.removeRow(invoiceTable.getSelectedRow());
                JOptionPane.showMessageDialog(this, "Invoice Deleted", "Info", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "error", "error", JOptionPane.ERROR_MESSAGE);
            }
            try {
                lineModel.removeRow(invoiceItems.getSelectedRow());
                JOptionPane.showMessageDialog(this, "Item Deleted", "Info", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "error", "error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource().equals(buttonSave))//to update two JTables the user must fill all JTextFields and must select the two rows to update from two tables

        {
            try {
                int invoiceNumber = Integer.parseInt(tfInvoiceNumber.getText());
                double total = 0;
                double price = Double.parseDouble(tfItemPrice.getText());
                int count = Integer.parseInt(tfItemCount.getText());
                total = price * count;
                final DefaultTableModel headerModel = (DefaultTableModel) invoiceTable.getModel();
                final DefaultTableModel lineModel = (DefaultTableModel) invoiceItems.getModel();
                lineModel.addRow(new Object[]{invoiceNumber, tfItemName.getText(), tfItemPrice.getText(), tfItemCount.getText(), total});

                boolean doesHeaderExists = false;
                double existingHeaderTotalSum = -1;
                // loop over the table rows to find a certain id
                for (int i = 0; i < headerModel.getRowCount(); i++) {
                    int id = Integer.parseInt(headerModel.getValueAt(i, 0).toString());
                    // if the user DOES already exist in the headers table,
                    // then UPDATE it's current totalSum by adding the price input to it
                    if (id == invoiceNumber) {
                        doesHeaderExists = true;
                        existingHeaderTotalSum = Double.parseDouble(headerModel.getValueAt(i, 3).toString());
                        double newSumTotal = existingHeaderTotalSum + total;
                        headerModel.setValueAt(newSumTotal, i, 3);
                        break;
                    }
                }
                JOptionPane.showMessageDialog(this, "Saved", "Info", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "error", "error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource().equals(buttonCancel))//to delete from JTable the user must select the row from JTable named invoiceItem (Item Table)

        {
            try {
                tfInvoiceNumber.setText("");
                tfInvoiceDate.setText("");
                tfInvoiceCustomer.setText("");
                tfInvoiceTotal.setText("");
                tfItemName.setText("");
                tfItemPrice.setText("");
                tfItemCount.setText("");
                JOptionPane.showMessageDialog(this, "Cancel", "Info", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "error", "error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
