CREATE TABLE IF NOT EXISTS user_chat (
    id BIGSERIAL PRIMARY KEY,
    phone_number VARCHAR(255) NOT NULL,
    country_code VARCHAR(255) NOT NULL,
    fullname VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    avatar VARCHAR,
    bio TEXT,
    dob INT,
    address TEXT,
    last_active_at INT,
    is_active BOOLEAN,
    created_date INT NOT NULL
);  

CREATE TYPE UIMODE AS ENUM ('DARK', 'WHITE');
CREATE TABLE IF NOT EXISTS user_setting (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    ui_mode UIMODE DEFAULT 'WHITE',
    created_date INT NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES user_chat(id) ON DELETE CASCADE
);

CREATE TYPE typeOtp AS ENUM ('REGISTER', 'LOGIN', 'LOGIN_NEW_DEVICE');
CREATE TABLE IF NOT EXISTS otp (
    id BIGSERIAL PRIMARY KEY,
    type_otp typeOtp NOT NULL,
    is_active BOOLEAN DEFAULT true,
    value VARCHAR(10) NOT NULL,
    expire_at INT NOT NULL,
    created_date INT NOT NULL
);

CREATE TYPE TYPECHAT AS ENUM ('PAIR', 'GROUP', 'SYSTEM');
CREATE TABLE IF NOT EXISTS chat (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(1000) NOT NULL, 
    avatar VARCHAR(10000) NOT NULL,
    type_chat TYPECHAT NOT NULL,
    description VARCHAR,
    max_member INT NOT NULL,
    last_message_at INT,
    created_date INT NOT NULL
);

CREATE TABLE IF NOT EXISTS chat_member (
    id BIGSERIAL PRIMARY KEY,
    chat_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    name VARCHAR(1000) NOT NULL, 
    created_date INT NOT NULL,
    CONSTRAINT fk_chat FOREIGN KEY(chat_id) REFERENCES chat(id) ON DELETE CASCADE,
    CONSTRAINT fk_user FOREIGN KEY(user_id) REFERENCES user_chat(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS chat_member_setting (
    id BIGSERIAL PRIMARY KEY,
    chat_member_id BIGINT NOT NULL,
    is_push_notify BOOLEAN DEFAULT true,
    created_date INT NOT NULL,
    CONSTRAINT fk_chat_member FOREIGN KEY(chat_member_id) REFERENCES chat_member(id) ON DELETE CASCADE
);

CREATE TYPE TYPECONTENTMESSAGE AS ENUM ('TEXT','IMAGE','AUDIO','VIDEO','FILE','EMOJI','LOGGING');
CREATE TABLE IF NOT EXISTS message (
    id BIGSERIAL PRIMARY KEY,
    content TEXT NOT NULL,
    content_type TYPECONTENTMESSAGE NOT NULL,
    is_sent BOOLEAN DEFAULT false,
    is_hide_with_sender BOOLEAN DEFAULT false,
    is_hide_with_everyone BOOLEAN DEFAULT false,
    sender_member_id BIGINT NOT NULL,
    chat_id BIGINT NOT NULL,
    created_date INT NOT NULL,
    updated_date INT NOT NULL,
    CONSTRAINT fk_chat_member FOREIGN KEY(sender_member_id) REFERENCES chat_member(id) ON DELETE CASCADE,
    CONSTRAINT fk_chat FOREIGN KEY(chat_id) REFERENCES chat(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS message_sent (
    id BIGSERIAL PRIMARY KEY,
    message_id BIGINT NOT NULL,
    chat_member_id BIGINT NOT NULL,
    created_date INT NOT NULL,
    CONSTRAINT fk_chat_member FOREIGN KEY(chat_member_id) REFERENCES chat_member(id) ON DELETE CASCADE,
    CONSTRAINT fk_message FOREIGN KEY(message_id) REFERENCES message(id) ON DELETE CASCADE
);