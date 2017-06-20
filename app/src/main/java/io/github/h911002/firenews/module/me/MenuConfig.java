package io.github.h911002.firenews.module.me;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import io.github.h911002.firenews.R;

/**
 * Created by athrun on 17/6/4.
 */

public class MenuConfig {

    public static List<MenuItem> menuItemData = new ArrayList<>();
    public static HashMap<String, List<MenuItem>> subMenuLists = new HashMap<>();

    static {
        MenuItem menuItem1 = new MenuItem("收入明细", R.mipmap.me_shourumingxi, "io.github.h911002.firenews.module.me.earning.EarningActivity", 1);
        MenuItem menuItem2 = new MenuItem("邀请好友", R.mipmap.me_yaoqinghaoyou, "io.github.h911002.firenews.module.me.invitation.InvitationActivity", 1);
        MenuItem menuItem3 = new MenuItem("排行榜", R.mipmap.me_paihangbang, "io.github.h911002.firenews.module.me.rank.RankActivity", 1);
        MenuItem menuItem4 = new MenuItem("每日任务", R.mipmap.me_meirirenwu, "", 1);
        MenuItem menuItem5 = new MenuItem("徒弟明细", R.mipmap.me_tudimingxi, "", 1);
        MenuItem menuItem6 = new MenuItem("绑定微信", R.mipmap.me_bangdingweixin, "", 1);
        MenuItem menuItem7 = new MenuItem("绑定手机", R.mipmap.me_bangdingshouji, "io.github.h911002.firenews.module.me.bind.phone.BindPhoneActivity", 1);
        MenuItem menuItem8 = new MenuItem("输入邀请码", R.mipmap.me_shuruyaoqingma, "", 1);
        MenuItem menuItem9 = new MenuItem("赠送金币", R.mipmap.me_zengsongjinbi, "io.github.h911002.firenews.module.me.presenter.PresenterCoinActivity", 1);

        MenuItem taskItem1 = new MenuItem("每日签到", 0, "io.github.h911002.firenews.support.WebViewActivity", 2);
        subMenuLists.put("每日任务", Arrays.asList(taskItem1));

        menuItemData.add(menuItem1);
        menuItemData.add(menuItem2);
        menuItemData.add(menuItem3);
        menuItemData.add(menuItem4);
        menuItemData.add(menuItem5);
        menuItemData.add(menuItem6);
        menuItemData.add(menuItem7);
        menuItemData.add(menuItem8);
        menuItemData.add(menuItem9);
    }
}
