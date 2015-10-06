package com.estafet.taxservice.webservice;

/**
 * Created by Angelo Atanasov on 06/10/15.
 */
public class TaxServiceEndpointImpl implements TaxServiceEndpoint {

    public String reportIncident(String in) {
        // just log and return a fixed response
        System.out.println("\n\n\nInvoked real web service: body=" + in);
        return in;
    }

}
