package pl.filewicz.grzegorz.restful.notes.controller;

import pl.filewicz.grzegorz.restful.notes.api.NoteDto;
import pl.filewicz.grzegorz.restful.notes.service.DefaultNoteHistoryService;
import pl.filewicz.grzegorz.restful.notes.service.NoteHistoryService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/notes/history")
public class NoteHistoryController {
    private NoteHistoryService noteHistoryService;

    public NoteHistoryController() {
        this.noteHistoryService = new DefaultNoteHistoryService();
    }

    public NoteHistoryController(NoteHistoryService noteHistoryService) {
        this.noteHistoryService = noteHistoryService;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<NoteDto> readNoteHistory(@PathParam("id") Long id) {
        return noteHistoryService.readHistory(id);
    }
}
