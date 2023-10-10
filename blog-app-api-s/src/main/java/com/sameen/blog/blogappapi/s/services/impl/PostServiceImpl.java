package com.sameen.blog.blogappapi.s.services.impl;

import com.sameen.blog.blogappapi.s.entities.Category;
import com.sameen.blog.blogappapi.s.entities.Post;
import com.sameen.blog.blogappapi.s.entities.User;
import com.sameen.blog.blogappapi.s.exceptions.ResourceNotFoundException;
import com.sameen.blog.blogappapi.s.payloads.PostDto;
import com.sameen.blog.blogappapi.s.payloads.PostResponse;
import com.sameen.blog.blogappapi.s.repositories.CategoryRepo;
import com.sameen.blog.blogappapi.s.repositories.PostRepo;
import com.sameen.blog.blogappapi.s.repositories.UserRepo;
import com.sameen.blog.blogappapi.s.services.PostService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepo postRepo;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    UserRepo userRepo;
    @Autowired
    CategoryRepo categoryRepo;
//    @Autowired
//    PostResponse postResponse;
    @Override

    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
        log.info("==> ServiceImpl :: Inside createPost() <==");

        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));

        Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "category id", categoryId));

       Post post =  this.modelMapper.map(postDto,Post.class);
       post.setImageName("default.png");
       post.setAddedDate(new Date());
       post.setUser(user);
       post.setCategory(category);

       Post newPost = this.postRepo.save(post);

       return this.modelMapper.map(newPost, PostDto.class);

    }
    @Override
    public Post updatePost(PostDto postDto) {
        log.info("==> ServiceImpl :: Inside updatePost() <==");

        return null;
    }

    @Override
    public void deletePost(Integer postId) {
        log.info("==> ServiceImpl :: Inside deletePost() <==");

    }

    @Override
    public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
    log.info("==> ServiceImpl :: Inside getAllPost() <==");

    Sort sort = null;
        if (sortDir.equalsIgnoreCase("asc")) {
            sort= Sort.by(sortBy).ascending();
        }
        else{
            sort = Sort.by(sortBy).descending();
        }

    Pageable p = PageRequest.of(pageNumber, pageSize,sort);

    Page<Post> pagePost = this.postRepo.findAll(p);
    List<Post> allPosts = pagePost.getContent();
    List<PostDto> postDtos = allPosts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

    PostResponse postResponse = new PostResponse();
    postResponse.setContent(postDtos);
    postResponse.setPageNumber(pagePost.getNumber());
    postResponse.setPageSize(pagePost.getSize());
    postResponse.setTotalPages(pagePost.getTotalPages());
    postResponse.setLastPage(pagePost.isLast());

    return postResponse;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        log.info("==> ServiceImpl :: Inside getPostById() <==");

        Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post" , "post id", postId));
        return this.modelMapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> getPostsByCategory(Integer categoryId) {
        log.info("==> ServiceImpl :: Inside getPostsByCategory() <==");
        Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("category", "category id", categoryId));
        List<Post> postList = this.postRepo.findByCategory(cat);

        List<PostDto> postDtos = postList.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> getPostsByUser(Integer userId) {
        log.info("==> ServiceImpl :: Inside getPostsByUser() <==");

        User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user", "user id", userId));
        List<Post> postList = this.postRepo.findByUser(user);

        List<PostDto> postDto = postList.stream().map((post -> this.modelMapper.map(post, PostDto.class))).collect(Collectors.toList());
        return postDto;
    }

    @Override
    public List<PostDto> searchPosts(String keyword) {
        log.info("==> ServiceImpl :: Inside searchPosts() <==");
        List<Post> postList = this.postRepo.findByTitleContaining(keyword);
        List<PostDto> postDtos = postList.stream().map((posts)-> new ModelMapper().map(posts, PostDto.class)).collect(Collectors.toList());

        return postDtos;
    }
}
