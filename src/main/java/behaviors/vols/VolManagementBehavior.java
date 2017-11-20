package behaviors.vols;

import containers.CompagnieContainer;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;

public class VolManagementBehavior extends CyclicBehaviour{

    private CompagnieContainer compagnieContainer;


    public VolManagementBehavior(Agent a) {
        super(a);
    }

    @Override
    public void action() {
        ACLMessage aclMessage=super.myAgent.receive();
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
    }
    }
}
