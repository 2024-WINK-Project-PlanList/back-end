package kr.ac.kookmin.wink.planlist.shared.calendar;

import kr.ac.kookmin.wink.planlist.shared.calendar.domain.SharedCalendar;
import kr.ac.kookmin.wink.planlist.shared.calendar.dto.CreateSharedCalendarRequestDTO;
import kr.ac.kookmin.wink.planlist.shared.calendar.dto.SharedCalendarResponseDTO;
import kr.ac.kookmin.wink.planlist.shared.calendar.dto.UpdateSharedCalendarRequestDTO;
import kr.ac.kookmin.wink.planlist.shared.calendar.repository.UserSharedCalendarRepository;
import kr.ac.kookmin.wink.planlist.shared.calendar.repository.SharedCalendarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SharedCalendarService {

    private final SharedCalendarRepository sharedCalendarRepository;
    private final UserSharedCalendarRepository userSharedCalendarRepository;

    public List<SharedCalendarResponseDTO> getMySharedCalendars() {

        return null;
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

    }

    public void deleteSharedCalendar(Long calendarId) {

    }

    public SharedCalendarResponseDTO getSharedCalendar(Long calendarId) {

        return null;
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
