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

    @Column(name = "client_id")
    private Long client_id;

    public PhoneDataSet() {
    }

    public PhoneDataSet(Long id, String number) {
        this.id = id;
        this.number = number;
        this.client_id = null;
    }

    public PhoneDataSet(String number) {
        this.id = null;
        this.number = number;
        this.client_id = null;
    }

    public PhoneDataSet(String number, long client_id) {
        this.id = null;
        this.number = number;
        this.client_id = client_id;
    }

    public Long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public Long getClient_id() {
        return client_id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setClient_id(Long client_id) {
        this.client_id = client_id;
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
                '}';
    }
}
