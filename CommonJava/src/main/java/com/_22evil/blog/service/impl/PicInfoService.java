package com._22evil.blog.service.impl;

import com._22evil.blog.common.GlobalData;
import com._22evil.blog.entity.PicInfo;
import com._22evil.blog.service.IPicInfoService;
import com._22evil.module_cache.mysql.service.GenericMySqlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PicInfoService implements IPicInfoService {
    @Autowired
    private GenericMySqlService genericMySqlService;

    @Override
    public void initPicData() {
        List<PicInfo> list = genericMySqlService.getBySql(PicInfo.class,"SELECT * FROM tb_pic_info order by create_date DESC LIMIT 30");
        GlobalData globalData = GlobalData.getInstance();
        for (int i = list.size() - 1; i >= 0; i--) {
            PicInfo info = (PicInfo) list.get(i);
            globalData.addRecentPic(info.getPath());
        }
    }

    @Override
    public void addPicInfo(String path) {
        PicInfo picInfo = new PicInfo();
        picInfo.setPath(path);
        genericMySqlService.save(picInfo);
    }
}
