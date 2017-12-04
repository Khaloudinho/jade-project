package behaviors.vols;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dao.DatabaseService;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.FIPANames;
import jade.lang.acl.ACLMessage;
import messages.DemandeVols;
import messages.VolAccepte;
import messages.VolAssociation;
import util.TypeVol;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

//PROVISOIRE POUR TESTER L'INTERACTION
//String message = "{\"pays\":\"Guinee\",\"date\":\"2017-01-01\",\"volume\":\"10\"}";
//System.out.println("EN DUR "+message);

public class VolManagementBehaviorCyclic extends CyclicBehaviour {

    public static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(VolManagementBehaviorCyclic.class);
    private final Gson gson = new GsonBuilder().create();

    public VolManagementBehaviorCyclic(Agent agent) {
        super(agent);
    }

    @Override
    public void action() {
        ACLMessage aclMessage = myAgent.receive();

        if (aclMessage != null) {
            switch (aclMessage.getPerformative()) {
                case ACLMessage.CFP:
                    ACLMessage vols = null;
                    vols = manageCFP(aclMessage);
                    myAgent.send(vols);
                    break;

                case ACLMessage.ACCEPT_PROPOSAL:
                    ACLMessage acceptation = manageACCEPT_PROPOSAL(aclMessage);
                    myAgent.send(acceptation);
                    break;

                default:
                    break;
            }
        } else {
            block();
        }
    }


    /**
     * Methode qui gere la demande initiale (volume, pays, date)
     * en renvoyant la liste des vols
     *
     * @param cfp message
     * @return message..
     */
    //{"pays":"Guinee","date":"May 16, 2017 09:10:10 AM","volume":"10"}
    private ACLMessage manageCFP(ACLMessage cfp) {
        //On recupere la demande
        //String message = cfp.getContent();
        String message = "{\"pays\":\"Guinee\",\"date\":\"May 16, 2017 09:10:10 AM\",\"volume\":\"10\"}";
        logger.info("Demande de vol : \n" + message);

        //On construit une reponse
        ACLMessage response = cfp.createReply();
        response.setProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);

        //On mappe de notre cote la demande
        DemandeVols demandeVols;

        demandeVols = gson.fromJson(message, DemandeVols.class);

        //On recupere la liste des vols pertinents
        ArrayList<VolAssociation> volsChartersCorrespondantsALaDemande = new ArrayList<VolAssociation>();
        //volsChartersCorrespondantsALaDemande.add(new VolAssociation("56867798", "Leopold San", "Guinee", new java.util.Date(), 40, 40, TypeVol.Charter));
        //volsChartersCorrespondantsALaDemande.add(new VolAssociation("568677989657698", "Leopold San2", "Guinee", new java.util.Date(), 40, 40, TypeVol.Charter));

        volsChartersCorrespondantsALaDemande = DatabaseService.getVols(TypeVol.Charter, demandeVols.getDate(), demandeVols.getPays(), demandeVols.getVolume());

        int tailleListeVols = volsChartersCorrespondantsALaDemande.size();
        logger.info("TAILLE LISTE VOLS : " + tailleListeVols);

        //On transforme cette de liste de resultats en JSON
        String messageAssociationContent = "";

        messageAssociationContent = gson.toJson(volsChartersCorrespondantsALaDemande);

        //Si la liste contient au moins 1 vol
        //On va envoyer cette liste avec le type PROPOSE
        if (tailleListeVols > 0) {
            response.setPerformative(ACLMessage.PROPOSE);
            response.setContent(messageAssociationContent);
            logger.info("Liste de vols envoyee aux associations");
            //Si nous n'avons aucun vols par rapport a la demande effectue
            //Nous envoyons un REFUSE
        } else {
            response.setPerformative(ACLMessage.REFUSE);
            logger.info("Pas de vols pour la date demandee");
        }
        //Si jamais le mapper venait a planter on aurait une erreur de format de donnees
        logger.info("CONTENU RESPONSE : " + response.toString());
        return response;
    }

    /*[{"uuid":"c0628118-e751-4ff2-8ee3-2d4d053262c2", "capacite":10},
     {"uuid":"c0a7cbdc-ac69-470f-997a-465a7d0fc584", "capacite":20}]*/
    //!\prevoir cas capacite trop grosse ?
    private ACLMessage manageACCEPT_PROPOSAL(ACLMessage acceptProposal) {

        //FIX ME @sana
        //trouver un moyen de connaitre la quantite d'argent gagne (par rapport a la liste communique) : liste des vols est de leur prix attribut ?
        //match/intersect des deux liste
        //recuperation du prix mise dans notre portefeuille = attribut representant l'argent de l'agent

        //Suite la premiere demande nous recuperons une liste de vols desires
        String volsChoisis = acceptProposal.getContent();
        logger.info("Liste de vols acceptes (idVol, capacite) : \n" + volsChoisis.toString());

        //Nous preparons une confirmation du traitement
        ACLMessage response = acceptProposal.createReply();
        response.setPerformative(ACLMessage.INFORM);

        //On doit faire un petit hack pour remapper leurs vols
        Type collectionType = new TypeToken<Collection<VolAccepte>>() {
        }.getType();
        ArrayList<VolAccepte> volAcceptes = gson.fromJson(volsChoisis, collectionType);

        //On met a jour l'etat de la base de donnees
        for (VolAccepte volAccepte :
                volAcceptes) {
            String idVol = volAccepte.getUuid();
            Integer capaciteAUtiliser = volAccepte.getCapacite();
            DatabaseService.updateCapaciteVol(idVol, capaciteAUtiliser);
        }
        //response.setContent(acceptedVols);
        return response;
    }
}



