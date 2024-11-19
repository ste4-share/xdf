package com.xdf.xd_f371.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GroupTitle {
    private int id;private String groupName;private String groupCode;

    public GroupTitle(int id, String groupName, String groupCode) {
        this.id = id;
        this.groupName = groupName;
        this.groupCode = groupCode;
    }
}
