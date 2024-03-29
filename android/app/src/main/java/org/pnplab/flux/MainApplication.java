package org.pnplab.flux;

import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import com.aware.Aware;
import com.aware.utils.SSLManager;
import com.facebook.react.ReactApplication;
import com.zoontek.rndevmenu.RNDevMenuPackage;
import com.airbnb.android.react.lottie.LottiePackage;
import io.invertase.firebase.RNFirebasePackage;
import io.invertase.firebase.messaging.RNFirebaseMessagingPackage;
import com.horcrux.svg.SvgPackage;
import com.learnium.RNDeviceInfo.RNDeviceInfo;
import com.brentvatne.react.ReactVideoPackage;
import px.fluidicslider.RNFluidicSliderPackage;
import com.oblador.vectoricons.VectorIconsPackage;
import io.realm.react.RealmReactPackage;
import com.BV.LinearGradient.LinearGradientPackage;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainApplication extends Application implements ReactApplication {

    private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
        @Override
        public boolean getUseDeveloperSupport() {
            // @note always display dev menu
            return true || BuildConfig.DEBUG;
        }

        @Override
        protected List<ReactPackage> getPackages() {
            List<ReactPackage> list = new ArrayList<>(Arrays.asList(
                new MainReactPackage(),
                new RNDevMenuPackage(),
                new LottiePackage(),
                new RNFirebasePackage(),
                new RNFirebaseMessagingPackage(),
                new SvgPackage(),
                new RNDeviceInfo(),
                new ReactVideoPackage(),
                new AwareManagerPackage(),
                new RNFluidicSliderPackage(),
                new VectorIconsPackage(),
                new RealmReactPackage(),
                new LinearGradientPackage()
            ));

            // MuseManagerPackage is only compatible with ARM v7 devices. Avoid launch-time errors
            // on android emulator (which is x86 on osx) by adding it conditionally.
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {
                Log.e("Flux", "Checking MUSE compatibility. Android version < LOLLIPOP " + android.os.Build.VERSION.SDK_INT);
            }
            else {
                // @note List -> SUPPORTED_ABIS `https://developer.android.com/ndk/guides/abis`
                String[] archs = new String[0];
                archs = Build.SUPPORTED_ABIS;
                if (!Arrays.asList(archs).contains("armeabi-v7a")) {
                    Log.e("Flux", "Checking MUSE compatibility. 'armeabi-v7a' is not supported.");
                }
                else {
                    Log.i("Flux", "Checking MUSE compatibility. MUSE appears to be compatible.");
                    list.add(new MuseManagerPackage());
                }
            }

            return list;
        }

        @Override
        protected String getJSMainModuleName() {
        return "index";
      }
    };

    @Override
    public ReactNativeHost getReactNativeHost() {
      return mReactNativeHost;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SoLoader.init(this, /* native exopackage */ false);

        String arch = System.getProperty("os.arch");
        Log.i("Flux", "Current arch: " + arch);
    }
}
