package com.jc.petal.board;

import com.google.common.base.Preconditions;

import com.jc.petal.RequestCallback;
import com.jc.petal.data.model.Board;
import com.jc.petal.data.model.BoardDetail;
import com.jc.petal.data.source.PetalRepository;

import android.support.annotation.NonNull;

/**
 * Board detail presenter
 * Created by JC on 2016-08-15.
 */
public class BoardDetailPresenterImpl implements BoardContract.BoardDetailPresenter {

    private final BoardContract.BoardDetailView mView;
    private final PetalRepository mRepository;

    public BoardDetailPresenterImpl(@NonNull BoardContract.BoardDetailView view,@NonNull PetalRepository
            repository) {
        mView = Preconditions.checkNotNull(view);
        mRepository = Preconditions.checkNotNull(repository);
        mView.setPresenter(this);
    }

    @Override
    public void getBoard(String boardId) {

        mView.showLoading();

        mRepository.getBoard(boardId, new RequestCallback<BoardDetail>() {
            @Override
            public void onSuccess(BoardDetail data) {

                Board board = data.board;

                mView.showBoard(board);
                mView.hideLoading();

            }

            @Override
            public void onError(String msg) {
                mView.showError(msg);
                mView.hideLoading();
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
