package com.bobo.something.protocol;

import com.bobo.something.base.BaseHttpRequest;

/**
 * Created by huangbo on 2017/6/20.
 */

public class ReqRegion extends BaseHttpRequest {
    public ReqRegion(int version) {
        url="";
        setRequestType(RequestTypePost);
    }

}
