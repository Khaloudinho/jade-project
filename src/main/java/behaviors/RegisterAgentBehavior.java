package behaviors;

import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

public class RegisterAgentBehavior extends OneShotBehaviour{

    private String serviceType;
    private String serviceName;

    public RegisterAgentBehavior(String serviceType, String serviceName) {
        super();
        this.serviceName = serviceName;
        this.serviceType = serviceType;
    }

    //Attention fait planter avec plusieurs agents
    @Override
    public void action() {
        System.out.println("*************************************************");
        DFAgentDescription dfa = new DFAgentDescription();
        dfa.setName(super.myAgent.getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType(serviceType);
        sd.setName(serviceName);
        dfa.addServices(sd);
        try {
            DFService.register(super.myAgent, dfa);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }
}
