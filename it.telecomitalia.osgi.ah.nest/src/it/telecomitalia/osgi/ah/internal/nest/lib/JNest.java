package it.telecomitalia.osgi.ah.internal.nest.lib;

import it.telecomitalia.osgi.ah.internal.nest.DiscoveryThread;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JNest {

	private Credentials credentials = null;
	private String userAgent = "";
	private String uri = "https://home.nest.com/user/login";
	private LoginResponse loginResponse;
	private StatusResponse statusResponse = null;
	public boolean isLoggedIn = false;
	public static Gson gson;
	private static Logger LOG = LoggerFactory.getLogger(DiscoveryThread.class);

	public JNest() {
		GsonBuilder gsonb = new GsonBuilder();
		gsonb.registerTypeHierarchyAdapter(Enum.class, new EnumSerializer());
		gsonb.registerTypeAdapter(Device.class, new DeviceDeserializer());
		gsonb.registerTypeAdapter(MetaData.class, new MetaDataDeserializer());
		gsonb.registerTypeAdapter(Structure.class, new StructureDeserializer());
		gsonb.registerTypeAdapter(Track.class, new TrackDeserializer());
		gsonb.registerTypeAdapter(Shared.class, new SharedDeserializer());
		gsonb.registerTypeAdapter(Topaz.class, new TopazDeserializer());
		gsonb.registerTypeAdapter(Widget_track.class, new Widget_trackDeserializer());
		gson = gsonb.create();
	}

	/**
	 * Try to contact NEST cloud through SSL connections
	 * 
	 * @return <b>-1</b> in case of errors or <b>0</b> if the login is
	 *         successful
	 */
	public int login() {

		URL url;

		if (credentials == null)
			return -1;

		Properties properties = credentials.toProperties();
		String query = Util.makeQueryString(properties);

		try {

			url = new URL(uri);
			HttpsURLConnection urlc = (HttpsURLConnection) url.openConnection();
			urlc.setRequestMethod("POST");
			urlc.setDoInput(true);
			urlc.setDoOutput(true);
			urlc.setRequestProperty("user-agent", userAgent);
			urlc.setRequestProperty("Content-length", String.valueOf(query.length()));
			urlc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			DataOutputStream output = new DataOutputStream(urlc.getOutputStream());
			output.writeBytes(query);

			switch (urlc.getResponseCode()) {
			case HttpsURLConnection.HTTP_OK:
				return handleLoginSuccess(urlc);
			default:
				return handleLoginFailure(urlc);
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
			return -1;
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * If the login was successful this method obtains the status of all the
	 * devices registered on the NEST cloud as a JSON. Then it populates the
	 * StatusResponse variable parsing this JSON. Call
	 * <b>getStatusResponse()</b> to obtain the related Object
	 */
	public void getStatus() {

		if (!isLoggedIn)
			return;

		URL url;
		HttpsURLConnection urlc;

		try {
			url = new URL(loginResponse.urls.transport_url + "/v2/mobile/" + loginResponse.user);
			urlc = (HttpsURLConnection) url.openConnection();
			urlc.setRequestMethod("GET");
			urlc.setDoInput(true);
			urlc.setDoOutput(false);
			urlc.setRequestProperty("user-agent", userAgent);
			urlc.setRequestProperty("Authorization", "Basic " + loginResponse.access_token);
			urlc.setRequestProperty("X-nl-user-id", loginResponse.userid);
			urlc.setRequestProperty("X-nl-protocol-version", "1");

			switch (urlc.getResponseCode()) {
			case HttpsURLConnection.HTTP_OK:
				handleStatusSuccess(urlc);
				break;
			default:
				handleStatusFailure(urlc);
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/*
	 * NOT USED. USED IN PAST. SCHEDULED FOR DELETION
	 */
	public void setTemperature(double temp, String deviceId) {

		if (!isLoggedIn)
			return;

		URL url;
		HttpsURLConnection urlc;
		String query;

		try {
			TempChange req = new TempChange();
			req.target_change_pending = true;
			req.target_temperature = temp;
			query = gson.toJson(req);
			url = new URL(loginResponse.urls.transport_url + "/v2/put/shared." + deviceId);
			urlc = (HttpsURLConnection) url.openConnection();
			urlc.setRequestMethod("POST");
			urlc.setDoInput(true);
			urlc.setDoOutput(true);
			urlc.setRequestProperty("user-agent", userAgent);
			urlc.setRequestProperty("Authorization", "Basic " + loginResponse.access_token);
			urlc.setRequestProperty("X-nl-protocol-version", "1");
			urlc.setRequestProperty("Content-length", String.valueOf(query.length()));
			urlc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			DataOutputStream output = new DataOutputStream(urlc.getOutputStream());
			output.writeBytes(query);

			switch (urlc.getResponseCode()) {
			case HttpsURLConnection.HTTP_OK:
				handleSuccess(urlc);
				break;
			default:
				handleFailure(urlc);
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/*
	 * NOT USED. USED IN PAST. SCHEDULED FOR DELETION
	 */
	public void setTemperature(double temp) {
		try {
			setTemperature(temp, getStatusResponse().getMetaData().getDeviceIds()[0]);
		} catch (Exception e) {
			// do something..
		}
	}

	/*
	 * NOT USED. USED IN PAST. SCHEDULED FOR DELETION
	 */
	public void setFanMode(FanModeEnum fanMode, String deviceId) {

		if (!isLoggedIn)
			return;

		URL url;
		HttpsURLConnection urlc;
		String query;

		try {

			FanMode req = new FanMode();
			req.fan_mode = fanMode;
			query = gson.toJson(req);
			url = new URL(loginResponse.urls.transport_url + "/v2/put/device." + deviceId);
			urlc = (HttpsURLConnection) url.openConnection();
			urlc.setRequestMethod("POST");
			urlc.setDoInput(true);
			urlc.setDoOutput(true);
			urlc.setRequestProperty("user-agent", userAgent);
			urlc.setRequestProperty("Authorization", "Basic " + loginResponse.access_token);
			urlc.setRequestProperty("X-nl-protocol-version", "1");
			urlc.setRequestProperty("Content-length", String.valueOf(query.length()));
			urlc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			DataOutputStream output = new DataOutputStream(urlc.getOutputStream());
			output.writeBytes(query);

			switch (urlc.getResponseCode()) {
			case HttpsURLConnection.HTTP_OK:
				handleSuccess(urlc);
				break;
			default:
				handleFailure(urlc);
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/*
	 * NOT USED. USED IN PAST. SCHEDULED FOR DELETION
	 */
	public void setFanMode(FanModeEnum fanMode) {
		try {
			setFanMode(fanMode, getStatusResponse().getMetaData().getDeviceIds()[0]);
		} catch (Exception e) {
			// do something
		}
	}

	/*
	 * NOT USED. USED IN PAST. SCHEDULED FOR DELETION
	 */
	public void setTemperatureMode(TemperatureModeEnum temperatureMode, String deviceId) {

		if (!isLoggedIn)
			return;

		URL url;
		HttpsURLConnection urlc;
		String query;

		try {

			TemperatureMode req = new TemperatureMode();
			req.target_temperature_type = temperatureMode;
			query = gson.toJson(req);
			url = new URL(loginResponse.urls.transport_url + "/v2/put/shared." + deviceId);
			urlc = (HttpsURLConnection) url.openConnection();
			urlc.setRequestMethod("POST");
			urlc.setDoInput(true);
			urlc.setDoOutput(true);
			urlc.setRequestProperty("user-agent", userAgent);
			urlc.setRequestProperty("Authorization", "Basic " + loginResponse.access_token);
			urlc.setRequestProperty("X-nl-protocol-version", "1");
			urlc.setRequestProperty("Content-length", String.valueOf(query.length()));
			urlc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			DataOutputStream output = new DataOutputStream(urlc.getOutputStream());
			output.writeBytes(query);

			switch (urlc.getResponseCode()) {
			case HttpsURLConnection.HTTP_OK:
				handleSuccess(urlc);
				break;
			default:
				handleFailure(urlc);
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/*
	 * NOT USED. USED IN PAST. SCHEDULED FOR DELETION
	 */
	public void setTemperatureMode(TemperatureModeEnum temperatureMode) {
		try {
			setTemperatureMode(temperatureMode, getStatusResponse().getMetaData().getDeviceIds()[0]);
		} catch (Exception e) {
			// do something
		}
	}

	public int handleLoginSuccess(HttpsURLConnection urlc) throws IOException {

		StringBuffer buffer = Util.getStringBufferFromResponse(urlc);
		loginResponse = gson.fromJson(buffer.toString(), LoginResponse.class);
		isLoggedIn = true;
		return urlc.getResponseCode();

	}

	public void handleStatusSuccess(HttpsURLConnection urlc) throws IOException {
		StringBuffer buffer = Util.getStringBufferFromResponse(urlc);
		statusResponse = gson.fromJson(buffer.toString(), StatusResponse.class);
	}

	private int handleLoginFailure(HttpsURLConnection urlc) throws IOException {
		LOG.error("LOGIN FAILURE:" + urlc.getResponseCode());
		return urlc.getResponseCode();
	}

	private void handleStatusFailure(HttpsURLConnection urlc) throws IOException {
		LOG.debug(urlc.getResponseMessage());
	}

	private void handleSuccess(HttpsURLConnection urlc) {

	}

	private void handleFailure(HttpsURLConnection urlc) {
		try {
			LOG.error(urlc.getResponseCode() + " : " + urlc.getResponseMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method return the object variable associated to the JSON obtained by
	 * a previous call of <b>getStatus()</b> method. Call getStatusResponse()
	 * before getStatus() will produce a null return value
	 * 
	 * @return The Object obtained by the parsing operation
	 */
	public StatusResponse getStatusResponse() {
		return statusResponse;
	}

	public Credentials getCredentials() {
		return credentials;
	}

	/**
	 * Set the credentials of the main JNest Object
	 * 
	 * @param credentials
	 */
	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

	public void mockStatusResonse(String str) {
		statusResponse = gson.fromJson(str, StatusResponse.class);
	}

	/**
	 * Send the json to the NEST cloud.
	 * 
	 * @param dataInType
	 *            The classdata where the key is defined
	 * @param targetId
	 *            The target of the action, e.g. it could be a deviceId or a
	 *            structureId or others, it depends on the class where the key
	 *            is. Look at the Json format for further explanation
	 * @param json
	 *            The action to be performed, it contains the key
	 * @return
	 */
	public String setParameter(String dataInType, String targetId, Object json) {

		if (!isLoggedIn)
			return "Unable to set Parameter";

		URL url;
		HttpsURLConnection urlc;
		String query;

		try {
			query = ((JSONObject) json).toString();
			url = new URL(loginResponse.urls.transport_url + "/v2/put/" + dataInType + "." + targetId);
			urlc = (HttpsURLConnection) url.openConnection();
			urlc.setRequestMethod("POST");
			urlc.setDoInput(true);
			urlc.setDoOutput(true);
			urlc.setRequestProperty("user-agent", userAgent);
			urlc.setRequestProperty("Authorization", "Basic " + loginResponse.access_token);
			urlc.setRequestProperty("X-nl-protocol-version", "1");
			urlc.setRequestProperty("Content-length", String.valueOf(query.length()));
			urlc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			DataOutputStream output = new DataOutputStream(urlc.getOutputStream());
			output.writeBytes(query);

			switch (urlc.getResponseCode()) {
			case HttpsURLConnection.HTTP_OK:
				return "OK";
			default:
				return urlc.getResponseCode() + " : " + urlc.getResponseMessage();
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "Unable to set Parameter";
	}

	/**
	 * With this method we listen for changes on Nest Thermostat Device
	 * 
	 * @param dataInType
	 * @param targetId
	 * @param json
	 * @return
	 */
	public String longPollingNest(String poll_json) {

		if (!isLoggedIn)
			return "Unable to listen for Changes";

		URL url;
		HttpsURLConnection urlc;
		// String query="{ \"objects\":["
		// +
		// " { \"object_key\":\"where.6e953e40-23dc-11e5-898f-22000bb5a0dd\", \"object_timestamp\":1436186535973, \"object_revision\":29625 },"
		// +
		// " { \"object_key\":\"message.02AA01AC09140922\", \"object_timestamp\":1436190216315, \"object_revision\":-16703 }, "
		// +
		// "{ \"object_key\":\"shared.02AA01AC09140922\", \"object_timestamp\":1445939044288, \"object_revision\":-22640 },"
		// +
		// " { \"object_key\":\"device_alert_dialog.02AA01AC09140922\", \"object_timestamp\":1436190210522, \"object_revision\":-9488 },"
		// +
		// " { \"object_key\":\"user.2163221\", \"object_timestamp\":1436186535973, \"object_revision\":-17462 },"
		// +
		// " { \"object_key\":\"device.02AA01AC09140922\", \"object_timestamp\":1445938515285, \"object_revision\":23117 },"
		// +
		// " { \"object_key\":\"structure.6e953e40-23dc-11e5-898f-22000bb5a0dd\", \"object_timestamp\":1445929709611, \"object_revision\":-31926 },"
		// +
		// " { \"object_key\":\"user_settings.2163221\", \"object_timestamp\":1436187835319, \"object_revision\":-20290 },"
		// +
		// " { \"object_key\":\"metadata.02AA01AC09140922\", \"object_timestamp\":1356998400000, \"object_revision\":-1 },"
		// +
		// " { \"object_key\":\"track.02AA01AC09140922\", \"object_timestamp\":1445939044288, \"object_revision\":14660 },"
		// +
		// " { \"object_key\":\"structure_metadata.6e953e40-23dc-11e5-898f-22000bb5a0dd\", \"object_timestamp\":1436186536124, \"object_revision\":1239 } ],"
		// + " \"timeout\":1017, \"session\":\"2163221.85759.1445870206001\" }";

		try {
			url = new URL(loginResponse.urls.transport_url + "/v5/subscribe");
			urlc = (HttpsURLConnection) url.openConnection();
			urlc.setRequestMethod("POST");
			urlc.setDoInput(true);
			urlc.setDoOutput(true);
			urlc.setRequestProperty("user-agent", userAgent);
			urlc.setRequestProperty("Authorization", "Basic " + loginResponse.access_token);
			urlc.setRequestProperty("X-nl-protocol-version", "1");
			urlc.setRequestProperty("Content-length", String.valueOf(poll_json.length()));
			urlc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			DataOutputStream output = new DataOutputStream(urlc.getOutputStream());
			output.writeBytes(poll_json);

			switch (urlc.getResponseCode()) {
			case HttpsURLConnection.HTTP_OK:
				StringBuffer buffer = Util.getStringBufferFromResponse(urlc);
				return buffer.toString();
			default:
				LOG.error(urlc.getResponseCode() + " : " + urlc.getResponseMessage());
				return "";
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "Unable to set Parameter";
	}
}
