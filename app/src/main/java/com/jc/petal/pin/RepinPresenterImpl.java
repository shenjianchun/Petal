package com.jc.petal.pin;

import com.jc.petal.RequestCallback;
import com.jc.petal.data.model.Board;
import com.jc.petal.data.model.BoardList;
import com.jc.petal.data.model.Pin;
import com.jc.petal.data.source.PetalRepository;
import com.orhanobut.logger.Logger;

import android.util.Log;

import java.util.List;

/**
 * Board list presenter
 * Created by JC on 2016-08-13.
 */
public class RepinPresenterImpl implements PinContract.RepinPresenter {

    private final PinContract.RepinView mView;
    private final PetalRepository mRepository;

    public RepinPresenterImpl(PinContract.RepinView view, PetalRepository repository) {
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
    public void repin(String viaPinId, String boardId, String text, String mediaType,
                      String origSource) {

        mRepository.repin(viaPinId, boardId, text, mediaType, origSource, new RequestCallback<Pin>() {


            @Override
            public void onSuccess(Pin data) {
                Log.d(this.getClass().getName(), "onSuccess: ");
                mView.repinSuccess();
            }

            @Override
            public void onError(String msg) {
                Logger.d(msg);
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
