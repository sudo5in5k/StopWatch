package com.dotinstall.a0723stopwatchkai;

import static org.junit.Assert.*;

import android.icu.text.SimpleDateFormat;
import android.support.v7.app.AlertController;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Locale;

/**
 * TODO クラス説明
 *
 * Created by sho on 2017/11/30.
 */
public class MainActivityTest {

    MainActivity mMainActivity = new MainActivity();
    SimpleDateFormat sdf = new SimpleDateFormat("01:01.001", Locale.US);

    @Test
    public void convertDateToStr() throws Exception {
        assertEquals("01:01.001", mMainActivity.convertDateToStr());
    }

}