package ru.poymanov.lunchtoday.service.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.poymanov.lunchtoday.model.RestaurantMenu;
import ru.poymanov.lunchtoday.model.User;
import ru.poymanov.lunchtoday.model.UserVote;
import ru.poymanov.lunchtoday.repository.restaurantMenu.RestaurantMenuRepository;
import ru.poymanov.lunchtoday.repository.user.UserRepository;
import ru.poymanov.lunchtoday.repository.userVotes.UserVoteRepository;
import ru.poymanov.lunchtoday.to.RestaurantMenuTo;
import ru.poymanov.lunchtoday.to.UserVoteTo;
import ru.poymanov.lunchtoday.util.RestaurantMenuUtil;
import ru.poymanov.lunchtoday.util.exception.IllegalRequestDataException;
import ru.poymanov.lunchtoday.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    private final RestaurantMenuRepository menuRepository;
    private final UserVoteRepository userVoteRepository;
    private final UserRepository userRepository;

    @Autowired
    public MenuServiceImpl(RestaurantMenuRepository repository, UserVoteRepository userVoteRepository, UserRepository userRepository) {
        this.menuRepository = repository;
        this.userVoteRepository = userVoteRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<RestaurantMenuTo> getTodayMenu() {
        LocalDate now = LocalDate.now();
        return RestaurantMenuUtil.asTo(menuRepository.getAllBetween(LocalDateTime.of(now, LocalTime.MIN), LocalDateTime.of(now, LocalTime.MAX)));
    }

    @Override
    public UserVoteTo voteMenu(int id) {
        RestaurantMenu menu = menuRepository.get(id);

        if (menu == null) {
            throw new IllegalRequestDataException("Menu not found");
        }

        LocalDateTime voteDeadline = LocalDateTime.parse(LocalDate.now().toString() + "T11:00:00");

        if (LocalDateTime.now().isAfter(voteDeadline)) {
            throw new IllegalRequestDataException("Trying to vote after 11:00");
        }

        User user = userRepository.get(SecurityUtil.authUserId());
        UserVote created = userVoteRepository.save(new UserVote(menu, user));
        return new UserVoteTo(created.getId(), menu.getId(), user.getId());
    }
}
