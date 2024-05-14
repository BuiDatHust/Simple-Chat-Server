CREATE TYPE LoginDeviceStatusEnum AS ENUM ('ACTIVE','INACTIVE', 'BANNED');
CREATE TABLE IF NOT EXISTS login_device (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    location VARCHAR NOT NULL,
    status LoginDeviceStatusEnum NOT NULL,
    created_date BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS refresh_token (
    id BIGSERIAL PRIMARY KEY,
    login_device_id BIGINT NOT NULL,
    value VARCHAR NOT NULL,
    is_active BOOLEAN NOT NULL,
    expire_time BIGINT NOT NULL,
    created_date BIGINT NOT NULL,
    CONSTRAINT fk_login_device FOREIGN KEY(login_device_id) REFERENCES login_device(id) ON DELETE CASCADE
);