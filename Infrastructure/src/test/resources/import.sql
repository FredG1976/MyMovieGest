INSERT INTO GENRE(name) VALUES ('Action');
INSERT INTO GENRE(name) VALUES ('Adventure');
INSERT INTO GENRE(name) VALUES ('Drama');

INSERT INTO ACTOR(name) VALUES ('Harisson Ford');
INSERT INTO ACTOR(name) VALUES ('Karen Allen');
INSERT INTO ACTOR(name) VALUES ('Paul Freeman');
INSERT INTO ACTOR(name) VALUES ('Kate Capshaw');
INSERT INTO ACTOR(name) VALUES ('Ke Huy Quan');
INSERT INTO ACTOR(name) VALUES ('Sean Connery');
INSERT INTO ACTOR(name) VALUES ('Alison Doody');

INSERT INTO USER_ML(full_name, is_admin, login, password)	VALUES ( 'Xavier Duflot', false, 'Xavier', '$2a$10$hzfPLjXZMl/w32hxoLz53u.R48mY463JSqHtDzEoFY50BZKOMOMd2');
INSERT INTO USER_ML(full_name, is_admin, login, password)	VALUES ( 'Florence Dujardin', true, 'Florence', '$2a$10$0o2oOunATWyQc.C6K464WuhwKXKRwnTYj3UtwBGpifFGUY/edn4E6');
INSERT INTO USER_ML(full_name, is_admin, login, password)	VALUES ( 'Claudia Castellanne', false, 'Claudia', '$2a$10$hzfPLjXZMl/w32hxoLz53u.R48mY463JSqHtDzEoFY50BZKOMOMd2');

INSERT INTO MEDIA(director, episode, imdbid, imdb_rating, plot, poster, runtime, season, seriesid, title, total_seasons, type, year) VALUES ('Steven Spielberg', 0, 'tt0082971', 8.4, 'In 1936, archaeologist and adventurer Indiana Jones is hired by the U.S. government to find the Ark of the Covenant before Adolf Hitler\"s Nazis can obtain its awesome powers.', 'https://m.media-amazon.com/images/M/MV5BMjA0ODEzMTc1Nl5BMl5BanBnXkFtZTcwODM2MjAxNA@@._V1_SX300.jpg', '114 min', 0, null, 'Les Aventuriers de l Arche Perdu', 0, 'MOVIE', '1981');
INSERT INTO MEDIA(director, episode, imdbid, imdb_rating, plot, poster, runtime, season, seriesid, title, total_seasons, type, year) VALUES ('Steven Spielberg', 0, 'tt0087469', 7.5, 'In 1935, Indiana Jones arrives in India, still part of the British Empire, and is asked to find a mystical stone. He then stumbles upon a secret cult committing enslavement and human sacrifices in the catacombs of an ancient palace.', 'https://m.media-amazon.com/images/M/MV5BMGI1NTk2ZWMtMmI0YS00Yzg0LTljMzgtZTg4YjkyY2E5Zjc0XkEyXkFqcGdeQXVyNzkwMjQ5NzM@._V1_SX300.jpg', '118 min', 0, null, 'Indiana Jones et le Temple Maudit', 0, 'MOVIE', '1984');
INSERT INTO MEDIA(director, episode, imdbid, imdb_rating, plot, poster, runtime, season, seriesid, title, total_seasons, type, year) VALUES ('Steven Spielberg', 0, 'tt0097576', 8.2, 'In 1938, after his father Professor Henry Jones, Sr. goes missing while pursuing the Holy Grail, Professor Henry \"Indiana\" Jones, Jr. finds himself up against Adolf Hitler\"s Nazis again to stop them from obtaining its powers.', 'https://m.media-amazon.com/images/M/MV5BMjNkMzc2N2QtNjVlNS00ZTk5LTg0MTgtODY2MDAwNTMwZjBjXkEyXkFqcGdeQXVyNDk3NzU2MTQ@._V1_SX300.jpg', '127 min', 0, null, 'Indiana Jones et la Dernière Croisade', 0, 'MOVIE', '1989');
INSERT INTO MEDIA(director, episode, imdbid, imdb_rating, plot, poster, runtime, season, seriesid, title, total_seasons, type, year) VALUES ('Steven Spielberg', 0, 'tt0367882', 6.1, 'In 1957, archaeologist and adventurer Dr. Henry \"Indiana\" Jones, Jr. is called back into action and becomes entangled in a Soviet plot to uncover the secret behind mysterious artifacts known as the Crystal Skulls.', 'https://m.media-amazon.com/images/M/MV5BZDIzNzM5MDUtZmI5MC00NGQ5LWFlNzEtYzE3ODIxNDI3ZmNhXkEyXkFqcGdeQXVyNjQ4ODE4MzQ@._V1_SX300.jpg', '123 min', 0, null, 'Indiana Jones et le Royaume du Crâne de Cristal', 0, 'MOVIE', '2008');

INSERT INTO MEDIA_GENRE(media_id, genre_id) VALUES (1, 3);
INSERT INTO MEDIA_GENRE(media_id, genre_id) VALUES (1, 1);
INSERT INTO MEDIA_GENRE(media_id, genre_id) VALUES (1, 2);

INSERT INTO MEDIA_GENRE(media_id, genre_id) VALUES (2, 1);
INSERT INTO MEDIA_GENRE(media_id, genre_id) VALUES (2, 3);

INSERT INTO MEDIA_GENRE(media_id, genre_id) VALUES (3, 1);
INSERT INTO MEDIA_GENRE(media_id, genre_id) VALUES (3, 3);


INSERT INTO MEDIA_ACTOR(media_id, actor_id) VALUES (1, 1);
INSERT INTO MEDIA_ACTOR(media_id, actor_id) VALUES (1, 2);
INSERT INTO MEDIA_ACTOR(media_id, actor_id) VALUES (1, 3);
INSERT INTO MEDIA_ACTOR(media_id, actor_id) VALUES (2, 1);
INSERT INTO MEDIA_ACTOR(media_id, actor_id) VALUES (2, 4);
INSERT INTO MEDIA_ACTOR(media_id, actor_id) VALUES (2, 5);
INSERT INTO MEDIA_ACTOR(media_id, actor_id) VALUES (3, 1);
INSERT INTO MEDIA_ACTOR(media_id, actor_id) VALUES (3, 6);
INSERT INTO MEDIA_ACTOR(media_id, actor_id) VALUES (3, 7);

INSERT INTO USER_MEDIA( own_comment, own_rating, status, media_id, userml_id) VALUES (null, null, 'TOWATCH', 1, 2);
INSERT INTO USER_MEDIA( own_comment, own_rating, status, media_id, userml_id) VALUES ('A recommander en famille', 8.4, 'WATCHED', 3, 2);
INSERT INTO USER_MEDIA( own_comment, own_rating, status, media_id, userml_id) VALUES (null, null, 'INPROGRESS', 2, 2);
INSERT INTO USER_MEDIA( own_comment, own_rating, status, media_id, userml_id) VALUES (null, null, 'INPROGRESS', 4, 1);
INSERT INTO USER_MEDIA( own_comment, own_rating, status, media_id, userml_id) VALUES (null, null, 'TOWATCH', 3, 1);

--INSERT INTO USERS(username, password, enabled) VALUES ('user11', '$2a$10$hzfPLjXZMl/w32hxoLz53u.R48mY463JSqHtDzEoFY50BZKOMOMd2', true);
--INSERT INTO USERS(username, password, enabled) VALUES ('user22', '$2a$10$hzfPLjXZMl/w32hxoLz53u.R48mY463JSqHtDzEoFY50BZKOMOMd2', true);
--INSERT INTO USERS(username, password, enabled) VALUES ('adminml', '$2a$10$0o2oOunATWyQc.C6K464WuhwKXKRwnTYj3UtwBGpifFGUY/edn4E6', true);

--INSERT INTO USER_ROLES( user_role_id, username, role) VALUES ( 1, 'user11', 'ROLE_USER');
--INSERT INTO USER_ROLES( user_role_id, username, role) VALUES ( 2, 'user22', 'ROLE_USER');
--INSERT INTO USER_ROLES( user_role_id, username, role) VALUES ( 3, 'adminml', 'ROLE_ADMIN');
