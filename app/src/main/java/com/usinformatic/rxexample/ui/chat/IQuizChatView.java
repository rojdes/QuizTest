package com.usinformatic.rxexample.ui.chat;

import com.usinformatic.rxexample.models.enums.OptionState;

/**
 * Created by d1m11n on 9/29/15.
 */
public interface IQuizChatView {

         public void setOptionsContent(String [] options);

        public void resetOptionViews();

        public void setSelectOptions(boolean locked);

        public void setQuestion(String question);

        public void updateOptionState(int number, OptionState state);

        public void updateTime(String value);

        public void showResult(/*?????*/);


}
