package io.github.h911002.firenews.support;

import android.support.annotation.AnimRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment切换类
 */
public class FragmentSwitcher {
    private List<Fragment> fragments = new ArrayList<>();
    private List<String> tags = new ArrayList<>();
    private FragmentManager fragmentManager;
    private int containerResId;
    private int currentIndex = -1;

    @AnimRes
    private int enter = -1;
    @AnimRes
    private int exit = -1;
    @AnimRes
    private int popEnter = -1;
    @AnimRes
    private int popExit = -1;

    public FragmentSwitcher(FragmentManager fragmentManager, int containerResId) {
        this.fragmentManager = fragmentManager;
        this.containerResId = containerResId;
    }

    /**
     * 设置通用切换动画
     *
     * @param enter
     * @param exit
     */
    public void setCustomAnimations(@AnimRes int enter, @AnimRes int exit) {
        this.enter = enter;
        this.exit = exit;
    }

    /**
     * 设置通用切换动画
     *
     * @param enter
     * @param exit
     * @param popEnter
     * @param popExit
     */
    public void setCustomAnimations(@AnimRes int enter, @AnimRes int exit,
                                    @AnimRes int popEnter, @AnimRes int popExit) {
        this.setCustomAnimations(enter, exit);
        this.popEnter = popEnter;
        this.popExit = popExit;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    /**
     * 添加Fragment
     *
     * @param f
     * @param tag
     */
    public void addFragment(Fragment f, String tag) {
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment == null) {
            fragment = f;
        }
        if (!fragments.contains(fragment)) {
            fragments.add(fragment);
            tags.add(tag);
        }
    }

    /**
     * 获取fragment
     *
     * @param index
     * @param <T>
     * @return 目标getFragment
     */
    public <T extends Fragment> T getFragment(int index) {
        return index < fragments.size() ? (T) fragments.get(index) : null;
    }

    /**
     * 切换fragment
     *
     * @param index
     */
    public void switchToFragment(int index) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentByTag(tags.get(index));
        applyAnimations(fragmentTransaction);
        if (fragment == null) {
            fragment = fragments.get(index);
            fragmentTransaction.add(containerResId, fragment, tags.get(index));
        }
        if (index != currentIndex) {
            for (int i = 0; i < fragments.size(); i++) {
                if (i != index) {
                    Fragment f = fragmentManager.findFragmentByTag(tags.get(i));
                    if (f != null) {
                        fragmentTransaction.hide(fragments.get(i));
                    }
                }
            }
        }

        fragmentTransaction.show(fragment);

        currentIndex = index;
        fragmentTransaction.commitAllowingStateLoss();
        //        fragmentTransaction.commit();
        fragmentManager.executePendingTransactions();
    }

    private void applyAnimations(FragmentTransaction transaction) {
        if (enter == -1 || exit == -1) {
            return;
        }
        if (popEnter == -1 || popExit == -1) {
            transaction.setCustomAnimations(enter, exit);
        } else {
            transaction.setCustomAnimations(enter, exit, popEnter, popExit);
        }
    }

    public int getFragmentSize() {
        return fragments.size();
    }
}
