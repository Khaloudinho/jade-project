package agents;

import containers.CompagnieContainer;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.wrapper.ControllerException;

public class CompagnieCharterAgent extends GuiAgent implements Compagnie {

    private CompagnieContainer compagnieContainer;


    @Override
    protected void setup(){
        compagnieContainer= (CompagnieContainer) getArguments()[0];
        compagnieContainer.setCompagnieAgent(this);
        System.out.println("Initialisation de l'agent "+this.getAID().getName());
        ParallelBehaviour parallelBehaviour = new ParallelBehaviour();
        addBehaviour(parallelBehaviour);
        parallelBehaviour.addSubBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                System.out.println("*************************************************");
                DFAgentDescription dfa=new DFAgentDescription();
                dfa.setName(getAID());
                ServiceDescription sd = new ServiceDescription();
                sd.setType("Vols");
                sd.setName("Vols-associations");
                dfa.addServices(sd);
                try {
                    DFService.register(myAgent, dfa);
                } catch (FIPAException e) {
                    e.printStackTrace();
                }
            }
        });

        parallelBehaviour.addSubBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage aclMessage=receive();
                if(aclMessage!=null){
                    switch (aclMessage.getPerformative()){
                        case ACLMessage.CFP:

                            System.out.println("Onthology message : "+aclMessage.getOntology());

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
        });
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
