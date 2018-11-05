package com.taiji.article.caseitem;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taiji.pubsec.orcode.addressorcode.FragmentBaseScan;
import com.taiji.pubsec.orcode.addressorcode.R;


public class CaseArticleActivity extends Activity implements
        InFragment.OnFragmentInteractionListener,
        OutFragment.OnFragmentInteractionListener
{
    private static final String TAG = "CaseArticleActivity";

    private Resources resources;
    Fragment main_fragment=null;
    TextView tittleItem;
    String scanText;
    int showType=-99;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_case_item);
        resources = getResources();
        Intent intent = getIntent();
        int showType=-99;
        try{
            showType = (int)intent.getSerializableExtra("showType");
            scanText = (String)intent.getSerializableExtra("scanCode");
        }catch(Exception e)
        {}

        LinearLayout scan_case_back_v3_lay=(LinearLayout) findViewById(R.id.scan_case_back_v3_lay);
        scan_case_back_v3_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        changeFrament(showType,null);

    }

    public String getScanText()
    {
        return scanText;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    int Backnumber=0;
    @Override
    public void onBackPressed() {
        if(true)
        {
            Intent intent = new Intent(CaseArticleActivity.this, CaseScanAllActivity.class);
          //  Bundle mBundle = new Bundle();
           // mBundle.putSerializable("showType", 1);//1入 2出 3返 4调整出 5调整入 6物品详细 7货架详细
           // intent.putExtras(mBundle);
            //startActivityForResult(intent, 106);
            startActivity(intent);
            overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
            finish();
        }else {
            if (Backnumber == 1) {
                //   super.onBackPressed();
                Bundle mBundle = new Bundle();
                //  mBundle.putSerializable("userTaskAdapterData", userTaskAdapterData);
                // Intent intent=new Intent();
                // intent.putExtras(mBundle);
                // setResult(106, intent);
                finish();
            } else if (Backnumber == 2 || Backnumber == 3) {
                if (Backnumber == 2) {
                    changeFrament(1, getDelOutFragment());
                } else if (Backnumber == 3) {
                    changeFrament(1, getDelInFragment());
                }

                //super.onBackPressed();
            } else if (Backnumber == 4 || Backnumber == 5 || Backnumber == 6) {
                if (Backnumber == 4) {
                    //  changeFrament(3, getDelCollectionLogDetailFragment());
                } else if (Backnumber == 5) {
                    changeFrament(6, getDelOutFragment());
                } else if (Backnumber == 6) {
                    changeFrament(1, getDelInFragment());
                }
            }
      /*  if(Backnumber==0)
        {
            Toast.makeText(this,"再次点击将退出", Toast.LENGTH_LONG).show();
            Backnumber++;
        }
        else if(Backnumber==1)
        {
            Backnumber++;
        }
        else if(Backnumber>=2){
            Backnumber=0;
            super.onBackPressed();
        }*/

        }
    }



  //  CollectionTableData collectionTableData;
  //  public void setCollectionTableData(CollectionTableData collectionTableData)
   // {
   //     this.collectionTableData=collectionTableData;
  //  }



    public void changeFrament(int changeCode,Fragment removeFragment)
    {
        // setChangeCode(changeCode);
        //hideInput();
        Backnumber=changeCode;
        if(changeCode==1)
        {
            if(tittleItem!=null)
            {
                tittleItem.setText("    涉案物品存库管理");
            }
            getInFragment(removeFragment, true);
        }
        else if(changeCode==2)
        {
            if(tittleItem!=null)
            {
                tittleItem.setText("    涉案物品出库管理");
            }
            getOutFragment( removeFragment, true);
           // getAddCollectionFragment(false);
        }
        else if(changeCode==3)
        {
            if(tittleItem!=null)
            {
                tittleItem.setText("  涉案物品再入库管理");
            }
            getBackFragment( removeFragment, true);
            // getAddCollectionFragment(false);
        }
        else if(changeCode==4)
        {
            if(tittleItem!=null)
            {
                tittleItem.setText("    涉案物品调整管理");
            }
            getBackAdjustment( removeFragment, true);
            // getAddCollectionFragment(false);
        }
        else if(changeCode==5)
        {
            if(tittleItem!=null)
            {
                tittleItem.setText("    涉案物品调整管理");
            }
          //  getInFragmentScan( removeFragment, false);
            // getAddCollectionFragment(false);
        }
        else if(changeCode==6)
        {
            if(tittleItem!=null)
            {
                tittleItem.setText("    涉案物品详细查询");
            }
             getItemDetailedFragment( removeFragment, true);
            // getAddCollectionFragment(false);
        }
        else if(changeCode==7)
        {
            if(tittleItem!=null)
            {
                tittleItem.setText("    暂存物品入库管理");
            }
            getTemporaryInFragment(removeFragment, true);
            //  getInFragmentScan( removeFragment, false);
            // getAddCollectionFragment(false);
        }
        else if(changeCode==8)
        {
            if(tittleItem!=null)
            {
                tittleItem.setText("    暂存物品出库返还管理");
            }
            getTemporaryOutFragment(removeFragment, true);
            //  getInFragmentScan( removeFragment, false);
            // getAddCollectionFragment(false);
        }
        else if(changeCode==-1)
        {
            if(tittleItem!=null)
            {
                tittleItem.setText("    涉案物品货架查询");
            }
            //  getInFragmentScan( removeFragment, false);
            // getAddCollectionFragment(false);
        }
        else if(changeCode==-4)
        {
            if(tittleItem!=null)
            {
                tittleItem.setText("    test");
            }
            getTestScan(removeFragment, true);
        }

    }

    private void getAddFragment(FragmentManager fm, String fragmentTag,Fragment oldFragment,boolean isRemoveOld)
    {
        if(oldFragment!=null)
        {
            if(isRemoveOld)
            {
                fm.beginTransaction()
                        .add(R.id.collecion_fg_content, main_fragment, fragmentTag)
                        .hide(oldFragment).remove(oldFragment)
                        .commit();
              //  fm.popBackStackImmediate(fragmentTag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
            else
            {
                fm.beginTransaction()
                        .add(R.id.collecion_fg_content, main_fragment, fragmentTag)
                        .hide(oldFragment)
                        .commit();
            }

        }
        else
        {
            fm.beginTransaction()
                    .add(R.id.collecion_fg_content, main_fragment, fragmentTag)
                    .commit();
        }
    }


    private void getShowFragment(FragmentManager fm, String fragmentTag, Fragment oldFragment,boolean isRemoveOld)
    {
        if(oldFragment!=null)
        {
            if(isRemoveOld)
            {
                fm.beginTransaction().show(main_fragment).hide(oldFragment).remove(oldFragment).commit();
               // fm.popBackStackImmediate(fragmentTag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
            else
            {
                fm.beginTransaction().show(main_fragment).hide(oldFragment).commit();
            }
        }
        else
        {
            fm.beginTransaction().show(main_fragment).commit();
        }
    }

    //切换到采集列表页面
    public void getInFragment(Fragment oldFragment,boolean isRemoveOld)
    {
        FragmentManager fm = getFragmentManager();

        main_fragment =fm.findFragmentByTag("InFragment");

        if(main_fragment==null)
        {
            main_fragment=new InFragment();
            getAddFragment(fm,"InFragment",oldFragment,isRemoveOld);

            //需保留切换Fragment用add
            //add B盖在A上  replace A销毁,B添加

        } else
        {
            getShowFragment(fm, "InFragment", oldFragment, isRemoveOld);
         // fm.beginTransaction().replace(R.id.collecion_fg_content, main_fragment, "collectionTableFragment").commit();

            // replace(R.id.collecion_fg_content, main_fragment, "collectionTableFragment").commit();
        }
        //   fm.executePendingTransactions();
    }

    //切换到人员详情页面
    public void getOutFragment(Fragment oldFragment,boolean isRemoveOld)
    {
        FragmentManager fm = getFragmentManager();

        main_fragment =fm.findFragmentByTag("OutFragment");
        if(main_fragment==null)
        {
            main_fragment=new OutFragment();
         //   ((CollectionPersonFragment)main_fragment).setCollectionTableData(collectionTableData);
            getAddFragment(fm, "OutFragment", oldFragment, isRemoveOld);

        } else
        {
          //  ((CollectionPersonFragment)main_fragment).setCollectionTableData(collectionTableData);
            getShowFragment(fm,"OutFragment",oldFragment,isRemoveOld);
           // fm.beginTransaction().replace(R.id.collecion_fg_content, main_fragment, "collectionPersonFragment")
                    // .addToBackStack(null)
           //         .commit();
        }
    }

    public void getBackFragment(Fragment oldFragment,boolean isRemoveOld)
    {
        FragmentManager fm = getFragmentManager();

        main_fragment =fm.findFragmentByTag("BackFragment");
        if(main_fragment==null)
        {
            main_fragment=new BackFragment();
            getAddFragment(fm, "BackFragment", oldFragment, isRemoveOld);

        } else
        {
            getShowFragment(fm,"BackFragment",oldFragment,isRemoveOld);

        }
    }

    public void getBackAdjustment(Fragment oldFragment,boolean isRemoveOld)
    {
        FragmentManager fm = getFragmentManager();

        main_fragment =fm.findFragmentByTag("AdjustmentFragment");
        if(main_fragment==null)
        {
            main_fragment=new AdjustmentFragment();
            getAddFragment(fm, "AdjustmentFragment", oldFragment, isRemoveOld);

        } else
        {
            getShowFragment(fm,"AdjustmentFragment",oldFragment,isRemoveOld);

        }
    }

    public void getItemDetailedFragment(Fragment oldFragment,boolean isRemoveOld)
    {
        FragmentManager fm = getFragmentManager();

        main_fragment =fm.findFragmentByTag("ItemDetailedFragment");
        if(main_fragment==null)
        {
            main_fragment=new ItemDetailedFragment();
            getAddFragment(fm, "ItemDetailedFragment", oldFragment, isRemoveOld);

        } else
        {
            getShowFragment(fm,"ItemDetailedFragment",oldFragment,isRemoveOld);

        }
    }

    public void getTemporaryInFragment(Fragment oldFragment,boolean isRemoveOld)
    {
        FragmentManager fm = getFragmentManager();

        main_fragment =fm.findFragmentByTag("TemporaryInFragment");
        if(main_fragment==null)
        {
            main_fragment=new TemporaryInFragment();
            getAddFragment(fm, "TemporaryInFragment", oldFragment, isRemoveOld);

        } else
        {
            getShowFragment(fm,"TemporaryInFragment",oldFragment,isRemoveOld);

        }
    }

    public void getTemporaryOutFragment(Fragment oldFragment,boolean isRemoveOld)
    {
        FragmentManager fm = getFragmentManager();

        main_fragment =fm.findFragmentByTag("TemporaryOutFragment");
        if(main_fragment==null)
        {
            main_fragment=new TemporaryOutFragment();
            getAddFragment(fm, "TemporaryOutFragment", oldFragment, isRemoveOld);

        } else
        {
            getShowFragment(fm,"TemporaryOutFragment",oldFragment,isRemoveOld);

        }
    }


    public void getInFragmentScan(Fragment oldFragment,boolean isRemoveOld)
    {
        FragmentManager fm = getFragmentManager();

        main_fragment =fm.findFragmentByTag("InFragmentScan");
        if(main_fragment==null)
        {
            main_fragment=new InFragmentScan();
            //   ((CollectionPersonFragment)main_fragment).setCollectionTableData(collectionTableData);
            getAddFragment(fm, "InFragmentScan", oldFragment, isRemoveOld);

        } else
        {
            //  ((CollectionPersonFragment)main_fragment).setCollectionTableData(collectionTableData);
            getShowFragment(fm,"InFragmentScan",oldFragment,isRemoveOld);
            // fm.beginTransaction().replace(R.id.collecion_fg_content, main_fragment, "collectionPersonFragment")
            // .addToBackStack(null)
            //         .commit();
        }
    }


    public void getTestScan(Fragment oldFragment,boolean isRemoveOld)
    {
        FragmentManager fm = getFragmentManager();

        main_fragment =fm.findFragmentByTag("FragmentBaseScan");
        if(main_fragment==null)
        {
            main_fragment=new FragmentBaseScan();
            //   ((CollectionPersonFragment)main_fragment).setCollectionTableData(collectionTableData);
            getAddFragment(fm, "FragmentBaseScan", oldFragment, isRemoveOld);

        } else
        {
            //  ((CollectionPersonFragment)main_fragment).setCollectionTableData(collectionTableData);
            getShowFragment(fm,"FragmentBaseScan",oldFragment,isRemoveOld);
            // fm.beginTransaction().replace(R.id.collecion_fg_content, main_fragment, "collectionPersonFragment")
            // .addToBackStack(null)
            //         .commit();
        }
    }


    //移出人员详情页面
    public Fragment getDelInFragment()
    {
        FragmentManager fm = getFragmentManager();

        main_fragment =fm.findFragmentByTag("InFragment");
        return main_fragment;
    }


    //切换到修改界面页面
    public Fragment getDelOutFragment()
    {
        FragmentManager fm = getFragmentManager();
        main_fragment =fm.findFragmentByTag("OutFragment");
        return main_fragment;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(data==null)
        {
            return;
        }
        if (resultCode == 0 && requestCode==151)
        {

            if (main_fragment != null) {
                main_fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
        else if (resultCode == 1 && requestCode==151)
        {

            if (main_fragment != null) {
                main_fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
        else if (resultCode == -1 && requestCode==151)
        {}
    }
}