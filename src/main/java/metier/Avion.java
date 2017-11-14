package metier;

public class Avion {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String idAvion;
    private String immatriculation;
    private int capaciteSoute;
    private int autonomie;
    private int consomationCarburant;
    private int capaciteLibre;



}
