CREATE TABLE IF NOT EXISTS customer_api.customer
(
    id int auto_increment primary key,
    name CHAR(200),
    cpf        CHAR(20),
    email      CHAR(200),
    birth_date DATE,
    gender     VARCHAR(1),
    created_at DATETIME,
    updated_at DATETIME
);

CREATE TABLE IF NOT EXISTS customer_api.address
(
    address_id int auto_increment primary key,
    customer_id int,
    state varchar(100),
    city varchar (100),
    neighborhood varchar(100),
    zipCode varchar(100),
    street varchar(100),
    number varchar(100),
    additional_information varchar(100),
    main boolean,
    foreign key (customer_id) references customer_api.customer(id)
);

CREATE TABLE IF NOT EXISTS customer_api.api_error
(
    code VARCHAR(100) primary key,
    description VARCHAR(100)
);
