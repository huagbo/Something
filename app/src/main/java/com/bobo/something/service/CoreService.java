package com.bobo.something.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.bobo.something.base.BaseHttpRequest.OnHttpResult;
import com.bobo.something.data.ProvinceCityBean;
import com.bobo.something.data.Region;
import com.bobo.something.utils.AppUtil;

/**
 * Created by huangbo on 2017/6/9.
 */

public class CoreService extends Service {

    private static CoreService coreService;

    public static CoreService sharedInstance() {
        return coreService;
    }

    public static void start() {
        if (CoreService.sharedInstance() == null) {
            try {
                Intent intent = new Intent(AppUtil.ApplicationContext, CoreService.class);
                AppUtil.ApplicationContext.startService(intent);
            } catch (Exception ignored) {
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        coreService = this;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        ProvinceCityBean provinceCityBean = Region.getInstance().getProvinceCityBean();
        if (provinceCityBean == null) {
            readRegionFromFile();
        }else {
            readRegionFromHttp();
        }
        return START_STICKY;
    }

    private void readRegionFromFile() {
        Region.getInstance().readRegionFromFile(new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String jsonRegion = (String) msg.obj;
                onReadRegionFromFileBack(jsonRegion);
            }
        });
    }

    /**
     * 从文件中读取数据，若为null 继续从Asset中获取
     *
     * @param jsonRegion
     */
    void onReadRegionFromFileBack(String jsonRegion) {
        if (!TextUtils.isEmpty(jsonRegion)) {/*文件中读取成功 设置到Region中更新json 取出version请求网络判断是否为最新的版本 */
            Region.getInstance().setProvinceCityBean(jsonRegion, httpHandler);
        } else {/*文件中读取失败  从assets 中继续读取*/
            Region.getInstance().readRegionFromAssets(new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    String jsonRegion = (String) msg.obj;
                    onReadRegionFromAssetsBack(jsonRegion);
                }
            });
        }
    }

    void onReadRegionFromAssetsBack(String jsonRegion) {
        if (!TextUtils.isEmpty(jsonRegion)) {/*从assets中读取成功 设置到Region中更新json*/
            Region.getInstance().setProvinceCityBean(jsonRegion, httpHandler);
        } else {
            readRegionFromHttp();
        }
    }

    private void readRegionFromHttp() {
        Region.getInstance().requestRegion(new OnHttpResult() {
            @Override
            public void onHttpSuccess(String data) {
                Region.getInstance().setProvinceCityBean(data,null);
                Region.getInstance().writeRegion2FileWithThread(data);
            }

            @Override
            public void onHttpFailure(String message) {

            }
        });
    }

    Handler httpHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            readRegionFromHttp();
        }
    };

    @Override
    public void onDestroy() {
        coreService = null;
        stopForeground(true);
        super.onDestroy();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public static void stop() {
        if (CoreService.sharedInstance() != null) {
            coreService.stopSelf();
        }
    }
}
