package behaviors.vols;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import containers.CompagnieContainer;
import dao.Seeder;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import messages.DemandeVols;
import messages.VolAssociation;
import util.TypeVol;

import java.util.ArrayList;

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
        String message = "{\"pays\":\"Guinee\",\"date\":\"2017-05-16\",\"volume\":\"10\"}";
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

            //FIX ME use entity manager
            System.out.println("Requête : Vol.getVolsChartersCorrespondantsALaDemande");

            System.out.println("TO STRING " + demandeVols.toString());
            ArrayList<VolAssociation> volsChartersPourLesAssociation = Seeder.getVols(TypeVol.Charter, demandeVols.getDate().toString(), demandeVols.getPays(), demandeVols.getVolume());
            System.out.println("TEST RESULTAT "+ volsChartersPourLesAssociation.size());

            String messageAssociationContent = "";
            try {
                messageAssociationContent = mapper.writeValueAsString(volsChartersPourLesAssociation);
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
            System.out.println("SENDER : " + aclMessage.getSender() + " " + aclMessage.getSender().getName() + " " + aclMessage.getSender().getLocalName());
            response.addReceiver(aclMessage.getSender());
            myAgent.send(response);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Format de la demande invalide");
            String formatErrorMessageContent = "Erreur dans le format de la demande";
            response.setPerformative(ACLMessage.FAILURE);
            response.setContent(formatErrorMessageContent);
            response.addReceiver(aclMessage.getSender());
            myAgent.send(response);
        }
    }
}



