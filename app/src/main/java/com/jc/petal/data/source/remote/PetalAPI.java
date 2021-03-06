package com.jc.petal.data.source.remote;

import com.jc.petal.data.model.AuthToken;
import com.jc.petal.data.model.User;
import com.jc.petal.utils.Base64;


/**
 * Created by JC on 2016-08-03.
 */
public class PetalAPI {

    protected static final String API_ACTIVITIES = "http://api.huaban.com/message/activities/";
    protected static final String API_ALL = "http://api.huaban.com/all/";
    protected static final String API_BOARDS = "http://api.huaban.com/boards/";
    protected static final String API_CATEGORY = "http://api.huaban.com/categories/";
    protected static final String API_FAVORITE = "http://api.huaban.com/favorite/";
    protected static final String API_FEEDS = "http://api.huaban.com/feeds/";
    protected static final String API_FRIENDS = "http://api.huaban.com/friends/";
    protected static final String API_Follow = "http://api.huaban.com/following/";
    protected static final String API_HOST = "https://huaban.com/";
    protected static final String API_MAIN = "http://api.huaban.com/";
    protected static final String API_MENTIONS = "http://api.huaban.com/message/mentions/";
    protected static final String API_MESSAGE = "http://api.huaban.com/message/";
    protected static final String API_PIN = "http://api.huaban.com/pins/";
    protected static final String API_POPULAR_PINS = "http://api.huaban.com/popular/";
    protected static final String API_REPORT = "http://api.huaban.com/feedback/report/";
    protected static final String API_SEARCH_BOARD = "http://api.huaban.com/search/boards/";
    protected static final String API_SEARCH_PEOPLE = "http://api.huaban.com/search/people/";
    protected static final String API_SEARCH_PIN = "http://api.huaban.com/search/";
    protected static final String API_SHARE = "http://api.huaban.com/share/";
    protected static final String API_TOKEN = "https://huaban.com/oauth/access_token/";
    protected static final String API_USER = "http://api.huaban.com/users/";
    protected static final String API_WEEKLY = "http://api.huaban.com/weekly/";


    private static final int CONNECTION_TIMEOUT = 20000;
    public static final String HBIMG = "http://img.hb.aicdn.com/";
    private static final int SO_TIMEOUT = 20000;
    private static final String X_Client_ID = "X-Client-ID";


    private final static String mClientID = "1d912cae47144fa09d88";
    private final static String mClientSecret = "f94fcc09b59b4349a148a203ab2f20c7";

    /**
     * 用于登录时的 Authorization
     */
    private final static String mClientInfo = mClientID + ":" + mClientSecret;
    public static final String mAuthorization = "Basic " + Base64.encodeString(mClientInfo);


    private AuthToken mToken;

    private User mSelf;

}
