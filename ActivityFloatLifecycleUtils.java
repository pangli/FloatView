package com.sino.gameplus.widget;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.sino.core.common.GPDictionaryUtils;
import com.sino.core.debug.GPLog;

import java.lang.ref.WeakReference;
import java.util.LinkedHashSet;

/**
 * Package:   com.sino.gameplus.widget
 * ClassName: ActivityFloatLifecycleUtils
 * Created by Zorro on 2020/5/18 18:21
 * 备注：通过生命周期回调，判断系统浮窗的信息，以及app是否位于前台
 */
public class ActivityFloatLifecycleUtils {
    //    private static Application application;
//    private static Application.ActivityLifecycleCallbacks lifecycleCallbacks;
    private static ViewGroup.LayoutParams mParams = new ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    private static WeakReference<Activity> mWeakAct;
    private static LinkedHashSet<String> filterSet = GPDictionaryUtils.getFilterSet();

    public static Activity getCurrentActivity() {
        if (mWeakAct != null && mWeakAct.get() != null && !(mWeakAct.get()).isFinishing()) {
            return mWeakAct.get();
        }
        return null;
    }

    public static void setLifecycleCallbacks(final Application application) {
        if (application != null) {
//            FloatLifecycleUtils.application = application;
            application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
                public void onActivityCreated(@Nullable Activity activity, @Nullable Bundle savedInstanceState) {
                }

                public void onActivityStarted(@Nullable Activity activity) {

                }

                // 每次都要判断当前页面是否需要显示
                public void onActivityResumed(@Nullable Activity activity) {
                    try {
                        if (activity != null) {
                            mWeakAct = new WeakReference<>(activity);
                            if (filterSet != null) {
                                if (!filterSet.contains(activity.getComponentName().getClassName())) {
                                    ((ViewGroup) activity.findViewById(android.R.id.content)).addView(ActivityFloatManager.create(application), mParams);
                                    ActivityFloatManager.setVisibility(true);
                                }
                            } else {
                                ((ViewGroup) activity.findViewById(android.R.id.content)).addView(ActivityFloatManager.create(application), mParams);
                                ActivityFloatManager.setVisibility(true);
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        GPLog.e(e.getMessage());
                    }

                }

                // 每次都要判断当前页面是否需要显示
                public void onActivityPaused(@Nullable Activity activity) {
                    try {
                        if (activity != null) {
                            if (filterSet != null) {
                                if (!filterSet.contains(activity.getComponentName().getClassName())) {
                                    ActivityFloatManager.setVisibility(false);
                                    ((ViewGroup) activity.findViewById(android.R.id.content)).removeView(ActivityFloatManager.create(application));
                                }
                            } else {
                                ActivityFloatManager.setVisibility(false);
                                ((ViewGroup) activity.findViewById(android.R.id.content)).removeView(ActivityFloatManager.create(application));
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        GPLog.e(e.getMessage());
                    }

                }

                public void onActivityStopped(@Nullable Activity activity) {


                }

                public void onActivityDestroyed(@Nullable Activity activity) {
                }

                public void onActivitySaveInstanceState(@Nullable Activity activity, @Nullable Bundle outState) {
                }
            });
        } else {
            Log.e("LifecycleUtils", "application is null");
        }

    }

    public static void setLifecycleCallbacks(final Application application, final FloatConfig config) {
        if (application != null) {
//            FloatLifecycleUtils.application = application;
            application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
                public void onActivityCreated(@Nullable Activity activity, @Nullable Bundle savedInstanceState) {
                }

                public void onActivityStarted(@Nullable Activity activity) {

                }

                // 每次都要判断当前页面是否需要显示
                public void onActivityResumed(@Nullable Activity activity) {
                    try {
                        if (activity != null) {
                            mWeakAct = new WeakReference<>(activity);
                            if (config != null && config.getFilterSet() != null) {
                                if (!config.getFilterSet().contains(activity.getComponentName().getClassName())) {
                                    ((ViewGroup) activity.findViewById(android.R.id.content)).addView(ActivityFloatManager.create(application), mParams);
                                }
                            } else {
                                ((ViewGroup) activity.findViewById(android.R.id.content)).addView(ActivityFloatManager.create(application), mParams);
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        GPLog.e(e.getMessage());
                    }

                }

                // 每次都要判断当前页面是否需要显示
                public void onActivityPaused(@Nullable Activity activity) {
                    try {
                        if (activity != null) {
                            if (config != null && config.getFilterSet() != null) {
                                if (!config.getFilterSet().contains(activity.getComponentName().getClassName())) {
                                    ((ViewGroup) activity.findViewById(android.R.id.content)).removeView(ActivityFloatManager.create(application));
                                }
                            } else {
                                ((ViewGroup) activity.findViewById(android.R.id.content)).removeView(ActivityFloatManager.create(application));
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        GPLog.e(e.getMessage());
                    }

                }

                public void onActivityStopped(@Nullable Activity activity) {


                }

                public void onActivityDestroyed(@Nullable Activity activity) {
                }

                public void onActivitySaveInstanceState(@Nullable Activity activity, @Nullable Bundle outState) {
                }
            });
        } else {
            Log.e("LifecycleUtils", "application is null");
        }

    }


//    public static void release() {
//        if (application != null && lifecycleCallbacks != null) {
//            FloatingView.getInstance(application).destroy();
//            application.unregisterActivityLifecycleCallbacks(lifecycleCallbacks);
//            lifecycleCallbacks = null;
//            mParams = null;
//            application = null;
//        } else {
//            Log.e("LifecycleUtils", "application is null");
//        }
//    }
}
