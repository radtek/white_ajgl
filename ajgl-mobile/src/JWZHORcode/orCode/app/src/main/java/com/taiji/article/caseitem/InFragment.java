package  com.taiji.article.caseitem;


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
import orcode.pubsec.taiji.com.business.caseitem.CaseData;
import com.taiji.article.caseitem.storageData.CaseInStorageData;
import com.taiji.article.caseitem.storageData.CaseStorageData;
import com.taiji.article.caseitem.viewmanager.CaseInAdapter;
import com.taiji.article.caseitem.viewmanager.CaseItemSearchStorageTask;
import com.taiji.pubsec.orcode.addressorcode.R;

import java.util.ArrayList;
import java.util.List;


public class InFragment extends Fragment {

    public static InFragment newInstance(String s) {
        InFragment newFragment = new InFragment();
        Bundle bundle = new Bundle();
        bundle.putString("hello", s);
        newFragment.setArguments(bundle);
        return newFragment;

    }
    ListView mListView;
    CaseInAdapter caseInAdapter;
    private NoDataView noDataView;
    private LoadingView loadingView;

    TextView case_item_time_tv, case_item_number_tv,case_item_name_tv,case_item_person_tv,case_item_file_tv,case_item_title_name_tv;
    ImageView case_item_btn_next_iv;
    ImageView collecttion_person_item_iv_male;




    TextView level_blue_tv;
    LinearLayout level_blue_lay;
    RelativeLayout collecttion_person_change_alarmType_ly;

    String select_level="0";//0all 1蓝 2白 3橙 4黄 5红

    View mView;

    boolean isAdd=true;

    CaseInStorageData caseInStorageData;

    //AppendFileData appendFileData;

    boolean isComit=false;

    public InFragment() {
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
        if(getActivity().getResources().getString(R.string.isTestCaseItemIn).equals("1"))
        {
            refreshTestData();
            return;
        }
        CaseInStorageData tempData=new CaseInStorageData();
        tempData.scanformCode=((CaseArticleActivity)getActivity()).getScanText();
        tempData.formCode=tempData.scanformCode;
        tempData.DID= tempData.scanformCode;
        CaseItemSearchStorageTask rt=new CaseItemSearchStorageTask(getActivity(),"",tempData);
        mListView.removeFooterView(noDataView);

        mListView.addFooterView(loadingView);
        caseInAdapter.clearDataSource();
        rt.setCompleteListener(new CaseItemSearchStorageTask.completeListener() {
            @Override
            public void completeBack(String progressId, String result,CaseStorageData caseStorageData) {
                try {
                    if (result.equals("1")) {
                        mListView.removeFooterView(loadingView);
                        caseInStorageData=(CaseInStorageData)caseStorageData;
                        if(caseInStorageData!=null)
                        {
                            setTextViewText(case_item_title_name_tv,caseInStorageData.scanformCode+"入库单");
                            setTextViewText(case_item_time_tv,caseInStorageData.changeDate(caseInStorageData.DataUpdateTime));
                            setTextViewText(case_item_number_tv,caseInStorageData.caseCode);
                            setTextViewText(case_item_name_tv,caseInStorageData.caseName);
                            setTextViewText(case_item_person_tv,caseInStorageData.suspectName);
                            setTextViewText(case_item_file_tv,caseInStorageData.papers);


                            if(caseInStorageData.l_caseData!=null && caseInStorageData.l_caseData.size()>0)
                            {
                                caseInStorageData.sortCaseData(caseInStorageData.l_caseData);
                                if(caseInAdapter!=null)
                                {
                                    caseInAdapter.setDataSource(caseInStorageData.l_view_show_caseData);
                                    caseInAdapter.notifyDataSetChanged();
                                    case_item_btn_next_iv.setVisibility(View.VISIBLE);
                                }
                            }
                            else
                            {
                                mListView.addFooterView(noDataView);
                            }

                        }

                    }
                    else{
                        //showRefreshErr(result);
                        setTextViewText(case_item_title_name_tv, "入库单信息加载失败");
                        mListView.removeFooterView(loadingView);
                        mListView.addFooterView(noDataView);
                        if(case_item_btn_next_iv!=null)case_item_btn_next_iv.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    // if(mListView!=null) {
                    //    mListView.setIsLoading(false);
                }
            }
        });
        rt.execute();
    }

    private void refreshTestData()
    {
        if(getActivity().getResources().getString(R.string.isTestCaseItemIn).equals("1"))
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
        View view = inflater.inflate(R.layout.fg_case_item_in, container, false);
        mView=view;

        noDataView = new NoDataView(getActivity());
        loadingView = new LoadingView(getActivity());
        if(caseInAdapter ==null)
        {
            caseInAdapter =new CaseInAdapter();
            caseInAdapter.setViewClick(new CaseInAdapter.viewClickListener() {

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
        mListView=(ListView) view.findViewById(R.id.case_in_item_detail_lv);
        mListView.setAdapter(caseInAdapter);
        refreshOldData();

        LinearLayout case_in_item_detail_title_lv=(LinearLayout) view.findViewById(R.id.case_in_item_detail_title_lv);
        case_in_item_detail_title_lv.setVisibility(View.GONE);

        case_item_btn_next_iv=(ImageView) view.findViewById(R.id.case_item_btn_next_iv);
        case_item_btn_next_iv.setVisibility(View.GONE);
        case_item_btn_next_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(caseInStorageData==null)
                {
                    return;
                }
                Intent intent = new Intent(getActivity(), CaseScanShelfActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("showType", 1);//1入 2出 3返 4调整出 5调整入 6物品详细 7货架详细
                if(caseInStorageData!=null)
                  mBundle.putSerializable("CaseStorageData", caseInStorageData);
                intent.putExtras(mBundle);
                //startActivityForResult(intent, 106);
                startActivity(intent);
                getActivity().finish();
            }
        });

         case_item_time_tv=(TextView) view.findViewById(R.id.case_item_time_tv);
         setTextViewText(case_item_time_tv,"");
         case_item_number_tv=(TextView) view.findViewById(R.id.case_item_number_tv);
         setTextViewText(case_item_number_tv,"");
         case_item_name_tv=(TextView) view.findViewById(R.id.case_item_name_tv);
         setTextViewText(case_item_name_tv,"");
         case_item_person_tv=(TextView) view.findViewById(R.id.case_item_person_tv);
         setTextViewText(case_item_person_tv,"");
         case_item_file_tv=(TextView) view.findViewById(R.id.case_item_file_tv);
         setTextViewText(case_item_file_tv,"");
         case_item_title_name_tv=(TextView) view.findViewById(R.id.case_item_title_name_tv);
         //setTextViewText(case_item_title_name_tv,((CaseArticleActivity)getActivity()).getScanText()+"入库单");
         setTextViewText(case_item_title_name_tv,"正在加载入库单信息...");
        // if(case_item_title_name_tv!=null )
        //    case_item_title_name_tv.setText(((CaseArticleActivity)getActivity()).getScanText());
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
    private class FetchItemsTask extends AsyncTask<Integer, Void, CaseInStorageData> {

        @Override
        protected CaseInStorageData doInBackground(Integer... params) {
            fetchItemsTaskRun=true;
            Integer count = params[0];
            CaseInStorageData tempData=new CaseInStorageData();
            tempData.scanformCode=((CaseArticleActivity)getActivity()).getScanText();
            tempData.formCode=tempData.scanformCode;
            tempData.DID= tempData.scanformCode;
            tempData.caseCode="AS30000001";
            tempData.caseName="王某一,王某二,王某三非法持有毒品案";
            tempData.suspectName="王某一";
            tempData.papers="筑经开工(刑)扣字(2015)8号";
            List<CaseData> result = new ArrayList<CaseData>();

            tempData.l_caseData=result;
            //增加5条固定数据
            CaseData tempData1=new CaseData();
            tempData1.DID = "test"+String.valueOf( count+1);
            tempData1.caeItemName = "海洛因";
            tempData1.earmark =  "海洛因";
            tempData1.caseItemNumber = "201600000"+String.valueOf( count+1);
            tempData1.ViewState= 1;
            tempData1.caseItemCountString ="八包";
            tempData1.caseItemHasCount=8;
            tempData1.caseDoItemCount=8;
            tempData1.lockerCode="030101";
            tempData1.lockerName="小型物品藏仓库 三排一层-1";
            result.add(tempData1);

            CaseData tempData2=new CaseData();
            tempData2.DID = "test"+String.valueOf( count+2);
            tempData2.caeItemName = "手机手机手机手机手机手机手机";
            tempData2.earmark =  "绿色VIVO绿色VIVO绿色VIVO绿色VIVO绿色VIVO绿色VIVO";
            tempData2.caseItemNumber = "casd201620162016201600000"+String.valueOf( count+2);
            tempData2.ViewState= 1;
            tempData2.caseItemCountString ="一一一一一部";
            tempData2.caseItemHasCount=999999;
            tempData2.caseDoItemCount=9999999;
           // tempData2.lockerCode="030101";
          //  tempData2.lockerName="小型物品藏仓库 三排一层-1";
            result.add(tempData2);

            CaseData tempData3=new CaseData();
            tempData3.DID = "test"+String.valueOf( count+3);
            tempData3.caeItemName = "手机";
            tempData3.earmark =  "蓝色VIVO";
            tempData3.caseItemNumber = "201600000"+String.valueOf( count+3);
            tempData3.ViewState= 1;
            tempData3.caseItemCountString ="共一部";
            tempData3.caseItemHasCount=1;
            tempData3.caseDoItemCount=0;
           // tempData3.lockerCode="030101";
           // tempData3.lockerName="小型物品藏仓库 三排一层-1";
            result.add(tempData3);

            CaseData tempData4=new CaseData();
            tempData4.DID = "test"+String.valueOf( count+4);
            tempData4.caeItemName = "现金";
            tempData4.earmark =  "白色袋子";
            tempData4.caseItemNumber = "201600000"+String.valueOf( count+4);
            tempData4.ViewState= 1;
            tempData4.caseItemCountString ="壹万伍仟元";//"255元";
            tempData4.caseItemHasCount=1;
            tempData4.caseDoItemCount=1;
            tempData4.lockerCode="030101";
            tempData4.lockerName="小型物品藏仓库 三排一层-1";
            result.add(tempData4);

            CaseData tempData5=new CaseData();
            tempData5.DID = "test"+String.valueOf( count+5);
            tempData5.caeItemName = "手机";
            tempData5.earmark =  "黑色VIVO";
            tempData5.caseItemNumber = "201600000"+String.valueOf( count+5);
            tempData5.ViewState= 1;
            tempData5.caseItemCountString ="共三部";
            tempData5.caseItemHasCount=3;
            tempData5.caseDoItemCount=2;
            tempData5.lockerCode="030101";
            tempData5.lockerName="小型物品藏仓库 三排一层-1";
            result.add(tempData5);

            CaseData tempData6=new CaseData();
            tempData6.DID = "test"+String.valueOf( count+6);
            tempData6.caeItemName = "海洛因";
            tempData6.earmark =  "";
            tempData6.caseItemNumber = "201600000"+String.valueOf( count+1);
            tempData6.ViewState= 1;
            tempData6.caseItemCountString ="十两包";
            tempData6.caseItemHasCount=12;
            tempData6.caseDoItemCount=12;
            tempData6.lockerCode="030201";
            tempData6.lockerName="小型物品藏仓库 三排二层-1";
            result.add(tempData6);

            CaseData tempData7=new CaseData();
            tempData7.DID = "test"+String.valueOf( count+7);
            tempData7.caeItemName = "手机21";
            tempData7.earmark =  "绿色VIVO";
            tempData7.caseItemNumber = "201600000"+String.valueOf( count+7);
            tempData7.ViewState= 1;
            tempData7.caseItemCountString ="一部";
            tempData7.caseItemHasCount=1;
            tempData7.caseDoItemCount=0;
            tempData7.lockerCode="030201";
            tempData7.lockerName="小型物品藏仓库 三排二层-1";
            result.add(tempData7);

            CaseData tempData8=new CaseData();
            tempData8.DID = "test"+String.valueOf( count+8);
            tempData8.caeItemName = "手机22";
            tempData8.earmark =  "绿色VIVO";
            tempData8.caseItemNumber = "201600000"+String.valueOf( count+8);
            tempData8.ViewState= 1;
            tempData8.caseItemCountString ="一部";
            tempData8.caseItemHasCount=1;
            tempData8.caseDoItemCount=0;
            tempData8.lockerCode="030201";
            tempData8.lockerName="小型物品藏仓库 三排二层-1";
            result.add(tempData8);

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
            tempData9.lockerCode="9771672909168";
            tempData9.lockerName="小型物品藏仓库 三排三层-1";
            result.add(tempData9);

            CaseData tempDatat1=new CaseData();
            tempDatat1.DID = "testh1";
            tempDatat1.caeItemName = "海洛因1";
            tempDatat1.earmark =  "";
            tempDatat1.caseItemNumber = "201600000H1";
            tempDatat1.ViewState= 1;
            tempDatat1.caseItemCountString ="八包";
            tempDatat1.caseItemHasCount=8;
            tempDatat1.caseDoItemCount=1;
            tempDatat1.lockerCode="030101";
            tempDatat1.lockerName="小型物品藏仓库 三排一层-1";
            result.add(tempDatat1);


            CaseData tempDatat2=new CaseData();
            tempDatat2.DID = "testh2";
            tempDatat2.caeItemName = "海洛因2";
            tempDatat2.earmark =  "";
            tempDatat2.caseItemNumber = "201600000H2";
            tempDatat2.ViewState= 1;
            tempDatat2.caseItemCountString ="八包";
            tempDatat2.caseItemHasCount=8;
            tempDatat2.caseDoItemCount=1;
            tempDatat2.lockerCode="030101";
            tempDatat2.lockerName="小型物品藏仓库 三排一层-1";
            result.add(tempDatat2);

            CaseData tempDatat3=new CaseData();
            tempDatat3.DID = "testh3";
            tempDatat3.caeItemName = "海洛因3";
            tempDatat3.earmark =  "";
            tempDatat3.caseItemNumber = "201600000H3";
            tempDatat3.ViewState= 1;
            tempDatat3.caseItemCountString ="八包";
            tempDatat3.caseItemHasCount=8;
            tempDatat3.caseDoItemCount=1;
            tempDatat3.lockerCode="030101";
            tempDatat3.lockerName="小型物品藏仓库 三排一层-1";
            result.add(tempDatat3);

            CaseData tempDatat4=new CaseData();
            tempDatat4.DID = "testh4";
            tempDatat4.caeItemName = "海洛因4";
            tempDatat4.earmark =  "";
            tempDatat4.caseItemNumber = "201600000H4";
            tempDatat4.ViewState= 1;
            tempDatat4.caseItemCountString ="八包";
            tempDatat4.caseItemHasCount=8;
            tempDatat4.caseDoItemCount=1;
            tempDatat4.lockerCode="030101";
            tempDatat4.lockerName="小型物品藏仓库 三排一层-1";
            result.add(tempDatat4);

            CaseData tempDatat5=new CaseData();
            tempDatat5.DID = "testh5";
            tempDatat5.caeItemName = "海洛因5";
            tempDatat5.earmark =  "";
            tempDatat5.caseItemNumber = "201600000H5";
            tempDatat5.ViewState= 1;
            tempDatat5.caseItemCountString ="八包";
            tempDatat5.caseItemHasCount=8;
            tempDatat5.caseDoItemCount=1;
            tempDatat5.lockerCode="030101";
            tempDatat5.lockerName="小型物品藏仓库 三排一层-1";
            result.add(tempDatat5);

            CaseData tempDatat6=new CaseData();
            tempDatat6.DID = "testh6";
            tempDatat6.caeItemName = "海洛因6";
            tempDatat6.earmark =  "";
            tempDatat6.caseItemNumber = "201600000H6";
            tempDatat6.ViewState= 1;
            tempDatat6.caseItemCountString ="八包";
            tempDatat6.caseItemHasCount=8;
            tempDatat6.caseDoItemCount=1;
            tempDatat6.lockerCode="030101";
            tempDatat6.lockerName="小型物品藏仓库 三排一层-1";
            result.add(tempDatat6);

            CaseData tempDatat7=new CaseData();
            tempDatat7.DID = "testh7";
            tempDatat7.caeItemName = "海洛因7";
            tempDatat7.earmark =  "";
            tempDatat7.caseItemNumber = "201600000H7";
            tempDatat7.ViewState= 1;
            tempDatat7.caseItemCountString ="八包";
            tempDatat7.caseItemHasCount=8;
            tempDatat7.caseDoItemCount=1;
            tempDatat7.lockerCode="030101";
            tempDatat7.lockerName="小型物品藏仓库 三排一层-1";
            result.add(tempDatat7);

            CaseData tempDatat8=new CaseData();
            tempDatat8.DID = "testh8";
            tempDatat8.caeItemName = "海洛因8";
            tempDatat8.earmark =  "";
            tempDatat8.caseItemNumber = "201600000H8";
            tempDatat8.ViewState= 1;
            tempDatat8.caseItemCountString ="八包";
            tempDatat8.caseItemHasCount=8;
            tempDatat8.caseDoItemCount=1;
            tempDatat8.lockerCode="030101";
            tempDatat8.lockerName="小型物品藏仓库 三排一层-1";
            result.add(tempDatat8);

            CaseData tempDatat9=new CaseData();
            tempDatat9.DID = "testh9";
            tempDatat9.caeItemName = "海洛因9";
            tempDatat9.earmark =  "";
            tempDatat9.caseItemNumber = "201600000H9";
            tempDatat9.ViewState= 1;
            tempDatat9.caseItemCountString ="八包";
            tempDatat9.caseItemHasCount=8;
            tempDatat9.caseDoItemCount=1;
            tempDatat9.lockerCode="030101";
            tempDatat9.lockerName="小型物品藏仓库 三排一层-1";
            result.add(tempDatat9);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return tempData;
        }

        protected void onPostExecute(CaseInStorageData caseStorageData) {
            mListView.removeFooterView(loadingView);
            fetchItemsTaskRun=false;
            caseInStorageData =caseStorageData;
            caseInStorageData.sortCaseData(caseInStorageData.l_caseData);
            caseInAdapter.addMoreItems(caseInStorageData.l_view_show_caseData);
            setTextViewText(case_item_title_name_tv, caseInStorageData.scanformCode + "入库单");
            setTextViewText(case_item_time_tv,caseInStorageData.changeDate(caseInStorageData.DataUpdateTime));
            setTextViewText(case_item_number_tv,caseInStorageData.caseCode);
            setTextViewText(case_item_name_tv,caseInStorageData.caseName);
            setTextViewText(case_item_person_tv,caseInStorageData.suspectName);
            setTextViewText(case_item_file_tv,caseInStorageData.papers);

            caseInAdapter.notifyDataSetChanged();
            case_item_btn_next_iv.setVisibility(View.VISIBLE);

        }
    }
}

