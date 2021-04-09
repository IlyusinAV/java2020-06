package ru.otus.crm.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "phone")
public class PhoneDataSet {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "number")
    private String number;

    @ManyToOne(targetEntity = Client.class, cascade = CascadeType.REFRESH)
    private Client client;

    public PhoneDataSet() {
    }

    public PhoneDataSet(Long id, String number, Client client) {
        this.id = id;
        this.number = number;
        this.client = client;
    }

    public PhoneDataSet(String number, Client client) {
        this.id = null;
        this.number = number;
        this.client = client;
    }

    @Override
    public PhoneDataSet clone() {
        return new PhoneDataSet(this.id, this.number, this.client);
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public Client getClient() {
        return client;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhoneDataSet that = (PhoneDataSet) o;
        return Objects.equals(id, that.id) && Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, number);
    }

    @Override
    public String toString() {
        return "PhoneDataSet{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", client=" + client +
                '}';
    }
}
