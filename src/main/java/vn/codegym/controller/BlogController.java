package vn.codegym.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import vn.codegym.model.Blog;
import vn.codegym.model.BlogForm;
import vn.codegym.service.BlogService;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class BlogController {

    private static String UPLOAD_LOCATION="E:/CodeGym Viet Nam/Spring MVC Project/blog-management-jpa/src/main/webapp/static/image/";

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
        md.addObject("blogForm", new BlogForm());

        return md;
    }

    @RequestMapping(value = "/blog/save", method = RequestMethod.POST,
            produces = "application/x-www-from-urlencoded;charset=UTF-8")
    private String saveBlog(@ModelAttribute BlogForm blogForm) throws IOException {
        Blog blog = new Blog();
        MultipartFile multipartFile = blogForm.getFile();
        FileCopyUtils.copy(blogForm.getFile().getBytes(),
                new File(UPLOAD_LOCATION + blogForm.getFile().getOriginalFilename()));

        String imagePath = "/static/image/" + blogForm.getFile().getOriginalFilename();
        //blogService.save(blogForm);

        blog.setName(blogForm.getName());
        blog.setContent(blogForm.getContent());
        blog.setImagePath(imagePath);

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

    @RequestMapping(value = "/blog/edit/{id}", method = RequestMethod.POST,
            produces = "application/x-www-from-urlencoded;charset=UTF-8")
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
