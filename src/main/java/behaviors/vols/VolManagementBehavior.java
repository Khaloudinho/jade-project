package behaviors.vols;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import containers.CompagnieContainer;
import jade.core.Agent;
import jade.core.behaviours.DataStore;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetResponder;
import messages.association.DemandeVols;
import messages.association.VolAssociation;
import util.TypeVol;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.io.IOException;
import java.security.acl.Acl;
import java.sql.Date;
import java.text.SimpleDateFormat;
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

            Query queryVolsChartersCorrespondantsALaDemande = em.createNamedQuery("Vol.getVolsCorrespondantsALaDemande", Object[].class);
            queryVolsChartersCorrespondantsALaDemande.setParameter("date", demandeVols.getDate());
            queryVolsChartersCorrespondantsALaDemande.setParameter("pays", demandeVols.getPays());
            queryVolsChartersCorrespondantsALaDemande.setParameter("capaciteLibre", demandeVols.getVolume());
            queryVolsChartersCorrespondantsALaDemande.setParameter("typeVol", TypeVol.Charter);

            List<Object[]> volsChartersCorrespondantsALaDemande = queryVolsChartersCorrespondantsALaDemande.getResultList();

            ArrayList<VolAssociation> volsChartersPourLesAssociation = new ArrayList<>();

            for (Object[] o : volsChartersCorrespondantsALaDemande){
                System.out.println("============== VOL CHARTER CORRESPONDANT ==============");
                System.out.println("Aéroport : " + o[0].toString());
                System.out.println("Pays : " + o[1].toString());
                System.out.println("Date arrivée : " + o[2].toString());
                System.out.println("Capacité libre : " + o[3].toString());
                System.out.println("Prix : " + o[4].toString());
                System.out.println("IdVol : " + o[5].toString());
                System.out.println("========================================================");

                volsChartersPourLesAssociation.add(
                        new VolAssociation(o[5].toString(), o[0].toString(), o[1].toString(), Date.valueOf(o[2].toString()),
                                Integer.parseInt(o[3].toString()), Integer.parseInt(o[4].toString().substring(0, o[4].toString().indexOf(".")))
                        )
                );
            }

            em.getTransaction().commit();
            em.close();
            emf.close();

            //1 On renvoie tous les vols ==> degeulasse
            //ArrayList<VolAssociation> volsPourLesAssociation = new ArrayList<>();

            //for (Object[] o : volsCorrespondantsALaDemande){
            //   System.out.println("============== VOL CORRESPONDANT ==============");
            //   System.out.println("Aéroport : " + o[0].toString());
            //   System.out.println("Pays : " + o[1].toString());
            //   System.out.println("Date départ : " + o[2].toString());
            //   System.out.println("Capacité libre : " + o[3].toString());
            //   System.out.println("Prix : " + o[4].toString());
            //   System.out.println("IdVol : " + o[5].toString());
            //   System.out.println("===============================================");

            //   //final String OLD_FORMAT = "dd-MM-yyyy";
            //   final String OLD_FORMAT = "yyyy-MM-dd";
            //   SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
            //   Date dateVol = null;
            //   try {
            //       System.out.println(" DATE VOL " + o[2].toString());
            //       String test = o[2].toString();
            //       dateVol = Date.valueOf(test);
            //   } catch (Exception e) {
            //       e.printStackTrace();
            //   }

            //   volsPourLesAssociation.add(new VolAssociation(o[5].toString(), o[0].toString(), o[1].toString(), dateVol, Integer.parseInt(o[3].toString()), Integer.parseInt(o[4].toString().substring(0, o[4].toString().indexOf(".")))));

            //}

            String messageAssociationContent = "";
            try {
            //    messageAssociationContent = mapper.writeValueAsString(volsPourLesAssociation);
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
