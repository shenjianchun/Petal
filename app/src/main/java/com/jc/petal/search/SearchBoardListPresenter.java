package com.jc.petal.search;

import com.jc.petal.RequestCallback;
import com.jc.petal.board.BoardContract;
import com.jc.petal.data.model.BoardList;
import com.jc.petal.data.source.PetalRepository;

import org.apache.commons.collections4.CollectionUtils;

import android.support.annotation.NonNull;

/**
 * SearchBoardList Presenter
 * Created by JC on 2016-09-01.
 */
public class SearchBoardListPresenter implements BoardContract.SearchBoardListPresenter {

    private PetalRepository mRepository;
    private BoardContract.SearchBoardListView mView;

    private int mCurrentPage;

    public SearchBoardListPresenter(BoardContract.SearchBoardListView view, PetalRepository
            repository) {
        mRepository = repository;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void searchBoards(final boolean isRefresh, @NonNull String key, int limit) {

        mView.showLoading();
        if (isRefresh) {
            mCurrentPage = 0;
        } else {
            mCurrentPage++;
        }

        mRepository.searchBoards(key, limit, String.valueOf(mCurrentPage), new
                RequestCallback<BoardList>() {
                    @Override
                    public void onSuccess(BoardList data) {
                        mView.hideLoading();
                        if (!CollectionUtils.isEmpty(data.boards)) {
                            mView.showBoards(isRefresh, data.boards);
                        }
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
