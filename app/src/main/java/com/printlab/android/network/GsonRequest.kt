package com.printlab.android.network

import com.android.volley.*
import com.android.volley.toolbox.HttpHeaderParser
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset

/**
 * Make a GET request and return a parsed object from JSON.
 *
 * @param url URL of the request to make
 * @param clazz Relevant class object, for Gson's reflection
 * @param headers Map of request headers
 *

 */
class GsonRequest<T>(
        tag: String,
        url: String,
        private val clazz: Class<T>,
        private val headers: MutableMap<String, String>?,
        private val params: MutableMap<String, String>?,
        private val callback: Callback<T>) : Request<T>(Method.POST, url, null) {
    private val gson = Gson()

    init {
        setTag(tag)
    }

    override fun getHeaders(): MutableMap<String, String> = headers ?: super.getHeaders()

    override fun getParams(): MutableMap<String, String> {
        return params ?: hashMapOf()
    }

    override fun parseNetworkResponse(response: NetworkResponse?): Response<T> {
        return try {
            val json = String(
                    response?.data ?: ByteArray(0),
                    Charset.forName(HttpHeaderParser.parseCharset(response?.headers)))
            Response.success(
                    gson.fromJson(json, clazz),
                    HttpHeaderParser.parseCacheHeaders(response))
        } catch (e: UnsupportedEncodingException) {
            Response.error(ParseError(e))
        } catch (e: JsonSyntaxException) {
            Response.error(ParseError(e))
        }
    }

    override fun deliverResponse(response: T) = callback.success(tag, response)
    override fun deliverError(error: VolleyError?) {
        callback.error(tag, error)
    }

    interface Callback<T> {
        fun success(tag: Any, t: T)
        fun error(tag: Any, e: VolleyError?)
    }
}