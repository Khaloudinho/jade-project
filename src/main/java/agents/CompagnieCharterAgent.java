package agents;

import behaviors.VolManagementBehavior;
import jade.core.Agent;
import util.EnregistrementService;

public class CompagnieCharterAgent extends Agent {

    public static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(CompagnieCharterAgent.class);

    private static final String SERVICE_TYPE = "compagnie";
    private static final String SERVICE_NAME = "Vols-Association";
    @Override
    protected void setup(){
        logger.info("Initialisation de l'agent : "+this.getName());

        EnregistrementService.enregistrer(this,SERVICE_TYPE, SERVICE_NAME);
        //EnregistrementService.enregistrer(this,SERVICE_NAME, SERVICE_NAME);

        VolManagementBehavior volManagementBehavior = new VolManagementBehavior(this, null);
        this.addBehaviour(volManagementBehavior);

    }

    @Override
    protected void takeDown(){
        EnregistrementService.quitter(this, SERVICE_TYPE, SERVICE_NAME);
        logger.info("Destruction de l'agent : "+this.getName());
    }

}
