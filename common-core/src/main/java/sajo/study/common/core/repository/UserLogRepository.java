package sajo.study.common.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sajo.study.common.core.model.UserLog;

public interface UserLogRepository extends JpaRepository<UserLog, Long> {
}
