package pl.filewicz.grzegorz.restful.notes.controller;

import pl.filewicz.grzegorz.restful.notes.api.NoteDto;
import pl.filewicz.grzegorz.restful.notes.service.DefaultNoteCrudService;
import pl.filewicz.grzegorz.restful.notes.service.NoteCrudService;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/notes")
public class NoteCrudController {
    private NoteCrudService noteCrudService;

    public NoteCrudController() {
        this.noteCrudService = new DefaultNoteCrudService();
    }

    public NoteCrudController(NoteCrudService noteCrudService) {
        this.noteCrudService = noteCrudService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Long createNote(@Valid NoteDto noteDto) {
        return noteCrudService.create(noteDto);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<NoteDto> readAllNotes() {
        return noteCrudService.readAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public NoteDto readNote(@PathParam("id") Long id) {
        return noteCrudService.read(id);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public NoteDto updateNote(@PathParam("id") Long id, NoteDto noteDto) {
        noteDto.setId(id);
        return noteCrudService.update(noteDto);
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public NoteDto deleteNote(@PathParam("id") Long id) {
        return noteCrudService.delete(id);
    }
}
