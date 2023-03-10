package org.example.socket;

import java.util.Objects;

public class RequestLine {
    private final String method;    //Get
    private final String urlPath;   // /calculate?operand1=11&operator=*&operand2=55
    private QueryStrings queryStrings;

    /**
     * GET /calculate?operand1=11&operator=*&operand2=55 HTTP/1.1
     */
    public RequestLine(String s) {
        String[] tokens = s.split(" ");
        this.method = tokens[0];
        String[] urlPathTokens = tokens[1].split("\\?");
        this.urlPath = urlPathTokens[0];

        if(urlPathTokens.length == 2){
            this.queryStrings = new QueryStrings(urlPathTokens[1]);
        }


    }

    public RequestLine(String method, String s, String s1) {
        this.method = method;
        this.urlPath = s;
        this.queryStrings = new QueryStrings(s1);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestLine that = (RequestLine) o;
        return Objects.equals(method, that.method) && Objects.equals(urlPath, that.urlPath) && Objects.equals(queryStrings, that.queryStrings);
    }

    @Override
    public int hashCode() {
        return Objects.hash(method, urlPath, queryStrings);
    }

    public boolean isGetRequest() {
        return "GET".equals(this.method);
    }

    public boolean matchPath(String requestPath) {
        return requestPath.equals(urlPath);
    }

    public QueryStrings getQueryString() {
        return this.queryStrings;
    }
}
