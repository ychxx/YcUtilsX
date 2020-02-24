package com.hc.testaudio.util

import android.media.MediaRecorder
import com.yc.yclibx.comment.YcLog
import com.yc.yclibx.comment.YcLoop
import com.yc.yclibx.file.YcFileUtils
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import java.io.File
import java.util.concurrent.TimeUnit
import kotlin.math.log10

/**
 *
 */
class MediaRecorderUtil {
    private var mMediaRecorder: MediaRecorder? = null
    private val MAX_LENGTH = 1000 * 60 * 10 // 最大录音时长1000*60*10;
    var mSaveFilePath: String =
        YcFileUtils.SD_PATH + File.separator + "temp" + File.separator + "test.mp3"

    fun start() {
        if (mMediaRecorder == null) {
            mMediaRecorder = MediaRecorder()
        }
        //设置音频采集方式(MIC从麦克风采集)
        mMediaRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
        //设置在录制过程中产生的输出文件的格式 AAC/AMR_NB/AMR_MB/Default 声音的（波形）的採样
        mMediaRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB)
        //②设置输出文件的格式：THREE_GPP/MPEG-4/RAW_AMR/Default THREE_GPP(3gp格式。H263视频/ARM音频编码)、
        // MPEG-4、RAW_AMR(仅仅支持音频且音频编码要求为AMR_NB)
        mMediaRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        YcFileUtils.createFile(mSaveFilePath)
        mMediaRecorder!!.setOutputFile(mSaveFilePath) //设置录音文件输出路径
        mMediaRecorder!!.setMaxDuration(MAX_LENGTH)
        mMediaRecorder!!.prepare()
        mMediaRecorder!!.start()
        updateData()
    }

    fun end(): Boolean {
        return if (mMediaRecorder == null) {
            false
        } else {
            mMediaRecorder!!.stop()
            mMediaRecorder!!.reset()
            mMediaRecorder!!.release()
            true
        }
    }

    //    var loop = YcLoop()
    var mDisposable: Disposable? = null

    private fun updateData() {
        mDisposable?.dispose()
        mDisposable = Observable.interval(0, 200, TimeUnit.MILLISECONDS).subscribe { aLong ->
            val ratio = (mMediaRecorder!!.maxAmplitude).toDouble()
            val db: Double
            if (ratio > 1) {
                db = 20 * log10(ratio)
            } else {
                db = 0.0
            }
            YcLog.e("分贝值：$db")
        }
    }
}