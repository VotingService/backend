CREATE TRIGGER set_updated_at_in_elections_trigger
    BEFORE UPDATE ON elections
    FOR EACH ROW
EXECUTE FUNCTION set_updated_at();