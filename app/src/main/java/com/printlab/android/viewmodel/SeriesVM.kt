package com.printlab.android.viewmodel

import android.app.Application
import com.google.gson.Gson


class SeriesVM(application: Application) : BaseVM(application) {

    /* val categoryData = MutableLiveData<List<String>?>()
     val seriesData = MutableLiveData<ArrayList<SeriesObj>>()
     private var config: ConfigObj? = null
     private var plist: PlistObj? = null
     private var prodIds: ArrayList<String> = arrayListOf()
     private var ownedProdIds = arrayListOf<String>()
     private var productsList = arrayListOf<ProductDetailObj>()
     fun init(plist: PlistObj?) {
         this.plist = plist
         val configStr = PrefsManager.getData(application(), AppConstants.Key.CONFIG_DATA, "")
         config = Gson().fromJson(configStr, ConfigObj::class.java)
     }

     fun init(prodIds: ArrayList<String>) {
         this.prodIds = prodIds
     }

     fun filterDropdown(type: Int) {
         mBgHandler?.post {
             when (type) {
                 TrackObj.Type.AUDIO -> {
                     val categories = plist?.audioSeries?.filter { it.category != null }
                             ?.map { it.category!! }
                             ?.distinct()
                     mUiHandler.post { categoryData.value = categories }
                 }
                 TrackObj.Type.VIDEO -> {
                     val categories = plist?.videoSeries?.filter { it.category != null }
                             ?.map { it.category!! }
                             ?.distinct()
                     mUiHandler.post { categoryData.value = categories }
                 }
                 TrackObj.Type.PDF -> {
                     val categories = plist?.pdfSeries?.filter { it.category != null }
                             ?.map { it.category!! }
                             ?.distinct()
                     mUiHandler.post { categoryData.value = categories }
                 }
             }
         }
     }

     fun filterSeries(type: Int, catFilterStr: String, sortByStr: String) {
         mBgHandler?.post {
             when (type) {
                 TrackObj.Type.AUDIO -> initSeries(plist?.audioSeries, type, catFilterStr, sortByStr)
                 TrackObj.Type.VIDEO -> initSeries(plist?.videoSeries, type, catFilterStr, sortByStr)
                 TrackObj.Type.PDF -> initSeries(plist?.pdfSeries, type, catFilterStr, sortByStr)
             }
         }
     }

     private fun initSeries(l: ArrayList<SeriesObj>?, type: Int, catType: String, featType: String) {
         mBgHandler?.post {
             val series = arrayListOf<SeriesObj>()
             if (l != null) {
                 val filteredSeries = l.filter {
                     when (catType) {
                         SeriesObj.Category.FEATURED -> it.featured == 1
                         SeriesObj.Category.ALL -> true
                         else -> it.category == catType
                     }
                 }
                 series.addAll(when (featType) {
                     SeriesObj.Category.NAME -> filteredSeries.sortedWith(compareBy { it.title })
                     SeriesObj.Category.SIZE -> filteredSeries.sortedWith(compareBy { it.fileSize })
                     SeriesObj.Category.DURATION,
                     SeriesObj.Category.PAGES -> filteredSeries.sortedWith(compareBy { it.duration })
                     else -> filteredSeries
                 })

                 series.forEach { seriesObj ->
                     val products = productsList.filter { product ->
                         product.productId == seriesObj.productId
                     }
                     if (products.isNotEmpty()) {
                         seriesObj.price = products.first().price
                         seriesObj.isPurchased = ownedProdIds.contains(seriesObj.productId)
                     }
                     seriesObj.type = type
                     seriesObj.tracks?.forEach {
                         it.type = type
                     }
                     seriesObj.thumbUrl = UriManager.seriesThumbUrl(config, seriesObj)
                 }

                 mUiHandler.post { seriesData.value = series }
             } else {
                 mUiHandler.post { seriesData.value = null }
             }
         }
     }

    */

}