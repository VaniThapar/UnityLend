package com.educare.unitylend.dao;


import com.educare.unitylend.model.BorrowRequest;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BorrowRequestCommunityMapRepository {
    @Insert({
            "<script>",
            "INSERT INTO borrow_request_community_map (borrow_request_id, community_id) VALUES ",
            "<foreach item='communityId' collection='communityIds' separator=','>",
            "(#{requestId}, #{communityId})",
            "</foreach>",
            "</script>"
    })
    void createBorrowRequestCommunityMapping(@Param("requestId") String requestId, @Param("communityIds") List<String> communityIds);
}
