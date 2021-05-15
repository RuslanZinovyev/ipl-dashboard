package io.javabrains.ipldashboard.repository;

import io.javabrains.ipldashboard.model.Match;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {

    List<Match> getByTeam1OrTeam2OrderByDateDesc(String teamName1, String teamName2, Pageable pageable);

    @Query("SELECT m FROM Match m WHERE (m.team1 = :teamName OR m.team2 = :teamName) AND m.date BETWEEN :dateStart AND :dateEnd order by m.date desc")
    List<Match> getMatchesByTeamBetweenDates(@Param("teamName") String teamName,
                                             @Param("dateStart") LocalDate dateStart,
                                             @Param("dateEnd") LocalDate dateEnd);

    default List<Match> findLatestMatchesByTeam(String teamName, int count) {
        return getByTeam1OrTeam2OrderByDateDesc(teamName, teamName, PageRequest.of(0, count));
    }

}
