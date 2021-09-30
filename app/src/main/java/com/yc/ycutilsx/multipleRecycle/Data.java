package com.yc.ycutilsx.multipleRecycle;

/**
 * Creator: yc
 * Date: 2021/9/30 16:28
 * UseDes:
 */

public class Data {
    String type;
    Object itemData;

    public Data(String type, Object itemData) {
        this.type = type;
        this.itemData = itemData;
    }

    static class ItemData1 {
        String name = "ItemData1";
    }

    static class ItemData2 {
        String test = "ItemData2";
    }

    static class ItemData3 {
        String proId = "ItemData3";
    }
}
