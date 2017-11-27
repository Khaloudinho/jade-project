package behaviors.vols;

import containers.CompagnieContainer;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.DataStore;
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
