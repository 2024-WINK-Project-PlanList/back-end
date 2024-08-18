package kr.ac.kookmin.wink.planlist.friend.controller;

import kr.ac.kookmin.wink.planlist.friend.dto.request.CreateFriendshipRequestDTO;
import kr.ac.kookmin.wink.planlist.friend.dto.response.UserFriendsResponseDTO;
import kr.ac.kookmin.wink.planlist.friend.dto.response.WaitingFriendsResponseDTO;
import kr.ac.kookmin.wink.planlist.friend.service.FriendshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friend")
public class FriendController {

    private final FriendshipService friendshipService;

    @PostMapping("/request")
    public ResponseEntity<?> request(@RequestBody CreateFriendshipRequestDTO requestDTO) {
        friendshipService.createFriendship(requestDTO, System.currentTimeMillis());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/accept/{friendshipId}")
    public ResponseEntity<?> accept(@PathVariable("friendshipId") Long friendshipId) {
        friendshipService.accept(friendshipId);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{friendshipId}")
    public ResponseEntity<?> delete(@PathVariable("friendshipId") Long friendshipId) {
        friendshipService.delete(friendshipId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<UserFriendsResponseDTO>> getFriends(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok().body(friendshipService.findAllFriendsByUser(userId));
    }

    @GetMapping("/{userId}/requests")
    public ResponseEntity<List<WaitingFriendsResponseDTO>> getRequestedFriendships(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok().body(friendshipService.findAllWaitingFriendshipsByUser(userId, true));
    }

    @GetMapping("/{userId}/received")
    public ResponseEntity<List<WaitingFriendsResponseDTO>> getReceivedFriendships(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok().body(friendshipService.findAllWaitingFriendshipsByUser(userId, false));
    }
}