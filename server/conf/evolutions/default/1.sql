# --- !Ups
create table if not exists users(
id serial,
username varchar not null,
password varchar  not null,
has_team bool not null default 'f',
primary key(username)
);

create table if not exists clubs(
id serial,
club_name varchar not null,
primary key(club_name)
);

create table if not exists players(
id serial,
player_name varchar not null,
club_name varchar not null,
position varchar not null,
foreign key (club_name) references clubs (club_name)
);

create table if not exists user_team(
id serial,
username varchar not null,
team_name varchar not null,
foreign key (username) references users (username),
unique (username, team_name)
);

create table if not exists user_team_players(
username varchar not null,
team_name varchar not null,
player varchar not null,
foreign key (username) references users (username)
--foreign key (team_name) references user_team (team_name),
--foreign key (player) references players (player_name)
);

# --- !Downs
drop table users;
drop table clubs;
drop table players;
drop table user_team;
drop table user_team_players;
