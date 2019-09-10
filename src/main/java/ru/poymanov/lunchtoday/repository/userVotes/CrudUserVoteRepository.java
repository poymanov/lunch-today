package ru.poymanov.lunchtoday.repository.userVotes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.poymanov.lunchtoday.model.UserVote;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudUserVoteRepository extends JpaRepository<UserVote, Integer> {
    @Override
    @Transactional
    UserVote save(UserVote user);

    @Query("SELECT uv from UserVote uv WHERE uv.user.id=:userId AND uv.menu.date BETWEEN :startDate AND :endDate")
    List<UserVote> findByUserIdBetween(@Param("userId") Integer userId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
