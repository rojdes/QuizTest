package com.usinformatic.rxexample.ui.chat;

import com.usinformatic.rxexample.models.Player;
import com.usinformatic.rxexample.models.RoundResponse;
import com.usinformatic.rxexample.models.enums.OptionState;

/**
 * Created by d1m11n on 9/29/15.
 */
public interface IQuizChatView {

        public void setOptionsContent(String [] options);

        public void resetOptionViews();

        public void setSelectOptions(boolean enabled);

        public void setQuestion(String question);

        public void updateOptionState(int number, OptionState state);

        public void updateTime(String value);

        public void showResult(RoundResponse player, RoundResponse opponent);

        public void showMessageDialog(String title, String message);

        public void showPlayersInfo(Player me, Player ... opponents);


        public void showProgress(String message);

        public void hideProgress();




}
