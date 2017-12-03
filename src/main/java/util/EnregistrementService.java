package util;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

public class EnregistrementService {

    public static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(EnregistrementService.class);


    public static boolean enregistrer(Agent agent, String serviceName, String serviceType) {
        boolean registerState = false;

        logger.info("*************************************************");
        DFAgentDescription dfa = new DFAgentDescription();
        dfa.setName(agent.getAID());

        ServiceDescription sd = new ServiceDescription();
        sd.setType(serviceType);
        sd.setName(serviceName);

        dfa.addServices(sd);

        try {
            DFService.register(agent, dfa);
            logger.info("Agent: " + serviceName + " de type: " + serviceType + " enregistre");
            registerState = true;
        } catch (FIPAException e) {
            e.printStackTrace();

            logger.error(agent.getLocalName() + " erreur lors de l'enregistrement", e);

            agent.doDelete();
        }
        return registerState;
    }

    public static boolean quitter(Agent agent, String serviceType, String serviceName) {
        boolean unregisterState = false;

        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(agent.getAID());

        ServiceDescription sd = new ServiceDescription();
        sd.setType(serviceType);
        sd.setName(serviceName);

        dfd.addServices(sd);
        try {
            DFService.deregister(agent, dfd);
            System.out.println("--------" + agent + " est desincrit " + serviceType + "/" + serviceName);
            unregisterState = true;
        } catch (FIPAException e) {
            System.err.println(agent.getLocalName() + " registration with DF unsucceeded. Reason: " + e.getMessage());
            agent.doDelete();
        }
        return unregisterState;
    }
}
