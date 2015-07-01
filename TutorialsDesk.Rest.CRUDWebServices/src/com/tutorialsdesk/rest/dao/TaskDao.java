package com.tutorialsdesk.rest.dao;
import java.util.HashMap;
import java.util.Map;
 
import com.tutorialsdesk.rest.model.Task;;
 
public enum TaskDao {
  instance;
   
  private Map<String, Task> contentProvider = new HashMap<String, Task>();
   
  private TaskDao() {
     
    Task task = new Task("1", "Learn REST");
    task.setDescription("Read http://www.tutorialsdesk.com/2014/09/jersey-restful-webservices-tutorial.html");
    contentProvider.put("1", task);
    task = new Task("2", "Do something");
    task.setDescription("Read complete http://www.tutorialsdesk.com");
    contentProvider.put("2", task);
     
  }
  public Map<String, Task> getModel(){
    return contentProvider;
  }
   
} 