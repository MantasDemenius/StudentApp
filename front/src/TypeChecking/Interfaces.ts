import { FeedbackRoomStage } from './Enums';

export interface User {
  name: string;
  surname: string;
}

export interface Room {
  id: number;
  address: string;
  building: string;
  roomNumber: string;
  type: string;
}

export interface CourseSeating {
  title: string;
  user: User;
  seatName: string;
}

export interface Course {
  id: number;
  code: string;
  title: string;
  language: string;
}

export interface Stage {
  name: FeedbackRoomStage;
  startDate: string;
  endDate: string;
}

export interface FeedbackRoom {
  id: number;
  startDate: string;
  endDate: string;
}

export interface FeedbackRoomStages {
  id: number;
  startDate: string;
  endDate: string;
  stages: Stage[];
}

export interface Vote {
  id: number;
  votes: number;
  userVote: number;
}

export interface Comment {
  id: number;
  creatorId: number;
  answerer: User | null;
  comment: string;
  status: string;
  answer: string | null;
  votes: Vote;
}

export interface CommentSection {
  id: number;
  feedbackRoomId: number;
  title: string;
  commentCount: number;
}

export interface Seat {
  id: number;
  name: string;
  code: string;
}

export interface SeatUsage {
  seatId: number;
  seatName: string;
  locked: boolean;
  userName: string | null;
  userSurname: string | null;
  startDate: string | null;
}

export interface Lecture {
  courseTitle: string;
  courseCode: string;
  roomBuilding: string;
  roomNumber: string;
  seat: string;
  courseStartDate: string;
  courseEndDate: string;
  userOccupyStartDate: string;
}

export interface Alert {
  isVisible: boolean;
  message: string;
  buttonText: string;
  title: string;
}

export interface TimeTable {
  id: number;
  courseTitle: string;
  roomId: number;
  roomBuilding: string;
  roomNumber: string;
  courseStartDate: string;
  courseEndDate: string;
}

export interface ServerError {
  timestamp: string;
  status: string;
  errors: string[];
}
