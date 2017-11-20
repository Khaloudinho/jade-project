package containers;

import agents.Compagnie;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.gui.GuiEvent;
import jade.wrapper.AgentContainer;
import javafx.application.Application;
import javafx.stage.Stage;


public class CompagnieContainer extends Application {

    private AgentContainer compagnieContainer;


    public void startContainer() {
        try {

            Runtime runtime = Runtime.instance();
            Profile profile = new ProfileImpl(false);
            profile.setParameter(Profile.MAIN_HOST, "127.0.0.1");
            compagnieContainer = runtime.createAgentContainer(profile);
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
    }

    public static void main(String[] args) {
        launch(CompagnieContainer.class);
    }

}
