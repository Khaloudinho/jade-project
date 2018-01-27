package agents;

import behaviors.EnregistrerService;
import behaviors.VolManagementBehavior;
import jade.core.Agent;

public class CompagnieCharterAgent extends Agent {

  public static final org.slf4j.Logger logger = org.slf4j.LoggerFactory
      .getLogger(CompagnieCharterAgent.class);

  private static final String SERVICE_TYPE = "compagnie";
  private static final String SERVICE_NAME = "Vols-Association";

  @Override
  protected void setup() {
    logger.info("Initialisation de l'agent : " + this.getName());

    EnregistrerService rs = new EnregistrerService(this, SERVICE_NAME, SERVICE_TYPE);
    this.addBehaviour(rs);

    VolManagementBehavior volManagementBehavior = new VolManagementBehavior(this, null);
    this.addBehaviour(volManagementBehavior);

  }

  @Override
  protected void takeDown() {
    logger.info("agent down");
  }

}
