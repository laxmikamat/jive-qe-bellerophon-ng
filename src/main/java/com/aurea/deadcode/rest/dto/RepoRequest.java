package com.aurea.deadcode.rest.dto;

public class RepoRequest {
  
  private String url;
  
  private String branch;

  public String getBranch() {
    return branch;
  }
  
  public String getUrl() {
    return url;
  }
}
