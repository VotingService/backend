-- DROP TABLE "tokens";
-- DROP TABLE "candidate_election";
-- DROP TABLE "ballots";
-- DROP TABLE "elections";
-- DROP TABLE "users";
-- DROP TABLE "locations";

CREATE TABLE IF NOT EXISTS "tokens" (
                         "id" SERIAL NOT NULL PRIMARY KEY,
                         "created_at" timestamp(6),
                         "token" varchar NOT NULL unique,
                         "token_type" varchar(255) not null check (token_type in ('BEARER')),
                         "is_revoked" boolean NOT NULL DEFAULT false,
                         "is_expired" boolean NOT NULL DEFAULT false,
                         "user_id" integer NOT NULL
);

CREATE TABLE IF NOT EXISTS "candidate_election" (
                                 "candidate_id" integer NOT NULL,
                                 "election_id" integer NOT NULL,
                                 PRIMARY KEY ("candidate_id", "election_id")
);

CREATE TABLE IF NOT EXISTS "locations" (
                             "id" SERIAL NOT NULL PRIMARY KEY,
                             "country" varchar(255) NOT NULL,
                             "city" varchar(255),
                             "street_name" varchar(255),
                             "house_number" varchar(255),
                             "post_code" varchar(255)
);

CREATE TABLE IF NOT EXISTS "users" (
                         "id" SERIAL NOT NULL PRIMARY KEY,
                         "created_at" timestamp(6),
                         "updated_at" timestamp(6),
                         "first_name" varchar(255) NOT NULL,
                         "last_name" varchar(255) NOT NULL,
                         by_father varchar(255) not null,
                         "email" varchar(255) UNIQUE NOT NULL,
                         "password" varchar(255) UNIQUE NOT NULL,
                         birth_date DATE NOT NULL,
                         "role" varchar(255) NOT NULL check (role in ('USER','ADMIN')),
                         "location_id" integer not null
);

CREATE TABLE IF NOT EXISTS "elections" (
                             "id" SERIAL NOT NULL PRIMARY KEY,
                             "created_at" timestamp(6),
                             "updated_at" timestamp(6),
                             "title" varchar(255) NOT NULL,
                             "description" varchar(255),
                             "start_date" timestamp(6) NOT NULL,
                             "end_date" timestamp(6) NOT NULL,
                             "can_retract_vote" boolean NOT NULL,
                             "max_votes" integer,
                             "voting_strategy" varchar(255) NOT NULL check (voting_strategy in ('ApprovalVoting','DistributionVoting','PluralityVoting')),
                             "location_id" integer NOT NULL
);

CREATE TABLE IF NOT EXISTS "ballots" (
                           "id" SERIAL NOT NULL PRIMARY KEY,
                           "created_at" timestamp(6),
                           "updated_at" timestamp(6),
                           "election_id" integer NOT NULL,
                           "user_id" integer NOT NULL,
                           "candidate_id" integer NOT NULL,
                           "candidate_point" integer NOT NULL,
                           unique (election_id, user_id, candidate_id)
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
