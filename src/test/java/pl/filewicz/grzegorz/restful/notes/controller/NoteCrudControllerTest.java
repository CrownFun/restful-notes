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
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class NoteCrudControllerTest extends JerseyTest {
    @Override
    protected Application configure() {
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);

        DefaultNoteCrudDao noteCrudDao = new DefaultNoteCrudDao();
        DefaultNoteCrudService noteCrudService = new DefaultNoteCrudService(noteCrudDao);

        return new ResourceConfig().register(new NoteCrudController(noteCrudService));
    }

    @Test
    public void givenPostNote_() {
        Form form = new Form();
        form.param("title", "JUnit Note Title 111");
        form.param("content", "JUnit Note Content 111");

        Response response = target("/notes")
                .request(MediaType.APPLICATION_FORM_URLENCODED)
                .post(Entity.form(form));

        System.out.println(response);
    }

    @Test
    public void givenGetReadAllNotes_whenCorrectRequest_thenResponseIsOkAndContainsJsonWithNotesList() {
        Response response = target("/notes").request().get();

        assertEquals("Http Response should be 200: ",
                Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("Http Content-Type should be: ",
                MediaType.APPLICATION_JSON, response.getHeaderString(HttpHeaders.CONTENT_TYPE));

        List<NoteDto> items = target("/notes")
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<NoteDto>>() {
                });

        System.out.println(items);

//        String content = response.readEntity(String.class);
//        assertEquals("Content of ressponse is: ", "hi", content);
//        assertEquals(3, items.size());
    }

    @Test
    public void givenGetReadNote_whenCorrectRequest_thenResponseIsOkAndContainsJsonWithNote() {
        Response response = target("/notes/2").request().get();

//        assertEquals("Http Response should be 200: ",
//                Response.Status.OK.getStatusCode(), response.getStatus());
//        assertEquals("Http Content-Type should be: ",
//                MediaType.APPLICATION_JSON, response.getHeaderString(HttpHeaders.CONTENT_TYPE));

        NoteDto noteDto = response.readEntity(NoteDto.class);
        System.out.println(noteDto);
//        String content = response.readEntity(String.class);
//        assertEquals("Content of ressponse is: ", "hi", content);
    }

}