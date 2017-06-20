package com.bobo.something.data;

import android.os.Handler;
import android.os.Message;

import com.bobo.something.utils.AppUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by huangbo on 2017/6/20.
 *
 * 主要是缓存的工具类
 *
 * 缓存设计：
 *         0.从内存中读取数据 ：0.1 读取成功-> 取出versionCode ->3
 *                             0.2 读取失败-> 1
 *
 *         1.从文件中读取数据：1.1读取成成功-> 取出versionCode ->3
 *                             1.2读取失败-> 2
 *         2.从Assets中读取数据：2.1读取成功-> 取出versionCode ->3
 *                                2.2读取失败-> versionCode==0 ->3
 *
 *         3.用versionCode请求网络 3.1请求成功（有版本更新）将文件写入内存，写入文件；
 *                                 3.1 请求失败，（没有版本更新）
 *
 */

public class CacheData {

    public static CacheData cacheData;

    public static CacheData getInstance() {
        if (cacheData == null) {
            cacheData = new CacheData();
        }
        return cacheData;
    }

    String mFileName;

    public CacheData cacheName(String mFileName) {
        this.mFileName = mFileName;
        return this;
    }


    ExecutorService cachedThreadPool;

    private CacheData() {
        cachedThreadPool = Executors.newCachedThreadPool();
    }


    /**
     * 从assets 中读取文件
     *
     * @return cacheData 的Json串
     */
    private String readDataFromAssets() {
        try {
            InputStream ips = AppUtil.ApplicationContext.getAssets().open(mFileName);
            byte[] bytes = new byte[ips.available()];
            ips.read(bytes);
            ips.close();
            return new String(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public void readDataFromAssets(final Handler handler) {
        cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                String json = readDataFromAssets();
                Message message = handler.obtainMessage(1, json);
                handler.sendMessage(message);
            }
        });
    }
    public void readDataFromFile(final Handler handler) {
        cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                Message message = handler.obtainMessage(1, readDataFromFile());
                handler.sendMessage(message);
            }
        });

    }

    /**
     * 将region 更新到指定文件里
     * @param
     */

    public void writeData2FileWithThread(final String Data) {
        cachedThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                writeRegion2File(Data);
            }
        });
    }

    /**
     * @return cacheData 的Json串
     */

    private String readDataFromFile() {
        try {
            File file = new File(AppUtil.getCacheDirectory(), mFileName);
            if (!file.exists()) {
                return null;
            }
            FileInputStream fis = new FileInputStream(file);
            byte[] bytes = new byte[fis.available()];
            fis.read(bytes);
            fis.close();
            return new String(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";

    }

    private void writeRegion2File(String regionJsonData) {
        try {
            File cityFile = new File(AppUtil.getCacheDirectory(), mFileName);
            if (!cityFile.exists()) {
                cityFile.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(cityFile);
            fos.write(regionJsonData.getBytes("UTF-8"));
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
