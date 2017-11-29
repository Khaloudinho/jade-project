package agents;

import behaviors.RegisterAgentBehavior;
import behaviors.VolManagementBehavior;
import behaviors.vols.VolManagementBehaviorCyclic;
import behaviors.RegisterAgentBehavior;
import behaviors.VolManagementBehavior;
import containers.CompagnieContainer;
import jade.core.AID;
import jade.core.behaviours.ParallelBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.wrapper.ControllerException;

public class CompagnieCharterAgent extends GuiAgent implements Compagnie {

    private CompagnieContainer compagnieContainer;


    @Override
    protected void setup(){
        compagnieContainer= (CompagnieContainer) getArguments()[0];
        compagnieContainer.setCompagnieCharterAgent(this);
        System.out.println("Initialisation de l'agent "+this.getAID().getName());

        ParallelBehaviour parallelBehaviour = new ParallelBehaviour();
        parallelBehaviour.addSubBehaviour(new RegisterAgentBehavior("compagnie", "Vols-Association"));
        //parallelBehaviour.addSubBehaviour(new VolManagementBehavior(this, null, compagnieContainer));
        parallelBehaviour.addSubBehaviour(new VolManagementBehaviorCyclic(compagnieContainer));

        addBehaviour(parallelBehaviour);

    }

    @Override
    protected void takeDown(){
        System.out.println("Destruction de l'agent");
    }

    @Override
    protected void beforeMove() {
        try {
            System.out.println("Avant migration ... du container "+this.getContainerController().getContainerName());
        } catch (ControllerException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void afterMove() {
        try {
            System.out.println("Apres migration ..."+this.getContainerController().getContainerName());
        } catch (ControllerException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onGuiEvent(GuiEvent guiEvent) {
        ACLMessage aclMessage = new ACLMessage(ACLMessage.REQUEST);
        String livre = guiEvent.getParameter(0).toString();
        aclMessage.setContent(livre);
        aclMessage.addReceiver(new AID("compagniecharter", AID.ISLOCALNAME));
        send(aclMessage);
    }
}
