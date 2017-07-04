package com.bobo.something.protocol;

/**
 * 跳转到地址选择界面后，需要返回第几层数据的type
 */
public interface AddressLevelType {
    /**
     * 直辖市到第一层,省到第一层
     */
    int TYPE_LEVEL_ONE = 0;
    /**
     * 直辖市到第一层,省到第二层
     */
    int TYPE_LEVEL_ONE_Point_FIVE = 1;
    /**
     * 直辖市到第二层，省到第二层
     */
    int TYPE_LEVEL_TWO = 2;
    /**
     * 直辖市到第二层，省到第三层
     */
    int TYPE_LEVEL_TWO_POINT_FIVE = 3;
    /**
     * 直辖市到第三层，省到第三层
     */
    int TYPE_LEVEL_THREE = 4;
}