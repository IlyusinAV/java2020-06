package ru.otus.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.otus.crm.model.AddressDataSet;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.PhoneDataSet;
import ru.otus.crm.service.DbServiceClient;
import ru.otus.dao.ClientDao;
import ru.otus.services.TemplateProcessor;

import java.io.IOException;
import java.util.List;

public class ClientSaveServlet extends HttpServlet {
    private static final String PARAM_NAME = "firstName";
    private static final String PARAM_ADDRESS = "address";
    private static final String PARAM_PHONE = "phone";
    private static final int MAX_INACTIVE_INTERVAL = 30;

    private final TemplateProcessor templateProcessor;
    private final DbServiceClient dbServiceClient;
    private final ClientDao clientDao;

    public ClientSaveServlet(TemplateProcessor templateProcessor, DbServiceClient dbServiceClient, ClientDao clientDao) {
        this.templateProcessor = templateProcessor;
        this.dbServiceClient = dbServiceClient;
        this.clientDao = clientDao;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String name = request.getParameter(PARAM_NAME);
        String address = request.getParameter(PARAM_ADDRESS);
        String phone = request.getParameter(PARAM_PHONE);

        var client = new Client(name, new AddressDataSet(address));
        var phones = List.of(new PhoneDataSet(phone, client));
        client.setPhones(phones);

        var clientSaved = dbServiceClient.saveClient(client);
        clientDao.addClient(clientSaved);

        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(MAX_INACTIVE_INTERVAL);
        response.sendRedirect("/clients");
    }
}
