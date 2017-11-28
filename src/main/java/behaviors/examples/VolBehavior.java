package behaviors.examples;

import containers.CompagnieContainer;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class VolBehavior extends CyclicBehaviour {
    private CompagnieContainer compagnieContainer;

    private AID[] listVendeurs;

    public VolBehavior(Agent a) {
        super(a);
    }

    @Override
    public void action() {
        MessageTemplate messageTemplate = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
        ACLMessage message = super.myAgent.receive(messageTemplate);
        if(message!= null){
            System.out.println("Reception d'un message"+message.getContent());
            GuiEvent guiEvent = new GuiEvent(this, 1);
            String nomLivre = message.getContent();
            guiEvent.addParameter(nomLivre);
            compagnieContainer.viewMessage(guiEvent);

            ACLMessage aclMessage=new ACLMessage(ACLMessage.CFP);
            aclMessage.setContent(nomLivre);
            for (AID aid:listVendeurs){
                aclMessage.addReceiver(aid);
            }
            super.myAgent.send(aclMessage);
        }
    }
}
