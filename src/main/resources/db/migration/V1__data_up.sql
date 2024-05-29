-- Insert fake locations
INSERT INTO "locations" ("country", "city", "street_name", "house_number", "post_code")
VALUES
    ('Україна', 'Львів', 'Різьбярська', '3', '79008'),
    ('Україна', 'Львів', 'Сахарова', '5', '79000'),
    ('Україна', 'Львів', 'Харківська', '16', '79001');

-- Insert fake users
INSERT INTO "users" ("photo_url", "description", "first_name", "last_name", "by_father", "email", "password", "birth_date", "role", "location_id")
VALUES
    ('https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.nationalgeographic.com%2Fanimals%2Fmammals%2Ffacts%2Fdomestic-dog&psig=AOvVaw2ZbJ-9k8q5cSFpVWAY9tb2&ust=1717053518677000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCICcm_yosoYDFQAAAAAdAAAAABAE',
     'Студент групи ІР-31','Юрій', 'Решетник', 'Романович', 'yurii.reshetnyk@example.com', '$2a$10$JF09boxBIIVZLIcOSiby4.7PlI9EEoTS50/OUkHfqMHNEdXgLIxnO', '2004-09-13', 'USER', 1), -- yurii123
    ('https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.goodhousekeeping.com%2Flife%2Fpets%2Fnews%2Fg3291%2Fbest-dog-breeds%2F&psig=AOvVaw2ZbJ-9k8q5cSFpVWAY9tb2&ust=1717053518677000&source=images&cd=vfe&opi=89978449&ved=0CBIQjRxqFwoTCICcm_yosoYDFQAAAAAdAAAAABAJ',
     'Студент групи ІР-31','Богдан', 'Завадка', 'Васильович', 'bogdan.zavadka@example.com', '$2a$10$/D3YHz.ogwiBVGKJ03IvP.ZgQIvSWMyiCY8Jh0xbUQXPR0X16/sW6', '2002-09-13', 'ADMIN', 2), -- bogdan123
    ('','','Alice', 'Smith', 'Johnson', 'alice.smith@example.com', '$2a$10$k/inwYN6eYMt/NNGQKWT5.A7oh/2.5KxB.GGQMbTLncmegJsSdh.G', '1985-12-10', 'USER', 3), -- password789
    ('','','Candidate', 'One', 'Johnson', 'candidate.one@example.com', '$2a$10$vIJQsAmfjJrGRPEVPLefkuyzw6rNU5cSMzypoJW9Ha..Riog0BKqC', '1980-01-01', 'USER', 1), -- candidate123
    ('','','Candidate', 'Two', 'Johnson', 'candidate.two@example.com', '$2a$10$ni6M.l.S688R0ndr/zO0G.VVqIC0z.3ubrWXZfHjydsQImom49MDy', '1975-03-10', 'USER', 2); -- candidate456

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
