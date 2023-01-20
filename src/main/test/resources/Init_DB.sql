CREATE TABLE if not exists ENGINE (
                                      name text NOT NULL,
                                      id identity NOT NULL,
                                      primary key (id)
);
CREATE TABLE if not exists PUBLISHER (
                                         name text NOT NULL,
                                         creation_Date date,
                                         id identity NOT NULL,
                                         primary key (id)
);
CREATE TABLE if not exists Game (
                                    name text NOT NULL,
                                    price numeric(19,4),
                                    description text,
                                    id identity NOT NULL,
                                    publisher_id integer NOT NULL,
                                    Engine_id integer NOT NULL,
                                    primary key (id),
                                    foreign key (publisher_id) references Publisher (id) on delete cascade ,
                                    foreign key (Engine_id) references Engine (id) on delete cascade
);
CREATE TABLE if not exists "USER" (
                                    username text NOT NULL,
                                    join_Date date,
                                    email text NOT NULL,
                                    id identity NOT NULL,
                                    primary key (id)
);
CREATE TABLE if not exists GAME_USER (
                 game_id integer NOT NULL,
                 user_id integer NOT NULL,
                 id identity NOT NULL,
                 primary key (id),
                 foreign key (game_id) references Game (id) on delete cascade,
                 foreign key (user_id) references "USER" (id) on delete cascade
);