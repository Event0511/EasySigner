/*
 *
 *  *
 *  *  * Copyright (C) 2017 ChillingVan
 *  *  *
 *  *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  *  * you may not use this file except in compliance with the License.
 *  *  * You may obtain a copy of the License at
 *  *  *
 *  *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *  *
 *  *  * Unless required by applicable law or agreed to in writing, software
 *  *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  * See the License for the specific language governing permissions and
 *  *  * limitations under the License.
 *  *
 *
 */

package com.example.hit.easysigner.publisher;

import android.app.Dialog;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.hit.easysigner.MonitorUI;
import com.example.hit.easysigner.R;
import com.example.hit.easysigner.utils.DialogUtil;
import com.example.hit.easysigner.utils.HttpUtil;

import java.io.IOException;
import java.util.LinkedList;

import me.lake.librestreaming.core.listener.RESConnectionListener;
import me.lake.librestreaming.filter.hardvideofilter.BaseHardVideoFilter;
import me.lake.librestreaming.filter.hardvideofilter.HardVideoGroupFilter;
import me.lake.librestreaming.ws.StreamAVOption;
import me.lake.librestreaming.ws.StreamLiveCameraView;
import me.lake.librestreaming.ws.filter.hardfilter.GPUImageBeautyFilter;
import me.lake.librestreaming.ws.filter.hardfilter.WatermarkFilter;
import me.lake.librestreaming.ws.filter.hardfilter.extra.GPUImageCompatibleFilter;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static org.litepal.LitePalApplication.getContext;

public class MonitorActivity extends AppCompatActivity {

    private static final String TAG = MonitorActivity.class.getSimpleName();
    private StreamLiveCameraView mLiveCameraView;
    private StreamAVOption streamAVOption;
    private String rtmpUrl = "rtmp://35.229.147.152:1935/record";
     //       "rtmp://ossrs.net/" + StatusBarUtils.getRandomAlphaString(3) + '/' + StatusBarUtils.getRandomAlphaDigitString(5);

    private MonitorUI mMonitorUI;

    private Dialog networkProgressDialog;
    private Button btnGetResult;

    public static final int START_PUBLISH = 1;
    public static final int QUERY_FROM_SERVER = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);
        initLiveConfig();
        mMonitorUI = new MonitorUI(this, mLiveCameraView, rtmpUrl);
        btnGetResult = findViewById(R.id.btn_getResult);
        btnGetResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                queryFromServer("http://35.229.147.152:18080/get","text");
            }
        });
    }

    /**
     * 设置推流参数
     */
    public void initLiveConfig() {
        mLiveCameraView = findViewById(R.id.stream_preview_view);

        //参数配置 start
        streamAVOption = new StreamAVOption();
        streamAVOption.streamUrl = rtmpUrl;
        //参数配置 end

        mLiveCameraView.init(this, streamAVOption);
        mLiveCameraView.addStreamStateListener(resConnectionListener);
        LinkedList<BaseHardVideoFilter> files = new LinkedList<>();
        files.add(new GPUImageCompatibleFilter(new GPUImageBeautyFilter()));
//        files.add(new WatermarkFilter(BitmapFactory.decodeResource(getResources(), R.mipmap.live),new Rect(100,100,200,200)));
        mLiveCameraView.setHardVideoFilter(new HardVideoGroupFilter(files));
    }

    RESConnectionListener resConnectionListener = new RESConnectionListener() {
        @Override
        public void onOpenConnectionResult(int result) {
            //result 0成功  1 失败
            Toast.makeText(MonitorActivity.this,"打开推流连接 状态："+result+ " 推流地址："+rtmpUrl,Toast.LENGTH_LONG).show();
        }

        @Override
        public void onWriteError(int errno) {
            Toast.makeText(MonitorActivity.this,"推流出错,请尝试重连",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCloseConnectionResult(int result) {
            //result 0成功  1 失败
            Toast.makeText(MonitorActivity.this,"关闭推流连接 状态："+result,Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLiveCameraView.destroy();
    }

    /**
     * 根据传入的第一和类型从服务器上查询数据
     */
    private void queryFromServer(final String address, final String type) {
        networkProgressDialog = DialogUtil.createAnimationDailog(MonitorActivity.this, DialogUtil.NETWORK, null);
        if (!isFinishing()) {
            networkProgressDialog.show();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    HttpUtil.sendOkHttpRequest(address + "?type=" + type, queryCallback);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private Callback queryCallback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            // 回到主线程处理逻辑
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    networkProgressDialog.dismiss();
                    Toast.makeText(getContext(), "呜...加载失败了QAQ", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            final String responseText = response.body().string();
            boolean result = true;
            if (result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        networkProgressDialog.dismiss();
                        DialogUtil.createAnimationDailog(MonitorActivity.this, DialogUtil.RESULT, responseText).show();
                    }
                });
            }
        }
    };

}
