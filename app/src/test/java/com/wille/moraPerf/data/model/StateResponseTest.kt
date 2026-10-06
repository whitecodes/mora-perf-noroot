package com.wille.moraPerf.data.model

import com.wille.moraPerf.data.api.moraJson
import kotlinx.serialization.decodeFromString
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Locks the model against the payload documented in `doc/api.md`. If the daemon
 * renames a field, this test is the first thing that should fail.
 */
class StateResponseTest {

    private val documentedPayload = """
        {
          "temps": { "cpu": 42.5, "gpu": 40.1, "soc": 43.0, "batt": 31.2 },
          "zone": { "name": "B52", "reduce_percent": 20 },
          "screen_on": true,
          "charging": { "hw": false, "enabled": true, "effective": false },
          "battery": {
            "percent": 76,
            "saver": {
              "enabled": true,
              "active": false,
              "override": false,
              "disabled_cores": [7],
              "reapply_in_sec": null
            },
            "screen_off_saver": { "after_minutes": 30, "active": false, "disabled_cores": [] },
            "split_charge": {
              "active": false, "package": null, "node": null,
              "stop_battery_percent": null, "last_error": null
            }
          },
          "game_mode": false,
          "triggers": { "active": true, "package": "com.example.game", "left": true, "right": false },
          "idle_mode": false,
          "daemon_notifications": true,
          "active_profile": "Normal",
          "led_profile": "Normal",
          "leds": {
            "base_external_desired": null,
            "fan_desired": { "mode": "steady", "color": "mixed_7" }
          },
          "mem": { "VmRSS_kb": 4096 },
          "config_rev": 7,
          "last_config_error": null,
          "games": { "count": 3, "driver_count": 2, "rev": 4, "last_error": null }
        }
    """.trimIndent()

    @Test
    fun statePayload_parsesDocumentedFields() {
        val state = moraJson.decodeFromString<StateResponse>(documentedPayload)

        assertEquals(42.5, state.temps.cpu!!, 0.001)
        assertEquals(31.2, state.temps.batt!!, 0.001)
        assertEquals("B52", state.zone.name)
        assertEquals(20, state.zone.reducePercent)
        assertTrue(state.screenOn)
        assertFalse(state.charging.effective)
        assertTrue(state.charging.enabled)
        assertEquals(76, state.battery.percent)
        assertTrue(state.battery.saver.enabled)
        assertEquals(listOf(7), state.battery.saver.disabledCores)
        assertTrue(state.triggers.active)
        assertEquals("com.example.game", state.triggers.packageName)
        assertTrue(state.daemonNotifications)
        assertEquals("Normal", state.activeProfile)
        assertEquals("Normal", state.ledProfile)
        assertEquals(4096L, state.mem.vmRssKb)
        assertEquals(3, state.games.count)
        assertEquals(2, state.games.driverCount)
        assertNull(state.lastConfigError)
    }

    @Test
    fun statePayload_ignoresUnmodelledFields() {
        // `leds`, `config_rev`, `screen_off_saver` and `split_charge` are not
        // modelled yet; parsing must stay forward compatible.
        val state = moraJson.decodeFromString<StateResponse>(documentedPayload)

        assertEquals(76, state.battery.percent)
    }

    @Test
    fun statePayload_defaultsWhenFieldsAreMissing() {
        val state = moraJson.decodeFromString<StateResponse>("{}")

        assertNull(state.temps.cpu)
        assertNull(state.battery.percent)
        assertFalse(state.screenOn)
        assertEquals(0, state.games.count)
        assertTrue(state.battery.saver.disabledCores.isEmpty())
    }
}
