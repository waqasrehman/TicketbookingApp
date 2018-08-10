package com.example.waqasur_rehman.myapplication;
import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;


/**
 * Edited by WAQAS UR-REHMAN on 26/11/2015.
 *
 * this class part of android volley library
 *
 */

public class AppController extends Application {
    public static final String TAG= AppController.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static AppController mInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
    }
    public static synchronized AppController getmInstance(){
        return mInstance;
    }
    public RequestQueue getmRequestQueue() {
        if(mRequestQueue==null){
            mRequestQueue= Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }
    public ImageLoader getmImageLoader(){
        getmRequestQueue();
        if(mImageLoader==null){
            mImageLoader=new ImageLoader(this.mRequestQueue,new BitmapCache());
        }
        return this.mImageLoader;
    }
    public <T> void addToRequesQueue(Request<T> request,String tag){
        request.setTag((TextUtils.isEmpty(tag) ? TAG : tag));
        getmRequestQueue().add(request);
    }
    public <T> void addToRequesQueue(Request<T> request){
        request.setTag(TAG);
        getmRequestQueue().add(request);
    }
    public void cancelPendingRequest(Object tag){
        if(mRequestQueue !=null){
            mRequestQueue.cancelAll(tag);
        }
    }
}