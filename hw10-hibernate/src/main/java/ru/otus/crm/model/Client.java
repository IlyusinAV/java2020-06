package ru.otus.crm.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "client")
public class Client implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(targetEntity = AddressDataSet.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private AddressDataSet address;

    @OneToMany(targetEntity = PhoneDataSet.class, cascade = CascadeType.ALL, mappedBy = "client", fetch = FetchType.LAZY)
    private List<PhoneDataSet> phones = new ArrayList<>();

    public Client() {
    }

    public Client(String name, AddressDataSet address) {
        this.id = null;
        this.name = name;
        this.address = address;
    }

    public Client(Long id, String name, AddressDataSet address, List<PhoneDataSet> phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phones = phones;
    }

    @Override
    public Client clone() {
        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AddressDataSet getAddress() {
        return address;
    }

    public void setAddress(AddressDataSet address) {
        this.address = address;
    }

    public List<PhoneDataSet> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneDataSet> phones) {
        this.phones = phones;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", phones=" + phones +
                '}';
    }
}
