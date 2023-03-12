package com.example.task.util

import android.app.Activity
import android.content.ContentValues.TAG
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.example.task.R
import com.example.task.util.state.ApiState
import com.example.task.util.state.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

fun Activity.hideSoftKeyboard() {
    currentFocus?.let {
        val inputMethodManager =
            ContextCompat.getSystemService(this, InputMethodManager::class.java)!!
        inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

fun Fragment.showToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun NavController.isFragmentExist(destinationId: Int) =
    try {
        getBackStackEntry(destinationId)
        true
    } catch (e: Exception) {
        false
    }

fun <T> toResultFlow(call: suspend () -> Response<T>): Flow<ApiState<T>> = flow {
    emit(ApiState.Loading())
    try {
        val response = call()
        if (response.isSuccessful) emit(ApiState.Success(response.body()))
        else emit(ApiState.Error(UiText.DynamicString(response.message())))
    } catch (e: HttpException) {
        Log.d(TAG, e.stackTraceToString())
        emit(ApiState.Error(UiText.StringResource(R.string.something_went_wrong)))
    } catch (e: IOException) {
        Log.d(TAG, e.stackTraceToString())
        emit(ApiState.Error(UiText.StringResource(R.string.check_your_internet_connection)))
    } catch (e: Exception) {
        Log.d(TAG, e.stackTraceToString())
        emit(ApiState.Error(UiText.StringResource(R.string.something_went_wrong)))
    }
}