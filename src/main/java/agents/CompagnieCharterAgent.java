package agents;

import behaviors.RegisterAgentBehavior;
import behaviors.VolManagementBehavior;
import behaviors.vols.VolManagementBehaviorCyclic;
import containers.CompagnieContainer;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.ParallelBehaviour;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import jade.wrapper.ControllerException;

public class CompagnieCharterAgent extends Agent implements Compagnie {

    public static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CompagnieCharterAgent.class);

    @Override
    protected void setup(){
        logger.info("Initialisation de l'agent :"+this.getAID().getName());

        //ParallelBehaviour parallelBehaviour = new ParallelBehaviour();

        RegisterAgentBehavior registerAgentBehavior = new RegisterAgentBehavior(this,"compagnie", "Vols-Association");
        //parallelBehaviour.addSubBehaviour(registerAgentBehavior);
        this.addBehaviour(registerAgentBehavior);
        //parallelBehaviour.addSubBehaviour(new VolManagementBehavior(this, null, compagnieContainer));

        VolManagementBehaviorCyclic volManagementBehaviorCyclic = new VolManagementBehaviorCyclic(this);
        //parallelBehaviour.addSubBehaviour(volManagementBehaviorCyclic);
        this.addBehaviour(volManagementBehaviorCyclic);

        //this.addBehaviour(parallelBehaviour);

    }

    @Override
    protected void takeDown(){
        logger.info("Destruction de l'agent : "+this.getName());
    }

}
