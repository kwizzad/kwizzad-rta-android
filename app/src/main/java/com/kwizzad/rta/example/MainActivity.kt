package com.kwizzad.rta.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.kwizzad.adsbase.AdOpportunity
import com.kwizzad.rta.AdCallback
import com.kwizzad.rta.KwizzadRta
import com.kwizzad.rta.cmp.CmpDialog
import com.kwizzad.rta.debug.KwizzadDebugActivity

class MainActivity : AppCompatActivity() {

    private lateinit var btnShowAd : Button
    private lateinit var btnDebug: Button
    private lateinit var btnCmpDialog: Button
    private lateinit var tvLog: TextView

    private lateinit var rta: KwizzadRta

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLayout()
        initRta()
        initDebug()
        initCmp()
        loadRta()
    }

    private fun logConsentUpdated() {
        log("Consent is updated")
    }

    private fun initCmp() {
        btnCmpDialog.setOnClickListener {
            CmpDialog.show(supportFragmentManager) {
                runOnUiThread {
                    logConsentUpdated()
                }
            }
        }
    }

    private fun initDebug() {
        btnDebug.setOnClickListener {
            KwizzadDebugActivity.show(this, rta) {
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

    private fun initRta() {
        rta = KwizzadRta.create(this, "rta_android")
        rta.identifyUser("com.kwizzad.rta.example:3452462")
        rta.onCreate(this)
        btnShowAd.setOnClickListener {
            showRta()
        }
    }

    private fun showRta() {
        rta.showAd(this)
    }

    private fun loadRta() {
        rta.addAdCallback(object: AdCallback {
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
        rta.load(this, "interstitials")
    }

    private fun setAdAvailable(available: Boolean) {
        val logText : String
        btnShowAd.isEnabled = available
        if (available) {
            logText = "Ad is loaded"
            btnShowAd.text = "SHOW RTA"
        } else {
            logText = "Loading ads..."
            btnShowAd.text = "LOADING RTA..."
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
        rta.onStart(this)
    }

    override fun onResume() {
        super.onResume()
        rta.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        rta.onPause(this)
    }

    override fun onStop() {
        super.onStop()
        rta.onStop(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        rta.onDestroy(this)
    }
}
