package com.example.hit.easysigner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.hit.easysigner.publisher.MonitorActivity;
import com.example.hit.easysigner.utils.DialogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_button_1)
    Button startSign;

    @BindView(R.id.main_button_2)
    Button manualSign;

    @BindView(R.id.main_image_view)
    ImageView mainImageView;

    @OnClick(R.id.main_button_1)
    public void startSignOnClickListener(View view) {
        Intent intent = new Intent(MainActivity.this, MonitorActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.main_button_2)
    public void manualSignOnClickListener(View view) {
        DialogUtil.createAnimationDailog(this, DialogUtil.NETWORK, null).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Glide.with(getApplicationContext()).asDrawable().load(R.drawable.ic_main).into(mainImageView);
    }


}
