package com.yc.ycutilsx;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yc.yclibx.adapter.YcAdapter;
import com.yc.yclibx.adapter.YcAdapterHelper;
import com.yc.yclibx.adapter.YcRecyclerViewAdapter;
import com.yc.yclibx.comment.YcLog;
import com.yc.yclibx.file.YcFileStreamUtils;
import com.yc.yclibx.file.YcFileUtils;
import com.yc.ycutilsx.bean.TestGetPhoneBean;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class TestGetPhone2 extends AppCompatActivity {
    YcRecyclerViewAdapter<TestGetPhoneBean> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_phone_activity);
        adapter = new YcRecyclerViewAdapter<TestGetPhoneBean>(this, R.layout.get_phone_item) {
            @Override
            public void onUpdate(YcAdapterHelper helper, TestGetPhoneBean item, int position) {
                helper.setText(R.id.getPhoneItemName, item.getName());
                helper.setText(R.id.getPhoneItemNum, item.getNum().get(0));
                helper.setOnClickListener(R.id.getPhoneItemCall, v -> {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + item.getNum()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                });
            }
        };
        RecyclerView recyclerView = findViewById(R.id.getPhoneRv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        get();
    }

    private void get() {
        String res = "";
        InputStream fileInputStream = null;
        try {
            fileInputStream = getResources().getAssets().open("temp.json");
            int length = fileInputStream.available();
            byte[] buffer = new byte[length];
            fileInputStream.read(buffer);
            res = new String(buffer, "UTF-8");
            fileInputStream.close();
        } catch (Exception e) {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            e.printStackTrace();
        }
        adapter.addAll(new Gson().fromJson(res, new TypeToken<List<TestGetPhoneBean>>() {
        }.getType()));
    }

    private void add(ArrayList<TestGetPhoneBean> arrayList) {
        String filePath = YcFileUtils.SD_PATH + File.separator + "temp.txt";
        YcFileStreamUtils.writeFile(filePath, new Gson().toJson(arrayList));
//        File file = new File("temp.json");
//        try {
//            FileOutputStream fileOutputStream = openFileOutput("temp.json", MODE_PRIVATE);
//            byte[] bytes = new Gson().toJson(arrayList).getBytes();
//            fileOutputStream.write(bytes);
//            fileOutputStream.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    public ArrayList<TestGetPhoneBean> getAllContacts(Context context) {
        ArrayList<TestGetPhoneBean> contacts = new ArrayList<TestGetPhoneBean>();
        Cursor cursor = context.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC");
        while (cursor.moveToNext()) {
            //新建一个联系人实例
            TestGetPhoneBean temp = new TestGetPhoneBean();
            String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            //获取联系人姓名
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            temp.setName(name);
            //获取联系人所有电话号码（一个联系人可能有多个电话）
            Cursor phoneCursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId, null, null);
            while (phoneCursor.moveToNext()) {
                String phone = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                phone = phone.replace("-", "");
                phone = phone.replace(" ", "");
                temp.getNum().add(phone);
            }
            contacts.add(temp);
            //记得要把cursor给close掉
            phoneCursor.close();
        }
        cursor.close();
        return contacts;
    }
}
