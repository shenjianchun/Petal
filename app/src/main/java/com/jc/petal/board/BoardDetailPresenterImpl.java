package com.jc.petal.board;

import com.google.common.base.Preconditions;

import com.jc.petal.RequestCallback;
import com.jc.petal.data.model.Board;
import com.jc.petal.data.model.BoardDetail;
import com.jc.petal.data.model.Pin;
import com.jc.petal.data.model.PinList;
import com.jc.petal.data.source.PetalRepository;

import org.apache.commons.collections4.CollectionUtils;

import android.support.annotation.NonNull;

import java.util.List;

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

                mView.showBoardInfo(board);
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
    public void getBoardPins(String boardId, int current, int limit) {

        mRepository.getBoardPins(boardId, current, limit, new RequestCallback<PinList>() {
            @Override
            public void onSuccess(PinList data) {
                List<Pin> pins = data.pins;
                if (!CollectionUtils.isEmpty(pins)) {
                    mView.showBoardPins(pins);
                }
            }

            @Override
            public void onError(String msg) {
//                mView.showError(msg);
//                mView.hideLoading();
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
