CREATE OR REPLACE FUNCTION set_revoked_at()
    RETURNS TRIGGER AS $$
BEGIN
    IF NEW.is_revoked = TRUE AND OLD.is_revoked IS DISTINCT FROM NEW.is_revoked THEN
        NEW.revoked_at := NOW();
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;