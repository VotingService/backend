CREATE TABLE "tokens" (
                         "id" SERIAL PRIMARY KEY,
                         "created_at" timestamp,
                         "expired_at" timestamp,
                         "revoked_at" timestamp,
                         "token" varchar NOT NULL,
                         "token_type" varchar,
                         "is_revoked" bool NOT NULL DEFAULT false,
                         "is_expired" bool NOT NULL DEFAULT false,
                         "user_id" integer NOT NULL
);

CREATE TABLE "candidate_election" (
                                 "candidate_id" integer,
                                 "election_id" integer,
                                 PRIMARY KEY ("candidate_id", "election_id")
);

CREATE TABLE "locations" (
                             "id" SERIAL PRIMARY KEY,
                             "country" varchar NOT NULL,
                             "city" varchar NOT NULL,
                             "street_name" varchar,
                             "house_number" varchar,
                             "post_code" varchar
);

CREATE TABLE "users" (
                         "id" SERIAL PRIMARY KEY,
                         "created_at" timestamp,
                         "updated_at" timestamp,
                         "firstname" varchar NOT NULL,
                         "lastname" varchar NOT NULL,
                         "email" varchar UNIQUE NOT NULL,
                         "password" varchar UNIQUE NOT NULL,
                         birth_date DATE,
                         "role" role NOT NULL,
                         "location_id" integer
);

CREATE TABLE "elections" (
                             "id" SERIAL PRIMARY KEY,
                             "created_at" timestamp,
                             "updated_at" timestamp,
                             "title" varchar NOT NULL,
                             "description" varchar,
                             "start_date" timestamp NOT NULL,
                             "end_date" timestamp NOT NULL,
                             "can_retract_vote" bool NOT NULL,
                             "max_votes" integer NOT NULL,
                             "voting_strategy" varchar NOT NULL,
                             "location_id" integer
);

CREATE TABLE "ballots" (
                           "id" SERIAL PRIMARY KEY,
                           "created_at" timestamp,
                           "updated_at" timestamp,
                           "election_id" integer,
                           "user_id" integer,
                           "candidate_id" integer,
                           "candidate_position" integer NOT NULL
);

ALTER TABLE "tokens" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "candidate_election" ADD FOREIGN KEY ("candidate_id") REFERENCES "users" ("id");

ALTER TABLE "candidate_election" ADD FOREIGN KEY ("election_id") REFERENCES "elections" ("id");

ALTER TABLE "users" ADD FOREIGN KEY ("location_id") REFERENCES "locations" ("id");

ALTER TABLE "elections" ADD FOREIGN KEY ("location_id") REFERENCES "locations" ("id");

ALTER TABLE "ballots" ADD FOREIGN KEY ("election_id") REFERENCES "elections" ("id");

ALTER TABLE "ballots" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "ballots" ADD FOREIGN KEY ("candidate_id") REFERENCES "users" ("id");

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
