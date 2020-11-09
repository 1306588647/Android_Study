package com.example.application;

import android.os.Bundle;

import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RightFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RightFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RightFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RightFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RightFragment newInstance(String param1, String param2) {
        RightFragment fragment = new RightFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_right, container, false);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        //获取主页传来的news对象
        Bundle bundle = this.getArguments();
        News news = (News) bundle.getSerializable("news");

        TextView title = getView().findViewById(R.id.title);
        //使用setText方法设置标题内容，标题内容在该对象中的title变量中，使用getTitle方法获得具体标题
        title.setText(news.getTitle());


        //获取详情界面的source   id并且转化为对象
        TextView source = getView().findViewById(R.id.source);
        //使用setText方法设置来源内容，来源内容在该对象中的source变量中，使用getSource方法获得具体来源
        source.setText(news.getSource());


        //获取详情界面的time   id并且转化为对象
        TextView time = getView().findViewById(R.id.date);
        //使用setText方法设置时间内容，时间内容在该对象中的time变量中，使用getTime方法获得具体时间
        time.setText(news.getTime());


        //获取详情界面的content   id并且转化为对象
        TextView content = getView().findViewById(R.id.content);
        //使用setText方法设置具体内容，具体内容在该对象中的content变量中，使用getContent方法获得具体内容
        content.setText(news.getContent());
    }
}