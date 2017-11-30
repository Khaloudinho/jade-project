package behaviors;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import util.Logger;

public class RegisterAgentBehavior extends OneShotBehaviour{

    public static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(RegisterAgentBehavior.class);

    private String serviceType;
    private String serviceName;

    public RegisterAgentBehavior(Agent agent, String serviceType, String serviceName) {
        super(agent);
        this.serviceName = serviceName;
        this.serviceType = serviceType;
    }

    //Attention fait planter avec plusieurs agents
    @Override
    public void action() {
        Logger.logger.info("*************************************************");
        DFAgentDescription dfa = new DFAgentDescription();
        dfa.setName(super.myAgent.getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType(this.serviceType);
        sd.setName(this.serviceName);
        dfa.addServices(sd);
        try {
            DFService.register(super.myAgent, dfa);
            logger.info("Agent: " + this.serviceName + " de type: " + this.serviceType + " enregistre");
        } catch (FIPAException e) {
            e.printStackTrace();
            logger.error(super.myAgent.getLocalName() + " erreur lors de l'enregistrement", e);
            super.myAgent.doDelete();
        }
    }
}
