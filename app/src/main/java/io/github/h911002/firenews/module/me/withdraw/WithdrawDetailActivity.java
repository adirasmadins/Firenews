package io.github.h911002.firenews.module.me.withdraw;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.Arrays;

import io.github.h911002.firenews.R;
import io.github.h911002.firenews.base.BaseActivity;
import io.github.h911002.firenews.base.mvp.IPresenter;

public class WithdrawDetailActivity extends BaseActivity {

    private RecyclerView recyclerView;

    @Override
    protected IPresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected int onContentViewID() {
        return R.layout.activity_withdraw_detail;
    }

    @Override
    protected void onContentViewCreated(View contentView, Bundle savedInstanceState) {
        initWhiteTitle("提现", true);
        recyclerView = (RecyclerView) findViewById(R.id.rv_nums);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(new CommonAdapter<String>(this, R.layout.item_withdraw, Arrays
                .asList("1元", "10元", "50元", "100元", "200元", "500元")) {

            @Override
            protected void convert(final ViewHolder holder, String s, int position) {
                holder.setText(R.id.tv_nums, s);
                holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        View tv = holder.getView(R.id.tv_nums);
                        tv.setSelected(!tv.isSelected());
                    }
                });
            }
        });
    }
}
