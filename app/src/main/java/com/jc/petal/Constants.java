package com.jc.petal;

/**
 * Created by LiCola on  2015/12/19  18:15
 * 常量类 用于保存字段的key值
 */
public class Constants {


    //token information
    public static final String TOKEN_ACCESS = "TokenAccess";
    public static final String TOKEN_REFRESH = "TokenRefresh";
    public static final String TOKEN_TYPE = "TokenType";
    public static final String TOKE_EXPIRES_IN = "TokeExpiresIn";



    public static final String USERHEADKEY = "userHeadKey";


    //board info
    public static final String BOARDTILTARRAY = "boardTitleArray";
    public static final String BOARDIDARRAY = "boardIdArray";

    //,
    public static final String SEPARATECOMMA = ",";

    //search
    public static final String HISTORYTEXT = "historyText";

    //RxView点击防止抖动时间间隔
    public static final long throttDuration = 300;

    //用户喜欢操作的操作字段
    public static final String OPERATELIKE = "like";
    public static final String OPERATEUNLIKE = "unlike";

    //用户对画板的关注操作字段
    public static final String OPERATEFOLLOW = "follow";
    public static final String OPERATEUNFOLLOW = "unfollow";

    //获得用户画板列表详情的操作符
    public static final String OPERATEBOARDEXTRA = "recommend_tags";
    public static final boolean OPERATECHECK = true;

    //删除画板的操作符
    public static final String OPERATEDELETEBOARD = "DELETE";

    /*****************************************************/

    public static final String Authorization = "Authorization";

    public static final String GRANT_TYPE_PASSWORD = "password";

    //user information
    public static final String IS_LOGIN = "isLogin";
    public static final String LOGIN_TIME = "loginTime";
    public static final String USER_ACCOUNT = "userAccount";
    public static final String USER_PASSWORD = "userPassword";
    public static final String USER_ID = "userID";
    public static final String USER_NAME = "userName";
    public static final String USER_EMAIL = "userEmail";
    public static final String USER_AVATAR_KEY = "userAvatarKey";


    public static final String DEFAULT_CATEGORY = "all";


    //http limit number
    public static final int LIMIT = 20;

    /*****************************************************/

    public static final String QUERY_KEY_CURRENT = "current";
    public static final String QUERY_KEY_MAX = "max";
    public static final String QUERY_KEY_SINCE = "since";

    /*****************************************************/
    public static final String ARG_TYPE = "type";

    public static final String ARG_PIN = "pin";
    public static final String ARG_PINS = "pins";


    public static final String ARG_ID = "id";
    public static final String ARG_BOARD_ID = "board_id";
    public static final String ARG_USER_ID = "user_id";
    public static final String ARG_USER = "user";
}
