CREATE TRIGGER set_created_at_in_elections_trigger
    BEFORE INSERT ON elections
    FOR EACH ROW
EXECUTE FUNCTION set_created_at();