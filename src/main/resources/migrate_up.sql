CREATE TYPE "role" AS ENUM (
    'USER',
    'ADMIN'
    );

CREATE TYPE "election_role" AS ENUM (
    'VOTER',
    'CANDIDATE'
    );

CREATE TYPE "strategy" AS ENUM (
    'USER',
    'ADMIN'
    );

CREATE TYPE "token_type" AS ENUM (
    'BEARER'
    );

CREATE TYPE "voting_strategy" AS ENUM (
    'ApprovalVoting',
    'InstantRunOff',
    'PlurarityVoting'
    );

CREATE TABLE "tokens" (
                         "id" SERIAL PRIMARY KEY,
                         "created_at" timestamp,
                         "expired_at" timestamp,
                         "revoked_at" timestamp,
                         "token" varchar NOT NULL,
                         "token_type" token_type,
                         "is_revoked" bool NOT NULL,
                         "is_expired" bool NOT NULL,
                         "user_id" integer
);

CREATE TABLE "user_election" (
                                 "user_id" integer,
                                 "election_id" integer,
                                 "role" election_role NOT NULL,
                                 PRIMARY KEY ("user_id", "election_id")
);

CREATE TABLE "locations" (
                             "id" SERIAL PRIMARY KEY,
                             "country" varchar NOT NULL,
                             "city" varchar NOT NULL,
                             "street_name" varchar NOT NULL,
                             "house_number" varchar NOT NULL,
                             "post_code" varchar NOT NULL
);

CREATE TABLE "users" (
                         "id" SERIAL PRIMARY KEY,
                         "created_at" timestamp,
                         "updated_at" timestamp,
                         "firstname" varchar NOT NULL,
                         "lastname" varchar NOT NULL,
                         "email" varchar NOT NULL,
                         "password" varchar NOT NULL,
                         "role" role NOT NULL,
                         "location_id" integer
);

CREATE TABLE "elections" (
                             "id" SERIAL PRIMARY KEY,
                             "created_at" timestamp,
                             "updated_at" timestamp,
                             "name" varchar NOT NULL,
                             "description" varchar,
                             "start_date" date NOT NULL,
                             "end_date" date NOT NULL,
                             "can_retract_vote" bool NOT NULL,
                             "strategy" strategy NOT NULL,
                             "location_id" integer
);

CREATE TABLE "ballots" (
                           "id" SERIAL PRIMARY KEY,
                           "created_at" timestamp,
                           "updated_at" timestamp,
                           "election_id" integer,
                           "user_id" integer,
                           "candidate_user_id" integer,
                           "candidate_position" integer NOT NULL
);

ALTER TABLE "tokens" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "user_election" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "user_election" ADD FOREIGN KEY ("election_id") REFERENCES "elections" ("id");

ALTER TABLE "users" ADD FOREIGN KEY ("location_id") REFERENCES "locations" ("id");

ALTER TABLE "elections" ADD FOREIGN KEY ("location_id") REFERENCES "locations" ("id");

ALTER TABLE "ballots" ADD FOREIGN KEY ("election_id") REFERENCES "elections" ("id");

ALTER TABLE "ballots" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "ballots" ADD FOREIGN KEY ("candidate_user_id") REFERENCES "users" ("id");
