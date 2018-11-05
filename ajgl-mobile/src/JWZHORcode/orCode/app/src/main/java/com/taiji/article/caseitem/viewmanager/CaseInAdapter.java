package com.taiji.article.caseitem.viewmanager;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taiji.androidui.view.paginglistview.PagingBaseAdapter;
import orcode.pubsec.taiji.com.business.caseitem.CaseData;
import com.taiji.pubsec.orcode.addressorcode.R;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by ycy on 2015/4/21.
 */
public class CaseInAdapter extends PagingBaseAdapter<CaseData> {
    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        if(items!=null) {
            return items.size();
        }else
        {
            return 0;
        }
    }

    public void setDataSource(List<CaseData> l)
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
    public CaseData getItem(int position) {
        if(items!=null && items.size()> 0 && position<items.size()) {
            return items.get(position);
        }else
        {
            return null;
        }

    }

    public void updateOrAddItem(CaseData data)
    {
        for(int i=0;i<items.size();i++)
        {
            if(items.get(i).DID.equals(data.DID))
            {
                items.set(i, data);
                return;
            }
        }
        items.add(0,data);
    }

    public int deleteItem(CaseData data)
    {

        boolean isFind=false;
        for(int i=0;i<items.size();i++)
        {
            if(items.get(i).DID.equals(data.DID))
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
    public List<CaseData> setUpdateItems(List<CaseData> l_data)
    {
        synchronized(this) {
            List<CaseData> l_newdata = new ArrayList<CaseData>();
            for (int j = 0; j < l_data.size(); j++) {
                CaseData data = l_data.get(j);
                boolean isFind = false;
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).DID.equals(data.DID)) {
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
            CaseData tempData = items.get(position);
            if(tempData!=null) {
                return tempData.ViewState - 1;
            }
        }
        return 0;
      //  return position % 3;
    }

    String[] newTagShowHasCountText=new String[]{" 扣押: "," 扣押: "," 出库: "," 扣押: "," 调出: "};

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
        CaseData tempData= getItem(position);
        tempData.OrderPosition=position;

       // String text = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.case_item_in_list_item_v2, null);
        }



        if(tempData==null)
        {
         // iv.setImageResource(tempData.ImageResID);
            return convertView;
        }
        try {

          //  tv = (TextView) convertView.findViewById(R.id.case_item_list_item_order_number);
          //  tv.setText(String.valueOf(tempData.OrderPosition + 1));

            tv = (TextView) convertView.findViewById(R.id.case_item_in_list_item_name);
            tv.setText(tempData.caeItemName);

            tv = (TextView) convertView.findViewById(R.id.case_item_in_list_item_earmark);
            setShowShortTextAndAddLongClick(tv, tempData.earmark, 27);
           // tv.setText(tempData.earmark);

            tv = (TextView) convertView.findViewById(R.id.case_item_in_list_item_code_tv);
            tv.setText(tempData.getShowCaseItemNumber());

            tv = (TextView) convertView.findViewById(R.id.case_item_in_list_item_count_showname);
            if(tempData.ViewState>0 && tempData.ViewState<6)
            {
                tv.setText(newTagShowHasCountText[tempData.ViewState-1]);
            }
            else
            {
                tv.setText(" ");
            }

            tv = (TextView) convertView.findViewById(R.id.case_item_in_list_item_count);
            tv.setText(tempData.caseItemCountString);

            tv = (TextView) convertView.findViewById(R.id.case_item_in_list_item_docount_tv);
            tv.setText(String.valueOf(tempData.getShowCaseDoItemCount()));
            setTextCountColorByDoCount(tv,tempData);

        }catch (Exception ex)
        {}
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
        convertView.setTag(tempData);
        return convertView;
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

    //public

    protected void setTextCountColorByDoCount(TextView tv,CaseData tempData)
    {
        if(tempData.caseDoItemCount==0)
        {
            tv.setTextColor(Color.RED);
        }
        else if(tempData.caseItemCount>tempData.caseDoItemCount)
        {
            if(//(tempData.caeItemName.indexOf("金")>-1 && tempData.caeItemName.indexOf("卡")<0)
                   // || (tempData.caeItemName.indexOf("银")>-1&& tempData.caeItemName.indexOf("卡")<0)||
                    (tempData.caeItemName.indexOf("现金")>-1&& tempData.caeItemName.indexOf("卡")<0)
                    ||tempData.caseItemCountString.indexOf("斤")>0
                    ||tempData.caseItemCountString.indexOf("克")>0
                    ||tempData.caseItemCountString.indexOf("升")>0
                    )
            {
                tv.setTextColor(Color.BLACK);
            }
            else
            {
                tv.setTextColor(Color.RED);
            }

        }
        else
        {
            tv.setTextColor(Color.BLACK);
        }
    }

    //实际计算maxlength+2,因为补点
    protected void setShowShortTextAndAddLongClick(TextView tv,String sourceText,int maxlength) {
        if(sourceText==null)return;
        if(tv==null)return;
        if(maxlength<1)return;
        if(sourceText.length()>maxlength+2)
        {
            String tempStr=sourceText.substring(0,maxlength-1)+"...";
            tv.setText(tempStr);
            tv.setTag(sourceText);
            tv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (viewClick != null) {
                        try {
                            String tempStr = v.getTag().toString();
                            viewClick.textClick(tempStr);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    return false;
                }
            });
        }
        else
        {
            tv.setText(sourceText);
            tv.setOnLongClickListener(null);
        }
    }


    public interface viewClickListener
    {
        public void textClick(String showText);
    }

    private viewClickListener viewClick;
    public void setViewClick(viewClickListener l) {
        viewClick = l;
    }
}

