package com.taiji.article.caseitem;


import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.taiji.androidui.view.paginglistview.LoadingView;
import com.taiji.androidui.view.paginglistview.NoDataView;
import com.taiji.article.caseitem.storageData.CaseAdjustmentData;
import com.taiji.article.caseitem.storageData.CaseAdjustmentStorageData;
import orcode.pubsec.taiji.com.business.caseitem.CaseData;

import com.taiji.article.caseitem.storageData.CaseStorageData;
import com.taiji.article.caseitem.viewmanager.CaseAdjustmentAdapter;
import com.taiji.article.caseitem.viewmanager.CaseItemSearchStorageTask;
import com.taiji.pubsec.orcode.addressorcode.R;

import java.util.ArrayList;
import java.util.List;


public class AdjustmentFragment extends Fragment {
    public static AdjustmentFragment newInstance(String s) {
        AdjustmentFragment newFragment = new AdjustmentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("hello", s);
        newFragment.setArguments(bundle);
        return newFragment;

    }
    ListView mListView;
    CaseAdjustmentAdapter caseAdjustmentAdapter;
    private NoDataView noDataView;
    private LoadingView loadingView;
    TextView case_item_title_name_tv,case_item_time_tv, case_item_cause_tv;
    ImageView collecttion_person_item_iv_male;
    ImageView case_item_btn_next_iv;



    TextView level_blue_tv;
    LinearLayout level_blue_lay;
    RelativeLayout collecttion_person_change_alarmType_ly;

    String select_level="0";//0all 1蓝 2白 3橙 4黄 5红

    View mView;

    boolean isAdd=true;


    CaseAdjustmentStorageData caseAdjustmentStorageData;

    boolean isComit=false;

    public AdjustmentFragment() {
        // Required empty public constructor
        //     mRefreshItemsClass = new RefreshItemsClass<String>(new Handler());
        //    testRefreshItemsReceiverTask = new TestRefreshItemsReceiverTask();
    }


    public void setIsAdd(boolean isAdd)
    {
        this.isAdd=isAdd;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onPause() {

        super.onPause();
    }

    @Override
    public void onResume() {

        super.onResume();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
    }

    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        //  FragmentManager fm = getFragmentManager();
        //  Fragment ft=fm.findFragmentByTag(MENU_DYNAMICINFO);
        //  if(ft!=null){
        //      ft.onDetach();
        //    }
        super.onDetach();
    }

    public void refreshOldData()
    {
        if(getActivity().getResources().getString(R.string.isTestCaseItemAdjustment).equals("1"))
        {
            refreshTestData();
            return;
        }
        CaseAdjustmentStorageData tempData=new CaseAdjustmentStorageData();
        tempData.scanformCode=((CaseArticleActivity)getActivity()).getScanText();
        tempData.formCode=tempData.scanformCode;
        tempData.DID= tempData.scanformCode;
        CaseItemSearchStorageTask rt=new CaseItemSearchStorageTask(getActivity(),"",tempData);
        mListView.removeFooterView(noDataView);
        mListView.addFooterView(loadingView);
        caseAdjustmentAdapter.clearDataSource();
        rt.setCompleteListener(new CaseItemSearchStorageTask.completeListener() {
            @Override
            public void completeBack(String progressId, String result, CaseStorageData caseStorageData) {
                try {
                    if (result.equals("1")) {
                        mListView.removeFooterView(loadingView);
                        caseAdjustmentStorageData=(CaseAdjustmentStorageData)caseStorageData;
                        if (caseAdjustmentStorageData != null) {
                            setTextViewText(case_item_title_name_tv, caseAdjustmentStorageData.scanformCode + "调整单");
                            setTextViewText(case_item_time_tv, caseAdjustmentStorageData.changeDate(caseAdjustmentStorageData.DataUpdateTime));
                            setTextViewText(case_item_cause_tv, caseAdjustmentStorageData.cause);



                            if (caseAdjustmentStorageData.getList_caseData() != null && caseAdjustmentStorageData.l_caseData.size() > 0) {
                                caseAdjustmentStorageData.sortCaseData(caseAdjustmentStorageData.getList_caseData());
                                if (caseAdjustmentAdapter != null) {
                                    caseAdjustmentAdapter.setDataSource(caseAdjustmentStorageData.getList_caseData());
                                    caseAdjustmentAdapter.notifyDataSetChanged();
                                    case_item_btn_next_iv.setVisibility(View.VISIBLE);
                                }
                            } else {
                                mListView.addFooterView(noDataView);
                            }

                        }

                    } else {
                        //showRefreshErr(result);
                        setTextViewText(case_item_title_name_tv, "调整单信息加载失败");
                        mListView.removeFooterView(loadingView);
                        mListView.addFooterView(noDataView);
                        if(case_item_btn_next_iv!=null)case_item_btn_next_iv.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // if(mListView!=null) {
                    //    mListView.setIsLoading(false);
                }
            }
        });
        rt.execute();

    }

    private void refreshTestData()
    {
        if(getActivity().getResources().getString(R.string.isTestCaseItemAdjustment).equals("1"))
        {
            mListView.removeFooterView(noDataView);
            mListView.addFooterView(loadingView);
            FetchItemsTask task = new FetchItemsTask();
            task.execute(5, 5);
        }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fg_case_item_adjustment, container, false);
        mView=view;

        noDataView = new NoDataView(getActivity());
        loadingView = new LoadingView(getActivity());


        if(caseAdjustmentAdapter ==null)
        {
            caseAdjustmentAdapter =new CaseAdjustmentAdapter();
            caseAdjustmentAdapter.setViewClick(new CaseAdjustmentAdapter.viewClickListener() {
                @Override
                public void textClick(String showText) {
                    try {
                        if (showText == null) return;
                        Toast.makeText(getActivity(), showText, Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                    }
                }
            });
        }

        case_item_btn_next_iv=(ImageView) view.findViewById(R.id.case_item_btn_next_iv);
        case_item_btn_next_iv.setVisibility(View.GONE);
        case_item_btn_next_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(caseAdjustmentStorageData==null)
                {
                    return;
                }
                Intent intent = new Intent(getActivity(), CaseScanShelfActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("showType", 4);//1入 2出 3返 4调整出 5调整入 6物品详细 7货架详细
                if(caseAdjustmentStorageData!=null)
                    mBundle.putSerializable("CaseStorageData", caseAdjustmentStorageData);
                intent.putExtras(mBundle);
                //startActivityForResult(intent, 106);
                startActivity(intent);
                getActivity().finish();
            }
        });


        case_item_time_tv=(TextView) view.findViewById(R.id.case_item_time_tv);
        setTextViewText(case_item_time_tv,"");
        case_item_cause_tv=(TextView) view.findViewById(R.id.case_item_cause_tv);
        setTextViewText(case_item_cause_tv,"");

        case_item_title_name_tv=(TextView) view.findViewById(R.id.case_item_title_name_tv);
        //setTextViewText(case_item_title_name_tv,((CaseArticleActivity)getActivity()).getScanText()+"入库单");
        setTextViewText(case_item_title_name_tv,"正在加载调整单信息...");
/*
        TextView collecttion_person_iv_alarmtype=(TextView) view.findViewById(R.id.collecttion_person_iv_alarmtype);
        collecttion_person_iv_alarmtype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collecttion_person_change_alarmType_ly = (RelativeLayout) mView.findViewById(R.id.collecttion_person_change_alarmType_ly);
                collecttion_person_change_alarmType_ly.setVisibility(View.VISIBLE);
            }
        });
*/
        mListView=(ListView) view.findViewById(R.id.orderfeedback_detail_lv);
        mListView.setAdapter(caseAdjustmentAdapter);
        refreshOldData();
        return view;
    }

    private void setTextViewText(TextView tview,String text)
    {
        if(tview!=null )
            tview.setText(text);
    }


    private void sendSuc()
    {
        Toast.makeText(getActivity(), "信息已保存", Toast.LENGTH_LONG).show();
        ((CaseArticleActivity) getActivity()).onBackPressed();
    }

    private void sendFail()
    {
        Toast.makeText(getActivity(), "信息保存失败", Toast.LENGTH_LONG).show();
    }



    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data==null)
        {
            return;
        }
        if (resultCode == 0 && requestCode==151)
        {

        }
        else if (resultCode == 1 && requestCode==151)
        {

        }
        else if (requestCode == 2)
        {

        }
    }

    private String getFilePathByUri (Uri uri )
    {
        final String scheme = uri.getScheme();
        String dataStr = "";
        if ( scheme == null )
            dataStr = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) )
        {
            dataStr = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) )
        {
            Cursor cursor = getActivity().getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        dataStr = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return dataStr;
    }






    private class MyTextWatcher implements TextWatcher
    {
        public int next_tv_id=0;
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }


        @Override
        public void afterTextChanged(Editable s) {
            if(s.length() == 2){
                if(next_tv_id!=0)
                {
                    TextView macText_tv = (TextView) mView.findViewById(next_tv_id);
                    macText_tv.setFocusable(true);
                    macText_tv.setFocusableInTouchMode(true);
                    macText_tv.requestFocus();
                }
            }
        }
    }


    boolean fetchItemsTaskRun=false;
    private class FetchItemsTask extends AsyncTask<Integer, Void, CaseAdjustmentStorageData> {

        @Override
        protected CaseAdjustmentStorageData doInBackground(Integer... params) {
            fetchItemsTaskRun=true;
            Integer count = params[0];
            CaseAdjustmentStorageData tempData=new CaseAdjustmentStorageData();
            tempData.scanformCode=((CaseArticleActivity)getActivity()).getScanText();
            tempData.formCode=tempData.scanformCode;
            tempData.DID= tempData.scanformCode;
            tempData.caseCode="AS30000001";
            tempData.caseName="王某一,王某二,王某三非法持有毒品案";
            tempData.cause="整体调整毒品案在库位置 将3,4排货架毒品类物品调整到专属位置";
            tempData.papers="筑经开工(刑)扣字(2015)8号";
            List<CaseData> result = new ArrayList<CaseData>();

            tempData.l_caseData=result;
            //增加5条固定数据
            //增加5条固定数据
            CaseAdjustmentData tempData1=new CaseAdjustmentData();
            tempData1.DID = "test"+String.valueOf( count+1);
            tempData1.caeItemName = "海洛因";
            tempData1.earmark =  "这个记录巨长这个记录巨长这个记录巨长这个记录巨长这个记录巨长这个记录巨长这个记录巨长";
            tempData1.caseItemNumber = "201600000"+String.valueOf( count+1);
            tempData1.ViewState= 4;
            tempData1.caseItemCountString ="8包";
            tempData1.caseItemHasCount=8;
            tempData1.caseDoItemCount=8;
            tempData1.lockerOldCode="020101";
            tempData1.lockerOldName="小型物品藏仓库 二排一层-1";
            tempData1.lockerCode="030101";
            tempData1.lockerName="小型物品藏仓库 三排一层-1";
            result.add(tempData1);

            CaseAdjustmentData tempData2=new CaseAdjustmentData();
            tempData2.DID = "test"+String.valueOf( count+2);
            tempData2.caeItemName = "手机";
            tempData2.earmark =  "绿色VIVO";
            tempData2.caseItemNumber = "201600000"+String.valueOf( count+2);
            tempData2.ViewState= 4;
            tempData2.caseItemCountString ="1部";
            tempData2.caseItemHasCount=1;
            tempData2.caseDoItemCount=0;
            tempData2.lockerOldCode="020101";
            tempData2.lockerOldName="小型物品藏仓库 二排一层-1";
            tempData2.lockerCode="030101";
            tempData2.lockerName="小型物品藏仓库 三排一层-1";
            result.add(tempData2);

            CaseAdjustmentData tempData3=new CaseAdjustmentData();
            tempData3.DID = "test"+String.valueOf( count+3);
            tempData3.caeItemName = "手机";
            tempData3.earmark =  "蓝色VIVO";
            tempData3.caseItemNumber = "201600000"+String.valueOf( count+3);
            tempData3.ViewState= 4;
            tempData3.caseItemCountString ="1部";
            tempData3.caseItemHasCount=1;
            tempData3.caseDoItemCount=0;
            tempData3.lockerOldCode="030101";
            tempData3.lockerOldName="小型物品藏仓库 三排一层-1";
            tempData3.lockerCode="030101";
            tempData3.lockerName="小型物品藏仓库 三排一层-1";
            result.add(tempData3);

            CaseAdjustmentData tempData4=new CaseAdjustmentData();
            tempData4.DID = "test"+String.valueOf( count+4);
            tempData4.caeItemName = "现金";
            tempData4.earmark =  "白色袋子";
            tempData4.caseItemNumber = "201600000"+String.valueOf( count+4);
            tempData4.ViewState= 4;
            tempData4.caseItemCountString ="1袋";//"255元";
            tempData4.caseItemHasCount=1;
            tempData4.caseDoItemCount=1;
            tempData4.lockerOldCode="020101";
            tempData4.lockerOldName="小型物品藏仓库 二排一层-1";
            tempData4.lockerCode="030101";
            tempData4.lockerName="小型物品藏仓库 三排一层-1";
            result.add(tempData4);

            CaseAdjustmentData tempData5=new CaseAdjustmentData();
            tempData5.DID = "test"+String.valueOf( count+5);
            tempData5.caeItemName = "手机";
            tempData5.earmark =  "黑色VIVO";
            tempData5.caseItemNumber = "201600000"+String.valueOf( count+5);
            tempData5.ViewState= 4;
            tempData5.caseItemCountString ="3部";
            tempData5.caseItemHasCount=3;
            tempData5.caseDoItemCount=0;
            tempData5.lockerOldCode="020101";
            tempData5.lockerOldName="小型物品藏仓库 二排一层-1";
            tempData5.lockerCode="030101";
            tempData5.lockerName="小型物品藏仓库 三排一层-1";
            result.add(tempData5);

            CaseAdjustmentData tempData6=new CaseAdjustmentData();
            tempData6.DID = "test"+String.valueOf( count+6);
            tempData6.caeItemName = "海洛因";
            tempData6.earmark =  "";
            tempData6.caseItemNumber = "201600000"+String.valueOf( count+1);
            tempData6.ViewState= 4;
            tempData6.caseItemCountString ="12包";
            tempData6.caseItemHasCount=12;
            tempData6.caseDoItemCount=12;
            tempData6.lockerOldCode="020201";
            tempData6.lockerOldName="小型物品藏仓库 二排二层-1";
            tempData6.lockerCode="032101";
            tempData6.lockerName="小型物品藏仓库 三排二层-1";
            result.add(tempData6);

            CaseAdjustmentData tempData7=new CaseAdjustmentData();
            tempData7.DID = "test"+String.valueOf( count+7);
            tempData7.caeItemName = "手机21";
            tempData7.earmark =  "绿色VIVO";
            tempData7.caseItemNumber = "201600000"+String.valueOf( count+7);
            tempData7.ViewState= 4;
            tempData7.caseItemCountString ="1部";
            tempData7.caseItemHasCount=1;
            tempData7.caseDoItemCount=0;
            tempData7.lockerOldCode="030201";
            tempData7.lockerOldName="小型物品藏仓库 三排二层-1";
            tempData7.lockerCode="030201";
            tempData7.lockerName="小型物品藏仓库 三排二层-1";
            result.add(tempData7);

            CaseAdjustmentData tempData8=new CaseAdjustmentData();
            tempData8.DID = "test"+String.valueOf( count+8);
            tempData8.caeItemName = "手机22";
            tempData8.earmark =  "绿色VIVO";
            tempData8.caseItemNumber = "201600000"+String.valueOf( count+8);
            tempData8.ViewState= 4;
            tempData8.caseItemCountString ="1部";
            tempData8.caseItemHasCount=1;
            tempData8.caseDoItemCount=0;
            tempData8.lockerOldCode="030201";
            tempData8.lockerOldName="小型物品藏仓库 三排二层-1";
            tempData8.lockerCode="030201";
            tempData8.lockerName="小型物品藏仓库 三排二层-1";
            result.add(tempData8);

            CaseAdjustmentData tempData9=new CaseAdjustmentData();
            tempData9.DID = "test"+String.valueOf( count+9);
            tempData9.caeItemName = "手机31";
            tempData9.earmark =  "绿色VIVO";
            tempData9.caseItemNumber = "6911989212589";
            tempData9.ViewState= 4;
            tempData9.caseItemCountString ="2部";
            tempData9.caseItemHasCount=2;
            tempData9.caseDoItemCount=0;
            tempData9.DataType=0;
            tempData9.scanCount=0;
            tempData9.lockerOldCode="030201";
            tempData9.lockerOldName="小型物品藏仓库 三排二层-1";
            tempData9.lockerCode="9771672909168";
            tempData9.lockerName="小型物品藏仓库 三排三层-1";
            result.add(tempData9);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return tempData;
        }

        protected void onPostExecute(CaseAdjustmentStorageData caseStorageData) {
            fetchItemsTaskRun=false;
            mListView.removeFooterView(loadingView);
            fetchItemsTaskRun=false;
            caseAdjustmentStorageData =(CaseAdjustmentStorageData)caseStorageData;
            caseAdjustmentStorageData.sortCaseData(caseAdjustmentStorageData.l_caseData);
            caseAdjustmentAdapter.addMoreItems(caseAdjustmentStorageData.l_caseData);
            setTextViewText(case_item_title_name_tv,caseAdjustmentStorageData.scanformCode+"调整单");
            setTextViewText(case_item_time_tv,caseAdjustmentStorageData.changeDate(caseAdjustmentStorageData.DataUpdateTime));
            setTextViewText(case_item_cause_tv,caseAdjustmentStorageData.cause);

            //caseOutAdapter.addMoreItems(result);


            caseAdjustmentAdapter.notifyDataSetChanged();
            case_item_btn_next_iv.setVisibility(View.VISIBLE);
        }
    }
}

