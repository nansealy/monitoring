package com.sahno.repository;

import com.sahno.model.entity.business.Dashboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DashboardRepo  extends JpaRepository<Dashboard, Long> {
}
