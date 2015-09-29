package it.telecomitalia.osgi.ah.internal.nest;

import it.telecomitalia.osgi.ah.internal.nest.lib.Credentials;
import it.telecomitalia.osgi.ah.internal.nest.lib.JNest;
import java.util.Map;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.log.LogService;

public class NestBaseDriver {

	public DiscoveryThread discovery = null;
	private Thread t;

	protected void activate(Map<String, String> props) {

		JNest jn = new JNest();
		Credentials cred = new Credentials(props.get("it.telecomitalia.osgi.ah.nest.username"),
				props.get("it.telecomitalia.osgi.ah.nest.password"));
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
	
	protected void modified(Map<String, String> props) throws InterruptedException{
		t.interrupt();
		t.join();
		JNest jn = new JNest();
		Credentials cred = new Credentials(props.get("it.telecomitalia.osgi.ah.nest.username"),
				props.get("it.telecomitalia.osgi.ah.nest.password"));
		jn.setCredentials(cred);
		jn.login();

		discovery = new DiscoveryThread(jn);
		t = new Thread(discovery);
		t.start();
	}
}
