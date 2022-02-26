DROP TABLE IF EXISTS client CASCADE;
DROP TABLE IF EXISTS 'number'CASCADE;
DROP TABLE IF EXISTS 'service' CASCADE;
DROP TABLE IF EXISTS service_history CASCADE;
DROP TABLE IF EXISTS transactions CASCADE;

CREATE SEQUENCE client_id_seq AS integer;
ALTER SEQUENCE client_id_seq START WITH 1;

CREATE TABLE client (
    id integer PRIMARY KEY DEFAULT nextval('client_id_seq'),
    fcn varchar(50) NOT NULL,
    'address' varchar(50) NOT NULL,
    'e-mail' varchar(50) NOT NULL,
    'type' varchar(15) NOT NULL
);

ALTER SEQUENCE client_id_seq OWNED BY client.id;

CREATE TABLE 'number' (
    number_id integer PRIMARY KEY,
    client_id integer REFERENCES client ON DELETE CASCADE ON UPDATE CASCADE NOT NULL,
    balance money,
    max_credit money,
    payment_period tsrange
);

CREATE SEQUENCE service_id_seq AS integer;
ALTER SEQUENCE service_id_seq START WITH 1;

CREATE TABLE 'service' (
    id integer PRIMARY KEY DEFAULT nextval('service_id_seq'),
    'name' varchar(50) NOT NULL,
    subscription_fee_month money, 
    subscription_fee_day money,
    validity tsrange,
    structure jsonb,
    connection_cost money
);

ALTER SEQUENCE service_id_seq OWNED BY service.id;

CREATE SEQUENCE service_history_id_seq AS integer;
ALTER SEQUENCE service_history_id_seq START WITH 1;

CREATE TABLE service_history (
    id integer PRIMARY KEY DEFAULT nextval('service_history_id_seq'),
    'number' integer REFERENCES 'number' ON DELETE SET NULL,
    service_id integer REFERENCES 'service' ON DELETE SET NULL, 
    connection_time timestamp,
    disconnection_time timestamp,
    payment_plan tsrange
);

ALTER SEQUENCE service_history_id_seq OWNED BY service_history.id;

CREATE SEQUENCE transactions_id_seq AS integer;
ALTER SEQUENCE transactions_id_seq START WITH 1;

CREATE TABLE transactions (
    id integer PRIMARY KEY DEFAULT nextval('transactions_id_seq'),
    'number' integer REFERENCES 'number' ON DELETE SET NULL,
    'sum' money,
    transaction_time timestamp,
    'type' varchar(15) NOT NULL
);

ALTER SEQUENCE transactions_id_seq OWNED BY transactions.id;
