package ru.poymanov.lunchtoday.repository.userVotes;

import ru.poymanov.lunchtoday.model.UserVote;

public interface UserVoteRepository {
    UserVote save(UserVote restaurant);
}
