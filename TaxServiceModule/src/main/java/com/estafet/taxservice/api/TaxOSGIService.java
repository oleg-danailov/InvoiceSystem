package com.estafet.taxservice.api;

import java.math.BigDecimal;

/**
 * Created by Angelo Atanasov on 12/10/15.
 */
public interface TaxOSGIService {

    public BigDecimal getTaxByType(String type);

}
