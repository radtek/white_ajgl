package orcode.pubsec.taiji.com.business.util;

import android.content.Context;

import com.taiji.log.httpLogUtil;

/**
 * Created by z0 on 2016/12/1.
 */
public class contextUtil {
    public static Context mContext = null;
    public static void setContext(Context context)
    {
        mContext=context;
        httpLogUtil.mContext=context;
    }
}
