package behaviors.vols;

import containers.CompagnieContainer;
import jade.core.behaviours.CyclicBehaviour;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;

public class VolManagementBehavior extends CyclicBehaviour{

    private CompagnieContainer compagnieContainer;


    public VolManagementBehavior(CompagnieContainer compagnieContainer) {
        super();
        this.compagnieContainer=compagnieContainer;
    }

    @Override
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
    }
}
