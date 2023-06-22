package com.sahno.repository;

import com.sahno.model.entity.business.DashboardRes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface DashboardResRepo extends JpaRepository<DashboardRes, Long> {
    @Query(nativeQuery = true, value = "DELETE FROM dashboard_res where dashboard_id = ?")
    @Modifying
    void clear(Long id);
}
