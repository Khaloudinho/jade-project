package messages;

import jade.core.Agent;
import jade.core.behaviours.DataStore;
import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetResponder;

public class VolResponder extends ContractNetResponder {
    public VolResponder(Agent a, MessageTemplate mt) {
        super(a, mt);
    }

    public VolResponder(Agent a, MessageTemplate mt, DataStore store) {
        super(a, mt, store);
    }
}
