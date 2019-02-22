package vn.codegym.service;

import vn.codegym.model.Blog;

import java.util.List;

public interface BlogService {
    List<Blog> findAll();

    Blog findById(Long id);

    void save(Blog model);

    void remove(Long id);
}
