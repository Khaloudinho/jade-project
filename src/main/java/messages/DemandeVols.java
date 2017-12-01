package messages;

import java.text.ParseException;

public class DemandeVols {

    private String pays;
    private String date;
    private Integer volume;

    public DemandeVols() {
    }

    public DemandeVols(String pays, String date, Integer volume) throws ParseException {
        this.pays = pays;
        this.date = date;
        this.volume = volume;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "DemandeVols{" +
                "pays='" + pays + '\'' +
                ", date=" + date +
                ", volume=" + volume +
                '}';
    }
}
