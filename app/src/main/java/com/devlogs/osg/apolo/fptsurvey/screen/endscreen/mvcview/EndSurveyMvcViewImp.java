package com.devlogs.osg.apolo.fptsurvey.screen.endscreen.mvcview;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.devlogs.osg.apolo.fptsurvey.R;
import com.devlogs.osg.apolo.fptsurvey.screen.common.basemvcview.BaseMvcView;
import com.flipkart.android.proteus.Proteus;
import com.flipkart.android.proteus.ProteusBuilder;
import com.flipkart.android.proteus.ProteusContext;
import com.flipkart.android.proteus.ProteusLayoutInflater;
import com.flipkart.android.proteus.ProteusView;
import com.flipkart.android.proteus.gson.ProteusTypeAdapterFactory;
import com.flipkart.android.proteus.value.Layout;
import com.flipkart.android.proteus.value.ObjectValue;
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.io.StringReader;

public class EndSurveyMvcViewImp extends BaseMvcView<EndSurveyMvcView.Listener> implements EndSurveyMvcView {
    private FrameLayout containerSurveyResult;
    private TextView txtTestTitle;

    public EndSurveyMvcViewImp (LayoutInflater inflater, ViewGroup container) {
        setRootView(inflater.inflate(R.layout.layout_endsurvey, container, false));
        container = findViewById(R.id.layoutContainerSurveyResult);
        txtTestTitle = findViewById(R.id.txtTestTitle);
    }

    @Override
    public void drawUI(String layoutDescriptor, String layoutData) {
        Log.d("tiendang-debug", "Layout Desccriptor: " + layoutDescriptor);
        ViewGroup container = findViewById(R.id.layoutContainerSurveyResult);

        // create a new instance of proteus
        Proteus proteus = new ProteusBuilder().build();

        // register proteus with a ProteusTypeAdapterFactory to deserialize proteus jsons
        ProteusTypeAdapterFactory adapter = new ProteusTypeAdapterFactory(getContext());
        ProteusTypeAdapterFactory.PROTEUS_INSTANCE_HOLDER.setProteus(proteus);

        // deserialize layout and data
        Layout layout;
        ObjectValue data;
        try {
            layout = adapter.LAYOUT_TYPE_ADAPTER.read(new JsonReader(new StringReader(layoutDescriptor)));
            data = adapter.OBJECT_TYPE_ADAPTER.read(new JsonReader(new StringReader(layoutData)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // create a new ProteusLayoutInflater
        ProteusContext context = proteus.createContextBuilder(getContext()).build();
        ProteusLayoutInflater inflater = context.getInflater();

        // Inflate the layout
        ProteusView view = inflater.inflate(layout, data, null, 0);

        // Add the inflated layout into the container
        container.addView(view.getAsView());
    }

    @Override
    public void setTitle(String title) {
        txtTestTitle.setText(title);
    }
}
