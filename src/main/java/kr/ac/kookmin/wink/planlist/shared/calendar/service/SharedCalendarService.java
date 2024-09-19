package kr.ac.kookmin.wink.planlist.shared.calendar.service;

import kr.ac.kookmin.wink.planlist.global.exception.CustomException;
import kr.ac.kookmin.wink.planlist.global.s3.S3Service;
import kr.ac.kookmin.wink.planlist.notification.aop.Notify;
import kr.ac.kookmin.wink.planlist.notification.domain.NotificationMessage;
import kr.ac.kookmin.wink.planlist.shared.calendar.domain.SharedCalendar;
import kr.ac.kookmin.wink.planlist.shared.calendar.domain.UserSharedCalendar;
import kr.ac.kookmin.wink.planlist.shared.calendar.dto.request.CreateSharedCalendarRequestDTO;
import kr.ac.kookmin.wink.planlist.shared.calendar.dto.request.InviteSharedCalendarRequestDTO;
import kr.ac.kookmin.wink.planlist.shared.calendar.dto.request.UpdateSharedCalendarRequestDTO;
import kr.ac.kookmin.wink.planlist.shared.calendar.dto.response.SharedCalendarResponseDTO;
import kr.ac.kookmin.wink.planlist.shared.calendar.repository.SharedCalendarRepository;
import kr.ac.kookmin.wink.planlist.shared.calendar.repository.UserSharedCalendarRepository;
import kr.ac.kookmin.wink.planlist.shared.exeption.SharedErrorCode;
import kr.ac.kookmin.wink.planlist.user.domain.User;
import kr.ac.kookmin.wink.planlist.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class SharedCalendarService {

    private final SharedCalendarRepository sharedCalendarRepository;
    private final UserSharedCalendarRepository userSharedCalendarRepository;
    private final UserService userService;
    private final S3Service s3Service;

    public boolean existsById(Long id) {
        return sharedCalendarRepository.existsById(id);
    }

    public SharedCalendar findById(Long id) {
        return sharedCalendarRepository.findById(id)
                .orElseThrow(() -> new CustomException(SharedErrorCode.INVALID_CALENDAR_ID));
    }

    public List<SharedCalendarResponseDTO> getMySharedCalendars(User user) {
        return userSharedCalendarRepository.findAllByUser(user)
                .stream()
                .filter(UserSharedCalendar::isInvitationStatus)
                .map(userSharedCalendar ->
                        SharedCalendarResponseDTO.create(userSharedCalendar.getSharedCalendar())
                )
                .toList();
    }

    @Notify(NotificationMessage.CALENDAR_INVITATION)
    @Transactional
    public List<UserSharedCalendar> createSharedCalendar(CreateSharedCalendarRequestDTO requestDTO, MultipartFile image, User user) {
        SharedCalendar sharedCalendar = SharedCalendar.builder()
                .name(requestDTO.getName())
                .description(requestDTO.getDescription())
                .build();

        String randomName = UUID.randomUUID().toString();

        if (image != null && !image.isEmpty()) {
            String imagePath = s3Service.uploadImageFile(image, "profile/calendar/" + randomName);

            sharedCalendar.setCalendarImagePath(imagePath);
        }

        SharedCalendar newSharedCalendar = sharedCalendarRepository.save(sharedCalendar);

        UserSharedCalendar userSharedCalendar = UserSharedCalendar.builder()
                .user(user)
                .sharedCalendar(newSharedCalendar)
                .invitationStatus(true)
                .build();

        userSharedCalendarRepository.save(userSharedCalendar);

        List<Long> membersToInvite = requestDTO.getMembersToInvite();

        return membersToInvite
                .stream()
                .map(userService::findUserById)
                .map((toInvite) -> createUserSharedCalendar(toInvite, newSharedCalendar))
                .toList();
    }

    @Transactional
    public UserSharedCalendar createUserSharedCalendar(User user, SharedCalendar sharedCalendar) {
        UserSharedCalendar userSharedCalendar1 = UserSharedCalendar.builder()
                .user(user)
                .sharedCalendar(sharedCalendar)
                .invitationStatus(false)
                .build();

        return userSharedCalendarRepository.save(userSharedCalendar1);
    }

    @Transactional
    public void updateSharedCalendar(Long calendarId, UpdateSharedCalendarRequestDTO updateSharedCalendarRequestDTO) {
        SharedCalendar sharedCalendar = sharedCalendarRepository.findById(calendarId)
                .orElseThrow(() -> new CustomException(SharedErrorCode.INVALID_CALENDAR_ID));

        sharedCalendar.update(updateSharedCalendarRequestDTO);
        sharedCalendarRepository.save(sharedCalendar);
    }

    @Transactional
    public void deleteSharedCalendar(Long calendarId) {
        SharedCalendar sharedCalendar = sharedCalendarRepository.findById(calendarId)
                .orElseThrow(() -> new CustomException(SharedErrorCode.INVALID_CALENDAR_ID));

        userSharedCalendarRepository.deleteAllBySharedCalendar(sharedCalendar);
        if (userSharedCalendarRepository.existsBySharedCalendar(sharedCalendar)) {
            throw new CustomException(SharedErrorCode.CALENDAR_DELETE_FAILED);
        }

        sharedCalendarRepository.deleteById(calendarId);
        if (sharedCalendarRepository.existsById(calendarId)) {
            throw new CustomException(SharedErrorCode.CALENDAR_DELETE_FAILED);
        }
    }

    public SharedCalendarResponseDTO getSharedCalendar(Long calendarId) {
        SharedCalendar sharedCalendar = sharedCalendarRepository.findById(calendarId)
                .orElseThrow(() -> new CustomException(SharedErrorCode.INVALID_CALENDAR_ID));

        return SharedCalendarResponseDTO.create(sharedCalendar);
    }

    @Notify(NotificationMessage.CALENDAR_INVITATION)
    @Transactional
    public List<UserSharedCalendar> invite(InviteSharedCalendarRequestDTO requestDTO) {
        List<Long> invitingUsers = requestDTO.getInvitingUsers();

        SharedCalendar sharedCalendar = sharedCalendarRepository.findById(requestDTO.getCalendarId())
                .orElseThrow(() -> new CustomException(SharedErrorCode.INVALID_CALENDAR_ID));

        return invitingUsers
                .stream()
                .map(userService::findUserById)
                .map((user) -> createUserSharedCalendar(user, sharedCalendar))
                .toList();
    }

    @Transactional
    public void reject(Long calendarId, User user) {
        SharedCalendar sharedCalendar = sharedCalendarRepository.findById(calendarId)
                .orElseThrow(() -> new CustomException(SharedErrorCode.INVALID_CALENDAR_ID));

        List<UserSharedCalendar> allBySharedCalendar = userSharedCalendarRepository.findAllBySharedCalendar(sharedCalendar);

        for (UserSharedCalendar userSharedCalendar : allBySharedCalendar) {
            if (Objects.equals(userSharedCalendar.getUser().getId(), user.getId())) {
                if (userSharedCalendar.isInvitationStatus()) {
                    throw new CustomException(SharedErrorCode.ALREADY_ACCEPTED);
                }
                userSharedCalendarRepository.delete(userSharedCalendar);
                return;
            }
        }

        throw new CustomException(SharedErrorCode.INVITE_REJECT_FAILED);
    }



    @Notify(NotificationMessage.CALENDAR_ACCEPTED)
    @Transactional
    public UserSharedCalendar join(Long calendarId, User user) {
        SharedCalendar sharedCalendar = sharedCalendarRepository.findById(calendarId)
                .orElseThrow(() -> new CustomException(SharedErrorCode.INVALID_CALENDAR_ID));

        List<UserSharedCalendar> allBySharedCalendar = userSharedCalendarRepository.findAllBySharedCalendar(sharedCalendar);

        for (UserSharedCalendar userSharedCalendar : allBySharedCalendar) {
            if (userSharedCalendar.getUser().getId().equals(user.getId())) {
                if (!userSharedCalendar.isInvitationStatus()) {
                    userSharedCalendar.setInvitationStatus(true);
                    return userSharedCalendar;
                } else {
                    throw new CustomException(SharedErrorCode.ALREADY_ACCEPTED);
                }
            }
        }

        return null;
    }

    @Transactional
    public void leave(Long calendarId, User user) {
        SharedCalendar sharedCalendar = sharedCalendarRepository.findById(calendarId)
                .orElseThrow(() -> new CustomException(SharedErrorCode.INVALID_CALENDAR_ID));

        List<UserSharedCalendar> allBySharedCalendar = userSharedCalendarRepository.findAllBySharedCalendar(sharedCalendar);

        for (UserSharedCalendar userSharedCalendar : allBySharedCalendar) {
            if (userSharedCalendar.getUser().getId().equals(user.getId())) {
                userSharedCalendarRepository.delete(userSharedCalendar);
                return;
            }
        }
        throw new CustomException(SharedErrorCode.LEAVE_FAILED);
    }

    public List<SharedCalendarResponseDTO> invitedSharedCalendarList(User user) {
        List<UserSharedCalendar> allByUser = userSharedCalendarRepository.findAllByUser(user);
        List<SharedCalendarResponseDTO> response = new ArrayList<>();
        for (UserSharedCalendar userSharedCalendar : allByUser) {
            if (!userSharedCalendar.isInvitationStatus()) {
                SharedCalendarResponseDTO dto = SharedCalendarResponseDTO.create(userSharedCalendar.getSharedCalendar());
                response.add(dto);
            }
        }
        return response;
    }
}
