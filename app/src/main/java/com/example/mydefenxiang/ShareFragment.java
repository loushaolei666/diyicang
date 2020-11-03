package com.example.mydefenxiang;


import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShareFragment extends Fragment {


    private SmartRefreshLayout sm;
    private RecyclerView recycler;
    int page = 2;
    private List<GirlsBean.ResultsBean> girls;
    private RecyAdapter adapter;

    public ShareFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_share, container, false);
        initView(view);
        initData(page);
        return view;
    }

    private void initData(int page) {
        ApiService service = RetrofitManager.getRetrofitManager().createService(ApiService.class);

        service.getGirls(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GirlsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GirlsBean girlsBean) {

                        List<GirlsBean.ResultsBean> results = girlsBean.getResults();
                        girls.addAll(results);
                        adapter.notifyDataSetChanged();
                        sm.finishLoadMore();
                        sm.finishRefresh();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG", "onError: "+e.getMessage() );
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    private void initView(View view) {
        sm = view.findViewById(R.id.sm);
        girls = new ArrayList<>();
        recycler = view.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL));
        adapter = new RecyAdapter(getActivity(), girls);
        recycler.setAdapter(adapter);
        sm.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                initData(page);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 2;
                girls.clear();
                initData(page);
                adapter.notifyDataSetChanged();
            }
        });
        adapter.setOnViewItemClick(new RecyAdapter.OnViewItemClick() {
            @Override
            public void getClick(int pos) {
                initPop();
            }

            private void initPop() {
                View pop = LayoutInflater.from(getActivity()).inflate(R.layout.pop_view, null);
                final PopupWindow window = new PopupWindow(pop, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                ImageView iv_qq = pop.findViewById(R.id.iv_qq);
                ImageView iv_weixin = pop.findViewById(R.id.iv_weixin);
                iv_qq.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        window.dismiss();
                    }
                });
                iv_weixin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        window.dismiss();
                    }
                });
                window.setBackgroundDrawable(new ColorDrawable());
                window.setOutsideTouchable(true);
                window.showAtLocation(recycler, Gravity.CENTER,0,0);
            }
        });

    }

}
