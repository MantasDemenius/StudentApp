INSERT INTO public.feedback_room
(start_date, end_date, is_deleted)
VALUES('2021-05-11 08:00:00', '2021-06-30 08:00:00', false);

INSERT INTO public.stage
(feedback_room_id, "name", start_date, end_date)
VALUES(2, 'Rašyti', '2021-05-11 08:00:00', '2021-06-30 08:00:00');
INSERT INTO public.stage
(feedback_room_id, "name", start_date, end_date)
VALUES(2, 'Modifikuoti', '2021-05-11 08:00:00', '2021-06-30 08:00:00');
INSERT INTO public.stage
(feedback_room_id, "name", start_date, end_date)
VALUES(2, 'Vertinti', '2021-05-11 08:00:00', '2021-06-30 08:00:00');
INSERT INTO public.stage
(feedback_room_id, "name", start_date, end_date)
VALUES(2, 'Atsakyti', '2021-05-11 08:00:00', '2021-06-30 08:00:00');


INSERT INTO public.user_feedback_room_visibility
(user_id, feedback_room_id)
VALUES(17, 2);
INSERT INTO public.user_feedback_room_visibility
(user_id, feedback_room_id)
VALUES(21, 2);
INSERT INTO public.user_feedback_room_visibility
(user_id, feedback_room_id)
VALUES(2, 2);
INSERT INTO public.user_feedback_room_visibility
(user_id, feedback_room_id)
VALUES(19, 2);
INSERT INTO public.user_feedback_room_visibility
(user_id, feedback_room_id)
VALUES(15, 2);
INSERT INTO public.user_feedback_room_visibility
(user_id, feedback_room_id)
VALUES(4, 2);
INSERT INTO public.user_feedback_room_visibility
(user_id, feedback_room_id)
VALUES(13, 2);
INSERT INTO public.user_feedback_room_visibility
(user_id, feedback_room_id)
VALUES(11, 2);
INSERT INTO public.user_feedback_room_visibility
(user_id, feedback_room_id)
VALUES(9, 2);
INSERT INTO public.user_feedback_room_visibility
(user_id, feedback_room_id)
VALUES(6, 2);
INSERT INTO public.user_feedback_room_visibility
(user_id, feedback_room_id)
VALUES(1, 2);
INSERT INTO public.user_feedback_room_visibility
(user_id, feedback_room_id)
VALUES(22, 2);
INSERT INTO public.user_feedback_room_visibility
(user_id, feedback_room_id)
VALUES(16, 2);
INSERT INTO public.user_feedback_room_visibility
(user_id, feedback_room_id)
VALUES(20, 2);
INSERT INTO public.user_feedback_room_visibility
(user_id, feedback_room_id)
VALUES(7, 2);
INSERT INTO public.user_feedback_room_visibility
(user_id, feedback_room_id)
VALUES(18, 2);
INSERT INTO public.user_feedback_room_visibility
(user_id, feedback_room_id)
VALUES(14, 2);
INSERT INTO public.user_feedback_room_visibility
(user_id, feedback_room_id)
VALUES(12, 2);
INSERT INTO public.user_feedback_room_visibility
(user_id, feedback_room_id)
VALUES(10, 2);
INSERT INTO public.user_feedback_room_visibility
(user_id, feedback_room_id)
VALUES(23, 2);
INSERT INTO public.user_feedback_room_visibility
(user_id, feedback_room_id)
VALUES(8, 2);


INSERT INTO public.course_feedback_room
(course_id, feedback_room_id)
VALUES(1, 2);
INSERT INTO public.course_feedback_room
(course_id, feedback_room_id)
VALUES(4, 2);
INSERT INTO public.course_feedback_room
(course_id, feedback_room_id)
VALUES(2, 2);
INSERT INTO public.course_feedback_room
(course_id, feedback_room_id)
VALUES(3, 2);

INSERT INTO public.course_comment
(user_id_answerer, user_id_asker, course_id, "comment", status, answer, feedback_room_id, is_deleted)
VALUES(NULL, 6, 1, 'Man patiko šitas modulis, dėstytojai buvo labai mandagūs.', 'Neatsakytas', NULL, 1, false);
INSERT INTO public.course_comment
(user_id_answerer, user_id_asker, course_id, "comment", status, answer, feedback_room_id, is_deleted)
VALUES(NULL, 6, 1, 'Laboratoriniai darbai vyksta per dažnai, nėra laiko juos paruošti.', 'Neatsakytas', NULL, 1, false);
INSERT INTO public.course_comment
(user_id_answerer, user_id_asker, course_id, "comment", status, answer, feedback_room_id, is_deleted)
VALUES(NULL, 6, 1, 'Šio modulio egzaminai yra per sudėtingi, per daug informacijos išmokti.', 'Neatsakytas', NULL, 1, false);
INSERT INTO public.course_comment
(user_id_answerer, user_id_asker, course_id, "comment", status, answer, feedback_room_id, is_deleted)
VALUES(NULL, 6, 1, 'Dėstytojas vėlavo atvykti į paskaitą 2min.', 'Neatsakytas', NULL, 1, false);
INSERT INTO public.course_comment
(user_id_answerer, user_id_asker, course_id, "comment", status, answer, feedback_room_id, is_deleted)
VALUES(NULL, 6, 1, 'Paskaitoje buvo uždrausta gerti kavą, gal pakeiskime šità taisyklę :)', 'Neatsakytas', NULL, 1, false);
INSERT INTO public.course_comment
(user_id_answerer, user_id_asker, course_id, "comment", status, answer, feedback_room_id, is_deleted)
VALUES(NULL, 6, 1, 'Norėčiau jog dėstytojai aiškiau papasakotų temą.', 'Neatsakytas', NULL, 1, false);
INSERT INTO public.course_comment
(user_id_answerer, user_id_asker, course_id, "comment", status, answer, feedback_room_id, is_deleted)
VALUES(NULL, 6, 1, 'Labai jau senos skaidrės, kaip su 2004 powerpointu padarytos.', 'Neatsakytas', NULL, 1, false);

