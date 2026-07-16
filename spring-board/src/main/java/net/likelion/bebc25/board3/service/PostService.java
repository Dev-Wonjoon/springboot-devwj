package net.likelion.bebc25.board3.service;

import net.likelion.bebc25.board3.post.PostDto;
import net.likelion.bebc25.board3.repository.PostRepository;

import java.util.List;

public interface PostService {

    public List<PostDto> getPosts();

    public PostDto getPost(int id);

    public void writePost(PostDto postDto);
    public void updatePost(PostDto postDto);
    public void deletePost(int id);
}
