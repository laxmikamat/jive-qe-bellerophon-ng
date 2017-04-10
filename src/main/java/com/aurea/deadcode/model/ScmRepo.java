package com.aurea.deadcode.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "url", "branch" }) })
public class ScmRepo {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String uuid;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String branch;

    @Column(nullable = false)
    private CompletionStatus completionStatus;

    @Column
    private String repoDir;


}
