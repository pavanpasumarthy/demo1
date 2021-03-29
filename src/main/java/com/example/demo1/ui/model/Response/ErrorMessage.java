package com.example.demo1.ui.model.Response;

import java.util.Date;

public class ErrorMessage {
private Date date;
private String message;
public Date getDate() {
	return date;
}
public String getMessage() {
	return message;
}
public void setDate(Date date) {
	this.date = date;
}
public void setMessage(String message) {
	this.message = message;
}
public ErrorMessage(Date date, String message) {
	super();
	this.date = date;
	this.message = message;
}

}
