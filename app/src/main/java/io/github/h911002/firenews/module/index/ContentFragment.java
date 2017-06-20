package io.github.h911002.firenews.module.index;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.github.h911002.firenews.R;
import io.github.h911002.firenews.base.BaseFragment;
import io.github.h911002.firenews.base.mvp.BasePresenter;
import io.github.h911002.firenews.module.index.model.BaseModel;
import io.github.h911002.firenews.module.index.model.News;

public class ContentFragment extends BaseFragment implements AbsListView.OnScrollListener {
    private String key;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ListView mListView;
    private IndexAdapter mIndexAdapter;
    private int mLastVisibleIndex;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x101:
                    if (mSwipeRefreshLayout.isRefreshing()) {
                        mIndexAdapter.notifyDataSetChanged();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                    break;
            }
        }
    };


    @Override
    protected int onContentViewID() {
        return R.layout.index_content_layout;
    }

    @Override
    protected BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    protected void onContentViewCreated(View contentView, Bundle savedInstanceState) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        mListView = (ListView) findViewById(R.id.content_list);
        mIndexAdapter = new IndexAdapter<News>();
        mListView.setAdapter(mIndexAdapter);
        mListView.setOnScrollListener(this);
        View header = View.inflate(getContext(), R.layout.index_header_layout, null);
        View footer = View.inflate(getContext(), R.layout.index_footer_layout, null);
        mListView.addHeaderView(header);
        mListView.addFooterView(footer);

        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                new LoadDataThread().start();
            }
        });
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        if (mIndexAdapter.getCount() == mLastVisibleIndex && i == SCROLL_STATE_IDLE) {
            mSwipeRefreshLayout.setRefreshing(true);
            new LoadDataThread().start();
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mLastVisibleIndex = firstVisibleItem + visibleItemCount - 2;
    }

    class LoadDataThread extends Thread {
        @Override
        public void run() {
            initData();
            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessage(0x101);
        }

        private void initData() {

            mIndexAdapter.addAll(getTestData(mLastVisibleIndex, 20));
        }
    }

    public List<News> getTestData(int start, int count) {
        List<News> data = new ArrayList<>();
        for (int i = start; i < count + start; i++) {
            News news = new News();
            news.setId(i);
            news.setTitle("title " + i);
            data.add(news);
        }
        return data;
    }

    class IndexAdapter<T extends BaseModel> extends BaseAdapter {
        private List<T> mData;

        IndexAdapter() {
            mData = new ArrayList<T>();
        }

        public void setData(List<T> data) {
            mData.clear();
            mData = data;
        }

        public void addAll(List<T> data) {
            mData.addAll(data);
        }

        @Override
        public int getCount() {
            return mData == null ? 0 : mData.size();
        }

        @Override
        public T getItem(int i) {
            return mData == null ? null : mData.get(i);
        }

        @Override
        public long getItemId(int i) {
            return mData.get(i).getId();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder = null;
            if (view == null) {
                view = View.inflate(getContext(), R.layout.index_item_layout, null);
                holder = new ViewHolder();
                holder.title = (TextView) view.findViewById(R.id.item_title);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            News news = (News) getItem(i);
            holder.title.setText(news.getTitle());
            return view;
        }

        class ViewHolder {
            public TextView title;
        }
    }

    public void setKey(String key) {
        this.key = key;
    }
}
