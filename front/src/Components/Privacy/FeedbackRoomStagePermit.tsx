import React from 'react';
import { useFeedbackRoomContext } from '../../Contexts/FeedbackRoomProvider';
import { FeedbackRoomStage } from '../../TypeChecking/Enums';

interface Props {
  stages: FeedbackRoomStage[];
}
const FeedbackRoomStagePermit: React.FC<Props> = ({ stages, children }) => {
  const { checkAllowedStages } = useFeedbackRoomContext();

  return (
    <>
      {checkAllowedStages(stages) &&
        ({ ...(children as object) } as React.ReactNode)}
    </>
  );
};

export default FeedbackRoomStagePermit;
