package com.droidhelios.clipboard.listeners;

import android.view.View;

public interface Response {
    interface Callback<T> {
        void onSuccess(T response);
        void onFailure(Exception e);
    }

    interface Status<T> {
        void onSuccess(T response);
    }

    interface NetworkCallback<T> {
        void onCompleted();
        void onDataLoaded();
        void onSuccess(T response);
        void onFailure(Exception e);
    }

    interface OnClickListener<T> {
        void onItemClicked(View view, T item);
    }

    interface Progress {
        void onStartProgressBar();
        void onStopProgressBar();
    }
}
