package info.ruibu.vlcandroiddemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import info.ruibu.util.SystemUtil;

public class MainActivity extends AppCompatActivity {
    private TextInputLayout tilRemoteVideo;
    private EditText etRemoteVideo;

    private final static int PERMISSIONS_REQUEST = 0;
    private final static int VIDEO_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tilRemoteVideo = (TextInputLayout) findViewById(R.id.tilRemoteVideo);

        etRemoteVideo = (EditText) findViewById(R.id.etRemoteVideo);

        //JUMPSMASH的按钮
        ImageButton btnJumpSmash = (ImageButton) findViewById(R.id.imageButton);
        btnJumpSmash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etRemoteVideo.getText().toString().equals("")) {
                    tilRemoteVideo.setError("远程视频地址不能为空！");
                    return;
                }

                Intent intentJumpSmash = new Intent();
                intentJumpSmash.setClass(MainActivity.this, VLCPlayerActivity.class);
                intentJumpSmash.putExtra("VideoType", "Remote");
                intentJumpSmash.putExtra("VideoUrl", etRemoteVideo.getText().toString().concat("/jumpsmash.mpg"));
                startActivity(intentJumpSmash);
            }
        });

        //OW的按钮
        ImageButton btnow = (ImageButton) findViewById(R.id.imageButton3);
        btnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etRemoteVideo.getText().toString().equals("")) {
                    tilRemoteVideo.setError("远程视频地址不能为空！");
                    return;
                }

                Intent intentow = new Intent();
                intentow.setClass(MainActivity.this, VLCPlayerActivity.class);
                intentow.putExtra("VideoType", "Remote");
                intentow.putExtra("VideoUrl", etRemoteVideo.getText().toString().concat("/ow.mpg"));
                startActivity(intentow);
            }
        });

        //PUBG的按钮
        ImageButton btnpubg = (ImageButton) findViewById(R.id.imageButton2);
        btnpubg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etRemoteVideo.getText().toString().equals("")) {
                    tilRemoteVideo.setError("远程视频地址不能为空！");
                    return;
                }

                Intent intentpubg = new Intent();
                intentpubg.setClass(MainActivity.this, VLCPlayerActivity.class);
                intentpubg.putExtra("VideoType", "Remote");
                intentpubg.putExtra("VideoUrl", etRemoteVideo.getText().toString().concat("/pubg.mpg"));
                startActivity(intentpubg);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSIONS_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("*/*");
                    startActivityForResult(intent, VIDEO_REQUEST);
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == VIDEO_REQUEST && data != null) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, VLCPlayerActivity.class);
                intent.putExtra("VideoType", "Local");
                intent.putExtra("VideoUrl", SystemUtil.getPath(MainActivity.this, data.getData()));
                startActivity(intent);
            }
        } catch (Exception e) {
            Log.d("Local", e.toString());
        }
    }
}