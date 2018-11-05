package com.taiji.article.caseitem.viewmanager;

import android.content.Context;

import com.taiji.article.caseitem.storageData.CaseScanData;
import com.taiji.article.caseitem.storageData.CaseShelfData;

import java.util.List;

/**
 * Created by z0 on 2016/3/21.
 */
public class CaseShelfSearchByItemViewTask extends CaseShelfSearchTask {


    List<CaseScanData> l_CaseScanData=null;
    CaseScanAdapter caseScanAdapter=null;
    public CaseShelfSearchByItemViewTask(Context context, String progressId, CaseShelfData caseShelfData,List<CaseScanData> l_CaseScanData,CaseScanAdapter caseScanAdapter) {
        super(context,progressId,caseShelfData);
        this.l_CaseScanData = l_CaseScanData;
        this.caseScanAdapter = caseScanAdapter;
    }

    @Override
    protected void onPostExecute(String result) {

        isRefresh=false;
        String resultCode=result;
        if(completeBack!=null)
        {
            completeBack.completeBack(progressId, resultCode,caseShelfData,l_CaseScanData,caseScanAdapter);

        }
    }


    @Override
    protected void onCancelled() {
        System.out.println("cancel");
    }

    public interface completeListener
    {
        public void completeBack(String progressId, String result, CaseShelfData caseShelfData,List<CaseScanData> l_CaseScanData,CaseScanAdapter caseScanAdapter);
    }

    private completeListener completeBack;
    public void setCompleteListener(completeListener l) {
        completeBack = l;
    }

}

