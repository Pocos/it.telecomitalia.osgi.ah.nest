package it.telecomitalia.osgi.ah.internal.nest.lib;

import java.util.HashMap;

public class Widget_track {
	public HashMap<String, Widget_trackData> widgets = new HashMap<String, Widget_trackData>();
	public Widget_track () {}
	
	public Widget_trackData getWidget_track(String widgetId) {
		return widgets.get(widgetId);
	}

	public Widget_trackData createWidget_track(String key) {
		Widget_trackData widget = new Widget_trackData();
		setWidget_track(key, widget);
		return widget;
	}
	
	public String[] getWidget_trackIds () {
		String[] a = new String[]{};
		return widgets.keySet().toArray(a);
	}

	public void setWidget_track(String key, Widget_trackData widget) {
		widgets.put(key, widget);
	}
}
