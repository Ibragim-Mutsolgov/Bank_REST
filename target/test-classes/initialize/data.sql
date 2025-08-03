
insert into "users" (id, Username, Password, FirstName, LastName, Role)
values (10, 'Ibra', '$2a$12$.WVzS6w6e1kgm29/mStKcerEeAkThq4a0tBoSUI9yQkNcUe43gili',
        'Ibragim', 'Barkinho','USER');

insert into "users" (id, Username, Password, FirstName, LastName, Role)
values (12, 'Ali', '$2a$12$gJrsIkkaU3KunuYpJt3b3eda1AyI20zkDwEfCss44fEN57yDcNCie',
        'Ali', 'Barkinho','USER');

insert into "users" (id, Username, Password, FirstName, LastName, Role)
values (13, 'admin', '$2a$12$yf0x9p6vJ7XBqJrxxvn1Z.hu8wJ3/RDZic9whMnTsAXCor.HOkEDe',
        'admin', 'admin','ADMIN');

insert into "card" (id, Balance, Date, user_id, CardNumber, Status, to_block)
VALUES (15, 1000000,  '2025-07-29', 10, 5469380043745632, 'ACTIVE', 'false');

insert into "card" (id, Balance, Date, user_id, CardNumber, Status, to_block)
VALUES (16, 10000,  '2025-07-29', 10, 5469380043745798, 'ACTIVE', 'false');


insert into "card" (id, Balance, Date, user_id, CardNumber, Status, to_block)
VALUES (22, 100,  '2025-07-29', 12, 5469380043745635, 'ACTIVE', 'false')