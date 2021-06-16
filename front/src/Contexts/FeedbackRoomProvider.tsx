import React, {
  createContext,
  Dispatch,
  SetStateAction,
  useCallback,
  useContext,
  useState
} from 'react';
import { formatDateTimeStringToDate, now } from '../Helper/DateHelper';
import { FeedbackRoomStage } from '../TypeChecking/Enums';
import { FeedbackRoomStages } from '../TypeChecking/Interfaces';

const FeedbackRoomContext = createContext<{
  feedbackRoom: FeedbackRoomStages | undefined;
  currentStages: FeedbackRoomStage[];
  tabHeaderTitle: string | undefined;
  checkAllowedStages: (stages: FeedbackRoomStage[]) => boolean;
  setContextFeedbackRoom: (activeFeedbackRoom: FeedbackRoomStages) => void;
  setTabHeaderTitle: Dispatch<SetStateAction<string | undefined>>;
}>({
  feedbackRoom: undefined,
  currentStages: [],
  tabHeaderTitle: undefined,
  checkAllowedStages: () => false,
  setContextFeedbackRoom: () => {},
  setTabHeaderTitle: () => {}
});

export const useFeedbackRoomContext = () => useContext(FeedbackRoomContext);

type Props = {};
const FeedbackRoomProvider: React.FC<Props> = ({ children }) => {
  const [feedbackRoom, setFeedbackRoom] = useState<FeedbackRoomStages>();
  const [currentStages, setCurrentStages] = useState<FeedbackRoomStage[]>([]);
  const [tabHeaderTitle, setTabHeaderTitle] = useState<string | undefined>();

  const setContextFeedbackRoom = useCallback(
    (activeFeedbackRoom: FeedbackRoomStages) => {
      const currentDate = now();
      activeFeedbackRoom.stages.forEach((stage) => {
        if (
          currentDate > formatDateTimeStringToDate(stage.startDate) &&
          currentDate < formatDateTimeStringToDate(stage.endDate)
        ) {
          setCurrentStages((currentCurrentStages) => [
            ...(currentCurrentStages ?? []),
            stage.name
          ]);
        }
      });
      setFeedbackRoom(activeFeedbackRoom);
    },
    []
  );

  const checkAllowedStages = (stages: FeedbackRoomStage[]) => {
    if (Array.isArray(currentStages) && currentStages.length === 0) {
      return false;
    }
    return stages.some((r) => currentStages.includes(r));
  };

  return (
    <FeedbackRoomContext.Provider
      value={{
        feedbackRoom,
        currentStages,
        checkAllowedStages,
        setContextFeedbackRoom,
        tabHeaderTitle,
        setTabHeaderTitle
      }}>
      {children}
    </FeedbackRoomContext.Provider>
  );
};

export default FeedbackRoomProvider;
