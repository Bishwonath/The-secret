/**
 * @author Ujjwal Pandey
 * @version 1.1
 * @since 2022-02-20
 */

package com.example.travellerblog.service;

import com.example.travellerblog.model.Blog;
import com.example.travellerblog.repository.BlogRepository;
import com.example.travellerblog.utils.BlogForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;


@Service
public class BlogServiceImpl implements BlogService {
    private final BlogRepository blogRepository;
    private final ImageStorageService imageStorageService;

    @Autowired
    public BlogServiceImpl(BlogRepository repository, ImageStorageService imageStorageService) {
        this.blogRepository = repository;
        this.imageStorageService = imageStorageService;
    }


    @Override
    public void saveBlog(BlogForm form) throws IOException {
        blogRepository.saveAndFlush(new Blog(
                form.getUserName(),
                form.getName(),
                form.getDate(),
                form.getDescription(),
                imageStorageService.saveImage(form.getCoverImage())
        ));
    }


    @Override
    public void saveBlog(BlogForm form, String id) throws IOException {

        // old blog item which needs to be updated
        Blog oldBlog = blogRepository.getById(Long.parseLong(id));

        if (form.getName() != null) oldBlog.setName(form.getName());
        if (form.getDate() != null) oldBlog.setDate(form.getDate());
        if (form.getDescription() != null) oldBlog.setDescription(form.getDescription());
        if (form.getCoverImage() != null && form.getCoverImage().getSize() > 0) {

            // Delete the old_image
            imageStorageService.delete(oldBlog.getCoverImageLocation(), true);
            // update to the new_image
            oldBlog.setCoverImageLocation(imageStorageService.saveImage(form.getCoverImage()));
        }

        blogRepository.saveAndFlush(oldBlog);
    }



    @Override
    public List<Blog> getBlogList(String userName) {
        List<Blog> blogByUserName = blogRepository.findBlogByUserName(userName);
        blogByUserName.sort(Comparator.comparing(Blog::getDate));
        return blogByUserName;
    }



    @Override
    public byte[] loadImageFile(String imgPath) throws IOException {
        MultipartFile imgFile = imageStorageService.loadImage(imgPath.replace("\\", "/"), false);
        return imgFile.getBytes();
    }



    @Override
    public void deleteBlogItem(String Id) {
        blogRepository.deleteById(Long.parseLong(Id));
    }



    @Override
    public Blog getBlogItem(String Id) throws RuntimeException{
        Optional<Blog> optionalBlog =  blogRepository.findById(Long.parseLong(Id));
        if (optionalBlog.isEmpty()) throw new RuntimeException("Blog Not Found");
        return optionalBlog.get();
    }

}
