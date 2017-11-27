package behaviors.vols;

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

public class VolManagementBehaviorBIS extends ContractNetResponder {

    private CompagnieContainer compagnieContainer;

    public VolManagementBehaviorBIS(Agent a, MessageTemplate mt) {
        super(a, mt);
    }

    public VolManagementBehaviorBIS(Agent a, MessageTemplate mt, DataStore store) {
        super(a, mt, store);
    }

    public VolManagementBehaviorBIS(Agent a, MessageTemplate mt, CompagnieContainer compagnieContainer) {
        super(a, mt);
        this.compagnieContainer = compagnieContainer;
    }

    @Override
    protected ACLMessage handleCfp(ACLMessage cfp) throws RefuseException, FailureException, NotUnderstoodException {
        //Recevoir le message d'Anne
        GuiEvent guiEvent = new GuiEvent(this, 1);
        guiEvent.addParameter(cfp.getContent());

        //On affiche le message d'Anne
        compagnieContainer.viewMessage(guiEvent);

        //On recupere le JSON
        //On construit un objet
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = "{'name' : 'mkyong'}";

        //On utilise cet objet (ses attributs/champ) pour effectuer la requete / cet objet n'est pas persiste
        //SANA

        //On recupere le resultat de la requete

        //1 On renvoie tous les vols ==> degeulasse

        //JSON from String to Object
        //User user = mapper.readValue(jsonInString, User.class);

        //Retourner des propositions a Anne
        return null;
    }

    /*public VolManagementBehaviorBIS(CompagnieContainer compagnieContainer) {
        super();
        this.compagnieContainer=compagnieContainer;
    }*/

    /*@Override
    public void action() {
        System.out.println("merde");
        ACLMessage aclMessage=myAgent.receive();
        if(aclMessage!=null){
            switch (aclMessage.getPerformative()){
                case ACLMessage.CFP:
                    GuiEvent guiEvent = new GuiEvent(this, 1);
                    guiEvent.addParameter(aclMessage.getContent());
                    compagnieContainer.viewMessage(guiEvent);
                    break;

                case ACLMessage.ACCEPT_PROPOSAL:
                    break;

                default:
                    break;
            }
        }else {
            block();
        }
    }*/
}
