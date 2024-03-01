/**
 * @author Ujjwal Pandey
 * @version 1.1
 * @since 2022-02-20
 */

package com.example.travellerblog.service;

import com.example.travellerblog.model.Blog;
import com.example.travellerblog.utils.BlogForm;

import java.io.IOException;
import java.util.List;



public interface BlogService {


    void saveBlog(BlogForm form) throws IOException;



    void saveBlog(BlogForm form, String id) throws IOException;


    List<Blog> getBlogList(String userName);




    byte[] loadImageFile(String imgPath) throws IOException;



    void deleteBlogItem(String Id);


    Blog getBlogItem(String Id) throws RuntimeException;
}
