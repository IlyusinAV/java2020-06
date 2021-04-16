package ru.otus.crm.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "phone")
public class PhoneDataSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long phone_id;

    @Column(name = "number")
    private String number;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    public PhoneDataSet() {
    }

    public PhoneDataSet(String number) {
        this.phone_id = null;
        this.number = number;
        this.client = new Client();
    }

    public PhoneDataSet(Long id, String number, Client client) {
        this.phone_id = id;
        this.number = number;
        this.client = client;
    }

    public Long getId() {
        return phone_id;
    }

    public void setId(Long id) {
        this.phone_id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneDataSet that = (PhoneDataSet) o;
        return Objects.equals(phone_id, that.phone_id) && Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phone_id, number);
    }

    @Override
    public String toString() {
        return "PhoneDataSet{" +
                "phone_id=" + phone_id +
                ", number='" + number + '\'' +
                '}';
    }
}
