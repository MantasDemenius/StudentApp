INSERT INTO public.seat_usage
(course_usage_id, seat_id, user_id, start_date, end_date)
VALUES(21, 1, 10, '2021-05-01 09:05:03', '2021-05-01 10:25:00');

INSERT INTO public.seat_usage
(course_usage_id, seat_id, user_id, start_date, end_date)
VALUES(21, 2, 11, '2021-05-01 09:05:03', '2021-05-01 10:25:00');
INSERT INTO public.seat_usage
(course_usage_id, seat_id, user_id, start_date, end_date)
VALUES(21, 3, 12, '2021-05-01 09:05:03', '2021-05-01 10:25:00');
INSERT INTO public.seat_usage
(course_usage_id, seat_id, user_id, start_date, end_date)
VALUES(21, 4, 13, '2021-05-01 09:05:03', '2021-05-01 10:25:00');
INSERT INTO public.seat_usage
(course_usage_id, seat_id, user_id, start_date, end_date)
VALUES(21, 5, 14, '2021-05-01 09:05:03', '2021-05-01 10:25:00');

INSERT INTO public.seat_usage
(course_usage_id, seat_id, user_id, start_date, end_date)
VALUES(22, 1, 15, '2021-05-01 10:30:03', '2021-05-01 11:58:00');

INSERT INTO public.seat_usage
(course_usage_id, seat_id, user_id, start_date, end_date)
VALUES(27, 1, 10, '2021-05-03 09:05:03', '2021-05-05 10:25:00');
INSERT INTO public.seat_usage
(course_usage_id, seat_id, user_id, start_date, end_date)
VALUES(27, 2, 11, '2021-05-03 09:05:03', '2021-05-03 10:25:00');
INSERT INTO public.seat_usage
(course_usage_id, seat_id, user_id, start_date, end_date)
VALUES(27, 3, 12, '2021-05-03 09:05:03', '2021-05-03 10:25:00');
INSERT INTO public.seat_usage
(course_usage_id, seat_id, user_id, start_date, end_date)
VALUES(27, 4, 13, '2021-05-03 09:05:03', '2021-05-03 10:25:00');

INSERT INTO public.user_course_responsibility
(course_id, user_id, is_head, is_direct_head)
VALUES(9, 9, false, false);
INSERT INTO public.user_course_responsibility
(course_id, user_id, is_head, is_direct_head)
VALUES(10, 9, false, false);
INSERT INTO public.user_course_responsibility
(course_id, user_id, is_head, is_direct_head)
VALUES(11, 9, false, false);
INSERT INTO public.user_course_responsibility
(course_id, user_id, is_head, is_direct_head)
VALUES(12, 9, false, false);
INSERT INTO public.user_course_responsibility
(course_id, user_id, is_head, is_direct_head)
VALUES(13, 9, false, false);
INSERT INTO public.user_course_responsibility
(course_id, user_id, is_head, is_direct_head)
VALUES(14, 9, false, false);


INSERT INTO public.course_usage
(course_id, room_id, start_date, end_date, type_of_session)
VALUES(9, 1, '2021-06-01 09:00:00', '2021-06-01 10:30:00', 'Laboratorija');
INSERT INTO public.course_usage
(course_id, room_id, start_date, end_date, type_of_session)
VALUES(9, 2, '2021-06-03 09:00:00', '2021-06-03 10:30:00', 'Laboratorija');
INSERT INTO public.course_usage
(course_id, room_id, start_date, end_date, type_of_session)
VALUES(10, 3, '2021-06-01 10:30:00', '2021-06-01 12:00:00', 'Laboratorija');
INSERT INTO public.course_usage
(course_id, room_id, start_date, end_date, type_of_session)
VALUES(10, 4, '2021-06-03 10:30:00', '2021-06-03 12:00:00', 'Laboratorija');
INSERT INTO public.course_usage
(course_id, room_id, start_date, end_date, type_of_session)
VALUES(11, 1, '2021-06-03 13:30:00', '2021-06-03 15:00:00', 'Laboratorija');
INSERT INTO public.course_usage
(course_id, room_id, start_date, end_date, type_of_session)
VALUES(11, 2, '2021-06-01 13:30:00', '2021-06-01 15:00:00', 'Laboratorija');
INSERT INTO public.course_usage
(course_id, room_id, start_date, end_date, type_of_session)
VALUES(12, 3, '2021-06-02 09:00:00', '2021-06-02 10:30:00', 'Laboratorija');
INSERT INTO public.course_usage
(course_id, room_id, start_date, end_date, type_of_session)
VALUES(13, 4, '2021-06-02 10:30:00', '2021-06-02 12:00:00', 'Laboratorija');
INSERT INTO public.course_usage
(course_id, room_id, start_date, end_date, type_of_session)
VALUES(14, 2, '2021-06-02 13:30:00', '2021-06-02 15:00:00', 'Laboratorija');

INSERT INTO public.course_usage
(course_id, room_id, start_date, end_date, type_of_session)
VALUES(12, 3, '2021-05-26 09:00:00', '2021-05-26 10:30:00', 'Laboratorija');
INSERT INTO public.course_usage
(course_id, room_id, start_date, end_date, type_of_session)
VALUES(13, 4, '2021-05-25 09:00:00', '2021-05-25 12:00:00', 'Laboratorija');
INSERT INTO public.course_usage
(course_id, room_id, start_date, end_date, type_of_session)
VALUES(14, 2, '2021-05-25 13:30:00', '2021-05-25 15:00:00', 'Laboratorija');


INSERT INTO public.seat
(room_id, "name", code, is_locked, is_deleted)
VALUES(4, '201 - 1', '73f3b382-de35-439b-98c5-f4d54ce539f5', true, false);
INSERT INTO public.seat
(room_id, "name", code, is_locked, is_deleted)
VALUES(4, '201 - 2', '07a47bcc-fcd1-4839-892d-0cd79e8accb2', true, false);
INSERT INTO public.seat
(room_id, "name", code, is_locked, is_deleted)
VALUES(4, '201 - 3', 'a18943a2-c0e8-40e7-99d8-d18ab4c0ad81', false, false);
INSERT INTO public.seat
(room_id, "name", code, is_locked, is_deleted)
VALUES(4, '201 - 4', '0cd0d951-6b93-4566-ada1-214fbb8a9fb3', true, false);
INSERT INTO public.seat
(room_id, "name", code, is_locked, is_deleted)
VALUES(4, '201 - 5', '064741af-4039-48ce-9ecb-5c61b4331cd3', false, false);




INSERT INTO public.course_feedback_room
(course_id, feedback_room_id)
VALUES(9, 2);
INSERT INTO public.course_feedback_room
(course_id, feedback_room_id)
VALUES(10, 2);
INSERT INTO public.course_feedback_room
(course_id, feedback_room_id)
VALUES(11, 2);
INSERT INTO public.course_feedback_room
(course_id, feedback_room_id)
VALUES(12, 2);
INSERT INTO public.course_feedback_room
(course_id, feedback_room_id)
VALUES(13, 2);
INSERT INTO public.course_feedback_room
(course_id, feedback_room_id)
VALUES(14, 2);


INSERT INTO public.course_comment
(user_id_answerer, user_id_asker, course_id, "comment", status, answer, feedback_room_id, is_deleted)
VALUES(NULL, 6, 10, 'Man patiko šitas modulis, dėstytojai buvo labai mandagūs.', 'Neatsakytas', NULL, 2, false);
INSERT INTO public.course_comment
(user_id_answerer, user_id_asker, course_id, "comment", status, answer, feedback_room_id, is_deleted)
VALUES(NULL, 6, 10, 'Laboratoriniai darbai vyksta per dažnai, nėra laiko juos paruošti.', 'Neatsakytas', NULL, 2, false);
INSERT INTO public.course_comment
(user_id_answerer, user_id_asker, course_id, "comment", status, answer, feedback_room_id, is_deleted)
VALUES(NULL, 6, 10, 'Šio modulio egzaminai yra per sudėtingi, per daug informacijos išmokti.', 'Neatsakytas', NULL, 2, false);
INSERT INTO public.course_comment
(user_id_answerer, user_id_asker, course_id, "comment", status, answer, feedback_room_id, is_deleted)
VALUES(NULL, 6, 10, 'Dėstytojas vėlavo atvykti į paskaitą 2min.', 'Neatsakytas', NULL, 2, false);
INSERT INTO public.course_comment
(user_id_answerer, user_id_asker, course_id, "comment", status, answer, feedback_room_id, is_deleted)
VALUES(NULL, 6, 10, 'Paskaitoje buvo uždrausta gerti kavą, gal pakeiskime šitą taisyklę :)', 'Neatsakytas', NULL, 2, false);
INSERT INTO public.course_comment
(user_id_answerer, user_id_asker, course_id, "comment", status, answer, feedback_room_id, is_deleted)
VALUES(NULL, 6, 10, 'Norėčiau jog dėstytojai aiškiau papasakotų temą.', 'Neatsakytas', NULL, 2, false);
INSERT INTO public.course_comment
(user_id_answerer, user_id_asker, course_id, "comment", status, answer, feedback_room_id, is_deleted)
VALUES(NULL, 6, 10, 'Labai jau senos skaidrės, kaip su 2004 powerpointu padarytos.', 'Neatsakytas', NULL, 2, false);

INSERT INTO public.course_comment
(user_id_answerer, user_id_asker, course_id, "comment", status, answer, feedback_room_id, is_deleted)
VALUES(NULL, 6, 9, 'Man patiko šitas modulis, dėstytojai buvo labai mandagūs.', 'Neatsakytas', NULL, 2, false);
INSERT INTO public.course_comment
(user_id_answerer, user_id_asker, course_id, "comment", status, answer, feedback_room_id, is_deleted)
VALUES(NULL, 6, 9, 'Laboratoriniai darbai vyksta per dažnai, nėra laiko juos paruošti.', 'Neatsakytas', NULL, 2, false);
INSERT INTO public.course_comment
(user_id_answerer, user_id_asker, course_id, "comment", status, answer, feedback_room_id, is_deleted)
VALUES(NULL, 6, 9, 'Šio modulio egzaminai yra per sudėtingi, per daug informacijos išmokti.', 'Neatsakytas', NULL, 2, false);


INSERT INTO public.course_comment
(user_id_answerer, user_id_asker, course_id, "comment", status, answer, feedback_room_id, is_deleted)
VALUES(NULL, 6, 11, 'Dėstytojas vėlavo atvykti į paskaitą 2min.', 'Neatsakytas', NULL, 2, false);
INSERT INTO public.course_comment
(user_id_answerer, user_id_asker, course_id, "comment", status, answer, feedback_room_id, is_deleted)
VALUES(NULL, 6, 11, 'Paskaitoje buvo uždrausta gerti kavą, gal pakeiskime šitą taisyklę :)', 'Neatsakytas', NULL, 2, false);
INSERT INTO public.course_comment
(user_id_answerer, user_id_asker, course_id, "comment", status, answer, feedback_room_id, is_deleted)
VALUES(NULL, 6, 11, 'Norėčiau jog dėstytojai aiškiau papasakotų temą.', 'Neatsakytas', NULL, 2, false);
INSERT INTO public.course_comment
(user_id_answerer, user_id_asker, course_id, "comment", status, answer, feedback_room_id, is_deleted)
VALUES(NULL, 6, 11, 'Labai jau senos skaidrės, kaip su 2004 powerpointu padarytos.', 'Neatsakytas', NULL, 2, false);

INSERT INTO public.course_comment
(user_id_answerer, user_id_asker, course_id, "comment", status, answer, feedback_room_id, is_deleted)
VALUES(NULL, 6, 12, 'Dėstytojas vėlavo atvykti į paskaitą 2min.', 'Neatsakytas', NULL, 2, false);
INSERT INTO public.course_comment
(user_id_answerer, user_id_asker, course_id, "comment", status, answer, feedback_room_id, is_deleted)
VALUES(NULL, 6, 13, 'Paskaitoje buvo uždrausta gerti kavą, gal pakeiskime šitą taisyklę :)', 'Neatsakytas', NULL, 2, false);
INSERT INTO public.course_comment
(user_id_answerer, user_id_asker, course_id, "comment", status, answer, feedback_room_id, is_deleted)
VALUES(NULL, 6, 13, 'Norėčiau jog dėstytojai aiškiau papasakotų temą.', 'Neatsakytas', NULL, 2, false);


INSERT INTO public.user_comment
(user_id_subject, user_id_asker, user_id_answerer, "comment", status, answer, feedback_room_id, is_deleted)
VALUES(3, 6, NULL, 'Paulius labai aiškiai išdėstė programų sistemų testavimo paskaitą.', 'Neatsakytas', NULL, 2, false);
INSERT INTO public.user_comment
(user_id_subject, user_id_asker, user_id_answerer, "comment", status, answer, feedback_room_id, is_deleted)
VALUES(3, 6, NULL, 'Dėstytojas labai įdomiai pasakojo, nes šnekėjo iš patirties.', 'Neatsakytas', NULL, 2, false);
INSERT INTO public.user_comment
(user_id_subject, user_id_asker, user_id_answerer, "comment", status, answer, feedback_room_id, is_deleted)
VALUES(3, 6, NULL, 'Noriu pagirti dėstytoją kadangi sugebėjo 1.5 valandos paskaitą išdėstyti per 30 minučių, todėl galėjome išeiti ankščiau namo.', 'Neatsakytas', NULL, 2, false);
INSERT INTO public.user_comment
(user_id_subject, user_id_asker, user_id_answerer, "comment", status, answer, feedback_room_id, is_deleted)
VALUES(3, 6, NULL, 'Kodėl man rašote tik neigiamus pažymius?', 'Neatsakytas', NULL, 2, false);


INSERT INTO public.course_comment_vote
(course_comment_id, user_id, vote)
VALUES(11, 10, 1);
INSERT INTO public.course_comment_vote
(course_comment_id, user_id, vote)
VALUES(11, 11, 1);
INSERT INTO public.course_comment_vote
(course_comment_id, user_id, vote)
VALUES(11, 12, 1);
INSERT INTO public.course_comment_vote
(course_comment_id, user_id, vote)
VALUES(11, 13, 1);
INSERT INTO public.course_comment_vote
(course_comment_id, user_id, vote)
VALUES(11, 14, 1);
INSERT INTO public.course_comment_vote
(course_comment_id, user_id, vote)
VALUES(14, 11, -5);
INSERT INTO public.course_comment_vote
(course_comment_id, user_id, vote)
VALUES(14, 12, -5);
INSERT INTO public.course_comment_vote
(course_comment_id, user_id, vote)
VALUES(11, 13, -5);
INSERT INTO public.course_comment_vote
(course_comment_id, user_id, vote)
VALUES(14, 14, -5);
INSERT INTO public.course_comment_vote
(course_comment_id, user_id, vote)
VALUES(11, 15, 1);
INSERT INTO public.course_comment_vote
(course_comment_id, user_id, vote)
VALUES(12, 14, 1);
INSERT INTO public.course_comment_vote
(course_comment_id, user_id, vote)
VALUES(12, 15, 1);
INSERT INTO public.course_comment_vote
(course_comment_id, user_id, vote)
VALUES(13, 14, -1);
INSERT INTO public.course_comment_vote
(course_comment_id, user_id, vote)
VALUES(13, 15, -1);

INSERT INTO public.course_comment_vote
(course_comment_id, user_id, vote)
VALUES(11, 10, 1);
INSERT INTO public.course_comment_vote
(course_comment_id, user_id, vote)
VALUES(11, 11, 1);
INSERT INTO public.course_comment_vote
(course_comment_id, user_id, vote)
VALUES(11, 12, 1);
INSERT INTO public.course_comment_vote
(course_comment_id, user_id, vote)
VALUES(11, 13, 1);
INSERT INTO public.course_comment_vote
(course_comment_id, user_id, vote)
VALUES(11, 14, 1);
INSERT INTO public.course_comment_vote
(course_comment_id, user_id, vote)
VALUES(14, 11, -5);
INSERT INTO public.course_comment_vote
(course_comment_id, user_id, vote)
VALUES(14, 12, -5);
INSERT INTO public.course_comment_vote
(course_comment_id, user_id, vote)
VALUES(11, 13, -5);
INSERT INTO public.course_comment_vote
(course_comment_id, user_id, vote)
VALUES(14, 14, -5);
INSERT INTO public.course_comment_vote
(course_comment_id, user_id, vote)
VALUES(11, 15, 1);
INSERT INTO public.course_comment_vote
(course_comment_id, user_id, vote)
VALUES(12, 14, 1);
INSERT INTO public.course_comment_vote
(course_comment_id, user_id, vote)
VALUES(12, 15, 1);
INSERT INTO public.course_comment_vote
(course_comment_id, user_id, vote)
VALUES(13, 14, -1);
INSERT INTO public.course_comment_vote
(course_comment_id, user_id, vote)
VALUES(13, 15, -1);


INSERT INTO public.user_comment_vote
(user_comment_id, user_id, vote)
VALUES(1, 10, 1);
INSERT INTO public.user_comment_vote
(user_comment_id, user_id, vote)
VALUES(1, 11, 1);
INSERT INTO public.user_comment_vote
(user_comment_id, user_id, vote)
VALUES(1, 12, 1);
INSERT INTO public.user_comment_vote
(user_comment_id, user_id, vote)
VALUES(1, 13, 1);
INSERT INTO public.user_comment_vote
(user_comment_id, user_id, vote)
VALUES(2, 10, 1);
INSERT INTO public.user_comment_vote
(user_comment_id, user_id, vote)
VALUES(2, 11, 0);
INSERT INTO public.user_comment_vote
(user_comment_id, user_id, vote)
VALUES(1, 12, 0);
INSERT INTO public.user_comment_vote
(user_comment_id, user_id, vote)
VALUES(1, 13, 1);
INSERT INTO public.user_comment_vote
(user_comment_id, user_id, vote)
VALUES(3, 10, 5);
INSERT INTO public.user_comment_vote
(user_comment_id, user_id, vote)
VALUES(3, 11, 5);
INSERT INTO public.user_comment_vote
(user_comment_id, user_id, vote)
VALUES(3, 12, 5);
INSERT INTO public.user_comment_vote
(user_comment_id, user_id, vote)
VALUES(3, 13, 5);
INSERT INTO public.user_comment_vote
(user_comment_id, user_id, vote)
VALUES(4, 10, -2);
INSERT INTO public.user_comment_vote
(user_comment_id, user_id, vote)
VALUES(4, 11, -3);
INSERT INTO public.user_comment_vote
(user_comment_id, user_id, vote)
VALUES(4, 12, 0);
INSERT INTO public.user_comment_vote
(user_comment_id, user_id, vote)
VALUES(4, 13, -1);


UPDATE public.course_comment SET user_id_answerer=3, user_id_asker=6, course_id=10, "comment"='Man patiko šitas modulis, dėstytojai buvo labai mandagūs.', status='Sutinka - šį semestrą', answer='Ačiū, stengiamės.', feedback_room_id=2, is_deleted=false WHERE id=11;
UPDATE public.course_comment SET user_id_answerer=3, user_id_asker=6, course_id=10, "comment"='Laboratoriniai darbai vyksta per dažnai, nėra laiko juos paruošti.', status='Dalinai sutinka - kitą semestrą', answer='Suprantame jog darbo yra daug, tačiau 5 laboratoriniai darbai yra puikus kiekis išmokti viską.', feedback_room_id=2, is_deleted=false WHERE id=12;


INSERT INTO public.course_usage (course_id, room_id, start_date, end_date, type_of_session) VALUES(9, 1, '2021-06-04 09:00:00', '2021-06-04 10:30:00', 'Laboratorija');
INSERT INTO public.course_usage (course_id, room_id, start_date, end_date, type_of_session) VALUES(10, 4, '2021-06-04 11:00:00', '2021-06-04 12:00:00', 'Laboratorija');
INSERT INTO public.course_usage (course_id, room_id, start_date, end_date, type_of_session) VALUES(11, 2, '2021-06-04 13:30:00', '2021-06-04 15:00:00', 'Laboratorija');

INSERT INTO public.course_usage (course_id, room_id, start_date, end_date, type_of_session) VALUES(9, 2, '2021-06-07 09:00:00', '2021-06-07 10:30:00', 'Laboratorija');
INSERT INTO public.course_usage (course_id, room_id, start_date, end_date, type_of_session) VALUES(10, 3, '2021-06-07 11:00:00', '2021-06-07 12:00:00', 'Laboratorija');
INSERT INTO public.course_usage (course_id, room_id, start_date, end_date, type_of_session) VALUES(11, 1, '2021-06-07 13:30:00', '2021-06-07 15:00:00', 'Laboratorija');

INSERT INTO public.course_usage (course_id, room_id, start_date, end_date, type_of_session) VALUES(11, 1, '2021-06-05 08:00:00', '2021-06-07 09:00:00', 'Laboratorija');

INSERT INTO public.course_usage (course_id, room_id, start_date, end_date, type_of_session) VALUES(13, 4, '2021-06-08 11:00:00', '2021-06-08 12:30:00', 'Laboratorija');
INSERT INTO public.course_usage (course_id, room_id, start_date, end_date, type_of_session) VALUES(14, 2, '2021-06-08 13:30:00', '2021-06-08 15:00:00', 'Laboratorija');
INSERT INTO public.course_usage (course_id, room_id, start_date, end_date, type_of_session) VALUES(12, 3, '2021-06-09 09:00:00', '2021-06-09 10:30:00', 'Laboratorija');
INSERT INTO public.course_usage (course_id, room_id, start_date, end_date, type_of_session) VALUES(13, 4, '2021-06-14 11:00:00', '2021-06-14 12:30:00', 'Laboratorija');
INSERT INTO public.course_usage (course_id, room_id, start_date, end_date, type_of_session) VALUES(14, 2, '2021-06-14 13:30:00', '2021-05-25 15:00:00', 'Laboratorija');


INSERT INTO public.seat_usage
(course_usage_id, seat_id, user_id, start_date, end_date)
VALUES(47, 1, 14, '2021-06-07 13:35:09', null);
INSERT INTO public.seat_usage
(course_usage_id, seat_id, user_id, start_date, end_date)
VALUES(47, 2, 15, '2021-06-07 13:30:09', null);

UPDATE public.seat SET room_id=1, "name"='101 - 1', code='fe99bba5-edf6-4db4-be58-6629915f804e', is_locked=true, is_deleted=false WHERE id=1;
UPDATE public.seat SET room_id=1, "name"='101 - 2', code='d17e6c71-13cd-4464-bd74-d551be72c271', is_locked=true, is_deleted=false WHERE id=2;

INSERT INTO public.user_course_responsibility (course_id, user_id, is_head, is_direct_head) VALUES(14, 4, false, false);
