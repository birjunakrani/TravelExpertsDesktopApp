package TravelExperts.Agent.Model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Agent {

    private SimpleIntegerProperty AgentId;
    private SimpleIntegerProperty AgencyId;
    private SimpleStringProperty AgtBusPhone;
    private SimpleStringProperty AgtEmail;
    private SimpleStringProperty AgtFirstName;
    private SimpleStringProperty AgtLastName;
    private SimpleStringProperty AgtMiddleInital;
    private SimpleStringProperty AgtPosition;

    public Agent(Integer agentId, String agtFirstName, String agtMiddleInital, String agtLastName,
                 String agtBusPhone, String agtEmail, String agtPosition, Integer agencyId) {
        AgentId = new SimpleIntegerProperty(agentId);
        AgtFirstName = new SimpleStringProperty(agtFirstName);
        AgtMiddleInital = new SimpleStringProperty(agtMiddleInital);
        AgtLastName = new SimpleStringProperty(agtLastName);
        AgtBusPhone = new SimpleStringProperty(agtBusPhone);
        AgtEmail = new SimpleStringProperty(agtEmail);
        AgtPosition = new SimpleStringProperty(agtPosition);
        AgencyId = new SimpleIntegerProperty(agencyId);
    }

    public int getAgentId() {
        return AgentId.get();
    }

    public SimpleIntegerProperty agentIdProperty() {
        return AgentId;
    }

    public void setAgentId(Integer agentId) {
        this.AgentId.set(agentId);
    }

    public int getAgencyId() {
        return AgencyId.get();
    }

    public SimpleIntegerProperty agencyIdProperty() {
        return AgencyId;
    }

    public void setAgencyId(Integer agencyId) {
        this.AgencyId.set(agencyId);
    }

    public String getAgtBusPhone() {
        return AgtBusPhone.get();
    }

    public SimpleStringProperty agtBusPhoneProperty() {
        return AgtBusPhone;
    }

    public void setAgtBusPhone(String agtBusPhone) {
        this.AgtBusPhone.set(agtBusPhone);
    }

    public String getAgtEmail() {
        return AgtEmail.get();
    }

    public SimpleStringProperty agtEmailProperty() {
        return AgtEmail;
    }

    public void setAgtEmail(String agtEmail) {
        this.AgtEmail.set(agtEmail);
    }

    public String getAgtFirstName() {
        return AgtFirstName.get();
    }

    public SimpleStringProperty agtFirstNameProperty() {
        return AgtFirstName;
    }

    public void setAgtFirstName(String agtFirstName) {
        this.AgtFirstName.set(agtFirstName);
    }

    public String getAgtLastName() {
        return AgtLastName.get();
    }

    public SimpleStringProperty agtLastNameProperty() {
        return AgtLastName;
    }

    public void setAgtLastName(String agtLastName) {
        this.AgtLastName.set(agtLastName);
    }

    public String getAgtMiddleInital() {
        return AgtMiddleInital.get();
    }

    public SimpleStringProperty agtMiddleInitalProperty() {
        return AgtMiddleInital;
    }

    public void setAgtMiddleInital(String agtMiddleInital) {
        this.AgtMiddleInital.set(agtMiddleInital);
    }

    public String getAgtPosition() {
        return AgtPosition.get();
    }

    public SimpleStringProperty agtPositionProperty() {
        return AgtPosition;
    }

    public void setAgtPosition(String agtPosition) {
        this.AgtPosition.set(agtPosition);
    }


    @Override
    public String toString() {
        return getAgtFirstName() + " " + getAgtLastName() ;
    }
}
