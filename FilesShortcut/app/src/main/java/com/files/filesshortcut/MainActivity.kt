package com.files.filesshortcut

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.provider.UserDictionary.Words.APP_ID
import android.webkit.MimeTypeMap
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.adcolony.sdk.*
import com.google.android.material.floatingactionbutton.FloatingActionButton

import net.theluckycoder.materialchooser.Chooser
import java.io.File


class MainActivity : AppCompatActivity() {
    private var bannerAdColony: AdColonyAdView? = null
    private companion object {
        private const val REQUEST_CODE = 10
        private const val BANNER_ID="aaa"
        private const val APP_ID= "aaa"
        val AD_UNIT_Zone_Ids = arrayOf(BANNER_ID)


    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initColonySdk()
        initBannerAd()

        openFileChooser()


    }


    //otwarcie file pickera
    private fun openFileChooser() {

        val floatingActionButton = findViewById<FloatingActionButton>(R.id.floatingActionButton)

        floatingActionButton.setOnClickListener {

            Chooser(this, 10,
                    startPath = Environment.getExternalStorageDirectory().absolutePath + "/Download/"

            ).start()
        }


    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        try{
        data ?: return

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
             val path = data.getStringExtra(Chooser.RESULT_PATH)
             var fileName: String?= path!!.substringAfterLast("/")
             val filePath= File(path)

            //val uriPath=path.toUri()

            //pobieranie typu danych z pliku
            val mimePath= FileProvider.getUriForFile(this,"com.example.homefolder.example.provider",filePath)
            val extension = MimeTypeMap.getFileExtensionFromUrl(mimePath.toString())



            //zadeklarowanie alert dialogu z możliwością zmiany nazwy skrótu
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle("Set shortcut name")
            val inputEditText = EditText(this)

            inputEditText.setHint("input shortcut name")
            inputEditText.setText(fileName)
            builder.setView(inputEditText)

            builder.setPositiveButton("Set shortcut", DialogInterface.OnClickListener { dialog, which ->
                fileName = inputEditText.text.toString()

                //wyslanie danych do obiektu sprawdzającego typ MIME
                if (extension != null) {
                    Shortcuts.extensionGet(extension)
                }
                //wysłanie danych do deklaracji skrótu
                Shortcuts.setUp(applicationContext, filePath, fileName!!)
            })
            builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

            builder.show()
        }


    } catch (e:Exception) {
        var error=e.localizedMessage
        Toast.makeText(applicationContext,"Error occured"+e,Toast.LENGTH_LONG).show()

    }

        }

    //inicjalizacja AdColony
    private fun initColonySdk() {
        // Construct optional app options object to be sent with configure
        val appOptions = AdColonyAppOptions().setKeepScreenOn(true)
        // Configure AdColony in your launching Activity's onCreate() method so that cached ads can
        // be available as soon as possible.
        AdColony.configure(application, appOptions, APP_ID, *AD_UNIT_Zone_Ids)
    }

    private fun initBannerAd() {
        try {
            val bannerConatiner = findViewById<LinearLayout>(R.id.bannerConatiner)
            //Remove previous ad view if present.
            val bannerListener: AdColonyAdViewListener = object : AdColonyAdViewListener() {
                override fun onRequestFilled(adColonyAdView: AdColonyAdView) {
                    //Remove previous ad view if present.
                    if (bannerConatiner.childCount > 0) {
                        bannerConatiner.removeView(bannerAdColony)
                    }
                    bannerConatiner.addView(adColonyAdView)
                    bannerAdColony = adColonyAdView
                }

                override fun onRequestNotFilled(zone: AdColonyZone) {
                    super.onRequestNotFilled(zone)
                }

                override fun onOpened(ad: AdColonyAdView) {
                    super.onOpened(ad)
                }

                override fun onClosed(ad: AdColonyAdView) {
                    super.onClosed(ad)
                }

                override fun onClicked(ad: AdColonyAdView) {
                    super.onClicked(ad)
                }

                override fun onLeftApplication(ad: AdColonyAdView) {
                    super.onLeftApplication(ad)
                }
            }
            // Optional Ad specific options to be sent with request
            val adOptions = AdColonyAdOptions()
            //Request Ad
            AdColony.requestAdView(BANNER_ID, bannerListener, AdColonyAdSize.BANNER, adOptions)

        }catch (e:Exception){

            Toast.makeText(this,e.localizedMessage,Toast.LENGTH_LONG).show()
        }
        }



    }





