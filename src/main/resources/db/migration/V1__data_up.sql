-- Insert fake locations
INSERT INTO "locations" ("country", "city", "street_name", "house_number", "post_code")
VALUES
    ('USA', 'New York', 'Broadway', '123', '10001'),
    ('UK', 'London', 'Oxford Street', '456', 'SW1A 1AA'),
    ('France', 'Paris', 'Champs-Élysées', '789', '75008');

-- Insert fake users
INSERT INTO "users" ("first_name", "last_name", "by_father", "email", "password", "birth_date", "role", "location_id")
VALUES
    ('John', 'Doe', 'Smith', 'john.doe@example.com', 'password123', '1990-05-15', 'USER', 1),
    ('Jane', 'Doe', 'Brown', 'jane.doe@example.com', 'password456', '1992-08-20', 'ADMIN', 2),
    ('Alice', 'Smith', 'Johnson', 'alice.smith@example.com', 'password789', '1985-12-10', 'USER', 3);

-- Insert fake tokens (assuming tokens are generated upon user creation)
INSERT INTO "tokens" ("token", "token_type", "user_id")
VALUES
    ('token123', 'BEARER', 1),
    ('token456', 'BEARER', 2),
    ('token789', 'BEARER', 3);

-- Insert fake elections
INSERT INTO "elections" ("title", "description", "start_date", "end_date", "can_retract_vote", "max_votes", "voting_strategy", "location_id")
VALUES
    ('Presidential Election', 'Election for the next president', NOW(), NOW() + interval '7' day, true, null, 'PluralityVoting', 1),
    ('City Council Election', 'Election for city council members', NOW(), NOW() + interval '10' day, false, null, 'ApprovalVoting', 2);

-- Insert fake candidates
INSERT INTO "users" ("first_name", "last_name", "by_father", "email", "password", "birth_date", "role", "location_id")
VALUES
    ('Candidate', 'One', 'Johnson', 'candidate.one@example.com', 'candidate123', '1980-01-01', 'USER', 1),
    ('Candidate', 'Two', 'Johnson', 'candidate.two@example.com', 'candidate456', '1975-03-10', 'USER', 2);

-- Insert fake candidate-election relationships
INSERT INTO "candidate_election" ("candidate_id", "election_id")
VALUES
    (4, 1),
    (5, 1),
    (4, 2),
    (5, 2);

-- Insert fake ballots
INSERT INTO "ballots" ("election_id", "user_id", "candidate_id", "candidate_point")
VALUES
    (1, 1, 4, 1),
    (1, 1, 5, 1),
    (2, 1, 4, 1),
    (2, 2, 4, 1),
    (2, 3, 5, 1);
