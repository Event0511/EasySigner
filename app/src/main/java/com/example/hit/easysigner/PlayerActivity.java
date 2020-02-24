package com.example.hit.easysigner;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hit.easysigner.media.IRenderView;
import com.example.hit.easysigner.media.IjkVideoView;
import com.example.hit.easysigner.media.PlayerManager;

import butterknife.BindView;
import butterknife.OnClick;
import tv.danmaku.ijk.media.player.IMediaPlayer;

public class PlayerActivity extends AppCompatActivity {

    @BindView(R.id.text_address)
    EditText text_address;
    @BindView(R.id.btn_address)
    Button BtnConfirmAddress;

    @OnClick(R.id.btn_address)
    public void BtnConfirmAddressOnClickListener(View view) {
        url = text_address.getText().toString();
        player.play(url);
        Toast.makeText(this,"playing:"+text_address.getText().toString(), Toast.LENGTH_SHORT).show();
    }

    private IjkVideoView mVideoView;
    private PlayerManager player;

    private String url = "rtmp://104.199.164.168:1935/record";



    /**
     * 可左半屏滑动控制亮度  右半屏控制音量  双击切换比例  （无提示）
     */
    private void initVideo() {
        player = new PlayerManager(this);
        player.setFullScreenOnly(true);
        player.live(true);
        player.setScaleType(PlayerManager.SCALETYPE_FITPARENT);
        player.playInFullScreen(true);
        player.setPlayerStateListener(new PlayerManager.PlayerStateListener() {
            @Override
            public void onComplete() {
                Log.e("   player  status    :", "complete");
            }

            @Override
            public void onError() {
                Log.e("   player  status    :", "error");
            }

            @Override
            public void onLoading() {
                Log.e("   player  status    :", "loading");
            }

            @Override
            public void onPlay() {
                Log.e("   player  status    :", "play");
            }
        });
        player.play(url);
        IjkVideoView videoView = player.getVideoView();
        videoView.setOnInfoListener(new IMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i1) {
                switch (i) {
                    case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                        break;
                    case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                    case MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:
                        break;
                }
                return false;
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        mVideoView = findViewById(R.id.video_view);

        /** 普通播放 start **/
        mVideoView.setAspectRatio(IRenderView.AR_ASPECT_FIT_PARENT);
        mVideoView.setVideoURI(Uri.parse(url));
        mVideoView.start();
        /** 普通播放 end **/
        initVideo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVideoView.suspend();
    }
}
