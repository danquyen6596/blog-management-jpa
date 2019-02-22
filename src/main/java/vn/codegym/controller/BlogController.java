package vn.codegym.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import vn.codegym.model.Blog;
import vn.codegym.service.BlogService;

import java.util.List;

@Controller
public class BlogController {

    @Autowired
    private BlogService blogService;

    @RequestMapping(value = "/", produces = "application/x-www-from-urlencoded;charset=UTF-8")
    private ModelAndView getListBlog(){
        ModelAndView md = new ModelAndView("index");
        List<Blog> blogList = blogService.findAll();
        md.addObject("blogList", blogList);

        return md;
    }

    @RequestMapping(value = "/blog/create", method = RequestMethod.GET,
            produces = "application/x-www-from-urlencoded;charset=UTF-8")
    private ModelAndView getCreateBlog(){
        ModelAndView md = new ModelAndView("create");
        md.addObject("blog", new Blog());

        return md;
    }

    @RequestMapping(value = "/blog/save", method = RequestMethod.POST,
            produces = "application/x-www-from-urlencoded;charset=UTF-8")
    private String saveBlog(@ModelAttribute("blog") Blog blog){
        blogService.save(blog);

        return "redirect:/";

    }

    @RequestMapping(value = "/blog/view/{id}", method = RequestMethod.GET)
    private ModelAndView getViewBlog(@PathVariable Long id){
        Blog blog = blogService.findById(id);

        ModelAndView md = new ModelAndView("view");
        md.addObject("blog", blog);

        return md;
    }

    @RequestMapping(value = "/blog/edit/{id}", method = RequestMethod.GET)
    private ModelAndView getEditBlog(@PathVariable Long id){
        Blog blog = blogService.findById(id);

        ModelAndView md = new ModelAndView("edit");
        md.addObject("blog", blog);

        return md;
    }

    @RequestMapping(value = "/blog/edit/{id}", method = RequestMethod.POST)
    private ModelAndView setEditBlog(@PathVariable Long id){
        Blog blog = blogService.findById(id);

        ModelAndView md = new ModelAndView("view");
        md.addObject("blog", blog);

        return md;
    }

    @RequestMapping(value = "/blog/delete/{id}", method = RequestMethod.GET)
    private ModelAndView getDeleteBlog(@PathVariable Long id){
        Blog blog = blogService.findById(id);

        ModelAndView md = new ModelAndView("delete");
        md.addObject("blog", blog);

        return md;
    }

    @RequestMapping(value = "/blog/delete", method = RequestMethod.POST)
    private String setDeleteBlog(@RequestParam("id") Long id){
        blogService.remove(id);

        return "redirect:/";
    }


}
