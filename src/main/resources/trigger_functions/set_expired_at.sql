CREATE OR REPLACE FUNCTION set_expired_at()
    RETURNS TRIGGER AS $$
BEGIN
    IF NEW.is_expired = TRUE AND OLD.is_expired IS DISTINCT FROM NEW.is_expired THEN
        NEW.expired_at := NOW();
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;