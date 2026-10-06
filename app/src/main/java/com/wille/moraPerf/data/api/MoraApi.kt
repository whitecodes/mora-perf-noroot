package com.wille.moraPerf.data.api

import com.wille.moraPerf.data.model.StateResponse
import retrofit2.http.GET

/**
 * The daemon's loopback API. Only the endpoints needed so far are declared;
 * the rest (`/api/save`, the `/api/games/...` endpoints and the toggles)
 * arrive with M2 and M3.
 */
interface MoraApi {
    @GET("api/state")
    suspend fun getState(): StateResponse
}
