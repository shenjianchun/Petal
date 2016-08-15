package com.jc.petal.board;

import com.jc.petal.BasePresenter;
import com.jc.petal.BaseView;
import com.jc.petal.data.model.Board;
import com.jc.petal.data.model.Pin;

import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 * Created by JC on 2016-07-29.
 */
public interface BoardContract {

    interface BoardListView extends BaseView<BoardListPresenter> {
        void showBoards(List<Board> boards);
    }

    interface BoardListPresenter extends BasePresenter {
        void getUserBoards(String userId);
    }

    interface BoardDetailView extends BaseView<BoardDetailPresenter> {
        void showBoardInfo(Board board);
        void showBoardPins(List<Pin> pins);
    }

    interface BoardDetailPresenter extends BasePresenter {
        void getBoard(String boardId);
        void getBoardPins(String boardId, int current, int limit);
    }


}
