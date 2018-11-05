package com.taiji.article.caseitem.viewmanager;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import orcode.pubsec.taiji.com.business.caseitem.CaseData;
import com.taiji.pubsec.orcode.addressorcode.R;


//返还基本对象同出库单
public class CaseBackAdapter extends CaseInAdapter {

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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.case_item_back_list_item_v2, null);
        }



        if(tempData==null)
        {
            // iv.setImageResource(tempData.ImageResID);
            return convertView;
        }
        try {

            //  tv = (TextView) convertView.findViewById(R.id.case_item_list_item_order_number);
            //  tv.setText(String.valueOf(tempData.OrderPosition + 1));

            tv = (TextView) convertView.findViewById(R.id.case_item_back_list_item_name);
            tv.setText(tempData.caeItemName);

            tv = (TextView) convertView.findViewById(R.id.case_item_back_list_item_earmark);
            setShowShortTextAndAddLongClick(tv, tempData.earmark, 27);
            // tv.setText(tempData.earmark);

            tv = (TextView) convertView.findViewById(R.id.case_item_back_list_item_code_tv);
            tv.setText(tempData.getShowCaseItemNumber());

            tv = (TextView) convertView.findViewById(R.id.case_item_back_list_item_count_showname);
            if(tempData.ViewState>0 && tempData.ViewState<6)
            {
                tv.setText(newTagShowHasCountText[tempData.ViewState-1]);
            }
            else
            {
                tv.setText(" ");
            }

            tv = (TextView) convertView.findViewById(R.id.case_item_back_list_item_count);
            tv.setText(tempData.caseItemCountString);

            tv = (TextView) convertView.findViewById(R.id.case_item_back_list_item_docount_tv);
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

    @Override
    protected void setTextCountColorByDoCount(TextView tv,CaseData tempData) {
        if(tempData.caseDoItemCount==0)
        {
            tv.setTextColor(Color.RED);
        }
        else if(tempData.caseItemCount>tempData.caseDoItemCount)
        {
            tv.setTextColor(Color.RED);
        }
        else
        {
            tv.setTextColor(Color.BLACK);
        }
    }
}

