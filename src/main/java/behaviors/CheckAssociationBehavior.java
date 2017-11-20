package behaviors;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

public class CheckAssociationBehavior extends TickerBehaviour {

    private AID[] listAssociations;


    public CheckAssociationBehavior(Agent a, long period) {
        super(a, period);
    }

    @Override
    protected void onTick() {
        DFAgentDescription description = new DFAgentDescription();
        ServiceDescription serviceDescription = new ServiceDescription();
        serviceDescription.setType("Association");
        description.addServices(serviceDescription);
        try {
            DFAgentDescription[] agentDescriptions= DFService.search(super.myAgent, description);
            listAssociations=new AID[agentDescriptions.length];
            for (int i=0;i<agentDescriptions.length;i++){
                listAssociations[i]=agentDescriptions[i].getName();
            }
        } catch (FIPAException e) {
            e.printStackTrace();
        }
        //serviceDescription.setName("Vente-Livre");
    }
}
