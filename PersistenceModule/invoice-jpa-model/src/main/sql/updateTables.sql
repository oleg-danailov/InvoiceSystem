


ALTER TABLE invoice ADD COLUMN "total_amount" numeric(19,2);
ALTER TABLE invoice ADD COLUMN "invoice_status" character varying(255);

ALTER TABLE invoice ADD CONSTRAINT provider_company_invoice_number_unique UNIQUE (invoice_number, provider_company);

ALTER TABLE invoice
   ADD COLUMN currency character varying(255);