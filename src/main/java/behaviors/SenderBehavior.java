package behaviors;

import impl.AvionImpl;
import impl.AeroportImpl;
import jade.core.Agent;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import metier.Vol;
import util.TypeVol;

import java.io.IOException;
import java.sql.Date;
import java.util.Iterator;

public class SenderBehavior extends SimpleBehaviour{

    private boolean jobdone = false;


    public SenderBehavior() {
    }

    public SenderBehavior(Agent a) {
        super(a);
    }

    @Override
    public void action() {
        // Preparer le message
        //TO DO REQUEST IN ORDER TO GET VOLS
        Date dateDepart = Date.valueOf("2017-01-01");
        Date dateArrivee = Date.valueOf("2017-01-01");

        AeroportImpl ae = new AeroportImpl();
        AvionImpl ai = new AvionImpl();

        Vol v1 = new Vol(dateDepart, dateArrivee, TypeVol.Charter,
                ai.getAvionParImmatriculation("GB2-398-WYR"),
                ae.getLieuParVille("Douala"));

        //String message = "Coucou, voila l'objet dont je te parlais.";
        ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);
        aclMessage.setSender(super.myAgent.getAID());
        //aclMessage.setContent(message);
        try {
            aclMessage.setContentObject(v1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Rechercher les agents a qui envoyer le message
        DFAgentDescription dfd = new DFAgentDescription();
        try {
            DFAgentDescription[] result = DFService.search(super.myAgent, dfd);
            //
            for (int i= 0; i<result.length; i++) {
                DFAgentDescription desc = (DFAgentDescription) result[i];
                Iterator iter = desc.getAllServices();
                while (iter.hasNext()) {
                    ServiceDescription sd = (ServiceDescription)iter.next();
                    if (sd.getName().equals("vols")){
                        aclMessage.addReceiver(desc.getName());
                        super.myAgent.send(aclMessage);
                    }
                }
            }
            this.jobdone = true;
        }
        catch (Exception fe) {
            System.err.println(super.myAgent.getLocalName() + " search with DF is not succeeded because of " + fe.getMessage());
            super.myAgent.doDelete();
        }
    }

    @Override
    public boolean done() {
        return this.jobdone;
    }
}
