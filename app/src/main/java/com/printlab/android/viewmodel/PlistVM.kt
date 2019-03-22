package com.printlab.android.viewmodel

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.android.volley.VolleyError
import com.google.gson.Gson
import com.printlab.android.network.GsonRequest
import com.printlab.android.utils.Logg
import java.io.File

class PlistVM(application: Application) : BaseVM(application), GsonRequest.Callback<BaseVM> {
    override fun success(tag: Any, t: BaseVM) {
    }

    override fun error(tag: Any, e: VolleyError?) {
    }

    /* companion object {
         const val TAG = "PlistVM"
     }

     val plistData = MutableLiveData<PlistObj>()
     private val encryptDecrypt = EncryptDecrypt()
     private var config: ConfigObj? = null
     fun init(isInternetConnected: Boolean = true) {
         Logg.d(TAG, "isInternetConnected:: $isInternetConnected")
         initConfig()
         checkPlistVersion()
     }

     private fun checkPlistVersion() {
         val url = BuildConfig.BASE_URL + application().getString(R.string.api_version)
         val params = hashMapOf<String, String>()
         params[AppConstants.ApiParams.REQUEST_ID] = "1"
         params[AppConstants.ApiParams.TYPE] = "2"
         params[AppConstants.ApiParams.VERSION] = config?.version.toString()
         val request = GsonRequest("version", url,
                 RespObj::class.java, null, params, this)
         VolleyReqQueue.getInstance(application()).addToRequestQueue(request)
     }

     override fun success(tag: Any, t: RespObj) {
         Logg.d(TAG, "success tag:: $tag\n${Gson().toJson(t)}")
         when (tag) {
             "version" -> {
                 config = t.config
                 config?.isEncrypted = true
                 if (config?.plistFlag == 1) {
                     encryptDecrypt.SetSecretKey(config?.privateKey + AppConstants.Key.PUBLIC_KEY)
                     val plistUrl = String(encryptDecrypt.decrypt(config?.plistPath))
                     Logg.d(TAG, "plist trackUrl:: $plistUrl")
                     val request = GsonRequest("plist", plistUrl, RespObj::class.java, null, null, this)
                     VolleyReqQueue.getInstance(application()).addToRequestQueue(request)
                 } else {
                     saveConfig(config)
                     initPlist()
                 }
             }
             "plist" -> {
                 FileManager.writeFile(application(), Gson().toJson(t), AppConstants.FILENAMES.PLIST)
                 saveConfig(config)
                 initPlist()
             }
         }
     }

     override fun error(tag: Any, e: VolleyError?) {
         Logg.d(TAG, "error:: $tag\n${e?.localizedMessage}")
         saveConfig(config)
         initPlist()
     }

     private fun saveConfig(config: ConfigObj?) {
         Logg.d(TAG, "saveConfig:: encrypted config:: ${Gson().toJson(config)}")
         if (config?.isEncrypted == true) {
             encryptDecrypt.SetSecretKey(config.privateKey + AppConstants.Key.PUBLIC_KEY)
             config.audioPath = String(encryptDecrypt.decrypt(config.audioPath))
             config.videoPath = String(encryptDecrypt.decrypt(config.videoPath))
             config.plistPath = String(encryptDecrypt.decrypt(config.plistPath))
             config.pdfPath = String(encryptDecrypt.decrypt(config.pdfPath))
         }
         Logg.d(TAG, "saveConfig:: decrypted config:: ${Gson().toJson(config)}")
         config?.isEncrypted = false
         PrefsManager.setData(application(), AppConstants.Key.CONFIG_DATA, Gson().toJson(config))
     }

     private fun initPlist() {
         val plistFile = File(application().filesDir, AppConstants.FILENAMES.PLIST)
         val plistStr = if (!plistFile.exists())
             FileManager.writeFile(application(), R.raw.plist, AppConstants.FILENAMES.PLIST)
         else
             FileManager.readFile(application(), AppConstants.FILENAMES.PLIST)
         mUiHandler.post {
             plistData.value = Gson().fromJson(plistStr, RespObj::class.java).plist
         }
     }

     private fun initConfig() {
         Logg.d(TAG, "initConfig")
         val configStr = PrefsManager.getData(application(), AppConstants.Key.CONFIG_DATA, "")
         config = if (configStr.isNotEmpty())
             Gson().fromJson(configStr, ConfigObj::class.java)
         else
             ConfigObj.local()
     }*/

}