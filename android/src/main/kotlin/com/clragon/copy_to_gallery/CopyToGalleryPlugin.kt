package com.clragon.copy_to_gallery

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_MEDIA_SCANNER_SCAN_FILE
import android.net.Uri
import android.os.Environment
import android.os.Handler
import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar
import java.io.File
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.concurrent.thread


const val methodName: String = "com.clragon/copy_to_gallery"
var context: Context? = null

public class CopyToGalleryPlugin : FlutterPlugin, MethodCallHandler {
    private val handler: Handler = Handler()
    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        context = flutterPluginBinding.applicationContext
        val channel = MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), methodName)
        channel.setMethodCallHandler(CopyToGalleryPlugin())

    }

    companion object {
        @JvmStatic
        fun registerWith(registrar: Registrar) {
            context = registrar.activity()

            val channel = MethodChannel(registrar.messenger(), methodName)
            channel.setMethodCallHandler(CopyToGalleryPlugin())
        }
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        when (call.method) {
            "createAlbum" -> createAlbum(call, result)
            "saveAlbum" -> saveAlbum(call, result)
            else -> result.notImplemented()
        }
    }

    private fun saveAlbum(call: MethodCall, result: Result) {

        val albumName = call.argument<String>("albumName")
        val files = call.argument<Map<String, String>>("files")
        if (albumName == null) {
            result.error("100", "albumName cannot be null", null)
            return
        }
        if (files == null) {
            result.error("101", "files cannot be null", null)
            return
        }
        thread {
            val rootFile = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), albumName)
            var resultPaths = mutableListOf<String>()

            for ((path, name) in files) {
                val org = File(path)
                var fileName = name
                if (name.isNullOrEmpty()) {
                    val now = LocalDateTime.now()
                    val formatter = DateTimeFormatter.ofPattern("YYYYYMMdd_HHmmssSS")
                    fileName = now.format(formatter)
                }
                val target = File(rootFile, fileName)
                try {
                    org.copyTo(target, true)
                } catch (exception: NoSuchFileException) {
                    result.error("102", "file at given path does not exist", null)
                } catch (exception: IOException) {
                    result.error("103", "couldnt copy file", null)
                }
                
                resultPaths.add(target.absolutePath)
                handler.post {
                    context!!.sendBroadcast(Intent(ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(target)))
                }
            }
            handler.post {
                result.success(resultPaths)
            }
        }
    }

    private fun createAlbum(call: MethodCall, result: Result) {
        val albumName = call.argument<String>("albumName")
        if (albumName == null) {
            result.error("100", "albumName is not null", null)
            return
        }
        thread {
            val rootFile = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), albumName)
            if (!rootFile.exists()) {
                rootFile.mkdirs()
            }
            handler.post {
                result.success(rootFile.absolutePath)
            }
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        context = null

    }
}
