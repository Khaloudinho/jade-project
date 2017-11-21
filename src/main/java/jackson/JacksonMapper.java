package jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import impl.AvionImpl;
import impl.BaseTarifImpl;
import impl.LieuImpl;
import metier.Vol;
import util.TypeVol;

import java.sql.Date;

public class JacksonMapper {

    private static String mapVol() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        Date dateDepart = Date.valueOf("2017-01-01");
        Date dateArrivee = Date.valueOf("2017-01-01");

        LieuImpl li = new LieuImpl();
        AvionImpl ai = new AvionImpl();
        BaseTarifImpl bi = new BaseTarifImpl();

        //Vol volExample = new Vol(dateDepart, dateArrivee, TypeVol.charter, bi.getBaseTarifParNom("charter"), ai.getAvionParImmatriculation("AFX-508-RF"), li.getLieuParVille("Douala"));
        Vol volExample = new Vol(dateDepart, dateArrivee, TypeVol.charter, bi.getBaseTarifParNom("charter"), ai.getAvionParImmatriculation("AFX-508-RF"), li.getLieuParVille("Douala"));

        //Object to JSON in String
        String jsonInString = mapper.writeValueAsString(volExample);

        return jsonInString;
    }

    public static void main(String[] args) throws JsonProcessingException {
        System.out.println(JacksonMapper.mapVol());

    }
}
