package com.estafet.taxservice.impl;

import com.estafet.invoicesystem.jpa.dao.api.TaxDAO;
import com.estafet.invoicesystem.jpa.model.Tax;
import com.estafet.taxservice.api.TaxOSGIService;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Angelo Atanasov on 12/10/15.
 */
public class TaxOSGIServiceImpl implements TaxOSGIService {
    private TaxDAO taxDao;

    public void setTaxDao(TaxDAO taxDao) {
        this.taxDao = taxDao;
    }

    @Override
    public BigDecimal getTaxByType(String type) {
        BigDecimal result = null;

        if (taxDao != null) {

            if (type == null) {
                type = "VAT";
            }

            List<Tax> taxes = taxDao.findTaxByName(type);

            if (taxes != null && taxes.size() == 1) {
                result = taxes.get(0).getTaxPercent();
            }
        }
        return result;
    }
}
