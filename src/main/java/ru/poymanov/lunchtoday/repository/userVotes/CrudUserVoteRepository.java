package ru.poymanov.lunchtoday.repository.userVotes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.poymanov.lunchtoday.model.UserVote;

@Transactional(readOnly = true)
public interface CrudUserVoteRepository extends JpaRepository<UserVote, Integer> {
    @Override
    @Transactional
    UserVote save(UserVote user);
}
