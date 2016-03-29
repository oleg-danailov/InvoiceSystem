package com.estafet.invoiceservice.model;

import javax.ws.rs.*;
import javax.xml.ws.Response;

/**
 * Created by Angelo Atanasov on 09/12/15.
 */
@Path("/invoice_service/")
public class InvoiceService {

    @GET
    @Path("/invoice/{id}/")
    public String getInvoiceById(@PathParam("id") String id){
        return null;
    }

    @POST
    @Path("/invoice")
    @Consumes("application/json")
    @Produces("application/json")
    public Response createTax(String payload){
        return null;
    }
}