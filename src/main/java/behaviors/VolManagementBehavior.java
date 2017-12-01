package behaviors;

//{"volume":0,"date":"Nov 30, 2017 10:33:51 AM","pays":"Guinee"}

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import containers.CompagnieContainer;
import dao.Seeder;
import jade.core.Agent;
import jade.core.behaviours.DataStore;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetResponder;
import messages.DemandeVols;
import messages.VolAssociation;
import util.TypeVol;

import java.util.ArrayList;

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

    //{"pays":"Guinee","date":"2017-05-16","volume":"10"}
    @Override
    protected ACLMessage handleCfp(ACLMessage cfp) throws RefuseException, FailureException, NotUnderstoodException {

        //Recevoir le message d'Anne
        //On recupere le JSON
        String message = cfp.getContent();
        System.out.println(message);

        //PROVISOIRE POUR TESTER L'INTERACTION
        //String message = "{\"pays\":\"Guinee\",\"date\":\"2017-01-01\",\"volume\":\"10\"}";
        //System.out.println("EN DUR "+message);

        //On construit un objet
        ObjectMapper mapper = new ObjectMapper();

        DemandeVols demandeVols;
        //JSON from String to Object

        try {
            demandeVols = mapper.readValue(message, DemandeVols.class);
            System.out.println(message);
            System.out.println(demandeVols.toString());

            System.out.println("TO STRING " + demandeVols.toString());
            ArrayList<VolAssociation> volsChartersCorrespondantsALaDemande = Seeder.getVols(TypeVol.Charter, demandeVols.getDate().toString(), demandeVols.getPays(), demandeVols.getVolume());
            int tailleListeVols = volsChartersCorrespondantsALaDemande.size();
            System.out.println("TAILLE LISTE VOLS : "+ tailleListeVols);

            String messageAssociationContent = "";
            try {
                messageAssociationContent = mapper.writeValueAsString(volsChartersCorrespondantsALaDemande);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

            //Retourner une propositions au groupe des associations
            if (tailleListeVols > 0) {
                ACLMessage messageAssociation = cfp.createReply();
                messageAssociation.setPerformative(ACLMessage.PROPOSE);
                messageAssociation.setContent(messageAssociationContent);

                //messageAssociation.addReceiver(cfp.getSender());

                System.out.println("Liste de vols envoyee aux associations");
                //return super.prepareResponse(messageAssociation);
                //myAgent.send(messageAssociation);
                return this.prepareResponse(messageAssociation);
            }else{
                ACLMessage messageAssociation = cfp.createReply();
                messageAssociation.setPerformative(ACLMessage.REFUSE);
                //messageAssociation.addReceiver(cfp.getSender());
                System.out.println("Pas de vols pour la date demandee");
                return super.prepareResponse(messageAssociation);
            }
        } catch (Exception e) {
            System.out.println("Format de la demande invalide");
            String formatErrorMessageContent = "Erreur dans le format de la demande";
            e.printStackTrace();
            ACLMessage formatErrorMessage = cfp.createReply();
            formatErrorMessage.setPerformative(ACLMessage.FAILURE);
            formatErrorMessage.setContent(formatErrorMessageContent);

            System.out.println(cfp.getSender());
            //formatErrorMessage.addReceiver(cfp.getSender());

            return super.prepareResponse(formatErrorMessage);
            //return formatErrorMessage;
        }
        //else
        //return null;
    }

    @Override
    protected ACLMessage prepareResponse(ACLMessage cfp) throws NotUnderstoodException, RefuseException {
        return cfp;
    }

    @Override
    protected ACLMessage handleAcceptProposal(ACLMessage cfp, ACLMessage propose, ACLMessage accept) throws FailureException {
        String acceptedVols = cfp.getContent();
        System.out.println("VOLS ACCEPTES : "+ acceptedVols);


        //Construct wrapper --> get vols
        //Do stuff in database
        //update status of vol/avion
        //update capaciteLibre
        ACLMessage validateInformMessage = cfp.createReply();
        validateInformMessage.setPerformative(ACLMessage.INFORM);
        validateInformMessage.setContent(acceptedVols);

        return validateInformMessage;
    }

}
