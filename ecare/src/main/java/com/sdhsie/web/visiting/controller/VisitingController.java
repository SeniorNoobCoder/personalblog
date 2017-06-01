package com.sdhsie.web.visiting.controller;
import com.sdhsie.base.controller.BaseController;
import com.sdhsie.base.util.Const;
import com.sdhsie.base.util.Page;
import com.sdhsie.base.util.PageData;
import com.sdhsie.web.visiting.service.VisitingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by zcx on 2016/12/15.
 */
@Controller
@RequestMapping(value = "/visiting/visiting")
public class VisitingController extends BaseController {
    @Autowired
    private VisitingService visitingService;
    private PageData pd;
    /**
     * 分页查询
     * @param request
     * @param session
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findList")
    public ModelAndView findList(HttpServletRequest request, HttpSession session, Page page) throws Exception {
        pd = new PageData(request);
        mv = getBaseMv(session, pd);
        page.setPd(pd);
        List<PageData> visitingList = visitingService.findListPage(page);
        mv.addObject("pd", pd);
        mv.addObject("visitingList", visitingList);
        mv.addObject("power",getRoleButton(session));
        mv.setViewName("visiting/visiting/visiting_list");
        return mv;
    }

    /**
     * 我的回访单分页查询
     * @param request
     * @param session
     * @param page
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findMyList")
    public ModelAndView findMyList(HttpServletRequest request, HttpSession session, Page page) throws Exception {
        pd = new PageData(request);
        mv = getBaseMv(session, pd);
        PageData user = (PageData) session.getAttribute(Const.SESSION_USER);
        pd.put("operator_id",user.get("id"));
        page.setPd(pd);
        List<PageData> visitingList = visitingService.findListPage(page);
        mv.addObject("pd", pd);
        mv.addObject("visitingList", visitingList);
        mv.addObject("power",getRoleButton(session));
        mv.setViewName("visiting/visiting/visiting_my_list");
        return mv;
    }
}
