package com.techyourchance.mvc.screens.common;

import androidx.appcompat.app.AppCompatActivity;

import com.techyourchance.mvc.CustomApplication;
import com.techyourchance.mvc.common.dependencyinjection.CompositionRoot;
import com.techyourchance.mvc.common.dependencyinjection.ControllerCompositionRoot;

public class BaseActivity extends AppCompatActivity {

    private ControllerCompositionRoot mControllerCompositionRoot;

    protected ControllerCompositionRoot getCompositionRoot() {
        if (mControllerCompositionRoot == null) {
            mControllerCompositionRoot = new ControllerCompositionRoot(
                    ((CustomApplication) getApplication()).getCompositionRoot(),
                    this
            );
        }
        return mControllerCompositionRoot;
    }

}
