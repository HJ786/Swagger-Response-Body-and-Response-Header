package com.tutorialsdesk.rest.resources;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import com.tutorialsdesk.rest.dao.TaskDao;
import com.tutorialsdesk.rest.model.Task;;

// Will map the resource to the URL tasks
@Path("/tasks")
public class TasksResource {
// Allows to insert contextual objects into the class, 
// e.g. ServletContext, Request, Response, UriInfo
@Context
UriInfo uriInfo;
@Context
Request request;
// Return the list of tasks to the user in the browser
@GET
@Produces(MediaType.TEXT_XML)
public List<Task> getTodosBrowser() {
List<Task> tasks = new ArrayList<Task>();
tasks.addAll(TaskDao.instance.getModel().values());
return tasks; 
}

// Return the list of tasks for applications
@GET
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
public List<Task> getTasks() {
List<Task> tasks = new ArrayList<Task>();
tasks.addAll(TaskDao.instance.getModel().values());
return tasks; 
}

// retuns the number of tasks
// use http://localhost:8080/TutorialsDesk.Rest.CRUDWebServices/rest/tasks/count
// to get the total number of records
@GET
@Path("count")
@Produces(MediaType.TEXT_PLAIN)
public String getCount() {
int count = TaskDao.instance.getModel().size();
return String.valueOf(count);
}

@POST
@Produces(MediaType.TEXT_HTML)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
public void newTask(@FormParam("id") String id,
@FormParam("summary") String summary,
@FormParam("description") String description,
@Context HttpServletResponse servletResponse) throws IOException {
Task task = new Task(id,summary);
if (description!=null){
task.setDescription(description);
}
TaskDao.instance.getModel().put(id, task);
servletResponse.sendRedirect("../create_task.html");
}

// Defines that the next path parameter after tasks is
// treated as a parameter and passed to the TodoResources
// Allows to type http://localhost:8080/TutorialsDesk.Rest.CRUDWebServices/rest/tasks/1
// 1 will be treaded as parameter tasks and passed to TaskResource
@Path("{task}")
public TaskResource getTask(@PathParam("task") String id) {
return new TaskResource(uriInfo, request, id);
}
} 