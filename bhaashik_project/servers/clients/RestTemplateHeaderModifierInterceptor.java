/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bhaashik.servers.clients;

import java.io.IOException;
import java.util.Collections;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

/**
 *
 * @author User
 */
public class RestTemplateHeaderModifierInterceptor implements ClientHttpRequestInterceptor {
    
    BhaashikSpringRestClient bhaashikSpringRestClient;
    
    public RestTemplateHeaderModifierInterceptor(BhaashikSpringRestClient bhaashikSpringRestClient)
    {
        this.bhaashikSpringRestClient = bhaashikSpringRestClient;
    }

    @Override
    public ClientHttpResponse intercept(
            HttpRequest request,
            byte[] body,
            ClientHttpRequestExecution execution) throws IOException {

        ClientHttpResponse response = execution.execute(request, body);
        
        System.err.println("HTTP status: " + response.getStatusCode());
        
        if (response.getStatusCode() == HttpStatus.FORBIDDEN
                || response.getStatusCode() == HttpStatus.UNAUTHORIZED) {
            
            bhaashikSpringRestClient.refreshToken();

            System.out.println("Refresh token called");
//            System.exit(1);
            
            HttpHeaders headers = request.getHeaders();
            
            headers.replace("Authorization", Collections.singletonList("Bearer " + bhaashikSpringRestClient.getAccessToken()));
            return execution.execute(request, body);
        }
        
        return response;
//        ClientHttpResponse response = execution.execute(request, body);
//        response.getHeaders().add("Foo", "bar");
//        return response;
    }
}
