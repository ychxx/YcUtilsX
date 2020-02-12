package com.yc.ycutilsx;

import android.content.Context;
import android.util.Log;

import com.yc.yclibx.file.YcFileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 */
public class Temp {
    public static boolean copy(Context context) {
        try {
            String[] fileNames = context.getAssets().list("temp");
            if (fileNames == null || fileNames.length <= 0) {
                return false;
            }
            File file;
            for (String fileName : fileNames) {
                file = YcFileUtils.createFile(YcFileUtils.SD_PATH + File.separator + "zhao_pian" + File.separator + fileName);
                InputStream inputStream = context.getAssets().open("temp" + File.separator + fileName);
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int byteRead;
                while (-1 != (byteRead = inputStream.read(buffer))) {
                    fileOutputStream.write(buffer, 0, byteRead);
                }
                inputStream.close();
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
