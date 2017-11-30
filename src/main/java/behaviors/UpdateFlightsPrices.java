package behaviors;

import dao.Seeder;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import util.TypeVol;

public class UpdateFlightsPrices extends TickerBehaviour {

    public UpdateFlightsPrices(Agent a, long period) {
        super(a, period);
    }

    @Override
    protected void onTick() {
        //On va regarder la liste des vols charters
        //Seeder.getVols(TypeVol.Charter, )

        //On va regarder la date courante
        //On regarde la date de depart
        //on fait la formule magique pour l'update

    }
}



