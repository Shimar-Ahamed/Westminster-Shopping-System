
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;



class WestminsterShoppingManager implements ShoppingManager {
    GUI gui = new GUI();

    private ArrayList<Product> products = new ArrayList<>();
    private ArrayList<Electronics> electronics = new ArrayList<>();
    private ArrayList<Clothing> clothing = new ArrayList<>();

    //Scanner for user input
    private Scanner scanner = new Scanner(System.in);


    private int availableItems;
    private double price;
    private int warrantyPeriod;
    private String productID;
    private String productName;
    private String brand;
    private String size;
    private String colour;



    public WestminsterShoppingManager() {
        this.products = new ArrayList<>();
        this.electronics= new ArrayList<>();
        this.clothing= new ArrayList<>();

    }


    public void displayMenu() {

        int enterOption = 0;
        try {
            System.out.println("1. Add Product to Cart");
            System.out.println("2. Remove Product from Cart");
            System.out.println("3. Display the all products");
            System.out.println("4. Save Files");
            System.out.println("5. Read File");
            System.out.println("6. GUI");
            System.out.println("7. EXIT");


            System.out.print("Enter Option: ");
            enterOption = scanner.nextInt();

            switch (enterOption) {
                case 1:
                    addProduct();
                    break;

                case 2:
                    deleteProduct();
                    break;
                case 3:
                    printProductList();
                    break;
                case 4:
                    saveProductsToFile();
                    break;
                case 5:
                    readProductsFromFile();
                    break;
                case 6:
                    gui.GUI(electronics, clothing, products);
                    break;
                case 7:
                    System.exit(0);
                    break;


            }

        } catch (InputMismatchException e) {
            System.out.println("This is not an option!!, Enter Valid Option!!");
            scanner.next();
        }


    }


    @Override
    public void addProduct() {
        Scanner getAnOption = new Scanner(System.in);

        if (products.size() >= 50) {
            System.out.println("Maximum number of products (50) reached. Cannot add more products.");
            return;
        }

        while (true) {
            System.out.println("1.Electronics or 2.Clothing: ");
            try {
                int type = getAnOption.nextInt();
                getAnOption.nextLine();

                if (type == 1) {
                    //Adding Electronics
                    while (true) {
                        System.out.print("Product ID: ");
                        productID = getAnOption.next();

                        //check for existing Product ID

                        boolean productExists = false;
                        for (Product existingProduct : products) {
                            if (existingProduct.getProductID().equals(productID)) {
                                productExists = true;
                                break;
                            }
                        }
                        if (productExists) {
                            System.out.println("Product ID already exists. Please enter a different ID.");
                        } else {
                            break;
                        }
                    }


                    System.out.print("Product Name: ");
                    productName = getAnOption.next();


                    while (true) {
                        try {
                            System.out.print("Available Items: ");
                            availableItems = getAnOption.nextInt();
                            getAnOption.nextLine();
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid Input . Please Enter a Number");
                            getAnOption.next();

                        }
                    }


                    while (true) {
                        try {
                            System.out.print("Price: ");
                            price = getAnOption.nextDouble();
                            getAnOption.nextLine();
                            break;

                        } catch (InputMismatchException e) {
                            System.out.println("Invalid Input . Please Enter a Valid Price!");
                            getAnOption.next();
                        }
                    }


                    System.out.print("Brand: ");
                    brand = getAnOption.next();


                    while (true) {
                        try {
                            System.out.print("Warranty Period: ");
                            warrantyPeriod = getAnOption.nextInt();
                            break;

                        } catch (InputMismatchException e) {
                            System.out.println("Invalid Input . Please Enter Valid Warranty Period!");
                            getAnOption.next();
                        }
                    }


                    Electronics electronics1 = new Electronics(productID, productName, availableItems, price, brand, warrantyPeriod);
                    electronics.add(electronics1);
                    products.add(electronics1);
                    for (Product product : products) {
                        System.out.println("---------------------------------------");
                        System.out.println(product);
                        System.out.println("---------------------------------------");
                    }


                } else if (type == 2) {
                    //Adding Cloths
                    while (true) {
                        System.out.print("Product ID: ");
                        productID = getAnOption.next();

                        boolean productExists = false;
                        for (Product existingProduct : products) {
                            if (existingProduct.getProductID().equals(productID)) {
                                productExists = true;
                                break;
                            }
                        }
                        if (productExists) {
                            System.out.println("Product ID already exists. Please enter a different ID");
                        } else {
                            break;
                        }
                    }


                    System.out.print("Product Name: ");
                    productName = getAnOption.next();

                    while (true) {
                        try {
                            System.out.print("Available Items: ");
                            availableItems = getAnOption.nextInt();
                            getAnOption.nextLine();
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid Input . Please Enter a Number");
                            getAnOption.next();

                        }
                    }


                    while (true) {
                        try {
                            System.out.print("Price: ");
                            price = getAnOption.nextDouble();
                            getAnOption.nextLine();
                            break;

                        } catch (InputMismatchException e) {
                            System.out.println("Invalid Input . Please Enter Valid Price!");
                            getAnOption.next();
                        }
                    }

                    System.out.print("Size: ");
                    size = getAnOption.next();

                    System.out.print("Colour: ");
                    colour = getAnOption.next();

                    //----------------------------------------------//
                    Clothing clothing1 = new Clothing(productID, productName, availableItems, price, size, colour);
                    clothing.add(clothing1);
                    products.add(clothing1);
                    for (Product product : products) {
                        System.out.println("---------------------------------------");
                        System.out.println(product);
                        System.out.println("---------------------------------------");
                    }


                } else {
                    System.out.println("Invalid Option. Please Enter 1 for Electronics or 2 for Clothing");
                    continue;
                }
                break;

            } catch (InputMismatchException e) {
                System.out.println("Invalid Input . Please Enter a Number 1 or 2..");
                getAnOption.next();
            }

        }


    }


    @Override
    public void deleteProduct() {
        Scanner scanner1 = new Scanner(System.in);
        System.out.print("Enter product ID to Delete:");
        String delete = scanner1.nextLine();

        boolean productFound = false;


        // referenced from W3 Schools

        Iterator<Product> iterator = products.iterator();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            if (product.getProductID().equals(delete)) {
                iterator.remove();
                productFound = true;
                System.out.println("Successfully deleted");
                break;
            }
        }

        if (!productFound) {
            System.out.println("product does not exist.");
        }

    }

    @Override
    public void printProductList() {
        Collections.sort(products, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return Character.compare(o1.getProductID().charAt(0), o2.getProductID().charAt(0));

            }
        });
        for (Product product : products) {
            System.out.println("---------------------------------------");
            System.out.println(product);
            System.out.println("---------------------------------------");
        }


    }
    @Override
    public void saveProductsToFile() {
        try (PrintWriter writer = new PrintWriter("products.txt")) {
            for (Product product : products) {
                writer.println(product.getProductType());
                writer.println(product.getProductID());
                writer.println(product.getProductName());
                writer.println(product.getAvailableItems());
                writer.println(product.getPrice());

                if (product instanceof Electronics) {
                    Electronics electronicProduct = (Electronics) product;
                    writer.println(electronicProduct.getBrand());
                    writer.println(electronicProduct.getWarrantyPeriod());
                } else if (product instanceof Clothing) {
                    Clothing clothing = (Clothing) product;
                    writer.println(clothing.getSize());
                    writer.println(clothing.getColour());
                }

                // Add delimiter after each product
                writer.println("---");
            }
            System.out.println("Products saved to file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void readProductsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("products.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals("electronic")) {
                    readElectronicProduct(reader);
                } else if (line.equals("clothing")) {
                    readClothingProduct(reader);
                }
            }
            System.out.println("Products loaded from file.");
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
    }

    private void readElectronicProduct(BufferedReader reader) throws IOException {
        productID = reader.readLine();
        productName = reader.readLine();
        availableItems = Integer.parseInt(reader.readLine());
        price = Double.parseDouble(reader.readLine());
        brand = reader.readLine();
        warrantyPeriod = Integer.parseInt(reader.readLine());

        Electronics electronicProduct = new Electronics(productID, productName, availableItems, price, brand, warrantyPeriod);
        electronics.add(electronicProduct);
        products.add(electronicProduct);
    }

    private void readClothingProduct(BufferedReader reader) throws IOException {
        productID = reader.readLine();
        productName = reader.readLine();
        availableItems = Integer.parseInt(reader.readLine());
        price = Double.parseDouble(reader.readLine());
        size = reader.readLine();
        colour = reader.readLine();

        Clothing clothingProduct = new Clothing(productID, productName, availableItems, price, size, colour);
        clothing.add(clothingProduct);
        products.add(clothingProduct);
    }







}
