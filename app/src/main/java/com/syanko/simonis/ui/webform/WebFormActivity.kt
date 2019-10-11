package com.syanko.simonis.ui.webform

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.syanko.simonis.R
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_web_form.*
import javax.inject.Inject

class WebFormActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: WebFormViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_form)

        viewModel = ViewModelProviders.of(this, viewModelFactory)[WebFormViewModel::class.java]

        viewModel.token.observe(this, Observer { token ->
            loadWebView(token)
        })
        viewModel.getToken()
    }

    override fun onBackPressed() {
        showDialogCloseForm()
    }

    private fun showDialogCloseForm() {
        val dialog: AlertDialog
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage("Data yang sudah anda isi akan hilang saat keluar form, Batalkan Inspeksi?")
        builder.setPositiveButton("Lanjutkan") { activeDialog, _ ->
            activeDialog.dismiss()
            finish()
        }
        builder.setNegativeButton("Tutup") { activeDialog, _ ->
            activeDialog.dismiss()
        }

        dialog = builder.create()
        dialog.show()
    }

    private fun showDialogSuccess() {
        val dialog: AlertDialog
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage("Form Berhasil Terkirim!")
        builder.setPositiveButton("Tutup") { activeDialog, _ ->
            activeDialog.dismiss()
            finish()
        }

        dialog = builder.create()
        dialog.show()
    }

    private fun loadWebView(token: String) {
        web.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                val key = "simonis_access_token"
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    web.evaluateJavascript("localStorage.setItem('$key','$token');", null)
                } else {
                    web.loadUrl("javascript:localStorage.setItem('$key','$token');")
                }
            }

            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                request?.let {
                    handleUrlRedirect(it.url.toString())
                }
                return true
            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                url?.let {
                    handleUrlRedirect(url)
                }
                return true
            }

            private fun handleUrlRedirect(url: String) {
                Log.wtf("url", url)
                if (url.contains("formsuccess")) {
                    showDialogSuccess()
                }
            }
        }

        web.settings.apply {
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            domStorageEnabled = true
            allowFileAccessFromFileURLs = true
            allowContentAccess = true
            allowFileAccess = true
            loadWithOverviewMode = true
            useWideViewPort = true

            setSupportZoom(true)
            builtInZoomControls = true
            displayZoomControls = false
        }
        web.scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
        web.isScrollbarFadingEnabled = true

        val inspectionId = intent?.extras?.getInt(INSPECTION_ID_EXTRA, -1)
        web.loadUrl("http://web.simonis.woicaksono.com/mobile/form/$inspectionId")
    }

    companion object {
        const val INSPECTION_ID_EXTRA = ".inspection_id_extra"

        fun openWebForm(context: Context, withInspectionId: Int): Intent {
            val intent = Intent(context, WebFormActivity::class.java)
            intent.putExtra(INSPECTION_ID_EXTRA, withInspectionId)

            return intent
        }
    }
}
