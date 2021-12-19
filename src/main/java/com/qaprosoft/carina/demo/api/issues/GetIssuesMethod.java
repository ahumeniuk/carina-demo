package com.qaprosoft.carina.demo.api.issues;

import com.qaprosoft.carina.core.foundation.api.AbstractApiMethodV2;
import com.qaprosoft.carina.core.foundation.utils.Configuration;
import com.qaprosoft.carina.core.foundation.utils.R;

public class GetIssuesMethod extends AbstractApiMethodV2 {

    public GetIssuesMethod(String owner, String repoName) {
        super(null, null);
        replaceUrlPlaceholder("base_url", Configuration.getEnvArg("api_url"));
        replaceUrlPlaceholder("owner", owner);
        replaceUrlPlaceholder("repo_name", repoName);
        request.header("Authorization", R.CONFIG.get("github_access_token"));
    }
}
