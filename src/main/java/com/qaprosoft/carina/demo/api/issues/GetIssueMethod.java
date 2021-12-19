package com.qaprosoft.carina.demo.api.issues;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.carina.core.foundation.utils.Configuration;
import com.qaprosoft.carina.core.foundation.utils.R;

public class GetIssueMethod extends AbstractApiMethodV2 {

    public GetIssueMethod(String repoOwner, String repoName, int issueNumber) {
        super();
        replaceUrlPlaceholder("base_url", Configuration.getEnvArg("api_url"));
        replaceUrlPlaceholder("owner", repoOwner);
        replaceUrlPlaceholder("repo_name", repoName);
        replaceUrlPlaceholder("issue_number", String.valueOf(issueNumber));
        request.header("Authorization", R.CONFIG.get("github_access_token"));
    }
}
