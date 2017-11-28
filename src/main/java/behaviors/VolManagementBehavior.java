package behaviors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import containers.CompagnieContainer;
import dao.Seeder;
import jade.core.Agent;
import jade.core.behaviours.DataStore;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetResponder;
import messages.DemandeVols;
import messages.VolAssociation;
import util.TypeVol;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class VolManagementBehavior extends ContractNetResponder {

    private CompagnieContainer compagnieContainer;

    public VolManagementBehavior(Agent a, MessageTemplate mt) {
        super(a, mt);
    }

    public VolManagementBehavior(Agent a, MessageTemplate mt, DataStore store) {
        super(a, mt, store);
    }

    public VolManagementBehavior(Agent a, MessageTemplate mt, CompagnieContainer compagnieContainer) {
        super(a, mt);
        this.compagnieContainer = compagnieContainer;
    }

    //{"pays":"Guinee","date":"2017-06-17","volume":"10"}
    @Override
    protected ACLMessage handleCfp(ACLMessage cfp) throws RefuseException, FailureException, NotUnderstoodException {
        //Recevoir le message d'Anne
        GuiEvent guiEvent = new GuiEvent(this, 1);
        guiEvent.addParameter(cfp.getContent());

        //On recupere le JSON
        String message = compagnieContainer.viewMessage(guiEvent);

        //On construit un objet
        ObjectMapper mapper = new ObjectMapper();

        DemandeVols demandeVols = null;
        //JSON from String to Object
        try {
            //FIX ME static mapper
            demandeVols = mapper.readValue(message, DemandeVols.class);
            System.out.println(message.toString());
            System.out.println(demandeVols.toString());

            //On utilise cet objet (ses attributs/) pour effectuer la requete / cet objet n'est pas persiste

            //FIX ME refractor with a design pattern
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("jadeprojectPU");
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();

            //FIX ME use entity manager
            System.out.println("Requête : Vol.getVolsChartersCorrespondantsALaDemande");

            ArrayList<VolAssociation> volsChartersCorrespondantsALaDemande = Seeder.getVols(TypeVol.Charter, String.valueOf(demandeVols.getDate()), demandeVols.getPays(), demandeVols.getVolume());

            String messageAssociationContent = "";
            try {
                messageAssociationContent = mapper.writeValueAsString(volsChartersCorrespondantsALaDemande);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            //Retourner une propositions au groupe des associations
            ACLMessage messageAssociation = new ACLMessage(ACLMessage.PROPOSE);
            messageAssociation.setContent(messageAssociationContent);
            System.out.println("TEST MESSAGE :" + messageAssociation.getContent());

            messageAssociation.addReplyTo(cfp.getSender());

            return messageAssociation;

        } catch (Exception e) {
            System.out.println("Format de la demande invalide");
            String formatErrorMessageContent = "Erreur dans le format de la demande";
            ACLMessage formatErrorMessage = new ACLMessage(ACLMessage.INFORM);
            formatErrorMessage.setContent(formatErrorMessageContent);
            formatErrorMessage.addReplyTo(cfp.getSender());

            return formatErrorMessage;
        }
    }

}
