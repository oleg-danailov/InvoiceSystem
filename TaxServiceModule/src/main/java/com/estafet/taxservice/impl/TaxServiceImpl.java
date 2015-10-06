package com.estafet.taxservice.impl;

import com.estafet.taxservice.api.TaxService;

/**
 * Created by Angelo Atanasov on 06/10/15.
 */
public class TaxServiceImpl implements TaxService {

    public String echo(String text) {
        System.out.println();
        return "echo " + text;
    }

}
