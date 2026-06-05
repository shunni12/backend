package com.fighting.goaltracker.domain.routine.repository;

import com.fighting.goaltracker.domain.routine.entity.Routine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.DayOfWeek;

import java.util.List;

@Repository
public interface RoutineRepository extends JpaRepository<Routine, Integer> {

    List<Routine> findByUserUserId(Integer userId);

    @Query("SELECT r FROM Routine r JOIN r.repeatDays d WHERE r.user.userId = :userId AND r.isActive = 'true' AND d = :dayOfWeek")
    List<Routine> findActiveRoutinesByDay(@Param("userId") Integer userId, @Param("dayOfWeek") DayOfWeek dayOfWeek);
}
