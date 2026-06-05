package com.fighting.goaltracker.domain.record.service;

import com.fighting.goaltracker.domain.record.entity.RoutineRecord;
import com.fighting.goaltracker.domain.record.repository.RoutineRecordRepository;
import com.fighting.goaltracker.domain.routine.entity.Routine;
import com.fighting.goaltracker.domain.routine.repository.RoutineRepository;
import com.fighting.goaltracker.domain.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RoutineRecordService {

    @Autowired
    private RoutineRecordRepository routineRecordRepository;

    @Autowired
    private RoutineRepository routineRepository;

    @Transactional
    public String toggleRoutineCheck(Integer userId, Integer routineId, String dateStr) {
        LocalDate recordDate = LocalDate.parse(dateStr);

        Optional<RoutineRecord> existingRecord = routineRecordRepository
                .findByRoutineRoutineIdAndRecordDate(routineId, recordDate);

        if (existingRecord.isPresent()) {
            routineRecordRepository.delete(existingRecord.get());
            return "체크 해제 완료 (기록 삭제)";
        } else {
            Routine routine = routineRepository.findById(routineId)
                    .orElseThrow(() -> new RuntimeException("해당 루틴을 찾을 수 없습니다."));

            User user = routine.getUser();

            RoutineRecord record = new RoutineRecord();
            record.setRoutine(routine);
            record.setUser(user);
            record.setRecordDate(recordDate);
            record.setIsCompleted("true");
            record.setCompletedAt(LocalDateTime.now());

            routineRecordRepository.save(record);
            return "체크 완료 (기록 생성)";
        }
    }

    @Transactional(readOnly = true)
    public List<RoutineRecord> getRecordsByDate(Integer userId, String dateStr) {
        LocalDate recordDate = LocalDate.parse(dateStr);
        return routineRecordRepository.findByUserUserIdAndRecordDate(userId, recordDate);
    }
}
