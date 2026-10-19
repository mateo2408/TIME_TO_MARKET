-- Run after OpenXava has started once and created the schema.
-- This transfers the records currently present in data/untitled-db.script.
USE untitleddb;

START TRANSACTION;

INSERT IGNORE INTO Customer
  (number, name, city, state, street, zipCode)
VALUES
  (1, 'JOHN SMITH', 'LOS ANGELES', 'CALIFORNIA', 'FIVE', 13006);

INSERT IGNORE INTO Category (oid, description) VALUES
  ('283c62b8-68eb-4e00-8b98-ac5e99aa3f64', 'Books'),
  ('6336846b-e27b-4eac-b237-4bf128921183', 'Music'),
  ('8f3e19ed-5fa3-44d0-82ab-ddfce25050d0', 'Software');

INSERT IGNORE INTO Product
  (number, description, photos, price, remarks, category_oid, author_oid)
VALUES
  (1, 'Learn OpenXava by example', '2DD074E4C0A8565E00000000A463B73A',
   19.00, '', '283c62b8-68eb-4e00-8b98-ac5e99aa3f64', NULL);

INSERT IGNORE INTO Invoice
  (oid, date, number, remarks, year, customer_number)
VALUES
  ('44e1233b-c7b3-434e-9ca9-00bc83c1d1ef', '2026-10-11', 1, '', 2026, 1);

INSERT IGNORE INTO Invoice_details (Invoice_oid, product_number, quantity)
VALUES
  ('44e1233b-c7b3-434e-9ca9-00bc83c1d1ef', 1, 2);

COMMIT;
