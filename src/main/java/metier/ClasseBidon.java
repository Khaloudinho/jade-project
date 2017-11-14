package metier;

import java.util.Iterator;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class ClasseBidon extends Agent{

    protected void setup(){
        searchAgents();
    }

    private void searchAgents(){
        int i;
        DFAgentDescription dfd = new DFAgentDescription();

        try {
            DFAgentDescription[] result = DFService.search(this, dfd);
            for(i = 0; i < result.length; i++){
                DFAgentDescription desc = (DFAgentDescription)result[i];
                String out = desc.getName() + " provide";
                Iterator iter2 = desc.getAllServices();
                while(iter2.hasNext()){
                    ServiceDescription sd = (ServiceDescription)iter2.next();
                    out += " " + sd.getName();
                }
                System.out.println(this.getLocalName() + " : " + out);
            }
        } catch (FIPAException e) {
            e.printStackTrace();
            doDelete();
        }
    }

}
