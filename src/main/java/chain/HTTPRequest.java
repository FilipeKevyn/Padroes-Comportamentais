package chain;

import java.util.*;

// Chain of Responsibility e Command

class HttpRequest {
    private String body;
    private boolean authenticated;
    private boolean authorized;
    private Map<String, String> attributes = new HashMap<>();

    public HttpRequest(String body, boolean authenticated, boolean authorized) {
        this.body = body;
        this.authenticated = authenticated;
        this.authorized = authorized;
    }

    public String getBody() {
        return body;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public boolean isAuthorized() {
        return authorized;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }
}

// Interface para os handlers da cadeia de responsabilidade
interface RequestHandler {
    void setNext(RequestHandler next);
    void handle(HttpRequest request);
}

// Handler para verificação de autenticação
class AuthenticationHandler implements RequestHandler {
    private RequestHandler next;

    @Override
    public void setNext(RequestHandler next) {
        this.next = next;
    }

    @Override
    public void handle(HttpRequest request) {
        if (!request.isAuthenticated()) {
            System.out.println("Autenticação falhou!");
            return;
        }
        System.out.println("Autenticação bem-sucedida.");
        if (next != null) next.handle(request);
    }
}

// Handler para verificação de permissões
class AuthorizationHandler implements RequestHandler {
    private RequestHandler next;

    @Override
    public void setNext(RequestHandler next) {
        this.next = next;
    }

    @Override
    public void handle(HttpRequest request) {
        if (!request.isAuthorized()) {
            System.out.println("Permissão negada!");
            return;
        }
        System.out.println("Permissão concedida.");
        if (next != null) next.handle(request);
    }
}

// Handler para conversão do JSON do corpo da requisição em um Map<String, String>
class JsonParsingHandler implements RequestHandler {
    private RequestHandler next;

    @Override
    public void setNext(RequestHandler next) {
        this.next = next;
    }

    @Override
    public void handle(HttpRequest request) {
        String body = request.getBody();
        Map<String, String> attributes = request.getAttributes();

        body = body.replaceAll("[{}]", ""); // Removendo chaves e aspa
        String[] pairs = body.split(",");
        for (String pair : pairs) {
            String[] keyValue = pair.split(":");
            if (keyValue.length == 2) {
                attributes.put(keyValue[0].trim(), keyValue[1].trim());
            }
        }
        System.out.println("JSON convertido em atributos: " + attributes);

        if (next != null) next.handle(request);
    }
}

// Classe para executar a cadeia de responsabilidade
class RequestProcessor {
    private RequestHandler firstHandler;

    public void setFirstHandler(RequestHandler firstHandler) {
        this.firstHandler = firstHandler;
    }

    public void process(HttpRequest request) {
        if (firstHandler != null) {
            firstHandler.handle(request);
        }
    }
}

