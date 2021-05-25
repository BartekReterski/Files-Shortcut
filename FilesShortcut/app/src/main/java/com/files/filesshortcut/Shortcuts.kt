package com.files.filesshortcut

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.os.Build
import android.webkit.MimeTypeMap
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import java.io.File


//Requires api level 25
@RequiresApi(Build.VERSION_CODES.N_MR1)
object Shortcuts {
    var extenstionInt=0
    fun extensionGet(extString:String) {

        try {
            extenstionInt = when (extString) {

                "aac" -> R.drawable.aac
                "ai" -> R.drawable.ai
                "avi" -> R.drawable.avi
                "bmp" -> R.drawable.bmp
                "cad" -> R.drawable.cad
                "cdr" -> R.drawable.cdr
                "css" -> R.drawable.css
                "dat" -> R.drawable.dat
                "dll" -> R.drawable.dll
                "dmg" -> R.drawable.dmg
                "doc" -> R.drawable.doc
                "eps" -> R.drawable.eps
                "fla" -> R.drawable.fla
                "flv" -> R.drawable.flv
                "gif" -> R.drawable.gif
                "html" -> R.drawable.html
                "indd" -> R.drawable.indd
                "iso" -> R.drawable.iso
                "jpg" -> R.drawable.jpg
                "js" -> R.drawable.js
                "midi" -> R.drawable.midi
                "mov" -> R.drawable.mov
                "mp3" -> R.drawable.mp3
                "mp4" -> R.drawable.mp4
                "mpg" -> R.drawable.mpg
                "pdf" -> R.drawable.pdf
                "php" -> R.drawable.php
                "png" -> R.drawable.png
                "ppt" -> R.drawable.ppt
                "pptx" -> R.drawable.ppt
                "ps" -> R.drawable.ps
                "psd" -> R.drawable.psd
                "raw" -> R.drawable.raw
                "sql" -> R.drawable.sql
                "svg" -> R.drawable.svg
                "tif" -> R.drawable.tif
                "txt" -> R.drawable.txt
                "wmw" -> R.drawable.wmv
                "xls" -> R.drawable.xls
                "xml" -> R.drawable.xml
                "zip" -> R.drawable.zip
                "docx"->R.drawable.docx
                "mobi"->R.drawable.mobi
                "epub"->R.drawable.epub
                else -> R.drawable.unknownformat
            }
        }catch (e:java.lang.Exception){

            val ex= e.localizedMessage
            extenstionInt=R.drawable.unknownformat
        }


    }

    fun setUp(context: Context, filePath: File, fileName:String) {
        try {

            val intent= Intent(Intent.ACTION_VIEW)
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            intent.data = FileProvider.getUriForFile(context,"com.example.homefolder.example.provider",filePath)

            val shortcutManager = context.getSystemService(ShortcutManager::class.java)
            if (shortcutManager!!.isRequestPinShortcutSupported) {
                val randomString =java.util.UUID.randomUUID().toString()
                val pinShortcutInfo = ShortcutInfo.Builder(context, randomString)
                        .setShortLabel(fileName)
                        .setIcon(Icon.createWithResource(context, extenstionInt))
                        .setLongLabel(fileName)
                        .setIntent(intent)


                        .build()
                val pinnedShortcutCallbackIntent = shortcutManager.createShortcutResultIntent(pinShortcutInfo)
                val successCallback = PendingIntent.getBroadcast(context, 0, pinnedShortcutCallbackIntent, 0)
                shortcutManager.requestPinShortcut(pinShortcutInfo, successCallback.intentSender)
            }
        }catch (e:Exception){
            val ex=e
        }
    }

}




