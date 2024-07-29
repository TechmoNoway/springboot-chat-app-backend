package com.trickynguci.civicmessagerbackend.service.Impl;

import com.trickynguci.civicmessagerbackend.enums.FriendshipStatus;
import com.trickynguci.civicmessagerbackend.model.Friendship;
import com.trickynguci.civicmessagerbackend.repository.FriendRepository;
import com.trickynguci.civicmessagerbackend.repository.UserRepository;
import com.trickynguci.civicmessagerbackend.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

    private final FriendRepository friendRepository;

    private final UserRepository userRepository;

    @Override
    public boolean checkIsFriend(int userId, int friendId) {
        List<Friendship> friends = friendRepository.findByUserIdAndFriendId(userId, friendId);
        return !friends.isEmpty();
    }

    @Override
    public void addFriend(int userId, int friendId, String status) {
        Friendship friendship = Friendship.builder()
                .user(userRepository.findById(userId))
                .friend(userRepository.findById(friendId))
                .status(FriendshipStatus.valueOf("accepted"))
                .build();
        friendRepository.save(friendship);
    }

    @Override
    public void removeFriend(int userId, int friendId) {
        friendRepository.removeByUserIdAndFriendId(userId, friendId);
    }
}
