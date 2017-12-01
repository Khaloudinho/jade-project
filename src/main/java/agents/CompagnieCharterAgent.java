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
        logger.info("Initialisation de l'agent : "+this.getName());

        RegisterAgentBehavior registerAgentBehavior = new RegisterAgentBehavior(this,"compagnie", "Vols-Association");
        this.addBehaviour(registerAgentBehavior);

        //VolManagementBehavior volManagementBehavior = new VolManagementBehavior(this, null);
        //this.addBehaviour(volManagementBehavior);

        VolManagementBehaviorCyclic volManagementBehaviorCyclic = new VolManagementBehaviorCyclic(this);
        this.addBehaviour(volManagementBehaviorCyclic);
    }

    @Override
    protected void takeDown(){
        logger.info("Destruction de l'agent : "+this.getName());
    }

}
