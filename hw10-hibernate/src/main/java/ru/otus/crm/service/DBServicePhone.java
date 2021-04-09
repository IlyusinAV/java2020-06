package ru.otus.crm.service;

import ru.otus.crm.model.PhoneDataSet;

import java.util.List;
import java.util.Optional;

public interface DBServicePhone {

    PhoneDataSet savePhone(PhoneDataSet phone);

    Optional<PhoneDataSet> getPhone(long id);

    List<PhoneDataSet> findAll();
}
