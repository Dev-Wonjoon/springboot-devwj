package net.likelion.bebc25.board3.service;

import net.likelion.bebc25.board3.post.PostDto;
import net.likelion.bebc25.board3.repository.PostRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(@Qualifier("jdbcTemplatePostRepository") PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<PostDto> getPosts() {
        return postRepository.findAll();
    }

    @Override
    public PostDto getPost(int id) {
        return postRepository.findById(id);
    }

    @Override
    public void writePost(PostDto postDto) {
        postRepository.save(postDto);
    }

    @Override
    public void updatePost(PostDto postDto) {
        postRepository.update(postDto);
    }

    @Override
    public void deletePost(int id) {
        postRepository.deleteById(id);
    }
}
