package com.educare.unitylend.dao;

import com.educare.unitylend.model.BorrowRequest;
import com.educare.unitylend.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Param;
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
}
