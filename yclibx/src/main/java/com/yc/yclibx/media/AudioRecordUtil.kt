package com.yc.yclibx.media

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.provider.MediaStore
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import com.yc.yclibx.comment.YcLog
import com.yc.yclibx.comment.YcLoop
import io.reactivex.disposables.Disposable
import java.util.*
import kotlin.math.log10


/**
 *
 */
class AudioRecordUtil {
    var mAudioRecord: AudioRecord? = null
    val SAMPLE_RATE_IN_HZ = 8000 //采样率（单位：赫兹）
    val BUFFER_SIZE = AudioRecord.getMinBufferSize(SAMPLE_RATE_IN_HZ, AudioFormat.CHANNEL_IN_DEFAULT, AudioFormat.ENCODING_PCM_16BIT)
    var mDisposable: Disposable? = null
    var isGetVoiceRun: Boolean = false
    var mLock: Object = Object()
    fun start() {
        if (isGetVoiceRun) {
            return
        }
        mDisposable?.dispose()

        mAudioRecord = AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE_IN_HZ, AudioFormat.CHANNEL_IN_DEFAULT, AudioFormat.ENCODING_PCM_16BIT, BUFFER_SIZE)
        if (mAudioRecord == null) {
            YcLog.e("mAudioRecord初始化失败")
        }
        isGetVoiceRun = true
        mDisposable = Observable.just(1).observeOn(Schedulers.newThread()) //上游
                .subscribe {
                    mAudioRecord?.startRecording()
                    val buffer = ShortArray(BUFFER_SIZE)
                    while (isGetVoiceRun) {
                        val r = mAudioRecord!!.read(buffer, 0, BUFFER_SIZE)
                        var v: Long = 0
                        for (item in buffer) {
                            v += item * item
                        }
                        val mean = v / r.toDouble()
                        val volume = 10 * log10(mean)
                        YcLog.e("分贝值:$volume")
                        synchronized(mLock) {
                            try {
                                mLock.wait(100)
                            } catch (e: InterruptedException) {
                                e.printStackTrace()
                            }
                        }
                    }
                    mAudioRecord!!.stop()
                    mAudioRecord!!.release()
                    mAudioRecord = null
                }
    }

    fun end() {
        isGetVoiceRun = false
    }
}