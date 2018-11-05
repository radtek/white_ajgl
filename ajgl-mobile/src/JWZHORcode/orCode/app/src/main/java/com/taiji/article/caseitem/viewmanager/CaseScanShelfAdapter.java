package com.taiji.article.caseitem.viewmanager;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.taiji.androidui.view.MyEditText.FinishListenEditText;
import com.taiji.androidui.view.paginglistview.PagingBaseAdapter;
import orcode.pubsec.taiji.com.business.caseitem.CaseData;
import com.taiji.pubsec.orcode.addressorcode.R;
import com.taiji.util.myArith;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by ycy on 2015/4/21.
 */
public class CaseScanShelfAdapter extends PagingBaseAdapter<CaseData> {
    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    Context context;
    String shelfCode;
    public CaseScanShelfAdapter(Context context,String shelfCode)
    {
        this.context=context;
        this.shelfCode=shelfCode;
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

    public void setDataSource(List<CaseData> l)
    {
        items=l;
    }

    public List<CaseData> getDataSource()
    {
        return items;
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

    public void updateItem(CaseData data)
    {
        for(int i=0;i<items.size();i++)
        {
            if(items.get(i).DID.equals(data.DID))
            {
                items.set(i, data);
                return;
            }
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
    String[] newTagShowText=new String[]{"本次入库: ","本次出库: ","本次返还: ","本次调出: ","本次调整: "};
    String[] newTagShowHasCountText=new String[]{"扣押: ","扣押: ","出库: ","扣押: ","调出: "};
    String[] newTagShowDoCountText=new String[]{"已入库数量: ","已出库数量: ","再入库数量: ","已调整数量: ","已调整数量: "};
    EditScanItemCountTextWatcher ew = new EditScanItemCountTextWatcher();
   // int iii=0;
    FinishListenEditText.OnFinishComposingListener ofcl= new FinishListenEditText.OnFinishComposingListener() {
        @Override
        public void finishComposing(FinishListenEditText fEditText) {
          //  Toast.makeText(context,String.valueOf(iii++), Toast.LENGTH_SHORT).show();
            if (fEditText.isFocused()) {
                fEditText.clearFocus();
              //  Toast.makeText(context,String.valueOf("c"+iii), Toast.LENGTH_SHORT).show();
            }
            // Toast.makeText(context,"11", Toast.LENGTH_SHORT).show();
        }
    };
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
        FinishListenEditText et;
        LinearLayout lay;
        CaseData tempData= getItem(position);
        tempData.OrderPosition=position;

       // String text = getItem(position);

        /*
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.case_item_in_list_item, null);
        }



        if(tempData==null)
        {
         // iv.setImageResource(tempData.ImageResID);
            return convertView;
        }


        tv = (TextView) convertView.findViewById(R.id.case_item_list_item_order_number);
        tv.setText(String.valueOf(tempData.OrderPosition+1));

        tv = (TextView) convertView.findViewById(R.id.case_item_list_item_name);
        tv.setText(tempData.caseItemName);

        tv = (TextView) convertView.findViewById(R.id.case_item_list_item_earmark);
        tv.setText(tempData.earmark);

        tv = (TextView) convertView.findViewById(R.id.case_item_list_item_number);
        tv.setText(tempData.caseItemNumber);

        tv = (TextView) convertView.findViewById(R.id.case_item_list_item_count);
        tv.setText(tempData.caseItemCountString);
        */

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.case_item_in_list_item_child, null);
        }



        if(tempData==null)
        {
            // iv.setImageResource(tempData.ImageResID);
            return convertView;
        }
        lay= (LinearLayout) convertView.findViewById(R.id.case_item_p_lay);
        lay.setTag(position);
        if(tempData.DataType==1) {
            lay.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                   // Toast.makeText(context, v.getHeight()+"", Toast.LENGTH_LONG).show();
                    CaseData tempData = CaseScanShelfAdapter.this.getItem((int) v.getTag());
                    if (tempData.DataType == 1) {
                        if (myViewRemove != null) {
                            myViewRemove.removeTempItem(CaseScanShelfAdapter.this, tempData,true);
                        }
                    }
                    return false;
                    //Toast.makeText(context, arg0.getHeight()+"", Toast.LENGTH_LONG).show();
                }
            });
        }else
        {
            lay.setOnClickListener(null);
          /*  lay.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(context, v.getHeight()+"", Toast.LENGTH_LONG).show();
                    return false;

                }
            });*/
        }

        tv = (TextView) convertView.findViewById(R.id.case_item_in_list_item_name);
        tv.setText(tempData.caeItemName);
        if(tempData.DataType==1)
        {
            tv.setTextColor(Color.RED);
        }
        else
        {
            tv.setTextColor(Color.BLACK);
        }
        tv = (TextView) convertView.findViewById(R.id.case_item_in_list_item_earmark);
        setShowShortTextAndAddLongClick(tv, tempData.earmark, 18);
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

        tv = (TextView) convertView.findViewById(R.id.case_item_in_list_item_docount_showname);
        if(tempData.ViewState>0 && tempData.ViewState<6)
        {
            tv.setText(newTagShowDoCountText[tempData.ViewState-1]);
        }
        else
        {
            tv.setText(" ");
        }


        tv = (TextView) convertView.findViewById(R.id.case_item_in_list_item_count);
        tv.setText(tempData.caseItemCountString);

        tv = (TextView) convertView.findViewById(R.id.case_item_in_list_item_docount_tv);
        tv.setText(String.valueOf(tempData.getShowCaseDoItemCount()));

        //这个颜色变化去掉,现在原数据和展示数据分开,原数据中没有caseItemCount,没有判断的依据
        /*
        if(tempData.caseDoItemCount==0)
        {
            tv.setTextColor(Color.RED);
        }
        else if(tempData.caseItemCount>tempData.caseDoItemCount)
        {
            if(tempData.caeItemName.indexOf("金")>0
                    || tempData.caeItemName.indexOf("银")>0
                    || tempData.caeItemName.indexOf("钱")>0
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
        */
        /*
        LinearLayout case_item_in_list_item_address_search_lay=(LinearLayout) convertView.findViewById(R.id.case_item_in_list_item_address_search_lay);
       if(tempData.lockerCode!=null &&!tempData.lockerCode.trim().equals(""))
       {
           case_item_in_list_item_address_search_lay.setVisibility(View.VISIBLE);
           tv = (TextView) convertView.findViewById(R.id.case_item_in_list_item_address_tv);
           tv.setText(tempData.lockerName);
       }
        else
       {
           case_item_in_list_item_address_search_lay.setVisibility(View.GONE);
           tv = (TextView) convertView.findViewById(R.id.case_item_in_list_item_address_tv);
           tv.setText("暂无");
       }
       */

        if(tempData.DataType==1)
        {
            LinearLayout case_item_in_list_item_address_search_lay=(LinearLayout) convertView.findViewById(R.id.case_item_in_list_item_address_search_lay);
            case_item_in_list_item_address_search_lay.setVisibility(View.VISIBLE);
            tv = (TextView) convertView.findViewById(R.id.case_item_in_list_item_new_tv);
            et = (FinishListenEditText) convertView.findViewById(R.id.case_item_in_list_item_new_ev);
            et.setTag(position);
           // tv.setVisibility(View.VISIBLE);
            if(tempData.ViewState>0 && tempData.ViewState<7)
            {
                tv.setText(newTagShowText[tempData.ViewState-1]);//+tempData.scanCount);
              //  try {
                    if(!et.isFocused()) {
                        et.setEditText(String.valueOf(tempData.getShowScanCount()));
                        et.setTag(position);
                    }
               // }catch (Exception ex)
              //  {
              //      ex.printStackTrace();
              //  }
            }
            else
            {
                tv.setText("新操作 ");
                et.setEditText("");
                et.setTag(position);
            }


            et.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus) {
                        //iii=0;
                        // 此处为得到焦点时的处理内容
                        FinishListenEditText etv = (FinishListenEditText) v;
                        CaseData cData = CaseScanShelfAdapter.this.getItem((int) v.getTag());
                        double surplusCount=-1;
                        if (myViewRemove != null) {
                            surplusCount = myViewRemove.findTempItemMaxChangeCount(CaseScanShelfAdapter.this, cData,shelfCode);
                        }
                        if(surplusCount<=-1)
                        {
                            Toast.makeText(context,"数据异常，无法认证数量!", Toast.LENGTH_SHORT).show();
                            etv.clearFocus();//.clearFouse();
                          //  if(ew==null) {
                           //     ew = new EditScanItemCountTextWatcher();
                          //  }
                          //  if(ew.context==null )ew.context=context;
                           // ew.et = (EditText) v;
                           // ew.min_mark = 0;
                           // ew.max_mark = cData.scanCount;
                          //  ew.isShowToast=false;
                          //  etv.addTextChangedListener(ew);
                        }
                        else
                        {
                            if(ew==null) {
                                ew = new EditScanItemCountTextWatcher();
                            }
                            if(ew.context==null )ew.context=context;
                            ew.et = (FinishListenEditText) v;
                            ew.min_mark = 0;
                            ew.max_mark= myArith.add(cData.scanCount, surplusCount);
                          //  ew.max_mark = cData.scanCount+surplusCount;
                            ew.isShowToast=true;
                            etv.addTextChangedListener(ew);
                            //设置输入完自动离开焦点
                            etv.setOnFinishComposingListener(ofcl);
                        }

                    }
                    else
                    {

                        // 此处为失去焦点时的处理内容
                        FinishListenEditText etv=(FinishListenEditText)v;
                        CaseData cData = CaseScanShelfAdapter.this.getItem((int) v.getTag());
                        try
                        {

                            if(ew!=null) {
                                etv.removeTextChangedListener(ew);
                                ew.et=null;
                                ew.context=null;
                            }
                            etv.setOnFinishComposingListener(null);
                            if(Double.valueOf(etv.getText().toString())==cData.scanCount)
                            {
                                //没改
                               // etv.setText(String.valueOf(cData.scanCount));
                               // Toast.makeText(context,"cl!", Toast.LENGTH_SHORT).show();
                                //用于界面刷新,否则卡光标
                              //  CaseScanShelfAdapter.this.notifyDataSetChanged();
                                return;
                            }
                            if(Double.valueOf(etv.getText().toString())<=0)
                            {
                                if (cData.DataType == 1) {
                                    if (myViewRemove != null) {
                                        myViewRemove.removeTempItem(CaseScanShelfAdapter.this, cData,false);
                                    }
                                }
                                else
                                {
                                    Toast.makeText(context,"清空数量异常!", Toast.LENGTH_SHORT).show();
                                    etv.setEditText(String.valueOf(cData.getShowScanCount()));
                                }
                            }
                            else
                            {
                                boolean isPass=false;
                                if (myViewRemove != null) {
                                    isPass = myViewRemove.itemChangeCount(CaseScanShelfAdapter.this, cData, Double.valueOf(etv.getText().toString()),shelfCode);
                                }
                                if(!isPass)
                                {
                                    Toast.makeText(context,"修改数量异常!", Toast.LENGTH_SHORT).show();
                                   // etv.setEditText(String.valueOf(cData.scanCount));
                                }
                            }
                        }
                        catch (Exception ex)
                        {
                            if(etv!=null && cData!=null)
                               etv.setEditText(String.valueOf(cData.getShowScanCount()));
                        }
                    }
                }
            });
        }
        else
        {
            LinearLayout case_item_in_list_item_address_search_lay=(LinearLayout) convertView.findViewById(R.id.case_item_in_list_item_address_search_lay);
            case_item_in_list_item_address_search_lay.setVisibility(View.GONE);
            et = (FinishListenEditText) convertView.findViewById(R.id.case_item_in_list_item_new_ev);
            et.setOnFocusChangeListener(null);
            et.setTag(position);
            //tv.setVisibility(View.GONE);
        }


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
            if (viewClick != null) {
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
        }
        else {
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

    //public
    public interface myViewRemoveListener
    {
        //删除本次操作项
        // isShowDialog true提示框 false不提示
        public void  removeTempItem(CaseScanShelfAdapter caseScanShelfAdapter,CaseData CaseData,boolean isShowDialog);

        //找本操作对象的可操作最大数量
        public double  findTempItemMaxChangeCount(CaseScanShelfAdapter caseScanShelfAdapter,CaseData CaseData,String shelfCode);

        //修改数量,true修改成功 false修改异常
        public boolean  itemChangeCount(CaseScanShelfAdapter caseScanShelfAdapter,CaseData CaseData,double newCount,String shelfCode);
    }

    private myViewRemoveListener myViewRemove;
    public void setViewReshListener(myViewRemoveListener l) {
        myViewRemove = l;
    }

    private class EditScanItemCountTextWatcher implements TextWatcher
    {
        public double min_mark = 0;
        public double max_mark = 100;
        public FinishListenEditText et;
        public  Context context;
        public boolean isShowToast=true;
        public int DECIMAL_DIGITS = 3;//小数的位数
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try {
                if (start >=0) {
                    if (min_mark != -1 && max_mark != -1) {
                        Double num =Double.valueOf(s.toString());
                        if (num > max_mark) {
                            s = String.valueOf(max_mark);
                            if (et != null) {
                              //  et.isFinishUser=false;
                                et.setEditText(s.toString());
                               // et.isFinishUser=true;
                                et.setSelection(s.length());
                                if(context!=null && isShowToast)
                                  Toast.makeText(context,"数量超过最大值!", Toast.LENGTH_SHORT).show();
                            }
                            return;
                        } else if (num < min_mark) {
                            s = String.valueOf(min_mark);
                            if (et != null) {
                             //   et.isFinishUser=false;
                                et.setEditText(s.toString());
                             //   et.isFinishUser=true;
                                et.setSelection(s.length());
                            }
                            return;
                        }
                    }
                }

            }catch (Exception ex)
            {
                ex.printStackTrace();
            }

            try {
                if (start >=0) {
                    if (s.toString().contains(".")) {
                        if (s.length() - 1 - s.toString().indexOf(".") > DECIMAL_DIGITS) {
                            s = s.toString().subSequence(0,
                                    s.toString().indexOf(".") + DECIMAL_DIGITS+1);
                            et.setText(s);
                            et.setSelection(s.length());
                        }
                    }
                    if (s.toString().trim().substring(0).equals(".")) {
                        s = "0" + s;
                        et.setText(s);
                        et.setSelection(2);
                    }
                    if (s.toString().startsWith("0")
                            && s.toString().trim().length() > 1) {
                        if (!s.toString().substring(1, 2).equals(".")) {
                            et.setText(s.subSequence(0, 1));
                            et.setSelection(1);
                            return;
                        }
                    }
                }

            }catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void afterTextChanged(Editable s)
        {
            /*
            try {
                if (s != null && !s.equals(""))
                {
                    if (min_mark != -1 && max_mark != -1)
                    {
                        int markVal = 0;
                        try
                        {
                            markVal = Integer.parseInt(s.toString());
                        }
                        catch (NumberFormatException e)
                        {
                            markVal = 0;
                        }
                        if (markVal > max_mark)
                        {
                            //Toast.makeText(getBaseContext(), "分数不能超过100", Toast.LENGTH_SHORT).show();
                            if(et!=null)
                            if (et != null) {
                                String tempStr=String.valueOf(max_mark);
                             //   et.isFinishUser=false;
                                et.setEditText(tempStr);
                             //   et.isFinishUser=true;
                                et.setSelection(tempStr.length());
                                if(context!=null&& isShowToast)
                                    Toast.makeText(context,"数量超过最大值2!", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else if (markVal < min_mark)
                        {
                            String tempStr=String.valueOf(min_mark);
                            if (et != null) {
                             //   et.isFinishUser=false;
                                et.setEditText(tempStr);
                             //   et.isFinishUser=true;
                                et.setSelection(tempStr.length());
                            }
                        }
                        return;
                    }
                }
            }catch (Exception ex)
            {
                ex.printStackTrace();
            }
            */
        }
    }
}

