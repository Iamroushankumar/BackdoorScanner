package com.example.lue.backdoorscanner.callback;


import com.example.lue.backdoorscanner.model.JunkInfo;

import java.util.ArrayList;


public interface IScanCallback {
    void onBegin();

    void onProgress(JunkInfo info);

    void onFinish(ArrayList<JunkInfo> children);
}
