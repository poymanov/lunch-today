package ru.poymanov.lunchtoday.repository.userVotes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.poymanov.lunchtoday.model.UserVote;

@Repository
public class UserVoteRepositoryImpl implements UserVoteRepository {
    @Autowired
    private CrudUserVoteRepository crudRepository;

    @Override
    public UserVote save(UserVote vote) {
        return crudRepository.save(vote);
    }
}
