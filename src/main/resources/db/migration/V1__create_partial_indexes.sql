-- CREATE UNIQUE INDEX IF NOT EXISTS uk_users_active_email
--     ON users(email)
--     WHERE deleted_at IS NULL;
--
-- CREATE UNIQUE INDEX IF NOT EXISTS uk_users_active_phone
--     ON users(phone_number)
--     WHERE deleted_at IS NULL;
--
-- CREATE UNIQUE INDEX IF NOT EXISTS uk_user_platform_active
--     ON user_social_links(user_id, platform_id)
--     WHERE deleted_at IS NULL;

CREATE SEQUENCE order_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE payment_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE invoice_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE refund_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;