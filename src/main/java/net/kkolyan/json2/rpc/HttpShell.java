package net.kkolyan.json2.rpc;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.net.URL;

/**
 * @author nplekhanov
 */
public class HttpShell implements Shell {
    private URL serverUrl;
    private HttpClient httpClient;

    public HttpShell() {
        httpClient = new HttpClient();
        httpClient.getParams().setIntParameter(HttpMethodParams.BUFFER_WARN_TRIGGER_LIMIT, 1000000);
    }

    @Override
    public void execute(Reader in, Appendable out) throws IOException {
        StringReader reader = (StringReader) in;

        PostMethod method = new PostMethod(serverUrl.toString());
        method.setRequestEntity(new StringRequestEntity(reader.toString(), "text/javascript", "utf8"));

        int statusCode = httpClient.executeMethod(method);

        int codeType = statusCode / 100;
        String response = method.getResponseBodyAsString();

        method.releaseConnection();

        switch (codeType) {
            case 2:
                out.append(response);
                return;
            case 3:
                throw new IllegalStateException(
                        "Unexpected Redirection (" + statusCode + " " + method.getStatusText() + ") to " + method.getResponseHeader("Location"));
            case 4:
                throw new IllegalStateException(
                        "Client Error (" + statusCode + " " + method.getStatusText() + "): " + response);
            case 5:
                throw new IllegalStateException(
                        "Server Error (" + statusCode + " " + method.getStatusText() + "): " + response);
            default:
                throw new IllegalStateException(
                        "Unexpected response status (" + statusCode + " " + method.getStatusText() + "): " + response);
        }
    }

    public void setServerUrl(URL serverUrl) {
        this.serverUrl = serverUrl;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }
}
