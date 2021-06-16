CREATE TABLE "course"
(
	id integer generated always as identity PRIMARY KEY,
	code text not null,
	title text not null,
	language text not null
);

CREATE TABLE "permission"
(
	id integer generated always as identity PRIMARY KEY,
	"name" text not null
);

CREATE TABLE "role"
(
	id integer generated always as identity PRIMARY KEY,
	"name" text not null
);

CREATE TABLE "room"
(
	id integer generated always as identity PRIMARY KEY,
	address text not null,
	building text not null,
	room_number text not null,
	type text not null
);

CREATE TABLE "feedback_room"
(
	id integer generated always as identity PRIMARY KEY,
  start_date TIMESTAMP not null,
	end_date TIMESTAMP not null,
	is_deleted boolean not null
);


CREATE TABLE "user"
(
	id integer generated always as identity PRIMARY KEY,
	username text not null,
	"name" text not null,
	surname text not null,
	code text not null,
	"password" text not null,
	picture_path text null,
	user_type text not null
);

CREATE TABLE "course_feedback_room"
(
	course_id integer not null,
	feedback_room_id integer not null,
	CONSTRAINT fk_course_feedback_room_course FOREIGN KEY(course_id) REFERENCES course (id),
	CONSTRAINT fk_course_feedback_room_feedback_room FOREIGN KEY(feedback_room_id) REFERENCES feedback_room (id)
);

CREATE TABLE "course_usage"
(
	id integer generated always as identity PRIMARY KEY,
	course_id integer not null,
	room_id integer not null,
	start_date TIMESTAMP not null,
	end_date TIMESTAMP,
	type_of_session text not null,
	CONSTRAINT fk_course_usage_course FOREIGN KEY(course_id) REFERENCES course (id),
	CONSTRAINT fk_course_usage_room FOREIGN KEY(room_id) REFERENCES room (id)
);

CREATE TABLE "email"
(
	id integer generated always as identity PRIMARY KEY,
	user_id integer not null,
	email text not null,
	is_main boolean,
	constraint fk_email_user FOREIGN KEY(user_id) REFERENCES "user" (id)
);

CREATE TABLE "role_permission"
(
	role_id integer not null,
	permission_id integer not null,
	CONSTRAINT fk_role_permission_permission FOREIGN KEY(permission_id) REFERENCES permission (id),
	constraint fk_role_permission_role FOREIGN key (role_id) REFERENCES role (id)
);

CREATE TABLE "seat"
(
	id integer generated always as identity PRIMARY KEY,
	room_id integer not null,
	"name" text not null,
	code text not null,
	is_locked boolean not null,
	is_deleted boolean not null,
	constraint fk_seat_room FOREIGN KEY(room_id) REFERENCES room (id)
);

CREATE TABLE "user_course_responsibility"
(
	id integer generated always as identity PRIMARY KEY,
	course_id integer not null,
	user_id integer not null,
	is_head boolean,
	is_direct_head boolean,
	constraint fk_user_course_responsibility_course FOREIGN KEY(course_id) REFERENCES course (id),
	constraint fk_user_course_responsibility_user FOREIGN KEY(user_id) REFERENCES "user" (id)
);

CREATE TABLE "stage"
(
	id integer generated always as identity PRIMARY KEY,
	feedback_room_id integer not null,
	"name" text not null,
	start_date TIMESTAMP not null,
	end_date TIMESTAMP,
	constraint fk_stage_feedback_room FOREIGN KEY(feedback_room_id) REFERENCES feedback_room (id)
);

CREATE TABLE "user_feedback_room_visibility"
(
	user_id integer not null,
	feedback_room_id integer not null,
	CONSTRAINT fk_user_feedback_room_visibility_user FOREIGN KEY(user_id) REFERENCES "user" (id),
	CONSTRAINT fk_user_feedback_room_visibility_rfeedback_room FOREIGN KEY(feedback_room_id) REFERENCES feedback_room (id)
);

CREATE TABLE "course_comment"
(
	id integer generated always as identity PRIMARY KEY,
	user_id_answerer integer,
	user_id_asker integer not null,
	course_id integer not null,
	"comment" text not null,
	status text not null,
	answer text,
	constraint fk_course_comment_user_answerer FOREIGN KEY(user_id_answerer) REFERENCES "user" (id),
	CONSTRAINT fk_course_comment_user_asker FOREIGN KEY(user_id_asker) REFERENCES "user" (id),
	constraint fk_course_comment_course FOREIGN KEY(course_id) REFERENCES course (id)
);

CREATE TABLE "user_course_visibility"
(
	user_id integer not null,
	course_id integer not null,
	constraint fk_user_course_visibility_course FOREIGN KEY(course_id) REFERENCES course (id),
	constraint fk_user_course_visibility_user FOREIGN KEY(user_id) REFERENCES "user" (id)
);

CREATE TABLE "user_course_usage"
(
	user_id integer not null,
	course_usage_id integer not null,
	constraint fk_user_course_usage_course_usage FOREIGN KEY(course_usage_id) REFERENCES course_usage (id),
	constraint fk_user_course_usage_user FOREIGN KEY(user_id) REFERENCES "user" (id)
);

CREATE TABLE "user_role"
(
	user_id integer not null,
	role_id integer not null,
	CONSTRAINT fk_user_role_user FOREIGN KEY(user_id) REFERENCES "user" (id),
	CONSTRAINT fk_user_role_role FOREIGN KEY(role_id) REFERENCES "role" (id)
);

CREATE TABLE "user_comment"
(
	id integer generated always as identity PRIMARY KEY,
	user_id_subject integer not null,
	user_id_asker integer not null,
	user_id_answerer integer,
	"comment" text not null,
	status text not null,
	answer text,
	CONSTRAINT fk_user_comment_user_subject FOREIGN KEY(user_id_subject) REFERENCES "user" (id),
	CONSTRAINT fk_user_comment_user_asker FOREIGN KEY(user_id_asker) REFERENCES "user" (id),
	CONSTRAINT fk_user_comment_user_answerer FOREIGN KEY(user_id_answerer) REFERENCES "user" (id)
);

CREATE TABLE "course_comment_vote"
(
	id integer generated always as identity PRIMARY KEY,
	course_comment_id integer not null,
	user_id integer not null,
	vote integer not null,
	constraint fk_course_comment_vote_course_comment FOREIGN KEY(course_comment_id) REFERENCES course_comment (id),
	constraint fk_course_comment_vote_user FOREIGN KEY(user_id) REFERENCES "user" (id)
);

CREATE TABLE "seat_usage"
(
	id integer generated always as identity PRIMARY KEY,
	course_usage_id integer not null,
	seat_id integer not null,
	user_id integer not null,
	start_date TIMESTAMP not null,
	end_date TIMESTAMP,
	constraint fk_seat_usage_course_usage FOREIGN KEY(course_usage_id) REFERENCES course_usage (id),
	constraint fk_seat_usage_seat FOREIGN KEY(seat_id) REFERENCES "seat" (id),
	constraint fk_seat_usage_user FOREIGN KEY(user_id) REFERENCES "user" (id)
);

CREATE TABLE "user_comment_vote"
(
	id integer generated always as identity PRIMARY KEY,
	user_comment_id integer not null,
	user_id integer not null,
	vote integer not null,
	constraint fk_user_comment_vote_user_comment FOREIGN KEY(user_comment_id) REFERENCES user_comment (id),
	constraint fk_user_comment_vote_user FOREIGN KEY(user_id) REFERENCES "user" (id)
);
