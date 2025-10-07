package com.joffer.organizeplus.features.dashboard.data.remote

class FakeDashboardRemoteDataSource : DashboardRemoteDataSource {
    
    var remoteResponse: DashboardRemoteResponse? = null
    var shouldThrowError = false
    var errorMessage = "Remote data source error"

    override suspend fun getDashboardData(): Result<DashboardRemoteResponse> {
        return if (shouldThrowError) {
            Result.failure(Exception(errorMessage))
        } else {
            remoteResponse?.let { Result.success(it) }
                ?: Result.failure(Exception("No remote data configured"))
        }
    }

    fun clear() {
        remoteResponse = null
        shouldThrowError = false
    }
}

