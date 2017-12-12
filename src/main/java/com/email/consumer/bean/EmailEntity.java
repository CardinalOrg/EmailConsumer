package com.email.consumer.bean;

public class EmailEntity {
	
	private String to;
	private String from;
	private String content;
	private String appId;
	private String timestamp;

	
	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String toString () {
		 return "to: '" + getTo() + "', from: '" + getFrom() + "', content: '" + getContent() + "', appId: '"+ getAppId() + "', timestamp: '"+ getTimestamp();
	}

}
