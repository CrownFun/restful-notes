package pl.filewicz.grzegorz.restful.notes.api.mapper;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NoteExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    @Override
    public Response toResponse(final ConstraintViolationException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(prepareMessage(exception))
                .type("text/plain")
                .build();
    }

    private String prepareMessage(ConstraintViolationException exception) {
        String msg = "";
        for (ConstraintViolation<?> cv : exception.getConstraintViolations()) {
//            msg += cv.getPropertyPath() + " " + cv.getMessage() + "\n";
            msg += cv.getMessage() + "\n";
        }
        return msg;
    }
}
