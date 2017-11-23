package behaviors.vols;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

public class RegisterVolBehavior extends OneShotBehaviour{

    public RegisterVolBehavior() {
        super();
    }

    @Override
    public void action() {
        System.out.println("*************************************************");
        DFAgentDescription dfa=new DFAgentDescription();
        dfa.setName(super.myAgent.getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("Vols");
        sd.setName("Vols-Associations");
        dfa.addServices(sd);
        try {
            DFService.register(myAgent, dfa);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }
}
