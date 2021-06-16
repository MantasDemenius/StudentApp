export enum CommentEvent {
  REMOVE = 'remove',
  EDIT = 'edit',
  ANSWER = 'answer',
  AGREE = 'agree',
  DISAGREE = 'disagree'
}

export enum CommentState {
  AGREE = 'Sutinka',
  DISAGREE = 'Nesutinka',
  PARTIALLY_AGREE = 'Dalinai sutinka',
  SPECIFY = 'Tikslinti',
  THIS_SEMESTER = 'šį semestrą',
  NEXT_SEMESTER = 'kitą semestrą',
  UNANSWERED = 'Neatsakytas'
}

export enum FeedbackRoomStage {
  INACTIVE = 'Neaktyvus',
  WRITE = 'Rašyti',
  MODIFY = 'Modifikuoti',
  EVALUATION = 'Vertinti',
  ANSWER = 'Atsakyti',
  ENDED = 'Pasibaigęs'
}
