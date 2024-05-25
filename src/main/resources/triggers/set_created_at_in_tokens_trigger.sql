CREATE TRIGGER set_created_at_in_tokens_trigger
    BEFORE INSERT ON tokens
    FOR EACH ROW
EXECUTE FUNCTION set_created_at();