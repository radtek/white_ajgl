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

import com.taiji.androidui.view.paginglistview.NoView;
import orcode.pubsec.taiji.com.business.caseitem.CaseData;
import com.taiji.article.caseitem.storageData.CaseScanData;
import com.taiji.article.caseitem.storageData.CaseShelfData;
import com.taiji.article.caseitem.viewmanager.CaseScanAdapter;
import com.taiji.pubsec.orcode.addressorcode.R;

import java.util.ArrayList;
import java.util.List;


public class InFragmentScan extends Fragment {

    public static InFragmentScan newInstance(String s) {
        InFragmentScan newFragment = new InFragmentScan();
        Bundle bundle = new Bundle();
        bundle.putString("hello", s);
        newFragment.setArguments(bundle);
        return newFragment;

    }
    ListView mListView;
    CaseScanAdapter caseInScanAdapter;


    TextView level_blue_tv;
    LinearLayout level_blue_lay;
    RelativeLayout collecttion_person_change_alarmType_ly;

    String select_level="0";//0all 1蓝 2白 3橙 4黄 5红

    View mView;
    NoView noVIew;

    boolean isAdd=true;

    //AppendFileData appendFileData;

    boolean isComit=false;

    public InFragmentScan() {
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
        if(caseInScanAdapter ==null)
        {
            caseInScanAdapter.UnCaseInScanAdapter();
            //caseScanAdapter=null;
        }

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

    public void refreshOldInstruction()
    {
        if(getActivity().getResources().getString(R.string.isTestCaseItemIn).equals("1"))
        {
            refreshTestInstruction();
            return;
        }

    }

    private void refreshTestInstruction()
    {
        if(getActivity().getResources().getString(R.string.isTestCaseItemIn).equals("1"))
        {
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
        View view = inflater.inflate(R.layout.fg_case_item_in_scan, container, false);
        mView=view;

        if(caseInScanAdapter ==null)
        {
            caseInScanAdapter =new CaseScanAdapter(getActivity());
        }
        noVIew=new NoView(getActivity());
        mListView=(ListView) view.findViewById(R.id.case_item_info_scan_lv);
        mListView.addFooterView(noVIew);
        mListView.setAdapter(caseInScanAdapter);
        refreshOldInstruction();

        ImageView collecttion_person_iv_alarmtype=(ImageView) view.findViewById(R.id.btn_in_item_new_shelf_iv);
        collecttion_person_iv_alarmtype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CaseScanData tempc3 = new CaseScanData();
                tempc3.caseShelfData = new CaseShelfData();

                tempc3.caseShelfData.DID = "test" + (caseInScanAdapter.getCount() + 1);
                tempc3.caseShelfData.lockerName = "新仓库" + (caseInScanAdapter.getCount() + 1);
                tempc3.caseShelfData.list_CaseData = new ArrayList<CaseData>();
                List<CaseScanData> newItems = new ArrayList<CaseScanData>();
                newItems.add(tempc3);
                caseInScanAdapter.addMoreItems(newItems);
            }
        });
        return view;
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
    private class FetchItemsTask extends AsyncTask<Integer, Void, List<CaseScanData>> {

        @Override
        protected List<CaseScanData> doInBackground(Integer... params) {
            fetchItemsTaskRun=true;
            Integer count = 0;//params[0];
            List<CaseScanData> result = new ArrayList<CaseScanData>();
            //增加3条固定数据
            CaseScanData tempc1=new CaseScanData();
            tempc1.caseShelfData =new CaseShelfData();
            tempc1.caseShelfData.DID = "test"+String.valueOf( count+1);
            tempc1.caseShelfData.lockerName = "小型物品藏仓库 三排一层-1";
            tempc1.caseShelfData.list_CaseData=new ArrayList<CaseData>();

            //增加5条固定数据
            CaseData tempData1=new CaseData();
            tempData1.DID = "test"+String.valueOf( count+1);
            tempData1.caeItemName = "海洛因";
            tempData1.earmark =  "";
            tempData1.caseItemNumber = "201600000"+String.valueOf( count+1);//待审核
            tempData1.ViewState= 1;
            tempData1.caseItemCountString ="两包";
            tempc1.caseShelfData.list_CaseData.add(tempData1);

            CaseData tempData2=new CaseData();
            tempData2.DID = "test"+String.valueOf( count+1);
            tempData2.caeItemName = "手机";
            tempData2.earmark =  "绿色VIVO";
            tempData2.caseItemNumber = "201600000"+String.valueOf( count+1);//待审核
            tempData2.ViewState= 1;
            tempData2.caseItemCountString ="一部";
            tempc1.caseShelfData.list_CaseData.add(tempData2);

            CaseData tempData3=new CaseData();
            tempData3.DID = "test"+String.valueOf( count+1);
            tempData3.caeItemName = "手机";
            tempData3.earmark =  "蓝色VIVO";
            tempData3.caseItemNumber = "201600000"+String.valueOf( count+1);//待审核
            tempData3.ViewState= 1;
            tempData3.caseItemCountString ="一部";
            tempc1.caseShelfData.list_CaseData.add(tempData3);

            CaseData tempData4=new CaseData();
            tempData4.DID = "test"+String.valueOf( count+1);
            tempData4.caeItemName = "手机";
            tempData4.earmark =  "白色VIVO";
            tempData4.caseItemNumber = "201600000"+String.valueOf( count+1);//待审核
            tempData4.ViewState= 1;
            tempData4.caseItemCountString ="一部";
            tempc1.caseShelfData.list_CaseData.add(tempData4);

            CaseData tempData5=new CaseData();
            tempData5.DID = "test"+String.valueOf( count+1);
            tempData5.caeItemName = "手机";
            tempData5.earmark =  "黑色VIVO";
            tempData5.caseItemNumber = "201600000"+String.valueOf( count+1);//待审核
            tempData5.ViewState= 1;
            tempData5.caseItemCountString ="一部";
            tempc1.caseShelfData.list_CaseData.add(tempData5);

            CaseData tempData6=new CaseData();
            tempData6.DID = "test6";
            tempData6.caeItemName = "手机6";
            tempData6.caseItemNumber = "2016000006";
            tempData6.ViewState= 1;
            tempData6.caseItemCountString ="6部";
            tempc1.caseShelfData.list_CaseData.add(tempData6);

            CaseData tempData7=new CaseData();
            tempData7.DID = "test7";
            tempData7.caeItemName = "手机7";
            tempData7.caseItemNumber = "2016000007";
            tempData7.ViewState= 1;
            tempData7.caseItemCountString ="7部";
            tempc1.caseShelfData.list_CaseData.add(tempData7);

            CaseData tempData8=new CaseData();
            tempData8.DID = "test8";
            tempData8.caeItemName = "手机8";
            tempData8.caseItemNumber = "2016000008";
            tempData8.ViewState= 1;
            tempData8.caseItemCountString ="8部";
            tempc1.caseShelfData.list_CaseData.add(tempData8);

            CaseData tempData9=new CaseData();
            tempData9.DID = "test9";
            tempData9.caeItemName = "手机9";
            tempData9.caseItemNumber = "2016000009";
            tempData9.ViewState= 1;
            tempData9.caseItemCountString ="9部";
            tempc1.caseShelfData.list_CaseData.add(tempData9);

            CaseData tempData10=new CaseData();
            tempData10.DID = "test10";
            tempData10.caeItemName = "手机10";
            tempData10.caseItemNumber = "20160000010";
            tempData10.ViewState= 1;
            tempData10.caseItemCountString ="10部";
            tempc1.caseShelfData.list_CaseData.add(tempData10);

            result.add(tempc1);

            //增加3条固定数据
            CaseScanData tempc2=new CaseScanData();
            tempc2.caseShelfData =new CaseShelfData();
            tempc2.caseShelfData.DID = "test"+String.valueOf( count+1);
            tempc2.caseShelfData.lockerName = "小型物品藏仓库 三排一层-2";
            tempc2.caseShelfData.list_CaseData=new ArrayList<CaseData>();

            //增加5条固定数据
            CaseData tempData12=new CaseData();
            tempData12.DID = "test"+String.valueOf( count+1);
            tempData12.caeItemName = "海洛因2";
            tempData12.earmark =  "";
            tempData12.caseItemNumber = "201600000"+String.valueOf( count+1);//待审核
            tempData12.ViewState= 1;
            tempData12.caseItemCountString ="两包";
            tempc2.caseShelfData.list_CaseData.add(tempData12);

            CaseData tempData22=new CaseData();
            tempData22.DID = "test"+String.valueOf( count+1);
            tempData22.caeItemName = "手机2";
            tempData22.earmark =  "绿色VIVO2";
            tempData22.caseItemNumber = "201600000"+String.valueOf( count+1);//待审核
            tempData22.ViewState= 1;
            tempData22.caseItemCountString ="一部";
            tempc2.caseShelfData.list_CaseData.add(tempData22);

            CaseData tempData32=new CaseData();
            tempData32.DID = "test"+String.valueOf( count+1);
            tempData32.caeItemName = "手机2";
            tempData32.earmark =  "蓝色VIVO2";
            tempData32.caseItemNumber = "201600000"+String.valueOf( count+1);//待审核
            tempData32.ViewState= 1;
            tempData32.caseItemCountString ="一部";
            tempc2.caseShelfData.list_CaseData.add(tempData32);

            result.add(tempc2);


            //增加3条固定数据
            CaseScanData tempc3=new CaseScanData();
            tempc3.caseShelfData =new CaseShelfData();
            tempc3.caseShelfData.DID = "test"+String.valueOf( count+1);
            tempc3.caseShelfData.lockerName = "小型物品藏仓库 三排一层-3";
            tempc3.caseShelfData.list_CaseData=new ArrayList<CaseData>();

            //增加5条固定数据
            CaseData tempData13=new CaseData();
            tempData13.DID = "test"+String.valueOf( count+1);
            tempData13.caeItemName = "海洛因3";
            tempData13.earmark =  "";
            tempData13.caseItemNumber = "201600000"+String.valueOf( count+1);//待审核
            tempData13.ViewState= 1;
            tempData13.caseItemCountString ="两包";
           // tempc3.caseShelfData.list_CaseData.add(tempData13);

            CaseData tempData23=new CaseData();
            tempData23.DID = "test"+String.valueOf( count+1);
            tempData23.caeItemName = "手机3";
            tempData23.earmark =  "绿色VIVO2";
            tempData23.caseItemNumber = "201600000"+String.valueOf( count+1);//待审核
            tempData23.ViewState= 1;
            tempData23.caseItemCountString ="一部";
         //   tempc3.caseShelfData.list_CaseData.add(tempData23);

            CaseData tempData33=new CaseData();
            tempData33.DID = "test"+String.valueOf( count+1);
            tempData33.caeItemName = "手机3";
            tempData33.earmark =  "蓝色VIVO3";
            tempData33.caseItemNumber = "201600000"+String.valueOf( count+1);//待审核
            tempData33.ViewState= 1;
            tempData33.caseItemCountString ="一部";
          //  tempc3.caseShelfData.list_CaseData.add(tempData33);

            result.add(tempc3);


            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return result;
        }

        protected void onPostExecute(List<CaseScanData> result) {
            fetchItemsTaskRun=false;
            caseInScanAdapter.addMoreItems(result);


            caseInScanAdapter.notifyDataSetChanged();

        }
    }
}

