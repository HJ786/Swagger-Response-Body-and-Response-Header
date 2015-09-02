package com.tutorialsdesk.rest.model;
//import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


//@XmlRootElement
@ApiModel( value = "Task", description = "Task resource representation" )
public class Task {

@ApiModelProperty( value = "Tasks Id", required = true ) private String id;
@ApiModelProperty( value = "Tasks Summary", required = true ) private String summary;
@ApiModelProperty( value = "Tasks Description", required = true ) private String description;

public Task(){
}
public Task( final String id ) {
	this.id = id;
}
public Task (String id, String summary){
this.id = id;
this.summary = summary;
}
public String getId() {
return id;
}
public void setId(String id) {
this.id = id;
}
public String getSummary() {
return summary;
}
public void setSummary(String summary) {
this.summary = summary;
}
public String getDescription() {
return description;
}
public void setDescription(String description){
this.description = description;
}
} 
