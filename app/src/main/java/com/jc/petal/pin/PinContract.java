package com.jc.petal.pin;

import com.jc.petal.BasePresenter;
import com.jc.petal.BaseView;
import com.jc.petal.data.model.Board;
import com.jc.petal.data.model.Pin;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * Listens to user actions from the UI ({@link PinDetailFragment}), retrieves the data and updates
 * the UI as required.
 *
 * Created by JC on 2016-08-22.
 */
public interface PinContract {


    interface PinListView extends BaseView<PinListPresenter> {
        void showPins(boolean isRefresh, List<Pin> pins);

    }

    interface PinListPresenter extends BasePresenter {
        void getPins(boolean isRefresh, String id, int limit, @NonNull String key,
                     String pinId);
    }

    interface SearchPinListView extends BaseView<SearchPinListPresenter> {
        void showPins(final boolean isRefresh, List<Pin> pins);

    }

    /**
     * 查询界面的采集列表Presenter
     */
    interface SearchPinListPresenter extends BasePresenter {
        /**
         * 查询pins
         * @param isRefresh 是否为刷新
         * @param key 关键字
         * @param limit 每页个数
         */
        void searchPins(final boolean isRefresh, @NonNull String key, int limit);
    }

    interface PinDetailView extends BaseView<PinDetailPresenter> {
        void showPinInfo(Pin pin);

//        void likeSuccess();
    }


    interface PinDetailPresenter extends BasePresenter {
        void getPin(String pinId);

//        void like(String pinId, boolean flag);
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

    interface ActionView extends BaseView<ActionPresenter> {
        void onLikeResult(boolean flag);
    }

    interface ActionPresenter extends BasePresenter {
        void like(String pinId, boolean flag);
    }

}
