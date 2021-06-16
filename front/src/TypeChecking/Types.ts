import { NavigatorScreenParams } from '@react-navigation/native';
import { CommentEvent } from './Enums';
import { Comment, CommentSection, Room } from './Interfaces';

export type AuthNavigatorParamsList = {
  SignIn: undefined;
};

export type HomeNavigatorParamsList = {
  Home: undefined;
  BarcodeCamera: undefined;
  Comments: NavigatorScreenParams<CommentNavigatorParamList>;
  CommentWriteModal: { comment: Comment; mode: string; action: CommentEvent };
};

export type CourseNavigatorParamsList = {
  Courses: undefined;
  CourseDetails: { name: string; id: number };
};

export type TimeTableNavigatorParamsList = {
  TimeTables: undefined;
  CourseManage: { name: string; roomId: number };
};

export type CommentTabNavigatorParamList = {
  CourseComments: undefined;
  UserComments: undefined;
};

export type CommentNavigatorParamList = {
  CommentsTab: NavigatorScreenParams<CommentTabNavigatorParamList>;
  CommentsList: { commentSection: CommentSection; mode: string };
};
export type FeedbackRoomNavigatorParamsList = {
  FeedbackRooms: undefined;
  CreateFeedbackRoom: undefined;
  EditFeedbackRoom: { feedbackRoomId: number };
  Comments: NavigatorScreenParams<CommentNavigatorParamList>;
  CommentWriteModal: { comment: Comment; mode: string; action: CommentEvent };
};

export type RoomNavigatorParamsList = {
  Rooms: undefined;
  RoomDetails: { name: string; room: Room };
  RoomCreate: undefined;
};

export type DrawerNavigatorParamsList = {
  Home: NavigatorScreenParams<HomeNavigatorParamsList>;
  Courses: NavigatorScreenParams<CourseNavigatorParamsList>;
  Rooms: NavigatorScreenParams<RoomNavigatorParamsList>;
  TimeTables: NavigatorScreenParams<TimeTableNavigatorParamsList>;
  FeedbackRooms: NavigatorScreenParams<FeedbackRoomNavigatorParamsList>;
  Comments: NavigatorScreenParams<CommentNavigatorParamList>;
};

export type RootNavigatorParamsList = {
  App: NavigatorScreenParams<DrawerNavigatorParamsList>;
  SignIn: NavigatorScreenParams<AuthNavigatorParamsList>;
};
