package com.bobo.something.data;

import android.os.Handler;
import android.text.TextUtils;

import com.bobo.something.Const;
import com.bobo.something.base.BaseHttpRequest.OnHttpResult;
import com.bobo.something.protocol.ReqRegion;
import com.bobo.something.utils.GsonUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by huangbo on 2017/6/8.
 */

public class Region {
    public static Region region;

    public static Region getInstance() {
        if (region == null) {
            region = new Region();
        }
        return region;
    }


    ProvinceCityBean provinceCityBean;

    public int getVersion() {
        return provinceCityBean == null ? 0 : provinceCityBean.getVersion();
    }

    public ProvinceCityBean getProvinceCityBean() {
        if (provinceCityBean != null) {
            return provinceCityBean;
        }
        return provinceCityBean;
    }

    public void setProvinceCityBean(final String mRegionJson, final Handler handler) {
        if (TextUtils.isEmpty(mRegionJson)) {
            return;
        }
        cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                provinceCityBean = GsonUtils.GsonToBean(mRegionJson, ProvinceCityBean.class);
                if (handler != null) {
                    handler.sendEmptyMessage(1);
                }
            }
        });

    }


    ExecutorService cachedThreadPool;
    CacheData cacheData;

    private Region() {
        cachedThreadPool = Executors.newCachedThreadPool();
        cacheData = CacheData.getInstance().cacheName(Const.REGION_JSON);
    }


    public void readRegionFromAssets(Handler handler) {
        cacheData.readDataFromAssets(handler);
    }

    public void readRegionFromFile(Handler handler) {
        cacheData.readDataFromFile(handler);
    }

    /**
     * 将region 更新到指定文件里
     *
     * @param regionJsonData
     */
    public void writeRegion2FileWithThread(String regionJsonData) {
        cacheData.writeData2FileWithThread(regionJsonData);
    }

    /**
     * 请求网络 数据
     *
     * @param onHttpResult
     */
    public void requestRegion(OnHttpResult onHttpResult) {
        ReqRegion reqRegion = new ReqRegion();
        reqRegion.sendRequest(false, onHttpResult);
    }

}

