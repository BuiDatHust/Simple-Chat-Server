ALTER TABLE login_device DROP COLUMN status ;
ALTER TABLE login_device ADD status VARCHAR(255) NOT NULL;