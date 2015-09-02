package com.example.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class TaskAlreadyExistsException extends WebApplicationException {
	private static final long serialVersionUID = 6817489620338221395L;

	public TaskAlreadyExistsException( final String email ) {
		super(
			Response
				.status( Status.CONFLICT )
				.build()
		);
	}
}
