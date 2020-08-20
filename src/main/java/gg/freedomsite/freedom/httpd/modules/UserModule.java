package gg.freedomsite.freedom.httpd.modules;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import gg.freedomsite.freedom.Freedom;
import gg.freedomsite.freedom.player.FPlayer;
import gg.freedomsite.freedom.utils.FreedomUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.bukkit.Bukkit;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class UserModule implements HttpHandler {


    @Override
    public void handle(HttpExchange httpExchange) throws IOException
    {
        String requestParamValue = null;
        if ("GET".equalsIgnoreCase(httpExchange.getRequestMethod()))
        {
            requestParamValue = handleGetRequest(httpExchange);
        }

        if (requestParamValue == null)
        {
            handleRequest(httpExchange);
        } else {
            handleRequest(httpExchange, requestParamValue);
        }
    }

    public String handleGetRequest(HttpExchange exchange)
    {
        return exchange
                .getRequestURI()
                .toString()
                .split("\\?")[1]
                .split("=")[1];
    }

    public String getValue(HttpExchange exchange)
    {
        return exchange
                .getRequestURI()
                .toString()
                .split("\\?")[1]
                .split("=")[0];
    }

    public void handleRequest(HttpExchange exchange, String requestParamValue) throws IOException {
        OutputStream outputStream = exchange.getResponseBody();
        StringBuilder htmlBuilder = new StringBuilder();

        Bukkit.getLogger().info(requestParamValue);
        String uuid = FreedomUtils.getUUID(requestParamValue);
        Bukkit.getLogger().info(uuid);
        Bukkit.getLogger().info(UUID.fromString(uuid).toString());
        FPlayer fPlayer = Freedom.get().getPlayerData().getData(UUID.fromString(uuid));
        Gson gson = new Gson();
        String json = gson.toJson(fPlayer);

        Bukkit.getLogger().info(getValue(exchange));
        htmlBuilder.append("<html>")
                .append("<body>")
                .append(new JSONObject(json).toString(4))
                .append("</body>")
                .append("</html>");

        String htmlResponse = StringEscapeUtils.escapeHtml(htmlBuilder.toString());

        htmlResponse = htmlResponse.replaceAll("&lt;", "<");
        htmlResponse = htmlResponse.replaceAll("&gt;", ">");

        exchange.sendResponseHeaders(200, htmlResponse.length());
        outputStream.write(htmlResponse.getBytes());
        outputStream.flush();
        outputStream.close();
    }
    public void handleRequest(HttpExchange exchange) throws IOException {
        OutputStream outputStream = exchange.getResponseBody();
        StringBuilder htmlBuilder = new StringBuilder();

        htmlBuilder.append("<html>")
                .append("<body>")
                .append("<h1>")
                .append("TAAHH")
                .append("</h1>")
                .append("</body>")
                .append("</html>");

        String htmlResponse = StringEscapeUtils.escapeHtml(htmlBuilder.toString());

        htmlResponse = htmlResponse.replaceAll("&lt;", "<");
        htmlResponse = htmlResponse.replaceAll("&gt;", ">");

        exchange.sendResponseHeaders(200, htmlResponse.length());
        outputStream.write(htmlResponse.getBytes());
        outputStream.flush();
        outputStream.close();
    }
}
