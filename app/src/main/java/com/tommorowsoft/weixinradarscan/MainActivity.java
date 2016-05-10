package com.tommorowsoft.weixinradarscan;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {
    Button btn;
    ImageView photo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_my_radar_view);
        btn= (Button) findViewById(R.id.btn);
        btn.setBackgroundResource(R.drawable.bt_drawable);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                btn.setBackgroundResource(R.drawable.bt_pressed);;
                finish();
            }

       });

        photo= (ImageView) findViewById(R.id.photo1);
        photo.setPadding(10,10,10,10);
    }
}
