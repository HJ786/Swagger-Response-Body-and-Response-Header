package com.tutorialsdesk.rest.resources;
import io.swagger.annotations.Api;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;



//import com.example.resource.Person;
//import com.example.services.PeopleService;
import com.tutorialsdesk.rest.dao.TaskDao;
import com.tutorialsdesk.rest.model.Task;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

// Will map the resource to the URL tasks

@Path("/tasks")
@Api(value = "/Tasks API", description = "Manage Tasks" )
public class TasksResource {
// Allows to insert contextual objects into the class, 
// e.g. ServletContext, Request, Response, UriInfo
@Context
UriInfo uriInfo;
@Context
Request request;
// Return the list of tasks to the user in the browser

@Inject private TaskResource taskresource;

@Produces( { MediaType.APPLICATION_JSON } )
@GET
@ApiOperation( value = "List all task", notes = "List all task using paging", response = Task.class, responseContainer = "List")
public Collection< Task > getTask(  @ApiParam( value = "Page to fetch", required = true ) @QueryParam( "page") @DefaultValue( "1" ) final int page ) {
	return taskresource.getTask( page, 5 );
}

@Produces( { MediaType.APPLICATION_JSON } )
@Path( "/{id}" )
@GET
@ApiOperation( value = "Find task by id", notes = "Find task by id", response = Task.class )
@ApiResponses( {
    @ApiResponse( code = 404, message = "Task with such ID doesn't exists" )			 
} )
public Task getTask( @ApiParam( value = "ID address to lookup for", required = true ) @PathParam( "id" ) final String id ) {
	return taskresource.getByID( id );
}


@Produces( { MediaType.APPLICATION_JSON  } )
@POST
@ApiOperation( value = "Create new task", notes = "Create new task" )
@ApiResponses( {
    @ApiResponse( code = 201, message = "Task created successfully" ),
    @ApiResponse( code = 409, message = "Task with such ID already exists" )
} )
public Response addTask( @Context final UriInfo uriInfo,
		@ApiParam( value = "ID", required = true ) @FormParam( "id" ) final String id, 
		@ApiParam( value = "Summary", required = true ) @FormParam( "summary" ) final String summary, 
		@ApiParam( value = "Description", required = true ) @FormParam( "description" ) final String description ) {
	
	taskresource.addTask( id, summary, description );
	return Response.created( uriInfo.getRequestUriBuilder().path( id ).build() ).build();
}


@Produces( { MediaType.APPLICATION_JSON  } )
@Path( "/{id}" )
@PUT
@ApiOperation( value = "Update existing task", notes = "Update existing task", response = Task.class )
@ApiResponses( {
    @ApiResponse( code = 404, message = "Task with such ID doesn't exists" )			 
} )
public Task updateTask(			
		@ApiParam( value = "ID", required = true ) @FormParam( "id" ) final String id, 
		@ApiParam( value = "Summary", required = true ) @FormParam( "summary" ) final String summary, 
		@ApiParam( value = "Description", required = true ) @FormParam( "description" ) final String description ) {
	
	final Task task = taskresource.getByID( id );
	
	if( summary != null ) {
		task.setSummary( summary );
	}
	
	if( description != null ) {
		task.setDescription( description );
	}

	return task; 				
}


@Path( "/{id}" )
@DELETE
@ApiOperation( value = "Delete existing task", notes = "Delete existing task", response = Task.class )
@ApiResponses( {
    @ApiResponse( code = 404, message = "Task with such ID doesn't exists" )			 
} )

public Response deleteTask( @ApiParam( value = "ID", required = true ) @PathParam( "id" ) final String id ) {
	taskresource.removeTask( id );
	return Response.ok().build();
}




@Path( "count" )
@GET
@ApiOperation( value = "Total Tasks", notes = "Total Tasks", response = Task.class )
@ApiResponses( {
    @ApiResponse( code = 404, message = "There are no Tasks" )			 
} )

public String getCount() {
int count = TaskDao.instance.getModel().size();
return String.valueOf(count);
}


} 









/*
 package com.tutorialsdesk.rest.resources;
import io.swagger.annotations.Api;

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
import com.tutorialsdesk.rest.model.Task;

// Will map the resource to the URL tasks
@Api(value = "Tasks API", description = "Manage people" )
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
*/