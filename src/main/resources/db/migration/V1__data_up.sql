-- Insert fake locations
INSERT INTO "locations" ("country", "city", "street_name", "house_number", "post_code")
VALUES
    ('Україна', 'Львів', 'Різьбярська', '3', '79008'),
    ('Україна', 'Львів', 'Сахарова', '5', '79000'),
    ('Україна', 'Львів', 'Харківська', '16', '79001'),
    ('Україна', 'Київ', 'Сахарова', '10', '81000'),
    ('Україна', null, null, null, null),
    ('Україна', 'Львів', null, null, null);

-- Insert fake users
INSERT INTO "users" ("photo_url", "description", "first_name", "last_name", "by_father", "email", "password", "birth_date", "role", "location_id")
VALUES
    ('https://i.natgeofe.com/n/4f5aaece-3300-41a4-b2a8-ed2708a0a27c/domestic-dog_thumb_square.jpg',
     'Студент групи ІР-31','Юрій', 'Решетник', 'Романович', 'yurii.reshetnyk@example.com', '$2a$10$JF09boxBIIVZLIcOSiby4.7PlI9EEoTS50/OUkHfqMHNEdXgLIxnO', '2004-09-13', 'USER', 1), -- yurii123
    ('https://hips.hearstapps.com/ghk.h-cdn.co/assets/16/08/gettyimages-530330473.jpg?crop=0.659xw:0.990xh;0.123xw,0.00779xh&resize=980:*',
     'Студент групи ІР-31','Богдан', 'Завадка', 'Васильович', 'bogdan.zavadka@example.com', '$2a$10$/D3YHz.ogwiBVGKJ03IvP.ZgQIvSWMyiCY8Jh0xbUQXPR0X16/sW6', '2002-09-13', 'ADMIN', 2), -- bogdan123
    ('https://cdn.mos.cms.futurecdn.net/ASHH5bDmsp6wnK6mEfZdcU.jpg',
     'Студент групи ІР-31','Устим', 'Бучко', 'Володимирович', 'ustem.buchko@example.com', '$2a$10$9NKFZWpovjRxj45X3ilt4e.FsDma4K36jKjswAslkwlo1Ea88eD9m', '1985-12-10', 'ADMIN', 3), -- ustem123
    ('https://www.hartz.com/wp-content/uploads/2022/04/small-dog-owners-1.jpg',
     'Студент групи ІР-31','Остап', 'Шийка', 'Андрійович', 'ostab.one@example.com', '$2a$10$0zVxT6awkCduRnPS5x0eWOtViagsXqqrs9yi2AJ48equs.RPcuLIG', '1980-01-01', 'USER', 4), -- ostab123
    ('https://cdn.theatlantic.com/thumbor/W544GIT4l3z8SG-FMUoaKpFLaxE=/0x131:2555x1568/1600x900/media/img/mt/2017/06/shutterstock_319985324/original.jpg',
     'Студентка групи ІР-31','Юля', 'Швець', 'Устимівна', 'julia.two@example.com', '$2a$10$4UNGRpYig434lc1fZTfs7eGRibiiUCSqzzXOnv85DjfQPNKeepyZe', '1975-03-10', 'USER', 3); -- julia456

-- Insert fake elections
INSERT INTO "elections" ("title", "description", "start_date", "end_date", "can_retract_vote", "max_votes", "voting_strategy", "location_id")
VALUES
    ('Presidential Election', 'Election for the next president', NOW() - interval '1' day, NOW() + interval '15' day, true, 1, 'PluralityVoting', 5),
    ('Lviv City Council Election', 'Election for city council members', NOW(), NOW() + interval '10' day, false, 1, 'ApprovalVoting', 6),
    ('Country Council Election', 'Election for country council members', NOW(), NOW() + interval '10' day, false, 1, 'ApprovalVoting', 5);


-- Insert fake candidate-election relationships
INSERT INTO "candidate_election" ("candidate_id", "election_id")
VALUES
    (4, 1),
    (5, 1),
    (1, 2),
    (2, 2);

-- Insert fake ballots
INSERT INTO "ballots" ("election_id", "user_id", "candidate_id", "candidate_point")
VALUES
    (1, 4, 4, 1),
    (1, 4, 5, 1),
    (2, 1, 1, 1),
    (2, 2, 2, 1),
    (2, 3, 1, 1),
    (2, 3, 2, 1);
