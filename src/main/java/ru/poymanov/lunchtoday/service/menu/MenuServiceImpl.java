package ru.poymanov.lunchtoday.service.menu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.poymanov.lunchtoday.model.RestaurantMenu;
import ru.poymanov.lunchtoday.model.User;
import ru.poymanov.lunchtoday.model.UserVote;
import ru.poymanov.lunchtoday.repository.restaurantMenu.CrudRestaurantMenuRepository;
import ru.poymanov.lunchtoday.repository.user.UserRepository;
import ru.poymanov.lunchtoday.repository.userVotes.CrudUserVoteRepository;
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
    private final CrudRestaurantMenuRepository menuRepository;
    private final CrudUserVoteRepository userVoteRepository;
    private final UserRepository userRepository;

    @Autowired
    public MenuServiceImpl(CrudRestaurantMenuRepository repository, CrudUserVoteRepository userVoteRepository, UserRepository userRepository) {
        this.menuRepository = repository;
        this.userVoteRepository = userVoteRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<RestaurantMenuTo> getTodayMenu() {
        LocalDate now = LocalDate.now();
        return RestaurantMenuUtil.asTo(menuRepository.findAllBetween(LocalDateTime.of(now, LocalTime.MIN), LocalDateTime.of(now, LocalTime.MAX)));
    }

    @Override
    public UserVoteTo voteMenu(int id) {
        RestaurantMenu menu = menuRepository.findById(id).orElse(null);

        if (menu == null) {
            throw new IllegalRequestDataException("Menu not found");
        }

        User user = new User();
        user.setId(SecurityUtil.authUserId());

        List<UserVote> votes = userVoteRepository.findByUserIdBetween(user.getId(), LocalDateTime.of(LocalDate.now(), LocalTime.MIN), LocalDateTime.of(LocalDate.now(), LocalTime.MAX));

        if (votes.size() > 0) {
            if (LocalTime.now().isAfter(LocalTime.of(11, 0))) {
                throw new IllegalRequestDataException("Trying to vote after 11:00");
            }
        }


        UserVote created = userVoteRepository.save(new UserVote(menu, user));
        return new UserVoteTo(created.getId(), menu.getId(), user.getId());
    }
}
