package com.app.service.session;

import javax.servlet.http.HttpServletRequest;

public interface SessionService {
    public long init(HttpServletRequest request);

    public void terminate(HttpServletRequest request);

    public long getUserId(HttpServletRequest request);

}
