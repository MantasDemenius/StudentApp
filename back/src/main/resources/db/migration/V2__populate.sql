insert into "course" (code, title, language) values ('T150M123', 'Virtualios technologijos aprangos pramonėje', 'Lietuvių');
insert into "course" (code, title, language) values ('T150B169', 'Mados produkto kompiuterizuotas modeliavimas', 'Lietuvių');
insert into "course" (code, title, language) values ('T120B188 ', 'Debesų technologijų saugyklos', 'Lietuvių');
insert into "course" (code, title, language) values ('T170B466', 'Taikomoji elektronika', 'Lietuvių');
insert into "course" (code, title, language) values ('P130B002', 'Matematika 2', 'Lietuvių');
insert into "course" (code, title, language) values ('', 'Anglų kalbos kursai dėstytojams', 'Anglų');
insert into "course" (code, title, language) values ('', 'Robotų Intelektas 2021', 'Lietuvių');
insert into "course" (code, title, language) values ('T120M168 ', 'Algoritmai ir objektinis programavimas', 'Lietuvių');
insert into "course" (code, title, language) values ('P175B129 ', 'Išskirstytosios duomenų bazės', 'Lietuvių');
insert into "course" (code, title, language) values ('T120B516 ', 'Objektinis programų projektavimas', 'Lietuvių');
insert into "course" (code, title, language) values ('T120B162 ', 'Programų sistemų testavimas', 'Lietuvių');
insert into "course" (code, title, language) values ('T125B114 ', 'Robotų programavimo technologijos', 'Lietuvių');
insert into "course" (code, title, language) values ('T120B165 ', 'Saityno taikomųjų programų projektavimas', 'Lietuvių');
insert into "course" (code, title, language) values ('S180B103 ', 'Inžinerinė ekonomika', 'Lietuvių');

INSERT INTO public."user"
(username, "name", surname, code, "password", "user_type")
VALUES('jurale', 'Jurgis', 'Alekna', 'B2345', '$2a$10$toef4ighbVgGERA.tiAo2e6lK/F0i1VwkLD6FtUBk1nz4lk/9PZSC', 'Staff');

INSERT INTO public."role"
("name")
VALUES('ADMIN');

INSERT INTO public.user_role
(user_id, role_id)
VALUES(1, 1);

INSERT INTO public.email
(user_id, email, is_main)
VALUES(1, 'Jurgis.Alekna@ktu.lt', true);

INSERT INTO public.email
(user_id, email, is_main)
VALUES(1, 'Jurgis.Alekna@gmail.com', false);

INSERT INTO public."user"
(username, "name", surname, code, "password", "user_type")
VALUES('jocbro', 'Jočys', 'Bronius', 'B2346', '$2a$10$UKTP3QVYYCeVokKO9YZLtOslaIFvr4g6hDf3pvdyoB1.FulG32o.u', 'Student');

INSERT INTO public."role"
("name")
VALUES( 'STUDENT');

INSERT INTO public.user_role
(user_id, role_id)
VALUES(2, 2);

INSERT INTO public."user"
(username, "name", surname, code, "password", "user_type")
VALUES('pauleo', 'Paulius', 'Leonas', 'B2347', '$2a$10$W5NNSwxVQ1IIHkube3wy2eXRft64A795oyIhLQmENdD/barzLoXpK', 'Staff');

INSERT INTO public."role"
("name")
VALUES( 'LECTURER');

INSERT INTO public.user_role
(user_id, role_id)
VALUES(3, 3);

INSERT INTO public.email
(user_id, email, is_main)
VALUES(3, 'Paulius.Leonas@ktu.lt', true);

INSERT INTO public."user"
(username, "name", surname, code, "password", "user_type")
VALUES('alfmyk', 'Alfonsas', 'Mykolaitis', 'B2348', '$2a$10$DXTY201KxnbhQ0fyXwNbe.wa2cXfXFVy7Cg2dAr7xsSJ6nonrsN6O', 'Staff');

INSERT INTO public.user_role
(user_id, role_id)
VALUES(4, 3);


INSERT INTO public.email
(user_id, email, is_main)
VALUES(4, 'Alfonsas.Mykolaitis@ktu.lt', true);

INSERT INTO public."user"
(username, "name", surname, code, "password", "user_type")
VALUES('hlecturer', 'Katedros', 'Vedėjas', 'B2349', '$2a$10$521431q2cEWxKVvIson9vebTL4ILSw7rGBfVqXLN50S4QC9HMKkMW', 'Staff');

INSERT INTO public.user_role
(user_id, role_id)
VALUES(5, 3);

INSERT INTO public.email
(user_id, email, is_main)
VALUES(5, 'MainHeadLecturer@ktu.edu', true);

INSERT INTO public."user"
(username, "name", surname, code, "password", "user_type")
VALUES('hstudent', 'Studentas', 'Seniūnas', 'B2350', '$2a$10$oeNDQVPp9vVIlhKZveUXr.bp4LZSfRaY3lW7xe5PFtkB63zXkK0/S', 'Student');

INSERT INTO public."role"
("name")
VALUES( 'HEAD_STUDENT');

INSERT INTO public.user_role
(user_id, role_id)
VALUES(6, 4);

INSERT INTO public.user_role
(user_id, role_id)
VALUES(6, 2);


INSERT INTO public."user"
(username, "name", surname, code, "password", "user_type")
VALUES('editor', 'Prižiūrėtojas', 'Atsiliepimų', 'B2351', '$2a$10$jd2fPM8CcnkQA4DJrp4S2uYnvjuPsphOKaPjX/lVbw9RaisNePCya', 'Staff');

INSERT INTO public."role"
("name")
VALUES( 'EDITOR');

INSERT INTO public.user_role
(user_id, role_id)
VALUES(7, 5);

INSERT INTO public."user"
(username, "name", surname, code, "password", "user_type")
VALUES('estudent', 'Studentas', 'Prižiūrėojas', 'B2352', '$2a$10$U5UAcw1eE5OrlLNp3x.VCudTC2rEAr0qJ/auO4Dtgx.d/P2wCEVra', 'Student');

INSERT INTO public.user_role
(user_id, role_id)
VALUES(8, 2);

INSERT INTO public.user_role
(user_id, role_id)
VALUES(8, 5);


--Super user
INSERT INTO public."user"
(username, "name", surname, code, "password", "user_type")
VALUES('mandem', 'Mantas', 'Demenius', 'B4932', '$2a$10$toef4ighbVgGERA.tiAo2e6lK/F0i1VwkLD6FtUBk1nz4lk/9PZSC', 'Staff');

INSERT INTO public.user_role
(user_id, role_id)
VALUES(9, 1);

INSERT INTO public.user_role
(user_id, role_id)
VALUES(9, 2);

INSERT INTO public.user_role
(user_id, role_id)
VALUES(9, 3);

INSERT INTO public.user_role
(user_id, role_id)
VALUES(9, 4);

INSERT INTO public.user_role
(user_id, role_id)
VALUES(9, 5);

INSERT INTO public.user_course_visibility
(user_id, course_id)
VALUES(9, 8);
INSERT INTO public.user_course_visibility
(user_id, course_id)
VALUES(9, 9);
INSERT INTO public.user_course_visibility
(user_id, course_id)
VALUES(9, 10);
INSERT INTO public.user_course_visibility
(user_id, course_id)
VALUES(9, 11);
INSERT INTO public.user_course_visibility
(user_id, course_id)
VALUES(9, 12);
INSERT INTO public.user_course_visibility
(user_id, course_id)
VALUES(9, 13);
INSERT INTO public.user_course_visibility
(user_id, course_id)
VALUES(9, 14);


--Super user

INSERT INTO public.user_course_visibility
(course_id, user_id)
VALUES(1, 2);

INSERT INTO public.user_course_visibility
(course_id, user_id)
VALUES(2, 2);

INSERT INTO public.user_course_visibility
(course_id, user_id)
VALUES(3, 2);

INSERT INTO public.user_course_visibility
(course_id, user_id)
VALUES(4, 2);


INSERT INTO public.user_course_visibility
(course_id, user_id)
VALUES(1, 6);

INSERT INTO public.user_course_visibility
(course_id, user_id)
VALUES(2, 6);

INSERT INTO public.user_course_visibility
(course_id, user_id)
VALUES(3, 6);

INSERT INTO public.user_course_visibility
(course_id, user_id)
VALUES(4, 6);

INSERT INTO public.user_course_visibility
(course_id, user_id)
VALUES(1, 6);

INSERT INTO public.user_course_visibility
(course_id, user_id)
VALUES(2, 6);

INSERT INTO public.user_course_visibility
(course_id, user_id)
VALUES(3, 6);

INSERT INTO public.user_course_visibility
(course_id, user_id)
VALUES(4, 6);


INSERT INTO public.user_course_visibility
(course_id, user_id)
VALUES(2, 3);

INSERT INTO public.user_course_visibility
(course_id, user_id)
VALUES(1, 4);

INSERT INTO public.user_course_visibility
(course_id, user_id)
VALUES(4, 4);

INSERT INTO public.user_course_visibility
(course_id, user_id)
VALUES(2, 5);

INSERT INTO public.user_course_visibility
(course_id, user_id)
VALUES(1, 1);

INSERT INTO public.user_course_visibility
(course_id, user_id)
VALUES(2, 1);

INSERT INTO public.user_course_visibility
(course_id, user_id)
VALUES(3, 1);

INSERT INTO public.user_course_visibility
(course_id, user_id)
VALUES(4, 1);

INSERT INTO public.user_course_visibility
(course_id, user_id)
VALUES(5, 1);

INSERT INTO public.user_course_visibility
(course_id, user_id)
VALUES(6, 1);

INSERT INTO public.user_course_visibility
(course_id, user_id)
VALUES(7, 1);

INSERT INTO public.user_course_visibility
(course_id, user_id)
VALUES(8, 1);


INSERT INTO public.user_course_visibility
(course_id, user_id)
VALUES(1, 7);

INSERT INTO public.user_course_visibility
(course_id, user_id)
VALUES(2, 7);

INSERT INTO public.user_course_visibility
(course_id, user_id)
VALUES(3, 7);

INSERT INTO public.user_course_visibility
(course_id, user_id)
VALUES(4, 7);

INSERT INTO public.user_course_visibility
(course_id, user_id)
VALUES(5, 7);

INSERT INTO public.user_course_visibility
(course_id, user_id)
VALUES(6, 7);

INSERT INTO public.user_course_visibility
(course_id, user_id)
VALUES(7, 7);

INSERT INTO public.user_course_visibility
(course_id, user_id)
VALUES(8, 7);


INSERT INTO public.user_course_responsibility
(course_id, user_id, is_head, is_direct_head)
VALUES(2, 3, false, false);

INSERT INTO public.user_course_visibility
(user_id, course_id)
VALUES(3, 9);
INSERT INTO public.user_course_visibility
(user_id, course_id)
VALUES(3, 10);
INSERT INTO public.user_course_visibility
(user_id, course_id)
VALUES(3, 11);
INSERT INTO public.user_course_visibility
(user_id, course_id)
VALUES(3, 12);
INSERT INTO public.user_course_visibility
(user_id, course_id)
VALUES(3, 13);
INSERT INTO public.user_course_visibility
(user_id, course_id)
VALUES(3, 14);


INSERT INTO public.user_course_visibility
(course_id, user_id)
VALUES(7, 4);

INSERT INTO public.user_course_visibility
(course_id, user_id)
VALUES(8, 4);

INSERT INTO public.user_course_visibility
(course_id, user_id)
VALUES(5, 4);

INSERT INTO public.user_course_responsibility
(course_id, user_id, is_head, is_direct_head)
VALUES(4, 4, false, true);

INSERT INTO public.user_course_responsibility
(course_id, user_id, is_head, is_direct_head)
VALUES(1, 4, false, true);




INSERT INTO public.user_course_visibility
(course_id, user_id)
VALUES(8, 5);

INSERT INTO public.user_course_visibility
(course_id, user_id)
VALUES(7, 5);

INSERT INTO public.user_course_visibility
(course_id, user_id)
VALUES(6, 5);

INSERT INTO public.user_course_visibility
(course_id, user_id)
VALUES(5, 5);



INSERT INTO public.user_course_responsibility
(course_id, user_id, is_head, is_direct_head)
VALUES(2, 5, true, false);



INSERT INTO public.user_course_visibility
(course_id, user_id)
VALUES(4, 8);

INSERT INTO public.user_course_visibility
(course_id, user_id)
VALUES(3, 8);

INSERT INTO public.user_course_visibility
(course_id, user_id)
VALUES(2, 8);

INSERT INTO public.user_course_visibility
(course_id, user_id)
VALUES(1, 8);


INSERT INTO public."permission"
("name")
VALUES('courses:read');
INSERT INTO public."permission"
("name")
VALUES('courses:timetable');
INSERT INTO public."permission"
("name")
VALUES('rooms:read');
INSERT INTO public."permission"
("name")
VALUES('seats:read');
INSERT INTO public."permission"
("name")
VALUES('seats:create');
INSERT INTO public."permission"
("name")
VALUES('seats:delete');
INSERT INTO public."permission"
("name")
VALUES('seats:download');
INSERT INTO public."permission"
("name")
VALUES('seats:occupy');
INSERT INTO public."permission"
("name")
VALUES('seats:usage');
INSERT INTO public."permission"
("name")
VALUES('seats:clear');
INSERT INTO public."permission"
("name")
VALUES('seats:lock');
INSERT INTO public."permission"
("name")
VALUES('seats:history');
INSERT INTO public."permission"
("name")
VALUES('lectures:read');
INSERT INTO public."permission"
("name")
VALUES('feedbackRooms:read');
INSERT INTO public."permission"
("name")
VALUES('feedbackRooms:stages');
INSERT INTO public."permission"
("name")
VALUES('feedbackRooms:create');
INSERT INTO public."permission"
("name")
VALUES('feedbackRooms:update');
INSERT INTO public."permission"
("name")
VALUES('feedbackRooms:delete');
INSERT INTO public."permission"
("name")
VALUES('feedbackRooms:active');
INSERT INTO public."permission"
("name")
VALUES('feedbackCourses:read');
INSERT INTO public."permission"
("name")
VALUES('feedbackUsers:read');
INSERT INTO public."permission"
("name")
VALUES('feedbackCourses:write');
INSERT INTO public."permission"
("name")
VALUES('feedbackCourses:feedback');
INSERT INTO public."permission"
("name")
VALUES('feedbackCourses:vote');
INSERT INTO public."permission"
("name")
VALUES('feedbackCourses:delete');
INSERT INTO public."permission"
("name")
VALUES('feedbackCourses:answer');
INSERT INTO public."permission"
("name")
VALUES('feedbackCourses:update');
INSERT INTO public."permission"
("name")
VALUES('feedbackUsers:write');
INSERT INTO public."permission"
("name")
VALUES('feedbackUsers:feedback');
INSERT INTO public."permission"
("name")
VALUES('feedbackUsers:vote');
INSERT INTO public."permission"
("name")
VALUES('feedbackUsers:delete');
INSERT INTO public."permission"
("name")
VALUES('feedbackUsers:answer');
INSERT INTO public."permission"
("name")
VALUES('feedbackUsers:update');


INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(1, 1);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(1, 3);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(1, 4);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(1, 5);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(1, 6);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(1, 7);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(1, 12);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(1, 14);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(1, 15);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(1, 16);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(1, 17);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(1, 18);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(1, 19);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(1, 20);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(1, 21);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(1, 23);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(1, 29);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(2, 1);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(2, 8);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(2, 12);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(2, 13);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(2, 19);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(2, 20);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(2, 23);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(2, 24);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(2, 21);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(2, 29);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(2, 30);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(3, 1);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(3, 2);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(3, 9);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(3, 10);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(3, 11);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(3, 12);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(3, 19);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(3, 20);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(3, 23);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(3, 26);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(3, 27);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(3, 21);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(3, 29);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(3, 32);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(3, 33);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(4, 22);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(4, 25);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(4, 27);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(4, 28);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(4, 31);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(4, 33);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(5, 19);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(5, 20);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(5, 23);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(5, 25);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(5, 27);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(5, 21);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(5, 29);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(5, 31);
INSERT INTO public.role_permission
(role_id, permission_id)
VALUES(5, 33);


INSERT INTO public.room
(address, building, room_number, "type")
VALUES('Studentų g. 67, Kaunas', 'Bendrabutis (Nr. 2)', '101', 'Kompiuterių klasė');

INSERT INTO public.room
(address, building, room_number, "type")
VALUES('Studentų g. 67, Kaunas', 'Bendrabutis (Nr. 2)', '102', 'Kompiuterių klasė');

INSERT INTO public.room
(address, building, room_number, "type")
VALUES('Studentų g. 67, Kaunas', 'Bendrabutis (Nr. 2)', '103', 'Kompiuterių klasė');

INSERT INTO public.room
(address, building, room_number, "type")
VALUES('Studentų g. 67, Kaunas', 'Bendrabutis (Nr. 2)', '201', 'Laboratorija');

INSERT INTO public.room
(address, building, room_number, "type")
VALUES('Studentų g. 67, Kaunas', 'Bendrabutis (Nr. 2)', '202', 'Laboratorija');

INSERT INTO public.room
(address, building, room_number, "type")
VALUES('Studentų g. 67, Kaunas', 'Bendrabutis (Nr. 2)', '205', 'Kambarys');

INSERT INTO public.room
(address, building, room_number, "type")
VALUES('Studentų g. 67, Kaunas', 'Bendrabutis (Nr. 2)', '305', 'Kompiuterių klasė');

INSERT INTO public.room
(address, building, room_number, "type")
VALUES('Studentų g. 67, Kaunas', 'Bendrabutis (Nr. 2)', '306', 'Laboratorija');


INSERT INTO public.course_usage
(course_id, room_id, start_date, end_date, type_of_session)
VALUES(1, 1, '2021-03-31 13:30', '2021-03-31 15:00', 'Laboratorija');

INSERT INTO public.course_usage
(course_id, room_id, start_date, end_date, type_of_session)
VALUES(2, 1, '2021-04-01 17:30', '2021-04-01 19:00', 'Laboratorija');

INSERT INTO public.course_usage
(course_id, room_id, start_date, end_date, type_of_session)
VALUES(1, 1, '2021-04-01 19:15', '2021-04-01 20:45', 'Laboratorija');

INSERT INTO public.course_usage
(course_id, room_id, start_date, end_date, type_of_session)
VALUES(1, 2, '2021-04-01 12:00', '2021-04-01 19:00', 'Laboratorija');

INSERT INTO public.course_usage
(course_id, room_id, start_date, end_date, type_of_session)
VALUES(2, 3, '2021-04-02 12:00', '2021-04-02 13:30', 'Laboratorija');

INSERT INTO public.course_usage
(course_id, room_id, start_date, end_date, type_of_session)
VALUES(4, 1, '2021-03-25 09:00', '2021-03-25 22:30', 'Laboratorija');

INSERT INTO public.course_usage
(course_id, room_id, start_date, end_date, type_of_session)
VALUES(4, 1, '2021-03-26 09:00', '2021-03-26 22:30', 'Laboratorija');

INSERT INTO public.course_usage
(course_id, room_id, start_date, end_date, type_of_session)
VALUES(4, 1, '2021-03-27 09:00', '2021-03-27 22:30', 'Laboratorija');

INSERT INTO public.course_usage
(course_id, room_id, start_date, end_date, type_of_session)
VALUES(4, 1, '2021-03-28 09:00', '2021-03-28 22:30', 'Laboratorija');

INSERT INTO public.course_usage
(course_id, room_id, start_date, end_date, type_of_session)
VALUES(4, 1, '2021-03-29 09:00', '2021-03-29 22:30', 'Laboratorija');

INSERT INTO public.course_usage
(course_id, room_id, start_date, end_date, type_of_session)
VALUES(1, 1, '2021-04-25 08:00', '2021-05-01 08:00', 'Laboratorija');

INSERT INTO public.course_usage
(course_id, room_id, start_date, end_date, type_of_session)
VALUES(1, 1, '2021-05-01 08:00', '2021-05-08 23:00', 'Laboratorija');

INSERT INTO public.course_usage
(course_id, room_id, start_date, end_date, type_of_session)
VALUES(1, 1, '2021-05-09 08:00', '2021-05-16 23:00', 'Laboratorija');

INSERT INTO public.course_usage
(course_id, room_id, start_date, end_date, type_of_session)
VALUES(1, 1, '2021-05-17 08:00', '2021-05-23 23:00', 'Laboratorija');

INSERT INTO public.course_usage
(course_id, room_id, start_date, end_date, type_of_session)
VALUES(1, 1, '2021-05-24 08:00', '2021-06-01 23:00', 'Laboratorija');

INSERT INTO public.course_usage
(course_id, room_id, start_date, end_date, type_of_session)
VALUES(1, 1, '2021-06-02 08:00', '2021-06-02 23:00', 'Laboratorija');

INSERT INTO public.course_usage
(course_id, room_id, start_date, end_date, type_of_session)
VALUES(1, 1, '2021-06-10 08:00', '2021-06-17 23:00', 'Laboratorija');




INSERT INTO public.user_course_usage
(user_id, course_usage_id)
VALUES(2, 11);

INSERT INTO public.user_course_usage
(user_id, course_usage_id)
VALUES(2, 12);

INSERT INTO public.user_course_usage
(user_id, course_usage_id)
VALUES(2, 13);

INSERT INTO public.user_course_usage
(user_id, course_usage_id)
VALUES(2, 14);

INSERT INTO public.user_course_usage
(user_id, course_usage_id)
VALUES(2, 15);

INSERT INTO public.user_course_usage
(user_id, course_usage_id)
VALUES(2, 16);

INSERT INTO public.user_course_usage
(user_id, course_usage_id)
VALUES(2, 17);



INSERT INTO public.user_course_usage
(user_id, course_usage_id)
VALUES(8, 11);

INSERT INTO public.user_course_usage
(user_id, course_usage_id)
VALUES(8, 12);

INSERT INTO public.user_course_usage
(user_id, course_usage_id)
VALUES(8, 13);

INSERT INTO public.user_course_usage
(user_id, course_usage_id)
VALUES(8, 14);

INSERT INTO public.user_course_usage
(user_id, course_usage_id)
VALUES(8, 15);

INSERT INTO public.user_course_usage
(user_id, course_usage_id)
VALUES(8, 16);

INSERT INTO public.user_course_usage
(user_id, course_usage_id)
VALUES(8, 17);


INSERT INTO public.seat
(room_id, "name", code, is_locked, is_deleted)
VALUES(1, '101 - 1', 'fe99bba5-edf6-4db4-be58-6629915f804e', false, false);
INSERT INTO public.seat
(room_id, "name", code, is_locked, is_deleted)
VALUES(1, '101 - 2', 'd17e6c71-13cd-4464-bd74-d551be72c271', false, false);
INSERT INTO public.seat
(room_id, "name", code, is_locked, is_deleted)
VALUES(1, '101 - 3', '09770a69-3b8e-4a72-8b93-150691afa8f2', false, false);
INSERT INTO public.seat
(room_id, "name", code, is_locked, is_deleted)
VALUES(1, '101 - 4', '4b5ab74b-0809-494b-95b5-0eadeeda2680', false, false);
INSERT INTO public.seat
(room_id, "name", code, is_locked, is_deleted)
VALUES(1, '101 - 5', 'd76ef7e9-4344-4939-925a-46d1e9519c82', false, false);
INSERT INTO public.seat
(room_id, "name", code, is_locked, is_deleted)
VALUES(1, '101 - 6', 'a6277644-0556-4e6d-a9c6-b5fe426dc276', false, false);
INSERT INTO public.seat
(room_id, "name", code, is_locked, is_deleted)
VALUES(1, '101 - 7', '2ef10e87-acd9-400d-b977-19fa3414dec3', false, false);
INSERT INTO public.seat
(room_id, "name", code, is_locked, is_deleted)
VALUES(1, '101 - 8', '65d03ce9-7e11-4ddd-ab6d-fbbd84b2bec7', false, false);
INSERT INTO public.seat
(room_id, "name", code, is_locked, is_deleted)
VALUES(1, '101 - 9', '17c2e134-e490-48e0-95a4-e6d79f8cc5cd', false, false);
INSERT INTO public.seat
(room_id, "name", code, is_locked, is_deleted)
VALUES(2, '102 - 1', 'ee1692b7-55c6-4596-b53b-94fdd1275538', false, false);
INSERT INTO public.seat
(room_id, "name", code, is_locked, is_deleted)
VALUES(2, '102 - 2', '99029faa-8021-4e6e-98d3-ff2afc78ca19', false, false);
INSERT INTO public.seat
(room_id, "name", code, is_locked, is_deleted)
VALUES(2, '102 - 3', 'a6d59349-b9a5-4680-b268-c1b8b61e20e6', false, false);
INSERT INTO public.seat
(room_id, "name", code, is_locked, is_deleted)
VALUES(2, '102 - 4', '136d7295-f12f-4edf-b022-2e61a9ac73a4', false, false);
INSERT INTO public.seat
(room_id, "name", code, is_locked, is_deleted)
VALUES(2, '102 - 5', '183762ac-8c0f-4d4c-a151-7535bade64e2', false, false);
INSERT INTO public.seat
(room_id, "name", code, is_locked, is_deleted)
VALUES(2, '102 - 6', '7a5298f7-8ac6-48fd-89ae-ebe9e9d7b805', false, false);
INSERT INTO public.seat
(room_id, "name", code, is_locked, is_deleted)
VALUES(2, '102 - 7', 'cb75b88f-68fa-4a22-bff4-216360c5cb4d', false, false);
INSERT INTO public.seat
(room_id, "name", code, is_locked, is_deleted)
VALUES(2, '102 - 8', 'e8482356-f6c9-4286-8243-474ad40e3875', false, false);
INSERT INTO public.seat
(room_id, "name", code, is_locked, is_deleted)
VALUES(2, '102 - 9', 'e77cf2e3-15aa-4bfb-9a6c-629615f6b55a', false, false);
INSERT INTO public.seat
(room_id, "name", code, is_locked, is_deleted)
VALUES(3, '103 - 1', '42963fb0-f22b-45b2-a86a-b50cbe6b5d39', false, false);
INSERT INTO public.seat
(room_id, "name", code, is_locked, is_deleted)
VALUES(3, '103 - 3', '15d1ca86-ab6b-422f-b06c-271d5a069411', false, false);
INSERT INTO public.seat
(room_id, "name", code, is_locked, is_deleted)
VALUES(3, '103 - 4', 'f1a02540-c08d-4016-b3b6-2d8e8cfeee02', false, false);
INSERT INTO public.seat
(room_id, "name", code, is_locked, is_deleted)
VALUES(3, '103 - 5', 'c25ca8a8-d078-4e29-a70e-0638bedcbc1e', false, false);
INSERT INTO public.seat
(room_id, "name", code, is_locked, is_deleted)
VALUES(3, '103 - 6', '60c85afb-dfc5-40af-87c1-a94960040c60', false, false);
INSERT INTO public.seat
(room_id, "name", code, is_locked, is_deleted)
VALUES(3, '103 - 7', '4ad06667-4e7a-4b79-9bd0-667e2f1722d3', false, false);
INSERT INTO public.seat
(room_id, "name", code, is_locked, is_deleted)
VALUES(3, '103 - 8', '577cf135-4963-45be-a7c0-0ffcc0585666', false, false);
INSERT INTO public.seat
(room_id, "name", code, is_locked, is_deleted)
VALUES(3, '103 - 9', 'f0e9b158-4b95-43ec-8b76-d54bd28f2845', false, false);
INSERT INTO public.seat
(room_id, "name", code, is_locked, is_deleted)
VALUES(3, '103 - 2', '0181ccae-51b4-49a2-b308-1d3533dc0064', false, false);



INSERT INTO public.feedback_room
(start_date, end_date, is_deleted)
VALUES('2021-04-18 08:00:00.000', '2021-05-05 08:00:00.000', false);


INSERT INTO public.stage
(feedback_room_id, "name", start_date, end_date)
VALUES(1, 'Rašyti', '2021-04-18 08:00:00.000', '2021-04-21 08:00:00.000');
INSERT INTO public.stage
(feedback_room_id, "name", start_date, end_date)
VALUES(1, 'Modifikuoti', '2021-04-21 08:00:00.000', '2021-04-24 08:00:00.000');
INSERT INTO public.stage
(feedback_room_id, "name", start_date, end_date)
VALUES(1, 'Vertinti', '2021-04-24 08:00:00.000', '2021-04-28 08:00:00.000');
INSERT INTO public.stage
(feedback_room_id, "name", start_date, end_date)
VALUES(1, 'Atsakyti', '2021-04-28 08:00:00.000', '2021-04-30 08:00:00.000');


INSERT INTO public.course_feedback_room
(course_id, feedback_room_id)
VALUES(1, 1);
INSERT INTO public.course_feedback_room
(course_id, feedback_room_id)
VALUES(4, 1);
INSERT INTO public.course_feedback_room
(course_id, feedback_room_id)
VALUES(2, 1);


INSERT INTO public.user_feedback_room_visibility
(user_id, feedback_room_id)
VALUES(5, 1);
INSERT INTO public.user_feedback_room_visibility
(user_id, feedback_room_id)
VALUES(1, 1);
INSERT INTO public.user_feedback_room_visibility
(user_id, feedback_room_id)
VALUES(3, 1);
INSERT INTO public.user_feedback_room_visibility
(user_id, feedback_room_id)
VALUES(4, 1);
INSERT INTO public.user_feedback_room_visibility
(user_id, feedback_room_id)
VALUES(2, 1);

INSERT INTO public.course_usage
(course_id, room_id, start_date, end_date, type_of_session)
VALUES(1, 1, '2021-04-25 09:00:00', '2021-04-25 10:30:00', 'Laboratorija');
INSERT INTO public.course_usage
(course_id, room_id, start_date, end_date, type_of_session)
VALUES(1, 1, '2021-05-01 09:00:00', '2021-05-01 10:30:00', 'Laboratorija');


INSERT INTO public.seat_usage
(course_usage_id, seat_id, user_id, start_date, end_date)
VALUES(11, 4, 2, '2021-05-01 14:37:40', '2021-05-10 12:59:00');
INSERT INTO public.seat_usage
(course_usage_id, seat_id, user_id, start_date, end_date)
VALUES(18, 4, 2, '2021-05-01 14:37:40', '2021-05-01 14:50:48');

INSERT INTO public.course_usage
(course_id, room_id, start_date, end_date, type_of_session)
VALUES(3, 1, '2021-03-31 13:30:00', '2021-03-31 15:00:00', 'Laboratorija');

INSERT INTO public.course_feedback_room
(course_id, feedback_room_id)
VALUES(3, 1);

INSERT INTO public.user_course_responsibility
(course_id, user_id, is_head, is_direct_head)
VALUES(9, 3, false, false);
INSERT INTO public.user_course_responsibility
(course_id, user_id, is_head, is_direct_head)
VALUES(10, 3, false, false);
INSERT INTO public.user_course_responsibility
(course_id, user_id, is_head, is_direct_head)
VALUES(11, 3, false, false);
INSERT INTO public.user_course_responsibility
(course_id, user_id, is_head, is_direct_head)
VALUES(12, 3, false, false);
INSERT INTO public.user_course_responsibility
(course_id, user_id, is_head, is_direct_head)
VALUES(13, 3, false, false);
INSERT INTO public.user_course_responsibility
(course_id, user_id, is_head, is_direct_head)
VALUES(14, 3, false, false);


INSERT INTO public.course_usage
(course_id, room_id, start_date, end_date, type_of_session)
VALUES(9, 1, '2021-05-01 09:00:00', '2021-05-01 10:30:00', 'Laboratorija');
INSERT INTO public.course_usage
(course_id, room_id, start_date, end_date, type_of_session)
VALUES(10, 1, '2021-05-01 10:30:00', '2021-05-01 12:00:00', 'Laboratorija');
INSERT INTO public.course_usage
(course_id, room_id, start_date, end_date, type_of_session)
VALUES(11, 1, '2021-05-01 13:30:00', '2021-05-01 15:00:00', 'Laboratorija');
INSERT INTO public.course_usage
(course_id, room_id, start_date, end_date, type_of_session)
VALUES(12, 1, '2021-05-02 09:00:00', '2021-05-02 10:30:00', 'Laboratorija');
INSERT INTO public.course_usage
(course_id, room_id, start_date, end_date, type_of_session)
VALUES(13, 1, '2021-05-02 10:30:00', '2021-05-02 12:00:00', 'Laboratorija');
INSERT INTO public.course_usage
(course_id, room_id, start_date, end_date, type_of_session)
VALUES(14, 1, '2021-05-02 13:30:00', '2021-05-02 15:00:00', 'Laboratorija');
INSERT INTO public.course_usage
(course_id, room_id, start_date, end_date, type_of_session)
VALUES(9, 1, '2021-05-03 09:00:00', '2021-05-03 10:30:00', 'Laboratorija');
INSERT INTO public.course_usage
(course_id, room_id, start_date, end_date, type_of_session)
VALUES(10, 1, '2021-05-03 10:30:00', '2021-05-03 12:00:00', 'Laboratorija');
INSERT INTO public.course_usage
(course_id, room_id, start_date, end_date, type_of_session)
VALUES(11, 1, '2021-05-03 13:30:00', '2021-05-03 15:00:00', 'Laboratorija');



