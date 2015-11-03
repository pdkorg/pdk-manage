package com.pdk.manage.action.bd;

import com.pdk.manage.model.bd.Position;
import com.pdk.manage.service.bd.PositionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuhaiming on 2015/8/17.
 */
@Controller
@RequestMapping("/bd")
public class PositionAction {
    private static final Logger log = LoggerFactory.getLogger(PositionAction.class);

    @Autowired
    private PositionService positionService;

    @RequestMapping("/bd_position/list")
    public @ResponseBody Map<String, Object> list( ) {

        Map<String, Object> result = new HashMap<>();
        List<Position> positions = null;
        try {
            positions = positionService.selectAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.put("result", "success");
        result.put("data", positions);

        return result;

    }


}
