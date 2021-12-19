package com.qaprosoft.carina.demo.github.issues;

import com.qaprosoft.carina.core.foundation.IAbstractTest;
import com.qaprosoft.carina.core.foundation.api.http.HttpResponseStatusType;
import com.qaprosoft.carina.demo.api.issues.GetIssueMethod;
import com.qaprosoft.carina.demo.api.issues.GetIssuesMethod;
import com.qaprosoft.carina.demo.api.issues.LockIssueMethod;
import com.qaprosoft.carina.demo.api.issues.PostIssueMethod;
import com.qaprosoft.carina.demo.api.repositories.PostRepositoryMethod;
import io.restassured.path.json.JsonPath;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.testng.Assert;
import org.testng.annotations.Test;

public class IssuesApiTest implements IAbstractTest {

    @Test(description = "Authenticated user can create issue for repository")
    public void testCreateIssue() {
        PostRepositoryMethod postRepositoryMethod = new PostRepositoryMethod();
        postRepositoryMethod.expectResponseStatus(HttpResponseStatusType.CREATED_201);
        String repoResponse = postRepositoryMethod.callAPI().asString();

        JsonPath jsonPath = new JsonPath(repoResponse);
        String repoName = jsonPath.getString("name");
        String repoOwner = jsonPath.getString("owner.login");

        PostIssueMethod postIssueMethod = new PostIssueMethod(repoOwner, repoName);
        postIssueMethod.expectResponseStatus(HttpResponseStatusType.CREATED_201);
        postIssueMethod.callAPI();
        postIssueMethod.validateResponseAgainstSchema("api/issues/_post/issue_scheme.json");
        postIssueMethod.validateResponse(JSONCompareMode.LENIENT);
    }

    @Test(description = "Authenticated user can create several issues for repository")
    public void testCreateSeveralIssues() {
        PostRepositoryMethod postRepositoryMethod = new PostRepositoryMethod();
        postRepositoryMethod.expectResponseStatus(HttpResponseStatusType.CREATED_201);
        String repoResponse = postRepositoryMethod.callAPI().asString();

        JsonPath jsonPath = new JsonPath(repoResponse);
        String repoOwner = jsonPath.getString("owner.login");
        String repoName = jsonPath.getString("name");

        int issuesAmount = 5;
        for (int i = 0; i < issuesAmount; i++) {
            PostIssueMethod postIssueMethod = new PostIssueMethod(repoOwner, repoName);
            postIssueMethod.expectResponseStatus(HttpResponseStatusType.CREATED_201);
            postIssueMethod.callAPI();
            postIssueMethod.validateResponseAgainstSchema("api/issues/_post/issue_scheme.json");
            postIssueMethod.validateResponse(JSONCompareMode.LENIENT);
        }

        GetIssuesMethod getIssuesMethod = new GetIssuesMethod(repoOwner, repoName);
        getIssuesMethod.expectResponseStatus(HttpResponseStatusType.OK_200);
        String issuesResponse = getIssuesMethod.callAPI().asString();
        int actualIssuesAmount = new JsonPath(issuesResponse).getList("$").size();

        Assert.assertEquals(actualIssuesAmount, issuesAmount, "Expected and actual issues amount different");
        getIssuesMethod.validateResponseAgainstSchema("api/issues/_get/issues_schema.json");
    }

    @Test(description = "Authenticated user can lock issue")
    public void testLockIssue() {
        PostRepositoryMethod postRepositoryMethod = new PostRepositoryMethod();
        postRepositoryMethod.expectResponseStatus(HttpResponseStatusType.CREATED_201);
        String repoResponse = postRepositoryMethod.callAPI().asString();

        JsonPath jsonPath = new JsonPath(repoResponse);
        String repoOwner = jsonPath.getString("owner.login");
        String repoName = jsonPath.getString("name");

        PostIssueMethod postIssueMethod = new PostIssueMethod(repoOwner, repoName);
        postIssueMethod.expectResponseStatus(HttpResponseStatusType.CREATED_201);
        String issueResponse = postIssueMethod.callAPI().asString();

        int issueNumber = new JsonPath(issueResponse).getInt("number");

        LockIssueMethod lockIssueMethod = new LockIssueMethod(repoOwner, repoName, issueNumber);
        lockIssueMethod.expectResponseStatus(HttpResponseStatusType.NO_CONTENT_204);
        lockIssueMethod.callAPI();

        GetIssueMethod getIssueMethod = new GetIssueMethod(repoOwner, repoName, issueNumber);
        getIssueMethod.expectResponseStatus(HttpResponseStatusType.OK_200);
        String lockedIssueResponse = getIssueMethod.callAPI().asString();
        new JsonPath(lockedIssueResponse);
    }
}
