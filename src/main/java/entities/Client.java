package entities;

import java.util.Date;

public class Client {

    private Long clientId;

    private String firstName;

    private String lastName;

    private String email;

    private Date creationDate;

    private Boolean available;
    

    public Client(Long clientId, String firstName, String lastName, String email, Date creationDate, Boolean available) {
        this.clientId = clientId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.creationDate = creationDate;
        this.available = available;
    }

    public Client(String firstName, String lastName, String email, Date creationDate, Boolean available) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.creationDate = creationDate;
        this.available = available;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Client{" + "clientId=" + clientId + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", available=" + available + '}';
    }

    
    
}
