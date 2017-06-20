package io.github.h911002.firenews.module.me;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnClickListener;
import com.zhy.adapter.abslistview.MultiItemTypeAdapter;
import com.zhy.adapter.abslistview.ViewHolder;
import com.zhy.adapter.abslistview.base.ItemViewDelegate;

import io.github.h911002.firenews.R;
import io.github.h911002.firenews.base.BaseFragment;
import io.github.h911002.firenews.base.mvp.BasePresenter;
import io.github.h911002.firenews.module.login.LoginActivity;
import io.github.h911002.firenews.module.me.setting.SettingActivity;
import io.github.h911002.firenews.module.me.withdraw.WithdrawActivity;
import io.github.h911002.firenews.support.utils.ActivityUtils;
import io.github.h911002.firenews.support.utils.EmptyUtils;

import static io.github.h911002.firenews.module.me.MenuConfig.menuItemData;
import static io.github.h911002.firenews.module.me.MenuConfig.subMenuLists;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends BaseFragment implements MeFragmentPresenter.MeFragmentView {

    private MenuListView menuListView;

    public MeFragment() {
        // Required empty public constructor
    }


    @Override
    protected BasePresenter onCreatePresenter() {
        return new MeFragmentPresenter(this, null);
    }

    @Override
    protected int onContentViewID() {
        return R.layout.fragment_me;
    }

    @Override
    protected void onContentViewCreated(View contentView, Bundle savedInstanceState) {
        initMenuList();
        initListener();
    }

    private void initMenuList() {
        menuListView = (MenuListView) findViewById(R.id.menu_list);

        final MultiItemTypeAdapter adapter = new MultiItemTypeAdapter(getActivity(), menuItemData);
        adapter.addItemViewDelegate(new ItemViewDelegate<MenuItem>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.list_item_me_group;
            }

            @Override
            public boolean isForViewType(MenuItem item, int position) {
                return item.type == 1;
            }

            @Override
            public void convert(ViewHolder holder, MenuItem menuItem, int position) {
                holder.setText(R.id.tv_group_title, menuItemData.get(position).title);
                holder.setImageResource(R.id.iv_group_icon, menuItem.icon);
                holder.setImageResource(R.id.iv_group_indicator, menuItem.isExpanded ? R.mipmap
                        .ic_arrow_down_grey : R.mipmap.ic_arrow_right_grey);
            }
        });
        adapter.addItemViewDelegate(new ItemViewDelegate<MenuItem>() {
            @Override
            public int getItemViewLayoutId() {
                return R.layout.list_item_me_group;
            }

            @Override
            public boolean isForViewType(MenuItem item, int position) {
                return item.type == 2;
            }

            @Override
            public void convert(ViewHolder holder, MenuItem menuItem, int position) {
                holder.setText(R.id.tv_group_title, menuItemData.get(position).title);
            }
        });


        menuListView.setAdapter(adapter);
        menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MenuItem item = menuItemData.get(position);
                if("每日任务".equals(item.title)){
                    DialogPlus dialog = DialogPlus.newDialog(getActivity())
                            .setGravity(Gravity.CENTER)
                            .setCancelable(true)
                            .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.dialog_sign_success))
                            .setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(DialogPlus dialog, View view) {
                                    if(view.getId() == R.id.iv_close){
                                        dialog.dismiss();
                                    }
                                }
                            }).setContentBackgroundResource(R.color.transparent)
                            .setContentWidth(ViewGroup.LayoutParams.WRAP_CONTENT)
                            .create();
                    dialog.show();
                } else if (subMenuLists.get(item.title) != null) {
                    item.isExpanded = !item.isExpanded;
                    if (item.isExpanded) {
                        menuItemData.addAll(position + 1, subMenuLists.get(item.title));
                    } else {
                        int size = subMenuLists.get(item.title).size();
                        for (int i = 0; i < size; i++) {
                            menuItemData.remove(position + 1);
                        }
                    }
                    adapter.notifyDataSetChanged();
                } else if (!EmptyUtils.isEmpty(item.url)) {
                    ActivityUtils.startActivity(getActivity(), item.url);
                }

            }
        });

    }

    private void initListener() {
        setViewClickListener(R.id.iv_setting, this);
        setViewClickListener(R.id.iv_logo, this);
        setViewClickListener(R.id.tv_withdraw,this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.iv_setting:
                ActivityUtils.startActivity(getActivity(), SettingActivity.class);
                break;
            case R.id.iv_logo:
                ActivityUtils.startActivity(getActivity(), LoginActivity.class);
                break;
            case R.id.tv_withdraw:
                ActivityUtils.startActivity(getActivity(), WithdrawActivity.class);
                break;


        }
    }
}
