package com.yc.yclibx.media

import android.media.MediaPlayer
import com.yc.yclibx.file.YcFileUtils
import java.io.File
import android.media.AudioManager
import android.media.audiofx.Visualizer
import com.yc.yclibx.comment.YcLog
import java.io.FileInputStream
import kotlin.math.atan2
import kotlin.math.hypot
import kotlin.math.log10
import kotlin.math.pow


/**
 *
 */
class MediaPlayerUtil {
    private var mMediaPlayer: MediaPlayer? = null
    private var mFilePath = YcFileUtils.SD_PATH + File.separator + "test.mp3"
    fun play() {
        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer()
            mMediaPlayer!!.setOnErrorListener { mp, what, extra ->
                mMediaPlayer!!.reset()
                false
            }
            mMediaPlayer!!.setOnBufferingUpdateListener { mp, percent -> }
        } else {
            mMediaPlayer!!.reset()
            mMediaPlayer!!.release()
        }
        mMediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
        val fio = FileInputStream(File(mFilePath))
        mMediaPlayer!!.setDataSource(fio.getFD())
        mMediaPlayer!!.prepareAsync()
        mMediaPlayer!!.setOnPreparedListener { mMediaPlayer!!.start() }
        visualizer = Visualizer(mMediaPlayer!!.audioSessionId)
        visualizer!!.captureSize = Visualizer.getCaptureSizeRange()[1]
        visualizer!!.setDataCaptureListener(object : Visualizer.OnDataCaptureListener {
            override fun onFftDataCapture(visualizer: Visualizer?, fft: ByteArray?, samplingRate: Int) {
                if (fft != null) {
                    for (i in 0 until fft.size step 2) {
                        val magnitude = hypot(fft[i].toDouble(), fft[i + 1].toDouble())
                        val phase = atan2(fft[i + 1].toDouble(), fft[i].toDouble())
                        val frequency = i * samplingRate / fft.size
                        YcLog.e("幅度：$magnitude  相位：$phase  频率：$frequency")
                    }
                }
            }

            override fun onWaveFormDataCapture(visualizer: Visualizer?, waveform: ByteArray?, samplingRate: Int) {
                if (waveform != null) {
                    var v: Long = 0
                    for (i in 0 until waveform.size) {
                        v += waveform[i].toDouble().pow(2.0).toLong()
                    }
                    val volume = 10 * log10(v / waveform.size.toDouble())
                    YcLog.e("音量:$volume")
                }

            }
        }, Visualizer.getMaxCaptureRate() / 2, true, true)
        visualizer!!.enabled = true //开启采样
        //        // 设置播放完事件
        //        mMediaPlayer!!.setOnCompletionListener(mNextPlayListener)
    }

    var visualizer: Visualizer? = null
    fun end() {
        visualizer?.enabled = false
        visualizer?.release()

    }
}