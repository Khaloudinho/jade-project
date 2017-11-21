package containers;

import agents.Compagnie;
import agents.CompagnieCharterAgent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.gui.GuiEvent;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import javafx.application.Application;
import javafx.stage.Stage;


public class CompagnieContainer extends Application {

    private AgentContainer agentContainer;
    private CompagnieContainer compagnieContainer;
    private CompagnieCharterAgent compagnieAgent;

    public void startContainer() {
        try {

            Runtime runtime = Runtime.instance();
            Profile profile = new ProfileImpl(false);
            profile.setParameter(Profile.MAIN_HOST, "127.0.0.1");
            agentContainer = runtime.createAgentContainer(profile);

            AgentController agentController = agentContainer.createNewAgent("CompagnieCharter","agents.CompagnieCharterAgent",new Object[]{this});
            agentController.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void viewMessage(GuiEvent guiEvent) {
        String message = guiEvent.getParameter(0).toString();
        System.out.println("COMPAGNIE CONTAINER : " + message);
        //observableList.add(message);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        startContainer();
        //compagnieContainer=this;
    }

    public AgentContainer getAgentContainer() {
        return agentContainer;
    }

    public void setAgentContainer(AgentContainer agentContainer) {
        this.agentContainer = agentContainer;
    }

    public CompagnieContainer getCompagnieContainer() {
        return compagnieContainer;
    }

    public void setCompagnieContainer(CompagnieContainer compagnieContainer) {
        this.compagnieContainer = compagnieContainer;
    }

    public CompagnieCharterAgent getCompagnieAgent() {
        return compagnieAgent;
    }

    public void setCompagnieAgent(CompagnieCharterAgent compagnieAgent) {
        this.compagnieAgent = compagnieAgent;
    }

    public static void main(String[] args) {
        launch(CompagnieContainer.class);
    }

}
