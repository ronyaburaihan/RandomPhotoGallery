package com.englesoft.randomphotogallery.viewmodels

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.englesoft.randomphotogallery.data.GalleryRepository
import com.englesoft.randomphotogallery.data.model.ImageItem
import com.englesoft.randomphotogallery.data.model.ImageListResponse
import com.englesoft.randomphotogallery.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: GalleryRepository,
    application: Application
) : AndroidViewModel(application) {

    /*For Room Database*/
    val imageListLiveData: LiveData<List<ImageListResponse>> = repository.local.loadImages().asLiveData()

    private fun insertImages(imageList: List<ImageListResponse>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertImages(imageList)
        }
    }

    /*For Retrofit*/
    var imageResponse: MutableLiveData<NetworkResult<List<ImageListResponse>>> = MutableLiveData()

    fun getImageList() = viewModelScope.launch {
        getImageListSafeCall()
    }

    @SuppressLint("NewApi")
    private suspend fun getImageListSafeCall() {
        imageResponse.value = NetworkResult.Loading()

        if (hasInternetConnection()) {
            try {
                Log.d("Gallery_App", "getImageListSafeCall: API Calling")
                val response = repository.remote.getImages()
                imageResponse.value = handleFoodRecipesResponse(response)
                Log.d("Gallery_App", "getImageListSafeCall: ${response.message()}")

                val imageList = imageResponse.value!!.data
                if (!imageList.isNullOrEmpty()) {
                    offlineCacheImages(imageList)
                }

            } catch (e: Exception) {
                Log.d("Gallery_App", "getImageListSafeCall: ${e.localizedMessage}")
                imageResponse.value = NetworkResult.Error(e.localizedMessage)
            }
        } else {
            imageResponse.value = NetworkResult.Error("No Internet Connection")
        }
    }

    private fun offlineCacheImages(imageList: List<ImageListResponse>) {
        insertImages(imageList)
    }

    private fun handleFoodRecipesResponse(response: Response<List<ImageListResponse>>): NetworkResult<List<ImageListResponse>>? {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Request timeout")
            }

            response.code() == 402 -> {
                return NetworkResult.Error("API Key Limited")
            }

            response.body()!!.isNullOrEmpty() -> {
                return NetworkResult.Error("Images not fond")
            }

            response.isSuccessful -> {
                return NetworkResult.Success(response.body()!!)
            }

            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}