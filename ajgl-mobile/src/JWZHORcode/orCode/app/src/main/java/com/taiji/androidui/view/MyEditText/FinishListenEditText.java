package com.taiji.androidui.view.MyEditText;

import android.content.Context;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.widget.EditText;

/**
 * Created by z0 on 2017/2/23.
 */
public class FinishListenEditText extends EditText {


    public boolean isFinishUser=true;//��������Ƿ����û�����,�������setTextҲ�ᴥ�����,��ʱ��Ӧ����
    public FinishListenEditText(Context context) {
        super(context);
    }

    public FinishListenEditText(Context context, AttributeSet attrs) {
        //���ﹹ�췽��Ҳ����Ҫ����������ܶ����Բ�����XML���涨��
        super(context, attrs, android.R.attr.editTextStyle);

      //  this(context, attrs, android.R.attr.editTextStyle);
    }


    private OnFinishComposingListener mFinishComposingListener;

    public void setOnFinishComposingListener(OnFinishComposingListener listener) {
        this.mFinishComposingListener = listener;
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        return new MyInputConnection(super.onCreateInputConnection(outAttrs), false);
    }


    public class MyInputConnection extends InputConnectionWrapper {
        public MyInputConnection(InputConnection target, boolean mutable) {
            super(target, mutable);
        }

        @Override
        public boolean finishComposingText() {
            boolean finishComposing = super.finishComposingText();
            if (mFinishComposingListener != null && isFinishUser) {
                mFinishComposingListener.finishComposing(FinishListenEditText.this);
            }
            isFinishUser=true;
            return finishComposing;
        }
    }

    public void setEditText(String text)
    {
        if(this.getText().toString().equals(text))
        {
            return;
        }
        //设置文本前标记本次修改是业务修改,不是用户修改,finishComposingText的回调是异步的,可能存在延迟导致异常,目前看延迟很短基本没出现过问题
        if (mFinishComposingListener != null) {
            isFinishUser = false;
        }
        this.setText(text);

    }

    public interface OnFinishComposingListener {
        public void finishComposing(FinishListenEditText fEditText);
    }
}

