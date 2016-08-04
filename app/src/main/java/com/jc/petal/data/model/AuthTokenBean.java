package com.jc.petal.data.model;

/**
 *
 * Created by JC on 2016-07-30.
 */
public class AuthTokenBean {

    /**
     * access_token : da0e9e1b-1a7a-4b3c-ade7-531910b6e847
     * token_type : bearer
     * expires_in : 10811
     * refresh_token : 1530d3c7-da74-46de-bd1f-98ef9e7181d2
     */

    public String access_token;
    public String token_type;
    public int expires_in;
    public String refresh_token;

    @Override
    public String toString() {
        return "AuthTokenBean{" +
                "access_token='" + access_token + '\'' +
                ", token_type='" + token_type + '\'' +
                ", expires_in=" + expires_in +
                ", refresh_token='" + refresh_token + '\'' +
                '}';
    }
}
