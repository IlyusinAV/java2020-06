package ru.otus.crm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.repository.DataTemplate;
import ru.otus.crm.model.PhoneDataSet;
import ru.otus.core.sessionmanager.TransactionManager;

import java.util.List;
import java.util.Optional;

public class DbServicePhoneImpl implements DBServicePhone {
    private static final Logger log = LoggerFactory.getLogger(DbServicePhoneImpl.class);

    private final DataTemplate<PhoneDataSet> phoneDataTemplate;
    private final TransactionManager transactionManager;

    public DbServicePhoneImpl(TransactionManager transactionManager, DataTemplate<PhoneDataSet> phoneDataTemplate) {
        this.transactionManager = transactionManager;
        this.phoneDataTemplate = phoneDataTemplate;
    }

    @Override
    public PhoneDataSet savePhone(PhoneDataSet phone) {
        return transactionManager.doInTransaction(session -> {
            var phoneCloned = phone.clone();
            if (phone.getId() == null) {
                phoneDataTemplate.insert(session, phoneCloned);
                log.info("created phone: {}", phoneCloned);
                return phoneCloned;
            }
            phoneDataTemplate.update(session, phoneCloned);
            log.info("updated phone: {}", phoneCloned);
            return phoneCloned;
        });
    }

    @Override
    public Optional<PhoneDataSet> getPhone(long id) {
        return transactionManager.doInTransaction(session -> {
            var phoneOptional = phoneDataTemplate.findById(session, id);
            log.info("phone: {}", phoneOptional);
            return phoneOptional;
        });
    }

    @Override
    public List<PhoneDataSet> findAll() {
        return transactionManager.doInTransaction(session -> {
            var phoneList = phoneDataTemplate.findAll(session);
            log.info("phoneList:{}", phoneList);
            return phoneList;
       });
    }
}
