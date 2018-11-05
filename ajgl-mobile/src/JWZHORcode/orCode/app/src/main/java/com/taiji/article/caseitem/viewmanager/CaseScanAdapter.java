package com.taiji.article.caseitem.viewmanager;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.taiji.androidui.view.paginglistview.PagingBaseAdapter;
import orcode.pubsec.taiji.com.business.caseitem.CaseData;
import com.taiji.article.caseitem.storageData.CaseScanData;
import com.taiji.article.caseitem.storageData.CaseShelfData;
import com.taiji.pubsec.orcode.addressorcode.R;
import com.taiji.util.myArith;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by ycy on 2015/4/21.
 */
public class CaseScanAdapter extends PagingBaseAdapter<CaseScanData> {
    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    Context context;

    public CaseScanAdapter(Context context)
    {
        this.context=context;
    }

    public void UnCaseInScanAdapter()
    {
        this.context=null;
    }

    @Override
    public int getCount() {
        if(items!=null) {
            return items.size();
        }else
        {
            return 0;
        }
    }

    public void setDataSource(List<CaseScanData> l)
    {
        items=l;
    }

    public void clearDataSource()
    {
        items.clear();
        notifyDataSetChanged();
    }
    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public CaseScanData getItem(int position) {
        if(items!=null && items.size()> 0 && position<items.size()) {
            return items.get(position);
        }else
        {
            return null;
        }

    }

    public void updateOrAddItem(CaseScanData data)
    {
        if(data==null)return;
        for(int i=0;i<items.size();i++)
        {
            CaseShelfData tempData1=items.get(i).caseShelfData;
            CaseShelfData tempData2=data.caseShelfData;
            if(tempData1!=null&&tempData1!=null&&tempData1.DID.equals(tempData2.DID))
            {
                items.set(i, data);
                return;
            }
        }
        items.add(0,data);
    }

    public int deleteItem(CaseScanData data)
    {

        boolean isFind=false;
        for(int i=0;i<items.size();i++)
        {
            CaseShelfData tempData1=items.get(i).caseShelfData;
            CaseShelfData tempData2=data.caseShelfData;
            if(tempData1!=null&&tempData1!=null&&tempData1.DID.equals(tempData2.DID))
            {
                items.remove(i);
                isFind = true;
                break;
            }
        }
        if(isFind)
        {/*
            UserTaskAdapterData[] ud=new  UserTaskAdapterData[items.size()];
            items.toArray(ud);
            removeAllItems();
            notifyDataSetChanged();
            for(int j=0;j<ud.length;j++)
            {
                items.add(ud[j]);
            }
            notifyDataSetChanged();*/
            notifyDataSetChanged();
            return 1;
        }
        return 0;
    }


    //替换相同id的item,并返回找不到id(新增)的item
    public List<CaseScanData> setUpdateItems(List<CaseScanData> l_data)
    {
        synchronized(this) {
            List<CaseScanData> l_newdata = new ArrayList<CaseScanData>();
            for (int j = 0; j < l_data.size(); j++) {
                CaseScanData data = l_data.get(j);
                boolean isFind = false;
                for (int i = 0; i < items.size(); i++) {
                    CaseShelfData tempData1=items.get(i).caseShelfData;
                    CaseShelfData tempData2=data.caseShelfData;
                    if(tempData1!=null&&tempData1!=null&&tempData1.DID.equals(tempData2.DID))
                    {
                        items.set(i, data);
                        isFind = true;
                        break;
                    }
                }
                if (!isFind) {
                    l_newdata.add(data);
                }
            }
            return l_newdata;
        }
    }


    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        // 返回ListView左滑菜单类型数量
        //这个值不对会出错!!
        return 5;
    }

    @Override
    public int getItemViewType(int position) {
        // 返回当前条目左滑菜单类型标识
        if(items!=null) {
            CaseScanData tempData = items.get(position);
            if(tempData!=null) {
                return tempData.ViewState - 1;
            }
        }
        return 0;
        //  return position % 3;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView iv;
        TextView tv;
        TextView tv2;
        LinearLayout lay;
        LinearLayout case_item_shelf_list_lay;
        LinearLayout case_item_shelf_list_title_nodata_lay;
        LinearLayout case_item_shelf_list_title_lay;
        ListView case_item_shelf_list_lv;
        ImageView case_in_shelf_open_iv;
        ImageView case_in_shelf_click_iv;
        CaseScanData tempData= getItem(position);
        tempData.OrderPosition=position;

        // String text = getItem(position);
/*
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.case_item_in_scan_list_item, null);

            if(tempData==null ||tempData.caseShelfData ==null)
            {
                // iv.setImageResource(tempData.ImageResID);
                return convertView;
            }

            convertView.setTag(tempData);

            tv = (TextView) convertView.findViewById(R.id.case_in_shelf_name_tv);
            tv.setText(String.valueOf(tempData.caseShelfData.lockerName));

             case_item_shelf_list_lay = (LinearLayout) convertView.findViewById(R.id.case_item_shelf_list_lay);
            case_item_shelf_list_lay.setVisibility(View.GONE);

             case_item_shelf_list_title_nodata_lay = (LinearLayout) convertView.findViewById(R.id.case_item_shelf_list_title_nodata_lay);
            case_item_shelf_list_title_nodata_lay.setVisibility(View.GONE);

             case_item_shelf_list_title_lay = (LinearLayout) convertView.findViewById(R.id.case_item_shelf_list_title_lay);
            case_item_shelf_list_title_lay.setVisibility(View.GONE);

             case_item_shelf_list_lv = (ListView) convertView.findViewById(R.id.case_item_shelf_list_lv);
            case_item_shelf_list_lv.setVisibility(View.GONE);

             case_in_shelf_open_iv = (ImageView) convertView.findViewById(R.id.case_in_shelf_open_iv);
            case_in_shelf_open_iv.setImageDrawable(context.getResources().getDrawable(R.drawable.arrow_right3x));

            lay = (LinearLayout) convertView.findViewById(R.id.case_item_info_lay);
            List<Object> lo=new ArrayList<Object>();
            lo.add(0);//0隐藏  1显示
            lo.add(convertView);
            lo.add(tempData);
            //view记录下来,尽量避免重复find损耗性能
            lo.add(case_item_shelf_list_lay);
            lo.add(case_item_shelf_list_title_nodata_lay);
            lo.add(case_item_shelf_list_title_lay);
            lo.add(case_item_shelf_list_lv);
            lo.add(case_in_shelf_open_iv);
            lo.add(tv);
            lay.setTag(lo);
            lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    showChild(arg0);
                }
            });
        }
        else{
            if(tempData==null ||tempData.caseShelfData ==null)
            {
                // iv.setImageResource(tempData.ImageResID);
                return convertView;
            }

            convertView.setTag(tempData);
            lay = (LinearLayout) convertView.findViewById(R.id.case_item_info_lay);
            List<Object> lo = (List<Object>) lay.getTag();
            lo.set(0, 0);
            //arg0.setTag(lo);

            tv = (TextView) lo.get(8);
            if (tv == null) {
                tv = (TextView) convertView.findViewById(R.id.case_in_shelf_name_tv);
            }
            tv.setText(String.valueOf(tempData.caseShelfData.lockerName));

            case_item_shelf_list_lay = (LinearLayout) lo.get(3);
            if (case_item_shelf_list_lay == null) {
                case_item_shelf_list_lay = (LinearLayout) convertView.findViewById(R.id.case_item_shelf_list_lay);
            }
            case_item_shelf_list_lay.setVisibility(View.GONE);

            case_item_shelf_list_title_nodata_lay = (LinearLayout) lo.get(4);
            if (case_item_shelf_list_title_nodata_lay == null) {
                case_item_shelf_list_title_nodata_lay = (LinearLayout) convertView.findViewById(R.id.case_item_shelf_list_title_nodata_lay);
            }
            case_item_shelf_list_title_nodata_lay.setVisibility(View.GONE);

            case_item_shelf_list_title_lay = (LinearLayout) lo.get(5);
            if (case_item_shelf_list_title_lay == null) {
                case_item_shelf_list_title_lay = (LinearLayout) convertView.findViewById(R.id.case_item_shelf_list_title_lay);
            }
            case_item_shelf_list_title_lay.setVisibility(View.GONE);

            case_item_shelf_list_lv = (ListView) lo.get(6);
            if (case_item_shelf_list_lv == null) {
                case_item_shelf_list_lv = (ListView) convertView.findViewById(R.id.case_item_shelf_list_lv);
            }
            case_item_shelf_list_lv.setVisibility(View.GONE);

            case_in_shelf_open_iv = (ImageView) lo.get(7);
            if (case_in_shelf_open_iv == null) {
                case_in_shelf_open_iv = (ImageView) convertView.findViewById(R.id.case_in_shelf_open_iv);
            }
            case_in_shelf_open_iv.setImageDrawable(context.getResources().getDrawable(R.drawable.arrow_right3x));
        }
*/

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.case_item_in_scan_list_item, null);

            if(tempData==null ||tempData.caseShelfData ==null)
            {
                // iv.setImageResource(tempData.ImageResID);
                return convertView;
            }

            convertView.setTag(tempData);

            tv = (TextView) convertView.findViewById(R.id.case_in_shelf_name_tv);
            tv.setText(String.valueOf(tempData.caseShelfData.lockerName));

            case_item_shelf_list_lay = (LinearLayout) convertView.findViewById(R.id.case_item_shelf_list_lay);
            case_item_shelf_list_lay.setVisibility(View.GONE);

            case_item_shelf_list_title_nodata_lay = (LinearLayout) convertView.findViewById(R.id.case_item_shelf_list_title_nodata_lay);
            case_item_shelf_list_title_nodata_lay.setVisibility(View.GONE);

            case_item_shelf_list_title_lay = (LinearLayout) convertView.findViewById(R.id.case_item_shelf_list_title_lay);
            case_item_shelf_list_title_lay.setVisibility(View.GONE);

            case_item_shelf_list_lv = (ListView) convertView.findViewById(R.id.case_item_shelf_list_lv);
            case_item_shelf_list_lv.setVisibility(View.GONE);

            case_in_shelf_open_iv = (ImageView) convertView.findViewById(R.id.case_in_shelf_open_iv);
            case_in_shelf_open_iv.setImageDrawable(context.getResources().getDrawable(R.drawable.arrow_right3x));

            case_in_shelf_click_iv=(ImageView) convertView.findViewById(R.id.case_in_shelf_click_iv);
            if(tempData.DataType==1)
            {
                case_in_shelf_click_iv.setVisibility(View.INVISIBLE);
            }else {
                case_in_shelf_click_iv.setVisibility(View.VISIBLE);
                if (userSelectShelf != null && userSelectShelf.caseShelfData != null && userSelectShelf.caseShelfData.DID.equals(tempData.caseShelfData.DID)) {
                    case_in_shelf_click_iv.setImageDrawable(context.getResources().getDrawable(R.drawable.ioclick));
                } else {
                    case_in_shelf_click_iv.setImageDrawable(context.getResources().getDrawable(R.drawable.ionoclick));
                }
            }
            case_in_shelf_click_iv.setTag(tempData);
            case_in_shelf_click_iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    try {
                        setSelectShelf((CaseScanData) arg0.getTag());
                    }catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }
                }
            });

            lay = (LinearLayout) convertView.findViewById(R.id.case_item_info_lay);
            List<Object> lo=new ArrayList<Object>();
            lo.add(0);//0隐藏  1显示
            lo.add(convertView);
            lo.add(tempData);
            //view记录下来,尽量避免重复find损耗性能
            lo.add(case_item_shelf_list_lay);
            lo.add(case_item_shelf_list_title_nodata_lay);
            lo.add(case_item_shelf_list_title_lay);
            lo.add(case_item_shelf_list_lv);
            lo.add(case_in_shelf_open_iv);
            lo.add(tv);
            lo.add(case_in_shelf_click_iv);
            lay.setTag(lo);
            lay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    showChild(arg0,true);
                }
            });
            try {
                //选中展开
                if (userSelectShelf != null && userSelectShelf.caseShelfData!=null &&tempData.caseShelfData!=null) {
                    if(userSelectShelf.caseShelfData.DID.equals(tempData.caseShelfData.DID))
                    {
                        showChild(lay,false);
                    }
                }
            }catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
        else{
            if(tempData==null ||tempData.caseShelfData ==null)
            {
                // iv.setImageResource(tempData.ImageResID);
                return convertView;
            }

            convertView.setTag(tempData);
            lay = (LinearLayout) convertView.findViewById(R.id.case_item_info_lay);
            List<Object> lo = (List<Object>) lay.getTag();
            lo.set(0, 0);
            lo.set(2, tempData);
            lay.setTag(lo);

            tv = (TextView) lo.get(8);
            if (tv == null) {
                tv = (TextView) convertView.findViewById(R.id.case_in_shelf_name_tv);
            }
            tv.setText(String.valueOf(tempData.caseShelfData.lockerName));

            case_item_shelf_list_lay = (LinearLayout) lo.get(3);
            if (case_item_shelf_list_lay == null) {
                case_item_shelf_list_lay = (LinearLayout) convertView.findViewById(R.id.case_item_shelf_list_lay);
            }
            case_item_shelf_list_lay.setVisibility(View.GONE);

            case_item_shelf_list_title_nodata_lay = (LinearLayout) lo.get(4);
            if (case_item_shelf_list_title_nodata_lay == null) {
                case_item_shelf_list_title_nodata_lay = (LinearLayout) convertView.findViewById(R.id.case_item_shelf_list_title_nodata_lay);
            }
            case_item_shelf_list_title_nodata_lay.setVisibility(View.GONE);

            case_item_shelf_list_title_lay = (LinearLayout) lo.get(5);
            if (case_item_shelf_list_title_lay == null) {
                case_item_shelf_list_title_lay = (LinearLayout) convertView.findViewById(R.id.case_item_shelf_list_title_lay);
            }
            case_item_shelf_list_title_lay.setVisibility(View.GONE);

            case_item_shelf_list_lv = (ListView) lo.get(6);
            if (case_item_shelf_list_lv == null) {
                case_item_shelf_list_lv = (ListView) convertView.findViewById(R.id.case_item_shelf_list_lv);
            }
            case_item_shelf_list_lv.setVisibility(View.GONE);

            case_in_shelf_open_iv = (ImageView) lo.get(7);
            if (case_in_shelf_open_iv == null) {
                case_in_shelf_open_iv = (ImageView) convertView.findViewById(R.id.case_in_shelf_open_iv);
            }
            case_in_shelf_open_iv.setImageDrawable(context.getResources().getDrawable(R.drawable.arrow_right3x));

            case_in_shelf_click_iv = (ImageView) lo.get(9);
            if (case_in_shelf_click_iv == null) {
                case_in_shelf_click_iv=(ImageView) convertView.findViewById(R.id.case_in_shelf_click_iv);
            }
            case_in_shelf_click_iv.setTag(tempData);
            if(tempData.DataType==1)
            {
                case_in_shelf_click_iv.setVisibility(View.INVISIBLE);
            }else {
                case_in_shelf_click_iv.setVisibility(View.VISIBLE);
                if (userSelectShelf != null && userSelectShelf.caseShelfData != null && userSelectShelf.caseShelfData.DID.equals(tempData.caseShelfData.DID)) {
                    case_in_shelf_click_iv.setImageDrawable(context.getResources().getDrawable(R.drawable.ioclick));
                } else {
                    case_in_shelf_click_iv.setImageDrawable(context.getResources().getDrawable(R.drawable.ionoclick));
                }
            }

            try {
                if (userSelectShelf != null && userSelectShelf.caseShelfData!=null &&tempData.caseShelfData!=null) {
                    if(userSelectShelf.caseShelfData.DID.equals(tempData.caseShelfData.DID))
                    {
                        showChild(lay,false);
                    }
                }
            }catch (Exception ex)
            {
                ex.printStackTrace();
            }

        }
       // lay.setTag(0, 0);//0隐藏  1显示
       // lay.setTag(1, convertView);
       // lay.setTag(2, tempData);
        //view记录下来,尽量避免重复find损耗性能
       // lay.setTag(3, case_item_shelf_list_lay);
       // lay.setTag(4, case_item_shelf_list_title_nodata_lay);
       // lay.setTag(5, case_item_shelf_list_title_lay);
       // lay.setTag(6, case_item_shelf_list_lv);

        if(tempData.caseInScanShelfAdapter==null)
        {
            tempData.caseInScanShelfAdapter=new CaseScanShelfAdapter(context,tempData.caseShelfData.DID);
            tempData.caseInScanShelfAdapter.setViewReshListener(new CaseScanShelfAdapter.myViewRemoveListener() {

                @Override
                public void  removeTempItem(CaseScanShelfAdapter caseScanShelfAdapter,CaseData CaseData,boolean isShowDialog)
                {
                    if(myViewResh!=null)
                    {
                        myViewResh.removeTempItem(caseScanShelfAdapter,CaseData,isShowDialog);

                    }
                }

                @Override
                public double  findTempItemMaxChangeCount(CaseScanShelfAdapter caseScanShelfAdapter,CaseData CaseData,String shelfCode)
                {
                    if(myViewResh!=null)
                    {
                       return myViewResh.findTempItemMaxChangeCount(caseScanShelfAdapter, CaseData,shelfCode, CaseScanAdapter.this);
                    }
                    return -1;
                }

                @Override
                public boolean  itemChangeCount(CaseScanShelfAdapter caseScanShelfAdapter,CaseData CaseData,double newCount,String shelfCode)
                {
                    if(myViewResh!=null)
                    {
                        return myViewResh.itemChangeCount(caseScanShelfAdapter, CaseData,newCount,shelfCode,CaseScanAdapter.this);
                    }
                    return false;
                }

            });
        }
        tempData.caseInScanShelfAdapter.setViewClick(new CaseScanShelfAdapter.viewClickListener() {
            @Override
            public void textClick(String showText)
            {
                 if(context!=null)
                 {
                     Toast.makeText(context,showText, Toast.LENGTH_LONG).show();
                 }
            }
        });
        /*
        tempData.caseInScanShelfAdapter.setDataSource(tempData.caseShelfData.list_CaseData);
        if(tempData.caseShelfData.list_CaseData.size()<2)
        {
            ViewGroup.LayoutParams linearParams = (ViewGroup.LayoutParams) case_item_shelf_list_lv.getLayoutParams();
            linearParams.height = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,111, convertView.getResources().getDisplayMetrics()));
            case_item_shelf_list_lv.setLayoutParams(linearParams);
        }
        else //if(tempData.caseShelfData.list_CaseData.size()<5)
        {
            int newCaseItemCount=getNewCaseItemCount(tempData);
            ViewGroup.LayoutParams linearParams = (ViewGroup.LayoutParams) case_item_shelf_list_lv.getLayoutParams();
            //66.16/90.86
            linearParams.height = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,15+72*tempData.caseShelfData.list_CaseData.size()+24*newCaseItemCount, convertView.getResources().getDisplayMetrics()));
            case_item_shelf_list_lv.setLayoutParams(linearParams);
        }
        */
                // else
                // {
                //      ViewGroup.LayoutParams linearParams = (ViewGroup.LayoutParams) case_item_shelf_list_lv.getLayoutParams();
                //      linearParams.height = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, convertView.getResources().getDisplayMetrics()));
                //       case_item_shelf_list_lv.setLayoutParams(linearParams);
                //  }
                case_item_shelf_list_lv.setAdapter(tempData.caseInScanShelfAdapter);
        changeChildListHight(convertView, case_item_shelf_list_lv,tempData);



        // tv = (TextView) convertView.findViewById(R.id.case_item_list_item_name);
        //  tv.setText(tempData.caseItemName);

        //   tv = (TextView) convertView.findViewById(R.id.case_item_list_item_earmark);
        //  tv.setText(tempData.earmark);

        //tv = (TextView) convertView.findViewById(R.id.case_item_list_item_number);
        //  tv.setText(tempData.caseItemNumber);

        // tv = (TextView) convertView.findViewById(R.id.case_item_list_item_count);
        //  tv.setText(tempData.caseItemCountString);
        /*try{
            Date d=new Date();
            boolean flag = isSameDate(d, tempData.DataCreateTime);
            String fmtStrDate="MM月dd日";
            String fmtStrTime="HH:mm:ss";
            SimpleDateFormat dfDate = new SimpleDateFormat(fmtStrDate);
            SimpleDateFormat dfTime = new SimpleDateFormat(fmtStrTime);
            if (flag) {
                tv.setText(dfTime.format(tempData.DataCreateTime));
            }
            else
            {
                tv.setText(dfDate.format(tempData.DataCreateTime)+"  "+dfTime.format(tempData.DataCreateTime));
            }
        }catch(Exception e)
        {}*/




       /* if (position <= 5) {

        }


        if (position % 4 == 0) {
            //iv.setVisibility(View.GONE);
        } else if (position % 4 == 1) {
          //  iv.setVisibility(View.INVISIBLE);
        } else if (position % 4 == 2) {
            int ii = 0;
            ii = 1;
        } else if (position % 4 == 3) {
            int ii = 0;
            ii = 1;
        }*/
//iv.set

        return convertView;
    }

    private void changeChildListHight(View convertView,ListView case_item_shelf_list_lv,CaseScanData tempData) {
        tempData.caseInScanShelfAdapter.setDataSource(tempData.caseShelfData.list_CaseData);

        int totalHeight = 0;
        for(int i=0;i< tempData.caseInScanShelfAdapter.getCount();i++) {
            View viewItem =  tempData.caseInScanShelfAdapter.getView(i, null, case_item_shelf_list_lv);
            viewItem.measure(0, 0);
            totalHeight += viewItem.getMeasuredHeight();
          //  System.out.println("---------"+"viewItemHeight="+ viewItem.getMeasuredHeight());
        }
        ViewGroup.LayoutParams linearParams = (ViewGroup.LayoutParams) case_item_shelf_list_lv.getLayoutParams();
        //66.16/90.86
        int dipHight =totalHeight + (case_item_shelf_list_lv.getDividerHeight() * (tempData.caseInScanShelfAdapter.getCount() - 1));//+ MyTools.dip2px(context,20);
        linearParams.height = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,dipHight, convertView.getResources().getDisplayMetrics()));
        case_item_shelf_list_lv.setLayoutParams(linearParams);
        /*
        if(tempData.caseShelfData.list_CaseData.size()<2)
        {
            ViewGroup.LayoutParams linearParams = (ViewGroup.LayoutParams) case_item_shelf_list_lv.getLayoutParams();
            linearParams.height = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,111, convertView.getResources().getDisplayMetrics()));
            case_item_shelf_list_lv.setLayoutParams(linearParams);
        }
        else //if(tempData.caseShelfData.list_CaseData.size()<5)
        {
            int newCaseItemCount=getNewCaseItemCount(tempData);
            ViewGroup.LayoutParams linearParams = (ViewGroup.LayoutParams) case_item_shelf_list_lv.getLayoutParams();
            //66.16/90.86
            linearParams.height = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,15+72*tempData.caseShelfData.list_CaseData.size()+24*newCaseItemCount, convertView.getResources().getDisplayMetrics()));
            case_item_shelf_list_lv.setLayoutParams(linearParams);
        }*/
    }

    private void showChild(View arg0,boolean isAnimation){

        try {
            List<Object> lo = (List<Object>) arg0.getTag();
            View convertView = (View) lo.get(1);
            if (convertView == null) return;
            LinearLayout case_item_shelf_list_lay = (LinearLayout) arg0.getTag(3);
            if (case_item_shelf_list_lay == null) {
                case_item_shelf_list_lay = (LinearLayout) convertView.findViewById(R.id.case_item_shelf_list_lay);
            }
            //
            int isShow = (int) lo.get(0);
            if (isShow == 0) {
                lo.set(0, 1);
                arg0.setTag(lo);
                case_item_shelf_list_lay.setVisibility(View.VISIBLE);
                try {
                    ImageView case_in_shelf_open_iv = (ImageView)lo.get(7);
                    if(context!=null)
                    {
                        case_in_shelf_open_iv.setImageDrawable(context.getResources().getDrawable(R.drawable.arrow_down3x));
                    }


                    CaseScanData tempData = (CaseScanData) lo.get(2);
                    if (tempData != null && tempData.caseShelfData != null) {
                        LinearLayout case_item_shelf_list_title_nodata_lay = (LinearLayout) lo.get(4);
                        if (case_item_shelf_list_title_nodata_lay == null) {
                            case_item_shelf_list_title_nodata_lay = (LinearLayout) convertView.findViewById(R.id.case_item_shelf_list_title_nodata_lay);
                        }

                        LinearLayout case_item_shelf_list_title_lay = (LinearLayout) lo.get(5);
                        if (case_item_shelf_list_title_lay == null) {
                            case_item_shelf_list_title_lay = (LinearLayout) convertView.findViewById(R.id.case_item_shelf_list_title_lay);
                        }

                        ListView case_item_shelf_list_lv = (ListView) lo.get(6);
                        if (case_item_shelf_list_lv == null) {
                            case_item_shelf_list_lv = (ListView) convertView.findViewById(R.id.case_item_shelf_list_lv);
                        }

                        if (tempData.caseShelfData.list_CaseData == null || tempData.caseShelfData.list_CaseData.size() == 0) {
                            case_item_shelf_list_title_nodata_lay.setVisibility(View.VISIBLE);
                            case_item_shelf_list_title_lay.setVisibility(View.GONE);
                            case_item_shelf_list_lv.setVisibility(View.GONE);
                        } else {
                            case_item_shelf_list_title_nodata_lay.setVisibility(View.GONE);
                            case_item_shelf_list_title_lay.setVisibility(View.VISIBLE);
                            case_item_shelf_list_lv.setVisibility(View.VISIBLE);
                        }

                        //效果太差,暂时不用
                        if(isAnimation && tempData.caseShelfData.list_CaseData != null
                                        && tempData.caseShelfData.list_CaseData.size() != 0 )
                        {
                            case_item_shelf_list_lay.clearAnimation();
                            case_item_shelf_list_lay.startAnimation(sizeShowChangeAnimation());
                        }

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            } else {
                lo.set(0, 0);
                arg0.setTag(lo);
                case_item_shelf_list_lay.setVisibility(View.GONE);
                try {
                    ImageView case_in_shelf_open_iv = (ImageView) lo.get(7);
                    if (context != null) {
                        case_in_shelf_open_iv.setImageDrawable(context.getResources().getDrawable(R.drawable.arrow_right3x));
                    }
                    // case_item_shelf_list_lay.clearAnimation();
                    // case_item_shelf_list_lay.startAnimation(sizeHideChangeAnimation());
                }catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static boolean isSameDate(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);
        boolean isSameMonth = isSameYear
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                .get(Calendar.DAY_OF_MONTH);

        return isSameDate;
    }
   /* public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }*/

    //验证货架是否存在
    //存在就返回对象position,否则-1
   public int  checkShelf(String shelfId)
   {
      // int position=-1;
       if(items==null || shelfId==null) return -1;
       for(int i=0;i<items.size();i++)
       {
           if(items.get(i).caseShelfData.DID.equals(shelfId.trim()))
           {
               return i;
           }
       }
       return -1;
   }

    public int  removeTempShelf()
    {
        for(int i=0;i<items.size();i++)
        {
            if(items.get(i).DataType==1)
            {
                items.remove(i);
                notifyDataSetChanged();
                return i;
            }
        }
        return -1;
    }

    public int  removeTempShelfCaseCode(String shelfCode)
    {
        for(int i=0;i<items.size();i++)
        {
            if(items.get(i).caseShelfData.DID.equals(shelfCode))
            {
                items.get(i).caseShelfData.caseCode="";
                return i;
            }
        }
        return -1;
    }


    public int  UpdateTempShelf(String caseShelfDataCode,String caseShelfDataName,String caseCode,String caseShelfAreaCode,String caseShelfAreaName)
    {

        for(int i=0;i<items.size();i++)
        {
            if(items.get(i).DataType==1)
            {
                items.get(i).DataType=-1;
                items.get(i).caseShelfData.DID=caseShelfDataCode;
                items.get(i).caseShelfData.DataType=1;
                items.get(i).caseShelfData.caseCode=caseCode;
                items.get(i).caseShelfData.lockerName=caseShelfDataName;
                items.get(i).caseShelfData.areaCode=caseShelfAreaCode;
                items.get(i).caseShelfData.areaName=caseShelfAreaName;
                notifyDataSetChanged();
                return i;
            }
        }
        return -1;
    }

    CaseScanData userSelectShelf=null;
    public void setSelectShelf(CaseScanData selectShelf)
    {
        if(selectShelf ==null)
        {
            //int oldPosition=getSelectShelfPosition(userSelectShelf);
            setItemViewClick(selectShelf, userSelectShelf);
        }
        else
        {
            //int oldPosition=getSelectShelfPosition(userSelectShelf);
            CaseScanData oldSelectShelf=userSelectShelf;
            userSelectShelf=selectShelf;//getItem(position);
            // userSelectShelfPosition=position;
            setItemViewClick(selectShelf, oldSelectShelf);
            if(myViewResh!=null)
            {
                int position=getSelectShelfPosition(userSelectShelf);
                myViewResh.myViewResh(position,getItem(position));
            }
        }
    }

    public int getSelectShelfPosition(CaseScanData selectShelfData)
    {
        try {
            if (selectShelfData == null || selectShelfData.caseShelfData == null || selectShelfData.caseShelfData.DID == null || selectShelfData.caseShelfData.DID.trim().equals("")) {
                return -1;
            }
            if (items == null) return -1;
            for (int i = 0; i < items.size(); i++) {
                if (selectShelfData.caseShelfData.DID.equals(items.get(i).caseShelfData.DID))
                {
                    return i;
                }
            }
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return -1;
    }

    //新要选中的选中,老的取消选中
    private void setItemViewClick(CaseScanData newSelectShelf,CaseScanData oldSelectShelf)
    {
        if(myViewResh!=null) {
            if (newSelectShelf!=null) {
                View v = myViewResh.myViewGetItemView(newSelectShelf);
                if(v!=null){
                    ImageView case_in_shelf_click_iv = (ImageView) v.findViewById(R.id.case_in_shelf_click_iv);
                    if (case_in_shelf_click_iv != null) {
                        case_in_shelf_click_iv.setImageDrawable(context.getResources().getDrawable(R.drawable.ioclick));

                    }
                }
            }
            if (oldSelectShelf!=null) {
                if(oldSelectShelf.caseShelfData!=null && newSelectShelf!=null &&newSelectShelf.caseShelfData!=null
                        &&oldSelectShelf.caseShelfData.DID.equals(newSelectShelf.caseShelfData.DID))
                {
                  //同一个对象不做事
                }
                else {
                    View v = myViewResh.myViewGetItemView(oldSelectShelf);
                    if (v != null) {
                        ImageView old_case_in_shelf_click_iv = (ImageView) v.findViewById(R.id.case_in_shelf_click_iv);
                        if (old_case_in_shelf_click_iv != null) {
                            old_case_in_shelf_click_iv.setImageDrawable(context.getResources().getDrawable(R.drawable.ionoclick));
                        }
                    }
                }
            }
        }
    }

    public CaseData  checkShelfItem(String scanItemCode,CaseScanData selectShelf)
    {
        if(scanItemCode==null || selectShelf==null)
        {
            return null;
        }
        for(int i=0;i<items.size();i++)
        {
            if(items.get(i).caseShelfData.DID.equals(selectShelf.caseShelfData.DID))
            {
                CaseData cd=null;
                for(int j=0;j<items.get(i).caseShelfData.list_CaseData.size();j++)
                {
                    cd=items.get(i).caseShelfData.list_CaseData.get(j);
                    if(cd.caseItemNumber.equals(scanItemCode.trim()))
                    {
                       return cd;
                    }
                }
                return null;
                // items.get(i).caseInScanShelfAdapter.updateOrAddItem (null);
            }
        }
        return null;
    }


    //isOnlyUpdate true 只修改,不添加,找不到修改返回-2,错误-1,用于出库,不在单据中不能出  false 找不到就添加新的
    public int  UpdateShelfItem(CaseData scanItem,CaseScanData selectShelf,boolean isOnlyUpdate,double maxCount)
    {
        if(selectShelf==null || selectShelf.caseShelfData==null)
        {
            return -1;
        }
        boolean isFind=false;
        for(int i=0;i<items.size();i++)
        {
            if(items.get(i).caseShelfData.DID.equals(selectShelf.caseShelfData.DID))
            {
                CaseData cd=null;
                for(int j=0;j<items.get(i).caseShelfData.list_CaseData.size();j++)
                {
                    cd=items.get(i).caseShelfData.list_CaseData.get(j);
                    if(cd.caseItemNumber.equals(scanItem.caseItemNumber.trim()))
                    {
                        isFind=true;
                        cd.DataType=1;
                        if(maxCount<=1)
                            cd.scanCount= myArith.add(cd.scanCount, maxCount);
                          //  cd.scanCount+=maxCount;
                        else
                            cd.scanCount= myArith.add(cd.scanCount, 1.0);
                           // cd.scanCount+=1;
                        break;
                    }
                }
                if(!isFind && !isOnlyUpdate)
                {
                    scanItem.DataType=1;
                    if(maxCount<=1)
                        scanItem.scanCount= myArith.add(scanItem.scanCount, maxCount);
                       // scanItem.scanCount+=maxCount;
                    else
                        scanItem.scanCount= myArith.add(scanItem.scanCount, 1.0);
                      //  scanItem.scanCount+=1;
                    items.get(i).caseShelfData.list_CaseData.add(0,scanItem);
                }
                items.get(i).caseShelfData.sortCaseData();
                this.notifyDataSetChanged();
              //  items.get(i).caseInScanShelfAdapter.notifyDataSetChanged();
                break;
               // items.get(i).caseInScanShelfAdapter.updateOrAddItem (null);
            }

        }
        return -1;
    }

    //获取架子上是新操作的物品数
    private int getNewCaseItemCount(CaseScanData cd)
    {
        if(cd==null || cd.caseShelfData==null)
        {
            return 0;
        }
        else
        {
            int newCaseItemCount=0;
            for(int i=0;i<cd.caseShelfData.list_CaseData.size();i++)
            {
                if(cd.caseShelfData.list_CaseData.get(i).DataType==1)
                {
                    newCaseItemCount++;
                }
            }
            return newCaseItemCount;
        }
    }

    //获取所有新添加物品
    public List<CaseData> getAllNewCaseItem()
    {
        List<CaseData> l_itemAllScan=new ArrayList<CaseData>();
        for(int i=0;i<items.size();i++) {
            for (int j = 0; j < items.get(i).caseShelfData.list_CaseData.size(); j++) {
                if (items.get(i).caseShelfData.list_CaseData.get(j).DataType == 1) {
                    l_itemAllScan.add(items.get(i).caseShelfData.list_CaseData.get(j));
                }
            }
        }
       return l_itemAllScan;
    }

    protected Animation sizeShowChangeAnimation() {
        ScaleAnimation animation =new ScaleAnimation(1.0f, 1.0f, 0.0f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);

        animation.setDuration(100);
        animation.setInterpolator(new AccelerateInterpolator());
        animation.setAnimationListener(listener);
        return animation;
    }

    protected Animation sizeHideChangeAnimation() {
        ScaleAnimation animation =new ScaleAnimation(1.0f, 1.0f, 1.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);

        animation.setDuration(100);
        animation.setInterpolator(new AccelerateInterpolator());
        animation.setAnimationListener(listener);
        return animation;
    }

    /***
     * 动画监听，动画完成后，动画恢复，设置文本
     */
    private Animation.AnimationListener listener = new Animation.AnimationListener() {

        @Override
        public void onAnimationStart(Animation arg0) {
        }

        @Override
        public void onAnimationRepeat(Animation arg0) {
        }

        @Override
        public void onAnimationEnd(Animation arg0) {
            // setCurText(curText);
        }
    };



    //public
    public interface myViewReshListener
    {
        public void myViewResh(int position, CaseScanData caseScanData);

        public View  myViewGetItemView(CaseScanData caseScanData);

        //删除本次操作项
        public void  removeTempItem(CaseScanShelfAdapter caseScanShelfAdapter,CaseData CaseData,boolean isShowDialog);

        //找本操作对象的可操作最大数量
        public double  findTempItemMaxChangeCount(CaseScanShelfAdapter caseScanShelfAdapter,CaseData CaseData,String shelfCode,CaseScanAdapter caseScanAdapter);

        //修改数量,true修改成功 false修改异常
        public boolean  itemChangeCount(CaseScanShelfAdapter caseScanShelfAdapter,CaseData CaseData,double newCount,String shelfCode,CaseScanAdapter caseScanAdapter);
    }

    private myViewReshListener myViewResh;
    public void setViewReshListener(myViewReshListener l) {
        myViewResh = l;
    }
}

