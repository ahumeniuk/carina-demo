package com.qaprosoft.carina.demo.api.issues;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.carina.core.foundation.utils.Configuration;
import com.qaprosoft.carina.core.foundation.utils.R;

public class PostIssueMethod extends AbstractApiMethodV2 {

    public PostIssueMethod(String repoOwner, String repoName) {
        super("api/issues/_post/rq.json", "api/issues/_post/rs.json", "api/issues/_post/issue.properties");
        replaceUrlPlaceholder("base_url", Configuration.getEnvArg("api_url"));
        replaceUrlPlaceholder("owner", repoOwner);
        replaceUrlPlaceholder("repo_name", repoName);
        request.header("Authorization", R.CONFIG.get("github_access_token"));
    }
}
