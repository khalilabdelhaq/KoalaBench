--external Line Order Table
CREATE TABLE ssb.ext_lineItem
(
    partKey NUMBER,
    supplierKey NUMBER,
    lineNumber NUMBER,
    quantity INTEGER,
    extendedPrice NUMBER(8, 2),
    discount NUMBER(3, 2),
    tax NUMBER(3, 2),
    returnFlag CHAR(1),
    l_status CHAR(1),
    shipDate CHAR(10),
    shipInstructions VARCHAR(40),
    shipMode VARCHAR(10),
    l_comment VARCHAR(50)
)
ORGANIZATION EXTERNAL
(
    TYPE oracle_loader DEFAULT DIRECTORY ssb_dir ACCESS PARAMETERS
(
        RECORDS DELIMITED BY '\n' FIELDS TERMINATED BY '|' MISSING FIELD
        VALUES
            ARE NULL
    ) LOCATION
('star_line_item.tab')
) PARALLEL 4;

--Target Lineorder Table, the external Table will be loaded into the below Table
DROP TABLE ssb.lineorder;
CREATE TABLE ssb.lineItem
(
    partKey NUMBER NOT NULL,
    supplierKey NUMBER NOT NULL,
    lineNumber NUMBER NOT NULL,
    quantity INTEGER NOT NULL,
    extendedPrice NUMBER(8, 2) NOT NULL,
    discount NUMBER(3, 2) NOT NULL,
    tax NUMBER(3, 2) NOT NULL,
    returnFlag CHAR(1) NOT NULL,
    l_status CHAR(1) NOT NULL,
    shipDate CHAR(10) NOT NULL,
    shipInstructions VARCHAR(40) NOT NULL,
    shipMode VARCHAR(10) NOT NULL,
    l_comment VARCHAR(50) NOT NULL
);

DROP TABLE ssb.ext_part;
CREATE TABLE ssb.ext_part
(
    partKey NUMBER,
    p_name VARCHAR2(60),
    manufacturer CHAR(15),
    brand CHAR(10),
    p_type CHAR(30),
    p_size NUMBER(2, 0),
    p_container VARCHAR2(10),
    retailPrice NUMBER(6, 2),
    p_comment VARCHAR(30)
)
ORGANIZATION EXTERNAL
(
    TYPE oracle_loader DEFAULT DIRECTORY ssb_dir ACCESS PARAMETERS
(
        RECORDS DELIMITED BY '\n' FIELDS TERMINATED BY '|' MISSING FIELD
        VALUES
            ARE NULL
    ) LOCATION
('star_part.tab')
);
DROP TABLE ssb.part;
CREATE TABLE ssb.part
(
    partKey NUMBER NOT NULL,
    p_name VARCHAR2(60) NOT NULL,
    manufacturer CHAR(15) NOT NULL,
    brand CHAR(10) NOT NULL,
    p_type CHAR(30) NOT NULL,
    p_size NUMBER(2, 0) NOT NULL,
    p_container VARCHAR2(10) NOT NULL,
    retailPrice NUMBER(6, 2) NOT NULL,
    p_comment VARCHAR(30) NOT NULL
);

DROP TABLE ssb.ext_supplier;
CREATE TABLE ssb.ext_supplier
(
    supplierKey NUMBER,
    s_name CHAR(25),
    address VARCHAR2(40),
    nationName CHAR(15),
    regionName CHAR(15),
    phone CHAR(15),
    accountBalance CHAR(15),
    s_comment VARCHAR(100)
)
ORGANIZATION EXTERNAL
(
    TYPE oracle_loader DEFAULT DIRECTORY ssb_dir ACCESS PARAMETERS
(
        RECORDS DELIMITED BY '\n' FIELDS TERMINATED BY '|' MISSING FIELD
        VALUES
            ARE NULL
    ) LOCATION
('star_supplier.tab')
);
DROP TABLE ssb.supplier;
CREATE TABLE ssb.supplier
(
    supplierKey NUMBER NOT NULL,
    s_name CHAR(25) NOT NULL,
    address VARCHAR2(40) NOT NULL,
    nationName CHAR(15) NOT NULL,
    regionName CHAR(15) NOT NULL,
    phone CHAR(15) NOT NULL,
    accountBalance CHAR(15) NOT NULL,
    s_comment VARCHAR(100) NOT NULL
);


DROP TABLE ssb.ext_customer;
CREATE TABLE ssb.ext_customer
(
    customerKey NUMBER,
    name VARCHAR2(25),
    address VARCHAR2(40),
    nation_name CHAR(15),
    regionName CHAR(15),
    phone CHAR(15),
    accountBalance CHAR(15),
    marketSegment CHAR(10)
)
ORGANIZATION EXTERNAL
(
    TYPE oracle_loader DEFAULT DIRECTORY ssb_dir ACCESS PARAMETERS
(
        RECORDS DELIMITED BY '\n' FIELDS TERMINATED BY '|' MISSING FIELD
        VALUES
            ARE NULL
    ) LOCATION
('star_customer.tab')
);
DROP TABLE ssb.customer;
CREATE TABLE ssb.customer
(
    customerKey NUMBER NOT NULL,
    name VARCHAR2(25) NOT NULL,
    address VARCHAR2(40) NOT NULL,
    nation_name CHAR(15) NOT NULL,
    regionName CHAR(15) NOT NULL,
    phone CHAR(15) NOT NULL,
    accountBalance CHAR(15) NOT NULL,
    marketSegment CHAR(10) NOT NULL
);


DROP TABLE ssb.ext_date_dim;
CREATE TABLE ssb.ext_date_dim
(
    dateKey CHAR(18),
    d_month CHAR(2),
    d_year CHAR(4),
    yearMonthNum CHAR(7),
    yearMonth CHAR(7),
    dayNumInWeek CHAR(1),
    dayNumInMonth CHAR(2),
    dayNumInYear CHAR(3),
    monthNumInYear CHAR(2),
    weekNumInYear CHAR(2)
)
ORGANIZATION EXTERNAL
(
    TYPE oracle_loader DEFAULT DIRECTORY ssb_dir ACCESS PARAMETERS
(
        RECORDS DELIMITED BY '\n' FIELDS TERMINATED BY '|' MISSING FIELD
        VALUES
            ARE NULL
    ) LOCATION
('snow_date.tab')
);
DROP TABLE ssb.date_dim;
CREATE TABLE ssb.date_dim
(
    dateKey CHAR(18) NOT NULL,
    d_month CHAR(2) NOT NULL,
    d_year CHAR(4) NOT NULL,
    yearMonthNum CHAR(7) NOT NULL,
    yearMonth CHAR(7) NOT NULL,
    dayNumInWeek CHAR(1) NOT NULL,
    dayNumInMonth CHAR(2) NOT NULL,
    dayNumInYear CHAR(3) NOT NULL,
    monthNumInYear CHAR(2) NOT NULL,
    weekNumInYear CHAR(2) NOT NULL
);

-- Load from external tables 

INSERT /*+ APPEND */ INTO  ssb.part
SELECT *
FROM ssb.ext_part;
commit;
INSERT /*+ APPEND */ INTO  ssb.supplier
SELECT *
FROM ssb.ext_supplier;
commit;
INSERT /*+ APPEND */ INTO  ssb.customer
SELECT *
FROM ssb.ext_customer;
commit;
INSERT /*+ APPEND */ INTO  ssb.date_dim
SELECT *
FROM ssb.ext_date_dim;
commit;
INSERT /*+ APPEND */ INTO  ssb.lineItem
SELECT *
FROM ssb.ext_lineItem;
commit;