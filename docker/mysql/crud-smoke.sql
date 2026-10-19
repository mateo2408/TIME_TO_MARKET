-- dialect: MySQL
-- Run after OpenXava has started once and Hibernate has created the schema:
-- docker compose exec mysql-client mysql -h mysql -uuntitled_app -puntitled_app_password untitleddb < docker/mysql/crud-smoke.sql
START TRANSACTION;

INSERT INTO Category (oid, description)
VALUES ('crud-smoke-category', 'CRUD smoke category');

SELECT 'after insert' AS step, oid, description
FROM Category
WHERE oid = 'crud-smoke-category';

UPDATE Category
SET description = 'CRUD smoke category updated'
WHERE oid = 'crud-smoke-category';

SELECT 'after update' AS step, oid, description
FROM Category
WHERE oid = 'crud-smoke-category';

DELETE FROM Category
WHERE oid = 'crud-smoke-category';

SELECT 'after delete' AS step, COUNT(*) AS remaining_rows
FROM Category
WHERE oid = 'crud-smoke-category';

ROLLBACK;
