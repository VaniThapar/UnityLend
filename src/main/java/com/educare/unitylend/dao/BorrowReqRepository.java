package com.educare.unitylend.dao;

import com.educare.unitylend.model.BorrowRequest;
import com.educare.unitylend.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface BorrowReqRepository {

    @Select(SELECT_REQUESTS_FOR_USER)
    List<BorrowRequest> getAllRequests(@Param("userId") String userId);
    @Select(SELECT_REQUESTS_FOR_USER_AND_COMMUNITY)
    List<BorrowRequest> getAllRequestsForUserAndCommunity(
            @Param("userId") String userId,
            @Param("communityId") String communityId
    );
    @Select(SELECT_REQUESTS_BY_COMMUNITY_AND_AMOUNT)
    List<BorrowRequest> getAllRequestsByCommunityAndAmount(
            @Param("userId") String userId,
            @Param("amount") double amount
    );

    @Select(SELECT_REQUESTS_BY_COMMUNITY_ID)
    List<BorrowRequest> getAllRequestsByCommunityId(
            @Param("communityId") String communityId
    );

    @Select(SELECT_REQUESTS_OF_COMMID_BY_AMOUNT)
    List<BorrowRequest> getAllRequestsOfCommunityByAmount(
            @Param("communityId") String communityId,
            @Param("amount") double amount
    );

    @Select("SELECT COUNT(*) > 0 FROM usercommunity WHERE userid = #{userId} AND communityid = #{communityId}")
    boolean isUserPartOfCommunityR(@Param("userId") String userId, @Param("communityId") String communityId);

    @Select("SELECT COUNT(*) > 0 FROM borrow_request WHERE userid = #{userId} AND status = 'pending'")
    boolean hasPendingRequestsR(@Param("userId") String userId);

    @Select("SELECT COUNT(*) > 0 FROM usercommunity WHERE userid = #{userId}")
    boolean isUserPartOfAnyCommunityR(@Param("userId") String userId);

    @Select("SELECT COUNT(*) > 0 FROM tempuser WHERE userid = #{userId}")
    boolean userExistsR(@Param("userId") String userId);

    @Select("SELECT COUNT(*) > 0 FROM community WHERE communityid = #{communityId}")
    boolean communityExistsR(@Param("communityId") String communityId);
    static final String SELECT_REQUESTS_FOR_USER = "SELECT * FROM borrow_request WHERE communityid IN " +
            "(SELECT communityid FROM usercommunity WHERE userid = #{userId})";

    static final String SELECT_REQUESTS_FOR_USER_AND_COMMUNITY =
            "SELECT br.* FROM borrow_request br " +
                    "WHERE br.communityid IN (SELECT communityid FROM usercommunity WHERE userid = #{userId}) " +
                    "AND br.communityid = #{communityId}";

    static final String SELECT_REQUESTS_BY_COMMUNITY_AND_AMOUNT =
            "SELECT br.* FROM borrow_request br " +
                    "WHERE br.communityid IN (" +
                    "   SELECT communityid FROM usercommunity WHERE userid = #{userId}" +
                    ") AND br.targetamount >= #{amount}";

    static final String SELECT_REQUESTS_BY_COMMUNITY_ID =
            "SELECT * FROM borrow_request WHERE communityid = #{communityId}";

    static final String SELECT_REQUESTS_OF_COMMID_BY_AMOUNT=
            "SELECT * FROM borrow_request WHERE communityid = #{communityId} AND targetamount >= #{amount}";

}
