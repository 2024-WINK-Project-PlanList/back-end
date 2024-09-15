package kr.ac.kookmin.wink.planlist.shared.calendar;

import kr.ac.kookmin.wink.planlist.global.exception.CustomException;
import kr.ac.kookmin.wink.planlist.global.security.SecurityUser;
import kr.ac.kookmin.wink.planlist.shared.calendar.domain.SharedCalendar;
import kr.ac.kookmin.wink.planlist.shared.calendar.domain.UserSharedCalendar;
import kr.ac.kookmin.wink.planlist.shared.calendar.dto.CreateSharedCalendarRequestDTO;
import kr.ac.kookmin.wink.planlist.shared.calendar.dto.SharedCalendarResponseDTO;
import kr.ac.kookmin.wink.planlist.shared.calendar.dto.UpdateSharedCalendarRequestDTO;
import kr.ac.kookmin.wink.planlist.shared.calendar.repository.UserSharedCalendarRepository;
import kr.ac.kookmin.wink.planlist.shared.calendar.repository.SharedCalendarRepository;
import kr.ac.kookmin.wink.planlist.shared.exeption.SharedError;
import kr.ac.kookmin.wink.planlist.user.domain.User;
import kr.ac.kookmin.wink.planlist.user.dto.response.UserInfoResponseDTO;
import kr.ac.kookmin.wink.planlist.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SharedCalendarService {

    private final SharedCalendarRepository sharedCalendarRepository;
    private final UserSharedCalendarRepository userSharedCalendarRepository;

    public List<SharedCalendarResponseDTO> getMySharedCalendars(SecurityUser securityUser) {
        User user = securityUser.getUser();
        List<UserSharedCalendar> calendars = userSharedCalendarRepository.findAllByUser(user);
        if (calendars.isEmpty()) {
            return new ArrayList<>();
        }

        List<SharedCalendarResponseDTO> response = new ArrayList<>();
        for (UserSharedCalendar calendar : calendars) {
            response.add(SharedCalendarResponseDTO.from(calendar.getSharedCalendar()));
        }
        return response;
    }

    public void createSharedCalendar(CreateSharedCalendarRequestDTO createSharedCalendarRequestDTO) {
        SharedCalendar sharedCalendar = SharedCalendar.builder()
                .name(createSharedCalendarRequestDTO.getName())
                .description(createSharedCalendarRequestDTO.getDescription())
                .imageBase64(createSharedCalendarRequestDTO.getImageBase64())
                .build();
        sharedCalendarRepository.save(sharedCalendar);
    }

    public void updateSharedCalendar(Long calendarId, UpdateSharedCalendarRequestDTO updateSharedCalendarRequestDTO) {
        SharedCalendar sharedCalendar = sharedCalendarRepository.findById(calendarId)
                .orElseThrow(() -> new CustomException(SharedError.INVALID_CALENDAR_ID));
        sharedCalendar.update(updateSharedCalendarRequestDTO);
        sharedCalendarRepository.save(sharedCalendar);
    }

    public void deleteSharedCalendar(Long calendarId) {
        sharedCalendarRepository.deleteById(calendarId);
        if (sharedCalendarRepository.existsById(calendarId)) {
            throw new CustomException(SharedError.CALENDAR_DELETE_FAILED);
        }
    }

    public SharedCalendarResponseDTO getSharedCalendar(Long calendarId) {
        SharedCalendar sharedCalendar = sharedCalendarRepository.findById(calendarId)
                .orElseThrow(() -> new CustomException(SharedError.INVALID_CALENDAR_ID));
        return SharedCalendarResponseDTO.from(sharedCalendar);
    }

    public void invite() {

    }

    public void reject(Long inviteId) {

    }

    public void join() {

    }

    public void leave(Long calendarId) {

    }

}
