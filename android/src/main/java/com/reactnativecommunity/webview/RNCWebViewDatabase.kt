package com.reactnativecommunity.webview

import android.os.Build;
import android.webkit.WebViewDatabase

import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod

class RNCWebViewDatabase(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

  override fun getName(): String =
    "RNCWebViewDatabase"

  @ReactMethod
  fun clearHttpAuthUsernamePassword(promise: Promise) = try {
    WebViewDatabase.getInstance(this.reactApplicationContext)
      .clearHttpAuthUsernamePassword()
    promise.resolve(true)
  } catch (e: Throwable) {
    promise.reject(e)
  }

  @ReactMethod
  fun getHttpAuthUsernamePassword(host: String, realm: String, promise: Promise) =
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
      promise.reject(UNSUPPORTED_CODE, UNSUPPORTED_MESSAGE)
    } else {
      try {
        WebViewDatabase.getInstance(this.reactApplicationContext)
          .getHttpAuthUsernamePassword(host, realm).also { result: Array<String>? ->
            promise.resolve(result?.let(Arguments::fromArray))
          }
      } catch (e: Throwable) {
        promise.reject(e)
      }

    }

  @ReactMethod
  fun setHttpAuthUsernamePassword(host: String,
                                  realm: String,
                                  userName: String,
                                  password: String,
                                  promise: Promise) =
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
      promise.reject(UNSUPPORTED_CODE, UNSUPPORTED_MESSAGE)
    } else {
      try {
        WebViewDatabase.getInstance(this.reactApplicationContext)
          .setHttpAuthUsernamePassword(host, realm, userName, password)
        promise.resolve(true)
      } catch (e: Throwable) {
        promise.reject(e)
      }
    }

  @ReactMethod
  fun hasHttpAuthUsernamePassword(promise: Promise) = try {
    WebViewDatabase.getInstance(this.reactApplicationContext)
      .hasHttpAuthUsernamePassword().also(promise::resolve)
  } catch (e: Throwable) {
    promise.reject(e)
  }

  @ReactMethod
  fun clearFormData(promise: Promise) = try {
    WebViewDatabase.getInstance(this.reactApplicationContext)
      .clearFormData()
    promise.resolve(true)
  } catch (e: Throwable) {
    promise.reject(e)
  }

  @ReactMethod
  fun hasFormData(promise: Promise) = try {
    WebViewDatabase.getInstance(this.reactApplicationContext)
      .hasFormData().also(promise::resolve)
  } catch (e: Throwable) {
    promise.reject(e)
  }

  companion object {
    private const val UNSUPPORTED_CODE = "UNSUPPORTED"
    private const val UNSUPPORTED_MESSAGE = "Call require android API 26"
  }
}
