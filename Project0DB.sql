--NEED TO FIGURE OUT HOW TO ADD FOREIGN KEYS TO TABLES--------------------------------------------------------



--Create a user so I may access the Project0DB
CREATE USER mgperkins1 IDENTIFIED BY p4ssw0rd;
GRANT CONNECT, RESOURCE TO mgperkins1;
GRANT DBA TO mgperkins1 WITH ADMIN OPTION;

--DROP USER mgperkins1;

--Customer table
CREATE TABLE customer(
    customer_id NUMBER NOT NULL,
    username VARCHAR2(30) UNIQUE NOT NULL,
    password VARCHAR2(30) NOT NULL,
    PRIMARY KEY (customer_id)
);
--DROP TABLE customer;

--ALTER TABLE customer
--ADD CONSTRAINT fk_customer_bank_account 
--    FOREIGN KEY (acct_num)
--    REFERENCES bank_account(acct_num)
--    ON DELETE CASCADE;
    
    


--Employee table
CREATE TABLE employee(
    employee_id NUMBER NOT NULL,
    username VARCHAR2(30) UNIQUE,
    password VARCHAR2(30) NOT NULL,
    PRIMARY KEY(employee_id)
);
--DROP TABLE employee;

--Administrator table
CREATE TABLE administrator(
    admin_id NUMBER NOT NULL,
    username VARCHAR2(30) UNIQUE,
    password VARCHAR2(30) NOT NULL,
    PRIMARY KEY(admin_id)
);
--DROP TABLE administrator;

--BankAccount table 
CREATE TABLE bank_account(
    acct_num NUMBER(38),
    acctID VARCHAR2(255),
    acctBalance NUMBER(38,2),
    approved VARCHAR(255) NOT NULL,
    PRIMARY KEY(acct_num)
);
--DROP TABLE bank_account;
--DESC bank_account;



--ALTER TABLE bank_account
--ADD FOREIGN KEY (customer_id)
--    REFERENCES customer(customer_id)
--    ON DELETE CASCADE;
    



--Sequence used by tables to auto increment the ID field(PK)
CREATE SEQUENCE customer_id_auto_increment
    START WITH 1
    INCREMENT BY 1;
--DROP SEQUENCE customer_id_auto_increment;


--procedure to insert new customer into table with auto incremented id field
CREATE OR REPLACE PROCEDURE insert_customer_null_id(c_username IN VARCHAR2,
                c_password IN VARCHAR2)
IS
BEGIN
    INSERT INTO customer VALUES(customer_id_auto_increment.NEXTVAL, c_username, c_password);
    COMMIT;
END;
/
--DROP PROCEDURE insert_customer_null_id;

--Trigger to input auto incremented value into customerID when customer is added to table
CREATE OR REPLACE TRIGGER before_customer_insert
BEFORE INSERT ON customer
FOR EACH ROW
BEGIN
    IF :new.customer_id IS NULL THEN
        SELECT customer_id_auto_increment.NEXTVAL INTO :new.customer_id FROM dual;
    END IF;
END;
/
--DROP TRIGGER before_customer_insert;



--Sequence used by tables to auto increment the ID field(PK)
CREATE SEQUENCE employee_id_auto_increment
    START WITH 1
    INCREMENT BY 1;
--DROP SEQUENCE employee_id_auto_increment;
    

--procedure to insert new employee into table with auto incremented id field
CREATE OR REPLACE PROCEDURE insert_employee_null_id(e_username IN VARCHAR2,
                e_password IN VARCHAR2)
IS
BEGIN
    INSERT INTO employee VALUES(employee_id_auto_increment.NEXTVAL, e_username, e_password);
    COMMIT;
END;
/
--DROP PROCEDURE insert_employee_null_id;

--Trigger to input auto incremented value into userID when employee is added to table
CREATE OR REPLACE TRIGGER before_employee_insert
BEFORE INSERT ON employee
FOR EACH ROW
BEGIN
    IF :new.employee_id IS NULL THEN
        SELECT employee_id_auto_increment.NEXTVAL INTO :new.employee_id FROM dual;
    END IF;
END;
/
--DROP TRIGGER before_employee_insert;



--Sequence used by tables to auto increment the ID field(PK)
CREATE SEQUENCE admin_id_auto_increment
    START WITH 1
    INCREMENT BY 1;
--DROP SEQUENCE admin_id_auto_increment;
    

--procedure to insert new administrator into table with auto incremented id field
CREATE OR REPLACE PROCEDURE insert_admin_null_id(a_username IN VARCHAR2,
                a_password IN VARCHAR2)
IS
BEGIN
    INSERT INTO Administrator VALUES(admin_id_auto_increment.NEXTVAL, a_username, a_password);
    COMMIT;
END;
/
--DROP PROCEDURE insert_admin_null_id;

--Trigger to input auto incremented value into userID when administrator is added to table
CREATE OR REPLACE TRIGGER before_administrator_insert
BEFORE INSERT ON administrator
FOR EACH ROW
BEGIN
    IF :new.admin_id IS NULL THEN
        SELECT admin_id_auto_increment.NEXTVAL INTO :new.admin_id FROM dual;
    END IF;
END;
/
--DROP TRIGGER before_administrator_insert;



--Sequence used by tables to auto increment the ID field(PK)
CREATE SEQUENCE account_id_auto_increment
    START WITH 1
    INCREMENT BY 1;
--DROP SEQUENCE account_id_auto_increment;
    

--procedure to insert new account into table with auto incremented id field
CREATE OR REPLACE PROCEDURE insert_bank_account_null_id(ac_acctID IN VARCHAR2,
                ac_acctBalance IN NUMBER, ac_approved IN VARCHAR2)
IS
BEGIN
    INSERT INTO bank_account VALUES(account_id_auto_increment.NEXTVAL, ac_acctID, ac_acctBalance,
                ac_approved);
    COMMIT;
END;
/
--DROP PROCEDURE insert_bank_account_null_id;

--Trigger to input auto incremented value into acctNum when account is added to table
CREATE OR REPLACE TRIGGER before_bank_account_insert
BEFORE INSERT ON bank_account
FOR EACH ROW
BEGIN
    IF :new.acct_num IS NULL THEN
        SELECT account_id_auto_increment.NEXTVAL INTO :new.acct_num FROM dual;
    END IF;
END;
/
--DROP TRIGGER before_bank_account_insert;






--exec
BEGIN
    insert_customer_null_id('aaaa', 'bbbb');
    insert_customer_null_id('bbbb', 'cccc');
    insert_employee_null_id('cccc', 'dddd');
    insert_employee_null_id('dddd', 'eeee');
    insert_admin_null_id('ffff', 'gggg');
END;
/

--TRUNCATE TABLE customer;
--TRUNCATE TABLE bank_account;
SELECT * FROM customer;
SELECT * FROM bank_account;
SELECT * FROM employee;
SELECT * FROM administrator;

--UPDATE BANK_ACCOUNT SET APPROVED='false' WHERE ACCT_NUM=2;

INSERT INTO bank_account VALUES(4, 22, 0.0, 'FALSE');
