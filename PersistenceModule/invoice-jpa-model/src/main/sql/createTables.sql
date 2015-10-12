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
  tax_percent money,
  CONSTRAINT tax_pk PRIMARY KEY (tax_id)
);