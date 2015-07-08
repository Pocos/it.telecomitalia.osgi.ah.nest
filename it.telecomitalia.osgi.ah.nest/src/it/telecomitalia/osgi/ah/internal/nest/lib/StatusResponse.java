package it.telecomitalia.osgi.ah.internal.nest.lib;

public class StatusResponse {
	public Device device;
	public MetaData metadata;
	public Structure structure;
	public Track track;
	public Shared shared;
	public StatusResponse () {}
	
	/*
	 * naming conventions here are a little confusing. i decided to maintain the
	 * original JSON name which tends to be singular (except metadata ?!?), but
	 * in fact these are wrappers for HashMaps of objects
	 */
	public MetaData getMetaData() {
		return metadata;
	}
	public Device getDevices() {
		return device;
	}
	public Structure getStructures() {
		return structure;
	}
	
	public Track getTracks(){
		return track;
	}
}

/*
{
	"metadata": {
		"DEVICESERIALNUMBER": {
			"$version": -470698778,
			"$timestamp": 1342052055000,
			"last_connection": 1342052055062,
			"last_ip": "EXTERNALIPADDRESS"
		}
	},
	"track": {
		"DEVICESERIALNUMBER": {
			"$version": 46423532,
			"$timestamp": 1342052055095,
			"online": true,
			"last_connection": 1342052055095,
			"last_ip": "EXTERNALIPADDRESS"
		}
	},
	"user_settings": {
		"0000": {
			"$version": 1864358976,
			"$timestamp": 1340213094527,
			"email_verified": true,
			"tos_accepted_version": 1319500800000,
			"receive_marketing_emails": true,
			"receive_nest_emails": true,
			"receive_support_emails": true,
			"max_structures": 2,
			"max_thermostats": 10,
			"max_thermostats_per_structure": 10,
			"tos_minimum_version": 1319500800000,
			"tos_current_version": 1319500800000
		}
	},
	"structure": {
		"STRUCTUREIDENTIFIER": {
			"$version": -1395287873,
			"$timestamp": 1341869932000,
			"away_timestamp": 1341869828,
			"away": false,
			"location": "CITYSTATE",
			"name": "LOCATIONNAME",
			"postal_code": "POSTALCODE",
			"creation_time": 1322945617554,
			"street_address": "STREETADDRESS",
			"devices": [
				"device.DEVICESERIALNUMBER"
			],
			"country_code": "ISOCOUNTRYCODE",
			"user": "user.0000",
			"away_setter": 0
		}
	},
	"link": {
		"DEVICESERIALNUMBER": {
			"$version": -1253763031,
			"$timestamp": 1322945631000,
			"structure": "structure.STRUCTUREIDENTIFIER"
		}
	},
	"device": {
		"DEVICESERIALNUMBER": {
			"$version": 1201892049,
			"$timestamp": 1342051221000,
			"heat_pump_comp_threshold": -31.5,
			"learning_days_completed_cool": 0,
			"backplate_bsl_version": "1.1",
			"switch_system_off": true,
			"local_ip": "LOCALIPADDRESS",
			"away_temperature_high": 26.66663,
			"current_schedule_mode": "HEAT",
			"leaf_threshold_cool": 0,
			"hvac_wires": "Heat,Fan,Rh",
			"fan_cooling_state": false,
			"leaf": true,
			"type": "TBD",
			"switch_preconditioning_control": false,
			"click_sound": "on",
			"away_temperature_low_enabled": true,
			"has_x2_heat": false,
			"fan_mode": "auto",
			"heat_pump_comp_threshold_enabled": false,
			"rssi": 62,
			"leaf_away_high": 0,
			"fan_cooling_readiness": "not ready",
			"capability_level": 2.1,
			"schedule_learning_reset": false,
			"temperature_scale": "F",
			"battery_level": 3.909,
			"ob_orientation": "O",
			"hvac_pins": "W1,Rh,G",
			"away_temperature_high_enabled": true,
			"learning_days_completed_heat": 219,
			"range_enable": false,
			"auto_away_enable": true,
			"upper_safety_temp_enabled": false,
			"lower_safety_temp_enabled": true,
			"has_fan": true,
			"current_humidity": 53,
			"postal_code": "POSTALCODE",
			"backplate_mono_version": "3.7.3",
			"range_mode": false,
			"nlclient_state": "",
			"has_heat_pump": false,
			"current_version": "2.1",
			"learning_state": "slow",
			"auto_away_reset": false,
			"away_temperature_low": 12.77776,
			"temperature_lock": false,
			"upper_safety_temp": 35,
			"time_to_target_training": "training",
			"target_time_confidence": 0,
			"backplate_serial_number": "BACKPLATESERIALNUMBER",
			"forced_air": true,
			"backplate_mono_info": "TFE (BP_DVT) 3.7.3 (ehs@ubuntu) 2012-03-07 10:43:21",
			"leaf_threshold_heat": 17.086014,
			"learning_time": 1957,
			"creation_time": 1322943061635,
			"learning_days_completed_range": 0,
			"leaf_schedule_delta": 1.109985,
			"learning_mode": true,
			"leaf_learning": "ready",
			"has_aux_heat": false,
			"backplate_bsl_info": "BSL",
			"user_brightness": "auto",
			"leaf_away_low": 13.587738,
			"serial_number": "DEVICESERIALNUMBER",
			"mac_address": "MACADDRESS",
			"lower_safety_temp": 7,
			"time_to_target": 0,
			"equipment_type": "gas",
			"backplate_model": "Backplate-1.9",
			"model_version": "Diamond-1.10",
			"heat_pump_aux_threshold_enabled": true,
			"heat_pump_aux_threshold": 10
		}
	},
	"schedule": {
		"DEVICESERIALNUMBER": {
			"$version": 108535350,
			"$timestamp": 1341988370000,
			"days": {
				"3": {
					"3": {
						"time": 64800,
						"entry_type": "setpoint",
						"temp": 18.292,
						"type": "HEAT"
					},
					"2": {
						"time": 29700,
						"entry_type": "setpoint",
						"temp": 12.969,
						"type": "HEAT"
					},
					"1": {
						"time": 25200,
						"entry_type": "setpoint",
						"temp": 18.363,
						"type": "HEAT"
					},
					"0": {
						"touched_by": 1,
						"time": 0,
						"touched_tzo": -21600,
						"entry_type": "continuation",
						"temp": 12.841,
						"type": "HEAT",
						"touched_at": 1341988357
					},
					"4": {
						"time": 78300,
						"entry_type": "setpoint",
						"temp": 12.841,
						"type": "HEAT"
					}
				},
				"2": {
					"3": {
						"time": 64800,
						"entry_type": "setpoint",
						"temp": 18.196,
						"type": "HEAT"
					},
					"2": {
						"time": 29700,
						"entry_type": "setpoint",
						"temp": 12.969,
						"type": "HEAT"
					},
					"1": {
						"time": 25200,
						"entry_type": "setpoint",
						"temp": 18.363,
						"type": "HEAT"
					},
					"0": {
						"touched_by": 1,
						"time": 0,
						"touched_tzo": -21600,
						"entry_type": "continuation",
						"temp": 12.841,
						"type": "HEAT",
						"touched_at": 1341988357
					},
					"4": {
						"time": 78300,
						"entry_type": "setpoint",
						"temp": 12.841,
						"type": "HEAT"
					}
				},
				"1": {
					"3": {
						"time": 64800,
						"entry_type": "setpoint",
						"temp": 18.196,
						"type": "HEAT"
					},
					"2": {
						"time": 29700,
						"entry_type": "setpoint",
						"temp": 12.969,
						"type": "HEAT"
					},
					"1": {
						"time": 25200,
						"entry_type": "setpoint",
						"temp": 18.363,
						"type": "HEAT"
					},
					"0": {
						"touched_by": 1,
						"time": 0,
						"touched_tzo": -21600,
						"entry_type": "continuation",
						"temp": 12.841,
						"type": "HEAT",
						"touched_at": 1341988357
					},
					"4": {
						"time": 78300,
						"entry_type": "setpoint",
						"temp": 12.841,
						"type": "HEAT"
					}
				},
				"0": {
					"3": {
						"time": 64800,
						"entry_type": "setpoint",
						"temp": 18.196,
						"type": "HEAT"
					},
					"2": {
						"time": 29700,
						"entry_type": "setpoint",
						"temp": 12.969,
						"type": "HEAT"
					},
					"1": {
						"time": 25200,
						"entry_type": "setpoint",
						"temp": 18.363,
						"type": "HEAT"
					},
					"0": {
						"touched_by": 1,
						"time": 0,
						"touched_tzo": -21600,
						"entry_type": "continuation",
						"temp": 12.822,
						"type": "HEAT",
						"touched_at": 1341988357
					},
					"4": {
						"time": 78300,
						"entry_type": "setpoint",
						"temp": 12.841,
						"type": "HEAT"
					}
				},
				"6": {
					"2": {
						"time": 81900,
						"entry_type": "setpoint",
						"temp": 12.822,
						"type": "HEAT"
					},
					"1": {
						"time": 27000,
						"entry_type": "setpoint",
						"temp": 18.44,
						"type": "HEAT"
					},
					"0": {
						"touched_by": 1,
						"time": 0,
						"touched_tzo": -21600,
						"entry_type": "continuation",
						"temp": 12.822,
						"type": "HEAT",
						"touched_at": 1341988357
					}
				},
				"5": {
					"2": {
						"time": 81900,
						"entry_type": "setpoint",
						"temp": 12.822,
						"type": "HEAT"
					},
					"1": {
						"time": 27000,
						"entry_type": "setpoint",
						"temp": 18.44,
						"type": "HEAT"
					},
					"0": {
						"touched_by": 1,
						"time": 0,
						"touched_tzo": -21600,
						"entry_type": "continuation",
						"temp": 12.841,
						"type": "HEAT",
						"touched_at": 1341988357
					}
				},
				"4": {
					"3": {
						"time": 64800,
						"entry_type": "setpoint",
						"temp": 18.196,
						"type": "HEAT"
					},
					"2": {
						"time": 29700,
						"entry_type": "setpoint",
						"temp": 12.969,
						"type": "HEAT"
					},
					"1": {
						"time": 25200,
						"entry_type": "setpoint",
						"temp": 18.363,
						"type": "HEAT"
					},
					"0": {
						"touched_by": 1,
						"time": 0,
						"touched_tzo": -21600,
						"entry_type": "continuation",
						"temp": 12.841,
						"type": "HEAT",
						"touched_at": 1341988357
					},
					"4": {
						"time": 78300,
						"entry_type": "setpoint",
						"temp": 12.841,
						"type": "HEAT"
					}
				}
			},
			"schedule_mode": "HEAT",
			"name": "Nest Current Schedule",
			"ver": 2
		}
	},
	"shared": {
		"DEVICESERIALNUMBER": {
			"$version": -1896619651,
			"$timestamp": 1342051221000,
			"auto_away": 0,
			"auto_away_learning": "ready",
			"compressor_lockout_enabled": false,
			"target_temperature_type": "off",
			"hvac_heater_state": false,
			"can_heat": true,
			"compressor_lockout_timeout": 0,
			"target_temperature_high": 26.66663,
			"hvac_aux_heater_state": false,
			"hvac_heat_x2_state": false,
			"target_temperature_low": 16.66663,
			"target_temperature": 18.196,
			"hvac_ac_state": false,
			"hvac_fan_state": false,
			"target_change_pending": false,
			"name": "Nest",
			"current_temperature": 22.06,
			"can_cool": false
		}
	},
	"user_alert_dialog": {
		"0000": {
			"$version": -141125587,
			"$timestamp": 1323898927000,
			"dialog_id": "confirm-pairing",
			"dialog_data": ""
		}
	},
	"user": {
		"0000": {
			"$version": 1138848506,
			"$timestamp": 1322945722000,
			"name": "EMAILADDRESS",
			"structures": [
				"structure.STRUCTUREIDENTIFIER"
			]
		}
	}
}
 */

