package id.heycoding.sholehapp.data

import id.heycoding.sholehapp.data.local.SholehLocalDataSource
import id.heycoding.sholehapp.data.remote.SholehRemoteDataSource
import id.heycoding.sholehapp.utils.Source
import javax.inject.Inject

class SholehSourceFactory @Inject constructor(
    private val sholehRemoteDataSource: SholehRemoteDataSource,
    private val sholehLocalDataSource: SholehLocalDataSource
) {

    fun create(source: Source): SholehDataSource {
        return when (source) {
            Source.NETWORK -> sholehRemoteDataSource
            else -> sholehLocalDataSource
        }
    }
}