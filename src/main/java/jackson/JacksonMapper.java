package jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import messages.DemandeVols;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

public class JacksonMapper {

    public static final ObjectMapper mapper = new ObjectMapper();

    public static String serialize(Object objectToSerialize) throws JsonProcessingException {
        return mapper.writeValueAsString(objectToSerialize);
    }

    public static Object deserialize(String JSONToDeserialize) throws IOException {
        return mapper.readValue(JSONToDeserialize, Object.class);
    }
}
