CREATE TRIGGER set_expired_at_in_tokens_trigger
    BEFORE UPDATE ON tokens
    FOR EACH ROW
EXECUTE FUNCTION set_expired_at();