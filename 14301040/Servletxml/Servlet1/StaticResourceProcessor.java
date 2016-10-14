package Servlet1;

import java.io.IOException;

import Servlet1.Request;
import Servlet1.Response;

public class StaticResourceProcessor {
public void process(Request request, Response response) {
try {
    response.sendStaticResource();
}
catch (IOException e) {
e.printStackTrace();
}
}
}
