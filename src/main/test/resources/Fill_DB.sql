INSERT INTO public.Engine (Name)
VALUES ('Unreal Engine 4'),
       ('Unity'),
       ('Unreal Engine 5'),
       ('Frostbite'),
       ('unknown'),
       ('Prometheus');


INSERT INTO public.Publisher (Name, Creation_Date)
VALUES ('Sony Interactive Entertainment', '1993-11-16'),
       ('Nintendo Co., Ltd.', '1889-09-23'),
       ('Microsoft Corporation', '1975-04-04'),
       ('Activision Blizzard, Inc.', '2008-07-10'),
       ('Electronic Arts Inc.', '1982-05-27'),
       ('Ubisoft Entertainment SA', '1986-03-28');


INSERT INTO public.Game (Name, Price, Description, Publisher_id, Engine_id)
VALUES ('Dragon Age: Inquisition', '40.00', 'Do you want to romance an egg?', 5, 4),
       ('Fifa 983', '1000.00', 'Best Game ever created', 5, 4),
       ('Mass Effect', '1000.00', 'You can #### an alien now!', 5, 4),
       ('Super Mario 64', '5.00', 'YAHOOO', 2, 5),
       ('Overwatch', '40.00', 'Team role based FPS (closed down)', 4, 6),
       ('Overwatch 2', '0.00', 'Actually costs 1000', 4, 6);


INSERT INTO public."USER" (username, Join_Date, email)
VALUES ('nikado_avokado', '2009-12-12', 'nikavokado@gmail.com'),
       ('pdp', '2000-01-20', 'pewpew@gmail.com'),
       ('dr0n', '2012-06-11', 'nikand@gmail.com'),
       ('valzal', '2018-09-18', 'vzal@gmail.com'),
       ('tomcat', '2022-12-24', 'tomcat@ninth.hell'),
       ('true_nord', '2011-11-11', 'skyrim@isfor.nords'),
       ('not_a_cat', '2020-12-20', 'cat@nine.lives');

INSERT INTO public.Game_User (Game_id, User_id)
VALUES (1, 4),
       (3, 4),
       (2, 1),
       (3, 5),
       (3, 6),
       (4, 5);