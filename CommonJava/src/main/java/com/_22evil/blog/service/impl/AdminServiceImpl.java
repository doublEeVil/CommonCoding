package com._22evil.blog.service.impl;

import com._22evil.blog.service.IAdminService;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements IAdminService {

    @Override
    public boolean login(String user, String pwd) {

        if ("zjs".equals(user) && "123456".equals(pwd)) {
            return true;
        }
        return false;
    }
}
