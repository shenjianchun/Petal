package com.jc.petal.data.module;

/**
 * 用户信息 Json格式
 * Created by JC on 2016-07-30.
 */
public class UserBean {

    /**
     * user_id : 14015812
     * username : ShenJC
     * urlname : ubtfdwhnru
     * avatar : {"id":45254635,"farm":"farm1","bucket":"hbimg",
     * "key":"cd515e4ab23ea6c576c02d446585e343bf12d5b52224-PUEYVW","type":"image/jpeg",
     * "width":180,"height":180,"frames":1}
     * email : shenjianchun@gmail.com
     * created_at : 1403098613
     * board_count : 6
     * pin_count : 87
     * like_count : 1
     * boards_like_count : 6
     * follower_count : 1
     * following_count : 18
     * explore_following_count : 0
     * commodity_count : 0
     * creations_count : 0
     * profile : {}
     * bindings : {"weibo":{"bind_id":"weibo-1581635801","user_id":14015812,
     * "service_name":"weibo","auth_type":"oauth2","user_info":{"id":1581635801,
     * "username":"我看着你在笑","email":"","avatar":{"id":45254635,"farm":"farm1","bucket":"hbimg",
     * "key":"cd515e4ab23ea6c576c02d446585e343bf12d5b52224-PUEYVW","type":"image/jpeg",
     * "width":180,"height":180,"frames":1},"verified":false,"verified_reason":"",
     * "verified_type":-1,"location":"上海 闵行区","gender":"m","url":"http://blog.sina.com
     * .cn/siobhan","about":"后知后觉"},"created_at":1403098595}}
     * user : {"user_id":14015812,"username":"ShenJC","urlname":"ubtfdwhnru",
     * "avatar":{"id":45254635,"farm":"farm1","bucket":"hbimg",
     * "key":"cd515e4ab23ea6c576c02d446585e343bf12d5b52224-PUEYVW","type":"image/jpeg",
     * "width":180,"height":180,"frames":1},"email":"shenjianchun@gmail.com",
     * "created_at":1403098613,"board_count":6,"pin_count":87,"like_count":1,
     * "boards_like_count":6,"follower_count":1,"following_count":18,"explore_following_count":0,
     * "commodity_count":0,"creations_count":0,"profile":{},
     * "bindings":{"weibo":{"bind_id":"weibo-1581635801","user_id":14015812,
     * "service_name":"weibo","auth_type":"oauth2","user_info":{"id":1581635801,
     * "username":"我看着你在笑","email":"","avatar":{"id":45254635,"farm":"farm1","bucket":"hbimg",
     * "key":"cd515e4ab23ea6c576c02d446585e343bf12d5b52224-PUEYVW","type":"image/jpeg",
     * "width":180,"height":180,"frames":1},"verified":false,"verified_reason":"",
     * "verified_type":-1,"location":"上海 闵行区","gender":"m","url":"http://blog.sina.com
     * .cn/siobhan","about":"后知后觉"},"created_at":1403098595}}}
     */

    private int user_id;
    private String username;
    private String urlname;
    /**
     * id : 45254635
     * farm : farm1
     * bucket : hbimg
     * key : cd515e4ab23ea6c576c02d446585e343bf12d5b52224-PUEYVW
     * type : image/jpeg
     * width : 180
     * height : 180
     * frames : 1
     */

    private AvatarEntity avatar;
    private String email;
    private int created_at;
    private int board_count;
    private int pin_count;
    private int like_count;
    private int boards_like_count;
    private int follower_count;
    private int following_count;
    private int explore_following_count;
    private int commodity_count;
    private int creations_count;
    /**
     * weibo : {"bind_id":"weibo-1581635801","user_id":14015812,"service_name":"weibo",
     * "auth_type":"oauth2","user_info":{"id":1581635801,"username":"我看着你在笑","email":"",
     * "avatar":{"id":45254635,"farm":"farm1","bucket":"hbimg",
     * "key":"cd515e4ab23ea6c576c02d446585e343bf12d5b52224-PUEYVW","type":"image/jpeg",
     * "width":180,"height":180,"frames":1},"verified":false,"verified_reason":"","verified_type":-1,"location":"上海 闵行区","gender":"m","url":"http://blog.sina.com.cn/siobhan","about":"后知后觉"},"created_at":1403098595}
     */

    private BindingsBean bindings;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUrlname() {
        return urlname;
    }

    public void setUrlname(String urlname) {
        this.urlname = urlname;
    }

    public AvatarEntity getAvatar() {
        return avatar;
    }

    public void setAvatar(AvatarEntity avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCreated_at() {
        return created_at;
    }

    public void setCreated_at(int created_at) {
        this.created_at = created_at;
    }

    public int getBoard_count() {
        return board_count;
    }

    public void setBoard_count(int board_count) {
        this.board_count = board_count;
    }

    public int getPin_count() {
        return pin_count;
    }

    public void setPin_count(int pin_count) {
        this.pin_count = pin_count;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public int getBoards_like_count() {
        return boards_like_count;
    }

    public void setBoards_like_count(int boards_like_count) {
        this.boards_like_count = boards_like_count;
    }

    public int getFollower_count() {
        return follower_count;
    }

    public void setFollower_count(int follower_count) {
        this.follower_count = follower_count;
    }

    public int getFollowing_count() {
        return following_count;
    }

    public void setFollowing_count(int following_count) {
        this.following_count = following_count;
    }

    public int getExplore_following_count() {
        return explore_following_count;
    }

    public void setExplore_following_count(int explore_following_count) {
        this.explore_following_count = explore_following_count;
    }

    public int getCommodity_count() {
        return commodity_count;
    }

    public void setCommodity_count(int commodity_count) {
        this.commodity_count = commodity_count;
    }

    public int getCreations_count() {
        return creations_count;
    }

    public void setCreations_count(int creations_count) {
        this.creations_count = creations_count;
    }

    public BindingsBean getBindings() {
        return bindings;
    }

    public void setBindings(BindingsBean bindings) {
        this.bindings = bindings;
    }

    public static class BindingsBean {
        /**
         * bind_id : weibo-1581635801
         * user_id : 14015812
         * service_name : weibo
         * auth_type : oauth2
         * user_info : {"id":1581635801,"username":"我看着你在笑","email":"","avatar":{"id":45254635,
         * "farm":"farm1","bucket":"hbimg",
         * "key":"cd515e4ab23ea6c576c02d446585e343bf12d5b52224-PUEYVW","type":"image/jpeg",
         * "width":180,"height":180,"frames":1},"verified":false,"verified_reason":"",
         * "verified_type":-1,"location":"上海 闵行区","gender":"m","url":"http://blog.sina.com
         * .cn/siobhan","about":"后知后觉"}
         * created_at : 1403098595
         */

        private WeiboBean weibo;

        public WeiboBean getWeibo() {
            return weibo;
        }

        public void setWeibo(WeiboBean weibo) {
            this.weibo = weibo;
        }

        public static class WeiboBean {
            private String bind_id;
            private int user_id;
            private String service_name;
            private String auth_type;
            /**
             * id : 1581635801
             * username : 我看着你在笑
             * email :
             * avatar : {"id":45254635,"farm":"farm1","bucket":"hbimg",
             * "key":"cd515e4ab23ea6c576c02d446585e343bf12d5b52224-PUEYVW","type":"image/jpeg",
             * "width":180,"height":180,"frames":1}
             * verified : false
             * verified_reason :
             * verified_type : -1
             * location : 上海 闵行区
             * gender : m
             * url : http://blog.sina.com.cn/siobhan
             * about : 后知后觉
             */

            private UserInfoBean user_info;
            private int created_at;

            public String getBind_id() {
                return bind_id;
            }

            public void setBind_id(String bind_id) {
                this.bind_id = bind_id;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getService_name() {
                return service_name;
            }

            public void setService_name(String service_name) {
                this.service_name = service_name;
            }

            public String getAuth_type() {
                return auth_type;
            }

            public void setAuth_type(String auth_type) {
                this.auth_type = auth_type;
            }

            public UserInfoBean getUser_info() {
                return user_info;
            }

            public void setUser_info(UserInfoBean user_info) {
                this.user_info = user_info;
            }

            public int getCreated_at() {
                return created_at;
            }

            public void setCreated_at(int created_at) {
                this.created_at = created_at;
            }

            public static class UserInfoBean {
                private int id;
                private String username;
                private String email;
                /**
                 * id : 45254635
                 * farm : farm1
                 * bucket : hbimg
                 * key : cd515e4ab23ea6c576c02d446585e343bf12d5b52224-PUEYVW
                 * type : image/jpeg
                 * width : 180
                 * height : 180
                 * frames : 1
                 */

                private AvatarBean avatar;
                private boolean verified;
                private String verified_reason;
                private int verified_type;
                private String location;
                private String gender;
                private String url;
                private String about;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getUsername() {
                    return username;
                }

                public void setUsername(String username) {
                    this.username = username;
                }

                public String getEmail() {
                    return email;
                }

                public void setEmail(String email) {
                    this.email = email;
                }

                public AvatarBean getAvatar() {
                    return avatar;
                }

                public void setAvatar(AvatarBean avatar) {
                    this.avatar = avatar;
                }

                public boolean isVerified() {
                    return verified;
                }

                public void setVerified(boolean verified) {
                    this.verified = verified;
                }

                public String getVerified_reason() {
                    return verified_reason;
                }

                public void setVerified_reason(String verified_reason) {
                    this.verified_reason = verified_reason;
                }

                public int getVerified_type() {
                    return verified_type;
                }

                public void setVerified_type(int verified_type) {
                    this.verified_type = verified_type;
                }

                public String getLocation() {
                    return location;
                }

                public void setLocation(String location) {
                    this.location = location;
                }

                public String getGender() {
                    return gender;
                }

                public void setGender(String gender) {
                    this.gender = gender;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getAbout() {
                    return about;
                }

                public void setAbout(String about) {
                    this.about = about;
                }

                public static class AvatarBean {
                    private int id;
                    private String farm;
                    private String bucket;
                    private String key;
                    private String type;
                    private int width;
                    private int height;
                    private int frames;

                    public int getId() {
                        return id;
                    }

                    public void setId(int id) {
                        this.id = id;
                    }

                    public String getFarm() {
                        return farm;
                    }

                    public void setFarm(String farm) {
                        this.farm = farm;
                    }

                    public String getBucket() {
                        return bucket;
                    }

                    public void setBucket(String bucket) {
                        this.bucket = bucket;
                    }

                    public String getKey() {
                        return key;
                    }

                    public void setKey(String key) {
                        this.key = key;
                    }

                    public String getType() {
                        return type;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }

                    public int getWidth() {
                        return width;
                    }

                    public void setWidth(int width) {
                        this.width = width;
                    }

                    public int getHeight() {
                        return height;
                    }

                    public void setHeight(int height) {
                        this.height = height;
                    }

                    public int getFrames() {
                        return frames;
                    }

                    public void setFrames(int frames) {
                        this.frames = frames;
                    }
                }
            }
        }
    }
}
