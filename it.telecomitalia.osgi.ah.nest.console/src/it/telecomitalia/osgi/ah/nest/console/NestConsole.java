package it.telecomitalia.osgi.ah.nest.console;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.telecomitalia.ah.nest.NestDevice;

import org.eclipse.osgi.framework.console.CommandInterpreter;
import org.eclipse.osgi.framework.console.CommandProvider;

public class NestConsole implements CommandProvider {

	// The cardinality is 1...n, so it is mandatory and i call setNestDevice for
	// each service in the registry
	private List<NestDevice> devices = Collections.synchronizedList(new ArrayList<NestDevice>());

	public void _isConnected(CommandInterpreter intp) {
		if (!checkNestDevice(intp))
			return;
		
		for (NestDevice d : devices) {
			intp.println(d.getId());
		}
	}

	private boolean checkNestDevice(CommandInterpreter intp) {
		if (this.devices == null) {
			intp.print("NestDevice Service needs to be started\n");
			return false;
		}

		return true;
	}

	public String getHelp() {
		String help = "Console";
		return help;
	}

	protected void addNestDevice(NestDevice s) {
		devices.add(s);
	}

	protected void removeNestDevice(NestDevice s) {
		devices.remove(s);
	}
}
