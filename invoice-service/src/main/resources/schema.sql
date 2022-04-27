
CREATE TABLE IF NOT EXISTS clients(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    lastname VARCHAR(100) NOT NULL,
    doc_number VARCHAR(15) NOT NULL UNIQUE,
    date_of_birth DATE,
    created_at DATETIME,
    status VARCHAR(10)
);

CREATE TABLE IF NOT EXISTS products(
    id INT PRIMARY KEY AUTO_INCREMENT,
    code VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(200) NOT NULL,
    price_buy DOUBLE,
    price_sell DOUBLE,
    stock INT,
    status VARCHAR(10) NOT NULL,
    created_at DATETIME,
    last_updated DATETIME
);

CREATE TABLE invoices(
    id INT PRIMARY KEY AUTO_INCREMENT,
    created_at DATETIME,
    client_id INT NOT NULL,
    total DOUBLE NOT NULL,
    CONSTRAINT fk_client_id FOREIGN KEY (client_id)
        REFERENCES clients (id)
);

CREATE TABLE invoice_details(
    id INT PRIMARY KEY AUTO_INCREMENT,
    invoice_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    unit_price DOUBLE,
    sub_total DOUBLE,
    CONSTRAINT fk_invoice_id FOREIGN KEY (invoice_id)
        REFERENCES invoices (id),
    CONSTRAINT fk_product_id FOREIGN KEY (product_id)
        REFERENCES products (id)
);




