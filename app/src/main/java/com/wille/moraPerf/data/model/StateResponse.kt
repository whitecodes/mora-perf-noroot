package com.wille.moraPerf.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Response of `GET /api/state`.
 *
 * Field names follow the daemon's JSON (see `docs/api.md` in mora-perf-daemon).
 * Every field has a default so a partial or evolving payload still parses.
 */
@Serializable
data class StateResponse(
    val temps: Temps = Temps(),
    val zone: Zone = Zone(),
    @SerialName("screen_on") val screenOn: Boolean = false,
    val charging: ChargingState = ChargingState(),
    val battery: BatteryState = BatteryState(),
    @SerialName("game_mode") val gameMode: Boolean = false,
    val triggers: TriggerRuntime = TriggerRuntime(),
    @SerialName("idle_mode") val idleMode: Boolean = false,
    @SerialName("daemon_notifications") val daemonNotifications: Boolean = false,
    @SerialName("active_profile") val activeProfile: String? = null,
    @SerialName("led_profile") val ledProfile: String? = null,
    val mem: MemoryState = MemoryState(),
    @SerialName("last_config_error") val lastConfigError: String? = null,
    val games: GamesRuntime = GamesRuntime(),
)

@Serializable
data class Temps(
    val cpu: Double? = null,
    val gpu: Double? = null,
    val soc: Double? = null,
    val batt: Double? = null,
)

@Serializable
data class Zone(
    val name: String? = null,
    @SerialName("reduce_percent") val reducePercent: Int? = null,
)

@Serializable
data class ChargingState(
    val hw: Boolean = false,
    val enabled: Boolean = false,
    val effective: Boolean = false,
)

@Serializable
data class BatteryState(
    val percent: Int? = null,
    val saver: BatterySaverRuntime = BatterySaverRuntime(),
)

@Serializable
data class BatterySaverRuntime(
    val enabled: Boolean = false,
    val active: Boolean = false,
    @SerialName("override") val overrideValue: Boolean = false,
    @SerialName("disabled_cores") val disabledCores: List<Int> = emptyList(),
    @SerialName("reapply_in_sec") val reapplyInSec: Long? = null,
)

@Serializable
data class TriggerRuntime(
    val active: Boolean = false,
    @SerialName("package") val packageName: String? = null,
    val left: Boolean = false,
    val right: Boolean = false,
)

@Serializable
data class MemoryState(
    @SerialName("VmRSS_kb") val vmRssKb: Long? = null,
)

@Serializable
data class GamesRuntime(
    val count: Int = 0,
    @SerialName("driver_count") val driverCount: Int = 0,
    @SerialName("last_error") val lastError: String? = null,
)
