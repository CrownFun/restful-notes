package pl.filewicz.grzegorz.restful.notes.controller;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;
import pl.filewicz.grzegorz.restful.notes.api.NoteDto;
import pl.filewicz.grzegorz.restful.notes.dao.DefaultNoteCrudDao;
import pl.filewicz.grzegorz.restful.notes.service.DefaultNoteCrudService;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class NoteCrudControllerIntegrationTest extends JerseyTest {

    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);

        DefaultNoteCrudDao noteCrudDao = new DefaultNoteCrudDao();
        DefaultNoteCrudService noteCrudService = new DefaultNoteCrudService(noteCrudDao);

        return new ResourceConfig().register(new NoteCrudController(noteCrudService));
    }

    @Test
    public void givenPostNote_whenCorrectRequest_thenResponseIsCreatedAndReturnCreatedNote() {
        NoteDto noteDto = new NoteDto("Test Title json JUnit 1", "Test Content json JUnit 1");

        Response response = target("/notes")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(noteDto));
        NoteDto createdNoteDto = response.readEntity(NoteDto.class);

        assertEquals("Http Response should be 201 ",
                Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertNotNull("Entity should not be null ", createdNoteDto);

        assertEquals(noteDto.getContent(), createdNoteDto.getContent());
        assertEquals(noteDto.getTitle(), createdNoteDto.getTitle());
    }

    @Test
    public void givenGetReadAllNotes_whenCorrectRequest_thenResponseIsOkAndContainsJsonWithNotesList() {
        createNoteDtoWithPost("Test Title json JUnit 1", "Test Content json JUnit 1");
        createNoteDtoWithPost("Test Title json JUnit 2", "Test Content json JUnit 2");
        createNoteDtoWithPost("Test Title json JUnit 3", "Test Content json JUnit 3");

        Response response = target("/notes").request().get();

        assertEquals("Http Response should be 200: ",
                Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("Http Content-Type should be: ",
                MediaType.APPLICATION_JSON, response.getHeaderString(HttpHeaders.CONTENT_TYPE));

        List<NoteDto> notes = target("/notes")
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<NoteDto>>() {
                });

        assertEquals(3, notes.size());
    }

    @Test
    public void givenGetReadNote_whenCorrectRequest_thenResponseIsOkAndContainsJsonWithNote() {
        NoteDto noteDto = createNoteDtoWithPost("Test Title json JUnit 1", "Test Content json JUnit 1");

        Response response = target("/notes/" + noteDto.getId()).request().get();

        assertEquals("Http Response should be 200: ",
                Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull("Entity should not be null ", noteDto);
    }

    @Test
    public void givenPutNote_whenCorrectRequest_thenResponseIsOkAndReturnUpdatedNote() {
        String updateTitle = "Test updated Title json JUnit 2";
        String updateContent = "Test updated Content json JUnit 2";

        NoteDto noteDto = createNoteDtoWithPost("Test Title json JUnit 1", "Test Content json JUnit 1");

        noteDto.setTitle(updateTitle);
        noteDto.setContent(updateContent);

        Response response = target("/notes/" + noteDto.getId())
                .request(MediaType.APPLICATION_JSON)
                .put(Entity.json(noteDto));
        NoteDto updatesNoteDto = response.readEntity(NoteDto.class);

        assertEquals("Http Response should be 200 ",
                Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull("Entity should not be null ", updatesNoteDto);

        assertEquals(updatesNoteDto.getTitle(), updateTitle);
        assertEquals(updatesNoteDto.getContent(), updateContent);
    }

    @Test
    public void givenDeleteNote_whenCorrectRequest_thenResponseIsOkAndReturnDeletedNote() {
        NoteDto noteDto = createNoteDtoWithPost("Test Title json JUnit 1", "Test Content json JUnit 1");

        Response response = target("/notes/" + noteDto.getId())
                .request(MediaType.APPLICATION_JSON)
                .delete();
        NoteDto updatesNoteDto = response.readEntity(NoteDto.class);

        assertEquals("Http Response should be 200 ",
                Response.Status.OK.getStatusCode(), response.getStatus());
        assertNotNull("Entity should not be null ", updatesNoteDto);
    }

    private NoteDto createNoteDtoWithPost(String title, String content) {
        Response response = target("/notes")
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(new NoteDto(title, content)));

        return response.readEntity(NoteDto.class);
    }

}