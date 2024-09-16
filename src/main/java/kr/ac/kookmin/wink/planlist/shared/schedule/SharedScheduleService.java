package kr.ac.kookmin.wink.planlist.shared.schedule;

import kr.ac.kookmin.wink.planlist.global.exception.CustomException;
import kr.ac.kookmin.wink.planlist.shared.calendar.domain.SharedCalendar;
import kr.ac.kookmin.wink.planlist.shared.calendar.repository.SharedCalendarRepository;
import kr.ac.kookmin.wink.planlist.shared.exeption.SharedErrorCode;
import kr.ac.kookmin.wink.planlist.shared.schedule.domain.SharedSchedule;
import kr.ac.kookmin.wink.planlist.shared.schedule.domain.UserSharedSchedule;
import kr.ac.kookmin.wink.planlist.shared.schedule.dto.SharedScheduleRequestDTO;
import kr.ac.kookmin.wink.planlist.shared.schedule.dto.SharedScheduleResponseDTO;
import kr.ac.kookmin.wink.planlist.shared.schedule.repository.UserSharedScheduleRepository;
import kr.ac.kookmin.wink.planlist.shared.schedule.repository.SharedScheduleRepository;
import kr.ac.kookmin.wink.planlist.user.domain.User;
import kr.ac.kookmin.wink.planlist.user.dto.response.UserDTO;
import kr.ac.kookmin.wink.planlist.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class SharedScheduleService {

    private final SharedScheduleRepository sharedScheduleRepository;
    private final UserSharedScheduleRepository userSharedScheduleRepository;
    private final SharedCalendarRepository sharedCalendarRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createSharedSchedule(SharedScheduleRequestDTO requestDTO) {
        SharedCalendar sharedCalendar = sharedCalendarRepository.findById(requestDTO.getCalendarId())
                .orElseThrow(() -> new CustomException(SharedErrorCode.INVALID_CALENDAR_ID));

        SharedSchedule sharedSchedule = SharedSchedule.create(requestDTO, sharedCalendar);
        sharedScheduleRepository.save(sharedSchedule);

        List<UserSharedSchedule> list = requestDTO.getScheduleMembers().stream()
                .map(id -> userRepository.findById(id).get())
                .toList().stream()
                .map(user -> userSharedScheduleRepository.save(new UserSharedSchedule(user, sharedSchedule)))
                .toList();
    }

    public List<SharedScheduleResponseDTO> getAllSharedSchedule(Long calendarId) {
        SharedCalendar sharedCalendar = sharedCalendarRepository.findById(calendarId)
                .orElseThrow(() -> new CustomException(SharedErrorCode.INVALID_CALENDAR_ID));

        List<SharedSchedule> sharedScheduleList = sharedScheduleRepository.findAllBySharedCalendar(sharedCalendar);

        if (sharedScheduleList.isEmpty()) {
            return new ArrayList<>();
        }
        return sharedScheduleList.stream()
                .map(sharedSchedule ->
                            SharedScheduleResponseDTO.convertToDTO(
                                    sharedSchedule,
                                    userSharedScheduleRepository.findAllBySharedSchedule(sharedSchedule).stream()
                                            .map(userSharedSchedule -> UserDTO.create(userSharedSchedule.getUser()))
                                            .toList()
                            )
                )
                .toList();

    }

    @Transactional
    public void updateSharedSchedule(Long scheduleId, SharedScheduleRequestDTO requestDTO) {
        SharedSchedule sharedSchedule = sharedScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new CustomException(SharedErrorCode.INVALID_SCHEDULE_ID));

        SharedCalendar sharedCalendar = sharedCalendarRepository.findById(requestDTO.getCalendarId())
                .orElseThrow(() -> new CustomException(SharedErrorCode.INVALID_CALENDAR_ID));

        List<Long> scheduleMemberIdList = userSharedScheduleRepository.findAllBySharedSchedule(sharedSchedule).stream()
                .map(userSharedSchedule -> userSharedSchedule.getUser().getId())
                .toList();

        // todo: 유저-일정 관계 테이블 업데이트 해내야만해
        List<Long> list = requestDTO.getScheduleMembers().stream()
                .peek(id -> {
                    if (!scheduleMemberIdList.contains(id)) {
                        User user = userRepository.findById(id).get();
                        userSharedScheduleRepository.save(new UserSharedSchedule(user, sharedSchedule));

                    }
                })
                .toList();
        List<Long> list1 = scheduleMemberIdList.stream()
                .peek(id -> {
                    if (!requestDTO.getScheduleMembers().contains(id)) {
                        userSharedScheduleRepository.deleteByUser(userRepository.findById(id).get());
                    }
                })
                .toList();

        //일정 내용 업데이트
        sharedSchedule.update(requestDTO, sharedCalendar);
        sharedScheduleRepository.save(sharedSchedule);

    }

    @Transactional
    public void deleteSharedSchedule(Long scheduleId) {
        SharedSchedule sharedSchedule = sharedScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new CustomException(SharedErrorCode.INVALID_SCHEDULE_ID));

        userSharedScheduleRepository.deleteAllBySharedSchedule(sharedSchedule);
        sharedScheduleRepository.delete(sharedSchedule);


    }


}
