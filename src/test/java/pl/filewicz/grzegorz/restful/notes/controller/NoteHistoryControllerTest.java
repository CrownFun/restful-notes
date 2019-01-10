package pl.filewicz.grzegorz.restful.notes.controller;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;
import pl.filewicz.grzegorz.restful.notes.api.NoteDto;
import pl.filewicz.grzegorz.restful.notes.dao.DefaultNoteCrudDao;
import pl.filewicz.grzegorz.restful.notes.dao.DefaultNoteHistoryDao;
import pl.filewicz.grzegorz.restful.notes.service.DefaultNoteCrudService;
import pl.filewicz.grzegorz.restful.notes.service.DefaultNoteHistoryService;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class NoteHistoryControllerTest extends JerseyTest {

    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);

        ResourceConfig resourceConfig = new ResourceConfig();

        DefaultNoteCrudDao noteCrudDao = new DefaultNoteCrudDao();
        DefaultNoteCrudService noteCrudService = new DefaultNoteCrudService(noteCrudDao);
        resourceConfig.register(new NoteCrudController(noteCrudService));

        DefaultNoteHistoryDao noteHistoryDao = new DefaultNoteHistoryDao();
        DefaultNoteHistoryService noteHistoryService = new DefaultNoteHistoryService(noteHistoryDao);
        resourceConfig.register(new NoteHistoryController(noteHistoryService));

        return resourceConfig;
    }

    @Test
    public void givenGetReadAllNotes_whenCorrectRequest_thenResponseIsOkAndContainsJsonWithNotesList() {
        String updateTitle = "Test updated Title json JUnit 2";
        String updateContent = "Test updated Content json JUnit 2";

        Response postResponse = target("/notes")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(new NoteDto("Test Title json JUnit 1", "Test Content json JUnit 1")));
        NoteDto createdNoteDto = postResponse.readEntity(NoteDto.class);

        createdNoteDto.setTitle(updateTitle);
        createdNoteDto.setContent(updateContent);

        target("/notes/" + createdNoteDto.getId())
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.json(createdNoteDto));

        List<NoteDto> noteHistoryList = target("/notes/history/" + createdNoteDto.getId())
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<NoteDto>>() {
                });

        assertEquals(2, noteHistoryList.size());
    }
}