package com.droidhelios.clipboard;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.droidhelios.clipboard.copy.CopiedService;
import com.droidhelios.clipboard.floating.FloatingViewService;
import com.droidhelios.clipboard.util.ClipboardUtil;
import com.droidhelios.clipboard.util.SharedPrefUtil;

public class MainActivity extends AppCompatActivity {
    private static final int CODE_DRAW_OVER_OTHER_APP_PERMISSION = 2084;
    private Switch swClipboard, swCopy;
    private Button btnOpen;
    private TextView tvOpenTag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (SharedPrefUtil.isEnableAutoCopy()) {
            ClipboardUtil.startCopyService(this);
        }

        initUi();
        updateUi();
    }

    private void updateUi() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(this)) {
            tvOpenTag.setText("Clipboard Floating Screen");
            btnOpen.setText("Open");
        }
    }

    private void initUi() {
        tvOpenTag = findViewById(R.id.tv_open_tag);
        btnOpen = findViewById(R.id.btn_open);
        swClipboard = findViewById(R.id.switch_clipboard);
        swCopy = findViewById(R.id.switch_copy);

        swClipboard.setChecked(SharedPrefUtil.isEnableClipboard());
        swClipboard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPrefUtil.setEnableClipboard(isChecked);
                if (isChecked) {
                    btnOpen.performClick();
                } else {
                    ClipboardUtil.stopService(MainActivity.this, FloatingViewService.class);
                }
            }
        });
        swCopy.setChecked(SharedPrefUtil.isEnableAutoCopy());
        swCopy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPrefUtil.setEnableAutoCopy(isChecked);
                if (isChecked) {
                    ClipboardUtil.startCopyService(MainActivity.this);
                } else {
                    ClipboardUtil.stopService(MainActivity.this, CopiedService.class);
                }
            }
        });

        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(MainActivity.this)) {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:" + getPackageName()));
                    startActivityForResult(intent, CODE_DRAW_OVER_OTHER_APP_PERMISSION);
                } else {
                    startService(new Intent(MainActivity.this, FloatingViewService.class));
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODE_DRAW_OVER_OTHER_APP_PERMISSION) {

            //Check if the permission is granted or not.
            if (resultCode == RESULT_OK) {
            btnOpen.performClick();
            } else { //Permission is not available
//                Toast.makeText(this,
//                        "Draw over other app permission not available. Closing the application",
//                        Toast.LENGTH_SHORT).show();
                finish();
                startActivity(new Intent(this, MainActivity.class));
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
