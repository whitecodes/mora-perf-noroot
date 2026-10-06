package com.wille.moraPerf.data.repo

import com.wille.moraPerf.data.api.MoraApiFactory
import com.wille.moraPerf.data.model.StateResponse

class MoraRepository {

    /**
     * Reads `/api/state` with the given token. A wrong token and an unreachable
     * daemon both surface as an empty 404, so callers must treat any failure as
     * "could not authenticate or reach the daemon".
     */
    suspend fun fetchState(token: String): StateResponse =
        MoraApiFactory.create(token).getState()
}
