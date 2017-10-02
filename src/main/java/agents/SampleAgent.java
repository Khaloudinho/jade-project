package agents;

import jade.core.Agent;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class SampleAgent extends Agent {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(SampleAgent.class);

    @Override
    public void setup() {
        System.out.println("Hello my name is " + getLocalName());
        // final String otherAgentName = (String) this.getArguments()[0];
        // addBehaviour(new IncrementBaseNumber(this, otherAgentName));
    }

    @Override
    public void takeDown() {
    }
}