# Get Started
This guide is for publishers who want to monetize an Android app with HyBidX SDK.

Integrating the HyBidX SDK into an app is the first step toward displaying ads and earning revenue.

## Prerequisites

- Use Android Studio 3.5 or later

- `minSdkVersion` 16 or later

- `compileSdkVersion` 28 or later

## Add the SDK to your Android project

__Step 1__. Add the repository to your build file.

Add it in the app's project-level `build.gradle` file:


```gradle
allprojects {
	repositories {
		...
		maven { url 'https://dl.bintray.com/pubnative/maven' }
	}
}
```

__Step 2__. Add the dependency


Open the app-level `build.gradle` file for your app, and look for a "dependencies" section

```gradle
dependencies {
	...
	implementation 'net.pubnative.android:hybidx:0.7.5-develop.252'
	...
}
```
Once that's done, save the file and perform a Gradle sync.

__Step3__. Add the following permission to your _AndroidManifest.xml_  file inside the manifest tag but outside the `<application>` tag:

```
<manifest xlmns:android="...">
	...
	<uses-permission android:name="android.permission.INTERNET" />
	
	<application>...</application>
</manifest>
```

## Integration

### Initialize

Before loading and showing ads, initialize the library by calling  `HyBidX.initialize(application)` .  This needs to be done only once at app launch (ideally in `Application.onCreate()` method).

```
class App: Application() {
	override fun onCreate() {
		super.onCreate()
		HyBidX.initialize(this)
	}
}
```

Create hyBidX object in your activity:
```
var hyBidX = HyBidX.create(activity, API_TOKEN)
```

After this, add lifecycle methods:
```
hyBidX.onCreate(activity)

hyBidX.onStart(activity)

hyBidX.onResume(activity)

hyBidX.onPause(activity)

hyBidX.onStop(activity)

hyBidX.onDestroy(activity)
```
to your activity lifecycle methods, and do it in every activity where you use ```hyBidX``` object



### Preload ads

Before starting to preload ads, create ``PreloadCallback``
```
val preloadCallback = object : PreloadCallback {
	override fun onAdAvailable(available: Boolean) {
		//called when ad available state changes
	}

	override fun onAdFailedToLoad(error: Throwable) {
		//called when ad cannot be preloaded
	}
}
```

and set it to ```hyBidX``` object:
```
hyBidX.setPreloadedCallback(preloadCallback)
```

then call ```load``` to start preloading:
```
hyBidX.load(activity, placement)
```

As soon as an ad is preloaded ```onAdAvailable``` will be called with param ```available``` equals to true. After this you can show and ad.



### Show

To do this, create a callback to receive ad result:

```
val showCallback = object : ShowCallback {
	override fun onAdOpened(adOpportunity: AdOpportunity) {
		//called before showing the ad
	}

	override fun onAdFinished(adOpportunity: AdOpportunity) {
		//called when the ad is finished successfully
	}

	override fun onAdError(error: Throwable) {
		//called when the ad finished with error
	}

	override fun onAdCancelled(adOpportunity: AdOpportunity) {
		//called when user cancels the ad
	}
}
```

then call ```show``` function:

```
hyBidX.showAd(this, showCallback)
```

You don't have to reload ads, it is done automatically. As soon as another ad is preloaded, you will receive  ```onAdAvailable``` callback with param ```available``` equals to true

## Add providers
VAST/VPAID is supported by default without any additional todo on your side.

Following providers can be enabled additionally:

 - AdMob (Interstitials, Rewarded Videos)
 - Facebook Audience Network (Interstitials, Rewarded Videos)
 - IronSource (Interstitials, Rewarded Videos)
 - Addapptr; including:
	 - Google Ad Manager (Interstitials, Rewarded Videos)
	 - SmartAdSErver (Interstitials)
	 - MoPub (Interstitials)
	 - Smaato (Interstitials)
	 - Applovin (Interstitials)
	 - Unity Ads (Interstitials, Rewarded Videos)
	 - Vungle (Interstitials)
	 - Ad Colony (Interstitials, Rewarded Videos)
	 - InMobi (Interstitials)
 - Fyber Marketplace (Interstitials, Rewarded Videos)
 - SpotX (VAST/VPAID)
 - FeedAd (Interstitials)

Only few lines of code are needed to enable them. Coming soon...
