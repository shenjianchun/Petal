package com.jc.petal;


/**
 * 带有分页的Presenter
 * Created by 14110105 on 2015/9/11.
 */
public abstract class PagedPresenter implements BasePresenter {

    public int page = 1;
    public int maxPage = 0;

    // 获取刷新的数据
    public void refresh() {
        page = 1;
        loadData(page);
    }

    // 获取加载更多的数据
    public void loadMore() {
        if(hasMore()) {
            loadData(++page);
        }
    }

    public abstract void loadData(int page);

    // 是否还可以继续加载更多
    public boolean hasMore() {
        return page < maxPage;
    }

}
