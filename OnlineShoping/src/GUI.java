import javax.swing.*;
import java.awt.*;
import java.util.List;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Comparator;

public class GUI {
    private double totalCalculation;
    public void GUI(List<Electronics>electronicsList,List<Clothing>clothingList,List<Product>productList){
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());

        JButton shoppingCartButton = new JButton("Shopping Cart");
        JPanel shoppingCartPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        shoppingCartPanel.add(shoppingCartButton);
        topPanel.add(shoppingCartPanel, BorderLayout.NORTH);



        DefaultTableModel cartTableModel = new DefaultTableModel();



        shoppingCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {



                JFrame cartFrame = new JFrame("Shopping Cart");


                // Initialize the shopping cart table model with columns

                if (cartTableModel.getColumnCount() == 0) {
                    cartTableModel.addColumn("Product");
                    cartTableModel.addColumn("Available Items");
                    cartTableModel.addColumn("Price");
                }




                JTable cartTable = new JTable(cartTableModel);
                cartTable.setRowHeight(50);
                cartFrame.add(new JScrollPane(cartTable));
                cartFrame.setSize(500, 500);
                cartFrame.setVisible(true);



                JLabel totalPriceLabel = new JLabel("Total Price: " + totalCalculation);
                cartFrame.add(totalPriceLabel, BorderLayout.SOUTH);

            }
        });






        JComboBox<String> comboBox = new JComboBox<>(new String[]{"All", "Electronics", "Clothing"});
        JPanel comboBoxPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        comboBoxPanel.add(new JLabel("Select Product Category : "));
        comboBoxPanel.add(comboBox);
        topPanel.add(comboBoxPanel, BorderLayout.CENTER);

        frame.add(topPanel, BorderLayout.NORTH);

        frame.setSize(1000, 700);
        frame.setVisible(true);

        JPanel tablePanel = new JPanel(new BorderLayout());
        frame.add(tablePanel, BorderLayout.CENTER);




        String[] columns = {"ID", "Name", "Category", "Price", "Info"};
        int totalRowCount = electronicsList.size() + clothingList.size();
        Object[][] data = new Object[totalRowCount][columns.length];
        int row = 0;


        for (Electronics electronics : electronicsList) {


                data[row][0] = electronics.getProductID();
                data[row][1] = electronics.getProductName();
                data[row][2] = electronics.getProductType();
                data[row][3] = electronics.getPrice();
                data[row][4] = electronics.getBrand() + "," + electronics.getWarrantyPeriod();

            row++;
        }



        for (Clothing clothing : clothingList) {
            data[row][0] = clothing.getProductID();
            data[row][1] = clothing.getProductName();
            data[row][2] = clothing.getProductType();
            data[row][3] = clothing.getPrice();
            data[row][4] = clothing.getSize() + "," + clothing.getColour();
            row++;
        }

        JTable combinedTable = new JTable(data, columns);
        frame.getContentPane().removeAll();
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(combinedTable), BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();




        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {





                String selected = (String) comboBox.getSelectedItem();
                if ("Electronics".equals(selected)) {

                    String[] columns = {"ID", "Name", "Category", "Price", "Info"};
                    Object[][] data = new Object[electronicsList.size()][columns.length];
                    int row = 0;
                    for (Electronics electronic : electronicsList) {
                        data[row][0] = electronic.getProductID();
                        data[row][1] = electronic.getProductName();
                        data[row][2] = electronic.getProductType();
                        data[row][3] = electronic.getPrice();
                        data[row][4] = electronic.getBrand()+","+electronic.getWarrantyPeriod();
                        row++;
                    }


                    JTable table = new JTable(data, columns);
                    frame.getContentPane().removeAll();
                    frame.add(topPanel, BorderLayout.NORTH);
                    frame.add(new JScrollPane(table), BorderLayout.CENTER);
                    frame.revalidate();
                    frame.repaint();





                    // table eke yata panel eke ekathu wena ewa
                    Component[] components = frame.getContentPane().getComponents();
                    for (Component component : components) {
                        if (component instanceof JPanel) {
                            String constraints = ((JPanel) component).getLayout().toString();
                            if (constraints.contains("SOUTH")) {
                                frame.remove(component);
                                break;
                            }
                        }
                    }





                    JPanel detailsPanel = new JPanel(new GridLayout(8, 2)); // GridLayout with 1 column, multiple rows



                    // Add the JTextArea to detailsPanel

                    frame.add(detailsPanel, BorderLayout.SOUTH);

                    table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                        @Override
                        public void valueChanged(ListSelectionEvent event) {
                            if (!event.getValueIsAdjusting()) {
                                int selectedRow = table.getSelectedRow();
                                if (selectedRow != -1 && selectedRow < electronicsList.size()) {
                                    Electronics electronic = electronicsList.get(selectedRow);

                                    // Set details using JLabels in the detailsPanel
                                    detailsPanel.removeAll(); // Clear previous labels

                                    detailsPanel.add(new JLabel("                   Select Product-Details"));
                                    detailsPanel.add(new Label("                    ID: " + electronic.getProductID()));
                                    detailsPanel.add(new Label("                    Name: " + electronic.getProductName()));
                                    detailsPanel.add(new Label("                    Category: " + electronic.getProductType()));
                                    detailsPanel.add(new Label("                    Price: " + electronic.getPrice()));
                                    detailsPanel.add(new Label("                    Info: " + electronic.getBrand() + ", " + electronic.getWarrantyPeriod()));



                                    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                                    JButton addToCart = new JButton("Add to Cart");
                                    addToCart.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {

                                            String productId = "<html>" + electronic.getProductID() + "<br>" + electronic.getBrand() + "<br>" + electronic.getWarrantyPeriod() + "</html>";
                                            int quantity =1;
                                            double price = electronic.getPrice();




                                            if (cartTableModel.getColumnCount() == 0) {
                                                cartTableModel.addColumn("Product");
                                                cartTableModel.addColumn("Available Items");
                                                cartTableModel.addColumn("Price");
                                            }


                                            boolean found = false;
                                            for (int i = 0; i < cartTableModel.getRowCount(); i++) {
                                                if (cartTableModel.getValueAt(i, 0).equals(productId)) {
                                                    int currentQuantity = (int) cartTableModel.getValueAt(i, 1);
                                                    cartTableModel.setValueAt(currentQuantity + 1, i, 1);
                                                    JOptionPane.showMessageDialog(frame,"Product Added.");
                                                    found = true;
                                                    break;
                                                }
                                            }


                                            if (!found) {
                                                Object[] rowData = {productId, quantity, price};
                                                cartTableModel.addRow(rowData);
                                                JOptionPane.showMessageDialog(frame,"Product Added.");
                                            }
                                            totalCalculation += quantity * price;
                                        }
                                    });


                                    buttonPanel.add(addToCart); // Add button to the center-aligned panel
                                    detailsPanel.add(buttonPanel);
                                    frame.revalidate(); // Update the layout
                                    // Repaint the frame to reflect changes
                                }
                            }
                        }
                    });
                    // table eke yata panel eke iwara wena tena


                    frame.setBackground(Color.BLUE);
                    frame.setVisible(true);




                } else if ("Clothing".equals(selected)) {
                    String[] columns ={"ID","Name","Category","Price","Info"};
                    Object[][] data= new Object[clothingList.size()][columns.length];
                    int row = 0;
                    for (Clothing clothing : clothingList) {
                        data[row][0] = clothing.getProductID();
                        data[row][1] = clothing.getProductName();
                        data[row][2] = clothing.getProductType();
                        data[row][3] = clothing.getPrice();
                        data[row][4] = clothing.getSize()+","+clothing.getColour();
                        row++;
                    }

                    JTable table = new JTable(data, columns);
                    frame.getContentPane().removeAll();
                    frame.add(topPanel, BorderLayout.NORTH);
                    frame.add(new JScrollPane(table), BorderLayout.CENTER);
                    frame.revalidate();
                    frame.repaint();




                    // table eke yata panel eke ekathu wena ewa
                    Component[] components = frame.getContentPane().getComponents();
                    for (Component component : components) {
                        if (component instanceof JPanel) {
                            String constraints = ((JPanel) component).getLayout().toString();
                            if (constraints.contains("SOUTH")) {
                                frame.remove(component);
                                break;
                            }
                        }
                    }





                    JPanel detailsPanel = new JPanel(new GridLayout(10, 2)); // GridLayout with 1 column, multiple rows



                    // Add the JTextArea to detailsPanel

                    frame.add(detailsPanel, BorderLayout.SOUTH);

                    table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                        @Override
                        public void valueChanged(ListSelectionEvent event) {
                            if (!event.getValueIsAdjusting()) {
                                int selectedRow = table.getSelectedRow();
                                if (selectedRow != -1 && selectedRow < clothingList.size()) {
                                    Clothing clothing = clothingList.get(selectedRow);

                                    // Set details using JLabels in the detailsPanel
                                    detailsPanel.removeAll(); // Clear previous labels

                                    detailsPanel.add(new JLabel("Select Product-Details"));
                                    detailsPanel.add(new Label("ID: " + clothing.getProductID()));
                                    detailsPanel.add(new Label("Name: " + clothing.getProductName()));
                                    detailsPanel.add(new Label("Category: " + clothing.getProductType()));
                                    detailsPanel.add(new Label("Price: " + clothing.getPrice()));


                                    frame.revalidate(); // Update the layout
                                    // Repaint the frame to reflect changes


                                    //clothing add to cart button and total and other methods

                                    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                                    JButton addToCart = new JButton("Add to Cart");
                                    addToCart.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {

                                            String productId = "<html>" + clothing.getProductID() + "<br>" + clothing.getProductName() + "<br>" + clothing.getColour() + "</html>";
                                            int quantity =1;
                                            double price = clothing.getPrice();




                                            if (cartTableModel.getColumnCount() == 0) {
                                                cartTableModel.addColumn("Product");
                                                cartTableModel.addColumn("Available Items");
                                                cartTableModel.addColumn("Price");
                                            }


                                            boolean found = false;
                                            for (int i = 0; i < cartTableModel.getRowCount(); i++) {
                                                if (cartTableModel.getValueAt(i, 0).equals(productId)) {
                                                    int currentQuantity = (int) cartTableModel.getValueAt(i, 1);
                                                    cartTableModel.setValueAt(currentQuantity + 1, i, 1);
                                                    JOptionPane.showMessageDialog(frame,"Product Added.");
                                                    found = true;
                                                    break;
                                                }
                                            }


                                            if (!found) {
                                                Object[] rowData = {productId, quantity, price};
                                                cartTableModel.addRow(rowData);
                                                JOptionPane.showMessageDialog(frame,"Product Added.");
                                            }
                                            totalCalculation += quantity * price;
                                        }
                                    });


                                    buttonPanel.add(addToCart); // Add button to the center-aligned panel
                                    detailsPanel.add(buttonPanel);
                                    frame.revalidate(); // Update the layout
                                    // Repaint the frame to reflect changes






                                }
                            }
                        }
                    });
                    // table eke yata panel eke iwara wena tena


                    frame.setBackground(Color.BLUE);
                    frame.setVisible(true);





                } else if ("All".equals(selected)) {

                    String[] columns = {"ID", "Name", "Category", "Price", "Info"};
                    int totalRowCount = electronicsList.size() + clothingList.size();
                    Object[][] data = new Object[totalRowCount][columns.length];
                    int row = 0;


                    for (Electronics electronic : electronicsList) {
                        data[row][0] = electronic.getProductID();
                        data[row][1] = electronic.getProductName();
                        data[row][2] = electronic.getProductType();
                        data[row][3] = electronic.getPrice();
                        data[row][4] = electronic.getBrand() + "," + electronic.getWarrantyPeriod();
                        row++;
                    }


                    for (Clothing clothing : clothingList) {
                        data[row][0] = clothing.getProductID();
                        data[row][1] = clothing.getProductName();
                        data[row][2] = clothing.getProductType();
                        data[row][3] = clothing.getPrice();
                        data[row][4] = clothing.getSize() + "," + clothing.getColour();
                        row++;
                    }

                    JTable table = new JTable(data, columns);
                    frame.getContentPane().removeAll();
                    frame.add(topPanel, BorderLayout.NORTH);
                    frame.add(new JScrollPane(table), BorderLayout.CENTER);
                    frame.revalidate();
                    frame.repaint();

                    Component[] components = frame.getContentPane().getComponents();
                    for (Component component : components) {
                        if (component instanceof JPanel) {
                            String constraints = ((JPanel) component).getLayout().toString();
                            if (constraints.contains("SOUTH")) {
                                frame.remove(component);
                                break;
                            }
                        }
                    }


                    JPanel detailsPanel = new JPanel(new GridLayout(10, 2)); // GridLayout with 10 rows, 2 columns
                    frame.add(detailsPanel, BorderLayout.SOUTH);

                    table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                        @Override
                        public void valueChanged(ListSelectionEvent event) {
                            if (!event.getValueIsAdjusting()) {
                                int selectedRow = table.getSelectedRow();
                                if (selectedRow != -1 && selectedRow < totalRowCount) {
                                    // Clear previous labels from detailsPanel
                                    detailsPanel.removeAll();

                                    if (selectedRow < electronicsList.size()) {
                                        Electronics electronic = electronicsList.get(selectedRow);

                                        // Set details using JLabels in the detailsPanel
                                        detailsPanel.add(new JLabel("Select Product-Details"));
                                        detailsPanel.add(new Label("ID: " + electronic.getProductID()));
                                        detailsPanel.add(new Label("Name: " + electronic.getProductName()));
                                        detailsPanel.add(new Label("Category: " + electronic.getProductType()));
                                        detailsPanel.add(new Label("Price: " + electronic.getPrice()));
                                        detailsPanel.add(new Label("Info: " + electronic.getBrand() + ", " + electronic.getWarrantyPeriod()));



                                        //all electronic under buttons and add to cart
                                        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                                        JButton addToCart = new JButton("Add to Cart");
                                        addToCart.addActionListener(new ActionListener() {
                                            @Override
                                            public void actionPerformed(ActionEvent e) {

                                                String productId = "<html>" + electronic.getProductID() + "<br>" + electronic.getBrand() + "<br>" + electronic.getWarrantyPeriod() + "</html>";
                                                int quantity =1;
                                                double price = electronic.getPrice();




                                                if (cartTableModel.getColumnCount() == 0) {
                                                    cartTableModel.addColumn("Product");
                                                    cartTableModel.addColumn("Available Items");
                                                    cartTableModel.addColumn("Price");
                                                }


                                                boolean found = false;
                                                for (int i = 0; i < cartTableModel.getRowCount(); i++) {
                                                    if (cartTableModel.getValueAt(i, 0).equals(productId)) {
                                                        int currentQuantity = (int) cartTableModel.getValueAt(i, 1);
                                                        cartTableModel.setValueAt(currentQuantity + 1, i, 1);
                                                        JOptionPane.showMessageDialog(frame,"Product Added.");
                                                        found = true;
                                                        break;
                                                    }
                                                }


                                                if (!found) {
                                                    Object[] rowData = {productId, quantity, price};
                                                    cartTableModel.addRow(rowData);
                                                    JOptionPane.showMessageDialog(frame,"Product Added.");
                                                }
                                                totalCalculation += quantity * price;
                                            }
                                        });


                                        buttonPanel.add(addToCart); // Add button to the center-aligned panel
                                        detailsPanel.add(buttonPanel);
                                        frame.revalidate();







                                    } else {
                                        // Subtract the size of electronicsList to get the clothingList index
                                        Clothing clothing = clothingList.get(selectedRow - electronicsList.size());

                                        // Set details using JLabels in the detailsPanel
                                        detailsPanel.add(new Label("Select Product-Details"));
                                        detailsPanel.add(new Label("ID: " + clothing.getProductID()));
                                        detailsPanel.add(new Label("Name: " + clothing.getProductName()));
                                        detailsPanel.add(new Label("Category: " + clothing.getProductType()));
                                        detailsPanel.add(new Label("Price: " + clothing.getPrice()));



                                        //all  clothing under add to cart and add to cart


                                        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                                        JButton addToCart = new JButton("Add to Cart");
                                        addToCart.addActionListener(new ActionListener() {
                                            @Override
                                            public void actionPerformed(ActionEvent e) {

                                                String productId = "<html>" + clothing.getProductID() + "<br>" + clothing.getProductName() + "<br>" + clothing.getColour() + "</html>";
                                                int quantity =1;
                                                double price = clothing.getPrice();




                                                if (cartTableModel.getColumnCount() == 0) {
                                                    cartTableModel.addColumn("Product");
                                                    cartTableModel.addColumn("Available Items");
                                                    cartTableModel.addColumn("Price");
                                                }


                                                boolean found = false;
                                                for (int i = 0; i < cartTableModel.getRowCount(); i++) {
                                                    if (cartTableModel.getValueAt(i, 0).equals(productId)) {
                                                        int currentQuantity = (int) cartTableModel.getValueAt(i, 1);
                                                        cartTableModel.setValueAt(currentQuantity + 1, i, 1);
                                                        JOptionPane.showMessageDialog(frame,"Product Added.");
                                                        found = true;
                                                        break;
                                                    }
                                                }


                                                if (!found) {
                                                    Object[] rowData = {productId, quantity, price};
                                                    cartTableModel.addRow(rowData);
                                                    JOptionPane.showMessageDialog(frame,"Product Added.");
                                                }
                                                totalCalculation += quantity * price;
                                            }
                                        });


                                        buttonPanel.add(addToCart); // Add button to the center-aligned panel
                                        detailsPanel.add(buttonPanel);
                                        frame.revalidate(); // Update the layout
                                        // Repaint the frame to reflect changes




                                    }

                                    frame.revalidate(); // Update the layout
                                    frame.repaint();
                                    frame.setBackground(Color.BLUE);
                                    frame.setVisible(true);
                                }
                            }
                        }
                    });




                }
            }
        });









        /*default tiyana eka combine table ekath ekka gatta eka */
        JPanel detailsPanel = new JPanel(new GridLayout(10, 2)); // GridLayout with 10 rows, 2 columns
        frame.add(detailsPanel, BorderLayout.SOUTH);


        combinedTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    int selectedRow = combinedTable.getSelectedRow();
                    if (selectedRow != -1 && selectedRow < totalRowCount) {
                        // Clear previous labels from detailsPanel
                        detailsPanel.removeAll();

                        if (selectedRow < electronicsList.size()) {
                            Electronics electronic = electronicsList.get(selectedRow);

                            // Set details using JLabels in the detailsPanel
                            detailsPanel.add(new JLabel("Select Product-Details"));
                            detailsPanel.add(new Label("ID: " + electronic.getProductID()));
                            detailsPanel.add(new Label("Name: " + electronic.getProductName()));
                            detailsPanel.add(new Label("Category: " + electronic.getProductType()));
                            detailsPanel.add(new Label("Price: " + electronic.getPrice()));
                            detailsPanel.add(new Label("Info: " + electronic.getBrand() + ", " + electronic.getWarrantyPeriod()));





                            //all default electronic


                            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                            JButton addToCart = new JButton("Add to Cart");
                            addToCart.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {

                                    String productId = "<html>" + electronic.getProductID() + "<br>" + electronic.getBrand() + "<br>" + electronic.getWarrantyPeriod() + "</html>";
                                    int quantity =1;
                                    double price = electronic.getPrice();




                                    if (cartTableModel.getColumnCount() == 0) {
                                        cartTableModel.addColumn("Product");
                                        cartTableModel.addColumn("Available Items");
                                        cartTableModel.addColumn("Price");
                                    }


                                    boolean found = false;
                                    for (int i = 0; i < cartTableModel.getRowCount(); i++) {
                                        if (cartTableModel.getValueAt(i, 0).equals(productId)) {
                                            int currentQuantity = (int) cartTableModel.getValueAt(i, 1);
                                            cartTableModel.setValueAt(currentQuantity + 1, i, 1);
                                            JOptionPane.showMessageDialog(frame,"Product Added.");
                                            found = true;
                                            break;
                                        }
                                    }


                                    if (!found) {
                                        Object[] rowData = {productId, quantity, price};
                                        cartTableModel.addRow(rowData);
                                        JOptionPane.showMessageDialog(frame,"Product Added.");
                                    }
                                    totalCalculation += quantity * price;
                                }
                            });


                            buttonPanel.add(addToCart); // Add button to the center-aligned panel
                            detailsPanel.add(buttonPanel);
                            frame.revalidate();







                        } else {
                            // Subtract the size of electronicsList to get the clothingList index
                            Clothing clothing = clothingList.get(selectedRow - electronicsList.size());

                            // Set details using JLabels in the detailsPanel
                            detailsPanel.add(new Label("Select Product-Details"));
                            detailsPanel.add(new Label("ID: " + clothing.getProductID()));
                            detailsPanel.add(new Label("Name: " + clothing.getProductName()));
                            detailsPanel.add(new Label("Category: " + clothing.getProductType()));
                            detailsPanel.add(new Label("Price: " + clothing.getPrice()));







                            //all default under clothing

                            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                            JButton addToCart = new JButton("Add to Cart");
                            addToCart.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {

                                    String productId = "<html>" + clothing.getProductID() + "<br>" + clothing.getProductName() + "<br>" + clothing.getColour() + "</html>";
                                    int quantity =1;
                                    double price = clothing.getPrice();




                                    if (cartTableModel.getColumnCount() == 0) {
                                        cartTableModel.addColumn("Product");
                                        cartTableModel.addColumn("Available Items");
                                        cartTableModel.addColumn("Price");
                                    }


                                    boolean found = false;
                                    for (int i = 0; i < cartTableModel.getRowCount(); i++) {
                                        if (cartTableModel.getValueAt(i, 0).equals(productId)) {
                                            int currentQuantity = (int) cartTableModel.getValueAt(i, 1);
                                            cartTableModel.setValueAt(currentQuantity + 1, i, 1);
                                            JOptionPane.showMessageDialog(frame,"Product Added.");
                                            found = true;
                                            break;
                                        }
                                    }


                                    if (!found) {
                                        Object[] rowData = {productId, quantity, price};
                                        cartTableModel.addRow(rowData);
                                        JOptionPane.showMessageDialog(frame,"Product Added.");
                                    }
                                    totalCalculation += quantity * price;
                                }
                            });


                            buttonPanel.add(addToCart); // Add button to the center-aligned panel
                            detailsPanel.add(buttonPanel);
                            frame.revalidate(); // Update the layout
                            // Repaint the frame to reflect changes





                        }

                        frame.revalidate(); // Update the layout
                        frame.repaint();
                        frame.setBackground(Color.BLUE);
                        frame.setVisible(true);
                    }
                }
            }

        });



        frame.setBackground(Color.BLUE);
        frame.setVisible(true);


    }
}
