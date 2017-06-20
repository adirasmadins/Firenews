package io.github.h911002.firenews.module.me.withdraw;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;

import io.github.h911002.firenews.R;
import io.github.h911002.firenews.base.BaseActivity;
import io.github.h911002.firenews.base.mvp.IPresenter;
import io.github.h911002.firenews.support.utils.ToastUtils;

/**
 * Created by athrun on 17/6/13.
 */

public class WithdrawActivity extends BaseActivity {
    @Override
    protected IPresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected int onContentViewID() {
        return R.layout.activity_withdraw;
    }

    @Override
    protected void onContentViewCreated(View contentView, Bundle savedInstanceState) {
        initWhiteTitle("提现兑换", true);
        setViewClickListener(R.id.tv_exchange, this);
        setViewClickListener(R.id.tv_withdraw, this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_exchange:
                DialogPlus.newDialog(this).setGravity(Gravity.CENTER)
                        .setContentBackgroundResource(R.color.transparent).setContentHolder(new
                        ViewHolder(R.layout.dialog_exchange_coin)).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(DialogPlus dialog, View view) {
                        if (view.getId() == R.id.tv_confirm) {
                            dialog.dismiss();
                            EditText editText = (EditText) dialog.findViewById(R.id.et_coin);
                            ToastUtils.showShort(editText.getText());
                        } else if (view.getId() == R.id.iv_close) {
                            dialog.dismiss();
                        }
                    }
                }).create().show();
                break;
            case R.id.tv_withdraw:
                startActivity(new Intent(this, WithdrawDetailActivity.class));
                break;

        }
    }
}
