CREATE TABLE invoice
(
  invoice_id serial NOT NULL,
  invoice_amount numeric(19,2),
  invoice_creation_date date DEFAULT ('now'::text)::date,
  invoice_number character varying(255),
  invoice_type character varying(255),
  provider_company character varying(255),
  receiver_company character varying(255),
  taxes_amount numeric(19,2),
  CONSTRAINT invoice_pkey PRIMARY KEY (invoice_id)
);


CREATE TABLE tax
(
  tax_id serial NOT NULL,
  tax_name character varying(100),
  invoice_type character varying(10),
  tax_percent numeric,
  CONSTRAINT tax_pk PRIMARY KEY (tax_id)
)

CREATE TABLE client
(
  client_id serial NOT NULL,
  name character varying(100),
  number character varying(100),
  CONSTRAINT client_pk PRIMARY KEY (client_id)
)