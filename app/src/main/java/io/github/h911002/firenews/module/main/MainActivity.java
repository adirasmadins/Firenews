package io.github.h911002.firenews.module.main;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import io.github.h911002.firenews.R;
import io.github.h911002.firenews.base.BaseActivity;
import io.github.h911002.firenews.base.mvp.IPresenter;
import io.github.h911002.firenews.module.coin.CoinFragment;
import io.github.h911002.firenews.module.index.IndexFragment;
import io.github.h911002.firenews.module.me.MeFragment;
import io.github.h911002.firenews.module.video.VideoFragment;
import io.github.h911002.firenews.support.FragmentSwitcher;
import io.github.h911002.firenews.support.constant.SPConstants;
import io.github.h911002.firenews.support.utils.LogUtils;
import io.github.h911002.firenews.support.utils.PermissionUtils;
import io.github.h911002.firenews.support.utils.PhoneUtils;
import io.github.h911002.firenews.support.utils.SPUtils;

public class MainActivity extends BaseActivity {
    private TabLayout bottomPanel;
    private FragmentSwitcher fragmentSwitcher;
    private static final int[] TAG_TITLES = {R.string.home_tag, R.string.video_tag, R.string.coin_tag, R.string.me_tag};
    private static final int[] TAG_ICONS = {R.mipmap.ic_tab_index, R.mipmap.ic_tab_video, R.mipmap.ic_tab_coin, R.mipmap.ic_tab_me};
    private static final int[] TAG_ICONS_SELECTED = {R.mipmap.ic_tab_index_selected, R.mipmap.ic_tab_video_selected, R.mipmap.ic_tab_coin_selected, R.mipmap.ic_tab_me_selected};


    @Override
    protected IPresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected int onContentViewID() {
        return R.layout.activity_main;
    }

    @Override
    protected void onContentViewCreated(View contentView, Bundle savedInstanceState) {
        initHomeViews();
    }

    public void initHomeViews() {
        initFragment();
        initTabLayout();
        PermissionUtils.requestPermissions(this, REQUEST_PERMISSIONS_CODE, permissions, new PermissionUtils.OnPermissionListener() {


            @Override
            public void onPermissionGranted() {
                LogUtils.d("");
            }

            @Override
            public void onPermissionDenied(String[] deniedPermissions) {
                LogUtils.d("");
            }
        });
    }


    private void initTabLayout() {
        bottomPanel = (TabLayout) findViewById(R.id.tl_bottom_panel);

        for (int i = 0; i < TAG_TITLES.length; i++) {
            TabLayout.Tab tab = bottomPanel.newTab();
            bottomPanel.addTab(tab);

            View tabView = LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
            ImageView tabIcon = (ImageView) tabView.findViewById(R.id.iv_tab_icon);
            TextView tabTitle = (TextView) tabView.findViewById(R.id.iv_tab_title);
            tabTitle.setText(TAG_TITLES[i]);
            tabIcon.setImageResource(TAG_ICONS[i]);
            tab.setCustomView(tabView);
        }

        bottomPanel.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                fragmentSwitcher.switchToFragment(tab.getPosition());
                ImageView imageView = (ImageView) tab.getCustomView().findViewById(R.id.iv_tab_icon);
                imageView.setImageDrawable(getResources().getDrawable(TAG_ICONS_SELECTED[tab.getPosition()]));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ImageView imageView = (ImageView) tab.getCustomView().findViewById(R.id.iv_tab_icon);
                imageView.setImageDrawable(getResources().getDrawable(TAG_ICONS[tab.getPosition()]));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        int defaultIndex = 0;
        ImageView imageView = (ImageView) bottomPanel.getTabAt(defaultIndex).getCustomView().findViewById(R.id.iv_tab_icon);
        imageView.setImageDrawable(getResources().getDrawable(TAG_ICONS_SELECTED[defaultIndex]));
    }

    private void initFragment() {
        fragmentSwitcher =
                new FragmentSwitcher(getSupportFragmentManager(), R.id.fragment_container);
        fragmentSwitcher.addFragment(new IndexFragment(), "IndexFragment");
        fragmentSwitcher.addFragment(new VideoFragment(), "LiveFragment");
        fragmentSwitcher.addFragment(new CoinFragment(), "NewsFragment");
        fragmentSwitcher.addFragment(new MeFragment(), "MeFragment");

        fragmentSwitcher.switchToFragment(0);
    }



    public static final int REQUEST_PERMISSIONS_CODE = 100;
    private String[] permissions = new String[]{Manifest
            .permission.READ_PHONE_STATE};

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_PERMISSIONS_CODE){
            if(permissions != null && permissions.length > 0 && grantResults != null && grantResults.length >0){
                if(Manifest.permission.READ_PHONE_STATE.equals(permissions[0]) && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    SPUtils.getInstance().put(SPConstants.IMEI, PhoneUtils.getIMEI());
                }
            }

        }

    }
}
