package kr.ac.kookmin.wink.planlist.friend.controller;

import kr.ac.kookmin.wink.planlist.friend.dto.request.AcceptFriendRequestDTO;
import kr.ac.kookmin.wink.planlist.friend.dto.request.CreateFriendshipRequestDTO;
import kr.ac.kookmin.wink.planlist.friend.dto.response.SearchUserResponseDTO;
import kr.ac.kookmin.wink.planlist.friend.dto.response.UserFriendsResponseDTO;
import kr.ac.kookmin.wink.planlist.friend.service.FriendshipService;
import kr.ac.kookmin.wink.planlist.global.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friend")
public class FriendController {

    private final FriendshipService friendshipService;

    @PostMapping
    public ResponseEntity<?> request(@RequestBody CreateFriendshipRequestDTO requestDTO) {
        friendshipService.createFriendship(requestDTO, System.currentTimeMillis());

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<UserFriendsResponseDTO>> getFriends(@AuthenticationPrincipal SecurityUser securityUser) {
        return ResponseEntity.ok().body(friendshipService.findAllFriendsByUser(securityUser.getUser()));
    }

    @PostMapping("/accept")
    public ResponseEntity<?> accept(@RequestBody AcceptFriendRequestDTO requestDTO) {
        friendshipService.accept(requestDTO);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{friendshipId}")
    public ResponseEntity<?> delete(@PathVariable("friendshipId") Long friendshipId) {
        friendshipService.delete(friendshipId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<SearchUserResponseDTO>> getUsersBySearch(@RequestParam(name = "keyword") String keyword, @RequestParam(name = "onlyFriends") boolean onlyFriends) {
        return ResponseEntity.ok().body(friendshipService.findAllUsersBySearch(keyword, onlyFriends));
    }



//    @GetMapping("/test/{userId}")
//    public List<User> test(@PathVariable("userId") Long userId) {
//        return friendshipService.test(userId);
//    }
}