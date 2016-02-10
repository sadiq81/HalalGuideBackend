package dk.eazyit.halalguide.editor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dk.eazyit.halalguide.domain.Location;

import java.beans.PropertyEditorSupport;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Privat
 * Date: 08/02/16
 * Time: 21.27
 * To change this template use File | Settings | File Templates.
 */
public class LocationEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Location value = mapper.readValue(text, Location.class);
            setValue(value);
        } catch (IOException e) {
            setValue(null);
        }
    }
}