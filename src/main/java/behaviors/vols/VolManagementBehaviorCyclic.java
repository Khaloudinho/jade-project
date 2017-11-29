package behaviors.vols;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import containers.CompagnieContainer;
import dao.Seeder;
import jade.core.behaviours.CyclicBehaviour;
import jade.gui.GuiEvent;
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

    //{"pays":"Guinee","date":"2017-05-16","volume":"10"}
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

        //Recevoir le message d'Anne
        //On recupere le JSON
        String message = aclMessage.getContent();
        System.out.println(message);

        //PROVISOIRE POUR TESTER L'INTERACTION
        //String message = "{\"pays\":\"Guinee\",\"date\":\"2017-05-16\",\"volume\":\"10\"}";
        //System.out.println("EN DUR " + message);

        //On construit un objet
        ObjectMapper mapper = new ObjectMapper();

        DemandeVols demandeVols = null;
        //JSON from String to Object
        try {
            demandeVols = mapper.readValue(message, DemandeVols.class);
            System.out.println(message.toString());
            System.out.println(demandeVols.toString());

            System.out.println("TO STRING " + demandeVols.toString());
            ArrayList<VolAssociation> volsChartersPourLesAssociation = Seeder.getVols(TypeVol.Charter, demandeVols.getDate().toString(), demandeVols.getPays(), demandeVols.getVolume());
            int tailleListeVols = volsChartersPourLesAssociation.size();
            System.out.println("TAILLE LISTE VOLS : "+ tailleListeVols);

            String messageAssociationContent = "";
            try {
                messageAssociationContent = mapper.writeValueAsString(volsChartersPourLesAssociation);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            //Retourner une propositions au groupe des associations
            if(tailleListeVols>0) {
                ACLMessage messageAssociation = aclMessage.createReply();
                messageAssociation.setPerformative(ACLMessage.PROPOSE);
                messageAssociation.setContent(messageAssociationContent);

                //messageAssociation.addReceiver(aclMessage.getSender());
                System.out.println("Liste de vols envoyee aux associations");
                myAgent.send(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Format de la demande invalide");
            String formatErrorMessageContent = "Erreur dans le format de la demande";
            response.setPerformative(ACLMessage.FAILURE);
            response.setContent(formatErrorMessageContent);
            //response.addReceiver(aclMessage.getSender());
            myAgent.send(response);
        }
    }
}



