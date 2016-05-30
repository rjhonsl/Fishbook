package com.santeh.rjhonsl.fishbook.Main;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.santeh.rjhonsl.fishbook.R;

/**
 * Created by rjhonsl on 5/28/2016.
 */
public class Activity_Comments extends AppCompatActivity {

    Activity activity;
    Context context;

    RecyclerView rvComments;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        activity = this;
        context = Activity_Comments.this;

        rvComments = (RecyclerView) findViewById(R.id.rv_comments);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(activity);
        rvComments.setLayoutManager(mLinearLayoutManager);
        registerForContextMenu(rvComments);

        //add ItemDecoration
//        int VERTICAL_ITEM_SPACE = 40;
//        rvComments.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));


    }



//
//    public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {
//
//        private final int mVerticalSpaceHeight;
//
//        public VerticalSpaceItemDecoration(int mVerticalSpaceHeight) {
//            this.mVerticalSpaceHeight = mVerticalSpaceHeight;
//        }
//
//        @Override
//        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
//                                   RecyclerView.State state) {
//            outRect.bottom = mVerticalSpaceHeight;
//        }
//    }
}
