ALTER TABLE public."course_comment"
ADD COLUMN feedback_room_id integer not null;

ALTER TABLE public."course_comment"
ADD COLUMN is_deleted boolean not null;


ALTER TABLE public."user_comment"
ADD COLUMN feedback_room_id integer not null;

ALTER TABLE public."user_comment"
ADD COLUMN is_deleted boolean not null;

ALTER TABLE public."course_comment"
ADD CONSTRAINT fk_course_comment_feedback_room FOREIGN KEY(feedback_room_id) REFERENCES "feedback_room" (id);

ALTER TABLE public."user_comment"
ADD CONSTRAINT fk_user_comment_feedback_room FOREIGN KEY(feedback_room_id) REFERENCES "feedback_room" (id);