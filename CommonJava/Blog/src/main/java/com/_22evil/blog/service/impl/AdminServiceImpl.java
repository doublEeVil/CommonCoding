package com._22evil.blog.service.impl;

import com._22evil.blog.BlogApp;
import com._22evil.blog.service.IAdminService;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements IAdminService {

    @Override
    public boolean login(String user, String pwd) {
        String cfgUsername = BlogApp.BLOG_CONFIG.username();
        String cfgPassword = BlogApp.BLOG_CONFIG.password();
        if (cfgUsername.equals(user) && cfgPassword.equals(pwd)) {
            return true;
        }
        return false;
    }
}
