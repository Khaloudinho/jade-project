package behaviors.vols;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import containers.CompagnieContainer;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import messages.association.DemandeVols;
import messages.association.VolAssociation;
import util.TypeVol;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class VolManagementBehaviorCyclic extends CyclicBehaviour {

    private CompagnieContainer compagnieContainer;

    public VolManagementBehaviorCyclic(CompagnieContainer compagnieContainer) {
        this.compagnieContainer = compagnieContainer;
    }

    //{"pays":"Guinee","date":"2017-06-17","volume":"10"}
    @Override
    public void action() {
        ACLMessage aclMessage = myAgent.receive();

        if (aclMessage != null) {
            switch (aclMessage.getPerformative()) {
                case ACLMessage.CFP:
                    manageCFP(aclMessage);
                    break;

                case ACLMessage.ACCEPT_PROPOSAL:
                    break;

                default:
                    break;
            }
        } else {
            block();
        }
    }

    private void manageCFP(ACLMessage aclMessage) {
        ACLMessage response = aclMessage.createReply();

        //GuiEvent guiEvent = new GuiEvent(this, 1);
        //guiEvent.addParameter(aclMessage.getContent());
        //gui.viewMessage(guiEvent);
                    /*
                        //Recevoir le message d'Anne
                        GuiEvent guiEvent = new GuiEvent(this, 1);
                        guiEvent.addParameter(cfp.getContent());

                        //On recupere le JSON
                        String message = compagnieContainer.viewMessage(guiEvent);
                     */

        //PROVISOIRE POUR TESTER L'INTERACTION
        String message = "{\"pays\":\"Guinee\",\"date\":\"2017-01-01\",\"volume\":\"10\"}";
        System.out.println("EN DUR " + message);

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

            for (Object[] o : volsChartersCorrespondantsALaDemande) {
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
                                Integer.parseInt(o[3].toString()),
                                Integer.parseInt(o[4].toString().substring(0, o[4].toString().indexOf("."))),
                                TypeVol.Charter
                        )
                );
            }

            em.getTransaction().commit();
            em.close();
            emf.close();


            String messageAssociationContent = "";
            try {
                messageAssociationContent = mapper.writeValueAsString(volsChartersCorrespondantsALaDemande);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            //Retourner une propositions au groupe des associations
            ACLMessage messageAssociation = aclMessage.createReply();
            messageAssociation.setPerformative(ACLMessage.CFP);
            //ACLMessage messageAssociation = new ACLMessage(ACLMessage.PROPOSE);
            messageAssociation.setContent(messageAssociationContent);
            System.out.println("TEST MESSAGE :" + messageAssociation.getContent());

            messageAssociation.addReceiver(aclMessage.getSender());

            //***Test***
            String livre = messageAssociation.getContent();
            response.setContent(livre);
            System.out.println("SENDER : " + response.getSender() + " " + response.getSender().getName() + " " + response.getSender().getLocalName());
            response.addReceiver(response.getSender());
            myAgent.send(response);

        } catch (Exception e) {
            System.out.println("Format de la demande invalide");
            String formatErrorMessageContent = "Erreur dans le format de la demande";
            response.setPerformative(ACLMessage.FAILURE);
            response.setContent(formatErrorMessageContent);
            response.addReceiver(aclMessage.getSender());

        }
    }
}



