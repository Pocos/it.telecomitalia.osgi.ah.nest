package it.telecomitalia.osgi.ah.internal.nest;

import it.telecomitalia.osgi.ah.internal.nest.lib.Credentials;
import it.telecomitalia.osgi.ah.internal.nest.lib.JNest;

import java.security.InvalidParameterException;
import java.util.Map;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.log.LogService;

public class NestBaseDriver {

	public String NEST_USERNAME_PROP = "it.telecomitalia.osgi.ah.nest.username";
	public String NEST_PASSWORD_PROP = "it.telecomitalia.osgi.ah.nest.password";

	public DiscoveryThread discovery = null;
	private Thread t;

	private String username = null;
	private String password = null;

	protected void activate(Map<String, String> props) {

		username = props.get(NEST_USERNAME_PROP);
		if (username == null) {
			username = System.getProperty(NEST_USERNAME_PROP);
			if (username == null) {
				throw new InvalidParameterException("USERNAME NOT PROVIDED");
			}
		}

		password = props.get(NEST_PASSWORD_PROP);
		if (password == null) {
			password = System.getProperty(NEST_PASSWORD_PROP);
			if (password == null) {
				throw new InvalidParameterException("USERNAME NOT PROVIDED");
			}
		}

		JNest jn = new JNest();
		Credentials cred = new Credentials(username, password);

		jn.setCredentials(cred);
		jn.login();
		discovery = new DiscoveryThread(jn);
		t = new Thread(discovery);
		t.start();
	}

	protected void deactivate(ComponentContext ctxt) throws InterruptedException {
		t.interrupt();
		t.join();
	}

	protected void bindLogService(LogService log) {

	}

	protected void unbindLogService(LogService log) {

	}

	protected void modified(Map<String, String> props) throws InterruptedException {
		t.interrupt();
		t.join();

		JNest jn = new JNest();
		Credentials cred = new Credentials(props.get(NEST_USERNAME_PROP), props.get(NEST_PASSWORD_PROP));
		jn.setCredentials(cred);
		jn.login();

		discovery = new DiscoveryThread(jn);
		t = new Thread(discovery);
		t.start();
	}
}
