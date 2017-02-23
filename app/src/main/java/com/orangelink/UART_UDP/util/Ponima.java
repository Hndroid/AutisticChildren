package com.orangelink.UART_UDP.util;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.zhuxiyungu.autisticchildren.R;


public class Ponima {

	// SoundPool sp; //得到一个声音池引用

	public static void initSoundPool(Context context) { // 初始化声音池
		SoundPool sp = new SoundPool(1, // maxStreams参数，该参数为设置同时能够播放多少音效
				AudioManager.STREAM_MUSIC, // streamType参数，该参数设置音频类型，在游戏中通常设置为：STREAM_MUSIC
				0 // srcQuality参数，该参数设置音频文件的质量，目前还没有效果，设置为0为默认值。
		);

		int sound = sp.load(context, R.raw.dahuoji, 1);
		try {
			Thread.sleep(400);// 给予加载声音足够的时间
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 播放声音,参数sound是播放音效的id，参数number是播放音效的次数
		AudioManager am = (AudioManager) context.getSystemService(context.AUDIO_SERVICE);// 实例化AudioManager对象
		float audioMaxVolumn = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC); // 返回当前AudioManager对象的最大音量值

		float volumnRatio = audioMaxVolumn;
		sp.play(sound, // 播放的音乐id
				volumnRatio, // 左声道音量
				volumnRatio, // 右声道音量
				1, // 优先级，0为最低
				0, // 循环次数，0无不循环，-1无永远循环
				1 // 回放速度 ，该值在0.5-2.0之间，1为正常速度
		);
	}
}