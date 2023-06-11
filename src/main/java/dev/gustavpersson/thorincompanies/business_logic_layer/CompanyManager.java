package dev.gustavpersson.thorincompanies.business_logic_layer;

import dev.gustavpersson.thorincompanies.ThorinCompanies;
import dev.gustavpersson.thorincompanies.data_access_layer.entities.CompanyEntity;
import dev.gustavpersson.thorincompanies.data_access_layer.repositories.CompanyRepository;
import dev.gustavpersson.thorincompanies.business_logic_layer.models.Company;
import org.bukkit.entity.Player;
import org.modelmapper.ModelMapper;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class CompanyManager {

    private final CompanyRepository companyRepository;

    private final ModelMapper modelMapper;

    public CompanyManager(ThorinCompanies plugin) throws SQLException {
        this.companyRepository = new CompanyRepository(plugin);
        this.modelMapper = new ModelMapper();
    }

    public void createCompany(Player founder, String name) throws Exception {

        Company company = new Company();
        company.setName(name);
        company.setFounderUUID(founder.getUniqueId());
        company.setCreatedAt(new Date(System.currentTimeMillis()));

        CompanyEntity companyEntity = modelMapper.map(company, CompanyEntity.class);

        companyRepository.createCompany(companyEntity);
    }

    public List<Company> getAllCompanies() throws SQLException {
        List<CompanyEntity> companyEntities = companyRepository.getAllCompanies();

        return companyEntities.stream()
                .map(entity -> modelMapper.map(entity, Company.class))
                .collect(Collectors.toList());
    }

}
