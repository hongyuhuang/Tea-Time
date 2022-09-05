CREATE TABLE IF NOT EXISTS CUSTOMER (
    customerID integer AUTO_INCREMENT (1000) unique,
    username varchar(50) not null unique,
    firstName varchar(50) not null,
    surname varchar(50) not null,
    emailAddress varchar(100) not null,
    shippingAddress varchar(100) not null,
    password varchar(50) not null,
    constraint Customer_PK primary key (customerID)    
);

CREATE TABLE IF NOT EXISTS PRODUCT (
    productID varchar unique,
    name varchar(50) not null,
    description varchar(100) not null,
    category varchar(50) not null,
    listPrice numeric(6,2) not null,
    quantityInStock integer not null,
    constraint Product_PK primary key (productID)
);
