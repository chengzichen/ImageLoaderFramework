package com.lasingwu.baselibrary;

import android.content.Context;
import android.view.View;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Administrator on 2017/3/22 0022.
 */

public class ImageLoaderManager {
    private static final ImageLoaderManager INSTANCE=new ImageLoaderManager();
    private  IImageLoaderstrategy loaderstrategy;
    private HashMap<LoaderEnum,IImageLoaderstrategy> imageloaderMap=new HashMap<>();
    private LoaderEnum curLoader = null;
    private static int holderDrawable=-1;  // 设置展位图
    private static int errorDrawable=-1;  //是否展示加载错误的图片
    private ImageLoaderManager(){
    }
    public static ImageLoaderManager getInstance(){
        return INSTANCE;
    }

    /*
     *   可创建默认的Options设置，假如不需要使用ImageView ，
     *    请自行new一个Imageview传入即可
     *  内部只需要获取Context
     */
    public static ImageLoaderOptions getDefaultOptions(@NonNull View container, @NonNull String url){
        ImageLoaderOptions.Builder builder =
                new ImageLoaderOptions.Builder(container, url).isCrossFade(true);
        return builder.build();
    }
    /*
     *   可创建默认的Options设置，假如不需要使用ImageView ，
     *    请自行new一个Imageview传入即可
     *  内部只需要获取Context
     */
    public static ImageLoaderOptions getHolderOptions(@NonNull View container,
                                                          @NonNull String url){
        ImageLoaderOptions.Builder builder =
                new ImageLoaderOptions.Builder(container, url).isCrossFade(true);
        if (holderDrawable!=-1){
            builder.placeholder(holderDrawable);
        }
        if (errorDrawable!=-1){
            builder.error(errorDrawable);
        }
        return builder.build();
    }

    public void showImage(@NonNull ImageLoaderOptions options) {
        if (getLoaderstrategy(curLoader) != null) {
            getLoaderstrategy(curLoader).showImage(options);
        }
    }
    public void showImage(@NonNull ImageLoaderOptions options,LoaderEnum loaderEnum) {
        if (getLoaderstrategy(loaderEnum) != null) {
            getLoaderstrategy(loaderEnum).showImage(options);
        }
    }

    public void hideImage(@NonNull View view, int visiable) {
        if (getLoaderstrategy(curLoader) != null) {
            getLoaderstrategy(curLoader).hideImage(view,visiable);
        }
    }


    public void cleanMemory(Context context) {
        getLoaderstrategy(curLoader).cleanMemory(context);
    }

    public void pause(Context context) {
        if (getLoaderstrategy(curLoader) != null) {
            getLoaderstrategy(curLoader).pause(context);
        }
    }

    public void resume(Context context) {
        if (getLoaderstrategy(curLoader) != null) {
            getLoaderstrategy(curLoader).resume(context);
        }
    }
    public void setCurImageLoader(LoaderEnum loader) {
        curLoader=loader;
    }
    // 在application的oncreate中初始化
    public void init(Context context,ImageLoaderConfig config) {
        imageloaderMap = config.getImageloaderMap();
        for (Map.Entry<LoaderEnum, IImageLoaderstrategy> entry : imageloaderMap.entrySet()) {
            if (entry.getValue() != null) {
                entry.getValue().init(context,config);
            }

            if (curLoader == null) {
                curLoader=entry.getKey();
            }
        }
    }
    private IImageLoaderstrategy getLoaderstrategy(LoaderEnum loaderEnum){
        return imageloaderMap.get(loaderEnum);
    }

    public void setHolderDrawable(@DrawableRes int holderDrawable) {
        this.holderDrawable = holderDrawable;
    }

    public void setErrorDrawable(@DrawableRes int errorDrawable) {
        this.errorDrawable = errorDrawable;
    }
}
