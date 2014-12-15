package com.tmiyamon.androidsampler.login;

import android.content.Intent;
import android.os.Bundle;

/**
* Created by tmiyamon on 12/16/14.
*/
public interface OnAuthListener {
    public void onSwitch(Class<? extends AuthFragment> fragment, Bundle bundle);
    public void onAuthFinished(Intent intent);
}
