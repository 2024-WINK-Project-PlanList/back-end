package kr.ac.kookmin.wink.planlist.shared.calendar;

import jakarta.transaction.Transactional;
import kr.ac.kookmin.wink.planlist.global.exception.CustomException;
import kr.ac.kookmin.wink.planlist.global.security.SecurityUser;
import kr.ac.kookmin.wink.planlist.shared.calendar.domain.SharedCalendar;
import kr.ac.kookmin.wink.planlist.shared.calendar.domain.UserSharedCalendar;
import kr.ac.kookmin.wink.planlist.shared.calendar.domain.UserSharedCalendarId;
import kr.ac.kookmin.wink.planlist.shared.calendar.dto.CreateSharedCalendarRequestDTO;
import kr.ac.kookmin.wink.planlist.shared.calendar.dto.InviteSharedCalendarDTO;
import kr.ac.kookmin.wink.planlist.shared.calendar.dto.SharedCalendarResponseDTO;
import kr.ac.kookmin.wink.planlist.shared.calendar.dto.UpdateSharedCalendarRequestDTO;
import kr.ac.kookmin.wink.planlist.shared.calendar.repository.UserSharedCalendarRepository;
import kr.ac.kookmin.wink.planlist.shared.calendar.repository.SharedCalendarRepository;
import kr.ac.kookmin.wink.planlist.shared.exeption.SharedError;
import kr.ac.kookmin.wink.planlist.user.domain.User;
import kr.ac.kookmin.wink.planlist.user.dto.response.UserDTO;
import kr.ac.kookmin.wink.planlist.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class SharedCalendarService {

    private final SharedCalendarRepository sharedCalendarRepository;
    private final UserSharedCalendarRepository userSharedCalendarRepository;
    private final UserRepository userRepository;

    public List<SharedCalendarResponseDTO> getMySharedCalendars(SecurityUser securityUser) {
        User user = securityUser.getUser();
        List<UserSharedCalendar> calendars = userSharedCalendarRepository.findAllByUser(user);
        if (calendars.isEmpty()) {
            return new ArrayList<>();
        }

        List<SharedCalendarResponseDTO> response = new ArrayList<>();
        for (UserSharedCalendar calendar : calendars) {
            if (calendar.getInvitationStatus() == 1) {
                response.add(SharedCalendarResponseDTO.from(calendar.getSharedCalendar()));
            }
        }
        return response;
    }

    public void createSharedCalendar(CreateSharedCalendarRequestDTO createSharedCalendarRequestDTO, SecurityUser securityUser) {
        SharedCalendar sharedCalendar = SharedCalendar.builder()
                .name(createSharedCalendarRequestDTO.getName())
                .description(createSharedCalendarRequestDTO.getDescription())
                .imageBase64(createSharedCalendarRequestDTO.getImageBase64())
                .build();
        sharedCalendarRepository.save(sharedCalendar);

        UserSharedCalendar userSharedCalendar = UserSharedCalendar.builder()
                .user(securityUser.getUser())
                .sharedCalendar(sharedCalendar)
                .invitationStatus(1)
                .createdDate(LocalDate.now())
                .build();
        userSharedCalendarRepository.save(userSharedCalendar);

        if (createSharedCalendarRequestDTO.getMembersToInvite() == null) {
            return;
        }

        for (UserDTO userDTO : createSharedCalendarRequestDTO.getMembersToInvite()) {
            User user = userRepository.findById(userDTO.getId()).get();
            UserSharedCalendar userSharedCalendar1 = UserSharedCalendar.builder()
                    .user(user)
                    .sharedCalendar(sharedCalendar)
                    .invitationStatus(0)
                    .createdDate(LocalDate.now())
                    .build();
            userSharedCalendarRepository.save(userSharedCalendar1);
        }
    }

    public void updateSharedCalendar(Long calendarId, UpdateSharedCalendarRequestDTO updateSharedCalendarRequestDTO) {
        SharedCalendar sharedCalendar = sharedCalendarRepository.findById(calendarId)
                .orElseThrow(() -> new CustomException(SharedError.INVALID_CALENDAR_ID));
        sharedCalendar.update(updateSharedCalendarRequestDTO);
        sharedCalendarRepository.save(sharedCalendar);
    }

    @Transactional
    public void deleteSharedCalendar(Long calendarId) {
        SharedCalendar sharedCalendar = sharedCalendarRepository.findById(calendarId)
                        .orElseThrow(() -> new CustomException(SharedError.INVALID_CALENDAR_ID));
        userSharedCalendarRepository.deleteAllBySharedCalendar(sharedCalendar);
        if (userSharedCalendarRepository.existsBySharedCalendar(sharedCalendar)) {
            throw new CustomException(SharedError.CALENDAR_DELETE_FAILED);
        }

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

    public void invite(InviteSharedCalendarDTO inviteSharedCalendarDTO) {
        if (inviteSharedCalendarDTO.getInvitingUsers().isEmpty()) {
            return;
        }
        SharedCalendar sharedCalendar = sharedCalendarRepository.findById(inviteSharedCalendarDTO.getCalendarId())
                .orElseThrow(() -> new CustomException(SharedError.INVALID_CALENDAR_ID));

        for (UserDTO userDTO : inviteSharedCalendarDTO.getInvitingUsers()) {
            User user = userRepository.findById(userDTO.getId()).get();
            UserSharedCalendar userSharedCalendar = UserSharedCalendar.builder()
                    .sharedCalendar(sharedCalendar)
                    .user(user)
                    .invitationStatus(0)
                    .createdDate(LocalDate.now())
                    .build();
            userSharedCalendarRepository.save(userSharedCalendar);
        }
    }

    public void reject(Long calendarId, SecurityUser securityUser) {
        SharedCalendar sharedCalendar = sharedCalendarRepository.findById(calendarId)
                .orElseThrow(() -> new CustomException(SharedError.INVALID_CALENDAR_ID));
        List<UserSharedCalendar> allBySharedCalendar = userSharedCalendarRepository.findAllBySharedCalendar(sharedCalendar);
        for (UserSharedCalendar userSharedCalendar : allBySharedCalendar) {
            if (Objects.equals(userSharedCalendar.getUser().getId(), securityUser.getUser().getId())) {
                if (userSharedCalendar.getInvitationStatus() == 1) {
                    throw new CustomException(SharedError.ALREADY_ACCEPTED);
                }
                userSharedCalendarRepository.delete(userSharedCalendar);
                return;
            }
        }
        throw new CustomException(SharedError.INVITE_REJECT_FAILED);


    }

    public void join(Long calendarId, SecurityUser securityUser) {
        SharedCalendar sharedCalendar = sharedCalendarRepository.findById(calendarId)
                .orElseThrow(() -> new CustomException(SharedError.INVALID_CALENDAR_ID));

        List<UserSharedCalendar> allBySharedCalendar = userSharedCalendarRepository.findAllBySharedCalendar(sharedCalendar);

        for (UserSharedCalendar userSharedCalendar : allBySharedCalendar) {
            if (userSharedCalendar.getUser().getId().equals(securityUser.getUser().getId())) {
                if (userSharedCalendar.getInvitationStatus() == 0) {
                    userSharedCalendar.setInvitationStatus(1);
                    userSharedCalendarRepository.save(userSharedCalendar);
                    return;
                } else {
                    throw new CustomException(SharedError.ALREADY_ACCEPTED);
                }
            }
        }
    }

    public void leave(Long calendarId, SecurityUser securityUser) {
        SharedCalendar sharedCalendar = sharedCalendarRepository.findById(calendarId)
                .orElseThrow(() -> new CustomException(SharedError.INVALID_CALENDAR_ID));

        List<UserSharedCalendar> allBySharedCalendar = userSharedCalendarRepository.findAllBySharedCalendar(sharedCalendar);

        for (UserSharedCalendar userSharedCalendar : allBySharedCalendar) {
            if (userSharedCalendar.getUser().getId().equals(securityUser.getUser().getId())) {
                userSharedCalendarRepository.delete(userSharedCalendar);
                return;
            }
        }
        throw new CustomException(SharedError.LEAVE_FAILED);
    }

    public List<SharedCalendarResponseDTO> invitedSharedCalendarList(SecurityUser securityUser) {
        List<UserSharedCalendar> allByUser = userSharedCalendarRepository.findAllByUser(securityUser.getUser());
        List<SharedCalendarResponseDTO> response = new ArrayList<>();
        for (UserSharedCalendar userSharedCalendar : allByUser) {
            if (userSharedCalendar.getInvitationStatus() == 0) {
                SharedCalendarResponseDTO dto = SharedCalendarResponseDTO.from(userSharedCalendar.getSharedCalendar());
                response.add(dto);
            }
        }
        return response;
    }
}
