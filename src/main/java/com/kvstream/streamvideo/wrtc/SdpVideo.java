package com.kvstream.streamvideo.wrtc;

import android.util.Log;
import org.webrtc.SdpObserver;
import org.webrtc.SessionDescription;

public class SdpVideo implements SdpObserver {

    protected static final String TAG = SdpVideo.class.getSimpleName();

    @Override
    public void onCreateSuccess(SessionDescription sessionDescription) {
        Log.d(TAG, "onCreateSuccess(): SDP=" + sessionDescription.description);
    }

    @Override
    public void onSetSuccess() {
        Log.d(TAG, "onSetSuccess(): SDP");
    }

    @Override
    public void onCreateFailure(String error) {
        Log.e(TAG, "onCreateFailure(): Error=" + error);
    }

    @Override
    public void onSetFailure(String error) {
        Log.e(TAG, "onSetFailure(): Error=" + error);

    }
}
