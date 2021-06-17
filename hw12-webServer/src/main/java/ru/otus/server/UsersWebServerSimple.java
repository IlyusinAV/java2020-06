package ru.otus.server;

import com.google.gson.Gson;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.crm.service.DbServiceClient;
import ru.otus.dao.ClientDao;
import ru.otus.dao.UserDao;
import ru.otus.helpers.FileSystemHelper;
import ru.otus.services.TemplateProcessor;
import ru.otus.services.UserAuthService;
import ru.otus.servlet.*;

import java.util.Arrays;


public class UsersWebServerSimple implements UsersWebServer {
    private static final String START_PAGE_NAME = "index.html";
    private static final String COMMON_RESOURCES_DIR = "static";

    private final UserAuthService userAuthService;
    private final UserDao userDao;
    private final Gson gson;
    protected final TemplateProcessor templateProcessor;
    private final Server server;
    private final ClientDao clientDao;
    private final DbServiceClient dbServiceClient;

    public UsersWebServerSimple(int port, UserAuthService userAuthService, UserDao userDao, Gson gson, TemplateProcessor templateProcessor, ClientDao clientDao, DbServiceClient dbServiceClient) {
        this.userAuthService = userAuthService;
        this.userDao = userDao;
        this.gson = gson;
        this.templateProcessor = templateProcessor;
        server = new Server(port);
        this.clientDao = clientDao;
        this.dbServiceClient = dbServiceClient;
    }

    @Override
    public void start() throws Exception {
        if (server.getHandlers().length == 0) {
            initContext();
        }
        server.start();
    }

    @Override
    public void join() throws Exception {
        server.join();
    }

    @Override
    public void stop() throws Exception {
        server.stop();
    }

    protected Handler applySecurity(ServletContextHandler servletContextHandler, String... paths) {
        servletContextHandler.addServlet(new ServletHolder(new LoginServlet(templateProcessor, userAuthService)), "/login");
        AuthorizationFilter authorizationFilter = new AuthorizationFilter();
        Arrays.stream(paths).forEachOrdered(path -> servletContextHandler.addFilter(new FilterHolder(authorizationFilter), path, null));
        return servletContextHandler;
    }

    private Server initContext() {

        ResourceHandler resourceHandler = createResourceHandler();
        ServletContextHandler servletContextHandler = createServletContextHandler();

        HandlerList handlers = new HandlerList();
        handlers.addHandler(resourceHandler);
        handlers.addHandler(applySecurity(servletContextHandler, "/users", "/api/user/*", "/clients", "/save"));

        server.setHandler(handlers);
        return server;
    }

    private ResourceHandler createResourceHandler() {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(false);
        resourceHandler.setWelcomeFiles(new String[]{START_PAGE_NAME});
        resourceHandler.setResourceBase(FileSystemHelper.localFileNameOrResourceNameToFullPath(COMMON_RESOURCES_DIR));
        return resourceHandler;
    }

    private ServletContextHandler createServletContextHandler() {
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.addServlet(new ServletHolder(new UsersServlet(templateProcessor, userDao)), "/users");
        servletContextHandler.addServlet(new ServletHolder(new UsersApiServlet(userDao, gson)), "/api/user/*");
        servletContextHandler.addServlet(new ServletHolder(new ClientsServlet(templateProcessor, clientDao)), "/clients");
        servletContextHandler.addServlet(new ServletHolder(new ClientSaveServlet(templateProcessor, dbServiceClient, clientDao)), "/save");
        return servletContextHandler;
    }
}