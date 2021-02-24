package httpserver.getmac.controller;

import com.google.gson.Gson;
import httpserver.getmac.MacHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("mac")
public class MacController {

    @RequestMapping("getmac")
    public void getMacAddr(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String macAddr = MacHelper.getMacAddr();
        //前端传过来的回调函数名称
        String callback = request.getParameter("callback");
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("mac", macAddr);
        //用回调函数名称包裹返回数据，这样，返回数据就作为回调函数的参数传回去了，callback()这个（）里面放的是json格式
        String str = callback + "(" + new Gson().toJson(resultMap) + ")";
        response.getWriter().write(str);
    }

}
