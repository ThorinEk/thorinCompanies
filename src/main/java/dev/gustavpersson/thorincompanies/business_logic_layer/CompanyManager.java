package dev.gustavpersson.thorincompanies.business_logic_layer;

import dev.gustavpersson.thorincompanies.ThorinCompanies;
import dev.gustavpersson.thorincompanies.data_access_layer.repositories.CompanyRepository;
import dev.gustavpersson.thorincompanies.business_logic_layer.models.Company;

import java.sql.Date;

public class CompanyManager {

    private final CompanyRepository companyRepository;

    public CompanyManager(ThorinCompanies plugin) {
        this.companyRepository = new CompanyRepository(plugin);
    }

    public void createCompany(Company company) throws Exception {

        company.setCreatedAt(new Date(System.currentTimeMillis()));

        companyRepository.createCompany(company);
    }

}
