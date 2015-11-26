package com.estafet.invoicesystem.jpa.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by Miroslava Stancheva on 18/11/15.
 */
@XmlRootElement(name = "company", namespace = "http://companyservice.estafet.com/")
@XmlType
@Entity
@Table(name = "COMPANY")
//@NamedQueries({@NamedQuery(name="company.findCompanyByName", query = "SELECT com FROM Company AS com WHERE com.companyName = :companyName")})
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMPANY_ID")
    private Integer companyId;

    @Column(name = "COMPANY_NAME", unique=true)
    private String companyName;

    @Column(name = "COMPANY_ADDRESS")
    private String address;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
