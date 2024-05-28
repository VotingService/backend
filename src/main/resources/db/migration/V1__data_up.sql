-- Insert fake locations
INSERT INTO "locations" ("country", "city", "street_name", "house_number", "post_code")
VALUES
    ('USA', 'New York', 'Broadway', '123', '10001'),
    ('UK', 'London', 'Oxford Street', '456', 'SW1A 1AA'),
    ('France', 'Paris', 'Champs-Élysées', '789', '75008');

-- Insert fake users
INSERT INTO "users" ("first_name", "last_name", "by_father", "email", "password", "birth_date", "role", "location_id")
VALUES
    ('John', 'Doe', 'Smith', 'john.doe@example.com', '$2a$10$.oSOEdpFwgQhO1sFsAyr6.k9VsmzMjluezry0rSB4/B5OPGzb/CQa', '1990-05-15', 'USER', 1), -- password123
    ('Jane', 'Doe', 'Brown', 'jane.doe@example.com', '$2a$10$4KXbTqlmchPzD26pcYi/J.NpnthvfOxWHTq8Zetmf4zJVzX9I9DWq', '1992-08-20', 'ADMIN', 2), -- password456
    ('Alice', 'Smith', 'Johnson', 'alice.smith@example.com', '$2a$10$k/inwYN6eYMt/NNGQKWT5.A7oh/2.5KxB.GGQMbTLncmegJsSdh.G', '1985-12-10', 'USER', 3), -- password789
    ('Candidate', 'One', 'Johnson', 'candidate.one@example.com', '$2a$10$vIJQsAmfjJrGRPEVPLefkuyzw6rNU5cSMzypoJW9Ha..Riog0BKqC', '1980-01-01', 'USER', 1), -- candidate123
    ('Candidate', 'Two', 'Johnson', 'candidate.two@example.com', '$2a$10$ni6M.l.S688R0ndr/zO0G.VVqIC0z.3ubrWXZfHjydsQImom49MDy', '1975-03-10', 'USER', 2); -- candidate456

-- Insert fake elections
INSERT INTO "elections" ("title", "description", "start_date", "end_date", "can_retract_vote", "max_votes", "voting_strategy", "location_id")
VALUES
    ('Presidential Election', 'Election for the next president', NOW(), NOW() + interval '7' day, true, null, 'PluralityVoting', 1),
    ('City Council Election', 'Election for city council members', NOW(), NOW() + interval '10' day, false, null, 'ApprovalVoting', 2);


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
