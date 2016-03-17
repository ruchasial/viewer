package org.concord.viewer;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class JsonResponse {
	String type=null;
	String path=null;
	public JsonResponse(String type, String path){this.type=type;this.path=path;}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}
