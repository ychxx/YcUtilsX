package com.yc.ycutilsx

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.yc.yclibx.adapter.YcAdapterHelper
import com.yc.yclibx.adapter.YcRecyclerViewAdapter
import com.yc.ycutilsx.bean.TestGetPhoneBean
import kotlinx.android.synthetic.main.get_phone_activity.*
import kotlinx.android.synthetic.main.get_phone_item.*


/**
 *
 */
class TestGetPhone : AppCompatActivity() {
    var adapter: YcRecyclerViewAdapter<TestGetPhoneBean>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.get_phone_activity)
        adapter = object : YcRecyclerViewAdapter<TestGetPhoneBean>(this, R.layout.get_phone_item) {
            override fun onUpdate(
                helper: YcAdapterHelper,
                item: TestGetPhoneBean,
                position: Int
            ) {
                getPhoneItemName?.text = item.name
//                getPhoneItemNum?.text = item?.num
            }
        }
        getPhoneRv.adapter = adapter
        getPhoneRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        getPhoneGetTv.setOnClickListener {
            adapter?.addAll(getPhoneInfo())
        }
    }

    /**
     * 获取联系人信息
     */
    @SuppressLint("Recycle")
    private fun getPhoneInfo(): ArrayList<TestGetPhoneBean> {
        val phoneList = ArrayList<TestGetPhoneBean>()
        val PHONE_NAME = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
        val PHONE_NUM = ContactsContract.CommonDataKinds.Phone.NUMBER
        val PHONE_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val PHONE_URI2 = Uri.parse("content://com.android.contacts/raw_contacts")
        val cursor = contentResolver.query(PHONE_URI, null, null, null, null)
        while (cursor!!.moveToNext()) {
            val item = TestGetPhoneBean()
            val contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
            //获取联系人姓名
            val name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
            val name2 = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            item.name = name
            val phoneCursor = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId,
                null,
                null
            );
//            while (phoneCursor.moveToNext()) {
//                String phone = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                phone = phone.replace("-", "");
//                phone = phone.replace(" ", "");
//                temp.phone = phone;
//            }

//            item.num =
//                cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

            phoneList.add(item)
        }
        return phoneList
    }
}
