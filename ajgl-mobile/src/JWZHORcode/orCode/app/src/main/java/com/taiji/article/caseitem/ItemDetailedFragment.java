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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.taiji.androidui.view.paginglistview.LoadingView;
import com.taiji.article.caseitem.storageData.CaseDetailedShowData;
import com.taiji.article.caseitem.viewmanager.CaseDetailedAdapter;
import com.taiji.article.caseitem.viewmanager.CaseItemSearchTask;
import com.taiji.pubsec.orcode.addressorcode.R;

import orcode.pubsec.taiji.com.business.caseitem.CaseData;


public class ItemDetailedFragment extends Fragment {
    public static ItemDetailedFragment newInstance(String s) {
        ItemDetailedFragment newFragment = new ItemDetailedFragment();
        Bundle bundle = new Bundle();
        bundle.putString("hello", s);
        newFragment.setArguments(bundle);
        return newFragment;

    }
    ListView mListView;
    CaseDetailedAdapter caseDetailedAdapter;
    private NoCaseItemView noCaseItemView;
    private LoadingView loadingView;

    TextView case_item_name_tv, case_item_type_tv, case_name_tv,case_name_code_tv, case_item_person_tv, case_item_file_tv, case_item_title_name_tv;


    int sex=0;
    int abnormity=0;//异常
    int urine=0;//尿检
    int result=0;//处理情况



    TextView level_blue_tv;
    LinearLayout level_blue_lay;
    RelativeLayout collecttion_person_change_alarmType_ly;

    String select_level="0";//0all 1蓝 2白 3橙 4黄 5红

    View mView;

    boolean isAdd=true;


   CaseDetailedShowData caseDetailedShowData;

    boolean isComit=false;

    public ItemDetailedFragment() {
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
        if(getActivity().getResources().getString(R.string.isTestCaseItemSearch).equals("1"))
        {
            refreshTestData();
            return;
        }
        CaseDetailedShowData tempData=new CaseDetailedShowData();
        tempData.caseItemNumber=((CaseArticleActivity)getActivity()).getScanText();
        CaseItemSearchTask rt=new CaseItemSearchTask(getActivity(),"",tempData);
        mListView.removeFooterView(noCaseItemView);
        mListView.addFooterView(loadingView);
        caseDetailedAdapter.clearDataSource();
        rt.setCompleteListener(new CaseItemSearchTask.completeListener() {
            @Override
            public void completeBack(String progressId, String result, CaseDetailedShowData caseDetailedShowData) {
                try {
                    if (result.equals("1")) {
                        mListView.removeFooterView(loadingView);
                        ItemDetailedFragment.this.caseDetailedShowData=caseDetailedShowData;
                        if (caseDetailedShowData != null) {
                           // setTextViewText(case_item_title_name_tv, caseDetailedShowData.caseItemNumber.replace("-","") +"\r\n"+ "物品信息");
                            setTextViewText(case_item_title_name_tv, "物品信息");
                            setTextViewText(case_name_tv, caseDetailedShowData.caseName);
                            setTextViewText(case_item_person_tv, caseDetailedShowData.suspectName);
                            setTextViewText(case_name_code_tv,caseDetailedShowData.caseCode);
                            setTextViewText(case_item_name_tv, caseDetailedShowData.caseItemName);
                            setTextViewText(case_item_type_tv, caseDetailedShowData.earmark);
                            setTextViewText(case_item_file_tv, caseDetailedShowData.papers);



                            if (caseDetailedShowData.l_Detailed != null && caseDetailedShowData.l_Detailed.size() > 0) {
                                if (caseDetailedAdapter != null) {
                                    caseDetailedAdapter.setDataSource(caseDetailedShowData.l_Detailed);
                                    caseDetailedAdapter.notifyDataSetChanged();
                                }
                            } else {
                                mListView.addFooterView(noCaseItemView);
                            }

                        }

                    } else {
                        //showRefreshErr(result);
                        setTextViewText(case_item_title_name_tv, "物品信息加载失败");
                        mListView.addFooterView(noCaseItemView);
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
        if(getActivity().getResources().getString(R.string.isTestCaseItemSearch).equals("1"))
        {
            mListView.removeFooterView(noCaseItemView);
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
        View view = inflater.inflate(R.layout.fg_case_item_detailed, container, false);
        mView=view;

        noCaseItemView = new NoCaseItemView(getActivity());
        loadingView = new LoadingView(getActivity());


        if(caseDetailedAdapter ==null)
        {
            caseDetailedAdapter =new CaseDetailedAdapter();
        }



        case_item_name_tv=(TextView) view.findViewById(R.id.case_item_name_tv);
        setTextViewText(case_item_name_tv,"");
        case_item_type_tv=(TextView) view.findViewById(R.id.case_item_type_tv);
        setTextViewText(case_item_type_tv,"");
        case_name_tv=(TextView) view.findViewById(R.id.case_name_tv);
        setTextViewText(case_name_tv,"");
        case_name_code_tv=(TextView) view.findViewById(R.id.case_name_code_tv);
        setTextViewText(case_name_code_tv,"");
        case_item_person_tv=(TextView) view.findViewById(R.id.case_item_person_tv);
        setTextViewText(case_item_person_tv,"");
        case_item_file_tv=(TextView) view.findViewById(R.id.case_item_file_tv);
        setTextViewText(case_item_file_tv,"");
        case_item_title_name_tv=(TextView) view.findViewById(R.id.case_item_title_name_tv);
        //setTextViewText(case_item_title_name_tv,((CaseArticleActivity)getActivity()).getScanText()+"入库单");
        setTextViewText(case_item_title_name_tv,"正在加载物品信息...");
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
        mListView.setAdapter(caseDetailedAdapter);
        refreshOldData();
        return view;
    }

    private void setTextViewText(TextView tview,String text)
    {
        if(tview!=null )
        {
            if(text==null ||text.trim().equals("null"))
            {
                tview.setText("");
            }
            else
            {
                tview.setText(text);
            }
        }
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
    private class FetchItemsTask extends AsyncTask<Integer, Void, CaseDetailedShowData> {

        @Override
        protected CaseDetailedShowData doInBackground(Integer... params) {
            fetchItemsTaskRun=true;
            Integer count = params[0];
            CaseDetailedShowData tempData=new CaseDetailedShowData();
            tempData.caseCode="AS30000001";
            tempData.caseName="王某一,王某二,王某三非法持有毒品案";
            tempData.suspectName="王某一";
            tempData.papers="筑经开工(刑)扣字(2015)8号";;
            tempData.caseItemNumber=((CaseArticleActivity)getActivity()).getScanText();
            tempData.caseItemName ="海洛因";
            tempData.earmark="白袋子";



            //增加5条固定数据
            //增加5条固定数据
            CaseData tempData1=new CaseData();
            tempData1.DID = "test"+String.valueOf( count+1);
            tempData1.caeItemName = "海洛因";
            tempData1.earmark =  "这个记录巨长这个记录巨长这个记录巨长这个记录巨长这个记录巨长这个记录巨长这个记录巨长";
            tempData1.caseItemNumber = "201600000"+String.valueOf( count+1);
            tempData1.ViewState= 1;
            tempData1.caseItemCountString ="两十包";
            tempData1.caseDoItemCount=8;
            tempData1.areaName="毒品1区";
            tempData1.lockerCode="030101";
            tempData1.lockerName="小型物品藏仓库 三排一层-1";
            tempData.l_Detailed.add(tempData1);

            CaseData tempData2=new CaseData();
            tempData2.DID = "test"+String.valueOf( count+2);
            tempData2.caeItemName = "手机";
            tempData2.earmark =  "绿色VIVO";
            tempData2.caseItemNumber = "201600000"+String.valueOf( count+2);
            tempData2.ViewState= 1;
            tempData2.caseItemCountString ="一部";
            tempData2.caseDoItemCount=0;
            tempData2.areaName="毒品2区";
             tempData2.lockerCode="030101";
              tempData2.lockerName="小型物品藏仓库 三排一层-1";
            tempData.l_Detailed.add(tempData2);

            CaseData tempData3=new CaseData();
            tempData3.DID = "test"+String.valueOf( count+3);
            tempData3.caeItemName = "手机";
            tempData3.earmark =  "蓝色VIVO";
            tempData3.caseItemNumber = "201600000"+String.valueOf( count+3);
            tempData3.ViewState= 1;
            tempData3.caseItemCountString ="共一部";
            tempData3.caseDoItemCount=0;
            tempData3.areaName="毒品3区";
             tempData3.lockerCode="030101";
             tempData3.lockerName="小型物品藏仓库 三排一层-1";
            tempData.l_Detailed.add(tempData3);

            CaseData tempData4=new CaseData();
            tempData4.DID = "test"+String.valueOf( count+4);
            tempData4.caeItemName = "现金";
            tempData4.earmark =  "白色袋子";
            tempData4.caseItemNumber = "201600000"+String.valueOf( count+4);
            tempData4.ViewState= 1;
            tempData4.caseItemCountString ="壹万伍仟元";//"255元";
            tempData4.caseDoItemCount=1;
            tempData4.areaName="毒品4区";
            tempData4.lockerCode="030101";
            tempData4.lockerName="小型物品藏仓库 三排一层-1";
            tempData.l_Detailed.add(tempData4);

            CaseData tempData5=new CaseData();
            tempData5.DID = "test"+String.valueOf( count+5);
            tempData5.caeItemName = "手机";
            tempData5.earmark =  "黑色VIVO";
            tempData5.caseItemNumber = "201600000"+String.valueOf( count+5);
            tempData5.ViewState= 1;
            tempData5.caseItemCountString ="共三部";
            tempData5.caseDoItemCount=2;
            tempData5.areaName="毒品5区";
            tempData5.lockerCode="030101";
            tempData5.lockerName="小型物品藏仓库 三排一层-1";
            tempData.l_Detailed.add(tempData5);

            CaseData tempData6=new CaseData();
            tempData6.DID = "test"+String.valueOf( count+6);
            tempData6.caeItemName = "海洛因";
            tempData6.earmark =  "";
            tempData6.caseItemNumber = "201600000"+String.valueOf( count+6);
            tempData6.ViewState= 1;
            tempData6.caseItemCountString ="两十包";
            tempData6.caseDoItemCount=12;
            tempData6.areaName="毒品6区";
            tempData6.lockerCode="032101";
            tempData6.lockerName="小型物品藏仓库 三排二层-1";
            tempData.l_Detailed.add(tempData6);

            CaseData tempData7=new CaseData();
            tempData7.DID = "test"+String.valueOf( count+7);
            tempData7.caeItemName = "手机21";
            tempData7.earmark =  "绿色VIVO";
            tempData7.caseItemNumber = "201600000"+String.valueOf( count+7);
            tempData7.ViewState= 1;
            tempData7.caseItemCountString ="一部";
            tempData7.caseDoItemCount=0;
            tempData7.areaName="毒品7区";
            tempData7.lockerCode="032101";
            tempData7.lockerName="小型物品藏仓库 三排二层-1";
            tempData.l_Detailed.add(tempData7);

            CaseData tempData8=new CaseData();
            tempData8.DID = "test"+String.valueOf( count+8);
            tempData8.caeItemName = "手机22";
            tempData8.earmark =  "绿色VIVO";
            tempData8.caseItemNumber = "201600000"+String.valueOf( count+8);
            tempData8.ViewState= 1;
            tempData8.caseItemCountString ="一部";
            tempData8.caseDoItemCount=0;
            tempData8.areaName="毒品8区";
            tempData8.lockerCode="032101";
            tempData8.lockerName="小型物品藏仓库 三排二层-1";
            tempData.l_Detailed.add(tempData8);

            CaseData tempData9=new CaseData();
            tempData9.DID = "test"+String.valueOf( count+9);
            tempData9.caeItemName = "手机31";
            tempData9.earmark =  "绿色VIVO";
            tempData9.caseItemNumber = "6911989212589";
            tempData9.ViewState= 1;
            tempData9.caseItemCountString ="一部";
            tempData9.caseDoItemCount=1;
            tempData9.DataType=0;
            tempData9.scanCount=0;
            tempData9.areaName="毒品9区";
            tempData9.lockerCode="9771672909168";
            tempData9.lockerName="小型物品藏仓库 三排三层-1";
            tempData.l_Detailed.add(tempData9);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return tempData;
        }

        protected void onPostExecute(CaseDetailedShowData caseDetailedShowData) {
            fetchItemsTaskRun=false;
            mListView.removeFooterView(loadingView);
            fetchItemsTaskRun=false;
            ItemDetailedFragment.this.caseDetailedShowData = caseDetailedShowData;
            caseDetailedAdapter.addMoreItems(caseDetailedShowData.l_Detailed);
            caseDetailedAdapter.notifyDataSetChanged();
            setTextViewText(case_item_title_name_tv, caseDetailedShowData.caseItemNumber);// + "物品信息");
            setTextViewText(case_name_tv, caseDetailedShowData.caseName);
            setTextViewText(case_item_person_tv, caseDetailedShowData.suspectName);
            setTextViewText(case_name_code_tv,caseDetailedShowData.caseCode);
            setTextViewText(case_item_name_tv, caseDetailedShowData.caseItemName);
            setTextViewText(case_item_type_tv, caseDetailedShowData.earmark);
            setTextViewText(case_item_file_tv, caseDetailedShowData.papers);



            if (caseDetailedShowData.l_Detailed != null && caseDetailedShowData.l_Detailed.size() > 0) {
                if (caseDetailedAdapter != null) {
                    caseDetailedAdapter.setDataSource(caseDetailedShowData.l_Detailed);
                    caseDetailedAdapter.notifyDataSetChanged();
                }
            } else {
                mListView.addFooterView(noCaseItemView);
            }

        }
    }
}

