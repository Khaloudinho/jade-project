package agents;

import behaviors.RegisterAgentBehavior;
import behaviors.vols.VolManagementBehaviorCyclic;
import jade.core.Agent;
import util.EnregistrementService;

public class CompagnieCharterAgent extends Agent implements Compagnie {

    public static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CompagnieCharterAgent.class);

    private static final String SERVICE_TYPE = "compagnie";
    private static final String SERVICE_NAME = "Vols-Association";
    @Override
    protected void setup(){
        logger.info("Initialisation de l'agent : "+this.getName());

        //EnregistrementService.enregistrer(this,SERVICE_TYPE, SERVICE_NAME);

        RegisterAgentBehavior registerAgentBehavior = new RegisterAgentBehavior(this,"compagnie", "Vols-Association");
        //parallelBehaviour.addSubBehaviour(registerAgentBehavior);
        this.addBehaviour(registerAgentBehavior);


        //VolManagementBehavior volManagementBehavior = new VolManagementBehavior(this, null);
        //this.addBehaviour(volManagementBehavior);

        VolManagementBehaviorCyclic volManagementBehaviorCyclic = new VolManagementBehaviorCyclic(this);
        this.addBehaviour(volManagementBehaviorCyclic);
    }

    @Override
    protected void takeDown(){
        EnregistrementService.quitter(this, SERVICE_TYPE, SERVICE_NAME);
        logger.info("Destruction de l'agent : "+this.getName());
    }

}
