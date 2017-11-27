package messages;

import jade.core.Agent;
import jade.core.behaviours.DataStore;
import jade.lang.acl.ACLMessage;
import jade.proto.ContractNetInitiator;

public class VolInitiator extends ContractNetInitiator {
    public VolInitiator(Agent a, ACLMessage cfp) {
        super(a, cfp);
    }

    public VolInitiator(Agent a, ACLMessage cfp, DataStore store) {
        super(a, cfp, store);
    }
}
