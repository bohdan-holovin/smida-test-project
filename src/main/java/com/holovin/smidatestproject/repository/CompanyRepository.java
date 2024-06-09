package com.holovin.smidatestproject.repository;

import com.holovin.smidatestproject.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {
}
