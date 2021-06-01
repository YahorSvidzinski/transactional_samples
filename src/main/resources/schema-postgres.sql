DROP TABLE IF EXISTS posts, comments, tickets;
CREATE TABLE posts (id serial PRIMARY KEY, title VARCHAR(255));
CREATE TABLE comments (id serial PRIMARY KEY, post_id int);
CREATE TABLE tickets (id serial PRIMARY KEY, place VARCHAR(255), price int);