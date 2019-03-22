package com.printlab.android.manager

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Build
import android.support.v4.content.FileProvider
import android.widget.Toast
import java.io.File

object NavigationManager {

    /*fun goTracksActivity(activity: Activity?, seriesObj: SeriesObj) {
        if (activity == null) return
        activity.startActivityForResult(Intent(activity, TracksDownloadActivity::class.java).apply {
            putExtra(AppConstants.Params.SERIES_OBJ, seriesObj)
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        }, AppConstants.ReqCode.TRACK_DOWNLOAD_CODE)
    }

    fun goSeriesActivity(activity: Activity?, plist: PlistObj?) {
        if (activity == null) return
        activity.startActivity(Intent(activity, SeriesPurchaseActivity::class.java).apply {
            putExtra(AppConstants.Params.PLIST_OBJ, plist)
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        })
    }

    fun goInfoActivity(activity: Activity?) {
        if (activity == null) return
        activity.startActivity(Intent(activity, InfoActivity::class.java))
    }

    fun goPdfActivity(activity: Activity?, title: String?, filePath: String) {
        if (activity == null) return
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            goPdfIntent(activity, filePath)
            return
        }
        val intent = Intent(activity, TrackPdfActivity::class.java)
        intent.putExtra(AppConstants.Params.FILE_PATH, filePath)
        intent.putExtra(AppConstants.Params.TITLE, title)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        activity.startActivity(intent)
    }

    fun goAudioActivity(activity: Activity?, pos: Int, title: String?, tracks: ArrayList<TrackObj>) {
        if (activity == null) return
        val intent = Intent(activity, TracksAudioActivity::class.java)
        intent.putExtra(AppConstants.Params.TRACK_OBJS, tracks)
        intent.putExtra(AppConstants.Params.TITLE, title)
        intent.putExtra(AppConstants.Params.POSITION, pos)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        activity.startActivity(intent)
    }

    fun goVideoActivity(activity: Activity?, pos: Int, title: String?, tracks: ArrayList<TrackObj>) {
        if (activity == null) return
        val intent = Intent(activity, TracksVideoActivity::class.java)
        intent.putExtra(AppConstants.Params.TRACK_OBJS, tracks)
        intent.putExtra(AppConstants.Params.TITLE, title)
        intent.putExtra(AppConstants.Params.POSITION, pos)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        activity.startActivity(intent)
    }

    private fun goPdfIntent(activity: Activity?, path: String) {
        if (activity == null) return
        val file = File(path)
        val contentUri = FileProvider.getUriForFile(activity,
                activity.getString(R.string.uri_file_provider), file)
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(contentUri, "application/pdf")
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        val chooser = Intent.createChooser(intent, activity.getString(R.string.share_pdf))
        try {
            activity.startActivity(chooser)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(activity, activity.getString(R.string.err_no_pdf_viewer), Toast.LENGTH_SHORT).show()
        }
    }*/

}