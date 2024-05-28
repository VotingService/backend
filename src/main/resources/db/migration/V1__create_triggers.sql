CREATE OR REPLACE FUNCTION set_created_at()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.created_at := NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION set_updated_at()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at := NOW();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS set_created_at_in_ballots_trigger ON ballots;
DROP TRIGGER IF EXISTS set_created_at_in_elections_trigger ON elections;
DROP TRIGGER IF EXISTS set_created_at_in_tokens_trigger ON tokens;
DROP TRIGGER IF EXISTS set_created_at_in_users_trigger ON users;
DROP TRIGGER IF EXISTS set_updated_at_in_ballots_trigger ON ballots;
DROP TRIGGER IF EXISTS set_updated_at_in_elections_trigger ON elections;
DROP TRIGGER IF EXISTS set_updated_at_in_users_trigger ON users;

CREATE TRIGGER set_created_at_in_ballots_trigger
    BEFORE INSERT ON ballots
    FOR EACH ROW
EXECUTE FUNCTION set_created_at();

CREATE TRIGGER set_created_at_in_elections_trigger
    BEFORE INSERT ON elections
    FOR EACH ROW
EXECUTE FUNCTION set_created_at();

CREATE TRIGGER set_created_at_in_tokens_trigger
    BEFORE INSERT ON tokens
    FOR EACH ROW
EXECUTE FUNCTION set_created_at();

CREATE TRIGGER set_created_at_in_users_trigger
    BEFORE INSERT ON users
    FOR EACH ROW
EXECUTE FUNCTION set_created_at();

CREATE TRIGGER set_updated_at_in_ballots_trigger
    BEFORE INSERT ON ballots
    FOR EACH ROW
EXECUTE FUNCTION set_updated_at();

CREATE TRIGGER set_updated_at_in_elections_trigger
    BEFORE UPDATE ON elections
    FOR EACH ROW
EXECUTE FUNCTION set_updated_at();

CREATE TRIGGER set_updated_at_in_users_trigger
    BEFORE UPDATE ON users
    FOR EACH ROW
EXECUTE FUNCTION set_updated_at();