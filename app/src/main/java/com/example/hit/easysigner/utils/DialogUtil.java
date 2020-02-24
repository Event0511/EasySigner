package com.example.hit.easysigner.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.example.hit.easysigner.R;
import com.example.hit.easysigner.app.EasySignerApp;

public class DialogUtil {

    public static final int NETWORK = 0;
    public static final int RESULT = 1;
    private static TextView textView;
    private static ImageView animationView;


    /**
     * 根据传入context创建一个加载dialog,必须运行在主线程
     *
     * @param context 上下文
     * @return 创建的dialog
     */
    public static Dialog createAnimationDailog(final Context context, int type, String text) {
        final Dialog dialog = new Dialog(context, R.style.NetworkProcessDialogTheme);
        switch (type) {
            case NETWORK:
                dialog.setContentView(R.layout.dialog_process_network);
                animationView = dialog.findViewById(R.id.dialog_animation_img);
                textView = dialog.findViewById(R.id.dialog_animation_txt);
                RequestOptions gifOptions = new RequestOptions()
                        .override(ScreenUtil.getScreenWidthPixels() / 5)
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
                Glide.with(context).load(R.drawable.anime_load).apply(gifOptions).into(animationView);
                break;
            case RESULT:
                dialog.setContentView(R.layout.dialog_result);
                animationView = dialog.findViewById(R.id.dialog_img);
                textView = dialog.findViewById(R.id.dialog_txt);
                textView.setText(text);
                break;
            default:
                break;

        }


        /**
         Animation anim = AnimationUtils.loadAnimation(context, R.anim.anime_dialog_network_process_test);
         anim.setRepeatMode(Animation.RESTART);
         anim.setRepeatCount(Animation.INFINITE);
         textView.startAnimation(anim);
         anim.start();

         //animation的循环播放模式不起作用，只能手动的在动画播放完之后再播放一次
         animation.setAnimationListener(new Animation.AnimationListener() {

        @Override public void onAnimationEnd(Animation arg0) {

        anim.setAnimationListener(this);
        }

        @Override public void onAnimationRepeat(Animation arg0) {
        // TODO Auto-generated method stub
        }

        @Override public void onAnimationStart(Animation arg0) {
        // TODO Auto-generated method stub

        }

        });
         // 绑定动画
         textView.startAnimation(animation);
         */
        return dialog;
    }
}
