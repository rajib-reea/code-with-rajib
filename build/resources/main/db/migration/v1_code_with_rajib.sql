-- Table for storing customer information
CREATE TABLE Customers (
                           customer_id SERIAL PRIMARY KEY,
                           first_name VARCHAR(50) NOT NULL,
                           last_name VARCHAR(50) NOT NULL,
                           email VARCHAR(100) NOT NULL UNIQUE,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table for storing product information
CREATE TABLE Products (
                          product_id SERIAL PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          description TEXT,
                          price NUMERIC(10, 2) NOT NULL,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table for storing wishlist information
CREATE TABLE Wishlists (
                           wishlist_id SERIAL PRIMARY KEY,
                           customer_id INT NOT NULL,
                           name VARCHAR(100) NOT NULL,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           FOREIGN KEY (customer_id) REFERENCES Customers(customer_id) ON DELETE CASCADE
);

-- Table for storing products added to wishlists
CREATE TABLE Wishlist_Items (
                                wishlist_item_id SERIAL PRIMARY KEY,
                                wishlist_id INT NOT NULL,
                                product_id INT NOT NULL,
                                added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                FOREIGN KEY (wishlist_id) REFERENCES Wishlists(wishlist_id) ON DELETE CASCADE,
                                FOREIGN KEY (product_id) REFERENCES Products(product_id) ON DELETE CASCADE,
                                UNIQUE (wishlist_id, product_id)
);

-- Table for storing orders
CREATE TABLE Orders (
                        order_id SERIAL PRIMARY KEY,
                        customer_id INT NOT NULL,
                        order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        total_amount NUMERIC(10, 2) NOT NULL,
                        FOREIGN KEY (customer_id) REFERENCES Customers(customer_id) ON DELETE CASCADE
);

-- Table for storing order items
CREATE TABLE Order_Items (
                             order_item_id SERIAL PRIMARY KEY,
                             order_id INT NOT NULL,
                             product_id INT NOT NULL,
                             quantity INT NOT NULL,
                             price NUMERIC(10, 2) NOT NULL,
                             FOREIGN KEY (order_id) REFERENCES Orders(order_id) ON DELETE CASCADE,
                             FOREIGN KEY (product_id) REFERENCES Products(product_id) ON DELETE CASCADE
);
