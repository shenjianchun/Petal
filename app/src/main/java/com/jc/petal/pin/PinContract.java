package com.jc.petal.pin;

import com.jc.petal.BasePresenter;
import com.jc.petal.BaseView;
import com.jc.petal.data.model.Board;
import com.jc.petal.data.model.Pin;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Created by JC on 2016-08-22.
 */
public interface PinContract {


    interface PinListView extends BaseView<PinListPresenter> {
        void showPins(boolean isRefresh, List<Pin> pins);

    }

    interface PinListPresenter extends BasePresenter {
        void getPins(boolean isRefresh, String id, int limit,  @NonNull String key,
                         String pinId);
    }

    interface PinDetailView extends BaseView<PinDetailPresenter> {
        void showPinInfo(Pin pin);
    }


    interface PinDetailPresenter extends BasePresenter {
        void getPin(String pinId);
    }

    interface RepinView extends BaseView<RepinPresenter> {
        void showBoards(List<Board> boards);
        void repinSuccess();
    }

    interface RepinPresenter extends BasePresenter {
        void getUserBoards(String userId);
        void repin(String viaPinId, String boardId, String text,
                   String mediaType, String origSource);
    }


}
