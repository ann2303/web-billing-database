DROP TABLE IF EXISTS client CASCADE;
DROP TABLE IF EXISTS "number" CASCADE;
DROP TABLE IF EXISTS "service" CASCADE;
DROP TABLE IF EXISTS service_history CASCADE;
DROP TABLE IF EXISTS transactions CASCADE;

CREATE SEQUENCE client_id_seq AS integer;
ALTER SEQUENCE client_id_seq START WITH 1;

CREATE TABLE client (
    id integer PRIMARY KEY DEFAULT nextval('client_id_seq'),
    fcn text NOT NULL,
    "address" text NOT NULL,
    "e-mail" text NOT NULL,
    "type" varchar(15) NOT NULL
);

ALTER SEQUENCE client_id_seq OWNED BY client.id;

CREATE TABLE "number" (
    number_id numeric(11) PRIMARY KEY,
    client_id integer REFERENCES client ON DELETE CASCADE ON UPDATE CASCADE NOT NULL,
    balance money,
    max_credit money,
    payment_period interval
);

CREATE SEQUENCE service_id_seq AS integer;
ALTER SEQUENCE service_id_seq START WITH 1;

CREATE TABLE "service" (
    id integer PRIMARY KEY DEFAULT nextval('service_id_seq'),
    "name" text NOT NULL,
    subscription_fee_month money, 
    subscription_fee_day money,
    validity interval,
    structure jsonb,
    connection_cost money
);

ALTER SEQUENCE service_id_seq OWNED BY service.id;

CREATE SEQUENCE service_history_id_seq AS integer;
ALTER SEQUENCE service_history_id_seq START WITH 1;

CREATE TABLE service_history (
    id integer PRIMARY KEY DEFAULT nextval('service_history_id_seq'),
    "number" numeric(11) REFERENCES "number" ON DELETE SET NULL,
    service_id integer REFERENCES "service" ON DELETE SET NULL, 
    connection_time timestamp,
    disconnection_time timestamp,
    payment_plan interval
);

ALTER SEQUENCE service_history_id_seq OWNED BY service_history.id;

CREATE SEQUENCE transactions_id_seq AS integer;
ALTER SEQUENCE transactions_id_seq START WITH 1;

CREATE TABLE transactions (
    id integer PRIMARY KEY DEFAULT nextval('transactions_id_seq'),
    "number" numeric(11) REFERENCES "number" ON DELETE SET NULL,
    "sum" money,
    transaction_time timestamp,
    "type" varchar(15) NOT NULL
);

ALTER SEQUENCE transactions_id_seq OWNED BY transactions.id;
