


ALTER TABLE invoice ADD COLUMN "total_amount" numeric(19,2);
ALTER TABLE invoice ADD COLUMN "invoice_status" character varying(255);

ALTER TABLE invoice ADD CONSTRAINT provider_company_invoice_number_unique UNIQUE (invoice_number, provider_company);

ALTER TABLE invoice
   ADD COLUMN currency character varying(255);

ALTER TABLE invoice
DROP provider_company,
DROP receiver_company;

ALTER TABLE invoice
ADD COLUMN provider_company integer REFERENCES company (company_id),
ADD COLUMN receiver_company integer REFERENCES company (company_id);

ALTER TABLE company ADD CONSTRAINT unq_company_name UNIQUE (company_name);