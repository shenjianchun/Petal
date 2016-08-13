package com.jc.petal.board;

import com.jc.petal.RequestCallback;
import com.jc.petal.data.model.Board;
import com.jc.petal.data.model.BoardList;
import com.jc.petal.data.source.PetalRepository;

import java.util.List;

/**
 * Board list presenter
 * Created by 14110105 on 2016-08-13.
 */
public class BoardListPresenterImpl implements BoardContract.BoardListPresenter {

    private final BoardContract.BoardListView mView;
    private final PetalRepository mRepository;

    public BoardListPresenterImpl(BoardContract.BoardListView view, PetalRepository repository) {
        mView = view;
        mRepository = repository;
        mView.setPresenter(this);
    }

    @Override
    public void getUserBoards(String userId) {

        mView.showLoading();

        mRepository.getUserBoards(userId, new RequestCallback<BoardList>() {
            @Override
            public void onSuccess(BoardList data) {
                List<Board> boards = data.boards;
                mView.showBoards(boards);
                mView.hideLoading();
            }

            @Override
            public void onError(String msg) {
                mView.hideLoading();
                mView.showError(msg);
            }
        });

    }

    @Override
    public void initialize() {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
