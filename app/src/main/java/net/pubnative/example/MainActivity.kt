package net.pubnative.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import net.pubnative.adsbase.AdOpportunity
import net.pubnative.hybidx.AdCallback
import net.pubnative.hybidx.HyBidX
import net.pubnative.hybidx.cmp.CmpDialog
import net.pubnative.hybidx.debug.HyBidXDebugActivity

class MainActivity : AppCompatActivity() {

    private lateinit var btnShowAd : Button
    private lateinit var btnDebug: Button
    private lateinit var btnCmpDialog: Button
    private lateinit var tvLog: TextView

    private lateinit var hyBidX: HyBidX

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLayout()
        initHyBidX()
        initDebug()
        initCmp()
        loadHyBidX()
    }

    private fun logConsentUpdated() {
        log("Consent is updated")
    }

    private fun initCmp() {
        btnCmpDialog.setOnClickListener {
            CmpDialog.show(this, supportFragmentManager) {
                runOnUiThread {
                    logConsentUpdated()
                }
            }
        }
    }

    private fun initDebug() {
        btnDebug.setOnClickListener {
            HyBidXDebugActivity.show(this, hyBidX) {
                log("Ad result from debug page: $it")
            }
        }
    }

    private fun initLayout() {
        setContentView(R.layout.activity_main)
        btnShowAd = findViewById(R.id.btn_show_ad)
        btnCmpDialog = findViewById(R.id.btn_show_cmp)
        btnDebug = findViewById(R.id.btn_show_debug)
        tvLog = findViewById(R.id.log)
    }

    private fun initHyBidX() {
        hyBidX = HyBidX.create(this, "rta_android")
        hyBidX.identifyUser("net.pubnative.example:3452462")
        hyBidX.onCreate(this)
        btnShowAd.setOnClickListener {
            showHyBidX()
        }
    }

    private fun showHyBidX() {
        hyBidX.showAd(this)
    }

    private fun loadHyBidX() {
        hyBidX.addAdCallback(object: AdCallback {
            override fun onAdAvailable(available: Boolean) {
                setAdAvailable(available)
            }

            override fun onAdFailedToLoad(error: Throwable) {
                log("onAdFailedToLoad : ${error.message ?: error.localizedMessage}")
            }

            override fun onAdCancelled(adOpportunity: AdOpportunity) {
                log("onAdCancelled : $adOpportunity")
            }

            override fun onAdError(adOpportunity: AdOpportunity?, error: Throwable) {
                log("onAdError : ${error.message ?: error.localizedMessage}")
            }

            override fun onAdFinished(adOpportunity: AdOpportunity) {
                log("onAdFinished : $adOpportunity")
            }

            override fun onAdOpened(adOpportunity: AdOpportunity) {
                log("onAdOpened : $adOpportunity")
            }
        })
        hyBidX.load(this, "interstitials")
    }

    private fun setAdAvailable(available: Boolean) {
        val logText : String
        btnShowAd.isEnabled = available
        if (available) {
            logText = "Ad is loaded"
            btnShowAd.text = "SHOW HyBidX"
        } else {
            logText = "Loading ads..."
            btnShowAd.text = "LOADING HyBidX..."
        }
        log(logText)
    }


    private fun log(text: String) {
        tvLog.text =  "${tvLog.text}\n\n$text"
    }

    private fun log(throwable: Throwable) {
        tvLog.text =  "${tvLog.text}\n\n${throwable.message}"
    }

    override fun onStart() {
        super.onStart()
        hyBidX.onStart(this)
    }

    override fun onResume() {
        super.onResume()
        hyBidX.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        hyBidX.onPause(this)
    }

    override fun onStop() {
        super.onStop()
        hyBidX.onStop(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        hyBidX.onDestroy(this)
    }
}
