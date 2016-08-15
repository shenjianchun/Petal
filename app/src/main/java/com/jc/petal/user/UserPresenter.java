package com.jc.petal.user;

import com.jc.petal.data.source.PetalRepository;

/**
 * User Presenter
 * Created by JC on 2016-08-13.
 */
public class UserPresenter implements UserContract.Presenter {

//    private final UserContract.BoardListView mBoardListView;
    private final PetalRepository mRepository;

    public UserPresenter(PetalRepository repository) {
        mRepository = repository;
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
