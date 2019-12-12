package com.droidhelios.clipboard.copy;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.droidhelios.clipboard.AppApplication;
import com.droidhelios.clipboard.MainActivity;
import com.droidhelios.clipboard.R;
import com.droidhelios.clipboard.database.Clipboard;
import com.droidhelios.clipboard.util.ClipboardUtil;
import com.droidhelios.clipboard.util.TaskRunner;


public class CopiedActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String EXTRA_COPIED_TEXT = "copied_text";
    private TextView tvMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_copied);
        initUi();
        getIntentData(getIntent());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void getIntentData(Intent intent) {
        if (intent != null) {
            String copiedText = intent.getStringExtra(EXTRA_COPIED_TEXT);

            if (!TextUtils.isEmpty(copiedText)) {
                tvMessage.setText(copiedText);
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState,@NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    private void initUi() {
        tvMessage = findViewById(R.id.tvMessage);

        findViewById(R.id.llCopied).setOnClickListener(this);
        findViewById(R.id.llCopiedBody).setOnClickListener(this);
        findViewById(R.id.llDictionary).setOnClickListener(this);
        findViewById(R.id.ivSave).setOnClickListener(this);
        findViewById(R.id.ivClose).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivSave:
                saveToDatabase(tvMessage.getText().toString());
                break;
            case R.id.llCopied:
                finish();
                break;
            case R.id.ivClose:
                finish();
                break;
            case R.id.llCopiedBody:
                openDictionaryApp();
                break;
            case R.id.llDictionary:
                openDictionaryApp();
                break;
        }
    }

    private void saveToDatabase(final String message) {
        AppApplication.databaseManager.insert(new Clipboard(message), new TaskRunner.Callback<Long>() {
            @Override
            public void onComplete(Long result) {
                ClipboardUtil.sendBroadcastMessage(getApplicationContext());
            }
        });
    }

    private void openDictionaryApp() {
        startActivity(new Intent(CopiedActivity.this, MainActivity.class));
        finish();
    }

}
