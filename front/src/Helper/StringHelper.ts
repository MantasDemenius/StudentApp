import { CommentState } from '../TypeChecking/Enums';

export const capitalizeFirstLetter = (text: string) =>
  text.charAt(0).toUpperCase() + text.slice(1);

export const splitCommentStateFromString = (status: string) =>
  status.split(' - ') as CommentState[];

export const getStatusFromStatusString = (status: string, index: number) => {
  const statusArray = splitCommentStateFromString(status);
  if (statusArray[0] === CommentState.UNANSWERED) {
    if (index === 0) {
      return CommentState.AGREE;
    }
    return CommentState.THIS_SEMESTER;
  }
  return statusArray[index];
};
