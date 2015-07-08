package it.telecomitalia.osgi.ah.internal.nest.lib;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JNest {

	private Credentials credentials = null;
	private String userAgent = "";
	private String uri = "https://home.nest.com/user/login";
	private LoginResponse loginResponse;
	private StatusResponse statusResponse=null;
	public boolean isLoggedIn = false;
	public static Gson gson;
	
	public JNest () {
		GsonBuilder gsonb = new GsonBuilder();
		gsonb.registerTypeHierarchyAdapter(Enum.class, new EnumSerializer());
		gsonb.registerTypeAdapter(Device.class, new DeviceDeserializer());
		gsonb.registerTypeAdapter(MetaData.class, new MetaDataDeserializer());
		gsonb.registerTypeAdapter(Structure.class, new StructureDeserializer());
		gsonb.registerTypeAdapter(Track.class, new TrackDeserializer());
		gsonb.registerTypeAdapter(Shared.class, new SharedDeserializer());
		gson = gsonb.create();
	}
	
	/**
	 * Try to contact NEST cloud through SSL connections
	 * @return <b>-1</b> in case of errors or <b>0</b> if the login is successful
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
		    urlc.setRequestProperty("Content-length",String.valueOf (query.length())); 
		    urlc.setRequestProperty("Content-Type","application/x-www-form-urlencoded"); 
		    DataOutputStream output = new DataOutputStream( urlc.getOutputStream() );  
		    output.writeBytes(query);
		    
		    switch (urlc.getResponseCode()) {
			    case HttpsURLConnection.HTTP_OK :
			    	return handleLoginSuccess(urlc);
			default :
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
	 * If the login was successful this method obtains the status of all the devices registered on the NEST cloud as a JSON.
	 *  Then it populates the StatusResponse variable parsing this JSON. Call <b>getStatusResponse()</b> to obtain the related Object
	 */
	public void getStatus () {
		
		if (!isLoggedIn)
			return;
		
		URL url;
		HttpsURLConnection urlc;
		
		try {
			url = new URL(loginResponse.urls.transport_url+"/v2/mobile/"+loginResponse.user);
			urlc = (HttpsURLConnection) url.openConnection();
		    urlc.setRequestMethod("GET"); 
		    urlc.setDoInput(true); 
		    urlc.setDoOutput(false);
		    urlc.setRequestProperty("user-agent", userAgent);
		    urlc.setRequestProperty("Authorization", "Basic " + loginResponse.access_token);
		    urlc.setRequestProperty("X-nl-user-id", loginResponse.userid);
		    urlc.setRequestProperty("X-nl-protocol-version", "1");
		    
		    switch (urlc.getResponseCode()) {
			    case HttpsURLConnection.HTTP_OK :
			    	handleStatusSuccess(urlc);
			    	break;
			    default :
			    	handleStatusFailure(urlc);
		    }
		    
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void setTemperature (double temp, String deviceId) {
		
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
			url = new URL(loginResponse.urls.transport_url+"/v2/put/shared."+deviceId);
			urlc = (HttpsURLConnection) url.openConnection();
		    urlc.setRequestMethod("POST"); 
		    urlc.setDoInput(true); 
		    urlc.setDoOutput(true);
		    urlc.setRequestProperty("user-agent", userAgent);
		    urlc.setRequestProperty("Authorization", "Basic " + loginResponse.access_token);
		    urlc.setRequestProperty("X-nl-protocol-version", "1");
		    urlc.setRequestProperty("Content-length",String.valueOf (query.length())); 
		    urlc.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		    DataOutputStream output = new DataOutputStream( urlc.getOutputStream() ); 
		    output.writeBytes(query);
		    
		    switch (urlc.getResponseCode()) {
			    case HttpsURLConnection.HTTP_OK :
			    	handleSuccess(urlc);
			    	break;
			    default :
			    	handleFailure(urlc);
		    }
	
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void setTemperature (double temp) {
		try {
			setTemperature(temp, getStatusResponse().getMetaData().getDeviceIds()[0]);
		} catch (Exception e) {
			//do something..
		}
	}
	
	public void setFanMode (FanModeEnum fanMode, String deviceId) {
		
		if (!isLoggedIn)
			return;
	
		URL url;
		HttpsURLConnection urlc;
		String query;
		
		try {
			
			FanMode req = new FanMode();
			req.fan_mode = fanMode;
			query = gson.toJson(req);
			url = new URL(loginResponse.urls.transport_url+"/v2/put/device."+deviceId);
			urlc = (HttpsURLConnection) url.openConnection();
		    urlc.setRequestMethod("POST"); 
		    urlc.setDoInput(true); 
		    urlc.setDoOutput(true);
		    urlc.setRequestProperty("user-agent", userAgent);
		    urlc.setRequestProperty("Authorization", "Basic " + loginResponse.access_token);
		    urlc.setRequestProperty("X-nl-protocol-version", "1");
		    urlc.setRequestProperty("Content-length",String.valueOf (query.length())); 
		    urlc.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		    DataOutputStream output = new DataOutputStream( urlc.getOutputStream() );  
		    output.writeBytes(query);
		    
		    switch (urlc.getResponseCode()) {
			    case HttpsURLConnection.HTTP_OK :
			    	handleSuccess(urlc);
			    	break;
			    default :
			    	handleFailure(urlc);
		    }
	
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void setFanMode (FanModeEnum fanMode) {
		try {
			setFanMode(fanMode, getStatusResponse().getMetaData().getDeviceIds()[0]);
		} catch (Exception e) {
			// do something
		}
	}
	
	public void setTemperatureMode (TemperatureModeEnum temperatureMode, String deviceId) {
		
		if (!isLoggedIn)
			return;
	
		URL url;
		HttpsURLConnection urlc;
		String query;
		
		try {
			
			TemperatureMode req = new TemperatureMode();
			req.target_temperature_type = temperatureMode;
			query = gson.toJson(req);
			url = new URL(loginResponse.urls.transport_url+"/v2/put/shared."+deviceId);
			urlc = (HttpsURLConnection) url.openConnection();
		    urlc.setRequestMethod("POST"); 
		    urlc.setDoInput(true); 
		    urlc.setDoOutput(true);
		    urlc.setRequestProperty("user-agent", userAgent);
		    urlc.setRequestProperty("Authorization", "Basic " + loginResponse.access_token);
		    urlc.setRequestProperty("X-nl-protocol-version", "1");
		    urlc.setRequestProperty("Content-length",String.valueOf (query.length())); 
		    urlc.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		    DataOutputStream output = new DataOutputStream( urlc.getOutputStream() );  
		    output.writeBytes(query);
		    
		    switch (urlc.getResponseCode()) {
			    case HttpsURLConnection.HTTP_OK :
			    	handleSuccess(urlc);
			    	break;
			    default :
			    	handleFailure(urlc);
		    }
	
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void setTemperatureMode (TemperatureModeEnum temperatureMode) {
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
	
	private int handleLoginFailure (HttpsURLConnection urlc) throws IOException {
		return urlc.getResponseCode();
	}
	
	private void handleStatusFailure (HttpsURLConnection urlc) throws IOException {
		urlc.getResponseMessage();
	}
	
	private void handleSuccess (HttpsURLConnection urlc) {
		
	}
	
	private void handleFailure (HttpsURLConnection urlc) {
		try {
			System.out.println(urlc.getResponseCode() + " : " + urlc.getResponseMessage() );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method return the object variable associated to the JSON obtained by a previous call of <b>getStatus()</b> method. 
	 * Call getStatusResponse() before getStatus() will produce a null return value 
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
	 * @param credentials
	 */
	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}
	
	public void mockStatusResonse (String str) {
		statusResponse = gson.fromJson(str, StatusResponse.class);
	}

}
